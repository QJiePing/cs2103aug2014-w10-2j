/**
 * 
 */
package taskaler.ui.controller;

import taskaler.common.enumerate.RECTANGLE_COLOR;
import taskaler.ui.model.TaskPaneModel;

import java.io.IOException;
import java.text.SimpleDateFormat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;

/**
 * Class that acts as the controller for taskPane FXML. This class renders a
 * view for a task
 * 
 * @author Cheah Kit Weng, A0059806W
 *
 */
public class TaskPaneController extends BorderPane implements IController {
    // Current model associated with this controller
    private TaskPaneModel currentModel = null;
    
    // FXML File Constant
    private static final String FXML_TASK_PANE = "/fxml/taskPane.fxml";

    // Binded FXML Elements
    @FXML
    private Label lblDueBy;

    @FXML
    private Label lblHigh;

    @FXML
    private Label lblMedium;

    @FXML
    private Label lblLow;

    @FXML
    private Label lblDefault;

    @FXML
    private Label lblStatus;

    @FXML
    private Label lblTaskID;

    @FXML
    private Label lblTaskName;

    @FXML
    private TextArea txtTaskDescription;

    /**
     * Default constructor
     * 
     * @param t
     *            The task to be associated with this view
     * @throws IOException
     *             Thrown if error encountered while reading FXML
     */
    public TaskPaneController(TaskPaneModel model) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(
                FXML_TASK_PANE));
        loader.setRoot(this);
        loader.setController(this);
        loader.load();

        currentModel = model;
        
        initialize();
        
    }
    

    @Override
    public void initialize() {
        setTitle(currentModel.taskName);
        setContent(currentModel.taskDescription);
        populateDetails(currentModel);
    }

    @Override
    public void setTitle(String title) {
        lblTaskName.setText(title);
    }

    @Override
    public void setContent(String content) {
        txtTaskDescription.setText(content);
        
    }

    /**
     * Method to populate the fields on the view
     * 
     * @param t
     *            The task containing the value fields
     */
    public void populateDetails(TaskPaneModel model) {
        lblTaskID.setText(model.taskID);
        lblStatus.setText(model.taskStatus);
        lblDueBy.setText(model.taskDueDate);

        switch (model.taskWorkload) {
        case GREEN:
            lblLow.setVisible(true);
            lblDefault.setVisible(false);
            break;
        case ORANGE:
            lblMedium.setVisible(true);
            lblDefault.setVisible(false);
            break;
        case RED:
            lblHigh.setVisible(true);
            lblDefault.setVisible(false);
            break;
        case GREY:
            lblDefault.setVisible(true);
            break;
        }
    }
}
