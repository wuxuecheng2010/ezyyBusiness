package com.zjezyy.entity.erp;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class VwMccSalesNotice {
	private Integer mcc_order_id;
	private BigDecimal mcc_total;
	private String mcc_fullname;
	private Integer salesorder_ibillid;
	private String salesorder_vcbillcode;
	private Integer salesnotice_ibillid;
	private String salesnotice_vcbillcode;
	private BigDecimal salesnotice_nummoney;
}
