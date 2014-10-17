/**
 * 
 */
package taskaler.ui.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import taskaler.ui.model.CalendarPaneModel;
import taskaler.ui.model.ListPaneModel;
import taskaler.ui.model.RootModel;
import taskaler.ui.model.TaskPaneModel;
import taskaler.common.data.Task;
import taskaler.common.util.parser.calendarToString;
import taskaler.controller.Controller;
import taskaler.ui.controller.common;

/**
 * Controller associated with the RootView
 * 
 * @author Cheah Kit Weng, A0059806W
 *
 */
public class RootController extends BorderPane implements IController {
    // Current model associated with this controller
    private RootModel currentModel = null;

    // FXML Element Bindings
    @FXML
    private Pane anchorPaneDisplay;

    @FXML
    private TextField txtCmdInput;

    @FXML
    private Label lblToast;

    /**
     * Default constructor
     * 
     * @param model
     *            Model to be associated with this controller
     * @throws IOException
     *             Thrown if an error is encountered while reading the FXML file
     */
    public RootController(RootModel model) throws IOException {
        currentModel = model;

        initialize(common.FXML_ROOT);
        update();
    }

    @Override
    public void update() {
        if (!currentModel.notification.isEmpty()) {
            showToast(currentModel.notification);
        }
    }

    @Override
    public void initialize(String FXML) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(FXML));
        loader.setRoot(this);
        loader.setController(this);
        loader.load();
    }

    /**
     * Method to show a toast notification on the interface
     * 
     * @param text
     *            Text to be shown on the toast
     */
    public void showToast(String text) {
        lblToast.setText(text);
        lblToast.setVisible(true);
        FadeTransition ft = new FadeTransition(Duration.millis(5000), lblToast);
        ft.setFromValue(1.0);
        ft.setToValue(0.0);
        ft.play();
    }

    /**
     * Method which is binded to the KeyPressed event of txtCmdInput element
     * This method passes user input to the controller
     * 
     * @param e
     *            Key pressed event
     */
    @FXML
    private void txtCmdInputKeyPressed(KeyEvent e) {
        if (e.getCode() == KeyCode.ENTER) {
            String cmd = txtCmdInput.getText();
            txtCmdInput.clear();
            Controller.getInstance().executeCMD(cmd);
        }
    }

    /**
     * Method to render the list view
     * 
     * @param list
     *            The list to be rendered
     * @throws IOException
     *             Thrown if any IO error was encountered while rendering
     *             ListPane
     */
    public void displayList(String title, ArrayList<Task> list)
            throws IOException {
        anchorPaneDisplay.getChildren().clear();
        ListPaneModel model = new ListPaneModel();
        model.currentTitle = title;
        model.currentItemList = list;
        ListPaneController pane = new ListPaneController(model);
        anchorPaneDisplay.getChildren().add(pane);
    }

    /**
     * Method to render the calendar view
     * 
     * @throws IOException
     *             Thrown if an IO error is encountered while rendering
     *             CalendarPane
     * 
     */
    public void displayCalendar(ArrayList<Task> list, Calendar cal)
            throws IOException {
        anchorPaneDisplay.getChildren().clear();

        CalendarPaneModel model = new CalendarPaneModel();
        model.currentCalendar = cal;
        model.currentMonth = cal.get(Calendar.MONTH)
                + common.OFFSET_BY_ONE;
        model.currentYear = cal.get(Calendar.YEAR);
        model.currentTaskList = list;

        CalendarPaneController pane = new CalendarPaneController(model);
        anchorPaneDisplay.getChildren().add(pane);
    }

    /**
     * Method to render a view for an individual task
     * 
     * @param t
     *            The task to be rendered
     * @throws IOException
     *             Thrown if an IO error is encountered while rendering
     *             TaskPanee
     */
    public void displayTask(Task t) throws IOException {
        anchorPaneDisplay.getChildren().clear();
        TaskPaneModel model = new TaskPaneModel();
        model.taskName = t.getTaskName();
        model.taskID = t.getTaskID();
        model.taskStatus = t.getTaskStatus();
        model.taskDueDate = calendarToString.parseDate(t.getTaskDeadLine());
        model.taskWorkload = parseWorkload(t.getTaskWorkLoad());
        model.taskDescription = t.getTaskDescription();
        TaskPaneController pane = new TaskPaneController(model);
        anchorPaneDisplay.getChildren().add(pane);

    }

    /**
     * Method to convert the string representation of the workload attribute to
     * RECTANGLE_COLOR representation
     * 
     * @param taskWorkLoad
     *            Value to be parsed
     * @return RECTANGLE_COLOR representation of value
     */
    private common.RectangleColor parseWorkload(String taskWorkLoad) {
        if (taskWorkLoad.compareToIgnoreCase(Task.WORKLOAD_LOW) == 0) {
            return common.RectangleColor.GREEN;
        } else if (taskWorkLoad.compareToIgnoreCase(Task.WORKLOAD_MEDIUM) == 0) {
            return common.RectangleColor.ORANGE;
        } else if (taskWorkLoad.compareToIgnoreCase(Task.WORKLOAD_MEDIUM) == 0) {
            return common.RectangleColor.RED;
        } else {
            return common.RectangleColor.GREY;
        }
    }

}
