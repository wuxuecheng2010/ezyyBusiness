package com.zjezyy.service;

import com.zjezyy.entity.erp.TbProducer;
import com.zjezyy.entity.erp.TbUnit;

//基本信息类  数据比较简单的都由此类处理
public interface BaseInfoService {
	
	TbUnit getTbUnitByID(int iunitid);

	TbProducer getTbProducerByID(int iproducerid);

}
