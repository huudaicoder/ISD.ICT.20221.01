package interfaces;

import entities.CreditCard;
import entities.PaymentTransaction;

/**
 * This interface provides functions that the interbank subsystem must be implemented for its communication with other subsystem
 * Any interbank must implement this interface as a loose connection between the subsystems
 * @author anhqn
 *
 */
public interface InterbankInterface {
	/**
	 * Perform paying deposit process, with a given credit card and and amount of money to deduct
	 * @param creditCard the card being deducted
	 * @param amount the amount of money to be deducted
	 * @param content information about the transfer
	 * @return A <b><i>PaymentTransaction</i></b> record about the transaction <br> <i>null</i> if the transaction fails
	 */
	public PaymentTransaction payDeposit(CreditCard creditCard, double amount, String content);

	/**
	 * Perform returning deposit process, with a given credit card and and amount of money to return
	 * @param creditCard the card getting the return
	 * @param amount the amount of money to be returned
	 * @param content information about the transfer
	 * @return A <b><i>PaymentTransaction</i></b> record about the transaction <br> <i>null</i> if the transaction fails
	 */
	public PaymentTransaction returnDeposit(CreditCard creditCard, double amount, String content);
	
	/**
	 * Perform paying rental process, with a given credit card and and amount of money to deduct
	 * @param creditCard the card being deducted
	 * @param amount the amount of money to be deducted
	 * @param content information about the transfer
	 * @return A <b><i>PaymentTransaction</i></b> record about the transaction <br> <i>null</i> if the transaction fails
	 */
	public PaymentTransaction payRental(CreditCard creditCard, double amount, String content);
	
	/**
	 * Get current balance of a given credit card.
	 * @param creditCard The card with the balance to be queried
	 * @return the current balance of the card
	 */
	public double getBalance(CreditCard creditCard);
}
