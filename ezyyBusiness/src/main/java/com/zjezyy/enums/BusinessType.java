package com.zjezyy.enums;

/**
 * 
* @ClassName: BusinessType
* @Description: 业务类型
* @author wuxuecheng
* @date 2019年3月5日
*
 */
public enum BusinessType {
	
	SALE(1,"销售"),
	REFUND(2,"退货"),
;

	private int code;
    private String msg;

    BusinessType(int code, String msg) {
        this.msg = msg;
        this.code=code;
    }

    public String getMsg() {
        return msg;
    }
    
    public int getCode() {
        return code;
    }


}
