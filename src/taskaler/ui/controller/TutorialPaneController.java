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
//@author A0059806W
public class TutorialPaneController extends BorderPane implements IController {
    // Special Constants
    private static final int INDEX_OF_LAST_PANE = 0;
    private static final int DURATION_OF_ANIMATION = 1000;
    private static final double FULL_OPACITY = 1.0;
    private static final double NO_OPACITY = 0.0;
    private static final int END_STEP_PANE = 5;
    private static final int STEP_4_PANE = 4;
    private static final int STEP_3_PANE = 3;
    private static final int STEP_2_PANE = 2;
    private static final int STEP_1_PANE = 1;

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
        if (currentModel.page > INDEX_OF_LAST_PANE) {
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
        if (currentModel.page < END_STEP_PANE) {
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
        if (currentModel.page < END_STEP_PANE) {
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
        case STEP_1_PANE:
            currentVisiblePane = paneStep1;
            break;
        case STEP_2_PANE:
            currentVisiblePane = paneStep2;
            break;
        case STEP_3_PANE:
            currentVisiblePane = paneStep3;
            break;
        case STEP_4_PANE:
            currentVisiblePane = paneStep4;
            break;
        case END_STEP_PANE:
            currentVisiblePane = paneEnd;
            break;
        default:
            currentVisiblePane = paneWelcome;
            break;
        }

        FadeTransition fadeIn = new FadeTransition(
                Duration.millis(DURATION_OF_ANIMATION), currentVisiblePane);
        fadeIn.setFromValue(NO_OPACITY);
        fadeIn.setToValue(FULL_OPACITY);
        fadeIn.play();
        currentVisiblePane.setVisible(true);
    }

    /**
     * Method to reset the visibility of all panes
     * 
     */
    private void resetAllPane() {
        FadeTransition fadeOut = new FadeTransition(
                Duration.millis(DURATION_OF_ANIMATION), currentVisiblePane);
        fadeOut.setFromValue(FULL_OPACITY);
        fadeOut.setToValue(NO_OPACITY);
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
