package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import controllers.PaymentController;
import interfaces.InterbankInterface;

class validateCardHolderNameTest {

	private PaymentController controllers;
	@BeforeEach
	void setUp() throws Exception {
		controllers = new PaymentController(null);
	}

	@ParameterizedTest
	@CsvSource({
		"Tran,true",
		"Le-,true",
		"a123,false",
		"1234567890,false"
	})
	public void test(String cardHolderName, boolean expected) {
		//when
		boolean isValid = controllers.validateCardHolderName(cardHolderName);
		
		//then
		assertEquals(expected, isValid);
	}

}
