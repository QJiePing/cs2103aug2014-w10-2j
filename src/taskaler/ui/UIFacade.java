/**
 * 
 */
package taskaler.ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import taskaler.common.data.Task;
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

    // File Constants
    private static final String ICON_PNG = "/images/icon.png";

    // Class Variables
    private RootController rootController = null;

    @Override
    public void start(Stage stage) {
        try {
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
            display("CAL", null);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    /**
     * Method to render a view, either in list or calendar. This method uses
     * default configurations to determine if view should be in calendar or list
     * 
     */
    public void display(String args, ArrayList<Task> list) {
        if (list == null) {
            list = new ArrayList<Task>();
        }
        try {
            if (args.compareToIgnoreCase("LIST") == 0) {
                rootController.displayList("All current tasks", list);
            } else if (args.compareToIgnoreCase("CAL") == 0) {
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

}
