/*
package spring.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Component
public aspect Aspect {

    @Pointcut(value = "")
    public void publicMethodsPointCut(){}

    @Around(value = "execution(public * spring..*(..))")
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
*/
/*
@Aspect
@Component
public class LoggingAspect {

    @Before(value = "publicMethodsPointCut()")
    public void loggingPublicMethodsAdvice(){
        System.out.println("public method was called");
    }

    @Pointcut(value = "execution(public * ua.artcode.testio.service..*(..))")
    public void publicMethodsPointCut(){}

    @Around(value = "publicMethodsPointCut()")
    public Object aroundAdvice(ProceedingJoinPoint proceedingJoinPoint){
        String methodName = proceedingJoinPoint.getSignature().getName();
        System.out.printf("before method %s\n", methodName);

        Object value = null;

        try {
            value =  proceedingJoinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        System.out.printf("after method %s\n", methodName);

        return value;
    }



}
*/

