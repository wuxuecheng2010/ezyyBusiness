package com.zjezyy.service;

import com.zjezyy.entity.Result;
import com.zjezyy.entity.erp.TbCustomer;

public interface CustomerService {
     TbCustomer getTbCustomerByMccCustomerId(int customer_id) throws RuntimeException ;
     
     Result makeTbCustomerToMccCustomer(int icustomerid,int flag_credit_user)throws RuntimeException ;
     
     void makeTbCustomerKindToMccTbCustomerKindByICustomerid(TbCustomer tbCustomer)throws RuntimeException ;
}
