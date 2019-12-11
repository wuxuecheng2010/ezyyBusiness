package com.zjezyy.entity.erp;

import lombok.Data;

@Data
public class TbStorePositionChangeS {
	private Integer isid;
	private Integer ibillid;
	private Integer iproductid;
	private String vcbatchnumber;
	private Integer iproviderid;
	private double numquantity;
}
