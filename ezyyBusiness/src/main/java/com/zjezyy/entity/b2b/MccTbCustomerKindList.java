package com.zjezyy.entity.b2b;

import java.util.Date;

import com.zjezyy.entity.erp.TbCustomerKindList;

import lombok.Data;

@Data
public class MccTbCustomerKindList {
	
	private Integer id;
	private Integer isid;
	private Integer icustomerkindid;
	private Integer icustomerid;
	private Date dtend;
	
	public MccTbCustomerKindList() {
		
	}
	
	public MccTbCustomerKindList(TbCustomerKindList tbCustomerKindList) {
		this.isid=tbCustomerKindList.getIsid();
		this.icustomerid=tbCustomerKindList.getIcustomerid();
		this.icustomerkindid=tbCustomerKindList.getIcustomerkindid();
		this.dtend=tbCustomerKindList.getDtend();
	}
}
