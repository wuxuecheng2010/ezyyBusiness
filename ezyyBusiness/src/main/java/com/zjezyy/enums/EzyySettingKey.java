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
	
	API_HEADER_NAME("api_header_name","http请求头名称"),
	API_USER_NAME("api_user_name","http请求授权用户名称"),
	API_USER_PASSWORD("api_user_password","http请求授权用户密码"),
	
	ORDER_EXPIRE_TIME("order_pay_expire_time","b2b订单付款时限  单位    分钟"),
	ORDER_UNPAY_STATUS("order_unpay_status","订单未付款的状态码"),
	ORDER_PAYED_STATUS("order_payed_status","订单付款后的状态码"),
	ORDER_EXPIRE_STATUS("order_expire_status","订单取消状态码（超时即为取消）"),
	ORDER_INIT_STATUS("order_init_status","订单初始状态"),
	B2B_MANAGER_TELS("b2b_manager_tels","b2b网站管理者，接收系统重要错误信息"),
	
	PRODUCT_PRICE_CUSTOMER_MODEL("product_price_customer_model","价格模式 0 一刀切按照B2B价格体系；1按照客户所在价格集合的商品价格"),
	PRODUCT_PRICE_CUSTOMER_FIELD("product_price_customer_field","b2b价格取ERP系统价格体系所对应的字段"),
	
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
