package com.zjezyy.mapper.b2b;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.zjezyy.entity.b2b.MccTbCustomerKindPrice;
import com.zjezyy.entity.erp.TbCustomerKindPrice;
@Repository
@Mapper
public interface MccTbCustomerKindPriceMapper {
	
	String SELECT_FIELDS="id,isid,icustomerkindid,iproductid,numprice,numlowprice,numassesscost,numguidprice";

	String INSERT_FIELDS="isid,icustomerkindid,iproductid,numprice,numlowprice,numassesscost,numguidprice";

	String INSERT_VALUES="#{isid ,jdbcType=INTEGER},#{icustomerkindid,jdbcType=INTEGER},#{iproductid,jdbcType=INTEGER},#{numprice,jdbcType=DECIMAL },#{numlowprice,jdbcType=DECIMAL },#{numassesscost,jdbcType=DECIMAL },#{numguidprice,jdbcType=DECIMAL }";



	String TABLE_NAME="mcc_tb_customerkindprice";
	
	@Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where isid = #{isid}"})
	MccTbCustomerKindPrice getOne(int isid);
	
	@Update({"update ",TABLE_NAME ," set ",
		" icustomerkindid=#{icustomerkindid,jdbcType=INTEGER} ,",
		" iproductid=#{iproductid,jdbcType=INTEGER}, ",
		" numprice=#{numprice,jdbcType=DECIMAL}, ",
		" numlowprice=#{numlowprice,jdbcType=DECIMAL} ,",
		" numassesscost=#{numassesscost,jdbcType=DECIMAL} ,",
		" numguidprice=#{numguidprice,jdbcType=DECIMAL} ",
		" where isid=#{isid ,jdbcType=INTEGER}"
	})
	int updateByTbCustomerKindPrice(TbCustomerKindPrice tbCustomerKindPrice);
	
	
	@Insert({"insert into ",TABLE_NAME,"("+INSERT_FIELDS+")","values","("+INSERT_VALUES+")"})
	@Options(useGeneratedKeys=true,keyProperty="id",keyColumn="id")
	int insert(MccTbCustomerKindPrice mccTbCustomerKindPrice);
	
}
