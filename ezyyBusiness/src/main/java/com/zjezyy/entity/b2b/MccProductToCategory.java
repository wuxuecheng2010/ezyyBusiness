package com.zjezyy.entity.b2b;

import lombok.Data;

@Data
public class MccProductToCategory {
	private Integer product_id;
	private Integer category_id;

	public MccProductToCategory(Integer product_id, Integer category_id) {
		this.product_id = product_id;
		this.category_id = category_id;
	}

	public MccProductToCategory() {
	}
}
