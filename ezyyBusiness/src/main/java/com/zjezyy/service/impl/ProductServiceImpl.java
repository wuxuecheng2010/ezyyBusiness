package com.zjezyy.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.zjezyy.entity.b2b.MccProduct;
import com.zjezyy.entity.b2b.MccProductDescription;
import com.zjezyy.entity.b2b.MccProductToCategory;
import com.zjezyy.entity.b2b.MccProductToLayout;
import com.zjezyy.entity.b2b.MccProductToStore;
import com.zjezyy.entity.b2b.MccProduct_Eo;
import com.zjezyy.entity.b2b.MccTbCustomerKind;
import com.zjezyy.entity.b2b.MccTbCustomerKindPrice;
import com.zjezyy.entity.erp.TbCustomer;
import com.zjezyy.entity.erp.TbCustomerKind;
import com.zjezyy.entity.erp.TbCustomerKindList;
import com.zjezyy.entity.erp.TbCustomerKindPrice;
import com.zjezyy.entity.erp.TbProductPacking;
import com.zjezyy.entity.erp.TbProductinfo;
import com.zjezyy.entity.erp.TbProductinfo_Eo;
import com.zjezyy.entity.erp.TbStockControl;
import com.zjezyy.entity.erp.TbStocks;
import com.zjezyy.enums.ExceptionEnum;
import com.zjezyy.enums.EzyySettingKey;
import com.zjezyy.enums.LanguageEnum;
import com.zjezyy.exception.BusinessException;
import com.zjezyy.mapper.b2b.MccProductDescriptionMapper;
import com.zjezyy.mapper.b2b.MccProductMapper;
import com.zjezyy.mapper.b2b.MccProductToCategoryMapper;
import com.zjezyy.mapper.b2b.MccProductToLayoutMapper;
import com.zjezyy.mapper.b2b.MccProductToStoreMapper;
import com.zjezyy.mapper.b2b.MccTbCustomerKindMapper;
import com.zjezyy.mapper.b2b.MccTbCustomerKindPriceMapper;
import com.zjezyy.mapper.erp.TbCustomerKindListMapper;
import com.zjezyy.mapper.erp.TbCustomerKindMapper;
import com.zjezyy.mapper.erp.TbCustomerKindPriceMapper;
import com.zjezyy.mapper.erp.TbProductPackingMapper;
import com.zjezyy.mapper.erp.TbProductinfoMapper;
import com.zjezyy.mapper.erp.TbStockControlMapper;
import com.zjezyy.mapper.erp.TbStocksMapper;
import com.zjezyy.service.BaseInfoService;
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
	TbCustomerKindMapper tbCustomerKindMapper;
	
	@Autowired
	TbCustomerKindListMapper tbCustomerKindListMapper;
	
	@Autowired
	TbCustomerKindPriceMapper tbCustomerKindPriceMapper;
	
	@Autowired
	MccTbCustomerKindPriceMapper mccTbCustomerKindPriceMapper;
	
	@Autowired
	MccProductDescriptionMapper mccProductDescriptionMapper;
	@Autowired
	MccProductToStoreMapper mccProductToStoreMapper;
	@Autowired
	MccProductToCategoryMapper mccProductToCategoryMapper;
	
	@Autowired
	TbProductPackingMapper tbProductPackingMapper;
	@Autowired
	MccProductToLayoutMapper mccProductToLayoutMapper;
	
	@Autowired
	BaseInfoService baseInfoServiceImpl;
	
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

	@Value("${redies.timetout}")
	private int productRedisTime;
	
	@Value("${erp.product.to.b2b.state}")
	private int productToB2bState;
	

	
	

	@Override
	public TbProductinfo_Eo getTbProductinfoEoByMccProductIDAndTBCustomer(int product_id,TbCustomer tbCustomer) throws RuntimeException {
		//获取B2B商品信息  语言为简体中文
		MccProduct_Eo mccProduct_Eo = mccProductMapper.getOne(product_id, 1);
		Integer erpiproductid = mccProduct_Eo.getErpiproductid();
		if (erpiproductid == null) {
			throw new BusinessException(String.format("商品ID:product_id:%d", product_id),ExceptionEnum.B2B_PRODUCT_UNRELATED);
		}
		
		TbProductinfo_Eo tbProductinfo_Eo = getTbProductinfoEoByIproductIDAndTBCustomer(tbCustomer, erpiproductid);
		
		if (tbProductinfo_Eo == null) {
			throw new BusinessException(String.format("B2B商品ID:product_id:%d", product_id),ExceptionEnum.B2B_PRODUCT_NOT_INCLUDED_BY_THIS_CUSTOMERKIND);
		}
		/*// 判断价格是否一致性 不一致 报自定义异常
		BigDecimal erp_price = getERPProductPriceByTbProductinfoEo(tbProductinfo_Eo);// tbProductinfo_Eo.getNumguidprice();
		
		BigDecimal b2b_price = mccProduct_Eo.getPrice();
		if (erp_price.compareTo(b2b_price) != 0) {
			throw new BusinessException(String.format("商品ID:product_id:%d", product_id),ExceptionEnum.B2B_PRODUCT_PRICE_TIMEOUT);
		}*/

		return tbProductinfo_Eo;
	}

	/**
	* @Title: getTbProductinfoEoByIproductIDAndTBCustomer
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @param @param tbCustomer
	* @param @param erpiproductid
	* @param @return
	* @param @throws RuntimeException    参数
	* @author wuxuecheng
	* @return TbProductinfo_Eo    返回类型
	* @throws
	*/
	public TbProductinfo_Eo getTbProductinfoEoByIproductIDAndTBCustomer(TbCustomer tbCustomer, Integer erpiproductid)
			throws RuntimeException {
		//根据价格模式   B2B统一价  和  客户集合价
		//获取价格模式
		String pricemodel=SettingServiceImpl.getEzyySettingValue(EzyySettingKey.PRODUCT_PRICE_CUSTOMER_MODEL);
		TbProductinfo_Eo tbProductinfo_Eo=null;
		if(pricemodel==null||"".equals(pricemodel)||"0".equals(pricemodel)) {
			 tbProductinfo_Eo = tbProductinfoMapper.getOneEo(erpiproductid, icustomerkindid);
		}else {
			 //客户价格体系的价格
			int myicustomerkindid=tbCustomer.getIcustomerkindid();
			
			//先去特殊集合找是否有这个客户  没有就直接用默认的客户集合
			TbCustomerKindList tbCustomerKindList=tbCustomerKindListMapper.getOne(tbCustomer.getIcustomerid());
			if(tbCustomerKindList==null) {
				tbProductinfo_Eo = tbProductinfoMapper.getOneEo(erpiproductid, myicustomerkindid);
			}else {
				//特殊集合
				int _myicustomerkindid=tbCustomerKindList.getIcustomerkindid();
				TbProductinfo_Eo _tbProductinfo_Eo=tbProductinfoMapper.getOneEo(erpiproductid, _myicustomerkindid);
				if(_tbProductinfo_Eo==null) {//没找到 就去默认的找
					tbProductinfo_Eo = tbProductinfoMapper.getOneEo(erpiproductid, myicustomerkindid);
				}else {
					//找到了就赋值
					tbProductinfo_Eo=_tbProductinfo_Eo;
				}
			}
			
			
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
	public void setTbProductinfoIydstate(TbProductinfo tbProductinfo,int iydstate) {
		tbProductinfoMapper.updateTbProductinfoIydstate(tbProductinfo.getIproductid(),iydstate);
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
			
			//ERP====>B2B 更新
			Integer icustomerkindid=mccTbCustomerKind.getIcustomerkindid();
			List<TbCustomerKindPrice> erppicelist= tbCustomerKindPriceMapper.getListByKindID(icustomerkindid);
			
			//检查b2b 是否有该集合   
			       for(TbCustomerKindPrice tbCustomerKindPrice:erppicelist) {
			    	  try {
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
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			       }
			       
			// B2B====>ERP 更新      
			       //获取B2B价格集合信息
			       List<MccTbCustomerKindPrice> b2bpicelist= mccTbCustomerKindPriceMapper.getListByKindID(icustomerkindid);
			       
			       for(MccTbCustomerKindPrice mccTbCustomerKindPrice:b2bpicelist) {
			    	 try {
						//获取ERP价格集合信息 //如果没有则删除B2b的价格集合  
						   TbCustomerKindPrice tbCustomerKindPrice= tbCustomerKindPriceMapper.getOneByISID(mccTbCustomerKindPrice.getIsid());
						   if(tbCustomerKindPrice==null)
							   mccTbCustomerKindPriceMapper.deleteByISID(mccTbCustomerKindPrice.getIsid());
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			       }
		}
			
		
	}
	
	
	

	@Override
	public BigDecimal getERPProductCustomerPriceByTbProductinfoEoAndTbCustomer(TbProductinfo_Eo tbProductinfoEo,
			TbCustomer tbCustomer) throws RuntimeException {
		BigDecimal price = BigDecimal.ZERO;
		String pricetype=SettingServiceImpl.getEzyySettingValue(EzyySettingKey.PRODUCT_PRICE_CUSTOMER_FIELD);
		
		//第一种 、获取客户信息中标志的价格集合
		TbCustomerKindPrice tbCustomerKindPrice=
				tbCustomerKindPriceMapper.getOneByKindIDAndIcustomerID( tbProductinfoEo.getIproductid(), tbCustomer.getIcustomerid());
		price = picktbCustomerKindPricePrice( pricetype, tbCustomerKindPrice);
		if(price.compareTo(BigDecimal.ZERO)<=0) {
			//如果没有价格
			//第二种、根据特殊的价格集合取价格
			//获取客户所在集合
			TbCustomerKindList tbCustomerKindList=tbCustomerKindListMapper.getOne(tbCustomer.getIcustomerid());
			//获取商品集合价格
			if(tbCustomerKindList!=null) {
				Integer icustomerkindid=tbCustomerKindList.getIcustomerkindid();
				Integer iproductid=tbProductinfoEo.getIproductid();
				tbCustomerKindPrice=tbCustomerKindPriceMapper.getOne(iproductid, icustomerkindid);
				price = picktbCustomerKindPricePrice( pricetype, tbCustomerKindPrice);
				
			}
		}	
				
		if (price.compareTo(new BigDecimal(0)) == 0 || price.compareTo(new BigDecimal(0)) == -1) {
			throw new BusinessException(tbProductinfoEo.toString(),ExceptionEnum.ERP_PRODUCT_PRICE_LQ_ZERO);
		}
		
		return price;
	}

	private BigDecimal picktbCustomerKindPricePrice( String pricetype,
			TbCustomerKindPrice tbCustomerKindPrice) {
		BigDecimal price=BigDecimal.ZERO; 
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
		return price;
	}

	@Override
	public BigDecimal getERPProductPrice(TbProductinfo_Eo tbProductinfoEo,TbCustomer tbCustomer) throws RuntimeException {
		BigDecimal price=BigDecimal.ZERO;
		//获取价格模式
		String pricemodel=SettingServiceImpl.getEzyySettingValue(EzyySettingKey.PRODUCT_PRICE_CUSTOMER_MODEL);
		
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

	//跟新ERP商品信息到B2B
	@Override
	public void doSynchronizeProductInfo(int iproductid,int store_id,int layout_id) throws RuntimeException {
		//获取商品信息
		TbProductinfo tbProductinfo=tbProductinfoMapper.getOne(iproductid);
		//获取商品的包装信息
		TbProductPacking  tbProductPacking=tbProductPackingMapper.getOne(iproductid);
		//检查信息是否存在  存在则更新  不存在则新增
		MccProduct mccProduct=mccProductMapper.getOneByErpIproductid(iproductid);
		if(mccProduct==null ) {
			makeTbProductinfoToMccProduct(tbProductinfo,store_id,layout_id,tbProductPacking);
		}else {
			//log.info("####iproductid:%d"+iproductid);
			updateMccProductByTbProductinfo(tbProductinfo);
		}
		
	}

/*	$this->insert_mcc_product($IPRODUCTID,$VCSTANDARD,$VCPRODUCERNAME,$ERPIPRODUCTID,$VCUNITNAME);
    $this->insert_mcc_product_description_1($IPRODUCTID,$VCUNIVERSALNAME);
    $this->insert_mcc_product_description_2($IPRODUCTID,$VCUNIVERSALNAME);
    $this->insert_mcc_product_description_3($IPRODUCTID,$VCUNIVERSALNAME);
    $this->insert_mcc_product_to_store($IPRODUCTID);
    $this->insert_mcc_product_to_category($IPRODUCTID,$IPRODUCTKINDID);
    $this->insert_mcc_product_to_layout($IPRODUCTID);
    $this->insert_mcc_url_alias($IPRODUCTID);*/
	@Transactional
	@Override
	public void makeTbProductinfoToMccProduct(TbProductinfo tbProductinfo,int store_id,int layout_id,TbProductPacking tbProductPacking) throws RuntimeException {
		//1、保存 mcc_product
		int nummiddle=getProductNumMiddle(tbProductPacking);
		MccProduct mccProduct=new MccProduct(tbProductinfo.getVcstandard(), 
				baseInfoServiceImpl.getTbUnitByID(tbProductinfo.getIproductunitid()).getVcunitname(), 
				baseInfoServiceImpl.getTbProducerByID(tbProductinfo.getIproducerid()).getVcproducername(), 
				tbProductinfo.getIproductid(),nummiddle);
		mccProductMapper.insert(mccProduct);
		
		//2、保存mcc_product_description 多语言都保存一次
		MccProductDescription mccProductDescription=new MccProductDescription(mccProduct.getProduct_id(),LanguageEnum.zh_cn, tbProductinfo);
		mccProductDescriptionMapper.insert(mccProductDescription);
							  mccProductDescription=new MccProductDescription(mccProduct.getProduct_id(),LanguageEnum.en_gb, tbProductinfo);
		mccProductDescriptionMapper.insert(mccProductDescription);
							  mccProductDescription=new MccProductDescription(mccProduct.getProduct_id(),LanguageEnum.zh_hk, tbProductinfo);
		mccProductDescriptionMapper.insert(mccProductDescription);
		
		//3、store
		MccProductToStore mccProductToStore=new MccProductToStore(mccProduct.getProduct_id(),store_id);
		mccProductToStoreMapper.insert(mccProductToStore);
		
		//4、category
		MccProductToCategory mccProductToCategory=new MccProductToCategory(mccProduct.getProduct_id(),tbProductinfo.getIproductkindid());
		mccProductToCategoryMapper.insert(mccProductToCategory);
		
		//5、layout
		MccProductToLayout mccProductToLayout=new MccProductToLayout(mccProduct.getProduct_id(),store_id,layout_id);
		mccProductToLayoutMapper.insert(mccProductToLayout);
		
		//6 mcc_url_alias
		
		//7、 修改ERP商品状态
		this.setTbProductinfoIydstate(tbProductinfo, productToB2bState);
		String msg=String.format("商品ID：%d 商品通用名：%s 规格：%s,成功加入到B2B", tbProductinfo.getIproductid(),tbProductinfo.getVcuniversalname(),tbProductinfo.getVcstandard());
		log.info(msg);
		
	}

	@Override
	public  Integer getProductNumMiddle(TbProductPacking tbProductPacking) {
		//获取小包装  如果小包装不为空   就返回小包装   
		//如果没小包装  就获取中包装   
		//如果中包装也没有  就直接返回1
		Integer nummiddle=1;
		if(tbProductPacking==null) {
			nummiddle=1;
		}else {
			Integer numsmall=tbProductPacking.getNumsmall();
			if(numsmall!=null && numsmall>=1) {
				nummiddle=numsmall;
			}else {
				nummiddle=tbProductPacking.getNummiddle();
				if(nummiddle==null || nummiddle==0)
					nummiddle=1;
			}
				
			
		}
		return nummiddle;
	}

	@Override
	public void updateMccProductByTbProductinfo(TbProductinfo tbProductinfo) throws RuntimeException {
		
		Integer iydstate=tbProductinfo.getIydstate();
		if(iydstate==null || iydstate!=2)
			//7、 修改ERP商品状态
			this.setTbProductinfoIydstate(tbProductinfo, productToB2bState);
		//其他的更新  比如名称规格等  此次不管
	}

	@Override
	public List<TbProductinfo> getProductsForPM() throws RuntimeException {
		return tbProductinfoMapper.getProductListForYD();
	}

	@Override
	public void doSynchronizeCustomerKind() throws RuntimeException {
		
		//获取集合列表(这里只处理基础的，特殊的由新增客户的功能中添加)
		List<TbCustomerKind> list =tbCustomerKindMapper.getBaseList();
		
		//循环获取价格
		for(TbCustomerKind tbCustomerKind:list) {
			int icustomerkindid=tbCustomerKind.getIcustomerkindid();
			//获取B2b上面的集合
			try {
				MccTbCustomerKind mccTbCustomerKind=mccTbCustomerKindMapper.getOne(icustomerkindid);
				if(mccTbCustomerKind==null) {
					//保存价格集合到B2B
					mccTbCustomerKind=new MccTbCustomerKind(tbCustomerKind);
					mccTbCustomerKindMapper.insert(mccTbCustomerKind);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
			
		
	}

}
