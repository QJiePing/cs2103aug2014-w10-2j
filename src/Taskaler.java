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
    public static Controller controller = null;
    public static Logic logic = null;
    public static OPLogic opLogic = null;
    public static Storage storage = null;
    public static UI ui = null;
    public static ViewLogic viewLogic = null;

    // Global Task ArrayList
    public static ArrayList<Task> taskList = null;
    
    // Global Task ID. Need to reload from storage
    // This line of code need to be changed
    public static int taskID = 0;

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
        taskList = new ArrayList<Task>();
        controller = new Controller();
        ui = new UI();
        ui.start(primaryStage);
        
    }

}
