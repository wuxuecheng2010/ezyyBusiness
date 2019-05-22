package com.zjezyy.entity.b2b;

import com.zjezyy.entity.erp.TbCustomerKind;

import lombok.Data;

@Data
public class MccTbCustomerKind {

	private Integer id;
	private Integer icustomerkindid;
	private String vccustomerkindname;

	public MccTbCustomerKind() {

	}

	public MccTbCustomerKind(TbCustomerKind tbCustomerKind) {
		this.icustomerkindid = tbCustomerKind.getIcustomerkindid();
		this.vccustomerkindname = tbCustomerKind.getVccustomerkindname();
	}

}
