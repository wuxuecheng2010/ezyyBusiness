package com.zjezyy.mapper.erp;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.zjezyy.entity.erp.TbLisenceCustomer_Eo;
@Repository
@Mapper
public interface TbLisenceCustomerMapper {
	
	@Select({"select t.icustomerid,t.vclisencecode,t.dtend, t2.flagspecial,t2.flagzzrs,t2.flagusefullife,t2.vclisencetypename" , 
			"from tb_lisence_customer t,tb_lisencetype t2 " ,
			"where t.ilisencetypeid = t2.ilisencetypeid and t.icustomerid = #{icustomerid}"})
	List<TbLisenceCustomer_Eo> getListByIcustomerid(int icustomerid);
}