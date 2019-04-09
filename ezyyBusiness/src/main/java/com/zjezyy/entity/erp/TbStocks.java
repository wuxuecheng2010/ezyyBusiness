package com.zjezyy.entity.erp;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class TbStocks {
	/*private Integer iproductid;
	private String vcproductcode;
	private String vcuniversalname;
	private String vcstandard;
	private String vcproductname;
	private Integer iproductunitid;
	private Integer idrugformid;
	private BigDecimal numcountryprice;
	private Integer iproducerid;
	private String vcproducername;
	private String vceasycode;
	private Integer numwarningdays;
	private Integer dtsaledate;
	private BigDecimal numcansell;
	private BigDecimal numlimitquantity;
	private BigDecimal numlarge;
	private char flaghardtoget;//紧俏标志
	private char flagsaleslimit;//是否限量销售
	private BigDecimal numopen;
	private char flagquantum;//是否定额供应
*/
	private Integer iproductid;
	private Integer ibatchid;
	private Integer iproviderid;
	private BigDecimal numprice; // 进价
	private BigDecimal numpurchasetaxrate;// 采购税率
	private BigDecimal numquantity;
	private Integer iunitid;
	private Integer ipackingid;
	private String vcbatchnumber;
	private String dtusefullife;
	private String dtmanufacture;
	private String vcconfirmfile;
	private Integer ilinkmanid;
	private Integer numlarge;
	private Integer nummiddle;
	private char flagtwo;
	private BigDecimal numsaletaxrate;
	
	private String vcdisinfection;
	private String dtdisinfection;
}
