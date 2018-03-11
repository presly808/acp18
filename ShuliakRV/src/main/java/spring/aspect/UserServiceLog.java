package spring.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Aspect
@Component
public class UserServiceLog {

    @Around(value = "execution(public * spring.service..*(..))")
    public Object userServiceAdvice(ProceedingJoinPoint joinPoint)
            throws Throwable {

        String methodName = joinPoint.getSignature().getDeclaringTypeName()
                + "." + joinPoint.getSignature().getName();

        LocalDateTime start = LocalDateTime.now();
        Object executionResult = joinPoint.proceed();
        LocalDateTime end = LocalDateTime.now();

        Duration duration = Duration.between(start, end);
        long minutes = duration.toMillis();

        DateTimeFormatter formatter = DateTimeFormatter.
                ofPattern("dd.MM.yyyy hh:mm:ss.SSS");

        String log = LocalDateTime.now().format(formatter)+
                " "+methodName + "() " + minutes + " millis";

        File newFile = new File("./src/main/resources",
                "user-service.log");

         if (!newFile.exists()) {
             newFile.createNewFile();
         }

        PrintWriter writer = new PrintWriter(new BufferedWriter(
                new FileWriter(newFile,true)));
        writer.println(log);
        writer.close();

        return executionResult;
    }

}
