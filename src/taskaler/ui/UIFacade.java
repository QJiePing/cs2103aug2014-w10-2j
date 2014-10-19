/**
 * 
 */
package taskaler.ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.JOptionPane;

import taskaler.common.data.Task;
import taskaler.common.data.TaskList;
import taskaler.ui.controller.RootController;
import taskaler.ui.model.RootModel;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Class to act as the facade for the UI component
 * 
 * @author Cheah Kit Weng, A0059806W
 *
 */
public class UIFacade extends Application {
    // Special Constants
    private static final String TITLE = "Taskaler";
    private static final String DEFAULT_VIEW_CALENDAR = "CALENDAR";
    private static final String DEFAULT_VIEW_LIST = "LIST";
    private static final String[] DEFAULT_VIEW_OPTIONS = {
            DEFAULT_VIEW_CALENDAR, DEFAULT_VIEW_LIST };

    // User Profile Configs
    private static String userDefaultView = DEFAULT_VIEW_CALENDAR;

    // File Constants
    private static final String ICON_PNG = "/res/icon.png";

    // Class Variables
    private RootController rootController = null;

    @Override
    public void start(Stage stage) {
        try {
            userDefaultView = (String) JOptionPane.showInputDialog(null,
                    "Choose a default view", "Default View Option",
                    JOptionPane.INFORMATION_MESSAGE, null,
                    DEFAULT_VIEW_OPTIONS, DEFAULT_VIEW_OPTIONS[0]);
            stage.getIcons().add(
                    new Image(getClass().getResourceAsStream(ICON_PNG)));
            stage.setTitle(TITLE);
            stage.setResizable(false);
            stage.setWidth(410.0);
            stage.setHeight(525.0);

            RootModel model = new RootModel();
            rootController = new RootController(model);
            Parent pane = rootController;
            Scene scene = new Scene(pane, 400, 475);

            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    /**
     * Method to render default view for the list of task
     */
    public void display(ArrayList<Task> list) {
        display(userDefaultView, list);
    }

    /**
     * Method to render a view, either in list or calendar.
     * 
     */
    public void display(String args, ArrayList<Task> list) {
        if (list == null) {
            list = new ArrayList<Task>();
        }
        try {
            if (args.compareToIgnoreCase(DEFAULT_VIEW_LIST) == 0) {
                rootController.displayList("All current tasks", list);
            } else if (args.compareToIgnoreCase(DEFAULT_VIEW_CALENDAR) == 0) {
                rootController.displayCalendar(list, Calendar.getInstance());
            } else {
                rootController.displayList(args, list);
            }
        } catch (IOException e) {
            rootController.showToast("IO error encountered!");
            e.printStackTrace();
        }
    }

    /**
     * Method to render a view in Task view.
     * 
     */
    public void display(Task t) {
        if (t == null) {
            rootController.showToast("No task to be displayed");
        } else {
            try {
                rootController.displayTask(t);
            } catch (IOException e) {
                rootController.showToast("IO error encountered!");
                e.printStackTrace();
            }
        }
    }

    /**
     * Method to show the toast notification.
     * 
     * @param t
     *            String to be displayed
     */
    public void display(String t) {
        if (t != null && !t.isEmpty()) {
            rootController.showToast(t);
        }
    }
}
