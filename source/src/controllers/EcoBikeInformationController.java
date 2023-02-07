package controllers;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.SQLException;
import java.util.ArrayList;

import boundaries.RentBikeServiceBoundary;
import entities.NormalBike;
import entities.Bike;
import entities.Dock;
import entities.EBike;
import exceptions.ecobike.EcoBikeException;
import exceptions.ecobike.EcoBikeUndefinedException;
import exceptions.ecobike.NoInformationException;
import interfaces.RentBikeServiceInterface;
import utils.Configs;
import utils.DBUtils;

/**
 * This class is in charge of getting information from the database and returns it to the screen handler for displaying
 * @author daidh
 *
 */
public class EcoBikeInformationController extends EcoBikeBaseController implements PropertyChangeListener {
	private static EcoBikeInformationController ecoBikeInformationController;
	private RentBikeServiceInterface rentBikeServiceInterface;
	
	// structures to save information about docks and bikes
	// must be initialized at creation
	private ArrayList<Dock> listAllDocks = new ArrayList<Dock>();
	private ArrayList<Bike> listAllBikes = new ArrayList<Bike>();
	private ArrayList<Bike> listAllRentedBikes = new ArrayList<Bike>();
	private ArrayList<Bike> listAllFreeBikes = new ArrayList<Bike>();

	/**
	 * Get the controller for querying information about bikes and docks in the system. 
	 * One instance of program has only one instance of this controller
	 * @throws SQLException
	 * @throws EcoBikeException
	 */
	public static EcoBikeInformationController getEcoBikeInformationController() throws SQLException, EcoBikeException {
		if (ecoBikeInformationController == null) {
			ecoBikeInformationController = new EcoBikeInformationController();
//			DBUtils.initializeConfigs();
			ecoBikeInformationController.listAllDocks = DBUtils.getAllDock();
			// in this part, actually we should combine the factory pattern and move this line to "getBoundary..."
			// the passed param will be the name of the rent bike service, and the factory will return the corresponding rent bike service
			// EcoBike will become a third-party that helps users to connect multiple rent bike services
			
			// in this scenarios, we can let it be like this since we only have one service of Ecobike only;
			ecoBikeInformationController.rentBikeServiceInterface = new RentBikeServiceBoundary();
			// add all free bike
			for (Dock d: ecoBikeInformationController.listAllDocks) {
				for (Bike b: d.getAllBikeInDock()) {
					ecoBikeInformationController.listAllBikes.add(b);
					ecoBikeInformationController.listAllFreeBikes.add(b);
					b.addStatusObserver(ecoBikeInformationController);
				}
			}
			// get all rented bike, which is not in the dock
			ArrayList<Bike> rentedBikes = DBUtils.getAllRentedBike();
			for (Bike b: rentedBikes) {
				ecoBikeInformationController.listAllBikes.add(b);
				ecoBikeInformationController.listAllRentedBikes.add(b);
				b.addStatusObserver(ecoBikeInformationController);
			}
		}
		return ecoBikeInformationController;
	}
	
	/**
	 * Get the rent bike service of the application.
	 * In reality, when the EcoBike a third-party that helps users to connect multiple rent bike services,
	 * this will receive a tag and use the factory pattern to get the corresponding rent bike service.
	 * However, in this scenarios, we can let it be like this since we only have one service of Ecobike only;
	 * @return
	 */
	public static RentBikeServiceInterface getRentBikeService() {
		return ecoBikeInformationController.rentBikeServiceInterface;
	}
	
	public ArrayList<Dock> getAllDocks() {
		return this.listAllDocks;
	}
	
	public ArrayList<Bike> getAllBikes() {
		return this.listAllBikes;
	}
	
	public ArrayList<Bike> getAllRentedBikes() {
		return this.listAllRentedBikes;
	}
	
	public ArrayList<Bike> getAllFreeBikes() {
		return this.listAllFreeBikes;
	}
	
	/**
	 * Get information about a dock, given an ID
	 * @param dockID ID of the dock to be queried
	 * @return an instance of dock having the ID given
	 * @throws SQLException
	 * @throws EcoBikeException
	 */
	public Dock getDockInformationByID(int dockID) throws SQLException, EcoBikeException {
		if (String.valueOf(dockID) == null) {
			throw new NoInformationException("no keyword to search");
		}
		
		if (String.valueOf(dockID).length() == 0) {
			throw new NoInformationException("no keyword to search");
		}

		for (Dock d : listAllDocks) {
			if (d.getDockID() == dockID) {
				return d;
			}
		}
		return null;
	}

	/**
	 * Get information about a bike given a barcode
	 * @param barCode the barcode of the bike to be queried
	 * @return an instance of the bike having the given barcode
	 * @throws EcoBikeException
	 * @throws SQLException
	 */
	public Bike getBikeInformationByBarcode(String barCode) throws EcoBikeException, SQLException {
		if (String.valueOf(barCode) == null) {
			throw new NoInformationException("no keyword to search");
		}
		
		if (String.valueOf(barCode).length() == 0) {
			throw new NoInformationException("no keyword to search");
		}

		for (Bike b : listAllBikes) {
			if (b.getBikeBarCode().toLowerCase().contains(barCode.toLowerCase())) {
				return b;
			}
		}
		return null;
	}
	
	/**
	 * Return the first bike in the list result of bikes having keyword in their name 
	 * @param name keyword to query
	 * @throws SQLException
	 * @throws EcoBikeException
	 */
	public Bike getBikeInformationByName(String name) throws SQLException, EcoBikeException {
		if(String.valueOf(name) == null) {
			throw new NoInformationException("no keyword to search");
		}
		
		if(String.valueOf(name).length() == 0) {
			throw new NoInformationException("no keyword to search");
		}
		
		for (Bike b: listAllBikes) {
			if(b.getName().toLowerCase().contains(name.toLowerCase())) {
				return b;
			}
		}
		return null;
	}
	
	/**
	 * Return the first dock in the list result of docks having keyword in their name 
	 * @param name keyword to query
	 * @throws SQLException
	 * @throws EcoBikeException
	 */
	public Dock getDockInformationByName(String name) throws SQLException, EcoBikeException {
		if(String.valueOf(name) == null) {
			throw new NoInformationException("no keyword to search");
		}
		
		if(String.valueOf(name).length() == 0) {
			throw new NoInformationException("no keyword to search");
		}	
		
		for (Dock d: listAllDocks) {
			if (d.getName().toLowerCase().contains(name.toLowerCase())) {
				return d;
			}
		}
		return null;
	}
	
	/**
	 * Return location of the bike having the barcode
	 * @param bikeBarcode barcode of the bike to be queried
	 * @throws SQLException
	 * @throws EcoBikeException
	 */
	public String getBikeLocation(String bikeBarcode) throws SQLException, EcoBikeException {
		if(String.valueOf(bikeBarcode) == null) {
			throw new NoInformationException("no keyword to search");
		}
		
		if(String.valueOf(bikeBarcode).length() == 0) {
			throw new NoInformationException("no keyword to search");
		}	
		
		for (Bike b: listAllBikes) {
			if (b.getBikeBarCode().equals(bikeBarcode)) {
				return b.getCurrentDock().getName();
			}
		}
		return null;
	}
	
	/**
	 * Get current baterry status of the bike having the barcode
	 * @param bikeBarcode barcode to be queried
	 * @throws SQLException
	 * @throws EcoBikeException
	 */
	public float getBikeBattery(String bikeBarcode) throws SQLException, EcoBikeException {
		if(String.valueOf(bikeBarcode) == null) {
			throw new NoInformationException("no keyword to search");
		}
		
		if(String.valueOf(bikeBarcode).length() == 0) {
			throw new NoInformationException("no keyword to search");
		}	
		
		for (Bike b : this.listAllBikes) {
			if (b.getBikeType().equals(Configs.BikeType.EBike.toString())) {
				return (float) ((EBike) b).getBattery();
			}
		}
		return -1;
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		Object val = evt.getNewValue();
		if (val instanceof Configs.BIKE_STATUS) {
			Configs.BIKE_STATUS newBikeStatus = (Configs.BIKE_STATUS) val;
			Configs.BIKE_STATUS oldBikeStatus = (Configs.BIKE_STATUS) evt.getOldValue();
			Bike sourceBike = (Bike)evt.getSource();
			if (oldBikeStatus == Configs.BIKE_STATUS.FREE && newBikeStatus == Configs.BIKE_STATUS.RENTED) {
				this.listAllFreeBikes.remove(sourceBike);
				this.listAllRentedBikes.add(sourceBike);
			} else if (newBikeStatus == Configs.BIKE_STATUS.FREE) {
				this.listAllFreeBikes.add(sourceBike);
				this.listAllRentedBikes.remove(sourceBike);
			}
		}
		
	}
}
