package com.nadeem.journalApp;

import com.nadeem.journalApp.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authorization.method.AuthorizeReturnObject;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest //this annotation is to say to spring to start an application context that is similar to normal exec of app
class JournalAppApplicationTests {

	//test name could be anything
	//tests are annotated by @Test

	@Autowired
	private UserRepository userRepository;

	@Test
	public void testFindByUsername() {
		assertNotNull(userRepository.findByUserName("nadeem")); //passes
//		assertNotNull(userRepository.findByUserName("nad")); //fails
	}

	/**
	 *
	 * @param a
	 * @param b
	 * @param expected
	 *
	 * this annotation is used to run a test with multiple values.
	 * these values can be passed with @CSV or @CSVSource
	 */

	@ParameterizedTest // to show that this test is parameterized
	@CsvSource({
			"1,1,2",
			"2,2,4",
			"3,5,8"
	})
	public void test(int a, int b, int expected){
		assertEquals(expected, a+b);
	}

}
