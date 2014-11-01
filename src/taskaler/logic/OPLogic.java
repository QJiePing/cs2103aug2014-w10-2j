package taskaler.logic;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Observable;

import taskaler.archive.OperationRecord;
import taskaler.common.data.DeadLineTask;
import taskaler.common.data.FloatTask;
import taskaler.common.data.RepeatedTask;
import taskaler.common.data.Task;
import taskaler.common.data.TaskList;
import taskaler.logic.common.RepeatPattern;

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
        
        if(!t.getTaskStatus()) {
        	TaskList.getInstance().incrementNumOfIncomplete();
        }

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
        
        if(!taskToBeRemoved.getTaskStatus()) {
        	TaskList.getInstance().decrementNumOfIncomplete();
        }
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
        
        Task oldTask = TaskList.getInstance().remove(taskIDIndex);
        TaskList.getInstance().add(t);
        
        
        if(t.getTaskStatus() != oldTask.getTaskStatus()) {
        	if(!oldTask.getTaskStatus()) {
            	TaskList.getInstance().decrementNumOfIncomplete();
            } else {
                TaskList.getInstance().incrementNumOfIncomplete();
            }
        }
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
    public Task addTask(String name_ADD, String description_ADD, String date_ADD, 
            String startTime, String endTime, String workload_ADD) {

        // assume task name cannot be null
        if (name_ADD == null) {
            // fail to add a new task
            return null;
        } else {

            if (description_ADD == null) {
                // change to default value
                description_ADD = common.TASK_PARAMETER_DEFAULT_VALUE;
            }
            
            if(workload_ADD == null){
                workload_ADD = common.TASK_PARAMETER_DEFAULT_VALUE;
            }
            
            Calendar start = null;
            Calendar end = null;
            if(startTime != null){
                start = setNewCalendarDate(startTime, "time");
            }
            if(endTime != null){
                end = setNewCalendarDate(endTime, "time");
            }
            
            // generate a new task ID
            int newTaskID = generateTaskID();

            Task newTask;
            if(date_ADD == null) {
            	//float task
	            newTask = new FloatTask(name_ADD, Integer.toString(newTaskID),
	                    common.TASK_INITIAL_STATUS, Calendar.getInstance(),
	                    workload_ADD, description_ADD, start, end);
            } else {
            	//deadline task
                Calendar date = setNewCalendarDate(date_ADD, "date");
				newTask = new DeadLineTask(name_ADD,
						Integer.toString(newTaskID),
						common.TASK_INITIAL_STATUS, Calendar.getInstance(), workload_ADD,
						description_ADD, date, start, end);
            }
            
            TaskList.getInstance().add(newTask);
            TaskList.getInstance().incrementNumOfIncomplete();
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
        int taskID = TaskList.getInstance().maxTaskID();

        return taskID + common.OFF_SET_BY_ONE;
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
        if(!taskToBeRemoved.getTaskStatus()) {
        	TaskList.getInstance().decrementNumOfIncomplete();
        }
        notifyObservers("DELETE", taskToBeRemoved);
        return taskToBeRemoved;

    }
    
    
    /**
     * 
     * Task deleteAll() will remove all the tasks in taskList:ArrayList<Task>.
     * 
     * @return return true all the tasks is successfully deleted
     * 
     */
    public boolean deleteAllTask() {
    	TaskList.getInstance().clear();
    	
    	//set number of incomplete tasks to zero
    	TaskList.getInstance().defaultNumOfIncomplete();
    	
    	return true;
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
     * Task editDate(String taskID, String date) is to
     * edit a existing task with same given ID in taskList:ArrayList<Task> by
     * given name and/or description
     * 
     * @param taskID
     * @param date
     * @return return null if given task ID not exist, edited task otherwise
     */
    public Task editDate(String taskID, String date) {
        Calendar newDeadLine = setNewCalendarDate(date, "date");
        int taskIDIndex = SearchLogic.findTaskByID(taskID);

        if (taskIDIndex == common.TAG_TASK_NOT_EXIST) {
            // fail to edit a task
            return null;
        }
        
        Task newTask = TaskList.getInstance().get(taskIDIndex);
        
        notifyObservers("DATE", newTask);
        
        if (taskIDIndex < TaskList.getInstance().floatToArray().size()) {
        	//float task "zone"
        	//float task has no date attribute
        	//remove task from float task list and add to deadline task list
        	Task deletedTask = TaskList.getInstance().remove(taskIDIndex);
        	newTask = new DeadLineTask(deletedTask.getTaskName(),
					deletedTask.getTaskID(), common.TASK_INITIAL_STATUS,
					deletedTask.getTaskCreationDate(), deletedTask.getTaskWorkLoad(),
					deletedTask.getTaskDescription(), newDeadLine, null, null);
        	TaskList.getInstance().add(newTask);
        	
        } else if (taskIDIndex < TaskList.getInstance().floatToArray().size() + TaskList.getInstance().deadlineToArray().size()) {
        	//deadline task "zone"
        	((DeadLineTask) newTask).setDeadline(newDeadLine);
       
        } else {
        	//repeated task "zone"
        	((RepeatedTask) newTask).setEndRepeatedDate(newDeadLine);
        }

        return newTask;
    }
    
    /**
     * 
     * setNewCalenderDate(String date) is to create a new
     * calendar date will given day, month and year
     * 
     * @param day
     * @param month
     * @param year
     * @return return new calendar date
     */
    private static Calendar setNewCalendarDate(String date, String type) {
        Calendar newDeadLine = Calendar.getInstance();
        try{
            if(type.compareToIgnoreCase("date")== 0) {
                newDeadLine.setTime(common.DEFAULT_DATE_FORMAT.parse(date));
            }
            else if(type.compareToIgnoreCase("time") == 0){
                newDeadLine.setTime(common.DEFAULT_TIME_FORMAT.parse(date));
            }
        }
        catch(Exception e){
            ;
        }
        return newDeadLine;
    }
    
    /**
     * 
     * Task editTime(String taskID, String startTime, String endTime) is to
     * edit a existing task with same given ID in taskList:ArrayList<Task> by
     * given start time and end time
     * 
     * @param taskID
     * @param startTime
     * @param endTime
     * @return return null if given task ID not exist, edited task otherwise
     */
    
    public Task editTime(String taskID, String startTime, String endTime){
    	int taskIDIndex = SearchLogic.findTaskByID(taskID);
        
        if (taskIDIndex == common.TAG_TASK_NOT_EXIST) {
            // fail to edit a task
            return null;
        }
        
        notifyObservers("TIME", TaskList.getInstance().get(taskIDIndex));
        
        
        /*The code below can be simplified to:
        if(startTime != null){
            Calendar start = setNewCalendarDate(startTime, "time");
            TaskList.getInstance().get(taskIDIndex).changeStartTime(start);
        if(endTime != null){
            Calendar end = setNewCalendarDate(endTime, "time");
            TaskList.getInstance().get(taskIDIndex).changeStartTime(end);
        }
        return TaskList.getInstance().get(taskIDIndex);
        */

        int startTimeHour, startTimeMins, endTimeHour, endTimeMins;
        
        if(startTime != null) {
        	startTimeHour = Integer.parseInt(startTime.substring(0, 2));
        	startTimeMins = Integer.parseInt(startTime.substring(2, 4));
        	
            Calendar newStartTime = TaskList.getInstance().get(taskIDIndex).getStartTime();
        	newStartTime.set(Calendar.HOUR_OF_DAY, startTimeHour);
        	newStartTime.set(Calendar.MINUTE, startTimeMins);
        	
        	TaskList.getInstance().get(taskIDIndex).changeStartTime(newStartTime);
        }
        
        if(endTime != null) {
        	endTimeHour = Integer.parseInt(endTime.substring(0, 2));
        	endTimeMins = Integer.parseInt(endTime.substring(2, 4));

            Calendar newEndTime = TaskList.getInstance().get(taskIDIndex).getEndTime();
        	newEndTime.set(Calendar.HOUR_OF_DAY, endTimeHour);
        	newEndTime.set(Calendar.MINUTE, endTimeMins);
        	TaskList.getInstance().get(taskIDIndex).changeEndTime(newEndTime);
        }
        
        return TaskList.getInstance().get(taskIDIndex);
        
    }
    
    
    
    /**
     * 
     * Task setRepeat(String taskID, String pattern, String startDate, String endDate)
     * will change any existed task to RepeatTask object with given pattern, start date
     * and end repeated date
     * 
     * @param taskID
     * @param pattern
     * @param startDate
     * @param endDate
     * @return return null if given task ID not exist, edited task otherwise
     */
    public Task setRepeat(String taskID, String pattern, String startDate, String endDate){
        Calendar startTime = setNewCalendarDate(startDate, "date");
        Calendar endTime = setNewCalendarDate(startDate, "date");
        Calendar endRepeatedTime = setNewCalendarDate(endDate, "date");
        if(endDate == null) {
        	//default end repeted time is 1 month.
        	endRepeatedTime.add(Calendar.MONTH, common.OFF_SET_BY_ONE);
        }
        RepeatedDate repeatedDate = new RepeatedDate();
        
        int taskIDIndex = SearchLogic.findTaskByID(taskID);
        
        if (taskIDIndex == common.TAG_TASK_NOT_EXIST || repeatedDate.getPattern(pattern) == RepeatPattern.NONE) {
            // fail to edit a task
            return null;
        }
        
        notifyObservers("REPEAT", TaskList.getInstance().get(taskIDIndex));
        
        ArrayList<Calendar> dates = repeatedDate.getRepeatDay(startTime, endRepeatedTime, pattern);
        
        //remove task from float task list or deadline task list
        Task deletedTask = TaskList.getInstance().remove(taskIDIndex);
        
        int collectionID = generateCollectionID();
        
		Task newTask = new RepeatedTask(deletedTask.getTaskName(), deletedTask.getTaskID(),
				deletedTask.getTaskStatus(), deletedTask.getTaskCreationDate(), 
				deletedTask.getTaskWorkLoad(), deletedTask.getTaskDescription(),
				startTime, endTime, pattern, dates, endRepeatedTime, 
				collectionID);
		
		TaskList.getInstance().add(newTask);
        return newTask;
    }
    
    /**
     * 
     * generateCollectionID() will generate a unique collection ID for
     * new RepeatedTask object
     * 
     * @return return new collection ID
     */
	private int generateCollectionID() {
		
		int sizeOfRepeatedTask = TaskList.getInstance().repeatedToArray().size();
        int collectionID = common.DEFAULT_COLLECTION_ID;
        if(sizeOfRepeatedTask != 0) {
        	collectionID = TaskList.getInstance().repeatedToArray().get(sizeOfRepeatedTask-1).getCollectiveID()+1;
        }
        
		return collectionID;
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

        notifyObservers("WORKLOAD", TaskList.getInstance().get(taskIDIndex));

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

        Task oldTask = TaskList.getInstance().get(taskIDIndex).clone();
        
        toggleStatus(taskIDIndex);
        notifyObservers("COMPLETE", oldTask);
        
        return TaskList.getInstance().get(taskIDIndex);
    }

    /**
     * toggleStatus(int taskIDIndex) will toggle the task status
     * 
     * @param taskIDIndex
     */
	private void toggleStatus(int taskIDIndex) {
		
		if(TaskList.getInstance().get(taskIDIndex).getTaskStatus()) {
        	TaskList.getInstance().get(taskIDIndex).changeTaskStatus(common.TASK_INITIAL_STATUS);
        	//increment number of incomplete tasks
        	TaskList.getInstance().incrementNumOfIncomplete();
        } else {
        	TaskList.getInstance().get(taskIDIndex).changeTaskStatus(common.TASK_COMPLETED_STATUS);
        	//decrement number of incomplete tasks
        	TaskList.getInstance().decrementNumOfIncomplete();
        }
		
	}

	/**
	 * notifyObservers(String type, Task task) is to notify all the Observers for
	 * the changes made
	 * 
	 * @param type
	 * @param task
	 */
    private void notifyObservers(String type, Task task) {
        setChanged();
        OperationRecord<Task, String> record = new OperationRecord<Task, String>(
                task.clone(), type);
        notifyObservers(record);
    }

}
