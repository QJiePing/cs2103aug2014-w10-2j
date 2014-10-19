package taskaler.logic;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Observable;

import taskaler.archive.OperationRecord;
import taskaler.common.data.Task;
import taskaler.common.data.TaskList;

/**
 * @author Weng Yuan
 *
 */

public class OPLogic extends Observable {

    private static OPLogic instance = null;

    /**
     * Inaccessible default constructor
     */
    private OPLogic() {

    }

    /**
     * Method to get an exist instance of this object
     * 
     * @return An instance of this object
     */
    public static OPLogic getInstance() {
        if (instance == null) {
            instance = new OPLogic();
        }

        return instance;
    }
    
    /**
     * Special method for undo feature
     * 
     * @param t Task to be re-added into the list
     * @return Task that has been re-added
     */
    public Task addTask(Task t){
        TaskList.getInstance().add(t);
        notifyObservers("UNDO", t);
        return t;
    }
    
    /**
     * Special method for undo feature
     * 
     * @param t Task to be deleted from the list
     * @return Task that has been deleted
     */
    public Task deleteTask(Task t){
        int taskIDIndex = SearchLogic.findTaskByID(t.getTaskID());

        if (taskIDIndex == common.TAG_TASK_NOT_EXIST) {
            // fail to delete a task
            return null;
        }

        Task taskToBeRemoved = TaskList.getInstance().remove(taskIDIndex);
        notifyObservers("UNDO", taskToBeRemoved);
        return t;
    }
    
    /**
     * Special method for undo feature
     * 
     * @param t Task to be overridden in the list
     * @return Task that has been overridden
     */
    public Task editTask(Task t){
        int taskIDIndex = SearchLogic.findTaskByID(t.getTaskID());

        if (taskIDIndex == common.TAG_TASK_NOT_EXIST) {
            // fail to delete a task
            return null;
        }

        Task oldTask = TaskList.getInstance().set(taskIDIndex, t);
        notifyObservers("UNDO", oldTask);
        return oldTask;
    }

    /**
     * addTask(String name_ADD, String description_ADD) will add a new task with
     * specified task name and description into taskList:ArrayList<Task>. New
     * added task will have a unique task ID, and task status will be set to Not
     * Done
     * 
     * @param name_ADD
     * @param description_ADD
     * @return return null if task name is null, return the newly added task
     *         otherwise.
     * 
     */
    public Task addTask(String name_ADD, String description_ADD) {

        // assume task name cannot be null
        if (name_ADD == null) {
            // fail to add a new task
            return null;
        } else {

            if (description_ADD == null) {
                // change to default value
                description_ADD = common.TASK_PARAMETER_DEFAULT_VALUE;
            }

            // generate a new task ID
            int newTaskID = generateTaskID();

            Task newTask = new Task(name_ADD, Integer.toString(newTaskID),
                    common.TASK_INITIAL_STATUS, Calendar.getInstance(),
                    common.TASK_PARAMETER_DEFAULT_VALUE, description_ADD);
            TaskList.getInstance().add(newTask);

            notifyObservers("ADD", newTask);

            return newTask;
        }

    }

    /**
     * 
     * generateTaskID() will generate a unique new task ID for
     * taskList:ArrayList<Task>
     * 
     * @return return new taskID
     */
    private int generateTaskID() {
        int taskID = common.DEFAULT_TASK_ID;

        if (TaskList.getInstance().isEmpty()) {
            taskID = 1;
        } else {
            int numOfTask = TaskList.getInstance().size();
            String lastTaskID = TaskList.getInstance().get(numOfTask - 1)
                    .getTaskID();
            taskID = Integer.parseInt(lastTaskID) + 1;
        }

        return taskID;
    }

    /**
     * 
     * Task deleteTask(String taskID_DELETE) is to delete a specified task with
     * given ID in taskList:ArrayList<Task>.
     * 
     * @param taskID_DELETE
     * @return return null if task ID not exist, otherwise, return the deleted
     *         task
     * 
     */
    public Task deleteTask(String taskID_DELETE) {

        int taskIDIndex = SearchLogic.findTaskByID(taskID_DELETE);

        if (taskIDIndex == common.TAG_TASK_NOT_EXIST) {
            // fail to delete a task
            return null;
        }

        Task taskToBeRemoved = TaskList.getInstance().remove(taskIDIndex);
        notifyObservers("DELETE", taskToBeRemoved);

        return taskToBeRemoved;

    }

    /**
     * 
     * editTask(String taskID_EDIT, String name_EDIT, String description_EDIT)
     * is to edit a existing task with given ID in taskList:ArrayList<Task> by
     * given name and/or description
     * 
     * @param taskID_EDIT
     * @param name_EDIT
     * @param description_EDIT
     * @return return null if task ID not exist or both name and description
     *         given are null, edited task otherwise
     * 
     */
    public Task editTask(String taskID_EDIT, String name_EDIT,
            String description_EDIT) {

        int taskIDIndex = SearchLogic.findTaskByID(taskID_EDIT);

        if (isError(name_EDIT, description_EDIT, taskIDIndex)) {
            return null;
        }

        notifyObservers("EDIT", TaskList.getInstance().get(taskIDIndex));

        // assume name will not change to null
        if (name_EDIT != null) {
            TaskList.getInstance().get(taskIDIndex).changeTaskName(name_EDIT);
        }

        if (description_EDIT != null) {
            TaskList.getInstance().get(taskIDIndex)
                    .changeTaskDescription(description_EDIT);
        }

        return TaskList.getInstance().get(taskIDIndex);

    }

    /**
     * 
     * isError(String name_EDIT, String description_EDIT, int taskIDIndex) is to
     * for error checking. It will check validity of all the parameters given
     * 
     * @param name_EDIT
     * @param description_EDIT
     * @param taskIDIndex
     * @return return true if any of parameters cannot be use for editing, false
     *         otherwise
     * 
     */
    private static boolean isError(String name_EDIT, String description_EDIT,
            int taskIDIndex) {

        boolean errorFinder = false;

        if (taskIDIndex == common.TAG_TASK_NOT_EXIST) {
            errorFinder = true; // fail to edit a task
        } else if (name_EDIT == null && description_EDIT == null) {
            errorFinder = true; // fail to edit a task
        }

        return errorFinder;
    }

    /**
     * 
     * Task editDate(String taskID, String day, String month, String year) is to
     * edit a existing task with same given ID in taskList:ArrayList<Task> by
     * given name and/or description
     * 
     * @param taskID
     * @param day
     * @param month
     * @param year
     * @return return null if given task ID not exist, edited task otherwise
     */
    public Task editDate(String taskID, String day, String month, String year) {
        Calendar newDeadLine = setNewCalenderDate(Integer.parseInt(day),
                Integer.parseInt(month), Integer.parseInt(year));

        int taskIDIndex = SearchLogic.findTaskByID(taskID);

        if (taskIDIndex == common.TAG_TASK_NOT_EXIST) {
            // fail to edit a task
            return null;
        }

        notifyObservers("EDIT", TaskList.getInstance().get(taskIDIndex));

        TaskList.getInstance().get(taskIDIndex).changeDeadLine(newDeadLine);

        return TaskList.getInstance().get(taskIDIndex);
    }

    /**
     * 
     * setNewCalenderDate(int day, int month, int year) is to create a new
     * calendar date will given day, month and year
     * 
     * @param day
     * @param month
     * @param year
     * @return return new calendar date
     */
    private static Calendar setNewCalenderDate(int day, int month, int year) {
        Calendar newDeadLine = Calendar.getInstance();
        newDeadLine.set(Calendar.YEAR, year);
        newDeadLine.set(Calendar.MONTH, month - common.OFF_SET_BY_ONE);
        newDeadLine.set(Calendar.DAY_OF_MONTH, day);
        return newDeadLine;
    }

    /**
     * 
     * Task editWorkload(String taskID, String workloadAttribute) is to change
     * the workload attribute of a task with same given task ID in
     * taskList:ArrayList<Task> to new workload attribute
     * 
     * @param taskID
     * @param workloadAttribute
     * @return return null if given task ID not exist, edited task otherwise
     */
    public Task editWorkload(String taskID, String workloadAttribute) {
        int workloadAtt = Integer.parseInt(workloadAttribute);
        int taskIDIndex = SearchLogic.findTaskByID(taskID);

        if (taskIDIndex == common.TAG_TASK_NOT_EXIST) {
            // fail to edit a task
            return null;
        }

        notifyObservers("EDIT", TaskList.getInstance().get(taskIDIndex));

        // assume workloadAtt is within the range of 1-3
        TaskList.getInstance().get(taskIDIndex)
                .changeTaskWorkLoad(Integer.toString(workloadAtt));

        return TaskList.getInstance().get(taskIDIndex);
    }

    /**
     * 
     * switchTag(String taskID) will change the status of a task with given ID
     * in taskList:ArrayList<Task> to "Done"
     * 
     * @param taskID
     * @return return null if given task ID not exist, edited task otherwise
     */
    public Task switchTag(String taskID) {
        int taskIDIndex = SearchLogic.findTaskByID(taskID);

        if (taskIDIndex == common.TAG_TASK_NOT_EXIST) {
            // fail to edit a task
            return null;
        }

        notifyObservers("EDIT", TaskList.getInstance().get(taskIDIndex));

        TaskList.getInstance().get(taskIDIndex).changeTaskStatus("Done");
        return TaskList.getInstance().get(taskIDIndex);
    }

    private void notifyObservers(String type, Task task) {
        setChanged();
        OperationRecord<Task, String> record = new OperationRecord<Task, String>(
                task.clone(), type);
        notifyObservers(record);
    }

}
