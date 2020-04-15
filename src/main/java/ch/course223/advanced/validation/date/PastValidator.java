package ch.course223.advanced.validation.date;

import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

@Component
public class PastValidator implements ConstraintValidator<InPast, String> {

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    try {
      if (LocalDate.parse(value).isBefore(LocalDate.now())) {
        return true;
      }
    } catch (Exception e) {
      return false;
    }
    return false;
  }
}
