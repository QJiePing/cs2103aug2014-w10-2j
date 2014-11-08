/**
 * 
 */
package taskaler.ui.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;

import taskaler.common.util.CommonLogger;
import taskaler.ui.model.TutorialPaneModel;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Class that acts as the controller for TutorialPaneView FXML. This class
 * renders the quick start interface
 * 
 * @author Cheah Kit Weng, A0059806W
 *
 */
// @author A0059806W
public class TutorialPaneController extends BorderPane implements IController {
    // Current model binded to this view
    private TutorialPaneModel currentModel = null;

    // Binded FXML Elements
    @FXML
    private AnchorPane paneWelcome;

    @FXML
    private AnchorPane paneStep1;

    @FXML
    private AnchorPane paneStep2;

    @FXML
    private AnchorPane paneStep3;

    @FXML
    private AnchorPane paneStep4;

    @FXML
    private AnchorPane paneEnd;

    // Class variable
    private AnchorPane currentVisiblePane;

    /**
     * Default Overloaded Constructor
     * 
     * @param model
     *            Model to bind this view to
     * @throws IOException
     *             Thrown if an error is encountered while creating the view
     */
    public TutorialPaneController(TutorialPaneModel model) throws IOException {
        currentModel = model;

        initialize(common.FXML_TUTORIAL_PANE);

        update();
    }

    /**
     * Method to bind the prev button to this handler
     * 
     * @param e
     *            Mouse event caught
     */
    @FXML
    private void onPrevBtnClicked(MouseEvent e) {
        if (currentModel.page > 0) {
            currentModel.page--;
            try {
                update();
            } catch (IOException error) {
                CommonLogger.getInstance().exceptionLogger(error, Level.SEVERE);
            }
        }
    }

    /**
     * Method to bind the next button to this handler
     * 
     * @param e
     *            Mouse event caught
     */
    @FXML
    private void onNextBtnClicked(MouseEvent e) {
        if (currentModel.page < 4) {
            currentModel.page++;
            try {
                update();
            } catch (IOException error) {
                CommonLogger.getInstance().exceptionLogger(error, Level.SEVERE);
            }
        } else {
            exitTutorial();
        }

    }

    /**
     * Method to bind the exit button to this handler
     * 
     * @param e
     *            Mouse event caught
     */
    @FXML
    private void onExitBtnClicked(MouseEvent e) {
        exitTutorial();
    }

    /**
     * Method to bind to the enter key to the Next action
     * 
     * @param e
     *            Action event caught
     */
    @FXML
    private void onNextAction(ActionEvent e) {
        if (currentModel.page < 5) {
            currentModel.page++;
            try {
                update();
            } catch (IOException error) {
                CommonLogger.getInstance().exceptionLogger(error, Level.SEVERE);
            }
        } else {
            exitTutorial();
        }
    }

    /**
     * Method to bind the esc key to the exit action
     * 
     * @param e
     *            Action event caught
     */
    @FXML
    private void onExitAction(ActionEvent e) {
        exitTutorial();
    }

    /**
     * Method to close this stage
     * 
     */
    private void exitTutorial() {
        Stage stage = (Stage) this.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(String FXML) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(FXML));
        loader.setRoot(this);
        loader.setController(this);
        loader.load();
    }

    @Override
    public void update() throws IOException {
        resetAllPane();

        switch (currentModel.page) {
        case 1:
            currentVisiblePane = paneStep1;
            break;
        case 2:
            currentVisiblePane = paneStep2;
            break;
        case 3:
            currentVisiblePane = paneStep3;
            break;
        case 4:
            currentVisiblePane = paneStep4;
            break;
        case 5:
            currentVisiblePane = paneEnd;
            break;
        default:
            currentVisiblePane = paneWelcome;
            break;
        }

        FadeTransition fadeIn = new FadeTransition(Duration.millis(1000),
                currentVisiblePane);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.play();
        currentVisiblePane.setVisible(true);
    }

    /**
     * Method to reset the visibility of all panes
     * 
     */
    private void resetAllPane() {
        FadeTransition fadeOut = new FadeTransition(Duration.millis(1000),
                currentVisiblePane);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        fadeOut.play();
        paneWelcome.setVisible(false);
        paneStep1.setVisible(false);
        paneStep2.setVisible(false);
        paneStep3.setVisible(false);
        paneStep4.setVisible(false);
        paneEnd.setVisible(false);
    }

    @Override
    public HashMap<String, String> getState() {
        return null;
    }
}
