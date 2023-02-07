package entities;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import exceptions.ecobike.EcoBikeException;
import utils.DBUtils;
import utils.FunctionalUtils;

/**
 * A bike tracker keep information about a bike's rental. 
 * It has a counter for couting the bike's rental time in current session, and
 * a list of transactions performed on the rental.
 * It also provides methods for interacting with the bike's renting session
 *
 */
public class BikeTracker {
	private TimeCounter timeCounter;
	private Bike bike;
	private Date startTime, endTime;
	private int rentID;
	private int timeRented;
	private ArrayList<PaymentTransaction> transactionList;
	private Invoice invoice;
	
	public BikeTracker(Bike bike, int rentID) {
		this.bike = bike;
		this.rentID = rentID;
		this.timeRented = 0;
		this.timeCounter = new TimeCounter();
		this.transactionList = new ArrayList<PaymentTransaction>();	
	}
	
	public void startCountingRentTime() throws SQLException, EcoBikeException {
		this.startTime = timeCounter.startCounter();
	}
	
	public int stopCountingRentTime() throws SQLException, EcoBikeException {
		this.endTime = timeCounter.stopCounter();
		this.timeRented = (int)((this.endTime.getTime() - this.startTime.getTime()) / (1000 * 60) %60);
		System.out.println("Start time: " + startTime.toString());
		System.out.println("End time: " + endTime.toString());
		System.out.println("Rent period is: " + this.timeRented);
		DBUtils.saveRentPeriod(rentID, this.timeRented);
		return this.timeRented;
	}
	
	public void resumeCountingRentTime() throws SQLException, EcoBikeException {
		timeCounter.startCounter();
	}
	
	public void addTransaction(PaymentTransaction trans) throws SQLException, EcoBikeException {
		if (!this.transactionList.contains(trans)) {
			this.transactionList.add(trans);			
		}
	}
	
	public void setRentedTime(int time) {
		this.timeRented = time;
	}
	
	public void setStartTime(String time) {
		try {
			this.startTime = FunctionalUtils.stringToDate(time);
		} catch (EcoBikeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public int getRentedTime() {
		return this.timeRented;
	}
	
	public int getRentID() {
		return this.rentID;
	}
	
	public Invoice createInvoice(int invoiceID) throws SQLException, EcoBikeException {
		this.invoice = new Invoice(invoiceID, this.bike, this.startTime, this.endTime, this.timeRented);
		for (PaymentTransaction trans : this.transactionList) {
			this.invoice.addTransaction(trans);
		}
		return this.invoice;
	}
	
	public Invoice getInvoice() {
		return this.invoice;
	}
	
	public void setActive(boolean activate) {
		this.timeCounter.setActive(activate);
	}

}
