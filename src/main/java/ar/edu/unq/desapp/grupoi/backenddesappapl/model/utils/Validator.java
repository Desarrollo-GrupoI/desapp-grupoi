package ar.edu.unq.desapp.grupoi.backenddesappapl.model.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import ar.edu.unq.desapp.grupoi.backenddesappapl.model.User;
import ar.edu.unq.desapp.grupoi.backenddesappapl.model.exceptions.InvalidUserBody;

public class Validator {
	
	public static boolean isValidEmail(String email) {
		return Pattern.matches("^[a-zA-Z]+[a-zA-Z._-]*@[a-zA-Z]+\\.[a-zA-Z^s]+$", email);
	}
	
	public static boolean isValidName(String name) {
		Integer nameLength = name.length();
		return nameLength >= 3 && nameLength <= 30;
	}
	
	public static boolean isValidSurname(String surname) {
		Integer surnameLength = surname.length();
		return surnameLength >= 3 && surnameLength <= 30;
	}
	
	public static boolean isValidAddress(String address) {
		Integer addressLength = address.length();
		return addressLength >= 10 && addressLength <= 30;
	}
	
	public static boolean isValidPassword(String password) {
		return Pattern.matches("^(?=.{6,}$)(?=.*[a-z])(?=.*[A-Z])(?=.*\\W).*$", password);
	}
	
	public static void validateUser(User user) {
		String errorMessageBase = "Error(s) in user body:";
		List<String> errorMessages = new ArrayList<String>();
		
		if(!Validator.isValidEmail(user.getEmail()))
			errorMessages.add("\n\u2022Invalid email");
		
		if(!Validator.isValidName(user.getName()))
			errorMessages.add("\n\u2022Invalid name");
		
		if(!Validator.isValidSurname(user.getSurname()))
			errorMessages.add("\n\u2022Invalid surname");
		
		if(!Validator.isValidAddress(user.getAddress()))
			errorMessages.add("\n\u2022Invalid address");
		
		if(!Validator.isValidPassword(user.getPassword()))
			errorMessages.add("\n\u2022Invalid password");
		
		if(!errorMessages.isEmpty())
			throw new InvalidUserBody(errorMessageBase + String.join("", errorMessages));
	}
}
