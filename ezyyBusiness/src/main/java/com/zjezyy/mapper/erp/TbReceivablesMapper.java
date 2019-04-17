package com.zjezyy.mapper.erp;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.zjezyy.entity.erp.TbReceivables_Eo;
@Repository
@Mapper
public interface TbReceivablesMapper {
	
	String SELECT_FIELDS="";

	
	String TABLE_NAME="tb_receivables";
	
	@Select({"select round(sysdate - tmp1.dtCreate, 0) days " , 
			"                  from (select min(t.dtcreationdate) as dtCreate " , 
			"                          from tb_receivables t" , 
			"                         where nvl(t.Flaggather, 'N') = 'N'" , 
			"                           and t.nummoney > 0" ,
			"                           and t.icustomerid = #{icustomerid}) tmp1"})
	Integer getEarliest(int icustomerid);
	
	
	
	@Select({"select t1.icustomerid,t2.numcreditmoney, sum(t1.nummoney) as nummoney " + 
			" from tb_receivables t1,Tb_Customer t2 where nvl(t1.Flaggather,'N') ='N' and t1.nummoney>0 " + 
			" and t1.icustomerid= t2.icustomerid and  t1.icustomerid = #{icustomerid} " + 
			" group by t1.icustomerid,t2.NUMCREDITMONEY "})
	TbReceivables_Eo getSummer(int icustomerid);
}