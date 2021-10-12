package ar.edu.unq.virtuaula.validator;

import java.util.Vector;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import ar.edu.unq.virtuaula.model.User;

@Component
public class UserValidator {

	public static boolean validate(User user, Vector<String> erros) {
		Boolean isValid = Boolean.TRUE;
		if(!validateUsername(user.getUsername())) {
			erros.add("Error username more to 5 charaters");
			erros.add("Error username cannot end or begin with {'.','-','_'} ");
			erros.add("Error username must have at least one capital letter");
			erros.add("Error username must have at least one special character");
			erros.add("Error username must have at least one capital letter");
			isValid = Boolean.FALSE;
		}
		if(!validatePassword(user.getPassword())) {
			erros.add("Error password more to 8 charaters");
			erros.add("Error password no blanks allowed");
			erros.add("Error password must have at least one capital letter");
			erros.add("Error password must have at least one special character {'@','#','$','%','^','&','+','='}");
			erros.add("Error password must have at least one capital letter");
			erros.add("Error password must have at least one number");
			isValid = Boolean.FALSE;
		}
		
		if(!validateEmail(user.getEmail())) {
			erros.add("Error email invalid");
			isValid = Boolean.FALSE;
		}
		
		return isValid;
	}

	public static void validateSamePassword(String password, String repeatPassword, Vector<String> erros) {
		if(!password.equals(repeatPassword)) {
			erros.add("Error the password must be the same");
		}
	}
	
	private static boolean validateEmail(String email) {
		final Pattern EMAIL_REGEX = Pattern.compile("[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", Pattern.CASE_INSENSITIVE);
	    return EMAIL_REGEX.matcher(email).matches();
	}

	private static boolean validatePassword(String password) {
		final Pattern PASSWORD_REGEX = Pattern.compile("(?=.*[0-9])(?=.*[a-z])(?=\\S+$).{8,}");
		return PASSWORD_REGEX.matcher(password).matches();
	}

	private static boolean validateUsername(String username) {
		final Pattern USER_REGEX = Pattern.compile("[a-zA-Z0-9\\._\\-]{5,}");
		return USER_REGEX.matcher(username).matches();
	}
}
