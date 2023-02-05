package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import entities.Dock;
import exceptions.ecobike.InvalidEcoBikeInformationException;

class DockTest {

	@BeforeEach
	void setUp() throws Exception {
	
	}

	@ParameterizedTest
	@CsvSource({
		"ABC, 123, 123 Tay Ho, 163.9, 10, 20",
		"A BC, 123, 123 Tay Ho, 163.9, 10, 20",
		"ABC, 12-3, 123/69 Tay Ho, 163.9, 10, 20",
	})
	void DockConstructorTestOK(String name, String dockID, String dock_address, double dock_area, int num_available_bike,
			int num_dock_space_free) {
		boolean isValid = true;
		try {
			Dock dock = new Dock(name, dockID, dock_address, dock_area, num_available_bike, num_dock_space_free);
		} catch (Exception e){
			isValid = false;
		} finally {
			assertTrue(isValid);
		}
	}
	
	@ParameterizedTest
	@CsvSource({
		"1ABC, 123, 123 Tay Ho, 163.9, 10, 20",
		"A?BC, 123, 123 Tay Ho, 163.9, 10, 20",
		"ABC, 123, 123?$ Tay Ho, 163.9, 10, 20",
		"1ABC, 123, 123 Tay Ho, 0, 10, 20",
		"1ABC, 123, 123 Tay Ho, 163.9, -1, 20",
		"1ABC, 123, 123 Tay Ho, 163.9, 10, -1",
	})
	
	void DockConstructorTestException(String name, String dockID, String dock_address, double dock_area, int num_available_bike,
			int num_dock_space_free) {
		boolean isValid = true;
		try {
			Dock dock = new Dock(name, dockID, dock_address, dock_area, num_available_bike, num_dock_space_free);
		} catch (Exception e) {
			assertTrue(e instanceof InvalidEcoBikeInformationException);
		}
	}

}
