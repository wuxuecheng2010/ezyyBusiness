package com.zjezyy.enums;

/**
 * 
* @ClassName: StorageoptionType
* @Description: 储存方式
* @author wuxuecheng
* @date 2019年3月5日
*
 */
public enum StorageoptionType {
	
	COLD(2,"冷藏"),
	FREEZING(4,"冷冻"),
;

	private int code;
    private String msg;

    StorageoptionType(int code, String msg) {
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
