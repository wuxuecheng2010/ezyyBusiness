package com.zjezyy.entity.b2b;

import lombok.Data;

@Data
public class MccProductToStore {
	private Integer product_id;
	private Integer store_id;
	
	public MccProductToStore(Integer product_id, Integer store_id) {
		this.product_id = product_id;
		this.store_id = store_id;
	}
	
	public MccProductToStore() {
		
	}
	
	
	
}
