package ch.course223.advanced.validation.date;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
@Constraint(validatedBy = FutureValidator.class)
public @interface InFuture {
  String message() default "";
  Class<?>[] groups() default {};
  Class<? extends Payload>[] payload() default {};

}