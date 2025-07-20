package com.api.backend.controler.validators;

import com.api.backend.resources.AppointmentResource;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class AppointmentResourceValidator implements Validator {

  @Override
  public boolean supports(Class<?> clazz) {
    return AppointmentResource.class.equals(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    var appointmentResource = (AppointmentResource) target;

    if (appointmentResource.date() ==  null) {
      errors.rejectValue("date", "null.date", "Darte shouldn't be null");
    }

  }
}
