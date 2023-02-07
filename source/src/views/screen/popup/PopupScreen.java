package views.screen.popup;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import controllers.EcoBikeBaseController;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import utils.Configs;
import views.screen.EcoBikeBaseScreenHandler;
/**
 * This is the class hanlder for pop up screen.
 * <br>@author anhnq
 *
 */
public class PopupScreen extends EcoBikeBaseScreenHandler {

  @FXML
  ImageView tickicon;

  @FXML
  Label message;

  public PopupScreen(Stage stage) throws IOException {
	  // TODO: consider this
    super(stage, Configs.POPUP_SCREEN_PATH);
  }

  private static PopupScreen popup(String message, String imagepath,
      Boolean undecorated) throws IOException {
    PopupScreen popup = new PopupScreen(new Stage());
    if (undecorated) {
      popup.stage.initStyle(StageStyle.UNDECORATED);
    }
    popup.message.setText(message);
    popup.setImage(imagepath);
    popup.initialize();
    return popup;
  }
  
  protected void initialize() {
	  
  }

  public static void success(String message) throws IOException {
    popup(message, Configs.IMAGE_PATH + "/" + "tickgreen.png", true).show(true);
  }

  public static void error(String message) throws IOException {
    popup(message, Configs.IMAGE_PATH + "/" + "tickerror.png", false).show(false);
  }

  public static PopupScreen loading(String message) throws IOException {
    return popup(message, Configs.IMAGE_PATH + "/" + "loading.gif", true);
  }

  public void setImage(String path) {
    super.setImage(tickicon, path);
  }

  /**
   * This is the method to show the popup screen.
   * <br>@param autoclose  the boolean variable to check if auto close or not
   */
  public void show(Boolean autoclose) {
    super.show();
    if (autoclose) {
      close(0.8);
    }
  }

  public void show(double time) {
    super.show();
    close(time);
  }

  /**
   * This is the method to delay a time value before closing the screen.
   * <br>@param time  the time value to delay before closing the screen
   */
  public void close(double time) {
    PauseTransition delay = new PauseTransition(Duration.seconds(time));
    delay.setOnFinished(event -> stage.close());
    delay.play();
  }
}
