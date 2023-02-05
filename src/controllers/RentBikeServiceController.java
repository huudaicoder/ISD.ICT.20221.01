package controllers;

import exceptions.ecobike.EcoBikeUndefinedException;
import exceptions.ecobike.RentBikeException;
import interfaces.InterbankInterface;
import entities.Bike;
import utils.*;

/**
 * This class handles rent bike, return bike and pause bike rental request from customers
 */
public class RentBikeServiceController {
	private InterbankInterface interbankSystem;
	
	/**
	 * Initialize the controller for EcoBike rent bike service
	 */
	public RentBikeServiceController() {
		
	}

	/**
	 * Start renting bike process, including calling the interbank subsystem for performing transaction
	 *
	 * @param bikeBarcode barCode of the bike to be rented
	 * @throws RentBikeException If the bike is not currently available, the barcode is not valid
	 * @throws EcoBikeUndefinedException If there is an unexpected error occurs during the renting process
	 */
	public void rentBike(String bikeBarcode) throws RentBikeException, EcoBikeUndefinedException  {
		entities.Bike bike = Repository.getBike(bikeBarcode);


	}
	
	/**
	 * Start pausing bike rental process
	 * @param bikeBarcode barCode of the bike to be rented
	 */
	public void pauseBikeRental(String bikeBarcode) {
		entities.Bike bike = Repository.getBike(bikeBarcode);
	}
	
	/**
	 * Start return bike process, including calling the interbank subsystem for performing transaction
	 * @param bikeBarcode barCode of the bike to be returned
	 * @throws RentBikeException If the bike is not currently available, the barcode is not valid
	 * @throws EcoBikeUndefinedException If there is an unexpected error occurs during the renting process
	 */
	public void returnBike(String bikeBarcode) throws RentBikeException, EcoBikeUndefinedException  {

	}
	
	/**
	 * Calculate the fee based on the time rent and deposit of the bike which got by searching barcode
	 * @param  bike Barcode of the bike to be rented
	 * @return a float value expresses the money have to pay in currency VND
	 */
	public float calculateFee(Bike bike) {
		//renting cost
		float rentingCost = bike.getTotalRentTime() <= Configs.freeOfChargeTimeInMinute
				? 0
				: bike.getTotalRentTime() - Configs.firstChargeTimeIntervalInMinute > 0
					? (Configs.firstChargeTimeIntervalCost + (float)Math.ceil((bike.getTotalRentTime() - Configs.firstChargeTimeIntervalInMinute)/ Configs.chargeTimeIntervalInMinute) * Configs.chargeTimeIntervalCost)
					: Configs.firstChargeTimeIntervalCost;

		rentingCost *= Configs.chargeMultiplierDictionary.get(bike.getBikeType());

		//add deposit
		rentingCost += bike.getDeposit();

		return rentingCost;
	}
	
	/**
	 * Check whether user have to pay or not
	 * @param bike The bike which is being returned
	 * @return true/false
	 */
	public boolean checkHaveToPayOrNot(Bike bike) {
		return bike.getTotalRentTime() <= 10 ? false : true;
	}
	
	/**
	 * Request to pay for rental bike
	 * @throws EcoBikeUndefinedException If there is an unexpected error occurs during the renting process
	 */
	public void requestToPay() throws EcoBikeUndefinedException {
		//todo: request to pay
	}
	
	/**
	 * Invoke counter for tracking rental time
	 */
	private void startCountingRentTime() {
		//todo: start counting rental time
	}


}