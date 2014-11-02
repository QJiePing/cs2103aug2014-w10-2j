/**
 * 
 */
package taskaler.ui.controller;

import taskaler.common.data.DeadLineTask;
import taskaler.common.data.FloatTask;
import taskaler.common.data.RepeatedTask;
import taskaler.ui.model.TaskPaneModel;

import java.io.IOException;
import java.util.HashMap;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
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
    private Label lblDate;

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
    private ImageView imgCheck;

    @FXML
    private Label lblTaskID;

    @FXML
    private Label lblTaskName;

    @FXML
    private TextArea txtTaskDescription;

    @FXML
    private Label lblFloatingHeader;

    @FXML
    private Label lblDeadlineHeader;

    @FXML
    private Label lblRepeatHeader;

    @FXML
    private Label lblPatternHeader;

    @FXML
    private Label lblPattern;

    @FXML
    private Label lblStartTime;

    @FXML
    private Label lblEndTime;

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
        FXMLLoader loader = new FXMLLoader(getClass().getResource(FXML));
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
        lblStartTime.setText(model.taskStartTime);
        lblEndTime.setText(model.taskEndTime);
        if (model.taskStatus) {
            lblStatus.setText(TaskPaneModel.DONE_VALUE);
            imgCheck.setVisible(true);
        } else {
            lblStatus.setText(TaskPaneModel.NOT_DONE_VALUE);
            imgCheck.setVisible(false);
        }
        lblDate.setText(model.taskDate);
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

        if (model.task instanceof FloatTask) {
            setType(true, false, false, null);
        } else if (model.task instanceof DeadLineTask) {
            setType(false, true, false, null);
        } else if (model.task instanceof RepeatedTask) {
            setType(false, false, true, model.taskPattern);
        }
    }

    /**
     * Method to set the view to display the details of each type of task
     * 
     */
    /**
     * Method to set the view to display the details of each type of task
     * 
     * @param isFloat
     *            True if is Floating Task
     * @param isDeadline
     *            True if is Deadline Task
     * @param isRepeat
     *            True if is Repeated Task
     * @param pattern
     *            Special argument required if it is a repeated task
     */
    public void setType(boolean isFloat, boolean isDeadline, boolean isRepeat,
            String pattern) {
        lblFloatingHeader.setVisible(isFloat);
        lblDeadlineHeader.setVisible(isDeadline);
        lblRepeatHeader.setVisible(isRepeat);
        lblPatternHeader.setVisible(isRepeat);
        lblPattern.setVisible(isRepeat);

        if (isRepeat) {
            lblPattern.setText(pattern);
        }
    }

    @Override
    public HashMap<String, String> getState() {
        return currentModel.toHashMap();
    }
}
