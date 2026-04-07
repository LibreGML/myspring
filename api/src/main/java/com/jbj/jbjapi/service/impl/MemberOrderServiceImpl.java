package com.jbj.jbjapi.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jbj.jbjapi.config.ServiceException;
import com.jbj.jbjapi.domain.Dto.PurchaseDTO;
import com.jbj.jbjapi.domain.Vo.SalesVO;
import com.jbj.jbjapi.domain.param.OrderParam;
import com.jbj.jbjapi.domain.param.PurchaseParam;
import com.jbj.jbjapi.entity.*;
import com.jbj.jbjapi.mapper.MemberOrderMapper;
import com.jbj.jbjapi.mapper.OrderMapper;
import com.jbj.jbjapi.service.*;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author mhw
 * @since 2023-10-21
 */
@Service
public class MemberOrderServiceImpl extends ServiceImpl<MemberOrderMapper, MemberOrderEntity> implements MemberOrderService {

}
