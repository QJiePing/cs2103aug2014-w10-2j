/**
 * 
 */
package taskaler.ui.controller;

import taskaler.ui.model.TaskPaneModel;

import java.io.IOException;
import java.util.HashMap;

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
     * @param model
     *            Model to be associated with this view
     * @throws IOException
     *             Thrown if error encountered while reading FXML
     */
    public TaskPaneController(TaskPaneModel model) throws IOException {
        

        currentModel = model;
        
        initialize(common.FXML_TASK_PANE);
        update();
    }
    

    @Override
    public void initialize(String FXML) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(
                FXML));
        loader.setRoot(this);
        loader.setController(this);
        loader.load();
    }

    public void setTitle(String title) {
        lblTaskName.setText(title);
    }

    @Override
    public void update() {
        setTitle(currentModel.taskName);
        populateDetails(currentModel);
        
    }

    /**
     * Method to populate the fields on the view
     * 
     * @param t
     *            The task containing the value fields
     */
    public void populateDetails(TaskPaneModel model) {
        lblTaskID.setText(model.taskID);
        if(model.taskStatus){
            lblStatus.setText(TaskPaneModel.DONE_VALUE);
        }else{
            lblStatus.setText(TaskPaneModel.NOT_DONE_VALUE);
        }
        lblDueBy.setText(model.taskDueDate);
        txtTaskDescription.setText(model.taskDescription);
        
        switch (model.taskWorkload) {
        case common.RECTANGLE_COLOR_GREEN:
            lblLow.setVisible(true);
            lblDefault.setVisible(false);
            break;
        case common.RECTANGLE_COLOR_ORANGE:
            lblMedium.setVisible(true);
            lblDefault.setVisible(false);
            break;
        case common.RECTANGLE_COLOR_RED:
            lblHigh.setVisible(true);
            lblDefault.setVisible(false);
            break;
        case common.RECTANGLE_COLOR_GREY:
            lblDefault.setVisible(true);
            break;
        }
    }


    @Override
    public HashMap<String, String> getState() {
        return currentModel.toHashMap();
    }
}
