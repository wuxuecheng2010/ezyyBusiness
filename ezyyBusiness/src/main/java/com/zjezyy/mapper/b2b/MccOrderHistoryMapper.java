package com.zjezyy.mapper.b2b;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.zjezyy.entity.b2b.MccOrderHistory;

@Repository
@Mapper
public interface MccOrderHistoryMapper {
	String SELECT_FIELDS="order_history_id,order_id,order_status_id,comment,notify,date_added";

	String INSERT_FIELDS="order_id,order_status_id,comment,notify,date_added";

	String INSERT_VALUES="#{order_id},#{order_status_id},#{comment},#{notify},now()";

	String TABLE_NAME="mcc_order_history";
	
	@Insert({"insert into ",TABLE_NAME,"("+INSERT_FIELDS+")","values","("+INSERT_VALUES+")"})
	@Options(useGeneratedKeys=true,keyProperty="order_history_id",keyColumn="order_history_id")
	int insert(MccOrderHistory mccOrderHistory);
	
	@Select({" select count(1) num from mcc_order_history where order_id=#{order_id} and order_status_id=#{order_status_id}"})
	int countByOrderIDAndStatusID(int order_id, int order_status_id);
	
}
