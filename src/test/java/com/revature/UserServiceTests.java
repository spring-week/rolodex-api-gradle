package com.revature;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Optional;

import com.revature.dto.Credentials;
import com.revature.exceptions.UserNotFoundException;
import com.revature.models.User;
import com.revature.repositories.UserRepository;
import com.revature.service.UserService;

/**
 * This is a Test Suite - a grouping of unit tests for the methods
 * within one class.
 * 
 * The @ExtendWith annotation is used to load a JUnit 5 extension. 
 * JUnit defines an extension API, which allows a third-party vendor 
 * like Mockito to hook into the life cycle of running test classes and 
 * add additional functionality.
 */
@ExtendWith(MockitoExtension.class)
public class UserServiceTests {

	@Mock
	UserRepository mockUserRepo;

	@InjectMocks
	UserService uServ;

	User dummyUser;

	@BeforeEach
	void setUp() throws Exception {
		this.dummyUser = new User(1, "dummyfirstname", "dummylastname", "dummyusername", "password", "dummy@mail.com", null);
	}

	@AfterEach
	void tearDown() throws Exception {
		// Garbage Collect the dummy user
		this.dummyUser = null;
	}

	@Test
	void testDivideHappyPathScenario() {

		int x = 10;
		int y = 2;

		int expected = 5;
		int actual = this.uServ.divide(x, y);

		assertEquals(expected, actual);
	}

	@Test
	void testDivide_Failure_ArithmeticException() {

		try {
			this.uServ.divide(10, 0);
		} catch (Exception e) {
			assertEquals(ArithmeticException.class, e.getClass());
		}
	}

	@Test
	void testGetById_Success() {

		int id = 1;

		given(this.mockUserRepo.findById(id)).willReturn(Optional.of(this.dummyUser));

		User expected = this.dummyUser;
		User actual = this.uServ.getById(id);

		assertEquals(expected, actual);
	}

	@Test
	void testGetById_Failure_IdLessThanZero() {

		int id = -1;
		// we never contact the repo layer!
		assertNull(this.uServ.getById(id));
	}

	@Test
	void testGetById_Failure_UserNotFoundException() {

		int id = 2;

		// when the service layer calls on the repo layer, it returns an empty Optional
		given(this.mockUserRepo.findById(id)).willReturn(Optional.empty());

		try {
			this.uServ.getById(id);
		} catch (Exception e) {
			// prove that the Exception thrown was indeed a UserNotFoundException
			assertEquals(UserNotFoundException.class, e.getClass());
		}

	}

	/**
	 * ============== CHALLENGE ==============
	 * 
	 * Write 2 unit tests for the service layer's findByUsername() method 
	 * 
	 * One should be successful. 
	 * The other should test that the UserNotFoundException is thrown.
	 */
	
	@Test
	void testGetByUsername_Success() {

		String username = "dummyusername";

		given(this.mockUserRepo.findByUsername(username)).willReturn(Optional.of(this.dummyUser));

		User expected = this.dummyUser;
		User actual = this.uServ.getByUsername(username);

		assertEquals(expected, actual);
	}
	
	@Test
	void testGetByUsername_Failure_UserNotFoundException() {

		String username = "brucebanner";

		// when the service layer calls on the repo layer, it returns an empty Optional
		given(this.mockUserRepo.findByUsername(username)).willReturn(Optional.empty());

		try {
			this.uServ.getByUsername(username);
		} catch (Exception e) {
			// prove that the Exception thrown was indeed a UserNotFoundException
			assertEquals(UserNotFoundException.class, e.getClass());
		}

	}
	
	// ================== SOLUTION ABOVE ==============================

	@Test
	void testGetByCredentials() {
		
		String username = this.dummyUser.getUsername();
		String password = this.dummyUser.getPassword();
		given(this.mockUserRepo.findByUsernameAndPassword(username, password)).willReturn(Optional.of(this.dummyUser));

		Credentials creds = new Credentials(username, password);
		
		User expected = this.dummyUser;
		User actual = this.uServ.getByCredentials(creds);

		assertEquals(expected, actual);
		
		verify(this.mockUserRepo, times(1)).findByUsernameAndPassword(username, password);
	}
	
	@Test
	void testGetByCredentials_Failure_IncorrectPassword() {
		
		String username = this.dummyUser.getUsername();
		String password = "wrongpassword";
		
		given(this.mockUserRepo.findByUsernameAndPassword(username, password)).willReturn(Optional.empty());

		Credentials creds = new Credentials(username, password);

		assertNull(this.uServ.getByCredentials(creds));
		
		verify(this.mockUserRepo, times(1)).findByUsernameAndPassword(username, password);
		
	}
}
