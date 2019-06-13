package com.zjezyy.mapper.b2b;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.zjezyy.entity.b2b.MccPayResult;

@Repository
@Mapper
public interface MccPayResultMapper {
	String SELECT_FIELDS="result_id,order_id,payment,busstype,plordercode,baseinfo,fee,attach,paydate,tradestatus";

	String INSERT_FIELDS="order_id,ordercode,payment,busstype,plordercode,baseinfo,fee,attach,paydate,tradestatus";

	String INSERT_VALUES="#{order_id},#{ordercode},#{payment},#{busstype},#{plordercode},#{baseinfo},#{fee},#{attach},#{paydate},#{tradestatus}";

	String TABLE_NAME="mcc_pay_result";
	
	@Insert({"insert into ",TABLE_NAME,"("+INSERT_FIELDS+")","values","("+INSERT_VALUES+")"})
	@Options(useGeneratedKeys=true,keyProperty="result_id",keyColumn="result_id")
	int insert(MccPayResult mccPayResult);
	
	@Select({"select count(*) from ",TABLE_NAME," where order_id=#{order_id}"})
	int countMccPayResultByOrderID(int  order_id);
	
	
}
