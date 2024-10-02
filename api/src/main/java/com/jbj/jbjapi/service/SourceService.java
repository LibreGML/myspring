package com.jbj.jbjapi.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jbj.jbjapi.domain.Vo.CommodityVo;
import com.jbj.jbjapi.domain.Vo.SourceVo;
import com.jbj.jbjapi.domain.param.CommodityParam;
import com.jbj.jbjapi.entity.SourceEntity;
import com.jbj.jbjapi.entity.SysSourceEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 菜单与角色关联表 服务类
 * </p>
 *
 * @author mhw
 * @since 2023-10-21
 */
public interface SourceService extends IService<SourceEntity> {

    Page<SourceEntity> getList(SourceVo vo);

    Page<CommodityParam> commodity(CommodityVo vo,String id, HttpServletRequest request);

    Page<CommodityParam> commodityDel(CommodityVo vo,String id, HttpServletRequest request);

    Boolean apply(String key, String ly,String sourceId, HttpServletRequest request);

    void setBalance(SourceEntity source);

    Page<CommodityParam> getBeforeFl(CommodityVo vo, HttpServletRequest request);

    void getBeforeFlInt(Map<String, Object> vo, String sourceId, HttpServletRequest request);

    void getBeforeFlIntAll(Map<String, Object> vo, HttpServletRequest request);

    Page<CommodityParam> getAllProduct(CommodityVo vo, HttpServletRequest request);

    void reshProList(HttpServletRequest request);
}
