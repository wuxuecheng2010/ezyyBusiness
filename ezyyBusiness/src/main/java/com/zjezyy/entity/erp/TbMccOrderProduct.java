package com.zjezyy.entity.erp;

import java.math.BigDecimal;

import com.zjezyy.entity.b2b.MccOrderProduct;

import lombok.Data;
@Data
public class TbMccOrderProduct {
    private Integer impdtlid;
    
    private Integer impid;
    private Integer erp_iproductid;
    private BigDecimal erp_numsaletaxrate;
    
    private Integer mcc_order_id;
    private Integer mcc_order_product_id;
    private Integer mcc_product_id;
    private String mcc_name;
    private String mcc_model;
    private BigDecimal mcc_quantity;
    private BigDecimal mcc_price;
    private BigDecimal mcc_total;
    private BigDecimal mcc_tax;
    
    public TbMccOrderProduct() {
    	super();
    }
    public TbMccOrderProduct(MccOrderProduct mccOrderProduct,int impid,
			int iproductid, BigDecimal numsaletaxrate) {
    	
    	//private Integer impdtlid;
    	this.impid=impid;
        this.erp_iproductid=iproductid;
        this.erp_numsaletaxrate= numsaletaxrate;
        this.mcc_order_id=mccOrderProduct.getOrder_id();
        this.mcc_order_product_id=mccOrderProduct.getOrder_product_id();
        this.mcc_product_id=mccOrderProduct.getProduct_id();
        this.mcc_name=mccOrderProduct.getName();
        this.mcc_model=mccOrderProduct.getModel();
        this.mcc_quantity=mccOrderProduct.getQuantity();
        this.mcc_price=mccOrderProduct.getPrice();
        this.mcc_total=mccOrderProduct.getTotal();
        this.mcc_tax=mccOrderProduct.getTax();
        
    }
    
}
