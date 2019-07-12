package com.zjezyy.mapper.b2b;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.zjezyy.entity.b2b.MccAddress;
import com.zjezyy.entity.b2b.MccProduct;
import com.zjezyy.entity.b2b.MccProduct_Eo;
@Repository
@Mapper
public interface MccProductMapper {
	
	String SELECT_FIELDS="a.product_id,a.model,a.sku,a.upc,a.ean,a.jan,a.isbn,a.mpn,a.location,a.quantity,a.stock_status_id,a.image,a.manufacturer_id,a.shipping,a.price,a.points,a.tax_class_id,a.date_available,a.weight,a.weight_class_id,a.length,a.width,a.height,a.length_class_id,a.subtract,a.minimum,a.sort_order,a.status,a.viewed,a.date_added,a.date_modified,a.erpiproductid,a.numlarge,a.nummiddle,a.numsmall";
	String TABLE_NAME="mcc_product a";
	
	String INSERT_FIELDS="model,sku,upc,ean,jan,isbn,mpn,location,quantity,stock_status_id,image,manufacturer_id,shipping,price,points,tax_class_id,date_available,weight,weight_class_id,length,width,height,length_class_id,subtract,minimum,sort_order,status,viewed,date_added,date_modified,erpiproductid,nummiddle";

	String INSERT_VALUES="#{model,jdbcType=VARCHAR},#{sku,jdbcType=VARCHAR},#{upc,jdbcType=VARCHAR},#{ean,jdbcType=VARCHAR},#{jan,jdbcType=VARCHAR},#{isbn,jdbcType=VARCHAR},#{mpn,jdbcType=VARCHAR},#{location,jdbcType=VARCHAR},#{quantity,jdbcType=DECIMAL},#{stock_status_id,jdbcType=INTEGER},#{image,jdbcType=VARCHAR},#{manufacturer_id,jdbcType=INTEGER},#{shipping,jdbcType=INTEGER},#{price,jdbcType=DECIMAL},#{points,jdbcType=INTEGER},#{tax_class_id,jdbcType=INTEGER},#{date_available,jdbcType=DATE},#{weight,jdbcType=DECIMAL},#{weight_class_id,jdbcType=INTEGER},#{length,jdbcType=DECIMAL},#{width,jdbcType=DECIMAL},#{height,jdbcType=DECIMAL},#{length_class_id,jdbcType=INTEGER},#{subtract,jdbcType=INTEGER},#{minimum,jdbcType=INTEGER},#{sort_order,jdbcType=INTEGER},#{status,jdbcType=INTEGER},#{viewed,jdbcType=INTEGER},#{date_added,jdbcType=TIMESTAMP},#{date_modified,jdbcType=TIMESTAMP},#{erpiproductid,jdbcType=INTEGER},#{nummiddle,jdbcType=INTEGER}";


	
	
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
	
	
	@Update({"update mcc_product set nummiddle=#{nummiddle} where product_id=#{product_id}"})
	int  setMccProductNumMiddle(int product_id,Integer nummiddle);
	
	
	@Update({"update mcc_product set numlarge=#{numlarge},nummiddle=#{nummiddle},numsmall=#{numsmall} where product_id=#{product_id}"})
	int setMccProductPackage(int product_id,Integer numlarge,Integer nummiddle,Integer numsmall);
	
	
	@Insert({"insert into ","mcc_product","("+INSERT_FIELDS+")","values","("+INSERT_VALUES+")"})
	@Options(useGeneratedKeys=true,keyProperty="product_id",keyColumn="product_id")
	int insert(MccProduct mccproduct);
	
}
