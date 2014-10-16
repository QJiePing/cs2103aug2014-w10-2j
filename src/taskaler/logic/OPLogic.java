package taskaler.logic;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import taskaler.common.data.Task;


/**
 * @author Weng Yuan
 *
 */

public class OPLogic {

	public static String ADD_NO_CONTENT = "No content is added";
	public static String EDIT_NO_CONTENT = "NO content, cannot be edited";
	public static String TASKID_NOT_EXIST = "Task ID no exist";
	public static String TASK_INITIAL_STATUS = "Not Done";
	public static String TASK_PARAMETER_DEFAULT_VALUE = "";
	
    public static String FORMAT_DAY_MONTH_YEAR = "dd/MM/yyyy";
	
	public static int TAG_TYPE_DATE = 0;
	public static int TAG_TYPE_MONTH = 1;
	public static int TAG_TYPE_YEAR = 2;
	public static int TAG_TASK_NOT_EXIST = -1;
	
	public static int OFF_SET_BY_ONE = 1;
	
	public static int DEFAULT_TASK_ID = 0;
	
	
	/**
	 * addTask(String name_ADD, String description_ADD)	will add a new task with specified task name and description
	 * 													into taskList:ArrayList<Task>. New added task will have a
	 * 													unique task ID, and task status will be set to Not Done
	 * 
	 * @param name_ADD
	 * @param description_ADD
	 * @return return null if task name is null, return the newly added task otherwise.
	 * 
	 */
	public static Task addTask(String name_ADD, String description_ADD) {
		
		//assume task name cannot be null
		if(name_ADD == null) {
			//fail to add a new task
			return null;
		} else {
			
			if(description_ADD == null) {
				//change to default value
				description_ADD = TASK_PARAMETER_DEFAULT_VALUE;
			}
			
			//generate a new task ID
			int newTaskID = generateTaskID();
			
			Task newTask = new Task(name_ADD, Integer.toString(newTaskID), TASK_INITIAL_STATUS,
									Calendar.getInstance(), TASK_PARAMETER_DEFAULT_VALUE, description_ADD);
			Taskaler.taskList.add(newTask);
			
			//operationSaveForUndo("delete", newTask);
			
			return newTask;
		}
		
	}

	/**
	 * 
	 * generateTaskID() will generate a unique new task ID for taskList:ArrayList<Task>
	 * 
	 * @return return new taskID
	 */
	private static int generateTaskID() {
		int taskID = DEFAULT_TASK_ID;
		
		if(Taskaler.taskList.isEmpty()) {
			taskID = 1;
		} else {
			int numOfTask = Taskaler.taskList.size();
			String lastTaskID = Taskaler.taskList.get(numOfTask-1).getTaskID();
			taskID = Integer.parseInt(lastTaskID) + 1;
		}
		
		return taskID;
	}
	
	/**
	 * 
	 * Task deleteTask(String taskID_DELETE) is to delete a specified task with given ID in taskList:ArrayList<Task>.
	 * 
	 * @param taskID_DELETE
	 * @return return null if task ID not exist, otherwise, return the deleted task
	 * 
	 */
	public static Task deleteTask(String taskID_DELETE) {
		
		int taskIDIndex = findTaskByID(taskID_DELETE);
		
		if(taskIDIndex == TAG_TASK_NOT_EXIST) {
			//fail to delete a task
			return null;
		}
		Task taskToBeRemoved = Taskaler.taskList.remove(taskIDIndex);
		
		
		//operationSaveForUndo("add", taskToBeRemoved);
		
		return taskToBeRemoved;
	
	}

	/**
	 * 
	 * editTask(String taskID_EDIT, String name_EDIT, String description_EDIT)	is to edit a existing task with given
	 * 																			ID in taskList:ArrayList<Task> by given
	 * 																			name and/or description
	 * 
	 * @param taskID_EDIT
	 * @param name_EDIT
	 * @param description_EDIT
	 * @return return null if task ID not exist or both name and description given are null, edited task otherwise
	 * 
	 */
	public static Task editTask(String taskID_EDIT, String name_EDIT,
			String description_EDIT) {
		
		int taskIDIndex = findTaskByID(taskID_EDIT);
		
		if(isError(name_EDIT, description_EDIT, taskIDIndex)) {
			return null;
		}
		
		//operationSaveForUndo("edit", Taskaler.taskList.get(taskIDIndex));
		
		//assume name will not change to null
		if(name_EDIT != null) {
			Taskaler.taskList.get(taskIDIndex).changeTaskName(name_EDIT);
		}
		
		if(description_EDIT != null) {
			Taskaler.taskList.get(taskIDIndex).changeTaskDescription(description_EDIT);
		}
		
		return Taskaler.taskList.get(taskIDIndex);
		
	}

	
	/**
	 * 
	 * isError(String name_EDIT, String description_EDIT, int taskIDIndex) 	is to for error checking. It will check validity
	 * 																		of all the parameters given
	 * 
	 * @param name_EDIT
	 * @param description_EDIT
	 * @param taskIDIndex
	 * @return return true if any of parameters cannot be use for editing, false otherwise
	 * 
	 */
	private static boolean isError(String name_EDIT, String description_EDIT,
			int taskIDIndex) {
		
		boolean errorFinder = false;
		
		if(taskIDIndex == TAG_TASK_NOT_EXIST) {
			errorFinder = true;       //fail to edit a task
		} else if (name_EDIT == null && description_EDIT == null) {
			errorFinder =  true; //fail to edit a task
		}
		
		return errorFinder;
	}
	
	/**
	 * 
	 * Task editDate(String taskID, String day, String month, String year) is to edit a existing task with same given ID
	 * 															           in taskList:ArrayList<Task> by given
	 * 															           name and/or description
	 * 
	 * @param taskID
	 * @param day
	 * @param month
	 * @param year
	 * @return return null if given task ID not exist, edited task otherwise
	 */
	public static Task editDate(String taskID, String day, String month, String year) {
		Calendar newDeadLine = setNewCalenderDate(Integer.parseInt(day), Integer.parseInt(month), Integer.parseInt(year));

		int taskIDIndex = findTaskByID(taskID);
		
		if(taskIDIndex == TAG_TASK_NOT_EXIST) {
			//fail to edit a task
			return null;
		}
		
		//operationSaveForUndo("edit", Taskaler.taskList.get(taskIDIndex));
		
		Taskaler.taskList.get(taskIDIndex).changeDeadLine(newDeadLine);
		
		return Taskaler.taskList.get(taskIDIndex);
	}

	/**
	 * 
	 * setNewCalenderDate(int day, int month, int year) is to create a new calendar date will given day, month and year
	 * 
	 * @param day
	 * @param month
	 * @param year
	 * @return return new calendar date
	 */
	private static Calendar setNewCalenderDate(int day, int month, int year) {
		Calendar newDeadLine = Calendar.getInstance();
		newDeadLine.set(Calendar.YEAR, year);
		newDeadLine.set(Calendar.MONTH, month-OFF_SET_BY_ONE);
		newDeadLine.set(Calendar.DAY_OF_MONTH, day);
		return newDeadLine;
	}
	
	
	/**
	 * 
	 * Task editWorkload(String taskID, String workloadAttribute) 	is to change the workload attribute of a task with same
	 * 																given task ID in taskList:ArrayList<Task> to new workload
	 * 																attribute
	 * 
	 * @param taskID
	 * @param workloadAttribute
	 * @return return null if given task ID not exist, edited task otherwise
	 */
	public static Task editWorkload(String taskID, String workloadAttribute) {
		int workloadAtt = Integer.parseInt(workloadAttribute);
		int taskIDIndex = findTaskByID(taskID);
		
		if(taskIDIndex == TAG_TASK_NOT_EXIST) {
			//fail to edit a task
			return null;
		}
		
		//operationSaveForUndo("edit", Taskaler.taskList.get(taskIDIndex));
		
		//assume workloadAtt is within the range of 1-3
		Taskaler.taskList.get(taskIDIndex).changeTaskWorkLoad(Integer.toString(workloadAtt));
		
		return Taskaler.taskList.get(taskIDIndex);
	}
	
	
	/**
	 * 
	 * switchTag(String taskID) will change the status of a task with given ID in taskList:ArrayList<Task> to "Done"
	 * 
	 * @param taskID
	 * @return return null if given task ID not exist, edited task otherwise
	 */
	public static Task switchTag(String taskID) {
		int taskIDIndex = findTaskByID(taskID);
		
		if(taskIDIndex == TAG_TASK_NOT_EXIST) {
			//fail to edit a task
			return null;
		}
		
		
		//operationSaveForUndo("edit", Taskaler.taskList.get(taskIDIndex));
		
		Taskaler.taskList.get(taskIDIndex).changeTaskStatus("Done");
		return Taskaler.taskList.get(taskIDIndex);
	}
	
	/*
	private static void operationSaveForUndo(String type, Task newTask) {
		UndoFunction.saveOperation(type, newTask);
	}
	*/
	
	
	/**
	 * find(String tagTypeFIND, String paramFIND)	is to find all the tasks in taskList:ArrayList<Task> with same
	 * 												information(type and parameter) given
	 * @param tagTypeFIND
	 * @param paramFIND
	 * @return return a list of tasks
	 */
	public static ArrayList<Task> find(String tagTypeFIND, String paramFIND){
		
		switch(tagTypeFIND.toUpperCase()){
		case "KEYWORD":
			return findByKeyword(paramFIND);
		case "DATE":
			return findByDeadLine(paramFIND);
		case "WORKLOAD":
			return findByWorkload(paramFIND);
		}
		return null;
	}
	
	
	/**
	 * 
	 * findByWorkload(String paramFIND)	is to find all the tasks in taskList:ArrayList<Task> with same workload given
	 * 									attribute
	 * 
	 * @param paramFIND
	 * @return return list of Tasks (can be empty if nothing is found)
	 */
	private static ArrayList<Task> findByWorkload(String paramFIND) {
		ArrayList<Task> searchResultList = new ArrayList<Task>();
		
		for(int i = 0; i < Taskaler.taskList.size(); i++){
			if(Taskaler.taskList.get(i).getTaskWorkLoad() != null) {
				if(Taskaler.taskList.get(i).getTaskWorkLoad().equals(paramFIND)) {
					searchResultList.add(Taskaler.taskList.get(i));
				}
			}
		}
		
		return searchResultList;
	}

	/**
	 * 
	 * findByDeadLine(String paramFIND)	is to find all the tasks in taskList:ArrayList<Task> with same date given
	 * 
	 * @param paramFIND
	 * @return return list of Tasks (can be empty if nothing is found)
	 */
	public static ArrayList<Task> findByDeadLine(String paramFIND) {

		ArrayList<Task> searchResultList = new ArrayList<Task>();
		
		for(int i = 0; i < Taskaler.taskList.size(); i++){
			if(Taskaler.taskList.get(i).getTaskDeadLine() != null) {
				String deadLine = getDeadline(i);
				if(deadLine.equals(paramFIND)) {
					searchResultList.add(Taskaler.taskList.get(i));
				}
			}
		}
		
		return searchResultList;
	}

	/**
	 * 
	 * getDeadline(int indexOfTask) will convert a deadline with data type of calendar into string
	 * 
	 * @param indexOfTask
	 * @return return a deadline with string data type
	 */
	private static String getDeadline(int indexOfTask) {
	
		Calendar taskDeadLine = Taskaler.taskList.get(indexOfTask).getTaskDeadLine();
		SimpleDateFormat formatter = new SimpleDateFormat(FORMAT_DAY_MONTH_YEAR);
		String deadLine = formatter.format(taskDeadLine.getTime());
		System.out.println(deadLine);
		return deadLine;
	}

	/**
	 * 
	 * findByKeyword(String paramFIND)	is to find all the tasks in taskList:ArrayList<Task> with same keyword given
	 * 
	 * @param paramFIND
	 * @return return list of Tasks (can be empty if nothing is found)
	 */
	private static ArrayList<Task> findByKeyword(String paramFIND) {
		ArrayList<Task> searchResultList = new ArrayList<Task>();
		
		for(int i = 0; i < Taskaler.taskList.size(); i++){
			//assume task name will never be null
			if(Taskaler.taskList.get(i).getTaskName().contains(paramFIND)) {
				searchResultList.add(Taskaler.taskList.get(i));
			}
		}
		
		return searchResultList;
	}

	/**
	 * 
	 * findByMonthAndYear(String MonthFIND, String YearFind)	is to find all the tasks in taskList:ArrayList<Task>
	 * 															with same month and year given
	 * @param MonthFIND
	 * @param YearFind
	 * @return return list of Tasks (can be empty if nothing is found)
	 */
	public static ArrayList<Task> findByMonthAndYear(String monthFind, String yearFind) {

		ArrayList<Task> searchResultList = new ArrayList<Task>();
		
		for(int i = 0; i < Taskaler.taskList.size(); i++){
			if(Taskaler.taskList.get(i).getTaskDeadLine() != null) {
				String deadLine = getDeadline(i);
				String[] date = deadLine.split("/");
				if(date[1].equals(monthFind) && date[2].equals(yearFind)) {
					searchResultList.add(Taskaler.taskList.get(i));
				}
			}
		}
		
		return searchResultList;
	}
	
	/**
	 * 
	 * findById(string taskID) function is to find a specify task with same taksID given
	 * 
	 * @param taskID
	 * @return	return a task with Task data type
	 */
	public static Task findByID(String taskID) {
		int taskIDIndex = findTaskIndex(taskID);
		
		if(taskIDIndex == TAG_TASK_NOT_EXIST) {
			return null;
		} else {
			return Taskaler.taskList.get(taskIDIndex);
		}
	}
	
	
	/**
	 * 
	 * findTaskByID(String taskID) function is to find a specify task with same taksID given
	 * 
	 * @param taskID
	 * @return return task ID
	 */
	private static int findTaskByID(String taskID) {
		int taskIDIndex = findTaskIndex(taskID);
		
		return taskIDIndex;
	}

	/**
	 * 
	 * findTaskIndex(String taskID) is to find the task index in taskList:ArrayList<Task> with same taskID given
	 * @param taskID
	 * @return	return index of the task in taskList
	 */
	private static int findTaskIndex(String taskID) {
		int taskIDIndex = TAG_TASK_NOT_EXIST;
		
		for(int i = 0; i < Taskaler.taskList.size(); i++){
			if(Taskaler.taskList.get(i).getTaskID().equals(taskID)) {
				taskIDIndex = i;
			}
		}
		
		return taskIDIndex;
	}
	
}
