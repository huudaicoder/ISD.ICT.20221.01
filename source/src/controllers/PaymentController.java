package controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import entities.CreditCard;
import entities.Invoice;
import entities.PaymentTransaction;
import exceptions.ecobike.EcoBikeUndefinedException;
import exceptions.ecobike.RentBikeException;

/**
 * This is the class controller including all the methods and operations for payment use case
 * @author anhqn
 *
 */
public class PaymentController {
	public PaymentController() {}
	
	/**
	 * Pay for the deposit for the rental bike
	 * @param card  the credit card used to pay
	 * @param amount  the money to pay deposit
	 * @param content  the content of transaction
	 * @throws RentBikeException If the transaction is invalid
	 * @throws EcoBikeUndefinedException If there is an unexpected error occurs during the renting process
	 * @return the transaction entity ({@link entities.PaymentTransaction}
	 */
	public PaymentTransaction payDeposit(CreditCard card, double amount, String content) throws RentBikeException, EcoBikeUndefinedException {
		return null;
	}
	
	/**
	 * Return the deposit for the rental bike that was paid before
	 * @param card  the credit card used to pay
	 * @param amount  the money to pay deposit
	 * @param content  the content of transaction
	 * @throws RentBikeException If the transaction is invalid
	 * @throws EcoBikeUndefinedException If there is an unexpected error occurs during the renting process
	 * @return the transaction entity ({@link entities.PaymentTransaction}
	 */
	public PaymentTransaction returnDeposit(CreditCard card, double amount, String content) throws RentBikeException, EcoBikeUndefinedException {
		return null;
	}
	
	/**
	 * Pay for the for the rental bike
	 * @param card  the credit card used to pay
	 * @param amount  the money to pay deposit
	 * @param content  the content of transaction
	 * @throws RentBikeException If the transaction is invalid
	 * @throws EcoBikeUndefinedException If there is an unexpected error occurs during the renting process
	 * @return the transaction entity ({@link entities.PaymentTransaction}
	 */
	public void payRental(CreditCard card, double amount, String content) throws RentBikeException, EcoBikeUndefinedException {
		
	}
	
	/**
	 * Request to update payment method
	 * @param card  The credit card used to pay
	 * @throws RentBikeException If the card is invalid
	 * @throws EcoBikeUndefinedException If there is an unexpected error occurs during the renting process
	 */
	public void requestToUpdatePaymentMethod(CreditCard card) throws RentBikeException, EcoBikeUndefinedException {
		
	}
	
	/**
	 * Display a screen including transaction information
	 * @param transaction  The transaction entity ({@link entities.PaymentTransaction}
	 * @throws RentBikeException If the transaction is not valid
	 * @throws EcoBikeUndefinedException If there is an unexpected error occurs during the renting process
	 */
	public void displayTransactionInfo(PaymentTransaction transaction) throws RentBikeException, EcoBikeUndefinedException {
		
	}
	
	/**
	 * save the transaction to the database
	 * @param transaction  The transaction entity ({@link entities.PaymentTransaction}
	 * @throws RentBikeException If the transaction is invalid
	 * @throws EcoBikeUndefinedException If there is an unexpected error occurs during the renting process
	 */
	public void saveTransaction(PaymentTransaction transaction) throws RentBikeException, EcoBikeUndefinedException {
		
	}
	
	/**
	 * Create an invoice including all the information relevant to renting bike
	 * @param transaction  The transaction entity ({@link entities.PaymentTransaction}
	 * @throws RentBikeException If the transaction is not valid
	 * @throws EcoBikeUndefinedException If there is an unexpected error occurs during the renting process
	 * @return The invoice entity ({@link entities.Invoice}
	 */
	public Invoice createInvoice(PaymentTransaction transaction) throws RentBikeException, EcoBikeUndefinedException {
		return null;
	}
	
	/**
	 * Display a screen including the invoice information
	 * @param invoice  The Invoice entity ({@link entities.Invoice}
	 * @throws RentBikeException If the invoice is not valid
	 * @throws EcoBikeUndefinedException If there is an unexpected error occurs during the renting process
	 * @return The invoice entity ({@link entities.Invoice}
	 */
	public void displayInvoiceScreen(Invoice invoice) throws RentBikeException, EcoBikeUndefinedException {
		
	}
	
	/**
	 * save the invoice to the database
	 * @param transaction  The invoice entity ({@link entities.Invoice}
	 * @throws RentBikeException If the invoice is invalid
	 * @throws EcoBikeUndefinedException If there is an unexpected error occurs during the renting process
	 */
	public void saveInvoice(Invoice invoice) throws RentBikeException, EcoBikeUndefinedException {
		
	}
	
	public boolean validateCard(CreditCard card) throws RentBikeException, EcoBikeUndefinedException {
		
		return true;
	}
	
	public boolean validateCardNumber(String cardNumber) {
		if(cardNumber.length()!=10) return false;
		try {
			Integer.parseInt(cardNumber);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}
	
	public boolean validateCardHolderName(String cardHolderName) {
		return cardHolderName.matches("^[\\p{L} .'-]+$");
	}
	
	public boolean validateIssueBank(String issueBank) {
		return issueBank.equalsIgnoreCase("VCB") || issueBank.equalsIgnoreCase("AGB");
	}
	
	public boolean validateExdpirationDate(String expirationDate) {
		SimpleDateFormat format = new java.text.SimpleDateFormat("mm/yy");
        try {
            format.parse(expirationDate);
            return true;
        } catch (ParseException e) {
            return false;
        }
	}
	
	public boolean validateCardSecurity(String cardSecurity) {
		return cardSecurity.matches("^[0-9]{3,4}$");
	}
	
}