package ch.course223.advanced.validation.date;

import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

@Component
public class FutureValidator implements ConstraintValidator<InFuture, String> {

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    try {
      if (LocalDate.parse(value).isAfter(LocalDate.now())) {
        return true;
      }
    } catch (Exception e) {
      return false;
    }
    return false;
  }
}
