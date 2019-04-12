package com.zjezyy.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.zjezyy.entity.b2b.MccSetting;
import com.zjezyy.enums.EzyySettingKey;
import com.zjezyy.mapper.b2b.MccSettingMapper;
import com.zjezyy.service.SettingService;
import com.zjezyy.utils.RedisUtil;

@Service
public class SettingServiceImpl implements SettingService{

	
	@Autowired
	MccSettingMapper mccSettingMapper;
	
	@Autowired
	RedisUtil redisUtil;
	
	@Value("${setting.ezyy.code}")
	private String setting_ezyy_code;
	
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

	@Override
	public Map<String, String> getEzyySettings() throws RuntimeException {
		String key=new StringBuilder().append(MccSetting.Prefix_Redis_Key)
				.append(MccSetting.Prefix_Redis_Key_Separtor)
				.append(setting_ezyy_code)
				.toString();
		Map<String, String> map=(Map<String, String>)redisUtil.hmget(key);
		if(map==null||map.isEmpty()) {
			List<MccSetting> list=mccSettingMapper.getSettingListByCode(setting_ezyy_code);
			map=new HashMap<String, String>();
			for(MccSetting mccSetting:list) {
				String _key=mccSetting.getKey();
				String _value=mccSetting.getValue();
				map.put(_key, _value);
			}
			redisUtil.hmset(key, map, 300);
		}
		return map;
	}
	
	@Override
	public String getEzyySettingValue(EzyySettingKey key) throws RuntimeException {
		Map<String, String> map=getEzyySettings();
		if(map!=null && map.containsKey(key.getKey())) {
			return map.get(key.getKey());
		}else {
			return "";
		}
			
	}

}
