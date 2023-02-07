package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import controllers.PaymentController;

class validateSecurityTest {

	private PaymentController controllers;
	@BeforeEach
	void setUp() throws Exception {
		controllers = new PaymentController();
	}

	@ParameterizedTest
	@CsvSource({
		"VCb0,false",
		"1234,true",
		"123,true",
		"12345,false"
	})
	public void test(String cardSecurity, boolean expected) {
		//when
		boolean isValid = controllers.validateCardSecurity(cardSecurity);
		
		//then
		assertEquals(expected, isValid);
	}

}
