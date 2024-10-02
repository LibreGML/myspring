package com.jbj.jbjapi.source.kaka;

import cn.hutool.http.HttpException;
import cn.hutool.http.HttpRequest;
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

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class KakaService {
    @Resource
    private RedisUtil redisUtil;


    public List<CommodityParam> getCommodity(SourceEntity entity, Boolean isCa) {
        if (isCa) {
            Object commodityList = redisUtil.get("CommodityList:" + entity.getId());
            if (commodityList != null) {
                return JSONUtil.toList(String.valueOf(commodityList), CommodityParam.class);
            }
        }
        List<CommodityParam> list = getAll(entity);
        redisUtil.set("CommodityList:" + entity.getId(), JSONUtil.toJsonStr(list), 60 * 30);
        return list;
    }

    private List<CommodityParam> getAll(SourceEntity entity) {
        Boolean jx = true;
        List<CommodityParam> list = new ArrayList<>();
        for (int i = 1; jx; i++) {
            String body;
            try {
                Map<String, String> map = new HashMap<>();
                map.put("userid", entity.getDockingAccount());
                map.put("limit", "100");
                map.put("page", String.valueOf(i));
                String sign = SignUtil.sign(map, entity.getDockingKey());
                body = HttpRequest.post(entity.getSourceWebsite() + "/dockapi/v2/getallgoods.html")
                        .form("sign", sign)
                        .form("limit", "100")
                        .form("page", String.valueOf(i))
                        .form("userid", entity.getDockingAccount())
                        .execute().body();
                JSONObject jsonObject = JSONUtil.parseObj(body);
                if (!jsonObject.getStr("code").equals("1")) {
                    throw new ServiceException("货源网络异常，稍后重试");
                }
                Thread.sleep(1000); // 休眠1秒
                if (jsonObject.getJSONArray("data").size() == 0) {
                    jx = false;
                }
                for (Object o : jsonObject.getJSONArray("data")) {
                    JSONObject os = (JSONObject) o;
                    CommodityParam commodityParam = new CommodityParam();
                    commodityParam.setCid(os.getStr("goodsgroupid"));
                    commodityParam.setId(os.getStr("goodsid"));
                    commodityParam.setName(os.getStr("goodsname"));
                    commodityParam.setPrice(os.getStr("goodsprice"));
                    commodityParam.setImgUrl(os.getStr("imgurl"));
                    list.add(commodityParam);
                }
            } catch (Exception e) {
                throw new ServiceException("货源网络异常，稍后重试");
            }
        }
        return list;
    }

    public Boolean connectivity(SourceEntity entity) {
        String body;
        try {
            Map<String, String> map = new HashMap<>();
            map.put("userid", entity.getDockingAccount());
            String sign = SignUtil.sign(map, entity.getDockingKey());
            body = HttpRequest.post(entity.getSourceWebsite() + "/dockapi/v2/getallgoods.html")
                    .form("sign", sign)
                    .form("userid", entity.getDockingAccount())
                    .execute().body();
            JSONObject jsonObject = JSONUtil.parseObj(body);
            if (!jsonObject.getStr("code").equals("1")) {
                return false;
            }
            return true;
        } catch (HttpException e) {
            return false;
        }
    }

    public JSONObject getProductDetails(SourceEntity source, String key) {
        String body;
        try {
            Map<String, String> map = new HashMap<>();
            map.put("userid", source.getDockingAccount());
            map.put("goodsid", key);
            String sign = SignUtil.sign(map, source.getDockingKey());
            body = HttpRequest.post(source.getSourceWebsite() + "/dockapi/v2/goodsdetails.html")
                    .form("sign", sign)
                    .form("userid", source.getDockingAccount())
                    .form("goodsid", key)
                    .execute().body();
            JSONObject jsonObject = JSONUtil.parseObj(body);
            if (!jsonObject.getStr("code").equals("1")) {
                throw new ServiceException("货源网络异常，稍后重试");
            }
            return jsonObject;
        } catch (HttpException e) {
            throw new ServiceException("货源网络异常，稍后重试");
        }
    }

    public JSONObject getUserInfo(SourceEntity res) {
        return null;
    }

    public String placeAnOrder(SourceEntity source, PurchaseDTO purchase, ProductEntity product) {
        String body;
        try {
            Map<String, String> map = new HashMap<>();
            map.put("userid", source.getDockingAccount());
            map.put("goodsid", product.getProductId());
            map.put("buynum", String.valueOf(purchase.getNum()));
            map.put("attach", JSONUtil.toJsonStr(purchase.getAttach()));
            String sign = SignUtil.sign(map, source.getDockingKey());
            body = HttpRequest.post(source.getSourceWebsite() + "/dockapi/index/buy")
                    .form("sign", sign)
                    .form("userid", source.getDockingAccount())
                    .form("buynum", purchase.getNum())
                    .form("goodsid", product.getProductId())
                    .form("attach", JSONUtil.toJsonStr(purchase.getAttach()))
                    .execute().body();
        } catch (HttpException e) {
            throw new ServiceException("货源网络异常，稍后重试");
        }
        JSONObject jsonObject = JSONUtil.parseObj(body);
        if (!jsonObject.getStr("code").equals("1")) {
            throw new ServiceException(jsonObject.getStr("message"));
        }
        return JSONUtil.toJsonStr(jsonObject);
    }

    public List<CommodityParam> getAllFl(SourceEntity source) {
        String body;
        try {
            Map<String, String> map = new HashMap<>();
            map.put("userid", source.getDockingAccount());
            String sign = SignUtil.sign(map, source.getDockingKey());
            body = HttpRequest.post(source.getSourceWebsite() + "/dockapi/v2/getallgoodsgroup")
                    .form("sign", sign)
                    .form("userid", source.getDockingAccount())
                    .execute().body();
            JSONObject jsonObject = JSONUtil.parseObj(body);
            if (!jsonObject.getStr("code").equals("1")) {
                throw new ServiceException("货源网络异常，稍后重试");
            }
            List<CommodityParam> list = new ArrayList<>();
            for (Object o : jsonObject.getJSONArray("data")) {
                JSONObject os = (JSONObject) o;
                CommodityParam commodityParam = new CommodityParam();
                commodityParam.setId(os.getStr("groupid"));
                commodityParam.setName(os.getStr("groupname"));
                commodityParam.setImgUrl(os.getStr("groupimgurl"));
                list.add(commodityParam);
            }
            return list;
        } catch (HttpException e) {
            throw new ServiceException("货源网络异常，稍后重试");
        }
    }
}
