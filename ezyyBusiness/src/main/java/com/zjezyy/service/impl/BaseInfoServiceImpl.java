package com.zjezyy.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.zjezyy.entity.erp.TbProducer;
import com.zjezyy.entity.erp.TbUnit;
import com.zjezyy.mapper.erp.TbProducerMapper;
import com.zjezyy.mapper.erp.TbUnitMapper;
import com.zjezyy.service.BaseInfoService;
import com.zjezyy.utils.RedisUtil;

@Service
public class BaseInfoServiceImpl implements BaseInfoService {
	
	@Autowired
	RedisUtil redisUtil;
	
	@Autowired
	TbUnitMapper tbUnitMapper;
	
	@Autowired
	TbProducerMapper tbProducerMapper;
	
	@Value("${redies.timetout}")
	private int redisTime;

	@Override
	public TbUnit getTbUnitByID(int iunitid) {

		String key = TbUnit.Prefix_Redis_Key + TbUnit.Prefix_Redis_Key_Separtor + iunitid;
		TbUnit tbUnit = (TbUnit) redisUtil.get(key);
		
		if (tbUnit == null) {
			tbUnit = tbUnitMapper.getOne(iunitid);
			redisUtil.set(key, tbUnit, redisTime);
			// redisTemplate.opsForValue().set(key, tbProductinfo);
			// redisTemplate.expire(key, productRedisTime, TimeUnit.SECONDS);
		}
		return tbUnit;
	
	}
	
	@Override
	public TbProducer getTbProducerByID(int iproducerid) {

		String key = TbProducer.Prefix_Redis_Key + TbProducer.Prefix_Redis_Key_Separtor + iproducerid;
		TbProducer tbProducer = (TbProducer) redisUtil.get(key);
		
		if (tbProducer == null) {
			tbProducer = tbProducerMapper.getOne(iproducerid);
			redisUtil.set(key, tbProducer, redisTime);
			// redisTemplate.opsForValue().set(key, tbProductinfo);
			// redisTemplate.expire(key, productRedisTime, TimeUnit.SECONDS);
		}
		return tbProducer;
	
	}

}
