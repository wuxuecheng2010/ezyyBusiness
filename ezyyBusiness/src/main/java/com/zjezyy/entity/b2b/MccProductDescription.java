package com.zjezyy.entity.b2b;

import java.util.regex.Pattern;

import com.zjezyy.entity.erp.TbProductinfo;
import com.zjezyy.enums.LanguageEnum;
import com.zjezyy.utils.DataUtil;

import lombok.Data;

@Data
public class MccProductDescription {
	private Integer product_id;
	private String name;
	private String meta_title;
	private String meta_keyword;
	private String meta_description;
	private Integer language_id;
	private String description;
	private String tag;
	private String vcproductname;
	private String vceasycode;
	public MccProductDescription(Integer product_id,String name, String meta_title, String meta_keyword, String meta_description,
			Integer language_id, String description, String tag) {
		this.product_id=product_id;
		this.name = name;
		this.meta_title = meta_title;
		this.meta_keyword = meta_keyword;
		this.meta_description = meta_description;
		this.language_id = language_id;
		this.description = description;
		this.tag = tag;
	}
	
	public MccProductDescription() {
		
	}
	
	
     public MccProductDescription(int product_id,LanguageEnum language,TbProductinfo tbProductinfo) {
    	 
    	this.product_id=product_id;
    	String vcuniversalname=tbProductinfo.getVcuniversalname();
    	vcuniversalname=DataUtil.formatNameSubfix(vcuniversalname);
    	
 		this.name = vcuniversalname;
 		String title=new StringBuilder().append(tbProductinfo.getVcproductname()).append(" ").append(tbProductinfo.getVcproductname()).toString();
 		this.meta_title = title;
 		this.meta_keyword = title;
 		this.meta_description = title;
 		this.language_id = language.getCode();
 		this.description = title;
 		this.tag = title;
 		this.vceasycode=tbProductinfo.getVceasycode();
 		this.vcproductname=tbProductinfo.getVcproductname();
 		
	}
	
     

}
