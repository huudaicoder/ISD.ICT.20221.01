package entities;

import exceptions.ecobike.InvalidEcoBikeInformationException;
import utils.Configs;

/**
 * A normal bike, with 2 wheels and no engine
 * This bike will have a standard rental price
 */
public class NormalBike extends Bike{
	/**
	 * Create a Normal Bike instance, automatically set its type and rental price
	 * @param name Name of the bike
	 * @param licensePlateCode License plate code of the bike
	 * @param bikeImage Path to the image of the bike
	 * @param bikeBarcode Barcode of the bike. This must be unique in the database
	 * @param currencyUnit Currency unit used to perform bike transactions
	 * @param deposit Deposit price of the bike
	 * @param createDate Day that the bike is created on the system
	 * @throws InvalidEcoBikeInformationException
	 */
	public NormalBike(String name, String licensePlateCode, String bikeImage, 
			String bikeBarcode, String currencyUnit,
			String createDate) throws InvalidEcoBikeInformationException {
		super(name, licensePlateCode, bikeImage, bikeBarcode, currencyUnit, createDate);
		this.setBikeType(Configs.BikeType.NormalBike);
	}		
}

	