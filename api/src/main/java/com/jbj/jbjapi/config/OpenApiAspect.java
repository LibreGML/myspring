package com.jbj.jbjapi.config;

import com.jbj.jbjapi.entity.AccountEntity;
import com.jbj.jbjapi.entity.BusinessEntity;
import com.jbj.jbjapi.service.AccountService;
import com.jbj.jbjapi.service.BusinessService;
import com.jbj.jbjapi.utils.SignUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Map;

@Aspect
@Component
public class OpenApiAspect {

    @Resource
    private AccountService accountService;

    @Resource
    private BusinessService businessService;

    @Pointcut("execution(public * com.jbj.jbjapi.controller.openApi.OpenApiController.*(..))")
    public void openApi() {
    }

    @Before("openApi()")
    public void doBefore(JoinPoint joinPoint) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String userId = request.getHeader("userId");
        String sign = request.getHeader("sign");
        if (StringUtils.isEmpty(userId) || StringUtils.isEmpty(sign)) {
            throw new ServiceException("缺失请求参数");
        }
        AccountEntity account = accountService.getById(userId);
        if (account == null || !account.getRoleType().equals("2")) {
            throw new ServiceException("商户不存在");
        }
        BusinessEntity business = businessService.getById(account.getRoleId());
        if (business == null) {
            throw new ServiceException("商户不存在");
        }
        Map<String, String> map;
        try {
            map = SignUtil.decryption(sign, business.getAbutmentKey());
        } catch (Exception e) {
            throw new ServiceException("解密异常");
        }
        String time = map.get("timestamp");
        if (StringUtils.isEmpty(time)) {
            throw new ServiceException("请求过期");
        }
        long currentTime = System.currentTimeMillis();
        try {
            map = SignUtil.decryption(sign, business.getAbutmentKey());
        } catch (Exception e) {
            throw new ServiceException("解密异常");
        }
        long specifiedTime = Long.parseLong(time);
        try {
            Instant instant = Instant.ofEpochSecond(specifiedTime);
            // 将 Instant 对象转换为 LocalDateTime 对象
            LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        } catch (Exception e) {
            throw new ServiceException("时间异常");
        }
        long difference = currentTime - specifiedTime;
        if (difference > 60 * 1000) {
//            throw new ServiceException("请求超时");
        }
        request.setAttribute("business", business);
    }

    @After("openApi()")
    public void doAfter(JoinPoint joinPoint) {
        // 打印日志
        System.out.println("请求结束");
    }
}