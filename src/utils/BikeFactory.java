package utils;

import java.sql.ResultSet;
import java.sql.SQLException;

import entities.Bike;
import entities.EBike;
import entities.NormalBike;
import entities.TwinBike;
import entities.TwinEbike;
import exceptions.ecobike.InvalidEcoBikeInformationException;
import utils.Configs.BikeType;

public class BikeFactory {
//	public static Bike getBikeWithInformation(String name, String bikeType, String licensePlateCode, String bikeImage, 
//			String bikeBarcode, String currencyUnit, double deposit, 
//			String createDate) {
//		Bike b = null;
//		try {			
//			if (bikeType.equalsIgnoreCase(Configs.BikeType.NormalBike.toString())) {
//				b = new NormalBike(name, licensePlateCode, bikeImage, bikeBarcode, currencyUnit, deposit, createDate);
//				return b;
//			} 
//			
//			if (bikeType.equalsIgnoreCase(Configs.BikeType.EBike.toString())) {
//				b = new EBike(name, licensePlateCode, bikeImage, bikeBarcode, currencyUnit, deposit, createDate);
//				
//				return b;
//			}
//			
//			if (bikeType.equalsIgnoreCase(Configs.BikeType.TwinBike.toString())) {
//				b = new TwinBike(name, licensePlateCode, bikeImage, bikeBarcode, currencyUnit, deposit, createDate);
//			}
//			
//			if (bikeType.equalsIgnoreCase(Configs.BikeType.TwinEBike.toString())) {
//				b = new TwinEbike(name, licensePlateCode, bikeImage, bikeBarcode, currencyUnit, deposit, createDate);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return b;
//	}
	
	
	public static Bike getBikeWithInformation(ResultSet result) {
		Bike b = null;
		try {
		BikeType bikeType = Configs.BikeType.toBikeType(result.getString("bike_type"));		
			if (bikeType == Configs.BikeType.NormalBike) {
				b = new NormalBike(result.getString("name"),
						result.getString("license_plate_code"),
						result.getString("bike_image"), 
						result.getString("bike_barcode"), 
						result.getString("currency_unit"),
						result.getDate("create_date").toString());
				}
			if (bikeType == Configs.BikeType.TwinBike) {
				b = new TwinBike(result.getString("name"),
						result.getString("license_plate_code"),
						result.getString("bike_image"), 
						result.getString("bike_barcode"), 
						result.getString("currency_unit"),
						result.getDate("create_date").toString());
			}
			if (bikeType == Configs.BikeType.EBike) {
				b = new EBike(result.getString("name"),
						result.getString("license_plate_code"),
						result.getString("bike_image"), 
						result.getString("bike_barcode"), 
						result.getString("currency_unit"),
						result.getDate("create_date").toString(),
						result.getFloat("current_battery"));
				
				}
			if (bikeType == Configs.BikeType.TwinEBike) {
				b = new TwinEbike(result.getString("name"),
						result.getString("license_plate_code"),
						result.getString("bike_image"), 
						result.getString("bike_barcode"), 
						result.getString("currency_unit"),
						result.getDate("create_date").toString(),
						result.getFloat("current_battery"));
			}
		} catch (InvalidEcoBikeInformationException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return b;		
	}
}
