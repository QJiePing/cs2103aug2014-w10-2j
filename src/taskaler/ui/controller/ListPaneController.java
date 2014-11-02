/**
 * 
 */
package taskaler.ui.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;

import taskaler.common.data.DeadLineTask;
import taskaler.common.data.FloatTask;
import taskaler.common.data.RepeatedTask;
import taskaler.common.data.Task;
import taskaler.common.util.parser.calendarToString;
import taskaler.ui.model.ListPaneModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 * Class that acts as the controller for ListPaneView FXML. This class renders a
 * view for a task
 * 
 * @author Cheah Kit Weng, A0059806W
 *
 */
public class ListPaneController extends TitledPane implements IController {

    private static final String ROW_COLOR = "#FFFFFF";

    private static final String ALT_ROW_COLOR = "#66CCFF";

    // Current model associated with this controller
    private ListPaneModel currentModel;

    // Special Constants
    private static final String MSG_NOTHING_TO_DISPLAY = "Nothing to display";
    private static final String REG_TASK_DISPLAY = "[%s] ID %s: %s";
    private static final int MAX_TEXT_WIDTH = 350;
    private static int floatingBreak = 0;
    private static int repeatedBreak = 1;
    private static int deadlineBreak = 2;

    // Binded FXML Elements
    @FXML
    private TitledPane paneListView;

    @FXML
    private GridPane gridList;

    /**
     * Default constructor
     * 
     * @param model
     *            Model to be associated with this view
     * @throws IOException
     *             Thrown if error encountered while reading FXML
     */
    public ListPaneController(ListPaneModel model) throws IOException {
        currentModel = model;

        initialize(common.FXML_LIST_PANE);
        update();
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
        setTitle(currentModel.currentTitle);
        populateList(currentModel.currentItemList);
    }

    /**
     * Method to set the title of this view
     * 
     * @param title
     *            String to set the title to
     */
    public void setTitle(String title) {
        paneListView.setText(title);
    }

    /**
     * Method to populate the view with data
     * 
     * @param list
     *            The list to display on the view
     */
    private void populateList(ArrayList<Task> list) {
        if (list == null || list.size() < 1) {
            Label statusLabel = new Label(MSG_NOTHING_TO_DISPLAY);
            statusLabel.setPrefWidth(400.0);
            statusLabel.setMinWidth(400.0);
            statusLabel.setAlignment(Pos.CENTER);
            statusLabel.setStyle("-fx-background-color: white;");
            gridList.add(statusLabel, 0, 1, 4, 1);
            return;
        }

        ArrayList<FloatTask> resultFloating = new ArrayList<FloatTask>();
        ArrayList<RepeatedTask> resultRepeated = new ArrayList<RepeatedTask>();
        ArrayList<DeadLineTask> resultDeadline = new ArrayList<DeadLineTask>();

        for (Task t : list) {
            if (t instanceof FloatTask) {
                resultFloating.add((FloatTask) t);
            } else if (t instanceof RepeatedTask) {
                resultRepeated.add((RepeatedTask) t);
            } else if (t instanceof DeadLineTask) {
                resultDeadline.add((DeadLineTask) t);
            }

        }
        insertRows(resultFloating, resultRepeated, resultDeadline);
    }

    private void insertRows(ArrayList<FloatTask> floatingTaskList,
            ArrayList<RepeatedTask> repeatedTaskList,
            ArrayList<DeadLineTask> deadlineTaskList) {
        int result = insertRows("Floating Tasks", 1, floatingTaskList, ALT_ROW_COLOR);
        result = insertRows("Deadline Tasks", result, deadlineTaskList, ROW_COLOR);
        insertRows("Repeated Tasks", result, repeatedTaskList, ALT_ROW_COLOR);

    }

    private <T> int insertRows(String category, int startIndex,
            ArrayList<T> taskList, String color) {
        if(taskList.size() < 1){
            return startIndex;
        }
        Label categoryLabel = new Label(category.substring(0, 1));
        categoryLabel.setFont(new Font("Cambria", 14));
        categoryLabel.setStyle("-fx-background-color: "+ color + ";");
        //categoryLabel.setRotate(-90.0);
        //categoryLabel.setTranslateY(-50);
        categoryLabel.setAlignment(Pos.CENTER);
        int span = taskList.size();
        categoryLabel.setPrefWidth(30);
        categoryLabel.setPrefHeight(17* span);
        GridPane.setRowSpan(categoryLabel, span);
        gridList.add(categoryLabel, 0, startIndex, 1, span);
        
        for (int i = 0; i < taskList.size(); i++) {
            Task currentTask = (Task) taskList.get(i);
            Label id = new Label(currentTask.getTaskID());
            id.setPrefWidth(40);
            id.setAlignment(Pos.CENTER);
            Label name = new Label(currentTask.getTaskName());
            name.setPrefWidth(247);
            name.setAlignment(Pos.CENTER);
            
            Label date = new Label("-");
            date.setPrefWidth(83);
            date.setAlignment(Pos.CENTER);
            if(currentTask instanceof RepeatedTask){
                RepeatedTask taskHolder = (RepeatedTask) currentTask;
                String repeatPattern = taskHolder.getPattern();
                date.setText(repeatPattern);
            }else if(currentTask instanceof DeadLineTask){
                DeadLineTask taskHolder = (DeadLineTask) currentTask;
                Calendar deadline = taskHolder.getDeadline();
                date.setText(calendarToString.parseDate(deadline));
            }
            if( (startIndex + i) % 2 == 0){
                color = ALT_ROW_COLOR;
            }else{
                color = ROW_COLOR;
            }
            date.setStyle("-fx-background-color: "+ color + ";");
            name.setStyle("-fx-background-color: "+ color + ";");
            id.setStyle("-fx-background-color: "+ color + ";");
            gridList.add(id, 1, startIndex + i);
            gridList.add(date, 2, startIndex + i);
            gridList.add(name, 3, startIndex + i);
        }
        return startIndex + span;
    }

    @Override
    public HashMap<String, String> getState() {
        return currentModel.toHashMap();
    }

}
