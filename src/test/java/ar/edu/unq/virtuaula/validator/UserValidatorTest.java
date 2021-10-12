package ar.edu.unq.virtuaula.validator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Vector;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import ar.edu.unq.virtuaula.VirtuaulaApplicationTests;
import ar.edu.unq.virtuaula.model.User;

public class UserValidatorTest extends VirtuaulaApplicationTests{

	@Test
    public void testValidatePasswordAndRepeatPaswordAreTheSame() {
		int expected = 0;
		Vector<String> erros = new Vector<>();
		UserValidator.validateSamePassword("1234", "1234", erros);
		 assertEquals(expected, erros.size());
	}
	
	@Test
    public void testValidatePasswordAndRepeatPaswordAreDifferentThenErrosIsNotEmpty() {
		int expected = 1;
		Vector<String> erros = new Vector<>();
		UserValidator.validateSamePassword("1234", "12345", erros);
		 assertEquals(expected, erros.size());
	}
	
	@Test
    public void testValidateUserValidThenErrosIsEmpty() {
		int expected = 0;
		User user = createOneUser();
		Vector<String> erros = new Vector<>();
		UserValidator.validate(user, erros);
		assertEquals(expected, erros.size());
	}
	
	@Test
    public void testValidateUserInvalidEmailThenErrosIsNotEmpty() {
		int expected = 1;
		User user = Mockito.mock(User.class);
	    Mockito.when(user.getPassword()).thenReturn("1234567n");
	    Mockito.when(user.getEmail()).thenReturn("charlie@");
	    Mockito.when(user.getUsername()).thenReturn("charly2");
		Vector<String> erros = new Vector<>();
		UserValidator.validate(user, erros);
		assertEquals(expected, erros.size());
	}
	
	@Test
    public void testValidateUserInvalidPasswordThenErrosIsNotEmpty() {
		int expected = 6;
		User user = Mockito.mock(User.class);
	    Mockito.when(user.getPassword()).thenReturn("1234");
	    Mockito.when(user.getEmail()).thenReturn("charlie@gmail.com");
	    Mockito.when(user.getUsername()).thenReturn("charly2");
		Vector<String> erros = new Vector<>();
		UserValidator.validate(user, erros);
		assertEquals(expected, erros.size());
	}
	
	@Test
    public void testValidateUserInvalidUsernameThenErrosIsNotEmpty() {
		int expected = 5;
		User user = Mockito.mock(User.class);
	    Mockito.when(user.getPassword()).thenReturn("1234567n");
	    Mockito.when(user.getEmail()).thenReturn("charlie@gmail.com");
	    Mockito.when(user.getUsername()).thenReturn("c2");
		Vector<String> erros = new Vector<>();
		UserValidator.validate(user, erros);
		assertEquals(expected, erros.size());
	}
}
