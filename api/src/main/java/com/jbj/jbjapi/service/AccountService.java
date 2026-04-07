package com.jbj.jbjapi.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jbj.jbjapi.domain.Dto.UserDTO;
import com.jbj.jbjapi.domain.Vo.AccountVo;
import com.jbj.jbjapi.domain.Vo.LoginVO;
import com.jbj.jbjapi.domain.Vo.UpdatePasswordVO;
import com.jbj.jbjapi.domain.param.AccountParam;
import com.jbj.jbjapi.entity.AccountEntity;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author mhw
 * @since 2023-10-21
 */
public interface AccountService extends IService<AccountEntity> {

    LoginVO login(UserDTO account);

    LoginVO register(UserDTO account);

    Object authPermList();

    Page<AccountEntity> accountList(AccountVo accountVo);

    Boolean updatePassword(UpdatePasswordVO updatePasswordVO);

    Boolean updateByPrimaryKey(AccountEntity accountEntity);

    AccountParam getDetail(String key);
}
