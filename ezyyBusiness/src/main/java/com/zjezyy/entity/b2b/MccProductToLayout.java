package com.zjezyy.entity.b2b;

import lombok.Data;

@Data
public class MccProductToLayout {
	private Integer product_id;
	private Integer store_id;
	private Integer layout_id;

	public MccProductToLayout(Integer product_id, Integer store_id, Integer layout_id) {
		super();
		this.product_id = product_id;
		this.store_id = store_id;
		this.layout_id = layout_id;
	}

	public MccProductToLayout() {
		super();
	}

}
