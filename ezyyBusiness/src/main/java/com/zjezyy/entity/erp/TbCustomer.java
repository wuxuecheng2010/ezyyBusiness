package com.zjezyy.entity.erp;

import lombok.Data;

@Data
public class TbCustomer {
	private Integer icustomerid;
	private String vccustomername;
	private Integer isalerid;
	private String vcregisteredaddress;// 注册地址
	private String vcaddress;// 客户地址
	private String vcstoreaddress;// 送货地址
}
