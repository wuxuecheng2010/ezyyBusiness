package com.zjezyy.entity.b2b;

import java.math.BigDecimal;

import com.zjezyy.entity.erp.VwMccSalesNotice;

import lombok.Data;

@Data
public class MccTbSalesNotice {
	private Integer mcc_salsenotice_id;//表ID
	private Integer mcc_order_id;
	private BigDecimal mcc_total;
	private String mcc_fullname;
	private Integer salesorder_ibillid;
	private String salesorder_vcbillcode;
	private Integer salesnotice_ibillid;
	private String salesnotice_vcbillcode;
	private BigDecimal salesnotice_nummoney;
	
	
	public MccTbSalesNotice(VwMccSalesNotice vwMccSalesNotice) {

		//this.mcc_salsenotice_id = mcc_salsenotice_id;
		this.mcc_order_id = vwMccSalesNotice.getMcc_order_id();
		this.mcc_total = vwMccSalesNotice.getMcc_total();
		this.mcc_fullname = vwMccSalesNotice.getMcc_fullname();
		this.salesorder_ibillid = vwMccSalesNotice.getSalesorder_ibillid();
		this.salesorder_vcbillcode = vwMccSalesNotice.getSalesorder_vcbillcode();
		this.salesnotice_ibillid = vwMccSalesNotice.getSalesnotice_ibillid();
		this.salesnotice_vcbillcode = vwMccSalesNotice.getSalesnotice_vcbillcode();
		this.salesnotice_nummoney = vwMccSalesNotice.getSalesnotice_nummoney();
	}


	public MccTbSalesNotice() {
		
	}
	
	
}
