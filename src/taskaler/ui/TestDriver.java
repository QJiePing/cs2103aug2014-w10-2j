/**
 * 
 */
package taskaler.ui;

import java.io.IOException;
import java.util.Calendar;
import java.util.Scanner;

import taskaler.common.util.parser.calendarToString;
import taskaler.ui.controller.CalendarPaneController;
import taskaler.ui.controller.ListPaneController;
import taskaler.ui.controller.RootController;
import taskaler.ui.controller.TaskPaneController;
import taskaler.ui.controller.common;
import taskaler.ui.model.CalendarPaneModel;
import taskaler.ui.model.ListPaneModel;
import taskaler.ui.model.RootModel;
import taskaler.ui.model.TaskPaneModel;
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
            case "Root":
                RootModel rootModel = new RootModel();
                RootController root = new RootController(rootModel);
                TestArea = root;
                break;
            case "TaskPane":
                TaskPaneModel taskPaneModel = new TaskPaneModel();
                taskPaneModel.taskName = "Test";
                taskPaneModel.taskID = "T35+";
                taskPaneModel.taskDueDate = calendarToString.parseDate(Calendar
                        .getInstance());
                taskPaneModel.taskStatus = "Not Completed";
                taskPaneModel.taskWorkload = common.RectangleColor.GREEN;
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
            case "Facade":
                isTestingFacade = true;
            }
            launch();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

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
