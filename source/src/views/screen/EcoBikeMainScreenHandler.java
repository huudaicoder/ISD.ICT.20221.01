package views.screen;

import entities.Bike;
import entities.Dock;
import exceptions.ecobike.EcoBikeException;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import utils.Configs;
import views.screen.popup.PopupScreen;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import controllers.EcoBikeInformationController;

/**
 * This class creates a handler for displaying the map and getting customer's activities on the main screen
 *
 * @author daidh
 */
public class EcoBikeMainScreenHandler extends EcoBikeBaseScreenHandler {

    private static EcoBikeMainScreenHandler ecoBikeMainScreenHandler = null;
    @FXML
    private ImageView dock1;
    @FXML
    private ImageView dock2;
    @FXML
    private ImageView dock3;
    @FXML
    private ImageView dock4;
    @FXML
    private ImageView dock5;
    @FXML
    private ChoiceBox<String> choiceBox;
    @FXML
    private TextField searchBarField;
    @FXML
    private Button searchBtn;
   
    
    private EcoBikeMainScreenHandler(Stage stage, String screenPath) throws IOException {
        super(stage, screenPath);
    }

    /**
     * This class return an instance of main screen handler, initialize it with the stage and prevScreen
     *
     * @param stage         the stage to show this screen
     * @param prevScreen    the screen that call to this screen
     *
     */
    public static EcoBikeMainScreenHandler getEcoBikeMainScreenHandler(Stage stage, EcoBikeBaseScreenHandler prevScreen) {
        if (ecoBikeMainScreenHandler == null) {
            try {
                ecoBikeMainScreenHandler = new EcoBikeMainScreenHandler(stage, Configs.MAIN_SCREEN_PATH);
                EcoBikeInformationController.getEcoBikeInformationController();
                ecoBikeMainScreenHandler.setScreenTitle("Main screen");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (prevScreen != null) {
            ecoBikeMainScreenHandler.setPreviousScreen(prevScreen);
        }
        ecoBikeMainScreenHandler.initialize();
        return ecoBikeMainScreenHandler;
    }

   
    protected void initialize() {
    	String choices[] = {"Bike", "Dock"};
        choiceBox.setItems(FXCollections.observableArrayList(choices));
        choiceBox.setValue(choices[0]);

        dock1.setOnMouseClicked(e -> {
			try {
				showDock(1);
			} catch (NumberFormatException | SQLException | EcoBikeException e1) {
				e1.printStackTrace();
			}
		});
        dock2.setOnMouseClicked(e -> {
			try {
				showDock(2);
			} catch (NumberFormatException | SQLException | EcoBikeException e1) {
				e1.printStackTrace();
			}
		});
        dock3.setOnMouseClicked(e -> {
			try {
				showDock(3);
			} catch (NumberFormatException | SQLException | EcoBikeException e1) {
				e1.printStackTrace();
			}
		});
        dock4.setOnMouseClicked(e -> {
			try {
				showDock(4);
			} catch (NumberFormatException | SQLException | EcoBikeException e1) {
				e1.printStackTrace();
			}
		});
        dock5.setOnMouseClicked(e -> {
			try {
				showDock(5);
			} catch (NumberFormatException | SQLException | EcoBikeException e1) {
				e1.printStackTrace();
			}
		});
        
        searchBtn.setOnMouseClicked(e->{
			try {
				search();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});
    }

    /**
     * This is the method called when the user press search button.
     * @throws IOException 
     */
    private void search() throws IOException {
    	
    	String searchString = searchBarField.getText();
    	try {
    		 if (choiceBox.getValue().toString() == "Bike") {
    	        	Bike bike = EcoBikeInformationController.getEcoBikeInformationController().getBikeInformationByName(searchString);
    	            if (bike != null) {
    	            	BikeInformationScreenHandler.getBikeInformationScreenHandler(this.stage, this, bike).show();            	
    	            } else {
    	            	bike = EcoBikeInformationController.getEcoBikeInformationController().getBikeInformationByBarcode(searchString);
    	            	if (bike != null) {
    	            		BikeInformationScreenHandler.getBikeInformationScreenHandler(this.stage, this, bike).show();
    	            	}else {
    	            		PopupScreen.error("Cannot find bike with such name");
    	            	}    	
    	            }
    	        } else if (choiceBox.getValue().toString() == "Dock") {
    	        	Dock dock = EcoBikeInformationController.getEcoBikeInformationController().getDockInformationByName(searchString);
    	            if (dock != null) {
    	            	DockInformationScreenHandler.getDockInformationScreenHandler(this.stage, this, dock).show();
    	            } else {
    	            	int searchInt = Integer.parseInt(searchString);
    	            	dock = EcoBikeInformationController.getEcoBikeInformationController().getDockInformationByID(searchInt);
    	            	if (dock != null) {
        	            	DockInformationScreenHandler.getDockInformationScreenHandler(this.stage, this, dock).show();
    	            	}else {
    	            		PopupScreen.error("Cannot find dock with such name");
    	            	}    	
    	            	
    	            }
    	        }
    	} catch (Exception e) {
    		PopupScreen.error(e.getMessage());
    	}

       
    }

    /**
     * This is the method called when the user press dock icons on the map to view specific dock.
     * @throws EcoBikeException 
     * @throws SQLException 
     * @throws NumberFormatException 
     * @throws IOException 
     */
    private void showDock(int dockID) throws NumberFormatException, SQLException, EcoBikeException {
        Dock dock = EcoBikeInformationController.getEcoBikeInformationController().getDockInformationByID(dockID);
        if (dock == null) {
        	try {
				PopupScreen.error("Cannot find dock information...");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        DockInformationScreenHandler.getDockInformationScreenHandler(this.stage, this, dock).show();
    }
}
