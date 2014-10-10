import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 * Main Class to create the required UI for the application This class also
 * serves as the Action Event Controller for the UI. i.e. OnClick Events
 * 
 * @author Cheah Kit Weng, A0059806W
 *
 */
public class UI extends Application {
    // Special Constants
    private static final String TITLE = "Taskaler";

    // File Constants
    private static final String ICON_PNG = "/images/icon.png";
    private static final String FXML_ROOT = "/fxml/root.fxml";

    // FXML Element Bindings
    @FXML
    private Pane anchorPaneDisplay;

    @FXML
    private TextField txtCmdInput;

    @FXML
    private Label lblToast;

    /**
     * Method to start rendering the UI elements of Taskaler
     * 
     * @param stage
     *            Main stage to render UI
     */
    @Override
    public void start(Stage stage) {
        try {

            stage.getIcons().add(
                    new Image(getClass().getResourceAsStream(ICON_PNG)));
            stage.setTitle(TITLE);
            stage.setResizable(false);
            stage.setWidth(410.0);
            stage.setHeight(525.0);

            FXMLLoader root = new FXMLLoader(getClass().getResource(FXML_ROOT));
            root.setController(this);
            Parent pane = root.load();
            Scene scene = new Scene(pane, 400, 475);

            stage.setScene(scene);
            stage.show();
            displayCalendar(Taskaler.taskList);

        } catch (Exception e) {
            e.printStackTrace();
        }
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
            Controller.executeCMD(cmd);
        }
    }

    /**
     * Method to render the calendar view
     * 
     */
    public void displayCalendar(ArrayList<Task> list) throws Exception {
        anchorPaneDisplay.getChildren().clear();
        CalendarPane pane = new CalendarPane(list, Calendar.getInstance());
        anchorPaneDisplay.getChildren().add(pane);
    }

    /**
     * Method to render the list view
     * 
     * @param list
     *            The list to be rendered
     */
    public void displayList(String title, ArrayList<Task> list)
            throws Exception {
        anchorPaneDisplay.getChildren().clear();
        ListPane pane = new ListPane(title, list);
        anchorPaneDisplay.getChildren().add(pane);
    }

    /**
     * Method stub to render a view, either in list or calendar. This method
     * uses default configurations to determine if view should be in calendar or
     * list
     * 
     */
    public void display(String args) throws Exception {
        if (args.equals("LIST")) {
            displayList("All current tasks", Taskaler.taskList);
        } else {
            displayCalendar(Taskaler.taskList);
        }
        txtCmdInput.requestFocus();
    }

    /**
     * Method to render a view for an individual task
     * 
     * @param t
     *            The task to be rendered
     */
    public void displayTask(Task t) throws Exception {
        if (t != null) {
            anchorPaneDisplay.getChildren().clear();
            TaskPane pane = new TaskPane(t);
            anchorPaneDisplay.getChildren().add(pane);
        } else {
            // JOptionPane.showMessageDialog(null,
            // "Task does not exist","Nothing to display",
            // JOptionPane.INFORMATION_MESSAGE, null);
            showToast("Task does not exist");
        }

    }
}

/**
 * Class that acts as a controller for calendarPane FXML This class renders the
 * calendar view
 *
 */
class CalendarPane extends BorderPane {

    // Special Constants
    private static final int ZERO_INDEX = 0;
    private static final int OFFSET_BY_ONE = 1;
    private static final String FXML_ELEMENT_GROUP = "Group";
    private static final String FXML_ELEMENT_ANCHOR_PANE = "AnchorPane";
    private static final String REG_MONTH_YEAR = "MMM yyyy";
    private static final int MAX_RENDERABLE_ROWS = 5;
    private static final int MAX_DAYS_IN_A_WEEK = 7;
    private static final int MAX_NUMBER_OF_DAYS = 31;

    // FXML File Constant
    private static final String FXML_CALENDAR = "/fxml/calendarPane.fxml";

    // Class Variables
    private int currMonth = 0;
    private int currYear = 0;
    private ArrayList<Task> currentList = null;

    // Binded FXML Elements
    @FXML
    private Label lblCurrent;

    @FXML
    private GridPane gridView;

    /**
     * Default Constructor
     * 
     * @throws Exception
     */
    public CalendarPane() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass()
                .getResource(FXML_CALENDAR));
        loader.setRoot(this);
        loader.setController(this);
        loader.load();

        currentList = Taskaler.taskList;

        resetToToday();
    }

    /**
     * Overloaded Constructor for inbuilt goto and listing items
     * 
     * @param list
     *            list to display
     * @param c
     *            Date to go to
     * @throws Exception
     */
    public CalendarPane(ArrayList<Task> list, Calendar c) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass()
                .getResource(FXML_CALENDAR));
        loader.setRoot(this);
        loader.setController(this);
        loader.load();

        currentList = Taskaler.taskList;

        goToDate(c);
    }

    /**
     * Method to set the title of the calendar
     * 
     * @param s
     *            String to set title to
     */
    public void setTitle(String s) {
        lblCurrent.setText(s);
    }

    /**
     * Method to populate the calendar grids
     * 
     * @param date
     *            The calendar object to set the month and year of the calendar
     */
    private void populateCalendar(Calendar date) throws Exception {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, date.get(Calendar.YEAR));
        cal.set(Calendar.MONTH, date.get(Calendar.MONTH));
        cal.set(Calendar.DAY_OF_MONTH, OFFSET_BY_ONE);

        cal.setFirstDayOfWeek(Calendar.SUNDAY);

        SimpleDateFormat formatter = new SimpleDateFormat(REG_MONTH_YEAR);
        String currentMonthAndYear = formatter.format(cal.getTime());
        setTitle(currentMonthAndYear);

        int[] numberOfTasksByDay = countTasks(cal.get(Calendar.MONTH));

        int dayOfTheWeekIterator = cal.get(Calendar.DAY_OF_WEEK)
                - OFFSET_BY_ONE;
        int weekOfTheMonthIterator = OFFSET_BY_ONE;

        for (int i = OFFSET_BY_ONE; i <= cal
                .getActualMaximum(Calendar.DAY_OF_MONTH); i++) {

            CellDate day = new CellDate(i + "");
            if (numberOfTasksByDay[i] > 0) {
                day.setNumberOfTasks(numberOfTasksByDay[i]);
                day.setBodyVisible(true);
            } else {
                day.setBodyVisible(false);
            }
            gridView.add(day, dayOfTheWeekIterator, weekOfTheMonthIterator);
            dayOfTheWeekIterator++;
            if (isThisDaySunday(dayOfTheWeekIterator)) {
                weekOfTheMonthIterator++;
                dayOfTheWeekIterator = ZERO_INDEX;
                if (hasOverflowedCalendarUI(weekOfTheMonthIterator)) {
                    weekOfTheMonthIterator = OFFSET_BY_ONE;
                }
            }

        }
    }

    /**
     * Method to count the number of tasks for each day of the month
     * 
     * @param month
     *            The month to count
     * @return returns an array of int with each index representing the the day
     *         and the value representing the total tasks
     */
    private int[] countTasks(int month) {
        int[] result = new int[MAX_NUMBER_OF_DAYS + OFFSET_BY_ONE];

        for (int i = ZERO_INDEX; i < currentList.size(); i++) {
            Task currentTask = currentList.get(i);
            if (currentTask.getTaskDeadLine().get(Calendar.MONTH) == month) {
                result[currentTask.getTaskDeadLine().get(Calendar.DATE)]++;
            }
        }

        return result;
    }

    /**
     * Method to detect if the number of cells has overflowed the maximum number
     * of rows
     * 
     * @param weekOfTheMonthIterator
     *            the number of rows to check
     * @return True if overflow detected; False otherwise
     */
    private boolean hasOverflowedCalendarUI(int weekOfTheMonthIterator) {
        return weekOfTheMonthIterator > MAX_RENDERABLE_ROWS;
    }

    /**
     * Method to determine if the day is next Sunday
     * 
     * @param dayOfTheWeekIterator
     *            The day to be checked
     * @return True if the day is next Sunday; False otherwise
     */
    private boolean isThisDaySunday(int dayOfTheWeekIterator) {
        return dayOfTheWeekIterator % MAX_DAYS_IN_A_WEEK == 0;
    }

    /**
     * Method to quickly reset the calendar to System current month and year
     * 
     * @throws Exception
     * 
     */
    public void resetToToday() throws Exception {
        Calendar now = Calendar.getInstance();
        goToDate(now);
    }

    /**
     * Method to move calendar to a specified month and year
     * 
     * @param now
     *            The calendar object that represent the desired month and year
     * @throws Exception
     */
    public void goToDate(Calendar now) throws Exception {
        clearGrid();
        currMonth = now.get(Calendar.MONTH);
        currYear = now.get(Calendar.YEAR);
        populateCalendar(now);
    }

    /**
     * Method to move the calendar forward by one month
     * 
     * @throws Exception
     * 
     */
    public void nextMonth() throws Exception {
        currMonth++;
        Calendar now = Calendar.getInstance();
        now.set(Calendar.MONTH, currMonth);
        goToDate(now);
    }

    /**
     * Method to move the calendar backward by one month
     * 
     * @throws Exception
     * 
     */
    public void prevMonth() throws Exception {
        currMonth--;
        Calendar now = Calendar.getInstance();
        now.set(Calendar.MONTH, currMonth);
        goToDate(now);
    }

    /**
     * Method to move the calendar forward by one year
     * 
     * @throws Exception
     * 
     */
    public void nextYear() throws Exception {
        currYear++;
        Calendar now = Calendar.getInstance();
        now.set(Calendar.YEAR, currYear);
        goToDate(now);
    }

    /**
     * Method to move the calendar backward by one year
     * 
     * @throws Exception
     * 
     */
    public void prevYear() throws Exception {
        currYear--;
        Calendar now = Calendar.getInstance();
        now.set(Calendar.YEAR, currYear);
        goToDate(now);
    }

    /**
     * Method to clear the elements in the calendar
     * 
     */
    private void clearGrid() {
        Object[] list = gridView.getChildren().toArray();
        for (Object node : list) {
            if (!node.getClass().getName().contains(FXML_ELEMENT_ANCHOR_PANE)
                    && !node.getClass().getName().contains(FXML_ELEMENT_GROUP)) {
                gridView.getChildren().remove(node);
            }
        }
    }
}

/**
 * Class that acts as the controller for cellDate FXML. This class modifies a
 * single cell of the calendar
 *
 */
class CellDate extends AnchorPane {

    // Special Constants
    private enum CIRCLE_COLOR {
        GREY, GREEN, ORANGE, RED
    }

    private static final String EMPTY_STRING = "";
    private static final String PLUS_STRING = "+";
    private static final int MAX_NUMBER_OF_TASKS_FOR_DISPLAY = 9;
    private static final int MIN_NUMBER_OF_TASK_FOR_DISPLAY = 1;

    // FXML File Constant
    private static final String FXML_CELL_DATE = "/fxml/cellDate.fxml";

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
     * Default overloaded constructor
     * 
     * @param date
     *            The date to set the cell to
     * @throws IOException
     *             Thrown when error met while reading FXML
     */
    public CellDate(String date) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(
                FXML_CELL_DATE));
        loader.setRoot(this);
        loader.setController(this);
        loader.load();

        lblDate.setText(date);
    }

    /**
     * Method to make the body of the cell visible
     * 
     * @param isVisible
     *            The boolean to determine if the body is visible
     */
    public void setBodyVisible(boolean isVisible) {
        paneBody.setVisible(isVisible);
    }

    /**
     * Method to set the number of tasks
     * 
     * @param totalNumberOfTasks
     *            The total number of tasks
     */
    public void setNumberOfTasks(int totalNumberOfTasks) {
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
    public void setCircleVisible(boolean grey, boolean green, boolean orange,
            boolean red) {
        resetCircleVisibility();
        setCircleVisible(CIRCLE_COLOR.GREY, grey);
        setCircleVisible(CIRCLE_COLOR.GREEN, green);
        setCircleVisible(CIRCLE_COLOR.ORANGE, orange);
        setCircleVisible(CIRCLE_COLOR.RED, red);
    }

    /**
     * Private method to set the visibility of a circle
     * 
     * @param color
     *            The color of the circle to be set
     * @param isVisible
     *            The boolean to determine if circle is visible
     */
    private void setCircleVisible(CIRCLE_COLOR color, boolean isVisible) {
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

/**
 * Class that acts as the controller for taskPane FXML. This class renders a
 * view for a task
 *
 */
class TaskPane extends BorderPane {

    // FXML File Constant
    private static final String FXML_TASK_PANE = "/fxml/taskPane.fxml";
    private static final String REG_MONTH_YEAR = "dd/MM/yyyy";

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
    public TaskPane(Task t) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(
                FXML_TASK_PANE));
        loader.setRoot(this);
        loader.setController(this);
        loader.load();

        populateDetails(t);
    }

    /**
     * Method to populate the fields on the view
     * 
     * @param t
     *            The task containing the value fields
     */
    public void populateDetails(Task t) {
        lblTaskName.setText(t.getTaskName());
        lblTaskID.setText(t.getTaskID());
        lblStatus.setText(t.getTaskStatus());
        SimpleDateFormat formatter = new SimpleDateFormat(REG_MONTH_YEAR);
        String deadline = formatter.format(t.getTaskDeadLine().getTime());
        lblDueBy.setText(deadline);

        switch (t.getTaskWorkLoad()) {
        case "1":
            lblLow.setVisible(true);
            lblDefault.setVisible(false);
            break;
        case "2":
            lblMedium.setVisible(true);
            lblDefault.setVisible(false);
            break;
        case "3":
            lblHigh.setVisible(true);
            lblDefault.setVisible(false);
            break;
        default:
            lblDefault.setVisible(true);
            break;
        }

        txtTaskDescription.setText(t.getTaskDescription());
    }
}

/**
 * Class that acts as the controller for listPane FXML. This class renders a
 * list view for any purpose
 * 
 */
class ListPane extends TitledPane {

    // Special Constants
    private static final String REG_TASK_DISPLAY = "[%s]ID=%s: %s";
    private static final int MAX_TEXT_WIDTH = 350;

    // FXML File Constant
    private static final String FXML_CELL_DATE = "/fxml/listPane.fxml";
    private static final String REG_MONTH_YEAR = "dd/MM/yyyy";

    // Binded FXML Elements
    @FXML
    private TitledPane paneListView;

    @FXML
    private ListView<Text> listBody;

    /**
     * Default Overloaded Constructor
     * 
     * @param title
     *            The title associated with this list
     * @param list
     *            The list to be displayed
     * @throws IOException
     *             Thrown if error encountered while reading FXML
     */
    public ListPane(String title, ArrayList<Task> list) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(
                FXML_CELL_DATE));
        loader.setRoot(this);
        loader.setController(this);
        loader.load();

        paneListView.setText(title);
        populateList(list);
    }

    /**
     * Method to populate the view with data
     * 
     * @param list
     *            The list to display on the view
     */
    private void populateList(ArrayList<Task> list) {
        if (list == null)
            return;
        for (Task t : list) {
            SimpleDateFormat formatter = new SimpleDateFormat(REG_MONTH_YEAR);
            String deadline = formatter.format(t.getTaskDeadLine().getTime());
            String temp = String.format(REG_TASK_DISPLAY, deadline,
                    t.getTaskID(), t.getTaskName());
            Text text = new Text(temp);
            text.wrappingWidthProperty().setValue(MAX_TEXT_WIDTH);
            listBody.getItems().add(text);
        }
    }
}