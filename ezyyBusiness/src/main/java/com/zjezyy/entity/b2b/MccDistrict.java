package com.zjezyy.entity.b2b;

import lombok.Data;

@Data
public class MccDistrict {
	private Integer district_id;
	private Integer country_id;
	private Integer zone_id;
	private Integer city_id;
	private String name;
	private Integer status;
}
