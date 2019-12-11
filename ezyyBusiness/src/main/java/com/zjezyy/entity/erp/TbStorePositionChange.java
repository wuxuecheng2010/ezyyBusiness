package com.zjezyy.entity.erp;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class TbStorePositionChange {
	private Integer ibillid;
	private String vcbillcode;
	private String vcmemo;
	private String dtcreationdate;
	private String vccreatedby;
	private BigDecimal nummoney;
}
