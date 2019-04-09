package com.zjezyy.enums;

public enum ExceptionEnum {
	
	UNKNOW_ERROR(-1,"未知原因"),
	TOKEN_LACK(-1000,"Token缺失"),
	TOKEN_TIMEOUT(-1001,"Token过时"),
	
	PREVENT_PARAMS_NOT_ALOW_EMPTY(-2,"[防刷]入参不允许为空"),
	PREVENT_STRATEGY_ONT_IN_USE(-2,"无效的策略"),
	
    B2B_ORDER_ID_LACK(-1101,"B2B订单ID缺失或者不存在"),
    B2B_ORDER_NOT_IN_ERP_TMP(-1102,"B2B订单未在接口表中找到"),
    
    B2B_PRODUCT_UNRELATED(-1201,"B2B商品信息不完整"),//B2B商品与ERP商品未做关联
    B2B_PRODUCT_RELATED_ERROR(-1202,"B2B商品信息有误"),//B2B商品与ERP商品关联不正确
    B2B_PRODUCT_PRICE_TIMEOUT(-1203,"B2B商品价格有误"),//B2B商品价格与ERP商品价格不一致
    
    B2B_CUSTOMER_UNRELATED(-1301,"B2B客户信息不完整"),//B2B客户与ERP客户未做关联
    B2B_CUSTOMER_RELATED_ERROR(-1302,"B2B客户信息有误"), //B2B客户与ERP客户关联不正确
    
    
    ERP_ORDER_CODE_CREATE_FAIL(-2101,"创建销售单据号失败"),//创建ERP销售订单号失败
    ERP_ORDER_CODE_CREATE_HTTP_FAIL(-2102,"创建销售单据号http失败"),//创建ERP销售订单号失败
    ERP_PRODUCT_PRICE_LQ_ZERO(-2201,"系统商品价格有误"),//ERP商品价格<=0
    
    ERP_PRODUCT_STOCK_SHORT(-2202,"商品库存不足"),
    
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
