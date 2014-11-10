/**
 * 
 */
package taskaler.ui.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import taskaler.common.configurations.Configuration;
import taskaler.common.data.DeadLineTask;
import taskaler.common.data.RepeatedTask;
import taskaler.common.data.Task;
import taskaler.common.util.parser.calendarToString;
import taskaler.ui.model.CalendarPaneModel;
import taskaler.ui.model.CellDateModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

/**
 * Class that acts as the controller for CalendarPaneView FXML. This class
 * renders a view for a specified month and year
 * 
 * @author Cheah Kit Weng, A0059806W
 *
 */
// @author A0059806W
public class CalendarPaneController extends BorderPane implements IController {
    private static final String FX_BACKGROUND_COLOR = "-fx-background-color: %s;";

    // Current model associated with this controller
    private CalendarPaneModel currentModel = null;

    // Special Constants
    private static final String FXML_ELEMENT_GROUP = "Group";
    private static final String FXML_ELEMENT_ANCHOR_PANE = "AnchorPane";
    private static final String REG_MONTH_YEAR = "MMM yyyy";
    private static final int MAX_RENDERABLE_ROWS = 5;
    private static final int MAX_DAYS_IN_A_WEEK = 7;
    private static final int MAX_NUMBER_OF_DAYS = 31;

    // Binded FXML Elements
    @FXML
    private Label lblCurrent;

    @FXML
    private GridPane gridView;

    /**
     * Overloaded Constructor for inbuilt goto and listing items
     * 
     * @param model
     *            Model to be associated with this controller
     * @throws IOException
     *             Thrown when an error is encountered while updating or
     *             initializing the controller
     */
    public CalendarPaneController(CalendarPaneModel model) throws IOException {
        currentModel = model;
        if (currentModel.currentTaskList == null) {
            currentModel.currentTaskList = new ArrayList<Task>();
        }

        if (currentModel.currentCalendar == null) {
            currentModel.currentCalendar = Calendar.getInstance();
        }

        initialize(common.FXML_CALENDAR);
        update();
    }

    /**
     * Method to set the title of this view
     * 
     * @param title
     *            String to set the title to
     */
    public void setTitle(String title) {
        lblCurrent.setText(title);
    }

    @Override
    public void update() throws IOException {
        goToDate(currentModel.currentCalendar);
    }

    @Override
    public void initialize(String FXML) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(FXML));
        loader.setRoot(this);
        loader.setController(this);
        loader.load();
    }

    /**
     * Method to populate the calendar grids
     * 
     * @param date
     *            The calendar object to set the month and year of the calendar
     * @throws IOException
     *             Thrown if an error is encountered while reading CellDate FXML
     */
    private void populateCalendar(Calendar date) throws IOException {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, date.get(Calendar.YEAR));
        cal.set(Calendar.MONTH, date.get(Calendar.MONTH));
        cal.set(Calendar.DAY_OF_MONTH, common.OFFSET_BY_ONE);

        cal.setFirstDayOfWeek(Calendar.SUNDAY);

        String currentMonthAndYear = calendarToString.parseDate(cal,
                REG_MONTH_YEAR);
        setTitle(currentMonthAndYear);

        int[][] tasksByDay = computeMonth(cal.get(Calendar.MONTH),
                cal.get(Calendar.YEAR));

        int dayOfTheWeekIterator = cal.get(Calendar.DAY_OF_WEEK)
                - common.OFFSET_BY_ONE;
        int weekOfTheMonthIterator = common.OFFSET_BY_ONE;

        for (int i = common.OFFSET_BY_ONE; i <= cal
                .getActualMaximum(Calendar.DAY_OF_MONTH); i++) {

            CellDateController day = createCellDate(tasksByDay, i);

            gridView.add(day, dayOfTheWeekIterator, weekOfTheMonthIterator);
            if (isToday(date, i)) {
                day.setStyle(String.format(FX_BACKGROUND_COLOR, Configuration
                        .getInstance().getDefaultHeaderColor()));
            }

            dayOfTheWeekIterator++;
            if (isThisDaySunday(dayOfTheWeekIterator)) {
                weekOfTheMonthIterator++;
                dayOfTheWeekIterator = common.ZERO_INDEX;
                if (hasOverflowedCalendarUI(weekOfTheMonthIterator)) {
                    weekOfTheMonthIterator = common.OFFSET_BY_ONE;
                }
            }

        }
    }

    /**
     * Method to create a cell date
     * 
     * @param tasksByDay
     *            Array of tasks by the day
     * @param i
     *            Current day
     * @return Newly created cell date
     * @throws IOException
     *             Thrown if an error is encountered while rendering the cell
     *             date
     */
    private CellDateController createCellDate(int[][] tasksByDay, int i)
            throws IOException {
        CellDateModel model = new CellDateModel();
        model.currentDate = i;
        model.currentNumberOfEvents = tasksByDay[i][common.ZERO_INDEX];
        model.currentWorkloadFilters = tasksByDay[i][common.OFFSET_BY_ONE];
        CellDateController day = new CellDateController(model);
        return day;
    }

    /**
     * Method to check if the date being processed is today
     * 
     * @param date
     *            The month and year of processing
     * @param i
     *            the The day of processing
     * @return True if the system date is the same as the processing date; False
     *         otherwise
     */
    private boolean isToday(Calendar date, int i) {
        return Calendar.getInstance().get(Calendar.DAY_OF_MONTH) == i
                && Calendar.getInstance().get(Calendar.MONTH) == date
                        .get(Calendar.MONTH)
                && Calendar.getInstance().get(Calendar.YEAR) == date
                        .get(Calendar.YEAR);
    }

    /**
     * Method to count the number of tasks and obtain the collective workload
     * for each day of the month
     * 
     * @param month
     *            The month to compute
     * @param year
     *            The year to compute
     * @return returns an array of int with each index representing the the day
     *         and the values representing the total tasks and collective
     *         workload
     */
    private int[][] computeMonth(int month, int year) {
        int[][] result = new int[MAX_NUMBER_OF_DAYS + common.OFFSET_BY_ONE][2];

        for (int i = common.ZERO_INDEX; i < currentModel.currentTaskList.size(); i++) {
            Task currentTask = currentModel.currentTaskList.get(i);
            Calendar currentTime = Calendar.getInstance();
            if (currentTask instanceof DeadLineTask) {
                currentTime = ((DeadLineTask) currentTask).getDeadline();
                if (isTaskInCurrentMonthAndYear(month, year, currentTime)) {
                    result[currentTime.get(Calendar.DATE)][common.ZERO_INDEX]++;

                    if (result[currentTime.get(Calendar.DATE)][common.OFFSET_BY_ONE] < mapStringToWorkload(currentTask
                            .getTaskWorkLoad())) {
                        result[currentTime.get(Calendar.DATE)][common.OFFSET_BY_ONE] = mapStringToWorkload(currentTask
                                .getTaskWorkLoad());
                    }
                }
            } else if (currentTask instanceof RepeatedTask) {
                RepeatedTask currentRepeated = (RepeatedTask) currentTask;
                for (int j = common.ZERO_INDEX; j < currentRepeated
                        .getRepeatedDate().size(); j++) {
                    currentTime = currentRepeated.getRepeatedDate().get(j);
                    if (isTaskInCurrentMonthAndYear(month, year, currentTime)) {
                        result[currentTime.get(Calendar.DATE)][common.ZERO_INDEX]++;

                        if (result[currentTime.get(Calendar.DATE)][common.OFFSET_BY_ONE] < mapStringToWorkload(currentTask
                                .getTaskWorkLoad())) {
                            result[currentTime.get(Calendar.DATE)][common.OFFSET_BY_ONE] = mapStringToWorkload(currentTask
                                    .getTaskWorkLoad());
                        }
                    }
                }
            }
        }

        return result;
    }

    /**
     * Method to check if a calendar is in the current month and year
     * 
     * @param month
     *            Current month
     * @param year
     *            Current year
     * @param currentTime
     *            Calendar to be checked
     * @return True if the calendar is in the current month and year; False
     *         otherwise
     */
    private boolean isTaskInCurrentMonthAndYear(int month, int year,
            Calendar currentTime) {
        return currentTime.get(Calendar.MONTH) == month
                && currentTime.get(Calendar.YEAR) == year;
    }

    /**
     * Helper method to map a string workload to a integer representation
     * 
     * @param workload
     *            String to be converted
     * @return Integer representation of the workload
     */
    private int mapStringToWorkload(String workload) {
        if (workload.compareToIgnoreCase(Task.WORKLOAD_NONE) == 0) {
            return common.RECTANGLE_COLOR_GREY;
        } else if (workload.compareToIgnoreCase(Task.WORKLOAD_LOW) == 0) {
            return common.RECTANGLE_COLOR_GREEN;
        } else if (workload.compareToIgnoreCase(Task.WORKLOAD_MEDIUM) == 0) {
            return common.RECTANGLE_COLOR_ORANGE;
        } else if (workload.compareToIgnoreCase(Task.WORKLOAD_HIGH) == 0) {
            return common.RECTANGLE_COLOR_RED;
        }

        return 0;
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
     * Method to move calendar to a specified month and year
     * 
     * @param now
     *            The calendar object that represent the desired month and year
     * @throws IOException
     *             Thrown if an IO error is encountered while loading CellDate
     */
    public void goToDate(Calendar now) throws IOException {
        clearGrid();
        populateCalendar(now);
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

    @Override
    public HashMap<String, String> getState() {
        return currentModel.toHashMap();
    }

}
