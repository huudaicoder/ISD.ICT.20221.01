package controllers;

import exceptions.ecobike.EcoBikeException;
import exceptions.ecobike.EcoBikeUndefinedException;
import exceptions.ecobike.RentBikeException;
import interfaces.InterbankInterface;
import entities.Bike;
import entities.BikeTracker;
import entities.CreditCard;
import entities.Dock;
import entities.Invoice;
import entities.PaymentTransaction;
import entities.strategies.RentalFactory;
import utils.*;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.HashMap;

/**
 * This class handles rent bike, return bike and pause bike rental request from customers
 * bienpt
 */
public class RentBikeController extends EcoBikeBaseController {
	private static int invoiceCounter = 1;
	private InterbankInterface interbankSystem;
	private static RentBikeController rentBikeServiceController;
	private HashMap<String, BikeTracker> listBikeTracker;
	

	public static RentBikeController getRentBikeServiceController(InterbankInterface interbankSystem){
		if (rentBikeServiceController == null) {
			rentBikeServiceController = new RentBikeController();
		}
		if (interbankSystem != null) {
			rentBikeServiceController.interbankSystem = interbankSystem;
		}
		return rentBikeServiceController;
	}
	/**
	 * Initialize the controller for EcoBike rent bike service
	 */
	private RentBikeController() {
		this.listBikeTracker = new HashMap<String, BikeTracker>();
	}

	/**
	 * Rent a bike by a given card. This function will try to deduct money from the interbank, and if the transaction is successfuly,
	 * it will change the bike status and modify the database correspodingly
	 * @param bikeToRent The bike to be rented
	 * @param card the card to perform transaction
	 * @return true if process is OK, false otherwise
	 * @throws EcoBikeException
	 * @throws SQLException
	 * @throws IOException
	 */
	public boolean rentBike(Bike bikeToRent, CreditCard card) throws EcoBikeException, SQLException, IOException {
		
		// NOTE: Since we cannot connect to the service
		// We will immediately check the card here
		// For java 11 and bellow, please uncomment this
		if (!checkCardIdentity(card)) {
			return false;
		}
		
		PaymentTransaction transaction = interbankSystem.payDeposit(card, bikeToRent.getDeposit(), "PAY_DEPOSIT");
		
		if (transaction == null) {
			return false;
		}
		
		int rentID = DBUtils.addStartRentBikeRecord(bikeToRent.getBikeBarCode());
		transaction.setRentID(rentID);
		DBUtils.removeBikeFromDock(bikeToRent.getBikeBarCode());
		bikeToRent.getOutOfDock();

		BikeTracker newTracker = new BikeTracker(bikeToRent, rentID);
		DBUtils.addTransaction(transaction, rentID);
		newTracker.addTransaction(transaction);		
		newTracker.startCountingRentTime();
		DBUtils.changeBikeStatus(bikeToRent.getBikeBarCode(), Configs.BIKE_STATUS.RENTED.toString());
		this.listBikeTracker.put(bikeToRent.getBikeBarCode(), newTracker);
		return true;


	}
	
	private boolean checkCardIdentity(CreditCard card) {
		/**
		 * 135393_group01_2022
		 * Group 01
		 * 341
		 * 02/25
		 */
		if (!card.getCardHolderName().equalsIgnoreCase("Group 01")) {
			System.out.println(card.getCardHolderName());
			System.out.println("Name failed");
			return false;
		}
		
		if (!card.getCardNumber().equalsIgnoreCase("135393_group01_2022")) {
			System.out.println("card number failed");
			return false;
		}
		
		if (!card.getCardSecurity().equalsIgnoreCase("341")) {
			System.out.println("cvv failed");
			return false;
		}
		
		if (!card.getExpirationDate().equalsIgnoreCase("02/25")) {
			System.out.println("expiration date failed");
			return false;
		}
		System.out.println("Everything's ok");
		return true;
	}
	
	/**
	 * Pause rental of a bike
	 * @param bike the bike to pause rental
	 * @return
	 */
	public int pauseBikeRental(Bike bike) {
		BikeTracker tracker = this.listBikeTracker.get(bike.getBikeBarCode());
		if (tracker == null) {
			try {
				tracker = DBUtils.getCurrentBikeRenting(bike);
				if (tracker != null) {
					listBikeTracker.put(bike.getBikeBarCode(), tracker);
				}
			} catch (SQLException | EcoBikeException e) {
				e.printStackTrace();
			}
		}
		try {
			System.out.println("Going to stop counting rent time");
			tracker.stopCountingRentTime();
			bike.setCurrentStatus(Configs.BIKE_STATUS.PAUSED);
		} catch (SQLException | EcoBikeException e) {
			e.printStackTrace();
		}
		return tracker.getRentedTime();
		
	}
	
	public void resumeBikeRental(Bike bike) {
		BikeTracker tracker = this.listBikeTracker.get(bike.getBikeBarCode());
		if (tracker == null) {
			try {
				int rentPeriod = DBUtils.getCurrentRentPeriodOfBike(bike.getBikeBarCode());
				if (rentPeriod != 0) {
					// retrieve bike renting record
					tracker = DBUtils.getCurrentBikeRenting(bike);
					tracker.resumeCountingRentTime();
				}
			} catch (SQLException | EcoBikeException e) {
				e.printStackTrace();
			}
		}
		try {
			tracker.resumeCountingRentTime();
			bike.setCurrentStatus(Configs.BIKE_STATUS.RENTED);
		} catch (SQLException | EcoBikeException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Pay rental for a bike by a given card and return it to the selected dock. 
	 * This function will try to deduct money from the interbank, and if the transaction is successfuly,
	 * it will change the bike status and modify the database correspodingly
	 * @param bikeToRent The bike to be rented
	 * @param dockToReturn the dock to return the bike
	 * @param card the card to perform transaction
	 * @return true if process is OK, false otherwise
	 * @throws EcoBikeException
	 * @throws SQLException
	 * @throws IOException
	 */
	public Invoice returnBike(Bike bikeToRent, Dock dockToReturn, CreditCard card) throws IOException, SQLException, EcoBikeException, ParseException {
		if (!checkCardIdentity(card)) {
			return null;
		}
		
		BikeTracker tracker = this.listBikeTracker.get(bikeToRent.getBikeBarCode());
		if (tracker == null) {
			tracker = DBUtils.getCurrentBikeRenting(bikeToRent);
		}
		
		int period = tracker.stopCountingRentTime();
		float rentCost = calculateFee(bikeToRent.getRentFactor(), period);
		
		PaymentTransaction transaction = interbankSystem.payRental(card, rentCost, "PAY_RENTAL");
		
		if (transaction == null) {
			return null;
		}
		
		DBUtils.addEndRentBikeRecord(tracker.getRentID(), period);
		transaction.setRentID(tracker.getRentID());;
		DBUtils.addTransaction(transaction, tracker.getRentID());
		tracker.addTransaction(transaction);
		Invoice invoice = tracker.createInvoice(invoiceCounter);
		invoiceCounter++;
		DBUtils.changeBikeStatus(bikeToRent.getBikeBarCode(), Configs.BIKE_STATUS.FREE.toString());
		bikeToRent.goToDock(dockToReturn);
		DBUtils.addBikeToDock(bikeToRent.getBikeBarCode(), dockToReturn.getDockID());
		this.listBikeTracker.remove(bikeToRent.getBikeBarCode());
		return invoice;
	}
		
	
	
	private float calculateFee(float factor, int rentTime) {
		return RentalFactory.getRentalStrategy(rentTime).getRentalPrice(factor, rentTime);
	}
	
	/**
	 * Return rental fee of the selected bike
	 * @param bike the bike having rental fee to be queried
	 * @return the bike's rental fee if found the record in database; -1 otherwise
	 */
	public float getRentalFee(Bike bike)  {
		BikeTracker tracker = this.listBikeTracker.get(bike.getBikeBarCode());
		if (tracker == null) {
			try {
				tracker = DBUtils.getCurrentBikeRenting(bike);
				if (tracker == null) {
					return -1;
				}
			} catch (SQLException | EcoBikeException e) {
				e.printStackTrace();
			}
		}
		
		return calculateFee(bike.getRentFactor(), tracker.getRentedTime());
	}
}