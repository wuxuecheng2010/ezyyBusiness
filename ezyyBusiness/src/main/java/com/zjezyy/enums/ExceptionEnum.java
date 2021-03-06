package com.zjezyy.enums;

public enum ExceptionEnum {
	
	UNKNOW_ERROR(-1,"未知原因"),
	HTTP_ERROR(-2,"HTTP失败"),
	USER_ERR(-0001,"API授权用户不存在"),
	USER_PSW_ERR(-0002,"API授权用户密码错误"),
	
	
	TOKEN_LACK(-1000,"API Token缺失"),
	TOKEN_TIMEOUT(-1001,"API Token过时"),
	TOKEN_INVALID(-1002,"API Token无效"),
	JTA_TRANSATION_UNEXPECTEDLY_ROLLED_BACK(-1003,"接口超时,请重试"),
	
	
	PREVENT_PARAMS_NOT_ALOW_EMPTY(-2,"[防刷]入参不允许为空"),
	PREVENT_STRATEGY_ONT_IN_USE(-2,"无效的策略"),
	
    B2B_ORDER_ID_LACK(-1101,"B2B订单ID缺失或者不存在"),
    B2B_ORDER_NOT_IN_ERP_TMP(-1102,"B2B订单未在接口表中找到"),
    B2B_ORDER_NOT_PAYED(-1103,"B2B订单不是支付状态"),
    B2B_ORDER_IS_NOT_INIT_STATUS(-1104,"B2B订单状态不为初始状态"),
    B2B_ORDER_IS_NOT_INIT_CREDIT_STATUS(-1105,"B2B订单状态不为信用订单初始状态"),
    
    B2B_PRODUCT_UNRELATED(-1201,"B2B商品信息不完整"),//B2B商品与ERP商品未做关联
    B2B_PRODUCT_RELATED_ERROR(-1202,"B2B商品信息有误"),//B2B商品与ERP商品关联不正确
    B2B_PRODUCT_PRICE_TIMEOUT(-1203,"B2B商品价格有误"),//B2B商品价格与ERP商品价格不一致
    B2B_PRODUCT_NOT_INCLUDED_BY_THIS_CUSTOMERKIND(-1204,"B2B商品信息没有纳入客户集合"),//B2B商品与ERP商品关联不正确
    
    B2B_CUSTOMER_UNRELATED(-1301,"B2B客户信息不完整"),//B2B客户与ERP客户未做关联
    B2B_CUSTOMER_RELATED_ERROR(-1302,"B2B客户信息有误"), //B2B客户与ERP客户关联不正确
    B2B_CUSTOMER_IS_EXISIT(-1303,"B2B客户信息已存在，不能重复创建"), //B2B客户信息已存在，不能重复创建
    
    
    ERP_ORDER_CODE_CREATE_FAIL(-2101,"创建销售单据号失败"),//创建ERP销售订单号失败
    ERP_ORDER_CODE_CREATE_HTTP_FAIL(-2102,"销售单据号创建失败"),//创建ERP销售订单号失败
    ERP_ORDER_IS_USED_TO_NOTICE(-2103,"销售订单已经转为销售开票，不能重复转"),//ERP销售订单flagapp=Y表示 已经转到销售开票了
    ERP_ORDER_NOT_EXISIT(-2104,"销售订单不存在"),//
    ERP_ORDER_ITYPEID_WRONG(-2105,"销售订单单据类型不为4"),// 单据itypeid不=4 参考BusinessInterfaceType
    ERP_ORDER_TBMCC_NOT_EXISIT(-2106,"销售订单中间表数据不存在"),
    YD_ORDER_IS_IN_B2B(-2107,"药店订单已经传递到B2B"),
    YD_ORDER_DTL_EMPTY(-2108,"药店订单明细没有"),
    
    ERP_PRODUCT_PRICE_LQ_ZERO(-2201,"商品价格未设定"),//ERP商品价格<=0
    ERP_PRODUCT_STOCK_SHORT(-2202,"商品库存不足"),
    ERP_PRODUCT_NO_CASH_SALE(-2203,"商品不允许现金销售"),
    ERP_PRODUCT_ZZRSOVER(-2204,"商品属于终止妊娠药品，但是您的该类经营许可证过期"),
    ERP_B2B_ORDER_PRICE_NOT_EQ(-2205,"价格异常"),//订单与商品设定的价格不一致
    
    ERP_PRODUCT_ID_ERR(-2207,"商品ID未提供或者不是有效的数字"),
    
    
    ERP_CUSTOMER_LOCKED(-2301,"客户被锁定"),
    ERP_CUSTOMER_ID_NOT_SUPPLY(-2302,"客户ID未提供"),
    ERP_CUSTOMER_VCSTOREADDRESS_NOT_EXISIT(-2303,"客户信息不存在运输地址."),
    ERP_CUSTOMER_VCSTOREADDRESS_TO_LATLNG_FAIL(-2304,"客户信息转经纬度失败."),
    ERP_CUSTOMER_LATLNG_TO_DISTRICT_FAIL(-2305,"经纬度信息转县级信息失败."),
    ERP_CUSTOMER_ID_NOT_EXISIT(-2306,"当前提供的客户ID不在客户信息中"),
    ERP_CUSTOMER_LACK_VCLINKMAN(-2307,"客户信息缺少维护联系人"),
    ERP_CUSTOMER_KIND_NOT_SET(-2308,"客户集合信息未维护,请先维护"),
    ERP_CUSTOMER_BASE_KIND_NOT_SET(-2309,"客户集合信息未维护,请先维护"),
    
    ERP_SALESNOTICE_NOT_EXISIT(-3101,"销售开票单据不存在"),
    ERP_SALESNOTICE_IS_APPED(-3102,"销售开票单据已审核"),
    ERP_SALESNOTICE_HAS_NO_SOURCEID(-3103,"销售开票单据不存在上级单据号"),
    ERP_SALESNOTICE_IS_TO_WMS(-3104,"销售开票单据已经传入到WMS"),
    ERP_SALESNOTICE_HTTP_APPROVAL_FAIL(-3105,"销售开票单据HTTP请求审核失败"),
    ;

    private Integer code;

    private String msg;

    ExceptionEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
