package com.jbj.jbjapi.config;

import cn.hutool.json.JSONUtil;
import com.jbj.jbjapi.annotation.OperateLog;
import com.jbj.jbjapi.domain.R;
import com.jbj.jbjapi.entity.AccountEntity;
import com.jbj.jbjapi.entity.BusinessEntity;
import com.jbj.jbjapi.entity.SysOperateLogEntity;
import com.jbj.jbjapi.service.SysOperateLogService;
import com.jbj.jbjapi.utils.JsonUtil;
import org.apache.catalina.connector.RequestFacade;
import org.apache.ibatis.annotations.Param;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Arrays;

@Aspect
@Component
public class LogAspect {

    @Resource
    private SysOperateLogService sysOperateLogService;

    @Around("@annotation(com.jbj.jbjapi.annotation.OperateLog)")
    public Object addLog(ProceedingJoinPoint joinPoint) throws Throwable {
        // 获取方法参数
        Object[] args = joinPoint.getArgs();
        // 执行原方法
        Object returnJson = joinPoint.proceed();
        try {
            // 将日志信息保存到数据库中
            SysOperateLogEntity logEntity = new SysOperateLogEntity();
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = signature.getMethod();
            OperateLog operateLog = method.getAnnotation(OperateLog.class);
            try {
                AccountEntity account = LoginHelper.getLoginUser();
                logEntity.setRoleId(account.getRoleId());
                logEntity.setRoleType(account.getRoleType());
            } catch (Exception e) {
                try {
                    if (operateLog.tip().equals("购买商品")) {
                        RequestFacade requestFacade = (RequestFacade) args[1];
                        BusinessEntity business = (BusinessEntity) requestFacade.getAttribute("business");
                        logEntity.setRoleId(business.getId());
                        logEntity.setRoleType("2");
                    }
                } catch (Exception ee) {
                    // do noting
                }
            }
            logEntity.setType(operateLog.tip());
            if (args.length > 0) {
                Object object = args[0];
                logEntity.setAcceptParam(JsonUtil.replacePropertyValues(object));
            }

            if (returnJson instanceof R) {
                R json = (R) returnJson;
                logEntity.setResponseParam(json.getMsg());
            }
            sysOperateLogService.save(logEntity);
            return returnJson;
        }catch (Exception e){
            // do noting
        }
        return returnJson;
    }

}