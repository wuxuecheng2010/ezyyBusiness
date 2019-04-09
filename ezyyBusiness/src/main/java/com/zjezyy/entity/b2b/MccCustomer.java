package com.zjezyy.entity.b2b;

import lombok.Data;

@Data
public class MccCustomer {
	   private Integer customer_id;
	   private Integer customer_group_id;
	   private String fullname;
	   private String email;
	   private String telephone;
	   private Integer erp_icustomerid;
}
