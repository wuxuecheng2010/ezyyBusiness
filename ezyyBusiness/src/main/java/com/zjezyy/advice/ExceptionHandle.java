package com.zjezyy.advice;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zjezyy.entity.Result;
import com.zjezyy.enums.ExceptionEnum;
import com.zjezyy.exception.BusinessException;
import com.zjezyy.exception.DescribeException;
import com.zjezyy.utils.ResultUtil;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class ExceptionHandle {


	  /**
	   * 判断错误是否是已定义的已知错误，不是则由未知错误代替，同时记录在log中
	   * @param e
	   * @return
	   */
	  @ExceptionHandler(value = Exception.class)
	  @ResponseBody
	  public Result exceptionGet(Exception e){
	      if(e instanceof DescribeException){
	          DescribeException MyException = (DescribeException) e;
	          return ResultUtil.error(MyException.getCode(),MyException.getMessage());
	      }else if(e instanceof BusinessException) {
	    	  BusinessException MyException = (BusinessException) e;
	          return ResultUtil.error(MyException.getCode(),MyException.getMessage());
	      }

	      log.error("【系统异常】{}",e);
	      return ResultUtil.error(ExceptionEnum.UNKNOW_ERROR);
	  }


}




