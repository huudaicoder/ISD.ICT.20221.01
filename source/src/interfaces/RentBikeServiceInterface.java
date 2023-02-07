package interfaces;

import java.io.IOException;
import java.sql.SQLException;

import entities.Bike;
import entities.Dock;
import exceptions.ecobike.EcoBikeException;
import exceptions.ecobike.EcoBikeUndefinedException;
import exceptions.ecobike.RentBikeException;

/**
 * This interface allow communication between the information subsystem and the rent bike service subsystem
 * Any rent bike service must implement this interface as a loose connection between the subsystems
 * @author bienpt
 *
 */
public interface RentBikeServiceInterface {
	/**
	 * Perform rent bike process. Each rent bike service must implement their own process here
	 * @param bike The bike to be rented
	 * @throws RentBikeException
	 * @throws EcoBikeUndefinedException
	 * @throws IOException
	 * @throws EcoBikeException
	 * @throws SQLException
	 */
	public void rentBike(Bike bike) throws RentBikeException, EcoBikeUndefinedException, IOException, EcoBikeException, SQLException;

	/**
	 * Perform return bike process. Each rent bike service must implement their own process here
	 * @param bike
	 * @param dock
	 * @throws RentBikeException
	 * @throws EcoBikeUndefinedException
	 * @throws IOException
	 */
	public void returnBike(Bike bike, Dock dock) throws RentBikeException, EcoBikeUndefinedException, IOException;
	
	/**
	 * Perform pause bike rental process. Each rent bike service must implement their own process here
	 * @param bike
	 * @throws RentBikeException
	 * @throws EcoBikeUndefinedException
	 * @throws EcoBikeException
	 * @throws SQLException
	 */
	public void pauseBikeRental(Bike bike) throws RentBikeException, EcoBikeUndefinedException, EcoBikeException, SQLException;
	
	/**
	 * Perform resume bike rental process. Each rent bike service must implement their own process here
	 * @param bike
	 * @throws EcoBikeException
	 * @throws SQLException
	 */
	public void resumeBikeRental(Bike bike) throws EcoBikeException, SQLException;
}
