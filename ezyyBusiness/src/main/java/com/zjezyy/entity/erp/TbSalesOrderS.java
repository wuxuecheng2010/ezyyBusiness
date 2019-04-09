package com.zjezyy.entity.erp;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class TbSalesOrderS {
	private Integer isid;
	private Integer ibillid;
	private Integer isourceid;
	private Integer iproductid;
	private float numqueue;
	private BigDecimal numprice;
	private BigDecimal numquantity;
	private Integer iunitid;
	private String vcmemo;
	private String dtcreationdate;
	private String vccreatedby;
	private String vcproductcode;
	private String vcuniversalname;
	private String vcstandard;
	private String vcproductunit;
	private String vcproducername;
	private Integer istate;//0,未处理，１、已开票、２，已回写
	
	public TbSalesOrderS() {
		super();
	}
	
	public TbSalesOrderS(TbMccOrderProduct tbMccOrderProduct,int ibillid,float numqueue,int iunitid ,
    		String vccreatedby,String vcproductcode,String vcuniversalname,
    		String vcstandard,String vcproductunit,String vcproducername) {
		
		this.ibillid=ibillid;
		this.isourceid=tbMccOrderProduct.getImpdtlid();
		this.iproductid=tbMccOrderProduct.getErp_iproductid();
		this.numqueue=numqueue;
		this.numprice=tbMccOrderProduct.getMcc_price();
		this.numquantity=tbMccOrderProduct.getMcc_quantity();
		this.iunitid=iunitid;
		//this.vcmemo="";
		//private String dtcreationdate;
		this.vccreatedby=vccreatedby;
		this.vcproductcode=vcproductcode;
		this.vcuniversalname=vcuniversalname;
		this.vcstandard=vcstandard;
		this.vcproductunit=vcproductunit;
		this.vcproducername=vcproducername;
		this.istate=0;//0,未处理，１、已开票、２，已回写
		
	}
	
}
