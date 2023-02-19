package views.screen;

import controllers.EcoBikeInformationController;
import exceptions.ecobike.EcoBikeException;
import entities.Bike;
import entities.Dock;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import utils.Configs;
import views.screen.popup.BikeInDockHandler;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * This class creates a handler for getting behaviors of customer on dock information screen
 *
 * @author anhnq
 */
public class DockInformationScreenHandler extends EcoBikeBaseScreenHandler implements PropertyChangeListener {

    private static DockInformationScreenHandler dockInformationScreenHandler = null;
    private Dock currentDock = null;
    private ArrayList<Bike> currentBikeList = null;

    @FXML
    private ImageView dockImageView;
    @FXML
    private Label dockNameText;
    @FXML
    private Label dockAddressText;
    @FXML
    private Label dockAreaText;
    @FXML
    private Label dockCount;
    @FXML
    private Label availableBikeCount;
    @FXML
    private Label availableDocksCount;
    @FXML
    private Label distance;
    @FXML
    private Label estimateWalkTime;
    @FXML
    private Button returnBikeButton;
    @FXML
    private VBox bikeVBox;
    @FXML
    private ImageView mainScreenIcon;
    @FXML
    private ImageView backIcon;

    private DockInformationScreenHandler(Stage stage, String screenPath, EcoBikeBaseScreenHandler prevScreen) throws IOException {
        super(stage, screenPath);
        this.setPreviousScreen(prevScreen);
    }

    /**
     * This class return an instance of dock screen handler, initialize it with the stage, prevScreen, dock and bikeList
     *
     * @param stage         the stage to show this screen
     * @param prevScreen    the screen that call to this screen
     * @param dock          the dock to render this screen, provide null if update is not needed
     *
     */
    public static DockInformationScreenHandler getDockInformationScreenHandler(Stage stage, EcoBikeBaseScreenHandler prevScreen, Dock dock) {
    	if (dockInformationScreenHandler == null) {
            try {
                dockInformationScreenHandler = new DockInformationScreenHandler(stage, Configs.VIEW_DOCK_SCREEN_PATH, prevScreen);
                dockInformationScreenHandler.setScreenTitle("Dock information screen");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (prevScreen != null) {
            dockInformationScreenHandler.setPreviousScreen(prevScreen);
        }

        if (dock != null) {
            dockInformationScreenHandler.currentDock = dock;
            dockInformationScreenHandler.currentDock.addObserver(dockInformationScreenHandler);
        }

        ArrayList<Bike> bikeList = dockInformationScreenHandler.currentDock.getAllBikeInDock();
        if (bikeList != null && bikeList.size() != 0) {
            dockInformationScreenHandler.currentBikeList = bikeList;
        }
        dockInformationScreenHandler.initialize();
        return dockInformationScreenHandler;
    }

    protected void initialize() {    	
        returnBikeButton.setOnMouseClicked(e -> {
			try {
				returnBike();
			} catch (IOException | SQLException | EcoBikeException e1) {
				e1.printStackTrace();
			}
		});
        mainScreenIcon.setOnMouseClicked(e -> EcoBikeMainScreenHandler.getEcoBikeMainScreenHandler(this.stage, null).show());
        backIcon.setOnMouseClicked(e -> {
            if (this.getPreviousScreen() != null)
                this.getPreviousScreen().show();
        });
        
        renderDockInformation();
    }

    /**
     * This is the method to render the screen with data.
     */
    private void renderDockInformation() {
    	if (currentDock.getDockImage() != null && currentDock.getDockImage().length() != 0) {
    		dockImageView.setImage(new Image(new File(Configs.DOCK_IMAGE_LIB + "/" + currentDock.getDockImage()).toURI().toString()));
    	}
        dockNameText.setText(currentDock.getName());
        dockAddressText.setText(currentDock.getDockAddress());
        dockAreaText.setText(currentDock.getDockArea() + " m2");
        dockCount.setText(currentDock.getTotalSpace() + "");
        availableDocksCount.setText(currentDock.getNumDockSpaceFree() + "");
        availableBikeCount.setText(currentDock.getNumAvailableBike() + "");
        distance.setText("2 km");
        estimateWalkTime.setText("20 mins");
        addBike(currentBikeList);
        
    	if (currentDock.isOKToAddBike()) {
    		returnBikeButton.setDisable(false);
    	} else {
    		returnBikeButton.setDisable(true);
    	}
    }

    /**
     * This is the method called when the user press button return bike.
     * @throws EcoBikeException 
     * @throws SQLException 
     * @throws IOException 
     */
    private void returnBike() throws IOException, SQLException, EcoBikeException {
    	SelectBikeToReturnScreenHandler bikeToReturnHandler = new SelectBikeToReturnScreenHandler(new Stage(), this, EcoBikeInformationController.getEcoBikeInformationController().getAllRentedBikes(), this.currentDock);
    	bikeToReturnHandler.show();
    }

    /**
     * This is the method to render bike list in the dock screen.
     */
    private void addBike(ArrayList<Bike> bikeList) {
    	bikeVBox.getChildren().clear();
    	for (Bike b : bikeList) {
    		BikeInDockHandler bikeHandler;
			try {
				bikeHandler = new BikeInDockHandler(this.stage, b, Configs.BIKE_IN_DOCK_PATH, this);
				bikeVBox.getChildren().add(bikeHandler.getContent());
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}
    }

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		System.out.println("The dock has changed....");
		renderDockInformation();
	}


}
