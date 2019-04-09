package com.zjezyy.aspect;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.zjezyy.advice.ExceptionHandle;
import com.zjezyy.entity.Result;

import lombok.extern.slf4j.Slf4j;

//@Aspect
@Component
@Slf4j
public class HttpAuthorize {

	// private final static Logger LOGGER =
	// LoggerFactory.getLogger(HttpAspect.class);

	@Autowired
	private ExceptionHandle exceptionHandle;

	@Pointcut("execution(public * com.zjezyy.controller.OrderController.*(..))")
	public void exe() {

	}

	@Before("exe()")
	public void doBefore(JoinPoint joinPoint)  {
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();
        
		// url
		log.info("url={}", request.getRequestURL());
		// method
		log.info("method={}", request.getMethod());
		// ip
		log.info("id={}", request.getRemoteAddr());
		// class_method
		log.info("class_method={}",
				joinPoint.getSignature().getDeclaringTypeName() + "," + joinPoint.getSignature().getName());
		// args[]
		log.info("args={}", joinPoint.getArgs());
		
		//throw new Exception("xxxx");
	}

	/*@Around("log()")
	public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		Result result = null;
		try {

		} catch (Exception e) {
			return exceptionHandle.exceptionGet(e);
		}
		if (result == null) {
			return proceedingJoinPoint.proceed();
		} else {
			return result;
		}
	}

	@AfterReturning(pointcut = "log()", returning = "object") // 打印输出结果
	public void doAfterReturing(Object object) {
		log.info("response={}", object.toString());
	}*/
}
