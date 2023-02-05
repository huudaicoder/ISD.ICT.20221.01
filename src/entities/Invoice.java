package entities;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import exceptions.ecobike.InvalidEcoBikeInformationException;

/**
 * An invoice saves information about transactions performed on a rental,
 * including the bike rented, ID of the rental, transactions for the rental,
 * and some other information related to the rental
 */
public class Invoice {
	
	/**
	 * The id of the invoice
	 */
	private int invoiceID;
	
	private Date dateIssued;
	
	/**
	 * The barcode of the bike customers rent
	 */
	private Bike bike;
	
	private ArrayList<PaymentTransaction> listTransaction;
	
	/**
	 * The amount of deposit customers have to pay before renting the bike
	 */
	private double deposit;
	
	/**
	 * The time customers begin to rent bike 
	 */
	private Date startTime;
	
	/**
	 * The time customers return bike
	 */
	private Date endTime;
	
	private int totalRentTime;
	
	/**
	 * The total money customers have to pay including sub-total and VAT
	 */
	private double total;
	
	private int rentID;
	
	public int getRentID() {
		return rentID;
	}

	public void setRentID(int rentID) {
		this.rentID = rentID;
	}

	/**
	 * Creates a new empty invoice to fill in information later
	 */
	public Invoice() {
		listTransaction = new ArrayList<PaymentTransaction>();
	}

	/**
	 * Creates a new invoice with basic information
	 * @param invoiceID ID of the invoice. This must be unique in the database
	 * @param bike The bike corresponding to this invoice
	 * @param startTime The time rental started
	 * @param endTime The time rental ended
	 * @param totalRentTime Total rent time. This might be not as the difference of end time and start time
	 * @throws InvalidEcoBikeInformationException
	 */
	public Invoice(int invoiceID, Bike bike, Date startTime, Date endTime, int totalRentTime) throws InvalidEcoBikeInformationException {
		listTransaction = new ArrayList<PaymentTransaction>();
		this.setInvoiceID(invoiceID);
		this.setBike(bike);
		this.setTotalRentTime(totalRentTime);
		this.dateIssued = Calendar.getInstance().getTime();
		this.rentID = -1;
	}
	
	public String getIssuedDate() {
		return this.dateIssued.toString();
	}
	
	private void setTotalRentTime(int time) {
		this.totalRentTime = time;
	}
	
	public int getTotalRentTime() {
		return this.totalRentTime;
	}
	
	private void setBike(Bike bike) {
		this.bike = bike;
	}
	
	/**
	 * Add transaction to list of transaction of this invoice
	 * If the transaction is already added, it will not be added again
	 * @param transaction The transaction to be added
	 */
	public void addTransaction(PaymentTransaction transaction) {
		if (this.listTransaction.contains(transaction)) {
			System.out.println("Hey the transaction is duplicated!:" + transaction.getTransactionId());
			return;
		}
		this.listTransaction.add(transaction);
		if (this.rentID == -1) {
			this.rentID = transaction.getRentID();
		}
		this.setTotal();
	}
	
	/**
	 * Remove a transaction from the invoice if it exists
	 * @param transaction The transaction to be removed
	 */
	
	public void removeTransaction(PaymentTransaction transaction) {
		if (this.listTransaction.contains(transaction)) {
			this.listTransaction.remove(transaction);				
		}
	}
	
	public ArrayList<PaymentTransaction> getTransactionList() {
		return this.listTransaction;
	}

	public int getInvoiceID() {
		return invoiceID;
	}

	public Bike getBike() {
		return this.bike;
	}

	public double getDeposit() {
		return deposit;
	}
	
	public void setDeposit(double deposit) {
		this.deposit = deposit;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) throws InvalidEcoBikeInformationException {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Date endTime) throws InvalidEcoBikeInformationException {
		this.endTime = endTime;
	}

	public double getTotal() {
		return total;
	}

	private void setTotal() {
		this.total = 0;
		for (PaymentTransaction trans : this.listTransaction) {
			if(trans.getContent().equalsIgnoreCase("PAY_RENTAL"))
				this.total -= trans.getAmount();
			else
				this.total += trans.getAmount();
		}
	}

	public void setInvoiceID(int invoiceID) {
		this.invoiceID = invoiceID;
	}
	
}