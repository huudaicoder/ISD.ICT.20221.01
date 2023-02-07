package views.screen;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ResourceBundle;
import boundaries.InterbankBoundary;
import controllers.RentBikeController;
import entities.Bike;
import entities.CreditCard;
import entities.strategies.DepositFactory;
import exceptions.ecobike.EcoBikeException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import utils.Configs;
import views.screen.popup.PopupScreen;

/**
 * This is the class handler for deposit screen
 *
 * @author Bienpt
 */
public class PayForDepositScreenHandler extends PaymentScreenHandler {
    @FXML
    private Label depositPrice;
    private static PayForDepositScreenHandler paymentScreenHandler;
    /**
     * Initialize handler for paying method screen of EcoBike application
     *
     * @param screenTitle Title of the screen
     * @param controller  Controller for handling request from the screen
     * @param prevScreen  An instance to the screen that called this screen
     * @throws IOException 
     */
    
    private Bike bikeToRent;
    
    private PayForDepositScreenHandler(Stage stage, String screenPath) throws IOException {
        super(stage, screenPath);
    }
    
    public static PayForDepositScreenHandler getPayForDepositScreenHandler(Stage stage, String screenPath, EcoBikeBaseScreenHandler prevScreen, Bike bike) throws IOException {
    	if (paymentScreenHandler == null) {
    		paymentScreenHandler = new PayForDepositScreenHandler(stage, screenPath);
    		paymentScreenHandler.setScreenTitle("Register payment method");
    	}
    	if (bike != null) {
    		paymentScreenHandler.bikeToRent = bike;
    	}
    	paymentScreenHandler.initialize();
    	return paymentScreenHandler;
    	
    }

    protected void initialize() {    
    	super.initializeComponent();
    	bikeImage.setImage(new Image(new File(Configs.BIKE_IMAGE_LIB + "/" + this.bikeToRent.getBikeImage()).toURI().toString()));
    	bikeName.setText(this.bikeToRent.getName());
    	bikeType.setText(this.bikeToRent.getBikeType());
    	depositPrice.setText(Double.toString(DepositFactory.getDepositStrategy().getDepositPrice((float)bikeToRent.getDeposit())) + this.bikeToRent.getCurrency());
    }
    
    /**
     * Get payment method information from the form and go to transaction screen
     * @throws IOException 
     * @throws SQLException 
     * @throws EcoBikeException 
     */
    public void confirmPaymentMethod() throws EcoBikeException, SQLException, IOException, ParseException {
    	if(validateInput()) {
    		System.out.println("Confirm successfully");
    		CreditCard card = new CreditCard(cardHolderName.getText(), cardNumber.getText(), "ACB", securityCode.getText(), expirationDate.getText());
    		// in reality, we will based on the issuing bank to create proper boundary 
    		InterbankBoundary interbank = new InterbankBoundary("ACB");
    		if (RentBikeController.getRentBikeServiceController(interbank).rentBike(bikeToRent, card)) {
    			PopupScreen.success("You have successfully rented bike "+ bikeToRent.getName());
    			this.stage.hide();
    		} else {
    			PopupScreen.error("Error performing transaction");
    		}
    	} else {
    		PopupScreen.error("Invalid input, please check again");
    	}
    }
}