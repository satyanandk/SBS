package com.sbs.model.errorhandling;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;


	public class ErrorHandling implements Validator{
		@Override
		public void validate(Object target, Errors errors) {
			ValidationUtils.rejectIfEmptyOrWhitespace(
				errors, "userID", "required.userID");
		}

		@Override
		public boolean supports(Class<?> arg0) {
			// TODO Auto-generated method stub
			return false;
		}


	}


