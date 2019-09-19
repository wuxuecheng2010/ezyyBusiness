package com.zjezyy.entity.b2b;

import lombok.Data;

@Data
public class MccAddress_Eo extends MccAddress {
	
	private String city_name;
	private String country_name;
	private String district_name;
	private String zone_name;

}
