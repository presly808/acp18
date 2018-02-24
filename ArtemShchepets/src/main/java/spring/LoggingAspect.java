package spring;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Aspect
@Component
public class LoggingAspect {

    @Around("within(spring.*)")
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

        String resultString = "DATE:" + currentTime + " " + methodName + " " + executingTime + "m";

        try {
            String filename = "C:\\Users\\usr\\IdeaProjects\\acp18\\ArtemShchepets\\src\\main\\resources\\spring\\user-service.log";
            FileWriter writer = new FileWriter(filename, true);

            writer.write(resultString + '\n');

            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return value ;
    }
}
