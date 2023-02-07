package exceptions.interbank;

/**
 * Exceptions on the interbank side
 * <br>@author daidh
 */

@SuppressWarnings("serial")
public class InterbankException extends RuntimeException {
	public InterbankException(String message) {
		super(message);
	}
}
