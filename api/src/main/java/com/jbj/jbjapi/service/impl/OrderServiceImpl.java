package com.jbj.jbjapi.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jbj.jbjapi.config.ServiceException;
import com.jbj.jbjapi.domain.Dto.PurchaseDTO;
import com.jbj.jbjapi.domain.Vo.SalesVO;
import com.jbj.jbjapi.domain.param.OrderParam;
import com.jbj.jbjapi.domain.param.PurchaseParam;
import com.jbj.jbjapi.entity.*;
import com.jbj.jbjapi.mapper.OrderMapper;
import com.jbj.jbjapi.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jbj.jbjapi.source.kaka.KakaService;
import com.jbj.jbjapi.source.rainbow.CaihongService;
import com.jbj.jbjapi.source.storage.StorageService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author mhw
 * @since 2023-10-21
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, OrderEntity> implements OrderService {

    @Resource
    private ProductService productService;

    @Resource
    private AftersalesService aftersalesService;

    @Resource
    private BusinessService businessService;

    @Resource
    private SupplierService supplierService;

    @Resource
    private FundService fundService;

    @Resource
    private SourceService sourceService;

    @Resource
    private KakaService kakaService;

    @Resource
    private CaihongService caihongService;

    @Resource
    private StorageService storageService;

    @Override
    public PurchaseParam purchase(PurchaseDTO purchase, BusinessEntity business) {
        if (purchase.getAttach() == null) {
            purchase.setAttach(new ArrayList<>());
        }
        String tip = "";
        if (business.getExpirationTime() == null) {
            tip = "您不是会员";
        } else if (business.getExpirationTime().isBefore(LocalDate.now())) {
            tip = "会员到期";
        }
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setOrderNumber(getOrderNumber());
        orderEntity.setOrderTime(LocalDateTime.now());
        orderEntity.setMerchantId(business.getId());
        orderEntity.setQuantity(purchase.getNum());
        orderEntity.setAttach(JSONUtil.toJsonStr(purchase.getAttach()));
        orderEntity.setProductId(purchase.getProductId());
        ProductEntity product = productService.getById(purchase.getProductId());
        if (product == null) {
            throw new ServiceException("商品不存在");
        }
        List<ProductEntity> entityList = productService.list(new LambdaQueryWrapper<ProductEntity>()
                .eq(ProductEntity::getCategoryId, product.getCategoryId())
                .eq(ProductEntity::getName, product.getName())
        );
        getAllDea(entityList);
        product = entityList.stream()
                .min(Comparator.comparing(ProductEntity::getPriceAsBigDecimal))
                .orElse(null); // 如果未找到最小价格产品，则返回null
        BigDecimal all = null;
        String integrationResponse = "", failResponse = "", kmdata = "";
        if (product != null) {
            orderEntity.setSupplierId(product.getSupplierId());
            orderEntity.setProductName(product.getName());
        }
        if (product == null) {
            tip = "商品信息不存在";
        } else if (business.getQuota().compareTo(new BigDecimal(0)) == 0 || business.getQuota().compareTo(new BigDecimal(0)) < 0) {
            tip = "额度包不足";
        } else {
            try {
                all = new BigDecimal(product.getPrice()).multiply(new BigDecimal(purchase.getNum()));
                if (all.compareTo(business.getWallet()) > 0) {
                    tip = "余额不足";
                }
            } catch (Exception e) {
                throw new ServiceException("下单失败");
            }

            orderEntity.setSupplierId(product.getSupplierId());
            orderEntity.setProductName(product.getName());
            SourceEntity source = sourceService.getById(product.getSourceId());
            if (source == null) {
                throw new ServiceException("下单失败");
            }
            // 我也不想用if，忙 1 storageService 2 caihongService 3 kakaService
            try {
                if (tip.equals("")) {
                    if (source.getSourceSystem().equals("1")) {
                        integrationResponse = storageService.placeAnOrder(source, purchase, product);
                        JSONObject data = JSONUtil.parseObj(integrationResponse);
                        integrationResponse = data.getStr("msg");
                        JSONArray jsonArray = data.getJSONArray("token");
                        if (jsonArray != null) {
                            List<String> list = new ArrayList<>();
                            jsonArray.forEach(res -> {
                                list.add(String.valueOf(res));
                            });
                            kmdata = list.stream().collect(Collectors.joining(", "));
                        }
                    } else if (source.getSourceSystem().equals("2")) {
                        integrationResponse = caihongService.placeAnOrder(source, purchase, product);
                        JSONObject data = JSONUtil.parseObj(integrationResponse);
                        integrationResponse = data.getStr("message");
                        JSONArray jsonArray = data.getJSONArray("kmdata");
                        if (jsonArray != null) {
                            List<String> list = new ArrayList<>();
                            jsonArray.forEach(res -> {
                                JSONObject ee = (JSONObject) res;
                                list.add(ee.getStr("card"));
                            });
                            kmdata = list.stream().collect(Collectors.joining(", "));
                        }
                    } else if (source.getSourceSystem().equals("3")) {
                        integrationResponse = kakaService.placeAnOrder(source, purchase, product);
                        JSONObject data = JSONUtil.parseObj(integrationResponse);
                        integrationResponse = data.getStr("msg");
                        if(StringUtils.hasLength(data.getStr("cardlist"))){
                            JSONArray jsonArray = data.getJSONArray("cardlist");
                            if (jsonArray != null) {
                                List<String> list = new ArrayList<>();
                                jsonArray.forEach(res -> {
                                    list.add(String.valueOf(res));
                                });
                                kmdata = list.stream().collect(Collectors.joining(", "));
                            }
                        }
                    } else {
                        throw new ServiceException("下单失败");
                    }
                }
            } catch (ServiceException e) {
                failResponse = e.getMessage();
            }
        }
        if (StringUtils.hasLength(kmdata)) {
            orderEntity.setKmData(kmdata);
        }
        if (!failResponse.equals("")) {
            orderEntity.setIntegrationResponse(failResponse);
        } else {
            orderEntity.setIntegrationResponse(integrationResponse);
        }
        if (all != null) {
            orderEntity.setPaymentAmount(all);
            orderEntity.setMerchantBalance(business.getWallet().subtract(all));
        }
        orderEntity.setMerchantQuotaBalance(business.getQuota().subtract(new BigDecimal(1)));
        if (tip.equals("") && failResponse.equals("")) {
            orderEntity.setStatus("1");
            orderEntity.setOrderInfo("下单成功");
        } else {
            orderEntity.setStatus("-1");
            if (!failResponse.equals("")) {
                orderEntity.setOrderInfo(failResponse);
            } else {
                orderEntity.setOrderInfo(tip);
            }
        }
        this.save(orderEntity);
        if (tip.equals("") && failResponse.equals("")) {
            business.setWallet(business.getWallet().subtract(all));
            business.setQuota(business.getQuota().subtract(new BigDecimal(1)));
            businessService.updateById(business);

            SupplierEntity supplier = supplierService.getById(product.getSupplierId());
            if (supplier.getBalance() == null) {
                supplier.setBalance(new BigDecimal(0));
            }
            supplier.setBalance(supplier.getBalance().add(all));
            supplierService.updateById(supplier);
            FundEntity fundEntity1 = new FundEntity();
            fundEntity1.setRoleId(business.getId());
            fundEntity1.setRoleType("2");
            fundEntity1.setAmount(all);
            fundEntity1.setBalance(business.getWallet());
            fundEntity1.setType("购买商品");
            fundEntity1.setInfo("购买" + purchase.getNum() + "份商品【" + product.getName() + "】");
            fundService.save(fundEntity1);
            FundEntity fundEntity2 = new FundEntity();
            fundEntity2.setRoleId(supplier.getId());
            fundEntity2.setRoleType("3");
            fundEntity2.setAmount(all);
            fundEntity2.setBalance(supplier.getBalance());
            fundEntity2.setType("售卖商品");
            fundEntity2.setInfo("售卖" + purchase.getNum() + "份商品【" + product.getName() + "】");
            fundService.save(fundEntity2);
        }

        PurchaseParam param = BeanUtil.copyProperties(orderEntity, PurchaseParam.class);
        param.setOrderId(orderEntity.getId());
        if (StringUtils.hasLength(kmdata)) {
            param.setKmData(kmdata);
        }
        if (!tip.equals("")) {
            param.setMsg(tip);
        } else if (!failResponse.equals("")) {
            param.setMsg(failResponse);
        } else {
            param.setMsg(integrationResponse);
        }
        return param;
    }

    @Override
    public Boolean mentary(String key, HttpServletRequest request) {
        OrderEntity orderEntity = this.getById(key);
        BusinessEntity business = businessService.getById(orderEntity.getMerchantId());
        String tip = "";
        if (business.getExpirationTime() == null) {
            tip = "您不是会员";
        } else if (business.getExpirationTime().isBefore(LocalDate.now())) {
            tip = "会员到期";
        }
        ProductEntity product = productService.getById(orderEntity.getProductId());
        List<ProductEntity> entityList = productService.list(new LambdaQueryWrapper<ProductEntity>()
                .eq(ProductEntity::getCategoryId, product.getCategoryId())
                .eq(ProductEntity::getName, product.getName())
        );
        getAllDea(entityList);
        product = entityList.stream()
                .min(Comparator.comparing(ProductEntity::getPriceAsBigDecimal))
                .orElse(null); // 如果未找到最小价格产品，则返回null
        BigDecimal all = null;
        String integrationResponse = "", failResponse = "", kmdata = "";
        if (product == null) {
            tip = "商品信息不存在";
        } else if (business.getQuota().compareTo(new BigDecimal(0)) == 0 || business.getQuota().compareTo(new BigDecimal(0)) < 0) {
            tip = "额度包不足";
        } else {
            try {
                all = new BigDecimal(product.getPrice()).multiply(new BigDecimal(orderEntity.getQuantity()));
                if (all.compareTo(business.getWallet()) > 0) {
                    tip = "余额不足";
                }
            } catch (Exception e) {
                throw new ServiceException("下单失败");
            }
            SourceEntity source = sourceService.getById(product.getSourceId());
            if (source == null) {
                throw new ServiceException("下单失败");
            }
            // 我也不想用if，忙 1 storageService 2 caihongService 3 kakaService
            try {
                if (tip.equals("")) {
                    PurchaseDTO purchase = new PurchaseDTO();
                    purchase.setUrl(request.getRemoteHost());
                    purchase.setProductId(product.getProductId());
                    purchase.setNum(orderEntity.getQuantity());
                    if (orderEntity.getAttach() != null) {
                        purchase.setAttach(JSONUtil.toList(orderEntity.getAttach(), String.class));
                    }
                    if (source.getSourceSystem().equals("1")) {
                        integrationResponse = storageService.placeAnOrder(source, purchase, product);
                        JSONObject data = JSONUtil.parseObj(integrationResponse);
                        integrationResponse = data.getStr("msg");
                        JSONArray jsonArray = data.getJSONArray("token");
                        if (jsonArray != null) {
                            List<String> list = new ArrayList<>();
                            jsonArray.forEach(res -> {
                                list.add(String.valueOf(res));
                            });
                            kmdata = list.stream().collect(Collectors.joining(", "));
                        }
                    } else if (source.getSourceSystem().equals("2")) {
                        integrationResponse = caihongService.placeAnOrder(source, purchase, product);
                        JSONObject data = JSONUtil.parseObj(integrationResponse);
                        integrationResponse = data.getStr("message");
                        JSONArray jsonArray = data.getJSONArray("kmdata");
                        if (jsonArray != null) {
                            List<String> list = new ArrayList<>();
                            jsonArray.forEach(res -> {
                                JSONObject ee = (JSONObject) res;
                                list.add(ee.getStr("card"));
                            });
                            kmdata = list.stream().collect(Collectors.joining(", "));
                        }
                    } else if (source.getSourceSystem().equals("3")) {
                        integrationResponse = kakaService.placeAnOrder(source, purchase, product);
                        JSONObject data = JSONUtil.parseObj(integrationResponse);
                        integrationResponse = data.getStr("msg");
                        JSONArray jsonArray = data.getJSONArray("cardlist");
                        if (jsonArray != null) {
                            List<String> list = new ArrayList<>();
                            jsonArray.forEach(res -> {
                                list.add(String.valueOf(res));
                            });
                            kmdata = list.stream().collect(Collectors.joining(", "));
                        }
                    } else {
                        throw new ServiceException("下单失败");
                    }
                }
            } catch (ServiceException e) {
                failResponse = e.getMessage();
            }
        }
        if (StringUtils.hasLength(kmdata)) {
            orderEntity.setKmData(kmdata);
        }
        if (StringUtils.hasLength(failResponse)) {
            orderEntity.setIntegrationResponse(failResponse);
        } else {
            orderEntity.setIntegrationResponse(integrationResponse);
        }
        if (all != null) {
            orderEntity.setPaymentAmount(all);
            orderEntity.setMerchantBalance(business.getWallet().subtract(all));
        }
        orderEntity.setMerchantQuotaBalance(business.getQuota().subtract(new BigDecimal(1)));
        if (tip.equals("") && failResponse.equals("")) {
            orderEntity.setStatus("1");
            orderEntity.setOrderInfo("下单成功");
        } else {
            orderEntity.setStatus("-1");
            if (!failResponse.equals("")) {
                orderEntity.setOrderInfo(failResponse);
            } else {
                orderEntity.setOrderInfo(tip);
            }
        }
        this.updateById(orderEntity);
        if (tip.equals("") && failResponse.equals("")) {
            business.setWallet(business.getWallet().subtract(all));
            business.setQuota(business.getQuota().subtract(new BigDecimal(1)));
            businessService.updateById(business);

            SupplierEntity supplier = supplierService.getById(product.getSupplierId());
            if (supplier.getBalance() == null) {
                supplier.setBalance(new BigDecimal(0));
            }
            supplier.setBalance(supplier.getBalance().add(all));
            supplierService.updateById(supplier);
            FundEntity fundEntity1 = new FundEntity();
            fundEntity1.setRoleId(business.getId());
            fundEntity1.setRoleType("2");
            fundEntity1.setAmount(all);
            fundEntity1.setBalance(business.getWallet());
            fundEntity1.setType("购买商品");
            fundEntity1.setInfo("购买" + orderEntity.getQuantity() + "份商品【" + product.getName() + "】");
            fundService.save(fundEntity1);
            FundEntity fundEntity2 = new FundEntity();
            fundEntity2.setRoleId(supplier.getId());
            fundEntity2.setRoleType("3");
            fundEntity2.setAmount(all);
            fundEntity2.setBalance(supplier.getBalance());
            fundEntity2.setType("售卖商品");
            fundEntity2.setInfo("售卖" + orderEntity.getQuantity() + "份商品【" + product.getName() + "】");
            fundService.save(fundEntity2);
        }
        return true;
    }

    @Override
    public List<OrderParam> order(String order, BusinessEntity business) {
        List<OrderEntity> list = this.list(new LambdaQueryWrapper<OrderEntity>()
                .eq(StringUtils.hasLength(order), OrderEntity::getOrderNumber, order)
                .eq(OrderEntity::getMerchantId, business.getId())
        );
        return BeanUtil.copyToList(list, OrderParam.class);
    }

    @Override
    public Boolean sales(SalesVO salesVO) {
        OrderEntity orderEntity = this.getById(salesVO.getId());
        orderEntity.setStatus("2");
        this.updateById(orderEntity);
        AftersalesEntity aftersales = new AftersalesEntity();
        aftersales.setOrderId(orderEntity.getId());
        aftersales.setIssue(salesVO.getReason());
        aftersales.setStatus("1");
        aftersales.setCreateTime(LocalDateTime.now());
        return aftersalesService.save(aftersales);
    }

    @Override
    public Double getAllTransaction(AccountEntity account) {
        return this.baseMapper.getAllTransaction(account);
    }

    @Override
    public List<Map<String, String>> getTransactionData() {
        return this.baseMapper.getTransactionData();
    }

    @Override
    public List<Map<String, String>> getBusTransactionData() {
        return this.baseMapper.getBusTransactionData();
    }

    @Override
    public List<Map<String, String>> category7(String roleType, String roleId) {
        return this.baseMapper.category7(roleType, roleId);
    }

    private String getOrderNumber() {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String formattedDate = currentDate.format(formatter);
        OrderEntity orderEntity = this.getOne(new LambdaQueryWrapper<OrderEntity>()
                .select(OrderEntity::getOrderNumber)
                .likeRight(OrderEntity::getOrderNumber, formattedDate)
                .orderByDesc(OrderEntity::getOrderNumber)
                .last("LIMIT 1")
        );
        if (orderEntity == null) {
            formattedDate += "00000001";
            return formattedDate;
        } else {
            String num = orderEntity.getOrderNumber();
            num.replace(formattedDate, "");
            Long tmp = Long.valueOf(num);
            tmp = tmp + 1;
            return String.format("%08d", tmp);
        }
    }

    @Override
    public void getAllDea(List<ProductEntity> list) {
        if (list == null || list.size() == 0) {
            return;
        }
        // 创建线程池
        ExecutorService executorService = Executors.newFixedThreadPool(list.size());
        // 创建Future列表
        List<Future<?>> futures = new ArrayList<>();
        // 提交任务到线程池
        for (ProductEntity res : list) {
            Future<?> future = executorService.submit(() -> productService.getPicDetails(res));
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
}
