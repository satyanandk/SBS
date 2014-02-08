package com.asu.ss.secure_banking_system.validation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class CommonValidations {
	
	public static boolean valiateSpecialCharacters(String inputStr)
	{
		if(inputStr == null || inputStr.isEmpty())
			return true;
		String pattern = "^[a-zA-Z0-9]*$";
		Pattern p = Pattern.compile(pattern);
		
		Matcher match = p.matcher(inputStr);
		if(match.matches()) {
			
			return true;
		}
		
		return false;
	}
	public static boolean validateAddress(String inputStr)
	{
		if(inputStr == null || inputStr.isEmpty())
			return true;
		String pattern = "^[a-zA-Z0-9\\.\\-#,]*$";
		Pattern p = Pattern.compile(pattern);
		
		Matcher match = p.matcher(inputStr);
		if(match.matches()) {
			return true;
		}
		
				
		return false;
	}
	public static boolean validateDateOfBirth(String dateToValidate)
	{
		if(dateToValidate == null || dateToValidate.isEmpty())
			return true;
		
		String dateFormat = "MM/dd/yyyy";
		Date date ;

		

		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		sdf.setLenient(false);

		try {

			//if not valid, it will throw ParseException
			date = sdf.parse(dateToValidate);


		} catch (ParseException e) {

			e.printStackTrace();
			return false;
		}
		

		Date currDate = new Date();
		if(date.after(currDate))
			return false;
		
		return true;
	}
	/*validates
	 * 1. length between 6 and 16.
	 * 2. Special characters allowed are %,_,!
	 */
	
	public static boolean checkValidPassword(String password)
	{
		if(password == null || password.isEmpty())
			return true;
		
		if((password.length()>16) || (password.length()<6))
			return false;
		String pattern = "^[a-zA-Z0-9%_!]*$";
		Pattern p = Pattern.compile(pattern);
		
		Matcher match = p.matcher(password);
		if(match.matches()) {
			return true;
		}
		
		return false;
	}
	
	public static boolean validateEmailFormat(String emailAddress)
	{
		if(emailAddress == null || emailAddress.isEmpty())
			return true;
		
		String pattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		
		Pattern p = Pattern.compile(pattern);
		
		Matcher match = p.matcher(emailAddress);
		if(match.matches()) {
			return true;
		}
		
				
		return false;
	}
	
	/*format is 123-456-7890*/
	public static boolean validatePhoneNumber(String phoneNum)
	{
		if(phoneNum == null || phoneNum.isEmpty())
			return true;
		
		String pattern = "\\d{3}-\\d{3}-\\d{4}";
		Pattern p = Pattern.compile(pattern);
		
		Matcher match = p.matcher(phoneNum);
		if(match.matches()) {
			return true;
		}
		
		return false;
	}
	
	/*Only exceptions. No return Value*/
	public static void validateLength(String input, int lengthToBeChecked, String fieldName) throws Exception
	{
		String message = "Length of field ["+fieldName+ "] should be less than "+lengthToBeChecked+"";
		if(input.length()>lengthToBeChecked)
			throw new Exception(message);
			
	}
	
	public static String validateStringNormal(String input, int lengthToBeChecked, String errorMessage, String FieldName, boolean isMandatory)
	{
		boolean status = true;
		errorMessage = new String();
		if(isMandatory)
		{
			if(input == null || input.isEmpty())
			{
				status = false;
				errorMessage = FieldName + " must be entered.";
				return errorMessage;
			}
		}
		status = valiateSpecialCharacters(input);
		if(!status)
		{
			errorMessage = "Special characters not allowed for field ["+FieldName+"]";
			return errorMessage;
		}
		
		if(input.length()>lengthToBeChecked)
		{
			errorMessage = "Length of field ["+FieldName+"] should be less than "+lengthToBeChecked+"";
			status = false;
			return errorMessage;
		}
		
		return errorMessage;
	}

}
