/**
 * 
 */
package taskaler.ui.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
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
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

/**
 * Class that acts as the controller for ListPaneView FXML. This class renders a
 * view for a task
 * 
 * @author Cheah Kit Weng, A0059806W
 *
 */
public class ListPaneController extends TitledPane implements IController {

    // Current model associated with this controller
    private ListPaneModel currentModel;

    // Special Constants
    private static final String MSG_NOTHING_TO_DISPLAY = "Nothing to display";
    private static final int NAME_COL_INDEX = 3;
    private static final int DATE_COL_INDEX = 2;
    private static final int ID_COL_INDEX = 1;
    private static final String DEFAULT_DATE_VALUE = "-";
    private static final int DATE_WIDTH = 83;
    private static final int NAME_WIDTH = 225;
    private static final int ID_WIDTH = 40;
    private static final int MIN_CATEGORY_HEIGHT = 17;
    private static final int CATEGORY_WIDTH = 30;
    private static final String REPEAT_HEADER = "R";
    private static final String DEADLINE_HEADER = "D";
    private static final String FLOAT_HEADER = "F";
    private static final int MIN_SPAN = 1;
    private static final int MAX_COL_SPAN = 4;
    private static final String FX_BACKGROUND_COLOR_STYLE = "-fx-background-color: %s;";
    private static final double MAX_WIDTH_VIEW = 400.0;
    private static final String ROW_COLOR = "#FFFFFF";
    private static final String ALT_ROW_COLOR = "#66CCFF";
    private static final String GREEN_COLOR = "#9bbb59";
    private static final String ORANGE_COLOR = "#f79646";
    private static final String RED_COLOR = "#c0504d";
    private static final String GREY_COLOR = "#a6a6a6";
    private static final int WORKLOAD_COL_INDEX = 4;
    private static final double SCROLL_AMOUNT = 0.1;

    // Binded FXML Elements
    @FXML
    private TitledPane paneListView;

    @FXML
    private GridPane gridList;
    
    @FXML
    private ScrollPane scrollBody;

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
            statusLabel.setPrefWidth(MAX_WIDTH_VIEW);
            statusLabel.setAlignment(Pos.CENTER);
            statusLabel.setStyle(String.format(FX_BACKGROUND_COLOR_STYLE,
                    ROW_COLOR));
            gridList.add(statusLabel, common.ZERO_INDEX, common.OFFSET_BY_ONE,
                    MAX_COL_SPAN, MIN_SPAN);
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

    /**
     * Method to add all tasks to the grid list
     * 
     * @param floatingTaskList
     *            List of floating tasks
     * @param repeatedTaskList
     *            List of repeated tasks
     * @param deadlineTaskList
     *            List of deadline tasks
     */
    private void insertRows(ArrayList<FloatTask> floatingTaskList,
            ArrayList<RepeatedTask> repeatedTaskList,
            ArrayList<DeadLineTask> deadlineTaskList) {
        int result = insertRows(FLOAT_HEADER, common.ZERO_INDEX
                + common.OFFSET_BY_ONE, floatingTaskList, ALT_ROW_COLOR);
        result = insertRows(DEADLINE_HEADER, result, deadlineTaskList,
                ROW_COLOR);
        result = insertRows(REPEAT_HEADER, result, repeatedTaskList, ALT_ROW_COLOR);

    }
    
    /**
     * Method to programmically scroll up the view
     * 
     */
    public void scrollUp(){
        if(scrollBody.getHeight() >= gridList.getHeight()){
            return;
        }
        scrollBody.setVvalue(scrollBody.vvalueProperty().doubleValue() - SCROLL_AMOUNT);
    }
    
    /**
     * Mehtod to programmically scroll down the view
     * 
     */
    public void scrollDown(){
        if(scrollBody.getHeight() >= gridList.getHeight()){
            return;
        }
        scrollBody.setVvalue(scrollBody.vvalueProperty().doubleValue() + SCROLL_AMOUNT);
    }
    
    /**
     * Method to add a single list of tasks into the grid list
     * 
     * @param category
     *            Category of this batch of tasks
     * @param startIndex
     *            Index to to start from on the grid
     * @param taskList
     *            List of tasks
     * @param color
     *            Color to assign to this category of tasks
     * @return Index that the next list should start from
     */
    private <T> int insertRows(String category, int startIndex,
            ArrayList<T> taskList, String color) {
        if (taskList.size() < 1) {
            return startIndex;
        }
        Label categoryLabel = new Label(category);
        categoryLabel.setStyle(String.format(FX_BACKGROUND_COLOR_STYLE, color));
        // categoryLabel.setRotate(-90.0);
        // categoryLabel.setTranslateY(-50);
        categoryLabel.setAlignment(Pos.CENTER);
        
        int span = taskList.size();
        AnchorPane container = new AnchorPane();
        categoryLabel.setPrefWidth(CATEGORY_WIDTH);
        categoryLabel.setPrefHeight(MIN_CATEGORY_HEIGHT * span);
        
        AnchorPane.setTopAnchor(categoryLabel, 0.0);
        AnchorPane.setBottomAnchor(categoryLabel, 0.0);
        container.getChildren().add(categoryLabel);
        GridPane.setRowSpan(container, span);
        gridList.add(container, common.ZERO_INDEX, startIndex, MIN_SPAN,
                span);

        for (int i = 0; i < taskList.size(); i++) {
            Task currentTask = (Task) taskList.get(i);
            Label id = new Label(currentTask.getTaskID());
            id.setPrefWidth(ID_WIDTH);
            id.setAlignment(Pos.CENTER);

            Label name = new Label(currentTask.getTaskName());
            name.setPrefWidth(NAME_WIDTH);
            name.setAlignment(Pos.CENTER);

            Label date = new Label(DEFAULT_DATE_VALUE);
            date.setPrefWidth(DATE_WIDTH);
            date.setAlignment(Pos.CENTER);

            
            
            String loadColor = GREY_COLOR;
            
            if(currentTask.getTaskWorkLoad().compareToIgnoreCase(Task.WORKLOAD_HIGH) == 0){
                loadColor = RED_COLOR;
            }else if(currentTask.getTaskWorkLoad().compareToIgnoreCase(Task.WORKLOAD_MEDIUM) == 0){
                loadColor = ORANGE_COLOR;
            }else if(currentTask.getTaskWorkLoad().compareToIgnoreCase(Task.WORKLOAD_LOW) == 0){
                loadColor = GREEN_COLOR;
            }
            Label workload = new Label();
            workload.setStyle(String.format(FX_BACKGROUND_COLOR_STYLE, loadColor));
            workload.setPrefWidth(ID_WIDTH);
            
            if (currentTask instanceof RepeatedTask) {
                RepeatedTask taskHolder = (RepeatedTask) currentTask;
                String repeatPattern = taskHolder.getPattern();
                date.setText(repeatPattern);
            } else if (currentTask instanceof DeadLineTask) {
                DeadLineTask taskHolder = (DeadLineTask) currentTask;
                Calendar deadline = taskHolder.getDeadline();
                date.setText(calendarToString.parseDate(deadline));
            }
            if (isEvenRow(startIndex, i)) {
                color = ALT_ROW_COLOR;
            } else {
                color = ROW_COLOR;
            }
            date.setStyle(String.format(FX_BACKGROUND_COLOR_STYLE, color));
            name.setStyle(String.format(FX_BACKGROUND_COLOR_STYLE, color));
            id.setStyle(String.format(FX_BACKGROUND_COLOR_STYLE, color));
            gridList.add(id, ID_COL_INDEX, startIndex + i);
            gridList.add(date, DATE_COL_INDEX, startIndex + i);
            gridList.add(name, NAME_COL_INDEX, startIndex + i);
            gridList.add(workload, WORKLOAD_COL_INDEX, startIndex + i);
        }
        return startIndex + span;
    }

    /**
     * Method to check if the pointer at an even row
     * 
     * @param startIndex
     *            Start index for this category of tasks
     * @param i
     *            Offset
     * @return True if pointer is at an even row; False otherwise
     */
    private boolean isEvenRow(int startIndex, int i) {
        return (startIndex + i) % 2 == 0;
    }

    @Override
    public HashMap<String, String> getState() {
        return currentModel.toHashMap();
    }

}
