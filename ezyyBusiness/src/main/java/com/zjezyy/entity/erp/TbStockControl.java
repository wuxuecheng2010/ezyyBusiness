package com.zjezyy.entity.erp;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class TbStockControl {
	private Integer iproductid;
	private BigDecimal numlow;
	private String vcmemo;
	private String dtcreationdate;
	private String vccreatedby;
	private String dtlastupdatedate;
	private String vclastupdatedby;
	private BigDecimal numopen ;
}
