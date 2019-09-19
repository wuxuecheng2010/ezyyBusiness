package com.zjezyy.service;

import java.math.BigDecimal;
import java.util.List;

import com.zjezyy.entity.b2b.MccProduct;
import com.zjezyy.entity.erp.TbCustomer;
import com.zjezyy.entity.erp.TbProductPacking;
import com.zjezyy.entity.erp.TbProductinfo;
import com.zjezyy.entity.erp.TbProductinfo_Eo;
import com.zjezyy.entity.erp.TbStocks;

public interface ProductService {
//TbProductinfo_Eo getTbProductinfoEoByMccProductId(int product_id) throws RuntimeException;
TbProductinfo_Eo getTbProductinfoEoByMccProductIDAndTBCustomer(int product_id,TbCustomer tbCustomer) throws RuntimeException;
TbProductinfo_Eo getTbProductinfoEoDirtyByProductID(int iproductid);

BigDecimal getERPProductPriceByTbProductinfoEo(TbProductinfo_Eo tbProductinfoEo) throws RuntimeException;

BigDecimal getERPProductCustomerPriceByTbProductinfoEoAndTbCustomer(TbProductinfo_Eo tbProductinfoEo,TbCustomer tbCustomer) throws RuntimeException;

BigDecimal getERPProductPrice(TbProductinfo_Eo tbProductinfoEo,TbCustomer tbCustomer)throws RuntimeException;

TbProductinfo getTbProductinfoById(int iproductid);
boolean isNearEffective(TbProductinfo tbProductinfo,String usefullife);
boolean isNearEffective(TbProductinfo tbProductinfo,TbStocks tbStocks );

List<MccProduct> getAllMccProductID();
boolean isLowStorage(int iproductid);
void doSynchronizeOnOff(MccProduct mccProduct) throws RuntimeException;

void updateMccProductPrice(MccProduct mccProduct,TbProductinfo_Eo tbProductinfo_Eo) throws RuntimeException;
void doSynchronizePrice(MccProduct mccProduct) throws RuntimeException;

List<TbProductinfo> getProductsForB2B()throws RuntimeException;
List<TbProductinfo> getProductsForPM()throws RuntimeException;//药店
void setTbProductinfoIydstate(TbProductinfo tbProductinfo,int iydstate);
List<MccProduct> getAllB2BOnProduct()throws RuntimeException;

void doSynchronizeStock(MccProduct mccProduct)throws RuntimeException;
void doSynchronizeStock(Integer erpiproductid) throws RuntimeException;

BigDecimal getTbProductStocks(List<TbStocks> list)throws RuntimeException;
void updateMccProductQuantity(MccProduct mccProduct,BigDecimal erpqty)throws RuntimeException;

void doSynchronizeCustomerKindPrice()throws RuntimeException;

void doSynchronizeCustomerProductPrice() throws RuntimeException;


void doSynchronizeProductInfo(int iproductid,int store_id,int layout_id)throws RuntimeException;
void makeTbProductinfoToMccProduct(TbProductinfo tbProductinfo,int store_id,int layout_id,TbProductPacking tbProductPacking)throws RuntimeException;
void updateMccProductByTbProductinfo(TbProductinfo tbProductinfo)throws RuntimeException;


void doSynchronizeCustomerKind() throws RuntimeException;

Integer getProductNumMiddle(TbProductPacking tbProductPacking);


TbProductinfo_Eo getTbProductinfoEoByIproductIDAndTBCustomer(TbCustomer tbCustomer, Integer erpiproductid)
		throws RuntimeException;


MccProduct getMccProductByErpIproductid(Integer erpIproductid);


MccProduct getMccProductByProductID(Integer product_id);



}
