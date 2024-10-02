package com.jbj.jbjapi.service.impl;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jbj.jbjapi.config.ServiceException;
import com.jbj.jbjapi.domain.Vo.ProductVO;
import com.jbj.jbjapi.entity.ProductEntity;
import com.jbj.jbjapi.entity.SourceEntity;
import com.jbj.jbjapi.mapper.ProductMapper;
import com.jbj.jbjapi.service.ProductService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jbj.jbjapi.service.SourceService;
import com.jbj.jbjapi.service.UploadService;
import com.jbj.jbjapi.source.kaka.KakaService;
import com.jbj.jbjapi.source.rainbow.CaihongService;
import com.jbj.jbjapi.source.storage.StorageService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author mhw
 * @since 2023-10-21
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, ProductEntity> implements ProductService {

    @Resource
    private SourceService sourceService;

    @Resource
    private KakaService kakaService;

    @Resource
    private StorageService storageService;

    @Resource
    private CaihongService caihongService;

    @Resource
    private UploadService uploadService;

    @Override
    public Page<ProductEntity> getProduct(ProductVO vo) {
        Page<ProductEntity> page = this.page(vo.build(), new LambdaQueryWrapper<ProductEntity>()
                .eq(StringUtils.hasLength(vo.getCategory()), ProductEntity::getCategoryId, vo.getCategory())
                .like(StringUtils.hasLength(vo.getName()), ProductEntity::getName, vo.getName())
                .eq(ProductEntity::getStatus, "2")
                .orderByDesc(ProductEntity::getCreateTime)
        );
        if (page.getRecords() == null || page.getRecords().size() == 0) {
            return page;
        }
        // 创建线程池
        ExecutorService executorService = Executors.newFixedThreadPool(page.getRecords().size());
        // 创建Future列表
        List<Future<?>> futures = new ArrayList<>();
        // 提交任务到线程池
        for (ProductEntity res : page.getRecords()) {
            Future<?> future = executorService.submit(() -> getPicDetails(res));
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
        return page;
    }

    @Override
    public void getPicDetails(ProductEntity res) {
        try {
            SourceEntity source = sourceService.getById(res.getSourceId());
            String key = res.getProductId();
            JSONObject ob, data;
            switch (source.getSourceSystem()) {
                case "1":
                    ob = storageService.getProductDetails(source, key);
                    data = ob.getJSONObject("data");
                    res.setPrice(data.getStr("money"));
                    res.setInventory(data.getStr("quota") == null ? "无限" : data.getStr("quota"));
                    List<String> liste = new ArrayList<>();
                    if (StringUtils.hasLength(data.getStr("input"))) {
                        JSONArray maps = data.getJSONArray("input");
                        maps.forEach(ress -> {
                            JSONObject ew = (JSONObject) ress;
                            liste.add(ew.getStr("name"));
                        });
                    }
                    res.setAttachPam(data.getStr("input"));
                    res.setAttach(liste);
                    break;
                case "2":
                    ob = caihongService.getProductDetails(source, key);
                    data = ob.getJSONObject("data");
                    res.setPrice(data.getStr("price"));
                    res.setInventory(data.getStr("stock") == null ? "无限" : data.getStr("stock"));
                    List<String> list = new ArrayList<>();
                    if (StringUtils.hasLength(data.getStr("input"))) {
                        list.add(data.getStr("input"));
                    }
                    if (StringUtils.hasLength(data.getStr("inputs"))) {
                        String inputs = data.getStr("inputs");
                        List<String> list1 = Arrays.asList(inputs.split("\\|"));
                        list.addAll(list1);
                    }
                    List<Map<String, String>> apam = new ArrayList<>();
                    list.forEach(res1 -> {
                        Map map = new HashMap();
                        map.put("type", "1");
                        map.put("name", res1);
                        apam.add(map);
                    });
                    res.setAttach(list);
                    res.setAttachPam(apam);
                    break;
                case "3":
                    ob = kakaService.getProductDetails(source, key);
                    data = ob.getJSONObject("goodsdetails");
                    res.setPrice(data.getStr("goodsprice"));
                    res.setInventory(data.getStr("stock") == null ? "无限" : data.getStr("stock"));
                    List<String> listes = new ArrayList<>();
                    if (StringUtils.hasLength(data.getStr("attach"))) {
                        JSONArray maps = data.getJSONArray("attach");
                        maps.forEach(ress -> {
                            JSONObject ew = (JSONObject) ress;
                            listes.add(ew.getStr("tip"));
                        });
                    }
                    res.setAttach(listes);
                    List<Map<String, String>> apamq = new ArrayList<>();
                    listes.forEach(res1 -> {
                        Map map = new HashMap();
                        map.put("type", "1");
                        map.put("name", res1);
                        apamq.add(map);
                    });
                    res.setAttachPam(apamq);
                    break;
                default:
            }
        } catch (Exception e) {
            // do thing
        }
    }

    @Override
    public Boolean editFl(Map<String, Object> vo) {
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
        list.forEach(res -> {
            ProductEntity product = this.getById(res.get("id"));
            product.setCategoryId(flId);
            if (!org.apache.commons.lang3.StringUtils.isEmpty(name)) {
                product.setName(name);
            }
            if (!org.apache.commons.lang3.StringUtils.isEmpty(pic)) {
                product.setImage(pic);
            }
            this.updateById(product);
        });
        return true;
    }

    @Override
    public ProductEntity selectByPrimaryKey(String key) {
        ProductEntity product = this.getById(key);
        getPicDetails(product);
        return product;
    }

    @Override
    public Boolean updateByPrimaryKey(Map<String, Object> vo, HttpServletRequest request) {
        ProductEntity product = this.getById(String.valueOf(vo.get("id")));

        List<Map<String, Object>> uppicList = (List<Map<String, Object>>) vo.get("uppic");
        String uppic = "";
        if (uppicList.size() > 0 && uppicList.get(0).get("response") != null) {
            Map<String, Object> map = uppicList.get(0);
            Map<String, Object> map2 = (Map<String, Object>) map.get("response");
            uppic = (String) map2.get("url");
        }
        String pic = (Boolean) vo.get("upType") ? (String) vo.get("pic") : uppic;
        String name = vo.get("name") == null ? "" : (String) vo.get("name");
        if (!StringUtils.isEmpty(name)) {
            product.setName(name);
        }
        if (!StringUtils.isEmpty(pic)) {
            product.setImage(pic);
        }
        product.setStatus(String.valueOf(vo.get("status")));
        product.setRemarks(String.valueOf(vo.get("remarks")));
        product.setCategoryId(String.valueOf(vo.get("categoryId")));
        uploadService.zhuancImg(product, String.valueOf(vo.get("cunType")), request, StringUtils.isEmpty(pic));
        return this.updateById(product);
    }
}
