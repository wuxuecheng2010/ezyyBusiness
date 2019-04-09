package com.zjezyy.enums;

public enum DeployPolicyEnum {
	
	FIFO(1,"先进先出"),
	FIFOAN(2,"先进先出，但是避免近效期"),
;

    private Integer code;

    private String msg;

    DeployPolicyEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
