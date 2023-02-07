package exceptions.interbank;

/**
 * This is class exception for case invalid card.
 * <br>@author bienpt
 *
 */
@SuppressWarnings("serial")
public class InvalidCardException extends InterbankException {
	public InvalidCardException(String string) {
		super(string);
	}
}
