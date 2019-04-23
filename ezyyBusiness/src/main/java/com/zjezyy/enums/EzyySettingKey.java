package com.zjezyy.enums;

/**
 * 
* @ClassName: EzyySettingKey
* @Description: B2B自定义设置Key
* @author wuxuecheng
* @date 2019年3月5日
*
 */
public enum EzyySettingKey {
	
	ORDER_EXPIRE_TIME("order_pay_expire_time","b2b订单付款时限  单位    分钟"),
	ORDER_UNPAY_STATUS("order_unpay_status","订单未付款的状态码"),
	ORDER_PAYED_STATUS("order_payed_status","订单付款后的状态码"),
	ORDER_EXPIRE_STATUS("order_expire_status","订单取消状态码（超时即为取消）"),
	ORDER_INIT_STATUS("order_init_status","订单初始状态"),
	B2B_MANAGER_TELS("b2b_manager_tels","b2b网站管理者，接收系统重要错误信息"),
;

	private String key;
    private String msg;

    /**
     * 只有b2b setting表code为ezyy的自定义信息项有效
    * 创建一个新的实例 EzyySettingKey.
    * 为了使接口使用的参数和B2b自定义的参数保持一致
    * @param key   b2b setting表key字段
    * @param msg   字段含义描述
     */
    EzyySettingKey(String key, String msg) {
        this.msg = msg;
        this.key=key;
    }

    public String getMsg() {
        return msg;
    }
    
    public String getKey() {
        return key;
    }


}
