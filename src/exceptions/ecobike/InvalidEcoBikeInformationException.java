package exceptions.ecobike;

@SuppressWarnings("serial")
public class InvalidEcoBikeInformationException extends EcoBikeException {
	
	private ECOBIKE_INFORMATION_ERROR_CODE errCode;

	public InvalidEcoBikeInformationException(String string, ECOBIKE_INFORMATION_ERROR_CODE err) {
		super(string);
		this.errCode = err;
	}
	
	public InvalidEcoBikeInformationException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}
	
	public enum ECOBIKE_INFORMATION_ERROR_CODE {
		ERROR_NULL_INFORMATION,
		ERROR_INVALID_FORMAT,
		ERROR_INVALID_PRICE,
	}
	
}
