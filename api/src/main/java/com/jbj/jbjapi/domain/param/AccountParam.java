package com.jbj.jbjapi.domain.param;

import com.jbj.jbjapi.entity.AccountEntity;
import com.jbj.jbjapi.entity.BusinessEntity;
import com.jbj.jbjapi.entity.SupplierEntity;
import lombok.Data;

@Data
public class AccountParam {
   private AccountEntity account;

   private SupplierEntity supplier;

   private BusinessEntity business;
}
