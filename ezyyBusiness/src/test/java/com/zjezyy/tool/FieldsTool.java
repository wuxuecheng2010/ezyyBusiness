package com.zjezyy.tool;

import java.lang.reflect.Field;

public class FieldsTool {

	public static void main(String[] args) throws ClassNotFoundException {
		 Class<?> clazz=	Class.forName("com.zjezyy.entity.b2b.MccPayResult");
		 String select="";
		 String insertv="";
		 int i=0;
		 for(Field f:clazz.getDeclaredFields()) {
			 i++;
			 if(i==clazz.getDeclaredFields().length) {
				 select+=f.getName();
				 insertv+="#{"+f.getName()+"}";
			 }
			 else {
				 select+=f.getName()+",";
				 insertv+="#{"+f.getName()+"},";
			 }
		 }
		 System.out.println("String SELECT_FIELDS=\""+select+"\";\n");
		 System.out.println("String INSERT_FIELDS=\""+select+"\";\n");
		 System.out.println("String INSERT_VALUES=\""+insertv+"\";\n");
	}

}
