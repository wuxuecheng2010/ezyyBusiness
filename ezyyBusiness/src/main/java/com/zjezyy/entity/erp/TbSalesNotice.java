package com.zjezyy.entity.erp;

import lombok.Data;

@Data
public class TbSalesNotice {
	private Integer ibillid;
	private String vcbillcode;
	private Integer isourceid;
	private Integer itypeid;
	private Integer icustomerid;
	private Integer isalerid;
	private Integer igathermode;
	private String vcaddress;
	private Integer isendtype;
	private char flagurgent;
	private String vcmemo;
	private String dtcreationdate;
	private String vccreatedby;
	private String dtlastupdatedate;
	private String vclastupdatedby;
	private String dtsenddate;
	private String vcddbh;
	private String icustomeraidid;
	
	
	public TbSalesNotice() {
		super();
	}

	public TbSalesNotice(TbSalesOrder tbSalesOrder,String vcbillcode,
			int itypeid,int isalerid,int igathermode,char flagurgent) {
		//private Integer ibillid;
		this.vcbillcode=vcbillcode;
		this.isourceid=tbSalesOrder.getIbillid();
		this.itypeid=itypeid;//从订单导入  此处为2
		this.icustomerid=tbSalesOrder.getIcustomerid();
		this.isalerid=isalerid;
		this.igathermode=igathermode;//1现金 2非现金
		this.vcaddress=tbSalesOrder.getVcaddress();
		//private Integer isendtype;
		this.flagurgent=flagurgent;//加急
		this.vcmemo=tbSalesOrder.getVcmemo();
		//private String dtcreationdate;
		this.vccreatedby=tbSalesOrder.getVccreatedby();
		//private String dtlastupdatedate;
		this.vclastupdatedby=tbSalesOrder.getVccreatedby();
		//private String dtsenddate;
		//private String vcddbh;
		//private String icustomeraidid;
	}
}
