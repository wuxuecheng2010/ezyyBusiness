package com.zjezyy.entity.b2b;

import java.math.BigDecimal;

import com.zjezyy.entity.erp.TbMccOrder;
import com.zjezyy.entity.erp.TbMccOrderProduct;
import com.zjezyy.entity.erp.TbSalesNoticeS;
import com.zjezyy.entity.erp.TbSalesOrder;

import lombok.Data;

@Data
public class MccOrderProductReceiveDtl {
	private Integer id;
	private Integer isid;
	private Integer ibillid;
	private Integer order_id;
	private Integer order_product_id;
	private Integer erpiproductid;
	private BigDecimal quantity;
	private BigDecimal numapplications;
	private BigDecimal numrealout;
	private BigDecimal numprice;
	private String vcbatchnumber;
	private String fbcredate;
	private String fbupdate;
	private BigDecimal realtotal;
	
	
	public  MccOrderProductReceiveDtl() {
		
	}
	
	public MccOrderProductReceiveDtl(TbSalesOrder tbSalesOrder,TbMccOrder tbMccOrder,TbMccOrderProduct tbMccOrderProduct ,TbSalesNoticeS tbSalesNoticeS) {
		
		this. isid=tbSalesNoticeS.getIsid();
		this. ibillid=tbSalesNoticeS.getIbillid();
		this. order_id=tbMccOrder.getMcc_order_id();
		this. order_product_id=tbMccOrderProduct.getMcc_order_product_id();
		this. erpiproductid=tbSalesNoticeS.getIproductid();
		this. quantity=tbSalesNoticeS.getNumquantity();
		this.  numapplications=tbSalesNoticeS.getNumapplications();
		this.  numrealout=tbSalesNoticeS.getNumrealout();
		this.  numprice=tbSalesNoticeS.getNumprice();
		this.  vcbatchnumber=tbSalesNoticeS.getVcbatchnumber();
		
	}

}
