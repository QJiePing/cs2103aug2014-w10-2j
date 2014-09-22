import java.util.ArrayList;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Main Class object to execute and
 * store global variables
 *
 */
public class Taskaler extends
        Application {

    // Global Instances
    private static Controller controller = null;
    private static Logic logic = null;
    private static OPLogic opLogic = null;
    private static Storage storage = null;
    private static UI ui = null;
    private static ViewLogic viewLogic = null;

    // Global Task ArrayList
    public static ArrayList<Task> taskList = null;

    /**
     * Main method to start process
     * 
     * @param args
     */
    public static void main(
            String[] args) {
        launch(args);
    }

    /**
     * Method to start launching the
     * application
     * 
     * @param primaryStage
     *            Main stage argument
     *            for ui
     */
    @Override
    public void start(Stage primaryStage)
            throws Exception {
        controller = new Controller();
        ui = new UI();
        ui.start(primaryStage);

    }

}
