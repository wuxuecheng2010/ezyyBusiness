package com.zjezyy.entity.b2b;

import lombok.Data;

@Data
public class MccPayResult {
	private int result_id;
	private int order_id ;//订单编号
	private String  payment;//支付类型
	private String  busstype;//业务类型
	private String plordercode; //平台交易ID
	private String baseinfo;//支付平台相关信息组合
	private String fee;//订单金额   单位分
	private String attach ;//附加信息
	private String paydate;//支付时间
	private String tradestatus;//交易状态
	@Override
	public String toString() {
		return "MccPayResult [result_id=" + result_id + ", payment=" + payment + ", busstype=" + busstype
				+ ", order_id=" + order_id + ", plordercode=" + plordercode + ", baseinfo=" + baseinfo + ", fee=" + fee
				+ ", attach=" + attach + ", paydate=" + paydate + ", tradestatus=" + tradestatus + "]";
	}
	
	
}
