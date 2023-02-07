package boundaries;

import java.io.IOException;
import java.sql.SQLException;

import controllers.RentBikeController;
import entities.Bike;
import entities.Dock;
import entities.NormalBike;
import exceptions.ecobike.EcoBikeException;
import exceptions.ecobike.EcoBikeUndefinedException;
import exceptions.ecobike.RentBikeException;
import interfaces.RentBikeServiceInterface;
import javafx.stage.Stage;
import utils.Configs;
import views.screen.EcoBikeBaseScreenHandler;
import views.screen.PayForDepositScreenHandler;
import views.screen.PayForRentScreenHandler;
import views.screen.popup.PopupScreen;

/**
 * This class is a real communicator of the rent bike subsystem
 */
public class RentBikeServiceBoundary implements RentBikeServiceInterface {
	public RentBikeServiceBoundary() {
		super();
	}
	
	public void rentBike(Bike bike) throws IOException, EcoBikeException, SQLException {
		PayForDepositScreenHandler paymentScreenHandler = PayForDepositScreenHandler.getPayForDepositScreenHandler(new Stage(), Configs.PAYING_FOR_DEPOSIT_SCREEN_PATH, null, bike);
		paymentScreenHandler.show();
	}
	
	public void returnBike(Bike bike, Dock dock) throws RentBikeException, EcoBikeUndefinedException, IOException {
		PayForRentScreenHandler paymentScreenHandler = PayForRentScreenHandler.getPayForRentScreenHandler(new Stage(), Configs.PAYING_FOR_RENTAL_SCREEN_PATH, null, bike, dock);
		paymentScreenHandler.show();
	}
	
	public void pauseBikeRental(Bike bike) throws EcoBikeException, SQLException {
		RentBikeController.getRentBikeServiceController(null).pauseBikeRental(bike);	
	}
	
	public void resumeBikeRental(Bike bike) throws EcoBikeException, SQLException {
		RentBikeController.getRentBikeServiceController(null).resumeBikeRental(bike);	
	}
}
