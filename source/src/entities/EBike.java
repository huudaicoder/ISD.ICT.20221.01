package entities;

import exceptions.ecobike.InvalidEcoBikeInformationException;
import interfaces.Chargeable;
import utils.Configs;

/**
 * A bike with 2 wheels and an electric engine
 * This bike will have the rental price as 1.5 times as the normal bike
 *
 */
public class EBike extends Bike implements Chargeable {
	
	private double battery;
	
	/**
	 * Create a EBike instance, automatically set its type and rental price
	 * @param name Name of the bike
	 * @param licensePlateCode License plate code of the bike
	 * @param bikeImage Path to the image of the bike
	 * @param bikeBarcode Barcode of the bike. This must be unique in the database
	 * @param currencyUnit Currency unit used to perform bike transactions
	 * @param deposit Deposit price of the bike
	 * @param createDate Day that the bike is created on the system
	 * @throws InvalidEcoBikeInformationException
	 */
	public EBike(String name, String licensePlateCode, String bikeImage, 
			String bikeBarcode, String currencyUnit, 
			String createDate, float battery) throws InvalidEcoBikeInformationException {
		super(name, licensePlateCode, bikeImage, bikeBarcode, currencyUnit, createDate);
		this.setBikeType(Configs.BikeType.EBike);
		this.setBattery(battery);
	}	

	/**
	 * Get the bike's battery status
	 * @return current battery of the bike
	 */
	public float getBattery() {
		return (float)this.battery;
	}
	
	/**
	 * Set the bike's battery status
	 * @param battery new battery status of the bike
	 * @throws InvalidEcoBikeInformationException 
	 */
	public void setBattery(double battery) throws InvalidEcoBikeInformationException {
		if (battery < 0 || battery > 100) {
			throw new InvalidEcoBikeInformationException("the bike battery is invalid");
		}
		this.battery = battery;
	}
}
