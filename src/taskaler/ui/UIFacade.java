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
import taskaler.storage.Storage;
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
public class UIFacade extends Application implements Observer {
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

    // DLL Constants
    private static final String DLL_PATH_OUTPUT = "/Taskaler";
    private static final String DLL_PATH_PARENT = "/lib";
    private static final String DLL_PATH_BIT_32 = "/x86";
    private static final String DLL_PATH_BIT_64 = "/x64";
    private static final String DLL_PATH_32 = "/JNILibrary.dll";
    private static final String DLL_PATH_64 = "/JNILibrary64.dll";
    private static final String DLL_PATH_MSVCR = "/msvcr120.dll";
    private static File[] libraryLoaded = null;

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
     * Method to create clean up procedures
     * 
     */
    private void createCleanUp() {
        // TODO Implementation required
        Thread shutDownHook = new Thread() {
            @Override
            public void run() {
                if (libraryLoaded != null) {

                }
            }
        };
        Runtime.getRuntime().addShutdownHook(shutDownHook);

    }

    /**
     * Method to create the hotkey hook
     * 
     */
    private void createHook() {
        try {
            String jvmVer = System.getProperty("sun.arch.data.model");
            libraryLoaded = new File[2];
            if (jvmVer.compareTo("32") == 0) {
                libraryLoaded[0] = loadDll(DLL_PATH_PARENT + DLL_PATH_BIT_32,
                        DLL_PATH_OUTPUT, DLL_PATH_MSVCR);
                libraryLoaded[1] = loadDll(DLL_PATH_PARENT + DLL_PATH_BIT_32,
                        DLL_PATH_OUTPUT, DLL_PATH_32);
            } else if (jvmVer.compareTo("64") == 0) {
                libraryLoaded[0] = loadDll(DLL_PATH_PARENT + DLL_PATH_BIT_64,
                        DLL_PATH_OUTPUT, DLL_PATH_MSVCR);
                libraryLoaded[1] = loadDll(DLL_PATH_PARENT + DLL_PATH_BIT_64,
                        DLL_PATH_OUTPUT, DLL_PATH_64);
            } else {
                throw new Exception("Unknown JVM version detected");
            }

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
     * Method to load a dll into the JVM
     * 
     * @param parent
     *            The path to the parent of the library
     * @param outputFolder
     *            The destination folder
     * @param library
     *            the library to be loaded
     * @return the file object of the library
     * @throws IOException
     *             thrown if there is an error reading the file
     */
    public File loadDll(String parent, String outputFolder, String library)
            throws IOException, UnsatisfiedLinkError {
        InputStream in = Storage.class.getResourceAsStream(parent + library);
        byte[] buffer = new byte[1024];
        int read = -1;
        File windowsUserTempDirectory = new File(
                System.getProperty("java.io.tmpdir") + outputFolder);
        if (!windowsUserTempDirectory.exists()) {
            windowsUserTempDirectory.mkdir();
        }
        File temp = new File(windowsUserTempDirectory, library);
        if (temp.exists()) {
            temp.delete();
        }
        // System.out.println("Creating temp dll: " + temp.getAbsolutePath());
        FileOutputStream fos = new FileOutputStream(temp);

        while ((read = in.read(buffer)) != -1) {
            fos.write(buffer, 0, read);
        }
        fos.close();
        in.close();

        System.load(temp.getAbsolutePath());

        return temp;
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
