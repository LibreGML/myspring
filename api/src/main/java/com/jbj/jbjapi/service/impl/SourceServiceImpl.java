package com.jbj.jbjapi.service.impl;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jbj.jbjapi.config.LoginHelper;
import com.jbj.jbjapi.config.ServiceException;
import com.jbj.jbjapi.domain.Vo.CommodityVo;
import com.jbj.jbjapi.domain.Vo.SourceVo;
import com.jbj.jbjapi.domain.param.CommodityParam;
import com.jbj.jbjapi.entity.*;
import com.jbj.jbjapi.mapper.SourceMapper;
import com.jbj.jbjapi.service.*;
import com.jbj.jbjapi.source.kaka.KakaService;
import com.jbj.jbjapi.source.rainbow.CaihongService;
import com.jbj.jbjapi.source.storage.StorageService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

/**
 * <p>
 * 菜单与角色关联表 服务实现类
 * </p>
 *
 * @author mhw
 * @since 2023-10-21
 */
@Service
public class SourceServiceImpl extends ServiceImpl<SourceMapper, SourceEntity> implements SourceService {

    @Resource
    private SupplierService supplierService;

    @Resource
    private AccountService accountService;

    @Resource
    private KakaService kakaService;

    @Resource
    private StorageService storageService;

    @Resource
    private ProductService productService;

    @Resource
    private CaihongService caihongService;


    @Resource
    private SourceService sourceService;

    @Resource
    private CategoryService categoryService;

    @Resource
    private UploadService uploadService;

    @Override
    public Page<SourceEntity> getList(SourceVo vo) {
        AccountEntity account = LoginHelper.getLoginUser();
        Page<SourceEntity> page;
        if (account.getRoleType().equals("1")) {
            page = this.page(vo.build(), new LambdaQueryWrapper<SourceEntity>()
                    .eq(!StringUtils.isEmpty(vo.getSourceId()), SourceEntity::getSourceSystem, vo.getSourceId())
                    .eq(!StringUtils.isEmpty(vo.getExamine()), SourceEntity::getExamine, vo.getExamine())
                    .orderByDesc(SourceEntity::getCreateTime)
            );
            page.getRecords().forEach(res -> {
                SupplierEntity supplier = supplierService.getById(res.getSupplierId());
                AccountEntity account1 = accountService.getOne(new LambdaQueryWrapper<AccountEntity>()
                        .eq(AccountEntity::getRoleType, 3).eq(AccountEntity::getRoleId, res.getSupplierId()).last("LIMIT 1")
                );
                if (supplier != null) {
                    res.setSupplierName(supplier.getName());
                }
                if (account1 != null) {
                    res.setAccountId(account1.getId());
                }
            });
        } else if (account.getRoleType().equals("3")) {
            SupplierEntity supplier = supplierService.getById(account.getRoleId());
            page = this.page(vo.build(), new LambdaQueryWrapper<SourceEntity>()
                    .eq(!StringUtils.isEmpty(vo.getSourceId()), SourceEntity::getSourceSystem, vo.getSourceId())
                    .eq(!StringUtils.isEmpty(vo.getExamine()), SourceEntity::getExamine, vo.getExamine())
                    .eq(SourceEntity::getSupplierId, supplier.getId())
                    .orderByDesc(SourceEntity::getCreateTime)
            );
        } else {
            throw new ServiceException("无权限");
        }
        return page;
    }

    @Override
    public void setBalance(SourceEntity source) {
        try {
            JSONObject user = null;
            if (source.getSourceSystem().equals("1")) {
                user = storageService.getUserInfo(source);
                source.setBalance(user == null ? "" : user.getJSONObject("result").getStr("balance"));
            }
            if (source.getSourceSystem().equals("2")) {
                user = caihongService.getUserInfo(source);
            }
            if (source.getSourceSystem().equals("3")) {
                user = kakaService.getUserInfo(source);
            }
        } catch (Exception e) {
            //do noting
        }
    }

    @Override
    public Page<CommodityParam> getBeforeFl(CommodityVo vo, HttpServletRequest request) {
        if (!StringUtils.isEmpty(vo.getProName())) {
            vo.setName(vo.getProName());
            Page<CommodityParam> page = commodity(vo, "", request);
            page.getRecords().forEach(res -> {
                res.setType("2");
            });
            return page;
        }
        SourceEntity source = this.getById(vo.getSourceId());
        String url = request.getRemoteHost();
        source.setUrl(url);
        List<CommodityParam> list = new ArrayList<>();
        switch (source.getSourceSystem()) {
            case "1":
                list = storageService.getAllFl(source);
                break;
            case "2":
                list = caihongService.getAllFl(source);
                break;
            case "3":
                list = kakaService.getAllFl(source);
                break;
            default:
                throw new ServiceException("货源不存在");
        }
        if (!StringUtils.isEmpty(vo.getName())) {
            list = list.stream().filter(q -> q.getName().contains(vo.getName())).collect(Collectors.toList());
        }
        int total = list.size();
        int pageNum = vo.getPageNum();
        int pageSize = vo.getPageSize();
        int startIndex = (pageNum - 1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize, total);
        List<CommodityParam> pageList = list.subList(startIndex, endIndex);
        pageList.forEach(res -> {
            res.setChildren(new ArrayList<>());
            res.setType("1");
        });
        Page<CommodityParam> page = new Page<>(pageNum, pageSize, total);
        page.setRecords(pageList);
        return page;
    }

    @Override
    public void getBeforeFlInt(Map<String, Object> vo, String sourceId, HttpServletRequest request) {
        SourceEntity source = sourceService.getById(sourceId);
        String url = request.getRemoteHost();
        source.setUrl(url);
        List<Map<String, String>> list = (List<Map<String, String>>) vo.get("selectList");
        if (vo.get("flId") == null || ((String) vo.get("flId")).equals("")) {
            throw new ServiceException("请选择分类");
        }
        String flId = (String) vo.get("flId");
        String name = vo.get("name") == null ? "" : (String) vo.get("name");
        List<Map<String, Object>> uppicList = (List<Map<String, Object>>) vo.get("uppic");
        String uppic = "";
        if (uppicList.size() > 0 && uppicList.get(0).get("response") != null) {
            Map<String, Object> map = uppicList.get(0);
            Map<String, Object> map2 = (Map<String, Object>) map.get("response");
            uppic = (String) map2.get("url");
        }
        String pic = (Boolean) vo.get("upType") ? (String) vo.get("pic") : uppic;
        String cunType = (String) vo.get("cunType");
        CategoryEntity category = categoryService.getById(flId);
        CommodityVo vo1 = new CommodityVo();
        vo1.setSourceId(sourceId);
        vo1.setPageNum(1);
        vo1.setPageSize(Integer.MAX_VALUE);
        final Page<CommodityParam>[] page = new Page[]{null};
        list.forEach(res -> {
            if (res.get("type").equals("1")) {
                if (page[0] == null) {
                    page[0] = commodity(vo1, "", request);
                }
                List<CommodityParam> listPa = page[0].getRecords().stream().filter(q -> q.getCid() != null && q.getCid().equals(res.get("id"))).collect(Collectors.toList());
                listPa.forEach(res2 -> {
                    savePrd(source, res2.getId(), category, name, pic, cunType, request);
                });
            }
            if (res.get("type").equals("2")) {
                savePrd(source, res.get("id"), category, name, pic, cunType, request);
            }
        });
    }

    @Override
    public void getBeforeFlIntAll(Map<String, Object> vo, HttpServletRequest request) {
        List<Map<String, String>> selectList = (List<Map<String, String>>) vo.get("selectList");
        if (vo.get("flId") == null || ((String) vo.get("flId")).equals("")) {
            throw new ServiceException("请选择分类");
        }
        String flId = (String) vo.get("flId");
        String name = vo.get("name") == null ? "" : (String) vo.get("name");
        List<Map<String, Object>> uppicList = (List<Map<String, Object>>) vo.get("uppic");
        String uppic = "";
        if (uppicList.size() > 0 && uppicList.get(0).get("response") != null) {
            Map<String, Object> map = uppicList.get(0);
            Map<String, Object> map2 = (Map<String, Object>) map.get("response");
            uppic = (String) map2.get("url");
        }
        String pic = (Boolean) vo.get("upType") ? (String) vo.get("pic") : uppic;
        String cunType = (String) vo.get("cunType");
        CategoryEntity category = categoryService.getById(flId);
        Map<String, List<Map<String, String>>> grouped = selectList.stream()
                .collect(Collectors.groupingBy(m -> m.get("sourceId"), Collectors.toList()));
        // 输出分组结果
        grouped.forEach((key, value) -> {
            SourceEntity source = sourceService.getById(key);
            value.forEach(res -> {
                try {
                    savePrd(source, res.get("id"), category, name, pic, cunType, request);
                } catch (Exception e) {
                    e.printStackTrace();
                    // do nothing
                }
            });
        });

    }

    @Override
    public Page<CommodityParam> getAllProduct(CommodityVo vo, HttpServletRequest request) {
        List<Map<String, String>> selectSource = vo.getSelectSource();
        List<CommodityParam> beList = new ArrayList<>();
        selectSource.forEach(res -> {
            try {
                CommodityVo vo2 = new CommodityVo();
                vo2.setSourceId(res.get("id"));
                vo2.setPageNum(1);
                vo2.setName(vo.getName());
                vo2.setPageSize(Integer.MAX_VALUE);
                Page<CommodityParam> paramPage = commodity(vo2, "", request);
                paramPage.getRecords().forEach(res1 -> {
                    res1.setSourceId(res.get("id"));
                });
                beList.addAll(paramPage.getRecords());
            } catch (Exception e) {
                // do noting
            }
        });
        List<CommodityParam> list = beList;
        if (!StringUtils.isEmpty(vo.getName())) {
            list = list.stream().filter(q -> q.getName().contains(vo.getName())).collect(Collectors.toList());
        }
        int total = list.size();
        int pageNum = vo.getPageNum();
        int pageSize = vo.getPageSize();
        int startIndex = (pageNum - 1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize, total);
        List<CommodityParam> pageList = list.subList(startIndex, endIndex);
        Page<CommodityParam> page = new Page<>(pageNum, pageSize, total);
        page.setRecords(pageList);
        return page;
    }

    @Override
    public void reshProList(HttpServletRequest request) {
        List<SourceEntity> list = sourceService.list();

        // 创建线程池
        ExecutorService executorService = Executors.newFixedThreadPool(list.size());
        // 创建Future列表
        List<Future<?>> futures = new ArrayList<>();
        // 提交任务到线程池
        for (SourceEntity res : list) {
            Future<?> future = executorService.submit(() -> {
                try {
                    switch (res.getSourceSystem()) {
                        case "1":
                            storageService.getCommodity(res, false);
                            break;
                        case "2":
                            caihongService.getCommodity(res, false);
                            break;
                        case "3":
                            kakaService.getCommodity(res, false);
                            break;
                        default:
                            throw new ServiceException("货源不存在");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    // do nothing
                }
            });
            futures.add(future);
        }
        // 等待所有任务完成
        for (Future<?> future : futures) {
            try {
                future.get();
            } catch (InterruptedException | ExecutionException e) {
                // 处理异常
                e.printStackTrace();
            }
        }
        // 关闭线程池
        executorService.shutdown();
    }

    private void savePrd(SourceEntity source, String key, CategoryEntity category, String name, String pic, String cunType, HttpServletRequest request) {
        if (productService.count(new LambdaQueryWrapper<ProductEntity>()
                .eq(ProductEntity::getSourceId, source.getId())
                .eq(ProductEntity::getProductId, key)
        ) == 0) {
            ProductEntity productEntity = new ProductEntity();
            productEntity.setSourceId(source.getId());
            productEntity.setProductId(key);
            productEntity.setStatus("2");
            productEntity.setSupplierId(source.getSupplierId());
            JSONObject ob, data;
            switch (source.getSourceSystem()) {
                case "1":
                    ob = storageService.getProductDetails(source, key);
                    data = ob.getJSONObject("data");
                    productEntity.setPrice(data.getStr("money"));
                    productEntity.setName(data.getStr("name"));
                    JSONArray image = data.getJSONArray("image");
                    productEntity.setDetails(data.getStr("docs"));
                    if (image.size() > 0) {
                        productEntity.setImage(image.getStr(0));
                    }
                    break;
                case "2":
                    ob = caihongService.getProductDetails(source, key);
                    data = ob.getJSONObject("data");
                    productEntity.setPrice(data.getStr("price"));
                    productEntity.setName(data.getStr("name"));
                    productEntity.setImage(data.getStr("shopimg"));
                    productEntity.setDetails(data.getStr("desc"));
                    break;
                case "3":
                    ob = kakaService.getProductDetails(source, key);
                    data = ob.getJSONObject("goodsdetails");
                    productEntity.setPrice(data.getStr("goodsprice"));
                    productEntity.setName(data.getStr("goodsname"));
                    productEntity.setImage(data.getStr("imgurl"));
                    productEntity.setDetails(data.getStr("details"));
                    break;
                default:
                    throw new ServiceException("货源不存在");
            }
            if (!StringUtils.isEmpty(name)) {
                productEntity.setName(name);
            }
            if (!StringUtils.isEmpty(pic)) {
                productEntity.setImage(pic);
            }
            productEntity.setCategoryId(category.getId());
            productEntity.setCategoryName(category.getName());
            productEntity.setRemarks("预售转在售，自动通过");
            productService.save(productEntity);
            uploadService.zhuancImg(productEntity, cunType, request, StringUtils.isEmpty(pic));
        }
    }

    @Override
    public Page<CommodityParam> commodityDel(CommodityVo vo, String id, HttpServletRequest request) {
        SourceEntity source = this.getById(vo.getSourceId());
        String url = request.getRemoteHost();
        source.setUrl(url);
        List<CommodityParam> list = new ArrayList<>();
        switch (source.getSourceSystem()) {
            case "1":
                list = storageService.getCommodity(source, true);
                break;
            case "2":
                list = caihongService.getCommodity(source, true);
                break;
            case "3":
                list = kakaService.getCommodity(source, true);
                break;
            default:
                throw new ServiceException("货源不存在");
        }
        if (!StringUtils.isEmpty(id)) {
            list = list.stream().filter(q -> q.getCid() != null && q.getCid().equals(id)).collect(Collectors.toList());
        }
        if (!StringUtils.isEmpty(vo.getName())) {
            list = list.stream().filter(q -> q.getName() != null && q.getName().contains(vo.getName())).collect(Collectors.toList());
        }
        int total = list.size();
        int pageNum = vo.getPageNum();
        int pageSize = vo.getPageSize();
        int startIndex = (pageNum - 1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize, total);
        List<CommodityParam> pageList = list.subList(startIndex, endIndex);
        pageList.forEach(res -> {
            res.setType("2");
            ProductEntity product = productService.getOne(new LambdaQueryWrapper<ProductEntity>()
                    .eq(ProductEntity::getProductId, res.getId())
                    .eq(ProductEntity::getSourceId, source.getId())
                    .last("LIMIT 1")
            );
            if (product != null) {
                res.setState(product.getStatus());
                res.setProductId(product.getId());
                res.setReason(product.getRemarks());
            } else {
                res.setState("0");
            }
        });
        if (source.getSourceSystem().equals("1")) {
            pageList.forEach(res -> {
                try {
                    JSONObject jsonObject1 = storageService.getProductDetails(source, res.getId());
                    JSONObject data = jsonObject1.getJSONObject("data");
                    res.setPrice(data.getStr("money"));
                    JSONArray image = data.getJSONArray("image");
                    if (image.size() > 0) {
                        res.setImgUrl(image.getStr(0));
                    }
                } catch (Exception e) {
                    // do noting
                }
            });
        }
        Page<CommodityParam> page = new Page<>(pageNum, pageSize, total);
        page.setRecords(pageList);
        return page;
    }


    @Override
    public Page<CommodityParam> commodity(CommodityVo vo, String id, HttpServletRequest request) {
        SourceEntity source = this.getById(vo.getSourceId());
        String url = request.getRemoteHost();
        source.setUrl(url);
        List<CommodityParam> list = new ArrayList<>();
        switch (source.getSourceSystem()) {
            case "1":
                list = storageService.getCommodity(source, true);
                break;
            case "2":
                list = caihongService.getCommodity(source, true);
                break;
            case "3":
                list = kakaService.getCommodity(source, true);
                break;
            default:
                throw new ServiceException("货源不存在");
        }
        if (!StringUtils.isEmpty(id)) {
            list = list.stream().filter(q -> q.getCid() != null && q.getCid().equals(id)).collect(Collectors.toList());
        }
        if (!StringUtils.isEmpty(vo.getName())) {
            list = list.stream().filter(q -> q.getName() != null && q.getName().contains(vo.getName())).collect(Collectors.toList());
        }
        int total = list.size();
        int pageNum = vo.getPageNum();
        int pageSize = vo.getPageSize();
        int startIndex = (pageNum - 1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize, total);
        List<CommodityParam> pageList = list.subList(startIndex, endIndex);
        pageList.forEach(res -> {
            res.setType("2");
        });
        Page<CommodityParam> page = new Page<>(pageNum, pageSize, total);
        page.setRecords(pageList);
        return page;
    }

    @Override
    public Boolean apply(String key, String ly, String sourceId, HttpServletRequest request) {
        AccountEntity account = LoginHelper.getLoginUser();
        if (!account.getRoleType().equals("3")) {
            throw new ServiceException("无权限");
        }
        SupplierEntity supplier = supplierService.getById(account.getRoleId());
        SourceEntity source = this.getById(sourceId);
        String url = request.getRemoteHost();
        source.setUrl(url);
        ProductEntity productEntity = new ProductEntity();
        productEntity.setSourceId(sourceId);
        productEntity.setProductId(key);
        productEntity.setStatus("1");
        productEntity.setSupplierId(supplier.getId());
        productEntity.setReason(ly);
        JSONObject ob, data;
        switch (source.getSourceSystem()) {
            case "1":
                ob = storageService.getProductDetails(source, key);
                data = ob.getJSONObject("data");
                productEntity.setPrice(data.getStr("money"));
                productEntity.setName(data.getStr("name"));
                JSONArray image = data.getJSONArray("image");
                productEntity.setDetails(data.getStr("docs"));
                if (image.size() > 0) {
                    productEntity.setImage(image.getStr(0));
                }
                break;
            case "2":
                ob = caihongService.getProductDetails(source, key);
                data = ob.getJSONObject("data");
                productEntity.setPrice(data.getStr("price"));
                productEntity.setName(data.getStr("name"));
                productEntity.setImage(data.getStr("shopimg"));
                productEntity.setDetails(data.getStr("desc"));
                break;
            case "3":
                ob = kakaService.getProductDetails(source, key);
                data = ob.getJSONObject("goodsdetails");
                productEntity.setPrice(data.getStr("goodsprice"));
                productEntity.setName(data.getStr("goodsname"));
                productEntity.setImage(data.getStr("imgurl"));
                productEntity.setDetails(data.getStr("details"));
                break;
            default:
                throw new ServiceException("货源不存在");
        }
        return productService.save(productEntity);
    }
}
