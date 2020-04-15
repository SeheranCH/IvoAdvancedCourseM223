package ch.course223.advanced.validation.date;

import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.DateTimeException;
import java.time.LocalDate;

@Component
public class DateValidator implements ConstraintValidator<Date, String> {

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    try {
      LocalDate.parse(value);
      return true;
    } catch (DateTimeException e) {
      return false;
    }
  }
}
