package spring.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Aspect
@Component
public class MethodAspect {

    @Around("within(spring.service.UserService)")
    public void processedTime(ProceedingJoinPoint point) {

        long startTime = System.currentTimeMillis();
        try {
            point.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        long stopTime = System.currentTimeMillis();

        System.out.println("aspect done");


        String currentTime = (ZonedDateTime.now().format(DateTimeFormatter.ISO_LOCAL_TIME).substring(0, 8));
        String methodName = point.toString().substring(15, 53);
        String executingTime = String.valueOf(stopTime - startTime);

        String resultString = "DATE:" + currentTime + " " + methodName + " " + executingTime + "m";

        try {
            String filename = "C:\\Users\\Evgeniy\\IdeaProjects\\artcode\\" +
                    "acp18\\PeregoncevEvgeniy\\src\\main\\resources\\user-service.log";
            FileWriter writer = new FileWriter(filename, true);

            writer.write(resultString + '\n');

            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
