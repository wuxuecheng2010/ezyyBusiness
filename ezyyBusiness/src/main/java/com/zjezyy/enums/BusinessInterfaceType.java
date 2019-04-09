package com.zjezyy.enums;

/**
 * 
* @ClassName: BusinessInterfaceType
* @Description: 接口业务类型
* @author wuxuecheng
* @date 2019年3月18日
*
 */
public enum BusinessInterfaceType {
	
	B2BToERPUnderLine(3,"线下B2B系统到ERP接口","b2b-underline"),
	B2BToERPOnLine(4,"线上B2B系统到ERP接口","b2b-online"),
	
;

	private int code;
    private String name;
    private String bif;

    BusinessInterfaceType(int code, String name,String bif) {
        this.name = name;
        this.code=code;
        this.bif=bif;
    }

    public String getMsg() {
        return name;
    }
    public String getBif() {
        return bif;
    }
    
    public int getCode() {
        return code;
    }


}
