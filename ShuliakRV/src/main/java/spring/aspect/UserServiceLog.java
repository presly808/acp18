package spring.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class UserServiceLog {

    @Around(value = "execution(public * spring.service..*(..))")
    public Object userServiceAdvice(ProceedingJoinPoint joinPoint) throws Throwable {

        String methodFullName = joinPoint.getSignature().getDeclaringType() + "."
                + joinPoint.getSignature().getName();

        long methodStartTime = System.currentTimeMillis();
        Object executionResult = joinPoint.proceed();
        long methodWorkTime = System.currentTimeMillis() - methodStartTime;

        System.out.println((methodFullName + "() " + methodWorkTime + "ms"));
        return executionResult;
    }

}
