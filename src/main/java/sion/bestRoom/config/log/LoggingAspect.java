package sion.bestRoom.config.log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Around("execution(* sion.bestRoom.controller.*.*(..))")
    public Object logController(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.info("========== Log start ==========");
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        long startTime = System.currentTimeMillis();
        logger.info("Executing method {} in class {}", methodName, className);
        Object result = joinPoint.proceed();
        long endTime = System.currentTimeMillis();
        logger.info("Finished method {} in class {} in {} ms", methodName, className, endTime - startTime);
        logger.info("========== Log End ==========");
        return result;
    }
}