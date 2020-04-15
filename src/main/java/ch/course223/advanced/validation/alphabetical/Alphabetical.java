package ch.course223.advanced.validation.alphabetical;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
@Constraint(validatedBy = AlphabeticalValidator.class)
public @interface Alphabetical {
  String message() default "";
  Class<?>[] groups() default {};
  Class<? extends Payload>[] payload() default {};
}
