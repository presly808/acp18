package spring.aspect;

import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import spring.service.MyUserService;

import java.io.IOException;

/**
 * Created by alex323glo on 19.02.18.
 */
@Aspect
@Component
public class MyUserServiceLoggingAspect {

    private static final String APPENDER_PATTERN = "%d{yyyy-MM-dd HH:mm:ss} %m\n";
    private static final String APPENDER_FILE_PATH = "user-service.log";
    private static final Logger LOGGER = configureLogger();

    private long methodStartTime;
    private String methodFullName;

    @Before("publicMethodsPointCut()")
    public void beforeLoggingPublicMethodsAdvice() {
        methodStartTime = System.currentTimeMillis();
    }

    @Around("publicMethodsPointCut()")
    public Object aroundLoggingPublicMethodsAdvice(ProceedingJoinPoint point) throws Throwable {
        methodFullName = point.getSignature().getDeclaringType() + "." + point.getSignature().getName();
        return point.proceed();
    }

    @After("publicMethodsPointCut()")
    public void afterLoggingPublicMethodsAdvice() {
        long workTime = System.currentTimeMillis() - methodStartTime;
        LOGGER.info(methodFullName + "() " + workTime + "ms");
    }

    @Pointcut(value = "execution(public * spring..*(..))")
    public void publicMethodsPointCut() {}

    private static Logger configureLogger() {
        Logger logger = Logger.getLogger(MyUserService.class);

        logger.setLevel(Level.INFO);
        try {
            logger.removeAllAppenders();
            logger.addAppender(new FileAppender(new PatternLayout(APPENDER_PATTERN), APPENDER_FILE_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return logger;
    }

}
