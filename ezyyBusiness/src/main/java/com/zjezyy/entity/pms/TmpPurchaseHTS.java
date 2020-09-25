package com.zjezyy.entity.pms;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class TmpPurchaseHTS {
	private Integer isid; 
	private String vcbillno; 
	private String vcmemo; 
	private String creationdate; 
	private String createdby; 
	private String lastupdatedate; 
	private String lastupdatedby; 
	private Integer iproductid; 
	private BigDecimal numnumber; 
	private BigDecimal numprice; 
	private BigDecimal nummoney; 
	private BigDecimal numnumberdh; 
	private String isourceid; 
	private BigDecimal numlow; 
	private BigDecimal numhigh; 
	private BigDecimal numstock; 
	private BigDecimal sumsales; 
	private Integer erpproductid;
}
