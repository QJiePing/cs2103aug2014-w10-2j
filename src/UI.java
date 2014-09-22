import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

/**
 * Main Class to create the required UI for the application
 * This class also serves as the Action Event Controller for the UI.
 * i.e. OnClick Events
 * 
 * @author Cheah Kit Weng, A0059806W
 *
 */
public class UI extends Application {
    
    private static final String TITLE = "Taskaler";
    private static final String ICON_PNG = "/images/icon.png";
    private static final String FXML_ROOT = "/fxml/root.fxml";
    
    @FXML
    private Pane BrdrPaneDisplay;
    
    @FXML
    private TextField TxtCmdInput;
    
    /**
     * Method to start rendering the UI elements of Taskaler
     * 
     * @param stage Main stage to render UI
     */
    @Override
    public void start(Stage stage) {
        try {
            
            java.io.File f = new java.io.File("");
            System.out.println(getClass().getResource(FXML_ROOT).toString());
            
            stage.getIcons().add(new Image(getClass().getResourceAsStream(ICON_PNG)));
            stage.setTitle(TITLE);
            
            FXMLLoader root = new FXMLLoader(getClass().getResource(FXML_ROOT));
            root.setController(this);
            Parent pane = root.load();
            
            Scene scene = new Scene(pane,400,400);
            
            //scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            stage.setScene(scene);
            stage.show();
            
            
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void TxtCmdInputKeyPressed(KeyEvent e){
        if(e.getCode() == KeyCode.ENTER){
            BrdrPaneDisplay.getChildren().clear();
            Label lblTest = new Label("Enter Detected: "+ TxtCmdInput.getText());
            TxtCmdInput.setText("");
            BrdrPaneDisplay.getChildren().add(lblTest);
        }else{
            if(!BrdrPaneDisplay.getChildren().isEmpty()){
                BrdrPaneDisplay.getChildren().clear();
            }
        }
    }
}