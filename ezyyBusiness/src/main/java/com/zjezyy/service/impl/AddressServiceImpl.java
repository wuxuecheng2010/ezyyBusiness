package com.zjezyy.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zjezyy.entity.Address;
import com.zjezyy.entity.Result;
import com.zjezyy.entity.b2b.MccAddress;
import com.zjezyy.entity.b2b.MccDistrict;
import com.zjezyy.entity.erp.TbCustomer;
import com.zjezyy.enums.ExceptionEnum;
import com.zjezyy.exception.BusinessException;
import com.zjezyy.mapper.b2b.MccAddressMapper;
import com.zjezyy.mapper.b2b.MccDistrictMapper;
import com.zjezyy.service.AddressService;
import com.zjezyy.utils.HttpClientUtil;

@Service
public class AddressServiceImpl implements AddressService {

	@Value("${baidu.address.to.latlng.url}")
	private String forlatlngurl;
	
	@Value("${baidu.latlng.to.district.url}")
	private String fordistricturl;
	
	@Autowired
	MccDistrictMapper mccDistrictMapper;
	
	@Autowired
	MccAddressMapper mccAddressMapper;
	
	@Override
	public MccAddress saveMccAddress(int customer_id, TbCustomer tbCustomer) throws RuntimeException {
		//1、获取客户的送货地址
		
		//2、通过请求百度地图api 获取经纬度
		
		//3、通过经纬度请求百度地图api 获取县级信息
		
		//4、通过县级信息 获取地址的标准的省市县代码ID
		
		//5、构建地址对象并保存，返回地址ID
		
		//1
		String vcstoreaddress=tbCustomer.getVcstoreaddress();
		if(vcstoreaddress==null ||"".equals(vcstoreaddress.trim()))
			throw new BusinessException(ExceptionEnum.ERP_CUSTOMER_VCSTOREADDRESS_NOT_EXISIT);
		
		//2
		String latLngString=addressToLatLngString(vcstoreaddress);
		if("".equals(latLngString))
			throw new BusinessException(ExceptionEnum.ERP_CUSTOMER_VCSTOREADDRESS_TO_LATLNG_FAIL);
		
		//3
		Address address=latLngStringToAddress(latLngString);
		if(address==null||"".equals(address.getDistrict()))
			throw new BusinessException(ExceptionEnum.ERP_CUSTOMER_LATLNG_TO_DISTRICT_FAIL);
		String shortname=address.getDistrict().substring(0, address.getDistrict().length()-1);
		
		//4
		MccDistrict mccDistrict =mccDistrictMapper.getOnebyName(address.getDistrict() );
		if(mccDistrict==null)
			mccDistrict =mccDistrictMapper.getOnebyShortName(shortname);
		
		//5
		MccAddress mccAddress=new MccAddress( customer_id,  tbCustomer,mccDistrict,address,".");
		mccAddressMapper.insert(mccAddress);
		return mccAddress;
	}

	@Override
	public String addressToLatLngString(String address) throws RuntimeException {
		String latLngString="";
		String url=String.format(forlatlngurl, address);
		Result res=HttpClientUtil.get(url, null);
		if(res!=null && res.getStatus()==0) {
			JSONObject obj=JSON.parseObject(res.getData().toString());
			JSONObject result=obj.getJSONObject("result");
			JSONObject location=result.getJSONObject("location");
			String lng=location.getString("lng");
			String lat=location.getString("lat");
			latLngString=new StringBuilder().append(lat).append(",").append(lng).toString();
		}else {
			throw new BusinessException(res.getMsg(),ExceptionEnum.HTTP_ERROR);
		}
		return latLngString;
	}

	@Override
	public Address latLngStringToAddress(String latLngString) throws RuntimeException {
		//String district="";
		Address address=null;
		String url=String.format(fordistricturl, latLngString);
		Result res=HttpClientUtil.get(url, null);
		if(res!=null && res.getStatus()==0) {
			JSONObject obj=JSON.parseObject(res.getData().toString());
			JSONObject result=obj.getJSONObject("result");
			JSONObject addressComponent=result.getJSONObject("addressComponent");
			String province=addressComponent.getString("province");
			String city=addressComponent.getString("city");
			String district=addressComponent.getString("district");
			String street=addressComponent.getString("street");
			address=new Address(province, city, district, street);
		}else {
			throw new BusinessException(ExceptionEnum.HTTP_ERROR);
		}
		return address;
	}
	
	

}
