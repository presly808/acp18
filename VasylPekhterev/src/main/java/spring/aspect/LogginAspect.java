package spring.aspect;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogginAspect {

    private static final Logger LOGGER = Logger.getLogger(LogginAspect.class);

    @Pointcut(value = "execution(public * spring.service.IUserService.* (..))")
    public void serviceMethods() {
    }

    @Around(value = "serviceMethods()")
    public Object aroundAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        String typeName = proceedingJoinPoint.getSignature().getDeclaringTypeName();
        String methodName = proceedingJoinPoint.getSignature().getName();
        System.out.printf("before method: %s\n", methodName);

        Object value = null;
        try {
            long startTime = System.currentTimeMillis();
            value = proceedingJoinPoint.proceed();
            long endTime = System.currentTimeMillis();
            LOGGER.info(String.format("service method %s.%s was run %s milliseconds\n",
                    typeName, methodName, endTime-startTime));
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            throw throwable;
        }

        System.out.printf("after method: %s\n", methodName);

        return value;
    }
}
