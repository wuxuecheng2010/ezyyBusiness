package com.zjezyy.entity.erp;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class SysCustomerProductCanSale {
	private String vcuniversalname;
	private String numopen; // 开发数量(0,不允许开票，-1不控制)
}
