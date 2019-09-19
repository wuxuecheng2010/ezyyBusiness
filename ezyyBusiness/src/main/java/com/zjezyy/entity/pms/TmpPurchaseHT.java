package com.zjezyy.entity.pms;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class TmpPurchaseHT {
	private Integer isid;
	private Integer ibillid;
	private String vcbillno;
	private String dtbilldate;
	private String vcperiod;
	private String vcmemo;
	private String creationdate;
	private String createdby;
	private String lastupdatedate;
	private String lastupdatedby;
	private char flagapp;
	private String appuser;
	private BigDecimal nummoneyall;
	private Integer iproviderid;
	private String appdate;
	private Integer idepartid;
	private Integer itype;
	private Integer ierpid;
	private String dtinsert;
	private Integer ocflag;
}
