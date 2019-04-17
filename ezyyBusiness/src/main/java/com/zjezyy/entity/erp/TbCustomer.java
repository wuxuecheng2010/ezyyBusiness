package com.zjezyy.entity.erp;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class TbCustomer {
	private Integer icustomerid;
	private String vccustomername;
	private Integer isalerid;
	private String vcregisteredaddress;// 注册地址
	private String vcaddress;// 客户地址
	private String vcstoreaddress;// 送货地址
	
	private char flaglock;//是否锁定
	
	private Integer numcreditdays;
	private Integer numcreditdays1;
	private Integer numcreditdays2;
	private Integer numcreditdays3;
	
	private BigDecimal numcreditmoney;
	private String vccustomercode;
	private Integer icredittype;// ICREDITTYPE
	
	
}
