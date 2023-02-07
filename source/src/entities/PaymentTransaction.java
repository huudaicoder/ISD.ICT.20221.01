package entities;

import java.util.Calendar;
import java.util.Date;

import exceptions.ecobike.InvalidEcoBikeInformationException;
import exceptions.interbank.InvalidCardException;
import utils.FunctionalUtils;

/**
 * A record of a transaction, including ID of the transaction, credit card number of the card performing the transaction,
 * amount of money transfered, and details about the transaction.
 * This transaction might be for deposit, rental or refund
 *
 */
public class PaymentTransaction {
	/**
	 * The id of transaction. This must be a unique String
	 */
	private String transactionId;
	
	/**
	 * The Credit card entity including all information about the credit card used to pay
	 * {@link entity.CreditCard}
	 */
	private String creditCardNumber;
	
	/**
	 * A double value describes the amount of money to make a transaction. The currency is VND
	 */
	private double amount;
	
	/**
	 * The time transaction is made with the a defined format
	 */
	private Date transactionTime;
	
	/**
	 * The content of the transaction
	 */
	private String content;
	
	/**
	 * The message describes the error if there is any exception
	 */
	private String errorMessage;
	
	private int rentID;
	
	/**
	 * Create a transaction for filling in information later
	 */
	public PaymentTransaction() {
		
	}
	
	/**
	 * Create a transaction with basic information
	 * @param transactionId ID of the transaction. This must be unique in the database
	 * @param creditCardNumber The credit card associated with this transaction
	 * @param amount The amount of transaction
	 * @param content Information of the transaction
	 * @throws InvalidEcoBikeInformationException
	 */
	public PaymentTransaction(int transactionId, String creditCardNumber, double amount, String content) throws InvalidEcoBikeInformationException {
		this.setAmount(amount);
		this.setTransactionId(transactionId);
		this.setCreditCardNumber(creditCardNumber);
		this.setContent(content);
		this.setTransactionTime("");
	}
	
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(int transactionId) {
		this.transactionId = Integer.toString(transactionId);
	}
	public String getCreditCardNumber() {
		return this.creditCardNumber;
	}
	public void setCreditCardNumber(String creditCardNumber) {
		if (creditCardNumber == null || creditCardNumber.length() == 0) {
			throw new InvalidCardException("card number must not be null");
		}
		
		if (FunctionalUtils.contains(creditCardNumber, "^[0-9 ]")) {
			throw new InvalidCardException("card number must not contains letters or special character");
		}
		this.creditCardNumber = creditCardNumber;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public Date getTransactionTime() {
		return transactionTime;
	}
	public void setTransactionTime(String transactionTime) throws InvalidEcoBikeInformationException {
		try {			
			if (transactionTime == null || transactionTime.length() == 0) {
				this.transactionTime = FunctionalUtils.stringToDate(Calendar.getInstance().getTime().toString());			
			} else {
				this.transactionTime = FunctionalUtils.stringToDate(transactionTime);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new InvalidEcoBikeInformationException("invalid time format");
		}
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	/**
	 * Set the rental ID associated with the transaction
	 * @param rentID
	 */
	public void setRentID(int rentID) {
		this.rentID = rentID;
	}
	
	/**
	 * Get the rental ID associated with the transaction
	 * @return
	 */
	public int getRentID() { 
		return this.rentID;
	}
	
}