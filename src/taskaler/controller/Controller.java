/**
 * 
 */
package taskaler.controller;

import taskaler.ArchiveFunction;
import taskaler.OPLogic;
import taskaler.Storage;
import taskaler.common.data.Task;
import taskaler.common.enumerate.CMD_TYPE;
import taskaler.controller.parser;

import java.util.ArrayList;

/**
 * @author Brendan
 *
 */
public class Controller {

        /*********************************** Public Functions ***********************************/
        
        /**
         * 
         * Method to parse user commands, and pass parameters to UI, Storage and Logic
         *  
         * @param commandString
         */
        public static void executeCMD(String commandString) {
            try {
                Parser values = new Parser(commandString);
                CMD_TYPE commandType = values.getCommand();
                String[] params = values.getParameters();
                Task result = null;
                switch (commandType) {
                case ADD:
                    String name_ADD = params[0];
                    String description_ADD = params[1];
                    result = OPLogic.addTask(name_ADD, description_ADD);
                    Taskaler.ui.displayTask(result);
                    break;
                case DELETE:
                    String taskID_DELETE = params[0];
                    assert(taskID_DELETE != null);
                    result = OPLogic.deleteTask(taskID_DELETE);
                    String name_DELETED = result.getTaskName();
                    Taskaler.ui.displayList("The task \"" + name_DELETED + "\" has been deleted.", Taskaler.taskList);
                    break;
                case EDIT:
                    String taskID_EDIT = params[0];
                    String name_EDIT = params[1];
                    String description_EDIT = params[2];
                    assert(taskID_EDIT != null);
                    result = OPLogic.editTask(taskID_EDIT, name_EDIT, description_EDIT);
                    Taskaler.ui.displayTask(result);
                    break;
                case DATE:
                    String taskID_DATE = params[0];
                    String day = params[1];
                    String month = params[2];
                    String year = params[3];
                    assert(taskID_DATE != null);
                    result = OPLogic.editDate(taskID_DATE, day, month, year);
                    Taskaler.ui.displayTask(result);
                    break;
                case WORKLOAD:
                    String taskID_WORKLOAD = params[0];
                    String workloadAttribute = params[1];
                    assert(taskID_WORKLOAD != null);
                    result = OPLogic.editWorkload(taskID_WORKLOAD, workloadAttribute);
                    Taskaler.ui.displayTask(result);
                    break;
                case COMPLETION_TAG:
                    String taskID_CT = params[0];
                    assert(taskID_CT != null);
                    result = OPLogic.switchTag(taskID_CT);
                    Taskaler.ui.displayTask(result);
                    break;
                case VIEW:
                    if(params[0] != null && params[0].equals("TASK")){
                        result = OPLogic.findByID(params[1]);
                        Taskaler.ui.displayTask(result);
                    }
                    else {
                        Taskaler.ui.display(params[0]);
                    }
                    break;
                case FIND:
                    String tagTypeFIND = params[0];
                    String toSearch = params[1];
                    ArrayList<Task> searchResult = OPLogic.find(tagTypeFIND, toSearch);
                    Taskaler.ui.displayList("Search Result for " + toSearch, searchResult);
                    break;
                case ARCHIVE:
                    String date = params[0];
                    ArchiveFunction.archiveHistory(date);
                    break;
                case UNDO:
                    //OPLogic.undo();
                    break;
                case INVALID:
                    throw new Exception("Invalid Arguments");
                default:
                    throw new Error("Unknown Error");
                }
                Storage.writeToFile(Taskaler.file, Taskaler.taskList);
            } 
            catch (AssertionError e) {
                Error er = new Error("Task ID was invalid");
                handleError(er);
            } 
            catch (Exception e) {
                handleError(e);
            }
        }
}
