package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import controllers.EcoBikeInformationController;
import entities.Bike;
import entities.Dock;

class BikeInformationControllerTest {
	private EcoBikeInformationController controller;
	private ArrayList<Bike> listBike;

	@BeforeEach
	void setUp() throws Exception {
		boolean isOK = true;
		try {
			controller = new EcoBikeInformationController();
			Bike bike1 = new Bike("ABC", "normal", "ABC/123", "ABC-123", 10, 300, "VND", "12/03/2021");
			Bike bike2 = new Bike("ABC", "normal", "ABC/124", "ABC-124", 10, 300, "VND", "12/03/2021");
			listBike = new ArrayList<Bike>();
			listBike.add(bike2);
			listBike.add(bike1);
			controller.setBikeList(listBike);
		} catch (Exception e) {
			e.printStackTrace();
		}
				
	}

	@ParameterizedTest
	@CsvSource({
		"ABC-123, true",
		"ABC-124, true",
		"ABC-125, false",
	})
	void test(String id, boolean expected) throws Exception {
		assertTrue((controller.getBikeInformation(id).compareTo("ABC") == 0) == expected);			
	}

}