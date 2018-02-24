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

    @Around(value = "execution(public * spring..*(..))")
    public Object aroundLoggingPublicMethodsAdvice(ProceedingJoinPoint point) throws Throwable {
        String methodFullName = point.getSignature().getDeclaringType() + "." + point.getSignature().getName();

        long methodStartTime = System.currentTimeMillis();
        Object executionResult = point.proceed();
        long methodWorkTime = System.currentTimeMillis() - methodStartTime;

        LOGGER.info(methodFullName + "() " + methodWorkTime + "ms");
        return executionResult;
    }

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
