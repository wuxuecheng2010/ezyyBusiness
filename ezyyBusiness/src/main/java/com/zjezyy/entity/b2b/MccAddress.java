package com.zjezyy.entity.b2b;

import com.zjezyy.entity.Address;
import com.zjezyy.entity.erp.TbCustomer;

import lombok.Data;

@Data
public class MccAddress {
	
	private Integer address_id;
	private Integer customer_id;
	private String fullname;

	private String address;
	private String company;

	private String city;
	private String postcode;

	private Integer country_id;
	private Integer zone_id;
	private Integer city_id;
	private Integer district_id;
	private String shipping_telephone;
	private String custom_field;
	

	public MccAddress(int customer_id, TbCustomer tbCustomer, MccDistrict mccDistrict,Address address,String custom_field) {
		this.customer_id=customer_id;
		this.fullname=tbCustomer.getVclinkman();

		this.address=tbCustomer.getVcstoreaddress();
		this.company=tbCustomer.getVccustomername();
		this.city=address.getCity();
		this.postcode=tbCustomer.getVczipcode()==null||"".equals(tbCustomer.getVczipcode())?".":tbCustomer.getVczipcode();

		this.country_id=mccDistrict.getCountry_id();
		this.zone_id=mccDistrict.getZone_id();
		this.city_id=mccDistrict.getCity_id();
		this.district_id=mccDistrict.getDistrict_id();
		this.shipping_telephone=tbCustomer.getVclinkphone();
		this.custom_field=custom_field;
	}


	public MccAddress() {
		super();
	}

}
