package com.zjezyy.enums;

/**
 * 
* @ClassName: TeskType
* @Description: 任务类型
* @author wuxuecheng
* @date 2019年3月5日
*
 */
public enum MessageType {
	
	M_In(1,"入库"),
	M_Out(2,"出库"),
	M_Map(3,"地图"),
	M_UNKNOWN(3,"未知"),
;

	private int code;
    private String msg;

    MessageType(int code, String msg) {
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
