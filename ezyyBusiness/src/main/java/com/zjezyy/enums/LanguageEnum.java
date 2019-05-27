package com.zjezyy.enums;

/**
 * 
* @ClassName: BusinessType
* @Description: 业务类型
* @author wuxuecheng
* @date 2019年3月5日
*
 */
public enum LanguageEnum {
	
	zh_cn(1,"简体中文"),
	en_gb(2,"English"),
	zh_hk(3,"繁体中文"),
;

	private int code;
    private String msg;

    LanguageEnum(int code, String msg) {
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
