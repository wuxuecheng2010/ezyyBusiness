package com.zjezyy.aop;


import com.zjezyy.enums.ExceptionEnum;
import com.zjezyy.exception.BusinessException;
import com.zjezyy.utils.RedisUtil;

import com.alibaba.fastjson.JSON;

import org.apache.catalina.connector.RequestFacade;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.Base64;

/**
 * 防刷切面实现类
 *
 * @author: zetting
 * @date: 2018/12/29 20:27
 */
@Aspect
@Component
public class PreventAop {
    private static Logger log = LoggerFactory.getLogger(PreventAop.class);

    @Autowired
    private RedisUtil redisUtil;
    

    /**
     * 切入点
     */
    @Pointcut("@annotation(com.zjezyy.aop.Prevent)")
    public void pointcut() {
    }


    /**
     * 处理前
     *
     * @return
     */
    @Before("pointcut()")
    public void joinPoint(JoinPoint joinPoint) throws Exception {
    	Object[] args=joinPoint.getArgs();
    	
    	if(args==null||args[0]==null) {
    		throw new BusinessException(ExceptionEnum.PREVENT_PARAMS_NOT_ALOW_EMPTY);
    	}
    	RequestFacade requestFacade=(RequestFacade) args[0];
        /*String requestStr = JSON.toJSONString(r);
        if (StringUtils.isEmpty(requestStr) || requestStr.equalsIgnoreCase("{}")) {
            throw new BusinessException(ExceptionEnum.PREVENT_PARAMS_NOT_ALOW_EMPTY);
        }*/


        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = joinPoint.getTarget().getClass().getMethod(methodSignature.getName(),
                methodSignature.getParameterTypes());

        Prevent preventAnnotation = method.getAnnotation(Prevent.class);
        String methodFullName = method.getDeclaringClass().getName() + method.getName();

        entrance(preventAnnotation, requestFacade,methodFullName);
        return;
    }


    /**
     * 入口
     *
     * @param prevent
     * @param requestStr
     */
    private void entrance(Prevent prevent, RequestFacade requestFacade,String methodFullName) throws Exception {
        PreventStrategy strategy = prevent.strategy();
        switch (strategy) {
            case DEFAULT:
                defaultHandle(requestFacade, prevent,methodFullName);
                break;
            default:
                throw new BusinessException(ExceptionEnum.PREVENT_STRATEGY_ONT_IN_USE);
        }
    }


    /**
     * 默认处理方式
     *
     * @param requestStr
     * @param prevent
     */
    private void defaultHandle(RequestFacade requestFacade, Prevent prevent,String methodFullName) throws Exception {
        String base64Str ="base64Str";// toBase64String(requestFacade);
        long expire = Long.parseLong(prevent.value());

        //String resp = redisTemplate.get(methodFullName+base64Str);
        String resp =(String)redisUtil.get(methodFullName+base64Str);
        if (StringUtils.isEmpty(resp)) {
            redisUtil.set(methodFullName+base64Str, requestFacade, expire);
        } else {
            String message = !StringUtils.isEmpty(prevent.message()) ? prevent.message() :
                    expire + "秒内不允许重复请求";
            throw new BusinessException(message,0);
        }
    }


    /**
     * 对象转换为base64字符串
     *
     * @param obj 对象值
     * @return base64字符串
     */
    private String toBase64String(String obj) throws Exception {
        if (StringUtils.isEmpty(obj)) {
            return null;
        }
        Base64.Encoder encoder = Base64.getEncoder();
        byte[] bytes = obj.getBytes("UTF-8");
        return encoder.encodeToString(bytes);
    }
}
