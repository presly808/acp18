package hibernate.aspect;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CheckMethodsWorkingTimeAspect {

    private static final Logger LOGGER = Logger.getLogger(CheckMethodsWorkingTimeAspect.class);

    @Around(value = "execution(public * hibernate..*(..))")
    public Object arroundMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        Object value;
        long start = System.currentTimeMillis();

        try {
            value = joinPoint.proceed();
        } catch (Throwable throwable) {
            LOGGER.error(String.format("The method %s failed", methodName));
            throw throwable;
        }

        long end = System.currentTimeMillis();
        LOGGER.info(String.format("Method: %s, time: %d%n", methodName, end - start));

        return value;
    }
}
