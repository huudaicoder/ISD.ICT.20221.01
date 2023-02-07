package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import controllers.PaymentController;

class validateCardNumerTest {
	private PaymentController controllers;
	@BeforeEach
	void setUp() throws Exception {
		controllers = new PaymentController();
	}

	@ParameterizedTest
	@CsvSource({
		"0123456789,true",
		"01234,false",
		"a123,false",
		"1234567890,true"
	})
	public void test(String cardNumber, boolean expected) {
		//when
		boolean isValid = controllers.validateCardNumber(cardNumber);
		
		//then
		assertEquals(expected, isValid);
	}

}
