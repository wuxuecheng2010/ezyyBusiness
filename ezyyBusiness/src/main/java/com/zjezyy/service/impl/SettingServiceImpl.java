package com.zjezyy.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zjezyy.entity.b2b.MccSetting;
import com.zjezyy.mapper.b2b.MccSettingMapper;
import com.zjezyy.service.SettingService;
import com.zjezyy.utils.RedisUtil;

@Service
public class SettingServiceImpl implements SettingService{

	
	@Autowired
	MccSettingMapper mccSettingMapper;
	
	@Autowired
	RedisUtil redisUtil;
	
	@Override
	public MccSetting getMccSetting(String code, String keystr) throws RuntimeException {

		String key=new StringBuilder().append(MccSetting.Prefix_Redis_Key)
				.append(MccSetting.Prefix_Redis_Key_Separtor)
				.append(code)
				.append(MccSetting.Prefix_Redis_Key_Separtor)
				.append(keystr)
				.toString();
		
		MccSetting mccSetting=(MccSetting)redisUtil.get(key);
		if(mccSetting==null) {
			mccSetting=mccSettingMapper.getSettingByKey(code, keystr);
			redisUtil.set(key, mccSetting,360000);
		}
		return mccSetting;
	}

	@Override
	public String getMccSettingValue(String code, String key) throws RuntimeException {
		MccSetting mccSetting=this.getMccSetting(code, key);
		String value="";
		if(mccSetting!=null)
			value=mccSetting.getValue();
		return value;
	}

}
