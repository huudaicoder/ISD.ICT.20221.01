package exceptions.ecobike;

/**
 * Exceptions occurs in Ecobike classes (does not includes the interbank side)
 */
public class EcoBikeException extends Exception {
	public EcoBikeException(String message) {
		super(message);
	}
}
