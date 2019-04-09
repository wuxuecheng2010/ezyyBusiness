package com.zjezyy.exception;

import com.zjezyy.enums.ExceptionEnum;

public class BusinessException extends RuntimeException{

    private Integer code;
    private String memo;

    /**
     * 继承exception，加入错误状态值
     * @param exceptionEnum
     */
    public BusinessException(ExceptionEnum exceptionEnum) {
        super(exceptionEnum.getMsg());
        this.code = exceptionEnum.getCode();
    }

    /**
     * 自定义错误信息
     * @param message
     * @param code
     */
    public BusinessException(String message, Integer code) {
        super(message);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
