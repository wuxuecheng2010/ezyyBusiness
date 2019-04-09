package com.zjezyy.entity.erp;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class TbSalesOrder {
   private Integer ibillid;
   private String vcbillcode;
   private  Integer isourceid;
   private Integer  itypeid;
   private Integer icustomerid;
   private BigDecimal nummoney;
   private char flagstop;
   private Integer numprintcount;
   private char flagperform;//执行标志
   private char flagurgent;//加急标志
   private String vcmemo;
   private String vcaddress;
   private char flagapp;//是否审核
   private String dtcreationdate;//制单日期
   private String vccreatedby;//制单人
   private Integer  icustomeraidid;//客户辅助ID
   private Integer  ifb;//反馈级别1-等待处理 2-已经反馈为处理中   3-已反馈发货
   
   public TbSalesOrder() {
	   super();
   }
   public TbSalesOrder(TbMccOrder tbMccOrder,String vcbillcode, int itypeid, String vccreatedby) {
	   //private Integer ibillid;
	   this.vcbillcode=vcbillcode;
	   this.isourceid=tbMccOrder.getImpid();
	   this.itypeid=itypeid;
	   this.icustomerid=tbMccOrder.getErp_icustomerid();
	   this.nummoney=tbMccOrder.getMcc_total();
	   this.flagstop='N';
	   this.numprintcount=0;
	   this.flagperform='N';//执行标志
	   this.flagurgent='N';//加急标志
	   this.vcmemo=tbMccOrder.getMcc_comment()==null?"":tbMccOrder.getMcc_comment();
	   this.vcaddress=(new StringBuilder()).append(tbMccOrder.getMcc_shipping_zone()).append(tbMccOrder.getMcc_shipping_city())
				.append(tbMccOrder.getMcc_shipping_district()).append(tbMccOrder.getMcc_shipping_address()).toString();
	   this.flagapp='Y';//是否审核
	   //private String dtcreationdate;//制单日期
	   this.vccreatedby=vccreatedby;//制单人
	   this.ifb=1;//反馈级别1-等待处理 2-已经反馈为处理中   3-已反馈发货
   }
}
