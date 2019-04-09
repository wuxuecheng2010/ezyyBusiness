package com.zjezyy.entity.erp;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class TbSalesNoticeS {
	private Integer isid;
	private Integer ibillid;

	private float numqueue;
	private Integer isourceid;
	private Integer iproductid;
	private Integer ilinkmanid;
	private Integer iunitid;
	private Integer ibatchid;
	private BigDecimal numprice;
	private BigDecimal numquantity;
	private BigDecimal numapplications;
	private BigDecimal numsaletaxrate;
	private BigDecimal numinprice;
	private BigDecimal numpurchasetaxrate;
	private Integer numlarge;
	private Integer ipackingid;
	private String vcmemo;
	private String dtcreationdate;
	private String vccreatedby;
	private String dtlastupdatedate;
	private String vclastupdatedby;
	private Integer istorepositionid;
	private String vcbatchnumber;
	private String vcconfirmfile;
	private String vcdisinfection;
	private String dtdisinfection;
	private Integer istockid;
	private Integer iproviderid;
	private String dtmanufacture;
	private String dtusefullife;
	
	
	
	
	private BigDecimal numguideprice;
	private BigDecimal numlastprice;
	private BigDecimal numlowprice;
	private BigDecimal numcountryprice;
	private BigDecimal numassesscost;
	
	
	private BigDecimal numwriteoff;
	private BigDecimal numreturn;
	private BigDecimal numpiece;
	private BigDecimal numrealout;
	
	
	public TbSalesNoticeS() {
		super();
	}
	
	public TbSalesNoticeS(int ibillid,float numqueue,BigDecimal numapplications,
			TbSalesOrderS tbSalesOrderS,TbStocks tbStocks,
			TbCustomerKindPrice tbCustomerKindPrice,
			BigDecimal numlastprice,BigDecimal numcountryprice
			) {
		
		//private Integer isid;
		this.ibillid=ibillid;
		this.numqueue=numqueue;
		this.isourceid=tbSalesOrderS.getIsid();
		this.iproductid=tbSalesOrderS.getIproductid();
		this.ilinkmanid=tbStocks.getIlinkmanid();
		this.iunitid=tbSalesOrderS.getIunitid();
		this.ibatchid=tbStocks.getIbatchid();
		this.numprice=tbSalesOrderS.getNumprice();
		this.numquantity=BigDecimal.ZERO;
		this.numapplications=numapplications;
		this.numsaletaxrate=tbStocks.getNumsaletaxrate();
		this.numinprice=tbStocks.getNumprice();//进价
		this.numpurchasetaxrate=tbStocks.getNumpurchasetaxrate();
		this.numlarge=tbStocks.getNumlarge();
		this.ipackingid=tbStocks.getIpackingid();
		this.vcmemo=tbSalesOrderS.getVcmemo();
		//private String dtcreationate;
		this.vccreatedby=tbSalesOrderS.getVccreatedby();
		//private String dtlastupdatedate;
		this.vclastupdatedby=tbSalesOrderS.getVccreatedby();
		//private Integer istorepositionid;
		this.vcbatchnumber=tbStocks.getVcbatchnumber();
		this.vcconfirmfile=tbStocks.getVcconfirmfile();
		this.vcdisinfection=tbStocks.getVcdisinfection();
		this.dtdisinfection=tbStocks.getDtdisinfection()==null?"":tbStocks.getDtdisinfection().substring(0, 10);
		this.istockid=0;
		this.iproviderid=tbStocks.getIproviderid();
		this.dtmanufacture=tbStocks.getDtmanufacture()==null?"":tbStocks.getDtmanufacture().substring(0, 10);
		this.dtusefullife=tbStocks.getDtusefullife()==null?"":tbStocks.getDtusefullife().substring(0, 10);
		
		
		this.numguideprice=tbSalesOrderS.getNumprice();
		this.numlastprice=numlastprice;
		this.numlowprice=tbCustomerKindPrice.getNumlowprice();
		this.numcountryprice=numcountryprice;
		this.numassesscost=tbCustomerKindPrice.getNumassesscost();
		
		this.numwriteoff=BigDecimal.ZERO;
		this.numreturn=BigDecimal.ZERO;
		this.numpiece=BigDecimal.ZERO;
		this.numrealout=BigDecimal.ZERO;
		
		this.istorepositionid=0;
		
	}

}
