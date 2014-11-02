/**
 * 
 */
package taskaler.ui.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import taskaler.common.data.DeadLineTask;
import taskaler.common.data.FloatTask;
import taskaler.common.data.RepeatedTask;
import taskaler.common.data.Task;
import taskaler.common.util.parser.calendarToString;
import taskaler.ui.model.ListPaneModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.control.TitledPane;
import javafx.scene.text.Text;

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
    private static final String REG_TASK_DISPLAY = "[%s] ID %s: %s";
    private static final int MAX_TEXT_WIDTH = 350;
    private static int floatingBreak = 0;
    private static int repeatedBreak = 1;
    private static int deadlineBreak = 2;

    // Binded FXML Elements
    @FXML
    private TitledPane paneListView;

    @FXML
    private ListView<Text> listBody;

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
            listBody.getItems().add(new Text(MSG_NOTHING_TO_DISPLAY));
            return;
        }

        ArrayList<Text> resultFloating = new ArrayList<Text>();
        ArrayList<Text> resultRepeated = new ArrayList<Text>();
        ArrayList<Text> resultDeadline = new ArrayList<Text>();
        
        resultFloating.add(new Text("FLOATING TASKS"));
        resultRepeated.add(new Text("REPEATED TASKS"));
        resultDeadline.add(new Text("DEADLINE TASKS"));

        for (Task t : list) {
            String deadline = "";
            if (t instanceof FloatTask) {
                deadline = calendarToString.parseDate(t.getTaskCreationDate());
            } else if (t instanceof RepeatedTask) {
                deadline = calendarToString.parseDate(((RepeatedTask) t)
                        .getEndRepeatedDate());
            } else if (t instanceof DeadLineTask) {
                deadline = calendarToString.parseDate(((DeadLineTask) t)
                        .getDeadline());
            }

            String temp = String.format(REG_TASK_DISPLAY, deadline,
                    t.getTaskID(), t.getTaskName());
            Text text = new Text(temp);
            text.wrappingWidthProperty().setValue(MAX_TEXT_WIDTH);
            
            if (t instanceof FloatTask) {
                resultFloating.add(text);
            } else if (t instanceof RepeatedTask) {
                resultRepeated.add(text);
            } else if (t instanceof DeadLineTask) {
                resultDeadline.add(text);
            }
            
        }
        resultFloating.addAll(resultRepeated);
        resultFloating.addAll(resultDeadline);
        listBody.getItems().addAll(resultFloating);
    }

    @Override
    public HashMap<String, String> getState() {
        return currentModel.toHashMap();
    }

}
