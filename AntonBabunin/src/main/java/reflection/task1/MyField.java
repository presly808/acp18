package reflection.task1;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by serhii on 21.01.18.
 */

@Retention(RetentionPolicy.RUNTIME)
public @interface MyField {
    RetentionPolicy asd = RetentionPolicy.RUNTIME;
}
