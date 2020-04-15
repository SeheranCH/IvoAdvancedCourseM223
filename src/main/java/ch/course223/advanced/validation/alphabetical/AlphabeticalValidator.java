package ch.course223.advanced.validation.alphabetical;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class AlphabeticalValidator implements ConstraintValidator<Alphabetical, String> {

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    if(value==null){
      return true;
    }
    return value.matches("^[a-zA-Z äöüéèà-]+$");
  }
}
