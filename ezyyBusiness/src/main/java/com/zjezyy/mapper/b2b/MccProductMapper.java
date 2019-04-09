package com.zjezyy.mapper.b2b;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.zjezyy.entity.b2b.MccProduct;
import com.zjezyy.entity.b2b.MccProduct_Eo;
@Repository
@Mapper
public interface MccProductMapper {
	
	String SELECT_FIELDS="a.product_id,a.model,a.sku,a.upc,a.ean,a.jan,a.isbn,a.mpn,a.location,a.quantity,a.stock_status_id,a.image,a.manufacturer_id,a.shipping,a.price,a.points,a.tax_class_id,a.date_available,a.weight,a.weight_class_id,a.length,a.width,a.height,a.length_class_id,a.subtract,a.minimum,a.sort_order,a.status,a.viewed,a.date_added,a.date_modified,a.erpiproductid";
	String TABLE_NAME="mcc_product a";
	@Select({"select ", SELECT_FIELDS,",b.name,b.description", " from ", TABLE_NAME,
		" left join  mcc_product_description b on a.product_id=b.product_id and b.language_id=#{language_id}",
		" where a.product_id = #{product_id}"})
	MccProduct_Eo getOne(int product_id,int language_id);
	
	
	@Select({"select ",SELECT_FIELDS," from ",TABLE_NAME})
	List<MccProduct> getAll();
	
	
	@Select({"select ",SELECT_FIELDS," from ",TABLE_NAME," where a.status=1"})
	List<MccProduct> getAllOnProduct();
	
	
	@Select({"select ",SELECT_FIELDS," from ",TABLE_NAME,"  where a.erpIproductid=#{erpIproductid}"})
	MccProduct getOneByErpIproductid(int erpIproductid);
	
	/**
	 * 
	* @Title: updateOne
	* @Description: 更新上下架
	* @param @param productId
	* @param @param status    参数
	* @author wuxuecheng
	* @return void    返回类型
	* @throws
	 */
	@Update({"update mcc_product set status=#{status} where product_id=#{product_id}"})
	int setMccProductOnOff(int product_id,int  status);
	
	
	@Update({"update mcc_product set price=#{price} where product_id=#{product_id}"})
	int setMccProductPrice(int product_id,BigDecimal price);
	
	@Update({"update mcc_product set quantity=#{quantity} where product_id=#{product_id}"})
	int  setMccProductQuantity(int product_id,BigDecimal quantity);
}
