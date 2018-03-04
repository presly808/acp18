package spring.aspects;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import spring.sevice.IUserServiceImpl;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger LOGGER = Logger.getLogger(LoggingAspect.class);

    @Around(value = "pointcutForUserServiceMethods()")
    public Object getProcessedTimeForServiceMethods(ProceedingJoinPoint joinPoint){
        Object val = null;
        try {
            long start = System.currentTimeMillis();
            val = joinPoint.proceed();
            long end = System.currentTimeMillis();
            LOGGER.info(String.format("%s.%s() %d millis", IUserServiceImpl.class.getSimpleName(),joinPoint.getSignature().getName(), end-start));
            return val;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return val;
    }

    @Pointcut(value = "within(spring.sevice.IUserServiceImpl)")
    public void pointcutForUserServiceMethods(){}


}
