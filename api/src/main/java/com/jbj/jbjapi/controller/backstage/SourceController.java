package com.jbj.jbjapi.controller.backstage;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jbj.jbjapi.annotation.OperateLog;
import com.jbj.jbjapi.config.LoginHelper;
import com.jbj.jbjapi.config.ServiceException;
import com.jbj.jbjapi.domain.R;
import com.jbj.jbjapi.domain.Vo.CommodityVo;
import com.jbj.jbjapi.domain.Vo.SourceVo;
import com.jbj.jbjapi.domain.param.CommodityParam;
import com.jbj.jbjapi.entity.*;
import com.jbj.jbjapi.service.SourceService;
import com.jbj.jbjapi.service.SupplierService;
import com.jbj.jbjapi.service.SysSourceService;
import com.jbj.jbjapi.source.kaka.KakaService;
import com.jbj.jbjapi.source.rainbow.CaihongService;
import com.jbj.jbjapi.source.storage.StorageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * <p>
 * 货源系统 前端控制器
 * </p>
 *
 * @author mhw
 * @since 2023-10-21
 */
@Api(value = "货源", tags = "货源")
@RestController
@RequestMapping("/source")
public class SourceController {
    @Resource
    private SourceService sourceService;

    @Resource
    private StorageService storageService;

    @Resource
    private CaihongService caihongService;

    @Resource
    private KakaService kakaService;

    @Resource
    private SupplierService supplierService;

    @ApiOperation(value = "列表")
    @PostMapping("/list")
    public R<Page<SourceEntity>> list(@RequestBody SourceVo vo) {
        return R.ok(sourceService.getList(vo));
    }

    @ApiOperation(value = "根据主键查询")
    @GetMapping("/detail")
    public R<SourceEntity> selectByPrimaryKey(@RequestParam String key) {
        SourceEntity source = sourceService.getById(key);
        sourceService.setBalance(source);
        return R.ok(source);
    }

    @ApiOperation(value = "货源商品预售分类")
    @PostMapping("/getBeforeFl")
    public R<Page<CommodityParam>> getBeforeFl(@RequestBody CommodityVo vo, HttpServletRequest request) {
        return R.ok(sourceService.getBeforeFl(vo, request));
    }

    @ApiOperation(value = "分类下的货源")
    @GetMapping("/getBeforeFl/prd")
    public R<Page<CommodityParam>> getBeforeFl(@RequestParam String id, @RequestParam String sourceId, HttpServletRequest request) {
        CommodityVo vo = new CommodityVo();
        vo.setSourceId(sourceId);
        vo.setPageNum(1);
        vo.setPageSize(Integer.MAX_VALUE);
        return R.ok(sourceService.commodity(vo, id, request));
    }

    @ApiOperation(value = "提交在售")
    @PostMapping("/getBeforeFl/int")
    public R<Boolean> getBeforeFlInt(@RequestBody Map<String, Object> vo, @RequestParam String sourceId, HttpServletRequest request) {
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> sourceService.getBeforeFlInt(vo, sourceId, request));
        return R.ok(true);
    }

    @ApiOperation(value = "提交在售(总)")
    @PostMapping("/getBeforeFl/intAll")
    public R<Boolean> getBeforeFlIntAll(@RequestBody Map<String, Object> vo, HttpServletRequest request) {
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> sourceService.getBeforeFlIntAll(vo, request));
        return R.ok(true);
    }

    @ApiOperation(value = "刷新商品")
    @GetMapping("/reshProList")
    public R<Boolean> reshProList(HttpServletRequest request) {
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> sourceService.reshProList(request));
        return R.ok(true);
    }

    @ApiOperation(value = "获取货源商品")
    @PostMapping("/getAllProduct")
    public R<Page<CommodityParam>> getAllProduct(@RequestBody CommodityVo vo, HttpServletRequest request) {
        return R.ok(sourceService.getAllProduct(vo, request));
    }

    @ApiOperation(value = "货源商品")
    @PostMapping("/commodity")
    public R<Page<CommodityParam>> commodity(@RequestBody CommodityVo vo, HttpServletRequest request) {
        return R.ok(sourceService.commodityDel(vo, null, request));
    }


    @ApiOperation(value = "更新")
    @OperateLog(tip = "货源数据更新")
    @PostMapping("/update")
    public R<Boolean> updateByPrimaryKey(@RequestBody SourceEntity entity) {
        AccountEntity account = LoginHelper.getLoginUser();
        if (!account.getRoleType().equals("1")) {
            throw new ServiceException("无权限");
        }
        return R.ok(sourceService.updateById(entity));
    }

    @ApiOperation(value = "新增货源")
    @OperateLog(tip = "货源数据新增")
    @PostMapping("/save")
    public R<Boolean> save(@RequestBody SourceEntity entity, HttpServletRequest request) {
        AccountEntity account = LoginHelper.getLoginUser();
        if (!account.getRoleType().equals("3")) {
            throw new ServiceException("无权限");
        }
        SupplierEntity supplier = supplierService.getById(account.getRoleId());
        String url = request.getRemoteHost();
        entity.setUrl(url);
        boolean connectivityCheck = false;
        switch (entity.getSourceSystem()) {
            case "1":
                connectivityCheck = storageService.connectivity(entity);
                break;
            case "2":
                connectivityCheck = caihongService.connectivity(entity);
                break;
            case "3":
                connectivityCheck = kakaService.connectivity(entity);
                break;
            default:
                throw new ServiceException("货源不存在");
        }
        if (!connectivityCheck) {
            throw new ServiceException("连通性检查失败，请检查参数");
        }
        entity.setConnectivity("1");
        entity.setSupplierId(supplier.getId());
        sourceService.save(entity);
        return R.ok(true);
    }

    @ApiOperation(value = "审核")
    @OperateLog(tip = "货源数据审核")
    @PostMapping("/examine")
    public R<Boolean> examine(@RequestBody SourceEntity entity) {
        SourceEntity source = new SourceEntity();
        source.setId(entity.getId());
        source.setExamine(entity.getExamine());
        source.setExamineReason(entity.getExamineReason());
        return R.ok(sourceService.updateById(source));
    }

    @ApiOperation(value = "插队申请")
    @OperateLog(tip = "货源数据插队申请")
    @GetMapping("/apply")
    public R<Boolean> apply(@RequestParam String key, @RequestParam String ly, @RequestParam String sourceId, HttpServletRequest request) {
        return R.ok(sourceService.apply(key, ly, sourceId, request));
    }

}
