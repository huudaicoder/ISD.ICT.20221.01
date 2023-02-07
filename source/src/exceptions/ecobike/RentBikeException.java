package exceptions.ecobike;

/**
 * Exceptions occurs during the renting, returning or pause bike rental process
 * @author daidh
 */
public class RentBikeException extends EcoBikeException {
	public enum RENT_BIKE_ERROR_CODE {
		ERROR_BIKE_BEING_RENTED,
		ERROR_BIKE_NOT_BEING_RENTED
	}

	private RENT_BIKE_ERROR_CODE errCode;
	public RentBikeException(String string) {
		super(string);
	}

	public RentBikeException(String string, RentBikeException.RENT_BIKE_ERROR_CODE err) {
		super(string);
		this.errCode = err;
	}


}
