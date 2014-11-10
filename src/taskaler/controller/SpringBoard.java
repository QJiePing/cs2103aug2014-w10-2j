//@author A0059806W

package taskaler.controller;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Class to act as springboard to begin launching the Taskaler
 */
public class SpringBoard extends Application{

    /**
     * Main method to start process
     * 
     * @param args
     */
    public static void main(String[] args) {

        // System.loadLibrary("JNILibrary64");
        launch(args);
    }

    /**
     * Method to start launching the application
     * 
     * @param primaryStage
     *            Main stage argument for UI
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        // System.out.println(getTest());
        //taskList = Storage.readFromFile(file);
        //if (taskList == null) {
        //    taskList = new ArrayList<Task>();
        //}
        Controller.getInstance().start(primaryStage);
    }
}
