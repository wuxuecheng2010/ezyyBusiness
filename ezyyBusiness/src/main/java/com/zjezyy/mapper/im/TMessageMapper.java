package com.zjezyy.mapper.im;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.zjezyy.entity.im.TMessage;

@Repository
@Mapper
public interface TMessageMapper {
	String SELECT_FIELDS="id,groupname,telephone,message,credate,flagerr";

	String INSERT_FIELDS="(groupname,telephone,message,credate,flagerr)";

	String INSERT_VALUES="(#{groupname,jdbcType=VARCHAR},#{telephone,jdbcType=VARCHAR},#{message,jdbcType=VARCHAR},now(),#{flagerr,jdbcType=INTEGER})";
	
	String TABLE_NAME="t_message";
	
	//根据用户名查用户信息
	@Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where id = #{id}"})
	TMessage getOne(int id);
	
	@Insert({"insert into ",TABLE_NAME,INSERT_FIELDS,"values",INSERT_VALUES})
	void insert(TMessage tMessage);
}
