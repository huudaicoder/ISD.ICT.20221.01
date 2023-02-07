package entities;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.sql.SQLException;
import java.util.ArrayList;

import org.json.JSONException;

import exceptions.ecobike.EcoBikeException;
import exceptions.ecobike.InvalidEcoBikeInformationException;
import utils.Configs;
import utils.DBUtils;
import utils.FunctionalUtils;
import utils.JSONUtils;

/**
 * This is the class for object entity Dock including all information of the dock
 * @author Bien
 *
 */
public class Dock implements PropertyChangeListener {

	/**
	 * name of the dock
	 */
	private String name;
	
	/**
	 * An unique string describes the id of the dock
	 */
	private int dockID;
	
	/**
	 * The address of the dock
	 */
	private String dockAddress;
	
	/**
	 * The area of the dock calculated in meter square
	 */
	private double dockArea;
	
	/**
	 * The number of available bikes in the dock that customers can rent
	 */
	private int numAvailableBike;

	/**
	 * The number of blank occupation for customers to return bike 
	 */
	private int numDockSpaceFree;
	
	private int totalSpace;
	
	private String dockImage;
	private PropertyChangeSupport propertyNotifier;
	
	private ArrayList<Bike> bikeInDock;
	
	public Dock(String name, int dockID, String dockAddress, double dock_area, int totalSpace, String dockImage) throws SQLException, EcoBikeException {
		this.setName(name);
		this.setDockID(dockID);
		this.setDockAddress(dockAddress);
		this.setDockArea(dock_area);
		this.totalSpace = totalSpace;
		this.numDockSpaceFree = totalSpace;
		this.setDockImage(dockImage);
		this.bikeInDock = new ArrayList<Bike>();
		this.propertyNotifier = new PropertyChangeSupport(this);
	}

	public void addObserver(PropertyChangeListener pcl) {
		this.propertyNotifier.addPropertyChangeListener(pcl);
	}
	
	public void removeObserver(PropertyChangeListener pcl) {
		this.propertyNotifier.removePropertyChangeListener(pcl);
	}
	
	public String getDockImage() {
		return dockImage;
	}

	private void setDockImage(String dockImage) {
		this.dockImage = dockImage;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) throws InvalidEcoBikeInformationException {
		if (name == null) {
			throw new InvalidEcoBikeInformationException("dock name parameter must not be null");
		}
		
		if (name.length() == 0) {
			throw new InvalidEcoBikeInformationException("dock must have a name");
		}
		
		if (!Character.isLetter(name.charAt(0))) {
			throw new InvalidEcoBikeInformationException("dock name must start with a letter");
		} 
		
		if (FunctionalUtils.contains(name, "[^a-z0-9 -_]")) {
			throw new InvalidEcoBikeInformationException("dock name can only contain letters, digits, space, hypen and underscore");
		}
		this.name = name;
	}

	public int getDockID() {
		return dockID;
	}

	public void setDockID(int dockID) {
		this.dockID = dockID;
	}

	public String getDockAddress() {
		return dockAddress;
	}

	public void setDockAddress(String dockAddress) throws InvalidEcoBikeInformationException {
		if (dockAddress == null) {
			throw new InvalidEcoBikeInformationException("dock address parameter must not be null");
		}
		
		if (dockAddress.length() == 0) {
			throw new InvalidEcoBikeInformationException("dock address must not empty");
		}
		
		if (!Character.isLetterOrDigit(dockAddress.charAt(0))) {
			throw new InvalidEcoBikeInformationException("dock address must start with a letter");
		} 
		
		if (FunctionalUtils.contains(dockAddress, "[^a-z0-9 -/,]")) {
			throw new InvalidEcoBikeInformationException("dock address can only contain letters, digits, space, hypen, comma and slash");
		}

		this.dockAddress = dockAddress;
	}

	public double getDockArea() {
		return dockArea;
	}

	public void setDockArea(double dockArea) throws InvalidEcoBikeInformationException {
		if (dockArea <= 0) {
			throw new InvalidEcoBikeInformationException("area of bike dock must be positive");
		}
		this.dockArea = dockArea;
	}

	public int getNumAvailableBike() {
		return numAvailableBike;
	}

	public void setNumAvailableBike(int numAvailableBike) throws InvalidEcoBikeInformationException {
		if (numAvailableBike < 0) {
			throw new InvalidEcoBikeInformationException("number of available bike in dock must be non-negative");
		}
		this.numAvailableBike = numAvailableBike;
	}

	public int getNumDockSpaceFree() {
		return numDockSpaceFree;
	}

	public void setNumDockSpaceFree(int numDockSpaceFree) throws InvalidEcoBikeInformationException {
		if (numDockSpaceFree < 0) {
			throw new InvalidEcoBikeInformationException("number of free space in dock must be non-negative");
		}
		this.numDockSpaceFree = numDockSpaceFree;
	}
	
	public String toString() {
		try {
			return JSONUtils.serializeDockInformation(this);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public ArrayList<Bike> getAllBikeInDock() {
		return this.bikeInDock;
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		Configs.BIKE_STATUS newStatus = (Configs.BIKE_STATUS) evt.getNewValue();
		System.out.println("Dock listened evt from bike");
		if (newStatus == Configs.BIKE_STATUS.RENTED) {
//			this.removeBikeFromDock((Bike)evt.getSource());
		}
	}
	
	public void addBikeToDock(Bike bike) {
		if (!this.bikeInDock.contains(bike)) {
			this.bikeInDock.add(bike);
			this.numDockSpaceFree -= 1;
			this.numAvailableBike += 1;
			this.propertyNotifier.firePropertyChange("numDockSpaceFree", this.numDockSpaceFree + 1, this.numDockSpaceFree);	
		}
	}
	
	public boolean isOKToAddBike() {
		System.out.println("Num free space of dock " + this.getName() + " is:" + this.getNumDockSpaceFree());
		return this.numDockSpaceFree > 0;
	}
	
	public void removeBikeFromDock(Bike bike) {
		if(this.bikeInDock.contains(bike)) {
			this.bikeInDock.remove(bike);
			this.numDockSpaceFree += 1;
			this.numAvailableBike -= 1;
			this.propertyNotifier.firePropertyChange("numDockSpaceFree", this.numDockSpaceFree - 1, this.bikeInDock);
		}
	}
	
	public int getTotalSpace() {
		return this.totalSpace;
	}
	
}
