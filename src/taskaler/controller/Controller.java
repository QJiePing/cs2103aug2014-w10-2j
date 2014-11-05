/**
 * @author Brendan
 *
 */
package taskaler.controller;

import taskaler.configurations.Configuration;
import taskaler.logic.OPLogic;
import taskaler.logic.SearchLogic;
import taskaler.storage.Storage;
import taskaler.ui.UIFacade;
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

public class Controller{
    private static String TASK_LIST_FILE = null;

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
                if(taskID_DELETE.equalsIgnoreCase("all") && ui.showConfirmation()){
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
                break;
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
                String viewType = params[0];
                String viewParam = params[1];
                if (viewType.equals("CALENDAR") || viewType.equals("LIST")) {
                    ui.display(viewType, list.toArray(new ArrayList<Task>()));
                } else if(viewType.equals("TASK")){
                    result = findLogic.findByID(viewParam);
                    ui.display(result);
                } else if(viewType.equals("DATE")){
                    ArrayList<Task> viewResult = findLogic.find(viewType,
                            viewParam);
                    ui.display(String.format(VIEW_DATE_MSG, viewParam), viewResult);
                } else if(viewType.equals("UNDO")){
                    String viewUndo = undo.stackToDisplay();
                    ui.display("Actions last taken", viewUndo);
                }
                break;
            case FIND:
                String tagTypeFIND = params[0];
                String toSearch = params[1];
                ArrayList<Task> searchResult = findLogic.find(tagTypeFIND,
                        toSearch);
                ui.display(String.format(FIND_MSG, tagTypeFIND, toSearch), searchResult);
                break;
            case ARCHIVE:
                String date_ARCH = params[0];
                String out = PastHistory.retrieveHistory(date_ARCH);
                ui.display("History", out);
                break;
            case UNDO:
                result = undo.undo();
                ui.display("LIST", list.toArray(new ArrayList<Task>()));
                ui.display("Undone last operation");
                break;
            case GOTO:
                String date_GOTO = params[0];
                ui.displayMonth(date_GOTO, list.toArray(new ArrayList<Task>()));
                break;
            case TODAY:
                String date = params[0];
                ArrayList<Task> todayResult = findLogic.find("today", date);
                ui.display("Tasks to do today: ", todayResult);
                break;
            case EXIT:
                System.exit(0);
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
        TASK_LIST_FILE = Configuration.getInstance().getDefaultFileName();
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
        if(Configuration.getInstance().getDefaultView().equals("today")){
            ui.display(Configuration.getInstance().getDefaultView() ,list.toArray(new ArrayList<Task>()));
            executeCMD("today");
        } else {
            ui.display(Configuration.getInstance().getDefaultView() ,list.toArray(new ArrayList<Task>()));
        }
    }
}
