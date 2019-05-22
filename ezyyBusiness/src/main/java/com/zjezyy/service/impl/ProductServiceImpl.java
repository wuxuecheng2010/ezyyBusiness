package com.zjezyy.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.zjezyy.entity.b2b.MccProduct;
import com.zjezyy.entity.b2b.MccProduct_Eo;
import com.zjezyy.entity.b2b.MccTbCustomerKind;
import com.zjezyy.entity.b2b.MccTbCustomerKindPrice;
import com.zjezyy.entity.erp.TbCustomer;
import com.zjezyy.entity.erp.TbCustomerKindList;
import com.zjezyy.entity.erp.TbCustomerKindPrice;
import com.zjezyy.entity.erp.TbProductinfo;
import com.zjezyy.entity.erp.TbProductinfo_Eo;
import com.zjezyy.entity.erp.TbStockControl;
import com.zjezyy.entity.erp.TbStocks;
import com.zjezyy.enums.ExceptionEnum;
import com.zjezyy.enums.EzyySettingKey;
import com.zjezyy.exception.BusinessException;
import com.zjezyy.mapper.b2b.MccProductMapper;
import com.zjezyy.mapper.b2b.MccTbCustomerKindMapper;
import com.zjezyy.mapper.b2b.MccTbCustomerKindPriceMapper;
import com.zjezyy.mapper.erp.TbCustomerKindListMapper;
import com.zjezyy.mapper.erp.TbCustomerKindPriceMapper;
import com.zjezyy.mapper.erp.TbProductinfoMapper;
import com.zjezyy.mapper.erp.TbStockControlMapper;
import com.zjezyy.mapper.erp.TbStocksMapper;
import com.zjezyy.service.ProductService;
import com.zjezyy.service.SettingService;
import com.zjezyy.utils.DateUtils;
import com.zjezyy.utils.RedisUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

	@Autowired
	MccProductMapper mccProductMapper;
	@Autowired
	TbProductinfoMapper tbProductinfoMapper;
	
	@Autowired
	MccTbCustomerKindMapper mccTbCustomerKindMapper;
	
	@Autowired
	TbCustomerKindListMapper tbCustomerKindListMapper;
	
	@Autowired
	TbCustomerKindPriceMapper tbCustomerKindPriceMapper;
	
	@Autowired
	MccTbCustomerKindPriceMapper mccTbCustomerKindPriceMapper;
	
	@Autowired
	SettingService SettingServiceImpl;

	// @Autowired
	// private RedisTemplate redisTemplate;

	@Autowired
	RedisUtil redisUtil;

	@Autowired
	TbStockControlMapper tbStockControlMapper;

	@Autowired
	TbStocksMapper tbStocksMapper;

	@Value("${b2b.product.price.icustomerkindid}")
	private Integer icustomerkindid;

	/*@Value("${b2b.product.price.pricetype}")
	private String pricetype;*/

	@Value("${product.redies.timetout}")
	private int productRedisTime;

	@Override
	public TbProductinfo_Eo getTbProductinfoEoByMccProductId(int product_id) throws RuntimeException {
		MccProduct_Eo mccProduct_Eo = mccProductMapper.getOne(product_id, 1);
		Integer erpiproductid = mccProduct_Eo.getErpiproductid();
		if (erpiproductid == null) {
			throw new BusinessException(ExceptionEnum.B2B_PRODUCT_UNRELATED);
		}
		TbProductinfo_Eo tbProductinfo_Eo = tbProductinfoMapper.getOneEo(erpiproductid, icustomerkindid);
		if (tbProductinfo_Eo == null) {
			throw new BusinessException(ExceptionEnum.B2B_PRODUCT_RELATED_ERROR);
		}
		// 判断价格是否一致性 不一致 报自定义异常
		BigDecimal erp_price = getERPProductPriceByTbProductinfoEo(tbProductinfo_Eo);// tbProductinfo_Eo.getNumguidprice();
		BigDecimal b2b_price = mccProduct_Eo.getPrice();
		if (erp_price.compareTo(b2b_price) != 0) {
			throw new BusinessException(ExceptionEnum.B2B_PRODUCT_PRICE_TIMEOUT);
		}

		return tbProductinfo_Eo;
	}

	@Override
	public BigDecimal getERPProductPriceByTbProductinfoEo(TbProductinfo_Eo tbProductinfoEo) throws RuntimeException {
		BigDecimal price = BigDecimal.ZERO;
		String pricetype=SettingServiceImpl.getEzyySettingValue(EzyySettingKey.PRODUCT_PRICE_CUSTOMER_FIELD);
		if (tbProductinfoEo != null) {
			switch (pricetype) {
			case "numprice":
				price = tbProductinfoEo.getNumprice();
				break;
			case "numlowprice":
				price = tbProductinfoEo.getNumlowprice();
				break;
			case "numassesscost":
				price = tbProductinfoEo.getNumassesscost();
				break;
			case "numguidprice":
				price = tbProductinfoEo.getNumguidprice();
				break;

			default:
				price = tbProductinfoEo.getNumguidprice();
				break;
			}
		}
		if (price.compareTo(new BigDecimal(0)) == 0 || price.compareTo(new BigDecimal(0)) == -1) {
			throw new BusinessException(ExceptionEnum.ERP_PRODUCT_PRICE_LQ_ZERO);
		}
		return price;
	}

	@Override
	public TbProductinfo getTbProductinfoById(int iproductid) {
		// TbProductinfo tbProductinfo=tbProductinfoMapper.getOne(iproductid);
		String key = TbProductinfo.Prefix_Redis_Key + TbProductinfo.Prefix_Redis_Key_Separtor + iproductid;
		// TbProductinfo tbProductinfo = (TbProductinfo)
		// redisTemplate.opsForValue().get(key);
		TbProductinfo tbProductinfo = (TbProductinfo) redisUtil.get(key);
		if (tbProductinfo == null) {
			tbProductinfo = tbProductinfoMapper.getOne(iproductid);
			redisUtil.set(key, tbProductinfo, productRedisTime);
			// redisTemplate.opsForValue().set(key, tbProductinfo);
			// redisTemplate.expire(key, productRedisTime, TimeUnit.SECONDS);
		}
		return tbProductinfo;
	}

	/**
	 * 判断商品是否近效期
	 */
	@Override
	public boolean isNearEffective(TbProductinfo tbProductinfo, String usefullife) {
		int numwarningdays = tbProductinfo.getNumwarningdays();// 警示天数
		boolean flag = false;

		try {
			Date today = new Date();
			String todaystr = DateUtils.dateFormat(today, DateUtils.DATE_PATTERN);
			today = DateUtils.dateParse(todaystr, DateUtils.DATE_PATTERN);
			Date dateGrant = DateUtils.dateAddDays(today, numwarningdays);// 假设今天+警示的天数
			Date usefullifeDate = DateUtils.dateParse(usefullife, DateUtils.DATE_PATTERN);// 有效期天数
			if (dateGrant.compareTo(usefullifeDate) >= 0) {
				flag = true;
			}

		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}

		return flag;
	}

	/**
	 * 判断商品是否近效期
	 */
	@Override
	public boolean isNearEffective(TbProductinfo tbProductinfo, TbStocks tbStocks) {
		// int numwarningdays=tbProductinfo.getNumwarningdays();//警示天数
		String usefullife = tbStocks.getDtusefullife();
		return isNearEffective(tbProductinfo, usefullife);
	}

	/**
	 * 判断商品是否低处
	 */
	@Override
	public boolean isLowStorage(int iproductid) {
		TbStockControl tbStockControl = tbStockControlMapper.getOne(iproductid);
		BigDecimal numopen = tbStockControl.getNumopen();
		return numopen.compareTo(BigDecimal.ZERO) >= 0;
	}

	@Override
	public List<MccProduct> getAllMccProductID() {
		// TbProductinfo tbProductinfo=tbProductinfoMapper.getOne(iproductid);
		String key = MccProduct.Prefix_Redis_Key + MccProduct.Prefix_Redis_Key_Separtor + "ALL";
		List<MccProduct> list = redisUtil.lGet(key, 0, -1);
		if (list == null || list.size() == 0) {
			list = mccProductMapper.getAll();
			redisUtil.lSet(key, list, productRedisTime);
		}
		return list;
	}

	@Override
	public void doSynchronizeOnOff(MccProduct mccProduct) throws RuntimeException {
		// 获取低储数据
		BigDecimal numopen = new BigDecimal("-1");// 默认是不控制的
		TbStockControl tbStockControl = tbStockControlMapper.getOne(mccProduct.getErpiproductid());
		if (tbStockControl != null)
			numopen = tbStockControl.getNumopen();// >=0算低储 -1不算

		// 获取ERP价格集合信息
		TbProductinfo_Eo tbProductinfo_Eo = tbProductinfoMapper.getOneEo(mccProduct.getErpiproductid(),
				icustomerkindid);

		// 获取b2b商品信息状态
		int status = mccProduct.getStatus();// 0 下架 1上架
		int x = numopen.compareTo(new BigDecimal("-1")) == 0 ? 1 : 0;

		String info = "";
		if (status != x) {// 低储因素有变化

			if (x == 0) {// 下架操作
				// 如果原本是上架状态
				if (status == 1) {
					mccProductMapper.setMccProductOnOff(mccProduct.getProduct_id(), 0);
					info = String.format("B2B商品ID：%d,%s 因低储下架", mccProduct.getProduct_id(), status == 1 ? "上架" : "下架");
				}

			} else {
				// 上架操作 判断价格集合是否维护 确定是否上架

				// 没维护价格
				if (!existB2bPrice(tbProductinfo_Eo)) {
					// 下架处理
					if (status == 1) {
						mccProductMapper.setMccProductOnOff(mccProduct.getProduct_id(), 0);
						info = String.format("B2B商品ID：%d, 因没有维护B2B价格下架", mccProduct.getProduct_id());
					}
				} else {
					// 如果原本是下架的
					if (status == 0) {
						mccProductMapper.setMccProductOnOff(mccProduct.getProduct_id(), 1);
						info = String.format("B2B商品ID：%d, 上架", mccProduct.getProduct_id());
					}

				}
			}

		} else {// 低储因素没有变化
				// 只管在线状态 如果是下线状态 不管价格有没有变
			if (status == 1) {
				// 判断价格是否在 如果在不管 不在就下线
				if (!existB2bPrice(tbProductinfo_Eo)) {
					// 下架处理
					mccProductMapper.setMccProductOnOff(mccProduct.getProduct_id(), 0);
					info = String.format("B2B商品ID：%d, 因未维护价格下架", mccProduct.getProduct_id());
				}
			}
		}
		if (!"".equals(info))
			log.info(info);

	}

	// 判断是否存在B2b价格集合信息
	private boolean existB2bPrice(TbProductinfo_Eo tbProductinfo_Eo) {
		boolean flag = !(tbProductinfo_Eo == null // 记录不存在
				|| getERPProductPriceByTbProductinfoEo(tbProductinfo_Eo) == null// 价格没有维护
				|| getERPProductPriceByTbProductinfoEo(tbProductinfo_Eo).compareTo(BigDecimal.ZERO) <= 0); // 价格<=0
		return flag;
	}

	@Override
	public void doSynchronizePrice(MccProduct mccProduct) throws RuntimeException {
		// 获取ERP价格集合信息
		TbProductinfo_Eo tbProductinfo_Eo = tbProductinfoMapper.getOneEo(mccProduct.getErpiproductid(),
				icustomerkindid);
		BigDecimal mcc_price = mccProduct.getPrice();
		BigDecimal erp_price = getERPProductPriceByTbProductinfoEo(tbProductinfo_Eo);
		String info = "";
		if (existB2bPrice(tbProductinfo_Eo)) {
			if (erp_price.compareTo(mcc_price) != 0) {
				updateMccProductPrice(mccProduct, tbProductinfo_Eo);
				info = String.format("B2B商品ID：%d, 价格%f-->%f ", mccProduct.getProduct_id(), mcc_price, erp_price);
			}
		} else {
			// 下架
			if (mccProduct.getStatus() == 1) {
				mccProductMapper.setMccProductOnOff(mccProduct.getProduct_id(), 0);
				info = String.format("B2B商品ID：%d, 因没有维护B2B价格下架", mccProduct.getProduct_id());
			}

		}
		if (!"".equals(info))
			log.info(info);

	}

	@Override
	public void updateMccProductPrice(MccProduct mccProduct, TbProductinfo_Eo tbProductinfo_Eo)
			throws RuntimeException {

		BigDecimal price = getERPProductPriceByTbProductinfoEo(tbProductinfo_Eo);
		mccProductMapper.setMccProductPrice(mccProduct.getProduct_id(), price);

	}

	@Override
	public List<TbProductinfo> getProductsForB2B() throws RuntimeException {
		return tbProductinfoMapper.getProductListForB2B(icustomerkindid);
	}

	@Override
	public void setTbProductinfoIydstate(TbProductinfo tbProductinfo) {
		tbProductinfoMapper.updateTbProductinfoIydstate(tbProductinfo.getIproductid());
	}

	@Override
	public List<MccProduct> getAllB2BOnProduct() throws RuntimeException {
		return mccProductMapper.getAllOnProduct();
	}

	@Override
	public void doSynchronizeStock(MccProduct mccProduct) throws RuntimeException {
		// 1、获取b2b库存
		BigDecimal b2bqty = mccProduct.getQuantity();
		// 2、获取erp库存
		int iproductid = mccProduct.getErpiproductid();
		List<TbStocks> list = tbStocksMapper.getListByIproductid(iproductid);
		BigDecimal erpqty = getTbProductStocks(list);
		// 3、同步处理
		if (b2bqty.compareTo(erpqty) != 0) {
			updateMccProductQuantity(mccProduct, erpqty);
			String info = String.format("B2B商品ID：%d, 库存%f-->%f ", mccProduct.getProduct_id(), mccProduct.getQuantity(),
					erpqty);
			log.info(info);
		}

	}

	@Override
	public BigDecimal getTbProductStocks(List<TbStocks> list) throws RuntimeException {
		BigDecimal total = BigDecimal.ZERO;
		if (list == null || list.size() == 0)
			return total;
		for (TbStocks tbStocks : list)
			total = total.add(tbStocks.getNumquantity());
		return total;
	}

	@Override
	public void updateMccProductQuantity(MccProduct mccProduct, BigDecimal erpqty) throws RuntimeException {
		mccProductMapper.setMccProductQuantity(mccProduct.getProduct_id(), erpqty);
	}

	
	//根据b2b中存在的集合  更新集合价格明细
	@Override
	public void doSynchronizeCustomerKindPrice() throws RuntimeException {
		
		//获取价格集合列表
		List<MccTbCustomerKind> list =mccTbCustomerKindMapper.getAllList();
		
		//循环获取价格
		for(MccTbCustomerKind mccTbCustomerKind:list) {
			
			Integer icustomerkindid=mccTbCustomerKind.getIcustomerkindid();
			List<TbCustomerKindPrice> erppicelist= tbCustomerKindPriceMapper.getListByKindID(icustomerkindid);
			
			//检查b2b 是否有该集合   
			       for(TbCustomerKindPrice tbCustomerKindPrice:erppicelist) {
			    	  Integer isid= tbCustomerKindPrice.getIsid();
			    	  MccTbCustomerKindPrice mccTbCustomerKindPrice=mccTbCustomerKindPriceMapper.getOne(isid);
			    	  if(mccTbCustomerKindPrice==null) {
			    		  //没有就插入
			    		  mccTbCustomerKindPrice=new MccTbCustomerKindPrice(tbCustomerKindPrice);
			    		  mccTbCustomerKindPriceMapper.insert(mccTbCustomerKindPrice);
			    	  }else {
			    		  //有就更新
			    		  mccTbCustomerKindPriceMapper.updateByTbCustomerKindPrice(tbCustomerKindPrice);
			    	  }
			       }
			       
		}
			
		
	}

	@Override
	public BigDecimal getERPProductCustomerPriceByTbProductinfoEoAndTbCustomer(TbProductinfo_Eo tbProductinfoEo,
			TbCustomer tbCustomer) throws RuntimeException {
		BigDecimal price = BigDecimal.ZERO;
		String pricetype=SettingServiceImpl.getEzyySettingValue(EzyySettingKey.PRODUCT_PRICE_CUSTOMER_FIELD);
		//获取客户所在集合
		TbCustomerKindList tbCustomerKindList=tbCustomerKindListMapper.getOne(tbCustomer.getIcustomerid());
		//获取商品集合价格
		if(tbCustomerKindList!=null) {
			Integer icustomerkindid=tbCustomerKindList.getIcustomerkindid();
			Integer iproductid=tbProductinfoEo.getIproductid();
			TbCustomerKindPrice tbCustomerKindPrice=tbCustomerKindPriceMapper.getOne(iproductid, icustomerkindid);

			if (tbCustomerKindPrice != null) {
				switch (pricetype) {
				case "numprice":
					price = tbCustomerKindPrice.getNumprice();
					break;
				case "numlowprice":
					price = tbCustomerKindPrice.getNumlowprice();
					break;
				case "numassesscost":
					price = tbCustomerKindPrice.getNumassesscost();
					break;
				case "numguidprice":
					price = tbCustomerKindPrice.getNumguidprice();
					break;

				default:
					price = tbCustomerKindPrice.getNumguidprice();
					break;
				}
			}
			if (price.compareTo(new BigDecimal(0)) == 0 || price.compareTo(new BigDecimal(0)) == -1) {
				throw new BusinessException(tbProductinfoEo.toString(),ExceptionEnum.ERP_PRODUCT_PRICE_LQ_ZERO);
			}

		}
		return price;
	}

	@Override
	public BigDecimal getERPProductPrice(TbProductinfo_Eo tbProductinfoEo,TbCustomer tbCustomer) throws RuntimeException {
		BigDecimal price=BigDecimal.ZERO;
		//获取价格模式
		String pricemodel=SettingServiceImpl.getEzyySettingValue(EzyySettingKey.PRODUCT_PRICE_CUSTOMER_MODEL);;
		
		if(pricemodel==null||"".equals(pricemodel)||"0".equals(pricemodel)) {
			price=getERPProductPriceByTbProductinfoEo( tbProductinfoEo);
		}else {
			price=getERPProductCustomerPriceByTbProductinfoEoAndTbCustomer( tbProductinfoEo,
					 tbCustomer);
		}
		if (price.compareTo(new BigDecimal(0)) == 0 || price.compareTo(new BigDecimal(0)) == -1) {
			throw new BusinessException(tbProductinfoEo.toString(),ExceptionEnum.ERP_PRODUCT_PRICE_LQ_ZERO);
		}
		
		return price;
	}

}
