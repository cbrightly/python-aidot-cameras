package kotlin;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
/* compiled from: Metadata.kt */
public @interface l {
    String[] d1() default {};

    String[] d2() default {};

    int k() default 1;

    int[] mv() default {};

    int xi() default 0;
}
