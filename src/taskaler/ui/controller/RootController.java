/**
 * 
 */
package taskaler.ui.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import javax.swing.JOptionPane;

import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import taskaler.ui.model.CalendarPaneModel;
import taskaler.ui.model.ListPaneModel;
import taskaler.ui.model.RootModel;
import taskaler.ui.model.TaskPaneModel;
import taskaler.ui.model.TextPaneModel;
import taskaler.common.configurations.Configuration;
import taskaler.common.data.DeadLineTask;
import taskaler.common.data.FloatTask;
import taskaler.common.data.RepeatedTask;
import taskaler.common.data.Task;
import taskaler.common.util.parser.calendarToString;
import taskaler.controller.Controller;
import taskaler.controller.parser.ParserLibrary;
import taskaler.ui.controller.common;

/**
 * Controller associated with the RootView
 * 
 * @author Cheah Kit Weng, A0059806W
 *
 */
//@author A0059806W
public class RootController extends BorderPane implements IController {

    // Special Constants
    private static final String CONFIRMATION_MESSAGE = "ARE YOU SURE? KEY IN YES IF YOU ARE SURE";
    public static final String PASSCODE = "YES";
    private static final double ANCHOR_ZERO = 0.0;
    private static final int HEIGHT_BUFFER = 2;
    private static final int MIN_HEIGHT_OF_ROW = 30;

    // Current model associated with this controller
    private RootModel currentModel = null;

    // Class variables
    private String[] commands = null;
    private static final String SUGGESTION_BOX_LABEL = "Suggestion(s):";
    private static final String CHAR_ENTER = "\r";
    private static final String CHAR_BACKSPACE = "\b";

    // FXML Element Bindings
    @FXML
    private Pane anchorPaneDisplay;

    @FXML
    private TextField txtCmdInput;

    @FXML
    private ListView<String> listCmd;

    @FXML
    private Label lblToast;

    @FXML
    private Label lblNotDone;

    @FXML
    private Label lblFloating;

    @FXML
    private Menu menuCmd;

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

        commands = ParserLibrary.getCommands();
        updateMenu();
        update(currentModel.totalNotDone, currentModel.totalFloating);
    }

    /**
     * Method to generate the command help in the menu
     * 
     */
    private void updateMenu() {
        for (String cmd : commands) {
            MenuItem item = new MenuItem(cmd);
            menuCmd.getItems().add(item);
        }

    }

    /**
     * Method to update only the score board
     * 
     * @param totalNotDone
     *            Total number of tasks that are not done
     * @param totalFloating
     *            Total number of tasks that are not confirmed
     */
    public void update(int totalNotDone, int totalFloating) {
        currentModel.totalNotDone = totalNotDone;
        currentModel.totalFloating = totalFloating;
        lblNotDone.setText(totalNotDone + common.EMPTY_STRING);
        lblFloating.setText(totalFloating + common.EMPTY_STRING);

    }

    @Override
    public void initialize(String FXML) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(FXML));
        loader.setRoot(this);
        loader.setController(this);
        loader.load();
    }

    /**
     * Method to ask the user for confirmation on an action
     * 
     * @return True if the user confirms the action; False otherwise
     */
    public Boolean showConfirmation() {
        String input = JOptionPane.showInputDialog(null, CONFIRMATION_MESSAGE);
        if (input.isEmpty() || PASSCODE.compareTo(input) != 0) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Method to show a toast notification on the interface
     * 
     * @param text
     *            Text to be shown on the toast
     */
    public void showToast(String text) {
        currentModel.notification = text;
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
        if (isKeyCodeEnter(e)) {
            String cmd = txtCmdInput.getText();
            txtCmdInput.clear();
            Controller.getInstance().executeCMD(cmd);
            listCmd.setVisible(false);
        } else if (isKeyUp(e)) {
            scrollUp();
        } else if (isKeyDown(e)) {
            scrollDown();
        }

    }

    /**
     * Method to determine if the key code is Enter
     * 
     * @param e
     *            Key event caught
     * @return True if the key event is a keycode enter; false otherwise
     */
    private boolean isKeyCodeEnter(KeyEvent e) {
        return e.getCode() == KeyCode.ENTER;
    }

    /**
     * Method to determine if the key event is a key press down
     * 
     * @param e
     *            Key event caught
     * @return True if event is a key press down; False otherwise
     */
    private boolean isKeyDown(KeyEvent e) {
        return e.getCode() == KeyCode.DOWN;
    }

    /**
     * Method to determine if the key event is a key press up
     * 
     * @param e
     *            Key event caught
     * @return True if event is a key press up; False otherwise
     */
    private boolean isKeyUp(KeyEvent e) {
        return e.getCode() == KeyCode.UP;
    }

    /**
     * Method to scroll children element down if possible. Only ListPane and
     * TextPane can be scrolled
     * 
     */
    private void scrollDown() {
        Node currentDisplay = anchorPaneDisplay.getChildren().get(
                common.ZERO_INDEX);
        if (currentDisplay instanceof ListPaneController) {
            ((ListPaneController) currentDisplay).scrollDown();
        } else if (currentDisplay instanceof TextPaneController) {
            ((TextPaneController) currentDisplay).scrollDown();
        }
    }

    /**
     * Method to scroll children element up if possible. Only ListPane and
     * TextPane can be scrolled
     * 
     */
    private void scrollUp() {
        Node currentDisplay = anchorPaneDisplay.getChildren().get(
                common.ZERO_INDEX);
        if (currentDisplay instanceof ListPaneController) {
            ((ListPaneController) currentDisplay).scrollUp();
        } else if (currentDisplay instanceof TextPaneController) {
            ((TextPaneController) currentDisplay).scrollUp();
        }
    }

    /**
     * Method which is binded to the KeyTyped event of txtCmdInput element This
     * method creates the suggestion view
     * 
     * @param e
     *            Key typed event
     */
    @FXML
    private void txtcmdInputKetTyped(KeyEvent e) {
        if (isProcessible(e)) {
            String input = txtCmdInput.getText();

            if (!isBackSpace(e)) {
                input = input + e.getCharacter();
            }

            ObservableList<String> suggestions = populateSuggestionList(input);

            if (suggestions.size() > 1) {
                displaySuggestionList(suggestions);
                return;
            }
        }
        listCmd.setVisible(false);

    }

    /**
     * Method to populate the suggestion list
     * 
     * @param input
     *            Input string to be compared
     * @return Populated list of suggestions
     */
    private ObservableList<String> populateSuggestionList(String input) {
        ObservableList<String> suggestions = FXCollections
                .observableArrayList(SUGGESTION_BOX_LABEL);

        for (int i = 0; i < commands.length; i++) {
            String currentCommand = commands[i];
            if (isPossibleCommand(input, currentCommand)) {
                suggestions.add(currentCommand);
            }
        }
        return suggestions;
    }

    /**
     * Method to allign and display the suggestion list
     * 
     * @param suggestions
     *            List of suggested commands
     */
    private void displaySuggestionList(ObservableList<String> suggestions) {
        listCmd.setItems(suggestions);
        listCmd.setPrefHeight(MIN_HEIGHT_OF_ROW * suggestions.size()
                + HEIGHT_BUFFER);
        listCmd.setMinHeight(MIN_HEIGHT_OF_ROW * suggestions.size()
                + HEIGHT_BUFFER);
        AnchorPane.setBottomAnchor(listCmd, ANCHOR_ZERO);
        listCmd.setVisible(true);
    }

    /**
     * Method to determine if the command is similar to input string
     * 
     * @param input
     *            Input string
     * @param command
     *            Command string
     * @return True if the command is similar to the input string; False
     *         otherwise
     */
    private boolean isPossibleCommand(String input, String command) {
        return command.startsWith(input);
    }

    /**
     * Method to determine if the key event can be processed by the controller
     * 
     * @param e
     *            Key event caught
     * @return True if key event is to be processed; False otherwise
     */
    private boolean isProcessible(KeyEvent e) {
        return !isEnterKey(e)
                && !(txtCmdInput.getText().isEmpty() && isBackSpace(e));
    }

    /**
     * Method to check if the key is a backspace key
     * 
     * @param e
     *            Key event to check
     * @return True if key is backspace key; False otherwise
     */
    private boolean isEnterKey(KeyEvent e) {
        return e.getCharacter().compareToIgnoreCase(CHAR_ENTER) == 0;
    }

    /**
     * Method to check if the key is a enter key
     * 
     * @param e
     *            Key event to check
     * @return True if key is enter key; False otherwise
     */
    private boolean isBackSpace(KeyEvent e) {
        return e.getCharacter().compareToIgnoreCase(CHAR_BACKSPACE) == 0;
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
        ListPaneController pane = createListPane(title, list);
        anchorPaneDisplay.getChildren().add(pane);
    }

    /**
     * Method to create a list pane
     * 
     * @param title
     *            Title of the list pane
     * @param list
     *            List of tasks
     * @return Newly created list pane
     * @throws IOException
     *             Thrown if an error is encoutnered while rendering list pane
     */
    private ListPaneController createListPane(String title, ArrayList<Task> list)
            throws IOException {
        ListPaneModel model = new ListPaneModel();
        model.currentTitle = title;
        model.currentItemList = list;
        ListPaneController pane = new ListPaneController(model);
        return pane;
    }

    /**
     * Method to render a list with multiple sub headers
     * 
     * @param title
     *            String to set the title of the list
     * @param header
     *            The array of sub headers
     * @param listOfTaskList
     *            The array of task lists
     * @throws IOException
     *             Thrown if an error was encountered while rendering list
     */
    public void displayDynamicList(String title, ArrayList<String> header,
            ArrayList<ArrayList<Task>> listOfTaskList) throws IOException {
        anchorPaneDisplay.getChildren().clear();
        ListPaneController pane = createDynamicListPane(title, header,
                listOfTaskList);
        anchorPaneDisplay.getChildren().add(pane);
    }

    /**
     * Method to create a list pane with multiple sub headers
     * 
     * @param title
     *            Title of the pane
     * @param header
     *            sub headers of the list
     * @param listOfTaskList
     *            Array of task lists
     * @return Newly created list pane
     * @throws IOException
     *             Thrown if an error was encountered while rendering list
     */
    private ListPaneController createDynamicListPane(String title,
            ArrayList<String> header, ArrayList<ArrayList<Task>> listOfTaskList)
            throws IOException {
        ListPaneModel model = new ListPaneModel();
        model.currentTitle = title;
        model.currentSubHeaders = header;
        model.arrayOfTaskLists = listOfTaskList;
        ListPaneController pane = new ListPaneController(model);
        return pane;
    }

    /**
     * Method to render the calendar view
     * 
     * @param list
     *            List of tasks
     * @param cal
     *            The calendar to be displayed
     * @throws IOException
     *             Thrown if an IO error is encountered while rendering Calendar
     */
    public void displayCalendar(ArrayList<Task> list, Calendar cal)
            throws IOException {
        anchorPaneDisplay.getChildren().clear();
        CalendarPaneController pane = createCalendarPane(list, cal);
        anchorPaneDisplay.getChildren().add(pane);
    }

    /**
     * Method to create a calendar pane
     * 
     * @param list
     *            List of tasks
     * @param cal
     *            Calendar date to be displayed
     * @return New created calendar pane
     * @throws IOException
     *             Thrown if an IO error is encountered while rendering Calendar
     */
    private CalendarPaneController createCalendarPane(ArrayList<Task> list,
            Calendar cal) throws IOException {
        CalendarPaneModel model = new CalendarPaneModel();
        model.currentCalendar = cal;
        model.currentMonth = cal.get(Calendar.MONTH) + common.OFFSET_BY_ONE;
        model.currentYear = cal.get(Calendar.YEAR);
        model.currentTaskList = list;

        CalendarPaneController pane = new CalendarPaneController(model);
        return pane;
    }

    /**
     * Method to render a view for an individual task
     * 
     * @param t
     *            The task to be rendered
     * @throws IOException
     *             Thrown if an IO error is encountered while rendering TaskPane
     */
    public void displayTask(Task t) throws IOException {
        anchorPaneDisplay.getChildren().clear();
        TaskPaneController pane = createTaskPane(t);
        anchorPaneDisplay.getChildren().add(pane);

    }

    /**
     * Method to create a task pane
     * 
     * @param t
     *            Task to be displayed
     * @return Newly created task pane
     * @throws IOException
     *             Thrown if an IO error is encountered while rendering TaskPane
     */
    private TaskPaneController createTaskPane(Task t) throws IOException {
        TaskPaneModel model = new TaskPaneModel();
        model.task = t;
        model.taskName = t.getTaskName();
        model.taskID = t.getTaskID();
        model.taskStatus = t.getTaskStatus();
        model.taskWorkload = parseWorkload(t.getTaskWorkLoad());
        model.taskDescription = t.getTaskDescription();

        if (t.getStartTime() != null) {
            model.taskStartTime = calendarToString.parseDate(t.getStartTime(),
                    Configuration.getInstance().getTimeFormat());
        }

        if (t.getEndTime() != null) {
            model.taskEndTime = calendarToString.parseDate(t.getEndTime(),
                    Configuration.getInstance().getTimeFormat());
        }

        // Process the different attributes of different Task types
        if (t instanceof FloatTask) {
            model.taskDate = calendarToString.parseDate(
                    t.getTaskCreationDate(), Configuration.getInstance()
                            .getDateFormat());
        } else if (t instanceof DeadLineTask) {
            model.taskDate = calendarToString
                    .parseDate(((DeadLineTask) t).getDeadline(), Configuration
                            .getInstance().getDateFormat());
        } else if (t instanceof RepeatedTask) {
            model.taskDate = calendarToString.parseDate(((RepeatedTask) t)
                    .getEndRepeatedDate(), Configuration.getInstance()
                    .getDateFormat());
            model.taskPattern = RepeatedTask
                    .patternToEnglish(((RepeatedTask) t).getPattern());
        }

        TaskPaneController pane = new TaskPaneController(model);
        return pane;
    }

    /**
     * Method to render a view that contains text only
     * 
     * @param title
     *            Title for the view
     * @param text
     *            The body text
     * @throws IOException
     *             Thrown if an IO error is encountered while rendering text
     *             pane
     */
    public void displayText(String title, String text) throws IOException {
        anchorPaneDisplay.getChildren().clear();
        TextPaneController pane = createTextView(title, text);
        anchorPaneDisplay.getChildren().add(pane);
    }

    /**
     * Method to create a text pane
     * 
     * @param title
     *            Title of the pane
     * @param text
     *            Text to display in the body of the pane
     * @return Newly created text pane
     * @throws IOException
     *             Thrown if an IO error is encountered while rendering text
     *             pane
     */
    private TextPaneController createTextView(String title, String text)
            throws IOException {
        TextPaneModel model = new TextPaneModel();
        model.title = title;
        model.textBody = text;
        TextPaneController pane = new TextPaneController(model);
        return pane;
    }

    /**
     * Method to convert the string representation of the workload attribute to
     * integer representation
     * 
     * @param taskWorkLoad
     *            Value to be parsed
     * @return Integer representation of value
     */
    private int parseWorkload(String taskWorkLoad) {
        if (taskWorkLoad.compareToIgnoreCase(Task.WORKLOAD_LOW) == 0) {
            return common.RECTANGLE_COLOR_GREEN;
        } else if (taskWorkLoad.compareToIgnoreCase(Task.WORKLOAD_MEDIUM) == 0) {
            return common.RECTANGLE_COLOR_ORANGE;
        } else if (taskWorkLoad.compareToIgnoreCase(Task.WORKLOAD_HIGH) == 0) {
            return common.RECTANGLE_COLOR_RED;
        } else {
            return common.RECTANGLE_COLOR_GREY;
        }
    }

    /**
     * Method to give focus to the window
     * 
     */
    public void giveFocus() {
        txtCmdInput.requestFocus();
    }

    @Override
    public HashMap<String, String> getState() {
        return ((IController) anchorPaneDisplay.getChildren().get(
                common.ZERO_INDEX)).getState();
    }

}
