package com.zjezyy.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.zjezyy.entity.erp.TbProductinfo;
import com.zjezyy.entity.erp.TbStorePositionChange;
import com.zjezyy.entity.erp.TbStorePositionChangeS;
import com.zjezyy.entity.erp2.TbMyUser;
import com.zjezyy.enums.MessageGroup;
import com.zjezyy.mapper.erp.TbProductinfoMapper;
import com.zjezyy.mapper.erp.TbStorePositionChangeMapper;
import com.zjezyy.mapper.erp.TbStorePositionChangeSMapper;
import com.zjezyy.mapper.erp2.TbMyUserMapper;
import com.zjezyy.service.StorePositionChangeService;
import com.zjezyy.utils.DateUtils;
import com.zjezyy.utils.PhoneNumberUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class StorePositionChangeServiceImpl implements StorePositionChangeService {

	@Autowired
	TbStorePositionChangeMapper tbStorePositionChangeMapper;
	
	@Autowired
	TbStorePositionChangeSMapper tbStorePositionChangeSMapper;
	
	@Autowired
	TbProductinfoMapper tbProductinfoMapper;
	
	@Autowired
	TbMyUserMapper tbMyUserMapper;
	
	@Autowired
	SystemServiceImpl systemServiceImpl;
	
	@Value("${erp.storepositionchange.timeout.notify.deftels}")
	private String defNotifyTels;
	
	/**
	 * 通知即将超时的转仓单
	 */
	@Override
	public void notifyTimeOutOrder() throws Exception{

		List<TbStorePositionChange>	list=tbStorePositionChangeMapper.getListTimerout();
		//log.info("长度{}",list.size());
		
		 for(TbStorePositionChange tbStorePositionChange:list) {
			 try {
				 notifyItem(tbStorePositionChange);
			} catch (Exception e) {
				log.error(e.getMessage());
			}
		 }
		

	}

	/**
	* @Title: notifyItem
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @param @param tbStorePositionChange
	* @param @throws Exception    参数
	* @author wuxuecheng
	* @return void    返回类型
	* @throws
	*/
	public void notifyItem(TbStorePositionChange tbStorePositionChange) throws Exception {
		int ibillid=tbStorePositionChange.getIbillid();//单据ID
		String vccreatedby=tbStorePositionChange.getVccreatedby();//创建人
		String vcbillcode=tbStorePositionChange.getVcbillcode();//单据号
		String dtcreationdate=tbStorePositionChange.getDtcreationdate();
		
		int n=getTimeOurDays(dtcreationdate,30);
		String msg=vcbillcode;
		//内容
		List<TbStorePositionChangeS> listS= tbStorePositionChangeSMapper.getListByIbillid(ibillid);
		if(listS.size()>=1) {
			int iproductid=listS.get(0).getIproductid();
			TbProductinfo tbproductinfo =tbProductinfoMapper.getOne(iproductid);
			if(n>=0)
			   msg+="包含"+tbproductinfo.getVcuniversalname()+"等"+listS.size()+"个商品,单据将在"+n+"天后过期，请抓紧时间处理。";
			else
			   msg+="包含"+tbproductinfo.getVcuniversalname()+"等"+listS.size()+"个商品,单据已过期"+n+"天，请抓紧时间处理，过期5天后不再通知了。";
		}else {
			return;
		}
		
		
		//获取电话号码:
		TbMyUser tbMyUser=tbMyUserMapper.getOne(vccreatedby);
		String username=tbMyUser.getVcusername();
		msg=username+" 创建的转仓单："+msg;
		
		String vctel=tbMyUser.getVctel();
		if(PhoneNumberUtil.isPhone(vctel)) {
			//发送短信通知
			systemServiceImpl.sendTelMsg(MessageGroup.转仓单超时通知,msg, vctel);
		}else {
			if(defNotifyTels!=null) {
				String[] tellist=defNotifyTels.split(";");
				for(String tel:tellist) {
					if(PhoneNumberUtil.isPhone(tel))
						systemServiceImpl.sendTelMsg(MessageGroup.转仓单超时通知,msg, tel);
				}
			}else {
				systemServiceImpl.sendTelMsg(MessageGroup.转仓单超时通知,msg, "15990683720");
			}
			
		}
		
	}



	private int getTimeOurDays(String dtcreationdate, int i)throws Exception {
		Date datetimeout=DateUtils.dateAddDays(DateUtils.dateParse(dtcreationdate, DateUtils.DATE_TIME_PATTERN), i);
		int n=DateUtils.dateBetweenIncludeToday(new Date(), datetimeout);
		return n;
	}

}
