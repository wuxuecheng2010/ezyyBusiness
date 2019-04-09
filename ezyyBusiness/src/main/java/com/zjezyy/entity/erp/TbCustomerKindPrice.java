package com.zjezyy.entity.erp;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class TbCustomerKindPrice {
	private Integer isid;
	private Integer icustomerkindid;
	private Integer iproductid;
	private char flagrate;
	private BigDecimal numprice;
	private BigDecimal numlowprice;
	private String dtcreationdate;
	private String vccreatedby;
	private String dtlastupdatedate;
	private String vclastupdatedby;
	private BigDecimal numassesscost;
	private BigDecimal numguidprice;
}
