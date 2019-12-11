package com.zjezyy.enums;

/**
 * 
* @ClassName: MessageGroup
* @Description:短信息類型
* @author wuxuecheng
* @date 2019年3月5日
*
 */
public enum MessageGroup {
	
	B2B付款通知("B2B付款通知"),
	转仓单超时通知("转仓单超时通知"),
;


    private String groupname;

    MessageGroup(String groupname) {
        this.groupname = groupname;

    }

	public String getGroupname() {
		return groupname;
	}

}
