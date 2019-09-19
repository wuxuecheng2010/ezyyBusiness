package com.zjezyy.mapper.b2b;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.zjezyy.entity.b2b.MccTbCustomerproductprice;

@Repository
@Mapper
public interface MccTbCustomerproductpriceMapper {
	String SELECT_FIELDS = "id,icustomerid,iproductid,price,updatedate,customer_id,productid_id";

	String INSERT_FIELDS = "icustomerid,iproductid,price,updatedate,customer_id,productid_id";

	String INSERT_VALUES = "#{icustomerid,jdbcType=INTEGER},#{iproductid,jdbcType=INTEGER},#{price,jdbcType=DECIMAL},now(),#{customer_id,jdbcType=INTEGER},#{productid_id,jdbcType=INTEGER}";
	String TABLE_NAME = "mcc_tb_customerproductprice";

	@Select({ "select ", SELECT_FIELDS, " from ", TABLE_NAME, " where icustomerid = #{icustomerid} and iproductid=#{iproductid}" })
	MccTbCustomerproductprice getOneByERP(int icustomerid,int iproductid);
	
	@Select({ "select ", SELECT_FIELDS, " from ", TABLE_NAME, " where customer_id = #{customer_id} and product_id=#{product_id}" })
	MccTbCustomerproductprice getOneByWMS(int customer_id,int product_id);

	@Insert({ "insert into ", TABLE_NAME, "(" + INSERT_FIELDS + ")", "values", "(" + INSERT_VALUES + ")" })
	@Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
	int insert(MccTbCustomerproductprice mccTbCustomerproductprice);
	
	@Update({"update ",TABLE_NAME," set price=#{price},updatedate=now() "," where id=#{id}"})
	int update(MccTbCustomerproductprice mccTbCustomerproductprice);
	
	@Delete({"delete from ",TABLE_NAME," where id=#{id}"})
	int delete(Integer id);
}
