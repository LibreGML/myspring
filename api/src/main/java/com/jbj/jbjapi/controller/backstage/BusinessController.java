package com.jbj.jbjapi.controller.backstage;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jbj.jbjapi.annotation.OperateLog;
import com.jbj.jbjapi.config.LoginHelper;
import com.jbj.jbjapi.config.ServiceException;
import com.jbj.jbjapi.domain.Dto.UserDTO;
import com.jbj.jbjapi.domain.R;
import com.jbj.jbjapi.domain.Vo.BusinessVo;
import com.jbj.jbjapi.domain.param.YhOrderParam;
import com.jbj.jbjapi.entity.*;
import com.jbj.jbjapi.service.*;
import com.jbj.jbjapi.utils.RedisUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.UUID;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author mhw
 * @since 2023-10-21
 */
@Api(value = "商家", tags = "商家")
@RestController
@RequestMapping("/business")
public class BusinessController {
    @Value("${default.password}")
    private String defaultPassword;

    @Resource
    private BusinessService businessService;

    @Resource
    private AccountService accountService;

    @Resource
    private MemberService memberService;

    @Resource
    private PaymentService paymentService;

    @Resource
    private MemberOrderService memberOrderService;

    @Resource
    private FundService fundService;

    @Resource
    private RedisUtil redisUtil;


    @Value("${host}")
    private String host;

    /**
     * 根据主键查询
     */
    @ApiOperation(value = "根据主键查询")
    @GetMapping("/detail")
    public R<BusinessEntity> selectByPrimaryKey(@RequestParam String key) {
        BusinessEntity business = businessService.getById(key);
        AccountEntity account = accountService.getOne(new LambdaQueryWrapper<AccountEntity>()
                .eq(AccountEntity::getRoleType, 2).eq(AccountEntity::getRoleId, business.getId()).last("LIMIT 1"));
        business.setAccountId(account.getId());
        business.setAccount(account.getAccount());
        return R.ok(business);
    }

    @ApiOperation(value = "列表")
    @PostMapping("/list")
    public R<Page<BusinessEntity>> list(@RequestBody BusinessVo vo) {
        return R.ok(businessService.businessList(vo));
    }

    @ApiOperation(value = "新增")
    @OperateLog(tip = "商家数据新增")
    @PostMapping("/add")
    public R<Boolean> add(@RequestBody BusinessEntity entity) {
        AccountEntity userEntity = accountService.getOne(new LambdaQueryWrapper<AccountEntity>().eq(AccountEntity::getAccount, entity.getAccount()));
        if (!Objects.isNull(userEntity)) {
            throw new ServiceException("账户名称已被注册");
        }
        entity.setAbutmentKey(UUID.randomUUID().toString());
        businessService.save(entity);
        UserDTO userDTO = new UserDTO();
        userDTO.setUserAccount(entity.getAccount());
        userDTO.setUserPassword(defaultPassword);
        userDTO.setRoleType("2");
        userDTO.setHeadPic(entity.getHeadPic());
        userDTO.setRoleId(entity.getId());
        accountService.register(userDTO);
        return R.ok();
    }

    @ApiOperation(value = "更新")
    @PostMapping("/update")
    @OperateLog(tip = "商家数据更新")
    public R<Boolean> updateByPrimaryKey(@RequestBody BusinessEntity entity) {
        return R.ok(businessService.updateById(entity));
    }

    @ApiOperation(value = "购买")
    @OperateLog(tip = "商家会员购买")
    @PostMapping("/buy")
    public R<Object> buy(@RequestParam String id, @RequestParam String type, HttpServletRequest request) {
        AccountEntity account = LoginHelper.getLoginUser();
        BusinessEntity business = businessService.getById(account.getRoleId());
        MemberEntity member = memberService.getById(id);
        if (member == null) {
            throw new ServiceException("购买包不存在");
        }
        MemberOrderEntity memberOrderEntity = new MemberOrderEntity();
        String orderNumber = getOrderNumber();
        memberOrderEntity.setOrderNumber(orderNumber);
        memberOrderEntity.setOrderTime(LocalDateTime.now());
        memberOrderEntity.setMemberId(member.getId());
        memberOrderEntity.setStatus("1");
        if (member.getType().equals("2")) {
            member.setName("额度包购买");
        }
        memberOrderEntity.setMemberName(member.getName());
        memberOrderEntity.setPaymentMethod(type);
        memberOrderEntity.setPaymentAmount(new BigDecimal(member.getPrice()));
        memberOrderEntity.setMerchantId(business.getId());
        try {
            YhOrderParam param = new YhOrderParam();
            param.setType(type);
            param.setName(member.getName());
            param.setMoney(member.getPrice());
            param.setSitename("聚比价");
            param.setOut_trade_no(orderNumber);
            String url = request.getRemoteHost();
            param.setNotify_url(host + "/api/business/return_url");
            param.setReturn_url(host + "/web");
            memberOrderService.save(memberOrderEntity);
            return R.ok((Object) paymentService.sendPostRequest(param));
        } catch (IOException e) {
            throw new ServiceException("发起支付异常");
        }
    }

    @ApiOperation(value = "商家会员购买支付回调")
    @GetMapping("/return_url")
    public void returnUrl(
            @RequestParam String pid,
            @RequestParam String trade_no,
            @RequestParam String out_trade_no,
            @RequestParam String type,
            @RequestParam String name,
            @RequestParam String money,
            @RequestParam String trade_status,
            @RequestParam String sign) {
        Object ob = redisUtil.get("TRADE_SUCCESS:" + out_trade_no);
        if (Objects.isNull(ob)) {
            redisUtil.set("TRADE_SUCCESS:" + out_trade_no, "lock", 3);
            MemberOrderEntity memberOrder = memberOrderService.getOne(new LambdaQueryWrapper<MemberOrderEntity>()
                    .eq(MemberOrderEntity::getOrderNumber, out_trade_no).last("LIMIT 1"));
            if (StringUtils.hasLength(trade_status) && trade_status.equals("TRADE_SUCCESS")) {
                if (paymentService.isVief(pid, trade_no, out_trade_no, type, name, money, trade_status, sign)) {
                    if (memberOrder.getStatus().equals("1")) {
                        memberOrder.setStatus("2");
                        memberOrderService.updateById(memberOrder);
                        MemberEntity member = memberService.getById(memberOrder.getMemberId());
                        BusinessEntity business = businessService.getById(memberOrder.getMerchantId());
                        FundEntity fundEntity2 = new FundEntity();
                        fundEntity2.setRoleId(business.getId());
                        fundEntity2.setRoleType("2");
                        fundEntity2.setAmount(new BigDecimal(money));
                        if (member.getType().equals("1")) {
                            fundEntity2.setType("购买会员");
                            fundEntity2.setInfo("购买会员【" + member.getName() + "】");
                            business.setExpirationTime(business.getExpirationTime().plusDays(Long.parseLong(member.getDuration())));
                        } else if (member.getType().equals("2")) {
                            fundEntity2.setType("购买额度包");
                            fundEntity2.setInfo("购买额度包【" + money + "】");
                        } else {
                            business.setWallet(business.getWallet().add(new BigDecimal(money)));
                            fundEntity2.setType("购买额度包");
                            fundEntity2.setInfo("购买额度包【" + money + "】");
                        }
                        fundEntity2.setBalance(business.getWallet());
                        fundService.save(fundEntity2);
                        business.setQuota(business.getQuota().add(new BigDecimal(member.getLimitb())));
                        businessService.updateById(business);
                    }
                }
            }
        }
    }

    @ApiOperation(value = "购买")
    @OperateLog(tip = "商家会员购买")
    @PostMapping("/buy/yue")
    public R<Object> buyYue(@RequestParam String Price, @RequestParam String type, HttpServletRequest request) {
        AccountEntity account = LoginHelper.getLoginUser();
        BusinessEntity business = businessService.getById(account.getRoleId());
        MemberOrderEntity memberOrderEntity = new MemberOrderEntity();
        String orderNumber = getOrderNumber();
        memberOrderEntity.setOrderNumber(orderNumber);
        memberOrderEntity.setOrderTime(LocalDateTime.now());
        memberOrderEntity.setMemberName("余额充值");
        memberOrderEntity.setPaymentMethod(type);
        memberOrderEntity.setPaymentAmount(new BigDecimal(Price));
        memberOrderEntity.setMerchantId(business.getId());
        memberOrderEntity.setStatus("1");
        try {
            YhOrderParam param = new YhOrderParam();
            param.setType(type);
            param.setName("余额充值");
            param.setMoney(Price);
            param.setSitename("聚比价");
            param.setOut_trade_no(orderNumber);
            param.setNotify_url(host + "/api/business/yue/return_url");
            param.setReturn_url(host + "/web");
            memberOrderService.save(memberOrderEntity);
            return R.ok((Object) paymentService.sendPostRequest(param));
        } catch (IOException e) {
            throw new ServiceException("发起支付异常");
        }
    }

    @ApiOperation(value = "商家会员购买支付回调")
    @GetMapping("/yue/return_url")
    public void yueReturnUrl(
            @RequestParam String pid,
            @RequestParam String trade_no,
            @RequestParam String out_trade_no,
            @RequestParam String type,
            @RequestParam String name,
            @RequestParam String money,
            @RequestParam String trade_status,
            @RequestParam String sign) {
        Object ob = redisUtil.get("TRADE_SUCCESS:" + out_trade_no);
        if (Objects.isNull(ob)) {
            redisUtil.set("TRADE_SUCCESS:" + out_trade_no, "lock", 3);
            MemberOrderEntity memberOrder = memberOrderService.getOne(new LambdaQueryWrapper<MemberOrderEntity>()
                    .eq(MemberOrderEntity::getOrderNumber, out_trade_no).last("LIMIT 1"));
            if (StringUtils.hasLength(trade_status) && trade_status.equals("TRADE_SUCCESS")) {
                if (paymentService.isVief(pid, trade_no, out_trade_no, type, name, money, trade_status, sign)) {
                    if (memberOrder.getStatus().equals("1")) {
                        memberOrder.setStatus("2");
                        memberOrderService.updateById(memberOrder);

                        BusinessEntity business = businessService.getById(memberOrder.getMerchantId());
                        FundEntity fundEntity2 = new FundEntity();
                        fundEntity2.setRoleId(business.getId());
                        fundEntity2.setRoleType("2");
                        fundEntity2.setAmount(new BigDecimal(money));
                        business.setWallet(business.getWallet().add(new BigDecimal(money)));
                        fundEntity2.setBalance(business.getWallet());
                        fundEntity2.setType("余额购买");
                        fundEntity2.setInfo("余额购买【" + money + "】");
                        fundService.save(fundEntity2);
                        businessService.updateById(business);
                    }
                }
            }
        }
    }

    private String getOrderNumber() {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String formattedDate = currentDate.format(formatter);
        MemberOrderEntity orderEntity = memberOrderService.getOne(new LambdaQueryWrapper<MemberOrderEntity>()
                .select(MemberOrderEntity::getOrderNumber)
                .likeRight(MemberOrderEntity::getOrderNumber, formattedDate)
                .orderByDesc(MemberOrderEntity::getOrderNumber)
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

    @ApiOperation(value = "更新密匙")
    @PostMapping("/update/key")
    @OperateLog(tip = "商家密匙更新")
    public R<Boolean> updateKey() {
        AccountEntity account = LoginHelper.getLoginUser();
        BusinessEntity business = businessService.getById(account.getRoleId());
        business.setAbutmentKey(UUID.randomUUID().toString());
        return R.ok(businessService.updateById(business));
    }

    @ApiOperation(value = "删除")
    @OperateLog(tip = "商家数据删除")
    @PostMapping("/del")
    public R<Boolean> del(@RequestParam String key) {
        accountService.remove(new LambdaQueryWrapper<AccountEntity>()
                .eq(AccountEntity::getRoleType, 2).eq(AccountEntity::getRoleId, key));
        return R.ok(businessService.removeById(key));
    }
}
