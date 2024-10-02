package com.jbj.jbjapi.source.storage;

import cn.hutool.http.HttpException;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.jbj.jbjapi.config.ServiceException;
import com.jbj.jbjapi.domain.Dto.PurchaseDTO;
import com.jbj.jbjapi.domain.param.CommodityParam;
import com.jbj.jbjapi.entity.ProductEntity;
import com.jbj.jbjapi.entity.SourceEntity;
import com.jbj.jbjapi.source.SignUtil;
import com.jbj.jbjapi.utils.RedisUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
public class StorageService {

    @Resource
    private RedisUtil redisUtil;

    public JSONObject getUserInfo(SourceEntity entity) {
        if (entity.getUrl() == null) {
            entity.setUrl("jbj");
        }
        String appTimestamp = String.valueOf(System.currentTimeMillis());
        String appToken = SignUtil.getAppToken(entity.getDockingAccount(), entity.getDockingKey(),
                "/api/client/account/v2/profile", appTimestamp);

        String body;
        try {
            Map<String, String> map = new HashMap<>();
            body = HttpRequest.post(entity.getSourceWebsite() + "/api/index.php?zhike=/api/client/account/v2/profile")
                    .header("AppId", entity.getDockingAccount())
                    .header("AppToken", appToken)
                    .header("AppTimestamp", appTimestamp)
                    .execute()
                    .body();
            JSONObject jsonObject = JSONUtil.parseObj(body);
            return jsonObject;
        } catch (Exception e) {
            throw new ServiceException("获取用户信息异常");
        }
    }

    public List<CommodityParam> getCommodity(SourceEntity source, Boolean isCa) {
        if (isCa) {
            Object commodityList = redisUtil.get("CommodityList:" + source.getId());
            if (commodityList != null) {
                return JSONUtil.toList(String.valueOf(commodityList), CommodityParam.class);
            }
        }
        String body;
        try {
            if (source.getUrl() == null) {
                source.setUrl("jbj");
            }
            Map<String, String> map = new HashMap<>();
            map.put("act", "DockingGoodsList");
            map.put("url", source.getUrl());
            map.put("id", source.getDockingAccount());
            String sign = SignUtil.sign(map, source.getDockingKey());
            body = HttpRequest.post(source.getSourceWebsite() + "/api.php")
                    .form("sign", sign)
                    .form("act", "DockingGoodsList")
                    .form("url", source.getUrl())
                    .form("id", source.getDockingAccount())
                    .execute()
                    .body();
            JSONObject jsonObject = JSONUtil.parseObj(body);
            if (!jsonObject.getStr("code").equals("1")) {
                throw new ServiceException("货源网络异常，稍后重试");
            }
            List<CommodityParam> list = new ArrayList<>();
            for (Object o : jsonObject.getJSONArray("data")) {
                JSONObject os = (JSONObject) o;
                CommodityParam commodityParam = new CommodityParam();
                commodityParam.setCid(os.getStr("cid"));
                commodityParam.setId(os.getStr("gid"));
                commodityParam.setName(os.getStr("name"));
                list.add(commodityParam);
            }
            if (list != null && list.size() != 0) {
                // 创建线程池
                ExecutorService executorService = Executors.newFixedThreadPool(list.size());
                // 创建Future列表
                List<Future<?>> futures = new ArrayList<>();
                // 提交任务到线程池
                for (CommodityParam res : list) {
                    Future<?> future = executorService.submit(() -> {
                                try {
                                    JSONObject jsonObject1 = getProductDetails(source, res.getId());
                                    JSONObject data = jsonObject1.getJSONObject("data");
                                    res.setPrice(data.getStr("money"));
                                    JSONArray image = data.getJSONArray("image");
                                    if (image.size() > 0) {
                                        res.setImgUrl(image.getStr(0));
                                    }
                                } catch (Exception e) {
                                    // do noting
                                }
                            }
                    );
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
            redisUtil.set("CommodityList:" + source.getId(), JSONUtil.toJsonStr(list), 60 * 30);
            return list;
        } catch (HttpException e) {
            e.printStackTrace();
            throw new ServiceException("货源网络异常，稍后重试");
        }
    }

    public JSONObject getProductDetails(SourceEntity source, String id) {
        String body;
        try {
            if (source.getUrl() == null) {
                source.setUrl("jbj");
            }
            Map<String, String> map = new HashMap<>();
            map.put("act", "DockingGoodsLog");
            map.put("url", source.getUrl());
            map.put("id", source.getDockingAccount());
            map.put("gid", id);
            String sign = SignUtil.sign(map, source.getDockingKey());
            body = HttpRequest.post(source.getSourceWebsite() + "/api.php")
                    .form("sign", sign)
                    .form("act", "DockingGoodsLog")
                    .form("url", source.getUrl())
                    .form("id", source.getDockingAccount())
                    .form("gid", id)
                    .execute()
                    .body();
        } catch (HttpException e) {
            e.printStackTrace();
            throw new ServiceException("货源网络异常，稍后重试");
        }
        return JSONUtil.parseObj(body);
    }

    public Boolean connectivity(SourceEntity entity) {
        String body;
        try {
            if (entity.getUrl() == null) {
                entity.setUrl("jbj");
            }
            Map<String, String> map = new HashMap<>();
            map.put("act", "DockingGoodsList");
            map.put("url", entity.getUrl());
            map.put("id", entity.getDockingAccount());
            String sign = SignUtil.sign(map, entity.getDockingKey());
            body = HttpRequest.post(entity.getSourceWebsite() + "/api.php")
                    .form("sign", sign)
                    .form("act", "DockingGoodsList")
                    .form("url", entity.getUrl())
                    .form("id", entity.getDockingAccount())
                    .execute()
                    .body();
            JSONObject jsonObject = JSONUtil.parseObj(body);
            if (jsonObject.getStr("code").equals("1")) {
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public String placeAnOrder(SourceEntity entity, PurchaseDTO purchase, ProductEntity product) {
        String body;
        try {
            if (entity.getUrl() == null) {
                entity.setUrl("jbj");
            }
            Map<String, String> map = new HashMap<>();
            map.put("act", "Docking_buy");
            map.put("url", purchase.getUrl());
            map.put("id", entity.getDockingAccount());
            map.put("num", String.valueOf(purchase.getNum()));
            map.put("gid", product.getProductId());
            map.put("type", "1");
            map.put("coupon", "-2");
            map.put("value1", purchase.getAttach().size() > 0 ? purchase.getAttach().get(0) : "无");
            for (int i = 0; i < purchase.getAttach().size(); i++) {
                if (StringUtils.hasLength(purchase.getAttach().get(i))) {
                    map.put("value" + (i + 2), purchase.getAttach().get(i));
                }
            }
            String sign = SignUtil.sign(map, entity.getDockingKey());
            HttpRequest httpRequest = HttpRequest.post(entity.getSourceWebsite() + "/api.php")
                    .form("sign", sign)
                    .form("act", "Docking_buy")
                    .form("url", purchase.getUrl())
                    .form("id", entity.getDockingAccount())
                    .form("num", purchase.getNum())
                    .form("gid", product.getProductId())
                    .form("type", "1")
                    .form("coupon", "-2")
                    .form("value1", purchase.getAttach().size() > 0 ? purchase.getAttach().get(0) : "无");
            for (int i = 0; i < purchase.getAttach().size(); i++) {
                if (StringUtils.hasLength(purchase.getAttach().get(i))) {
                    httpRequest.form("value" + (i + 2), purchase.getAttach().get(i));
                }
            }
            body = httpRequest.execute()
                    .body();
        } catch (Exception e) {
            throw new ServiceException("货源网络异常，稍后重试");
        }
        JSONObject jsonObject = null;
        try {
            jsonObject = JSONUtil.parseObj(body);
        } catch (Exception e) {
            throw new ServiceException("货源网络异常，稍后重试");
        }
        if (!jsonObject.getStr("code").equals("1")) {
            throw new ServiceException(jsonObject.getStr("msg"));
        }
        return JSONUtil.toJsonStr(jsonObject);
    }

    public List<CommodityParam> getAllFl(SourceEntity entity) {
        String body;
        try {
            if (entity.getUrl() == null) {
                entity.setUrl("jbj");
            }
            Map<String, String> map = new HashMap<>();
            map.put("act", "DockingClassList");
            map.put("url", entity.getUrl());
            map.put("id", entity.getDockingAccount());
            String sign = SignUtil.sign(map, entity.getDockingKey());
            body = HttpRequest.post(entity.getSourceWebsite() + "/api.php")
                    .form("sign", sign)
                    .form("act", "DockingClassList")
                    .form("url", entity.getUrl())
                    .form("id", entity.getDockingAccount())
                    .execute()
                    .body();
            JSONObject jsonObject = null;
            try {
                jsonObject = JSONUtil.parseObj(body);
            } catch (Exception e) {
                throw new ServiceException("货源网络异常，稍后重试");
            }
            if (!jsonObject.getStr("code").equals("1")) {
                throw new ServiceException(jsonObject.getStr("msg"));
            }
            List<CommodityParam> list = new ArrayList<>();
            for (Object o : jsonObject.getJSONArray("data")) {
                JSONObject os = (JSONObject) o;
                CommodityParam commodityParam = new CommodityParam();
                commodityParam.setId(os.getStr("cid"));
                commodityParam.setName(os.getStr("name"));
                commodityParam.setImgUrl(os.getStr("image"));
                list.add(commodityParam);
            }
            return list;
        } catch (Exception e) {
            throw new ServiceException("货源网络异常，稍后重试");
        }
    }
}
