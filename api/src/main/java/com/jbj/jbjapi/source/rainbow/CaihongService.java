package com.jbj.jbjapi.source.rainbow;

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
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CaihongService {
    @Resource
    private RedisUtil redisUtil;

    public Boolean connectivity(SourceEntity source) {
        String body;
        try {
            body = HttpRequest.post(source.getSourceWebsite() + "/api.php?act=goodslist").form("user", source.getDockingAccount()).form("pass", source.getDockingKey()).execute().body();
            JSONObject jsonObject = JSONUtil.parseObj(body);
            if (!jsonObject.getStr("code").equals("0")) {
                return false;
            }
            return true;
        } catch (HttpException e) {
            return false;
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
            body = HttpRequest.post(source.getSourceWebsite() + "/api.php?act=goodslist").form("user", source.getDockingAccount()).form("pass", source.getDockingKey()).execute().body();
            JSONObject jsonObject = JSONUtil.parseObj(body);
            if (!jsonObject.getStr("code").equals("0")) {
                throw new ServiceException("货源网络异常，稍后重试");
            }
            List<CommodityParam> list = new ArrayList<>();
            for (Object o : jsonObject.getJSONArray("data")) {
                JSONObject os = (JSONObject) o;
                CommodityParam commodityParam = new CommodityParam();
                commodityParam.setCid(os.getStr("cid"));
                commodityParam.setId(os.getStr("tid"));
                commodityParam.setName(os.getStr("name"));
                commodityParam.setPrice(os.getStr("price"));
                commodityParam.setImgUrl(os.getStr("shopimg"));
                list.add(commodityParam);
            }
            redisUtil.set("CommodityList:" + source.getId(), JSONUtil.toJsonStr(list), 60 * 30);
            return list;
        } catch (HttpException e) {
            throw new ServiceException("货源网络异常，稍后重试");
        }
    }

    public JSONObject getProductDetails(SourceEntity source, String key) {
        String body;
        try {
            body = HttpRequest.post(source.getSourceWebsite() + "/api.php?act=goodsdetails").form("tid", key).form("user", source.getDockingAccount()).form("pass", source.getDockingKey()).execute().body();
            JSONObject jsonObject = JSONUtil.parseObj(body);
            if (!jsonObject.getStr("code").equals("0")) {
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
            HttpRequest httpRequest = HttpRequest.post(source.getSourceWebsite() + "/api.php?act=pay").form("num", purchase.getNum()).form("tid", product.getProductId()).form("user", source.getDockingAccount()).form("pass", source.getDockingKey());
            for (int i = 0; i < purchase.getAttach().size(); i++) {
                if (StringUtils.hasLength(purchase.getAttach().get(i))) {
                    httpRequest.form("input" + (i + 1), purchase.getAttach().get(i));
                }
            }
            body = httpRequest.execute().body();
        } catch (HttpException e) {
            throw new ServiceException("货源网络异常，稍后重试");
        }
        JSONObject jsonObject = JSONUtil.parseObj(body);
        if (!jsonObject.getStr("code").equals("0")) {
            throw new ServiceException(jsonObject.getStr("message"));
        }
        return JSONUtil.toJsonStr(jsonObject);
    }

    public List<CommodityParam> getAllFl(SourceEntity source) {
        String body;
        try {
            body = HttpRequest.post(source.getSourceWebsite() + "/api.php?act=classlist").form("user", source.getDockingAccount()).form("pass", source.getDockingKey()).execute().body();
            JSONObject jsonObject = JSONUtil.parseObj(body);
            if (!jsonObject.getStr("code").equals("0")) {
                throw new ServiceException("货源网络异常，稍后重试");
            }
            List<CommodityParam> list = new ArrayList<>();
            for (Object o : jsonObject.getJSONArray("data")) {
                JSONObject os = (JSONObject) o;
                CommodityParam commodityParam = new CommodityParam();
                commodityParam.setId(os.getStr("cid"));
                commodityParam.setName(os.getStr("name"));
                commodityParam.setImgUrl(os.getStr("shopimg"));
                list.add(commodityParam);
            }
            return list;
        } catch (HttpException e) {
            throw new ServiceException("货源网络异常，稍后重试");
        }
    }
}
