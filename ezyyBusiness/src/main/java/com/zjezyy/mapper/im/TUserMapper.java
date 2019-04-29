package com.zjezyy.mapper.im;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.zjezyy.entity.im.TUser;

@Repository
@Mapper
public interface TUserMapper {
	String SELECT_FIELDS="id,username,password,flagstop,memo";
	String TABLE_NAME="t_user";
	
	//根据用户名查用户信息
	@Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where username = #{username}"})
	TUser getOne(String username);
}
