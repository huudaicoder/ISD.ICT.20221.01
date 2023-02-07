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
		"ABC, 123, 123 Tay Ho, 163.9, ksjlkjlkj, 20",
		"A BC, 123, 123 Tay Ho, 163.9, fdiufyiy, 20",
		"ABC, 124, 123/69 Tay Ho, 163.9, hkskvhk, 20",
	})
	void DockConstructorTestOK(String name, int dockID, String dock_address, double dock_area, String dockImage,
			int totalSpace) {
		boolean isValid = true;
		try {
			Dock dock = new Dock(name, dockID, dock_address, dock_area, totalSpace, dockImage);
		} catch (Exception e){
			isValid = false;
		} finally {
			assertTrue(isValid);
		}
	}
	
	@ParameterizedTest
	@CsvSource({
		"1ABC, 123, 123 Tay Ho, 163.9, khdkvkd, 20",
		"A?BC, 123, 123 Tay Ho, 163.9, skknkcnks, 20",
		"ABC, 123, 123?$ Tay Ho, 163.9, skkdfdsd, 20",
		"1ABC, 123, 123 Tay Ho, 0, gahdsgj, 10",
		"1ABC, 123, 123 Tay Ho, 163.9, jdsjkdskf, 20",
		"1ABC, 123, 123 Tay Ho, 163.9, ahfhgdbc, -1",
	})
	
	void DockConstructorTestException(String name, int dockID, String dock_address, double dock_area, String dockImage,
			int num_dock_space_free) {
		boolean isValid = true;
		try {
			Dock dock = new Dock(name, dockID, dock_address, dock_area, num_dock_space_free, dockImage);
		} catch (Exception e) {
			assertTrue(e instanceof InvalidEcoBikeInformationException);
		}
	}

}