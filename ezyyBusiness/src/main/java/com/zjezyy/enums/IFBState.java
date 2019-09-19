package com.zjezyy.enums;

/**
 * 
* @ClassName: IFBUnderLineState
* @Description: 线下支付反馈状态
* @author wuxuecheng
* @date 2019年3月18日
*
 */
public enum IFBState {
	
	等待处理(1),//线下认为是 线上就是等待发货
	处理中(2),
	已发货(3),
	;

	private int code;

    IFBState(int code) {
        this.code=code;
    }
    public int getCode() {
        return code;
    }


}
