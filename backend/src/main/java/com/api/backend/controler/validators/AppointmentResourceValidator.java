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
      errors.rejectValue("date", "null.date", "Date shouldn't be null");
    }

    if (appointmentResource.nameSession() == null) {
      errors.rejectValue("number", "null.number", "Number shouldn't be null");
    }

    if (appointmentResource.teacher() == null || appointmentResource.teacher().isBlank()) {
      errors.rejectValue("teacher", "null.teacher", "Teacher name shouldn't be null or blank");
    }

    if (appointmentResource.status() == null) {
      errors.rejectValue("status", "null.status", "Status shouldn't be null");
    }

  }
}
