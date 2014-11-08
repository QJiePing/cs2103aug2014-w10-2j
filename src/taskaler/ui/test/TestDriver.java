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
public class TestDriver extends Application {

    // Class variables
    private static Parent TestArea = null;
    private static boolean isTestingFacade = false;

    // Location of current DLL library
    private static final String DLL_PATH = "/JNILibrary64";

    /**
     * Main Method to test the UI component
     * 
     * @param args
     */
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        System.out.println("Please key in UI component to test:");
        try {
            switch (sc.nextLine()) {
            case "CalendarPane":
                CalendarPaneModel calendarModel = new CalendarPaneModel();
                calendarModel.currentCalendar = Calendar.getInstance();
                calendarModel.currentMonth = Calendar.getInstance().get(
                        Calendar.MONTH)
                        + common.OFFSET_BY_ONE;
                calendarModel.currentYear = Calendar.getInstance().get(
                        Calendar.YEAR);
                CalendarPaneController calendarPane = new CalendarPaneController(
                        calendarModel);
                TestArea = calendarPane;
                break;
            case "CellDate":
                break;
            case "TutorialPane":
                TutorialPaneModel tutorialModel = new TutorialPaneModel();
                tutorialModel.page = 0;
                TutorialPaneController controller = new TutorialPaneController(tutorialModel);
                TestArea = controller;
                break;
            case "Root":
                RootModel rootModel = new RootModel();
                RootController root = new RootController(rootModel);
                TestArea = root;
                break;
            case "TaskPane":
                TaskPaneModel taskPaneModel = new TaskPaneModel();
                taskPaneModel.taskName = "Test";
                taskPaneModel.taskID = "T35+";
                taskPaneModel.taskDate = calendarToString.parseDate(Calendar
                        .getInstance());
                taskPaneModel.taskStatus = false;
                taskPaneModel.taskWorkload = common.RECTANGLE_COLOR_GREEN;
                taskPaneModel.taskDescription = "Test\ntest test";
                TaskPaneController taskPane = new TaskPaneController(
                        taskPaneModel);
                TestArea = taskPane;
                break;

            case "ListPane":
                ListPaneModel listPaneModel = new ListPaneModel();
                listPaneModel.currentTitle = "Test List";
                listPaneModel.currentItemList = null;
                ListPaneController listPane = new ListPaneController(
                        listPaneModel);
                TestArea = listPane;
                break;
            case "Hook":
                new TestDriver().startHookTest();
                return;
            case "TextPane":
                TextPaneModel textPaneModel = new TextPaneModel();
                textPaneModel.title = "Title Text";
                textPaneModel.textBody = "WEIRD WEIRD\n WEIRD\n WEIRD WEIRD\n WEIRD\n WEIRD WEIRD\n WEIRD\n ";
                TextPaneController textPaneController = new TextPaneController(textPaneModel);
                TestArea = textPaneController;
                break;
            case "Facade":
                isTestingFacade = true;
            }
            launch();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    private void startHookTest() {
        System.out.println("Starting hook test...");
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
