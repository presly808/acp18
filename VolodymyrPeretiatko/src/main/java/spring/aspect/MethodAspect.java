package spring.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Aspect
@Component
public class MethodAspect {

    @Autowired
    private Environment environment;

    @Around("within(spring.service.UserServiceImpl)")
    public Object processedTime(ProceedingJoinPoint point) {
        Object value = null;
        long startTime = System.currentTimeMillis();
        try {
            value = point.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        long stopTime = System.currentTimeMillis();

        String currentTime = (ZonedDateTime.now().format(DateTimeFormatter.ISO_LOCAL_TIME).substring(0, 8));
        int a = point.toString().length();
        String methodName = point.toString().substring(15,a);
        String executingTime = String.valueOf(stopTime - startTime);

        writeLog("DATE:" + currentTime + " " + methodName + " " + executingTime + "m");

        return value ;
    }

    private void writeLog(String logMsg){

        FileWriter writer = null;
        try {
            File logFile = new File(environment.getProperty("file.aspectlogname"));
            writer = new FileWriter(logFile, true);

            writer.write(logMsg + '\n');

            writer.flush();
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}