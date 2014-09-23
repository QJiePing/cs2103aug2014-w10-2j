import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

/**
 * Main Class to create the required UI
 * for the application This class also
 * serves as the Action Event Controller
 * for the UI. i.e. OnClick Events
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
    private AnchorPane anchorPaneDisplay;

    @FXML
    private TextField txtCmdInput;

    /**
     * Method to start rendering the UI
     * elements of Taskaler
     * 
     * @param stage
     *            Main stage to render
     *            UI
     */
    @Override
    public void start(Stage stage) {
        try {

            stage.getIcons()
                    .add(new Image(
                            getClass()
                                    .getResourceAsStream(
                                            ICON_PNG)));
            stage.setTitle(TITLE);
            stage.setResizable(false);

            FXMLLoader root = new FXMLLoader(
                    getClass()
                            .getResource(
                                    FXML_ROOT));
            root.setController(this);
            Parent pane = root.load();
            Scene scene = new Scene(
                    pane, 400, 485);

            stage.setScene(scene);
            stage.show();

            displayCalendar();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Method which is binded to the
     * KeyPressed event of txtCmdInput
     * element This method passes user
     * input to the controller
     * 
     * @param e
     *            Key pressed event
     */
    @FXML
    private void txtCmdInputKeyPressed(
            KeyEvent e) {
        if (e.getCode() == KeyCode.ENTER) {
            try {
                Taskaler.controller
                        .executeCMD(txtCmdInput
                                .getText());
            } catch (Exception e1) {
                // TODO Auto-generated
                // catch block
                e1.printStackTrace();
            }
        }
    }

    /**
     * Method to render the calendar
     * view
     * 
     */
    public void displayCalendar() {
        anchorPaneDisplay.getChildren()
                .clear();
        try {
            CalendarPane pane = new CalendarPane();
            anchorPaneDisplay
                    .getChildren().add(
                            pane);

        } catch (IOException e) {
            // TODO Auto-generated catch
            // block
            e.printStackTrace();
        }
    }

    /**
     * Method to render the list view()
     * 
     */
    public void displayList()
            throws Exception {
        // TODO Implement a list view
        throw new Exception(
                "Not Yet Implemented");
    }
}

/**
 * Class that acts as a controller for
 * calendarPane FXML This class renders
 * the calendar view
 *
 */
class CalendarPane extends BorderPane {

    // Special Constants
    private static final String REG_MONTH_YEAR = "MMM yyyy";

    // FXML File Constant
    private static final String FXML_CALENDAR = "/fxml/calendarPane.fxml";

    // Class Variables
    private String currentMonthAndYear = "";

    // Binded FXML Elements
    @FXML
    private Label lblCurrent;

    @FXML
    private GridPane gridView;

    /**
     * Default Constructor
     * 
     * @throws IOException
     *             Thrown when error met
     *             while reading FXML
     */
    public CalendarPane()
            throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        FXML_CALENDAR));
        loader.setRoot(this);
        loader.setController(this);
        loader.load();

        resetToToday();
    }

    /**
     * Method to set the title of the
     * calendar
     * 
     * @param s
     *            String to set title to
     */
    public void setTitle(String s) {
        lblCurrent.setText(s);
    }

    /**
     * Method to populate the calendar
     * grids
     * 
     * @param date
     *            The calendar object to
     *            set the month and year
     *            of the calendar
     */
    public void populateCalendar(
            Calendar date) {
        Calendar cal = Calendar
                .getInstance();
        cal.set(Calendar.YEAR,
                date.get(Calendar.YEAR));
        cal.set(Calendar.MONTH, date
                .get(Calendar.MONTH));
        cal.set(Calendar.DAY_OF_MONTH,
                1);

        cal.setFirstDayOfWeek(Calendar.SUNDAY);

        SimpleDateFormat formatter = new SimpleDateFormat(
                REG_MONTH_YEAR);
        currentMonthAndYear = formatter
                .format(cal.getTime());
        setTitle(currentMonthAndYear);

        int dayOfTheWeekIterator = cal
                .get(Calendar.DAY_OF_WEEK) - 1;
        int weekOfTheMonthIterator = 1;

        for (int i = 1; i <= cal
                .getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
            try {
                CellDate day = new CellDate(
                        i + "");
                gridView.add(
                        day,
                        dayOfTheWeekIterator,
                        weekOfTheMonthIterator);
                dayOfTheWeekIterator++;
                if (isThisDaySunday(dayOfTheWeekIterator)) {
                    weekOfTheMonthIterator++;
                    dayOfTheWeekIterator = 0;
                }
            } catch (IOException e) {
                // TODO Auto-generated
                // catch block
                e.printStackTrace();
            }
        }
    }

    /**
     * Method to determine if the day is
     * next sunday
     * 
     * @param dayOfTheWeekIterator
     *            The day to be checked
     * @return True if the day is next
     *         Sunday; False otherwise
     */
    private boolean isThisDaySunday(
            int dayOfTheWeekIterator) {
        return dayOfTheWeekIterator % 7 == 0;
    }

    /**
     * Method to quickly reset the
     * calendar to System current month
     * and year
     * 
     */
    public void resetToToday() {
        populateCalendar(Calendar
                .getInstance());
    }
}

/**
 * Class that acts as the controller for
 * cellDate FXML. This class modifies a
 * single cell of the calendar
 *
 */
class CellDate extends AnchorPane {

    // FXML File Constant
    private static final String FXML_CELL_DATE = "/fxml/cellDate.fxml";

    // Binded FXML Elements
    @FXML
    private Label lblDate;

    /**
     * Default overloaded constructor
     * 
     * @param date
     *            The date to set the
     *            cell to
     * @throws IOException
     *             Thrown when error met
     *             while reading FXML
     */
    public CellDate(String date)
            throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        FXML_CELL_DATE));
        loader.setRoot(this);
        loader.setController(this);
        loader.load();

        lblDate.setText(date);
    }
}