package spring.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class Aspects {
    @Pointcut(value = "execution(public * spring..*(..))")
    public void publicMethodsPointCut(){}

    @Around(value = "publicMethodsPointCut()")
    public Object arroundMethod(ProceedingJoinPoint joinPoint){
        String methodName = joinPoint.getSignature().getName();
        Object value = null;
        long start = System.currentTimeMillis();

        try {
            value = joinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        long end = System.currentTimeMillis();
        System.out.printf("Method: %s, time: %d%n", methodName, end - start);

        return value;
    }
}
