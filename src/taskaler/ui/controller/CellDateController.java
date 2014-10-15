/**
 * Package that contains all controllers used by the UI Component
 */
package taskaler.ui.controller;

import taskaler.ui.model.CellDateModel;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

/**
 * Class that acts as the controller for cellDate FXML. This class modifies a
 * single cell of the calendar
 * 
 * @author Cheah Kit Weng, A0059806W
 *
 */
public class CellDateController extends AnchorPane implements IController{
    
    // Current model associated with this controller
    private CellDateModel currentModel = null;
    
    // Special Constants
    private static final String EMPTY_STRING = "";
    private static final String PLUS_STRING = "+";
    private static final int MAX_NUMBER_OF_TASKS_FOR_DISPLAY = 9;
    private static final int MIN_NUMBER_OF_TASK_FOR_DISPLAY = 1;

    // FXML File Constant
    private static final String FXML_CELL_DATE = "/taskaler/ui/view/cellDate.fxml";

    // Binded FXML Elements
    @FXML
    private Label lblDate;

    @FXML
    private Pane paneBody;

    @FXML
    private Label lblNumber;

    @FXML
    private Rectangle rectangleGrey;

    @FXML
    private Rectangle rectangleGreen;

    @FXML
    private Rectangle rectangleOrange;

    @FXML
    private Rectangle rectangleRed;

    /**
     * Overloaded constructor
     * 
     * @param date
     *            The date to set the cell to
     * @throws IOException
     *             Thrown when error met while reading FXML
     */
    public CellDateController(CellDateModel model) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(
                FXML_CELL_DATE));
        loader.setRoot(this);
        loader.setController(this);
        loader.load();

        currentModel = model;
        
        initialize();
    }
    
    @Override
    public void initialize() {
        setTitle(currentModel.currentDate + EMPTY_STRING);
        setContent(currentModel.currentNumberOfEvents + EMPTY_STRING);
    }

    @Override
    public void setTitle(String title) {
        lblDate.setText(title);
        
    }

    @Override
    public void setContent(String content) {
        int totalNumberOfTasks = Integer.parseInt(content);
        setNumberOfTasks(totalNumberOfTasks);
    }
    
    /**
     * Method to make the body of the cell visible
     * 
     * @param isVisible
     *            The boolean to determine if the body is visible
     */
    private void setBodyVisible(boolean isVisible) {
        paneBody.setVisible(isVisible);
    }

    /**
     * Method to set the number of tasks
     * 
     * @param totalNumberOfTasks
     *            The total number of tasks
     */
    private void setNumberOfTasks(int totalNumberOfTasks) {
        if (totalNumberOfTasks < MIN_NUMBER_OF_TASK_FOR_DISPLAY) {
            setBodyVisible(false);
            return;
        }
        if (totalNumberOfTasks > MAX_NUMBER_OF_TASKS_FOR_DISPLAY) {
            lblNumber.setText(MAX_NUMBER_OF_TASKS_FOR_DISPLAY + PLUS_STRING);
        } else {
            lblNumber.setText(totalNumberOfTasks + EMPTY_STRING);
        }
    }

    /**
     * Method to reset visibility of all circles to false
     * 
     */
    private void resetCircleVisibility() {
        rectangleGrey.setVisible(false);
        rectangleGreen.setVisible(false);
        rectangleOrange.setVisible(false);
        rectangleRed.setVisible(false);
    }

    /**
     * Method to set the visibility of each circle
     * 
     * @param grey
     *            Boolean for the visibility of the grey circle
     * @param green
     *            Boolean for the visibility of the green circle
     * @param orange
     *            Boolean for the visibility of the orange circle
     * @param red
     *            Boolean for the visibility of the red circle
     */
    private void setCircleVisible(boolean grey, boolean green, boolean orange,
            boolean red) {
        resetCircleVisibility();
        setCircleVisible(CellDateModel.RECTANGLE_COLOR.GREY, grey);
        setCircleVisible(CellDateModel.RECTANGLE_COLOR.GREEN, green);
        setCircleVisible(CellDateModel.RECTANGLE_COLOR.ORANGE, orange);
        setCircleVisible(CellDateModel.RECTANGLE_COLOR.RED, red);
    }

    /**
     * Private method to set the visibility of a circle
     * 
     * @param color
     *            The color of the circle to be set
     * @param isVisible
     *            The boolean to determine if circle is visible
     */
    private void setCircleVisible(CellDateModel.RECTANGLE_COLOR color, boolean isVisible) {
        switch (color) {
        case GREY:
            rectangleGrey.setVisible(isVisible);
        case GREEN:
            rectangleGreen.setVisible(isVisible);
        case ORANGE:
            rectangleOrange.setVisible(isVisible);
        case RED:
            rectangleRed.setVisible(isVisible);
        }
    }

}
