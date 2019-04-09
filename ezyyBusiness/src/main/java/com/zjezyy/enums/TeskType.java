package com.zjezyy.enums;

/**
 * 
* @ClassName: TeskType
* @Description: 任务类型
* @author wuxuecheng
* @date 2019年3月5日
*
 */
public enum TeskType {
	Tesk_Order("新单"),
	Tesk_Replenishment("补货"),
	Tesk_BulkPicking("散件拣货"),
	Tesk_WholePicking("整件拣货"),
	Tesk_WholeInUp("整件上架"),
	Tesk_BulkInUp("散件上架"),
;

    private String msg;

    TeskType( String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }


}
