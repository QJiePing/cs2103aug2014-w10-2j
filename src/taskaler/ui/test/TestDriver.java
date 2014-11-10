/**
 * 
 */
package taskaler.ui.test;

import java.io.IOException;
import java.util.Calendar;
import java.util.Scanner;

import taskaler.common.util.parser.calendarToString;
import taskaler.ui.UIFacade;
import taskaler.ui.controller.CalendarPaneController;
import taskaler.ui.controller.ListPaneController;
import taskaler.ui.controller.RootController;
import taskaler.ui.controller.TaskPaneController;
import taskaler.ui.controller.TextPaneController;
import taskaler.ui.controller.TutorialPaneController;
import taskaler.ui.controller.common;
import taskaler.ui.hook.DLLConnector;
import taskaler.ui.hook.KeyPressHandler;
import taskaler.ui.model.CalendarPaneModel;
import taskaler.ui.model.ListPaneModel;
import taskaler.ui.model.RootModel;
import taskaler.ui.model.TaskPaneModel;
import taskaler.ui.model.TextPaneModel;
import taskaler.ui.model.TutorialPaneModel;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Test driver to launch tests against the UI component
 * 
 * @author Cheah Kit Weng, A0059806W
 *
 */
//@author A0059806W
public class TestDriver extends Application {

    // Test Messages
    private static final String MSG_TEST_HOOK = "Starting hook test...";
    private static final String TEST_TEXT_BODY = "WEIRD WEIRD\n WEIRD\n WEIRD WEIRD\n WEIRD\n WEIRD WEIRD\n WEIRD\n ";
    private static final String TEST_TEXT_TITLE = "Title Text";
    private static final String TEST_LIST_TITLE = "Test List";
    private static final String TEST_TASK_DESCRIPTION = "Test\ntest test";
    private static final String TEST_TASK_ID = "T35+";
    private static final String TEST_TASK_NAME = "Test";

    // UI Elements available for test
    private static final String UI_FACADE = "Facade";
    private static final String TEXT_PANE = "TextPane";
    private static final String HOOK = "Hook";
    private static final String LIST_PANE = "ListPane";
    private static final String TASK_PANE = "TaskPane";
    private static final String ROOT_PANE = "Root";
    private static final String TUTORIAL_PANE = "TutorialPane";
    private static final String CALENDAR_PANE = "CalendarPane";

    // Prompt
    private static final String MSG_PROMPT = "Please key in UI component to test:";

    // Class variables
    private static Parent TestArea = null;
    private static boolean isTestingFacade = false;

    // Location of current DLL library
    private static final String DLL_PATH = "/JNILibrary64";

    /**
     * Main Method to test the UI component
     * 
     * @param args
     *            Commandline arguments
     */
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        System.out.println(MSG_PROMPT);
        try {
            switch (sc.nextLine()) {
            case CALENDAR_PANE:
                CalendarPaneController calendarPane = createCalendarPane();
                TestArea = calendarPane;
                break;
            case TUTORIAL_PANE:
                TutorialPaneController controller = createTutorialPane();
                TestArea = controller;
                break;
            case ROOT_PANE:
                RootController root = createRootPane();
                TestArea = root;
                break;
            case TASK_PANE:
                TaskPaneController taskPane = createTaskPane();
                TestArea = taskPane;
                break;
            case LIST_PANE:
                ListPaneController listPane = createListPane();
                TestArea = listPane;
                break;
            case HOOK:
                new TestDriver().startHookTest();
                return;
            case TEXT_PANE:
                TextPaneController textPaneController = createTextPane();
                TestArea = textPaneController;
                break;
            case UI_FACADE:
                isTestingFacade = true;
            }
            launch();
        } catch (IOException e) {
            // Failed to run any case
            e.printStackTrace();
        }

    }

    /**
     * Method to create a text pane
     * 
     * @return Newly created text pane
     * @throws IOException
     *             Thrown if an error is encountered when an error is
     *             encountered while creating view
     */
    private static TextPaneController createTextPane() throws IOException {
        TextPaneModel textPaneModel = new TextPaneModel();
        textPaneModel.title = TEST_TEXT_TITLE;
        textPaneModel.textBody = TEST_TEXT_BODY;
        TextPaneController textPaneController = new TextPaneController(
                textPaneModel);
        return textPaneController;
    }

    /**
     * Method to create a list pane
     * 
     * @return Newly created list pane
     * @throws IOException
     *             Thrown if an error is encountered when an error is
     *             encountered while creating view
     */
    private static ListPaneController createListPane() throws IOException {
        ListPaneModel listPaneModel = new ListPaneModel();
        listPaneModel.currentTitle = TEST_LIST_TITLE;
        listPaneModel.currentItemList = null;
        ListPaneController listPane = new ListPaneController(listPaneModel);
        return listPane;
    }

    /**
     * Method to create a task pane
     * 
     * @return Newly created task pane
     * @throws IOException
     *             Thrown if an error is encountered when an error is
     *             encountered while creating view
     */
    private static TaskPaneController createTaskPane() throws IOException {
        TaskPaneModel taskPaneModel = new TaskPaneModel();
        taskPaneModel.taskName = TEST_TASK_NAME;
        taskPaneModel.taskID = TEST_TASK_ID;
        taskPaneModel.taskDate = calendarToString.parseDate(Calendar
                .getInstance());
        taskPaneModel.taskStatus = false;
        taskPaneModel.taskWorkload = common.RECTANGLE_COLOR_GREEN;
        taskPaneModel.taskDescription = TEST_TASK_DESCRIPTION;
        TaskPaneController taskPane = new TaskPaneController(taskPaneModel);
        return taskPane;
    }

    /**
     * Method to create a root pane
     * 
     * @return Newly created root pane
     * @throws IOException
     *             Thrown if an error is encountered when an error is
     *             encountered while creating view
     */
    private static RootController createRootPane() throws IOException {
        RootModel rootModel = new RootModel();
        RootController root = new RootController(rootModel);
        return root;
    }

    /**
     * Method to create a tutorial pane
     * 
     * @return Newly created tutorial pane
     * @throws IOException
     *             Thrown if an error is encountered when an error is
     *             encountered while creating view
     */
    private static TutorialPaneController createTutorialPane()
            throws IOException {
        TutorialPaneModel tutorialModel = new TutorialPaneModel();
        tutorialModel.page = 0;
        TutorialPaneController controller = new TutorialPaneController(
                tutorialModel);
        return controller;
    }

    /**
     * Method to create a calendar pane
     * 
     * @return Newly created calendar pane
     * @throws IOException
     *             Thrown if an error is encountered when an error is
     *             encountered while creating view
     */
    private static CalendarPaneController createCalendarPane()
            throws IOException {
        CalendarPaneModel calendarModel = new CalendarPaneModel();
        calendarModel.currentCalendar = Calendar.getInstance();
        calendarModel.currentMonth = Calendar.getInstance().get(Calendar.MONTH)
                + common.OFFSET_BY_ONE;
        calendarModel.currentYear = Calendar.getInstance().get(Calendar.YEAR);
        CalendarPaneController calendarPane = new CalendarPaneController(
                calendarModel);
        return calendarPane;
    }

    /**
     * Method to test the DLL hook
     * 
     */
    private void startHookTest() {
        System.out.println(MSG_TEST_HOOK);
        System.loadLibrary(DLL_PATH);

        // create an event source - reads from stdin
        final DLLConnector eventSource = new DLLConnector();

        // create an observer
        final KeyPressHandler responseHandler = new KeyPressHandler();

        // subscribe the observer to the event source
        eventSource.addObserver(responseHandler);

        // starts the event thread
        Thread thread = new Thread(eventSource);
        thread.start();
    }

    @Override
    public void start(Stage stage) {
        if (isTestingFacade) {
            UIFacade ui = new UIFacade();
            ui.start(stage);
        } else {
            Scene scene = new Scene(TestArea);
            stage.setScene(scene);
            stage.show();
        }
    }
}
