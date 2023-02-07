package entities;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.sql.Date;
import utils.FunctionalUtils;
import exceptions.interbank.InvalidCardException;

/**
 * This is the class for object entity Credit card including all information of a card 
 * @author anhnq
 *
 */
public class CreditCard {
	
	/**
	 * The name of the holder of the card
	 */
	private String cardHolderName;
	
	/**
	 * The valid number of the card 
	 */
	private String cardNumber;
	
	/**
	 * The bank responsible for the card
	 */
	private String issueBank;
	
	/**
	 * The security code of the card
	 */
	private String cardSecurity;
	
	/**
	 * The money left in the card.
	 */
	private double balance;
	
	/**
	 * The expired date of the card in defined time format
	 */
	private java.util.Date expirationDate;
	
	
	public CreditCard(String cardHolderName,String cardNumber, String issueBank, String cardSecurity, String expirationDate) {
		super();
		this.setCardHolderName(cardHolderName);
		this.setCardNumber(cardNumber);
		this.setIssueBank(issueBank);
		this.setCardSecurity(cardSecurity);
		this.setExpirationDate(expirationDate);
	}
	public String getCardNumber() {
		return cardNumber;
	}
	
	public void setCardNumber(String cardNumber) throws InvalidCardException {
		if (cardNumber == null || cardNumber.length() == 0) {
			throw new InvalidCardException("card number must not be null");
		}
		
//		if (!FunctionalUtils.contains(cardNumber, "^[a-zA-Z0-9 ]")) {
//			throw new InvalidCardException("card number must not contains letters or special character");
//		}
		
		this.cardNumber = cardNumber;
	}
	public String getCardHolderName() {
		return cardHolderName;
	}
	public void setCardHolderName(String cardHolderName) {
		if (cardHolderName == null || cardHolderName.length() == 0) {
			throw new InvalidCardException("cardholder name must not be null");
		}
		System.out.println("Cardholder name is:"+cardHolderName);
		if (!FunctionalUtils.contains(cardHolderName, "^[a-zA-z ]")) {
			throw new InvalidCardException("card number must not contains digits or special characters");
		}
		this.cardHolderName = cardHolderName;
	}
	public String getIssueBank() {
		return issueBank;
	}
	public void setIssueBank(String issueBank) {
		if (issueBank == null || issueBank.length() == 0) {
			throw new InvalidCardException("issuing bank must not be null");
		}
		
		if (!FunctionalUtils.contains(issueBank, "^[a-zA-z ]")) {
			throw new InvalidCardException("issuing bank must not contains digits or special characters");
		}
		this.issueBank = issueBank;
	}
	public String getExpirationDate() {
		DateFormat dateFormatter = new SimpleDateFormat("mm/yy");
		return dateFormatter.format(expirationDate);
	}
	public void setExpirationDate(String expirationDate) {
		// TODO: need to redo this
		try {
			this.expirationDate = new SimpleDateFormat("mm/yy").parse(expirationDate);
		} catch (Exception e) {
			throw new InvalidCardException("invalid expiration date");
		}
	}
	public String getCardSecurity() {
		return cardSecurity;
	}
	public void setCardSecurity(String cardSecurity) {
		this.cardSecurity = cardSecurity;
	}
	public double getBalance() {
		return balance;
	}
}
