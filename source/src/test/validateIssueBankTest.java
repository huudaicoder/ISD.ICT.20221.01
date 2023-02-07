package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import controllers.PaymentController;

class validateIssueBankTest {

	private PaymentController controllers;
	@BeforeEach
	void setUp() throws Exception {
		controllers = new PaymentController();
	}

	@ParameterizedTest
	@CsvSource({
		"VCb,true",
		"AGB,true",
		"a123,false",
		"1234567890,false"
	})
	public void test(String issueBank, boolean expected) {
		//when
		boolean isValid = controllers.validateIssueBank(issueBank);
		
		//then
		assertEquals(expected, isValid);
	}

}