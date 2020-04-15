package ch.course223.advanced.validation.date;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
@Constraint(validatedBy = DateValidator.class)
public @interface Date {
  String message() default "";
  Class<?>[] groups() default {};
  Class<? extends Payload>[] payload() default {};

}