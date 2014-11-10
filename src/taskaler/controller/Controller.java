//@author A0108541M

package taskaler.controller;

import taskaler.logic.OPLogic;
import taskaler.logic.SearchLogic;
import taskaler.storage.TaskAndConfigStorage;
import taskaler.ui.UIFacade;
import taskaler.archive.PastHistory;
import taskaler.archive.Undo;
import taskaler.common.configurations.Configuration;
import taskaler.common.data.Task;
import taskaler.common.data.TaskList;
import taskaler.common.util.CommonLogger;
import static taskaler.controller.common.*;
import taskaler.controller.parser.Parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;

import javafx.stage.Stage;

/**
 * Instantiates all other components, controls the workflow of Taskaler
 */
public class Controller {
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
     * Method to parse user commands, and pass parameters to UI, Storage and
     * Logic
     * 
     * @param commandString
     *            User input string
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
                String taskIDDELETE = params[0];
                if (taskIDDELETE.equalsIgnoreCase("all")
                        && ui.showConfirmation()) {
                    crudLogic.deleteAllTask();
                    ui.display(MSG_DELETE_ALL,
                            list.toArray(new ArrayList<Task>()));
                } else {
                    result = crudLogic.deleteTask(taskIDDELETE);
                    String nameDELETED = result.getTaskName();
                    ui.display(String.format(MSG_DELETED, nameDELETED),
                            list.toArray(new ArrayList<Task>()));
                }
                break;
            case EDIT:
                String taskIDEDIT = params[0];
                String nameEDIT = params[1];
                String descriptionEDIT = params[2];
                assert (taskIDEDIT != null);
                result = crudLogic.editTask(taskIDEDIT, nameEDIT,
                        descriptionEDIT);
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
                String taskIDREPEAT = params[0];
                String pattern = params[1];
                String startDate = params[2];
                String endDate = params[3];
                result = crudLogic.setRepeat(taskIDREPEAT, pattern, startDate,
                        endDate);
                ui.display(result);
                break;
            case WORKLOAD:
                String taskIDWORKLOAD = params[0];
                String workloadAttribute = params[1];
                assert (taskIDWORKLOAD != null);
                result = crudLogic.editWorkload(taskIDWORKLOAD,
                        workloadAttribute);
                ui.display(result);
                break;
            case COMPLETION_TAG:
                String taskIDCT = params[0];
                result = crudLogic.switchTag(taskIDCT);
                ui.display(result);
                break;
            case VIEW:
                String viewType = params[0];
                String viewParam = params[1];
                if (viewType.equals("CALENDAR") || viewType.equals("LIST")) {
                    ui.display(viewType, list.toArray(new ArrayList<Task>()));
                } else if (viewType.equals("TASK")) {
                    result = findLogic.findByID(viewParam);
                    ui.display(result);
                } else if (viewType.equals("DATE")) {
                    ArrayList<Task> viewResult = findLogic.find(viewType,
                            viewParam);
                    ui.display(String.format(MSG_VIEW_DATE, viewParam),
                            viewResult);
                } else if (viewType.equals("UNDO")) {
                    String viewUndo = undo.stackToDisplay();
                    ui.display(MSG_VIEW_UNDO, viewUndo);
                }
                break;
            case FIND:
                String tagTypeFIND = params[0];
                String toSearch = params[1];
                ArrayList<Task> searchResult = findLogic.find(tagTypeFIND,
                        toSearch);
                ui.display(String.format(MSG_FIND, tagTypeFIND.toLowerCase(),
                        toSearch), searchResult);
                break;
            case ARCHIVE:
                String dateARCH = params[0];
                String out = PastHistory.retrieveHistory(dateARCH);
                ui.display(MSG_HISTORY, out);
                break;
            case UNDO:
                result = undo.undo();
                ui.display("LIST", list.toArray(new ArrayList<Task>()));
                ui.display(MSG_UNDO);
                break;
            case GOTO:
                String dateGOTO = params[0];
                ui.displayMonth(dateGOTO, list.toArray(new ArrayList<Task>()));
                break;
            case TODAY:
                String date = params[0];
                ArrayList<Task> todayResult = findLogic.find("today", date);
                ArrayList<Task> overdueResult = findLogic.find("overdue", date);
                ArrayList<String> headers = new ArrayList<String>();
                headers.add(MSG_TODAY);
                headers.add(MSG_OVERDUE);
                ArrayList<ArrayList<Task>> lists = new ArrayList<ArrayList<Task>>();
                lists.add(todayResult);
                lists.add(overdueResult);
                ui.display(Configuration.getInstance().getWelcomeMsg(), headers, lists);
                break;
            case EXIT:
                System.exit(0);
            case INVALID:
                throw new Exception(EXCEPTION_INVALID_COMMAND);
            default:
                Error e = new Error(ERROR_UNEXPECTED);
                CommonLogger.getInstance().exceptionLogger(e, Level.SEVERE);
                throw e;
            }
            TaskAndConfigStorage store = TaskAndConfigStorage.getInstance();
            store.writeToFile(TASK_LIST_FILE, list);
        } catch (Exception e) {
            handleError(e);
        } catch (Error e) {
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
        TaskAndConfigStorage store = TaskAndConfigStorage.getInstance();
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
        ui.start(primaryStage);
        if (Configuration.getInstance().getDefaultView().equals("today")) {
            ui.display(Configuration.getInstance().getDefaultView(),
                    list.toArray(new ArrayList<Task>()));
            executeCMD("today");
        } else {
            ui.display(Configuration.getInstance().getDefaultView(),
                    list.toArray(new ArrayList<Task>()));
        }
    }
}
