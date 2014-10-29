
package taskaler.controller;

import taskaler.logic.OPLogic;
import taskaler.logic.SearchLogic;
import taskaler.storage.Storage;
import taskaler.ui.UIFacade;
import taskaler.ui.model.CalendarPaneModel;
import taskaler.ui.model.IModel;
import taskaler.ui.model.TaskPaneModel;
import taskaler.archive.PastHistory;
import taskaler.archive.Undo;
import taskaler.common.data.Task;
import taskaler.common.data.TaskList;
import static taskaler.controller.common.*;
import taskaler.controller.parser.Parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javafx.stage.Stage;

/**
 * @author Brendan
 *
 */
public class Controller{

    private static final String TASK_LIST_FILE = "task_list";

    private static UIFacade ui = null;

    private static TaskList list = null;

    private static Controller instance = null;
    
    private static OPLogic crudLogic = null;
    
    private static SearchLogic findLogic = null;
    
    private static PastHistory history = null;
    
    private static Undo undo = null;

    /*********************************** Public Functions ***********************************/

    /**
     * 
     * Method to parse user commands, and pass parameters to UI, Storage and
     * Logic
     * 
     * @param commandString
     */
    public void executeCMD(String commandString) {
        try {
            HashMap<String, String> stateVariables = ui.getCurrentState();
            Parser values = new Parser();
            values.parseCMD(commandString, stateVariables);
            CmdType commandType = values.getCommand();
            String[] params = values.getParameters();
            Task result = null;
            switch (commandType) {
            case ADD:
                String nameADD = params[0];
                String descriptionADD = params[1];
                String dateADD = params[2];
                String startTimeADD = params[3];
                String endTimeADD = params[4];
                String workloadADD = params[5];
                result = crudLogic.addTask(nameADD, descriptionADD, dateADD, 
                        startTimeADD, endTimeADD, workloadADD);
                ui.display(result);
                break;
            case DELETE:
                String taskID_DELETE = params[0];
                if(taskID_DELETE.equalsIgnoreCase("all")){
                    crudLogic.deleteAllTask();
                    ui.display("All tasks have been deleted.", 
                            list.toArray(new ArrayList<Task>()));
                }
                else {
                    result = crudLogic.deleteTask(taskID_DELETE);
                    String name_DELETED = result.getTaskName();
                    ui.display("The task \"" + name_DELETED
                            + "\" has been deleted.",
                            list.toArray(new ArrayList<Task>()));
                }
                break;
            case EDIT:
                String taskID_EDIT = params[0];
                String name_EDIT = params[1];
                String description_EDIT = params[2];
                assert (taskID_EDIT != null);
                result = crudLogic.editTask(taskID_EDIT, name_EDIT,
                        description_EDIT);
                ui.display(result);
                break;
            case DEADLINE:
                String taskIDDEADLINE = params[0];
                String newDeadline = params[1];
                result = crudLogic.editDate(taskIDDEADLINE, newDeadline);
                ui.display(result);
                break;
            case TIME:
                String taskIDTIME = params[0];
                String startTime = params[1];
                String endTime = params[2];
                result = crudLogic.editTime(taskIDTIME, startTime, endTime);
                ui.display(result);
            case REPEAT:
                String taskID_REPEAT = params[0];
                String pattern = params[1];
                String startDate = params[2];
                String endDate = params[3];
                result = crudLogic.setRepeat(taskID_REPEAT, pattern, startDate, endDate);
                ui.display(result);
                break;
            case WORKLOAD:
                String taskID_WORKLOAD = params[0];
                String workloadAttribute = params[1];
                assert (taskID_WORKLOAD != null);
                result = crudLogic.editWorkload(taskID_WORKLOAD,
                        workloadAttribute);
                ui.display(result);
                break;
            case COMPLETION_TAG:
                String taskID_CT = params[0];
                result = crudLogic.switchTag(taskID_CT);
                ui.display(result);
                break;
            case VIEW:
                String tag_VIEW = params[0];
                String taskID_VIEW = params[1];
                if (taskID_VIEW != null && !taskID_VIEW.isEmpty() && tag_VIEW.equals("TASK")) {
                    result = findLogic.findByID(params[1]);
                    ui.display(result);
                } else {
                    ui.display(tag_VIEW, list.toArray(new ArrayList<Task>()));
                }
                break;
            case FIND:
                String tagTypeFIND = params[0];
                String toSearch = params[1];
                ArrayList<Task> searchResult = findLogic.find(tagTypeFIND,
                        toSearch);
                ui.display("Search Result for " + toSearch, searchResult);
                break;
            case ARCHIVE:
                String date_ARCH = params[0];
                String out = PastHistory.retrieveHistory(date_ARCH);
                ui.display("History", out);
                break;
            case UNDO:
                result = undo.undo();
                ui.display(list.toArray(new ArrayList<Task>()));
                ui.display("Undone last operation");
                break;
            case GOTO:
                String date_GOTO = params[0];
                ui.displayMonth(date_GOTO, list.toArray(new ArrayList<Task>()));
                break;
            case INVALID:
                throw new Exception("Invalid Command");
            default:
                throw new Error("Unknown Error");
            }
            Storage store=Storage.getInstance();
            store.writeToFile(TASK_LIST_FILE, list);
        } catch (Exception e) {
            handleError(e);
        } catch (Error e){
            handleError(e);
        }
    }
    
    /**
     * Method to handle Errors
     * 
     * @param err
     *            Error to be handled
     */
    private static void handleError(Error err) {
        ui.display(err.getMessage());
    }

    /**
     * Method to handle Exceptions
     * 
     * @param err
     *            Exception to be handled
     */
    private static void handleError(Exception err) {
        ui.display(err.getMessage());
    }
    
    
    /**
     * Method to get a static instance of Controller object
     * 
     * @return A static reference of a Controller object
     */
    public static Controller getInstance() {
        if (instance == null) {
            instance = new Controller();
        }
        return instance;
    }

    /**
     * Private default constructor
     * 
     */
    private Controller() {
        list = TaskList.getInstance();
        crudLogic = OPLogic.getInstance();
        findLogic = new SearchLogic();
        history = new PastHistory();
        undo = new Undo();
        Storage store= Storage.getInstance();
        list.addAll(store.readFromFile(TASK_LIST_FILE));
        ui = new UIFacade();
        
        crudLogic.addObserver(history);
        crudLogic.addObserver(undo);
        crudLogic.addObserver(ui);
    }

    /**
     * Method to start the UI
     * 
     * @param primaryStage
     *            Window of the application
     * @throws IOException
     *             Thrown if an IO error is encountered while rendering the UI
     */
    public void start(Stage primaryStage) throws IOException {
        // System.out.println(getTest());
        // taskList = Storage.readFromFile(file);
        // if (taskList == null) {
        // taskList = new ArrayList<Task>();
        // }
        ui.start(primaryStage);
        ui.display(list.toArray(new ArrayList<Task>()));
    }
}
