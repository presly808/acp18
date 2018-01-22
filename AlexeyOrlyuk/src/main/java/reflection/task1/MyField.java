package reflection.task1;

import java.lang.annotation.*;

/**
 * Created by serhii on 21.01.18.
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface MyField {

    Class type();

}
