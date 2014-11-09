/**
 * 
 */
package taskaler.ui;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.text.SimpleDateFormat;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import taskaler.archive.OperationRecord;
import taskaler.common.configurations.Configuration;
import taskaler.common.data.Task;
import taskaler.common.data.TaskList;
import taskaler.common.util.CommonLogger;
import taskaler.storage.TaskAndConfigStorage;
import taskaler.ui.controller.RootController;
import taskaler.ui.controller.TutorialPaneController;
import taskaler.ui.hook.DLLConnector;
import taskaler.ui.model.RootModel;
import taskaler.ui.model.TutorialPaneModel;

/**
 * Class to act as the facade for the UI component
 * 
 * @author Cheah Kit Weng, A0059806W
 *
 */
//@author A0059806W
public class UIFacade extends Application implements Observer {
    // Special Constants
    private static final String TITLE = "Taskaler";
    private static final String DEFAULT_VIEW_CALENDAR = "CALENDAR";
    private static final String DEFAULT_VIEW_LIST = "LIST";
    
    // User Profile Configs
    private static String userDefaultView = DEFAULT_VIEW_CALENDAR;

    // File Constants
    private static final String ICON_PNG = "/res/icon.png";

    // Class Variables
    private RootController rootController = null;
    private Stage primaryStage = null;

    @Override
    public void start(Stage stage) {
        try {
            
            userDefaultView = Configuration.getInstance().getDefaultView();
            stage.getIcons().add(
                    new Image(getClass().getResourceAsStream(ICON_PNG)));
            stage.setTitle(TITLE);
            stage.setResizable(false);

            RootModel model = new RootModel();
            model.totalFloating = TaskList.getInstance().floatToArray().size();
            model.totalNotDone = TaskList.getInstance().getNumOfIncomplete();

            rootController = new RootController(model);
            Parent pane = rootController;
            Scene scene = new Scene(pane, 400, 499);

            stage.setScene(scene);
            stage.sizeToScene();

            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent we) {
                    DLLConnector.isStopped.set(true);
                }
            });
            
            stage.show();
            primaryStage = stage;
            
            if(Configuration.getInstance().getIsFirstRun()){
                Stage tutorialStage = new Stage();
                tutorialStage.initStyle(StageStyle.UNDECORATED);
                TutorialPaneModel tutorialModel = new TutorialPaneModel();
                tutorialModel.page = 0;
                TutorialPaneController tutController = new TutorialPaneController(tutorialModel);
                Parent secondaryParent = tutController;
                Scene secondaryScene = new Scene(secondaryParent);
                tutorialStage.setScene(secondaryScene);
                tutorialStage.sizeToScene();
                stage.toBack();
                tutorialStage.toFront();
                tutorialStage.show();
            }
            
            createHook();
            // createCleanUp();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    /**
     * Method to create the hotkey hook
     * 
     */
    private void createHook() {
        try {

            // Create the source of the key press
            final DLLConnector eventSource = new DLLConnector();

            // subscribe the observer to the event source
            eventSource.addObserver(this);

            // starts the event thread
            Thread thread = new Thread(eventSource);
            thread.start();
        } catch (UnsatisfiedLinkError err) {
            CommonLogger.getInstance().exceptionLogger(err, Level.WARNING);
        } catch (Exception err) {
            CommonLogger.getInstance().exceptionLogger(err, Level.WARNING);
        }
    }

    

    /**
     * Method to render default view for the list of task
     */
    public void display(ArrayList<Task> list) {
        display(userDefaultView, list);
    }

    /**
     * Method to render a dynamic list
     * 
     * @param title
     *            Title of the list view
     * @param headers
     *            Array of sub headers
     * @param arrayOfTasks
     *            Arraya of array list
     */
    public void display(String title, ArrayList<String> headers,
            ArrayList<ArrayList<Task>> arrayOfTasks) {
        
        try {
            rootController.displayDynamicList(title, headers, arrayOfTasks);
        } catch (IOException e) {
            rootController.showToast("IO error encountered!");
            CommonLogger.getInstance().exceptionLogger(e, Level.SEVERE);
        }
    }

    /**
     * Method to render a view, either in list or calendar.
     * 
     * @param args
     *            Arguments to determine the view properties
     * @param list
     *            The list of tasks to be displayed
     */
    public void display(String args, ArrayList<Task> list) {
        if (list == null) {
            list = new ArrayList<Task>();
        }
        try {
            if (args.compareToIgnoreCase(DEFAULT_VIEW_LIST) == 0) {
                rootController.displayList("All Current Tasks", list);
            } else if (args.compareToIgnoreCase(DEFAULT_VIEW_CALENDAR) == 0) {
                rootController.displayCalendar(list, Calendar.getInstance());
            } else {
                rootController.displayList(args, list);
            }
        } catch (IOException e) {
            rootController.showToast("IO error encountered!");
            CommonLogger.getInstance().exceptionLogger(e, Level.SEVERE);
        }
    }

    /**
     * Method to display the calendar for a specified month
     * 
     * @param month
     *            Month to be displayed
     * @param list
     *            List of tasks
     */
    public void displayMonth(String date, ArrayList<Task> list) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("MM/yyyy");
        try {
            cal.setTime(format.parse(date));
            rootController.displayCalendar(list, cal);
        } catch (IOException e) {
            rootController.showToast("IO error encountered!");
            CommonLogger.getInstance().exceptionLogger(e, Level.SEVERE);
        } catch (Exception e) {
            rootController.showToast("Parse Exception encountered!");
            CommonLogger.getInstance().exceptionLogger(e, Level.SEVERE);
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
                CommonLogger.getInstance().exceptionLogger(e, Level.SEVERE);
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

    /**
     * Method to show a text pane view
     * 
     * @param title
     *            title of the text view
     * @param text
     *            body of the text view
     */
    public void display(String title, String text) {
        try {
            rootController.displayText(title, text);
        } catch (IOException e) {
            rootController.showToast("IO error encountered!");
            CommonLogger.getInstance().exceptionLogger(e, Level.SEVERE);
        }
    }

    /**
     * Method to obtain the current state of the view being displayed
     * 
     * @return Hashmap representation of the state of view
     */
    public HashMap<String, String> getCurrentState() {
        return rootController.getState();
    }

    @Override
    public void update(Observable arg0, Object arg1) {
        if (arg1 instanceof String) {
            if (((String) arg1).compareToIgnoreCase("WM_HOTKEY") == 0) {
                Platform.runLater(new Runnable() {

                    @Override
                    public void run() {
                        if (primaryStage.isIconified()) {
                            rootController.giveFocus();
                            primaryStage.setIconified(false);
                            primaryStage.toFront();
                        } else {
                            primaryStage.setIconified(true);
                        }
                    }
                });
            } else {
                System.out.println("No Idea who called this event");
            }
        } else if (arg1 instanceof OperationRecord) {
            rootController.update(TaskList.getInstance().getNumOfIncomplete(),
                    TaskList.getInstance().floatToArray().size());
        }
    }

    /**
     * Public method to display the confirmation interface
     * 
     * @return True if the confirmation is received; False otherwise
     */
    public Boolean showConfirmation() {
        return rootController.showConfirmation();
    }
}
