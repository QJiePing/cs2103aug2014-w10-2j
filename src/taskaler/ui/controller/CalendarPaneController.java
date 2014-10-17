/**
 * 
 */
package taskaler.ui.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import taskaler.common.data.FXML_CONSTANTS;
import taskaler.common.data.Task;
import taskaler.ui.model.CalendarPaneModel;
import taskaler.ui.model.CellDateModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

/**
 * @author Kiwi
 *
 */
public class CalendarPaneController extends BorderPane implements IController {
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

        initialize(FXML_CONSTANTS.FXML_CALENDAR);
        update();
    }

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
        cal.set(Calendar.DAY_OF_MONTH, FXML_CONSTANTS.OFFSET_BY_ONE);

        cal.setFirstDayOfWeek(Calendar.SUNDAY);

        SimpleDateFormat formatter = new SimpleDateFormat(REG_MONTH_YEAR);
        String currentMonthAndYear = formatter.format(cal.getTime());
        setTitle(currentMonthAndYear);

        int[] numberOfTasksByDay = countTasks(cal.get(Calendar.MONTH));

        int dayOfTheWeekIterator = cal.get(Calendar.DAY_OF_WEEK)
                - FXML_CONSTANTS.OFFSET_BY_ONE;
        int weekOfTheMonthIterator = FXML_CONSTANTS.OFFSET_BY_ONE;

        for (int i = FXML_CONSTANTS.OFFSET_BY_ONE; i <= cal
                .getActualMaximum(Calendar.DAY_OF_MONTH); i++) {

            CellDateModel model = new CellDateModel();
            model.currentDate = i;
            model.currentNumberOfEvents = numberOfTasksByDay[i];
            CellDateController day = new CellDateController(model);
            
            gridView.add(day, dayOfTheWeekIterator, weekOfTheMonthIterator);
            
            dayOfTheWeekIterator++;
            if (isThisDaySunday(dayOfTheWeekIterator)) {
                weekOfTheMonthIterator++;
                dayOfTheWeekIterator = FXML_CONSTANTS.ZERO_INDEX;
                if (hasOverflowedCalendarUI(weekOfTheMonthIterator)) {
                    weekOfTheMonthIterator = FXML_CONSTANTS.OFFSET_BY_ONE;
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
        int[] result = new int[MAX_NUMBER_OF_DAYS + FXML_CONSTANTS.OFFSET_BY_ONE];

        for (int i = FXML_CONSTANTS.ZERO_INDEX; i < currentModel.currentTaskList.size(); i++) {
            Task currentTask = currentModel.currentTaskList.get(i);
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

}
