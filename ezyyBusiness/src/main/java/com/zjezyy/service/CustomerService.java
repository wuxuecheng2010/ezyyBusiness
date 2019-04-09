package com.zjezyy.service;

import com.zjezyy.entity.erp.TbCustomer;

public interface CustomerService {
     TbCustomer getTbCustomerByMccCustomerId(int customer_id) throws RuntimeException ;
}
