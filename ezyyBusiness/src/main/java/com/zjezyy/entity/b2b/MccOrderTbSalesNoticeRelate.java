package com.zjezyy.entity.b2b;

import java.math.BigDecimal;

import com.zjezyy.entity.erp.TbSalesNotice;
import com.zjezyy.entity.erp.TbSalesOrder;

import lombok.Data;

@Data
public class MccOrderTbSalesNoticeRelate {
	
	private Integer id;
	private Integer order_id;
	private String fullname;
	private String ordercode;
	private String payment_method;
	private String payment_code;
	private BigDecimal total_fee;
	private Integer salesnotice_ibillid;
	private String salesnotice_vcbillcode;
	private Integer salesorder_ibillid;
	private String salesorder_vcbillcode;
	private BigDecimal salesnotice_nummoney;
	private Integer flagpay;
	
	
	public MccOrderTbSalesNoticeRelate(TbSalesOrder tbSalesOrder, TbSalesNotice tbSalesNotice, MccOrder mccOrder) {
		this.order_id=mccOrder.getOrder_id();
		this.fullname=mccOrder.getFullname();
		this.ordercode=mccOrder.getOrdercode();
		this.payment_method=mccOrder.getPayment_method();
		this.payment_code=mccOrder.getPayment_code();
		this.total_fee=mccOrder.getTotal();
		this.salesnotice_ibillid=tbSalesNotice.getIbillid();
		this.salesnotice_vcbillcode=tbSalesNotice.getVcbillcode();
		this.salesorder_ibillid=tbSalesOrder.getIbillid();
		this.salesorder_vcbillcode=tbSalesOrder.getVcbillcode();
		this.salesnotice_nummoney=tbSalesNotice.getNummoney();
		this.flagpay=0;
		
	}


	public MccOrderTbSalesNoticeRelate() {
	}
}
