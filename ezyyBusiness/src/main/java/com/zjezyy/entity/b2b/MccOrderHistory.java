package com.zjezyy.entity.b2b;

import lombok.Data;

@Data
public class MccOrderHistory {
	private Integer order_history_id;
	private Integer order_id;
	private Integer order_status_id;
	private String comment;
	private int notify;
	private String date_added;
}
