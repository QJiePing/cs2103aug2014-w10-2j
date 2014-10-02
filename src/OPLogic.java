import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;


public class OPLogic extends Logic {

	public static String ADD_NO_CONTENT = "No content is added";
	public static String EDIT_NO_CONTENT = "NO content, cannot be edited";
	public static String TASKID_NOT_EXIST = "Task ID no exist";
	public static String TASK_INITIAL_STATUS = "Not Done";
	public static String TASK_PARAMETER_DEFAULT_VALUE = "";
	
	public static int TAG_TYPE_DATE = 0;
	public static int TAG_TYPE_MONTH = 1;
	public static int TAG_TYPE_YEAR = 2;
	public static int TAG_TASK_NOT_EXIST = -1;
	
	
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
			Controller.handleError(ADD_NO_CONTENT);
			
			//fail to add a new task
			return null;
		} else {
			
			if(description_ADD == null) {
				//change to default value
				description_ADD = TASK_PARAMETER_DEFAULT_VALUE;
			}
			
			//generate a new task ID
			Taskaler.taskID++;
			Task newTask = new Task(name_ADD, Integer.toString(Taskaler.taskID), TASK_INITIAL_STATUS,
									Calendar.getInstance(), TASK_PARAMETER_DEFAULT_VALUE, description_ADD);
			Taskaler.taskList.add(newTask);
			
			return newTask;
		}
		
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
			Controller.handleError(TASKID_NOT_EXIST);
			
			//fail to delete a task
			return null;
		}
		Task taskToBeRemoved = Taskaler.taskList.remove(taskIDIndex);
		
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
	 * 																		of all the parameter given
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
			Controller.handleError(TASKID_NOT_EXIST);
			
			errorFinder = true;       //fail to edit a task
		} else if (name_EDIT == null && description_EDIT == null) {
			Controller.handleError(EDIT_NO_CONTENT);
			
			errorFinder =  true; //fail to edit a task
		}
		
		return errorFinder;
	}
	
	/**
	 * 
	 * Task editDate(String taskID, int day, int month, int year) is to edit a existing task with given ID
	 * 															  in taskList:ArrayList<Task> by given
	 * 															  name and/or description
	 * 
	 * @param taskID
	 * @param day
	 * @param month
	 * @param year
	 * @return return null if given task ID not exist, edited task otherwise
	 */
	public static Task editDate(String taskID, int day, int month, int year) {
		
		Calendar newDeadLine = setNewCalenderDate(day, month, year);

		int taskIDIndex = findTaskByID(taskID);
		
		if(taskIDIndex == TAG_TASK_NOT_EXIST) {
			Controller.handleError(TASKID_NOT_EXIST);
			
			//fail to edit a task
			return null;
		}
		
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
		newDeadLine.set(Calendar.MONTH, month);
		newDeadLine.set(Calendar.DAY_OF_MONTH, day);
		return newDeadLine;
	}
	
	
	/**
	 * 
	 * Task editWorkload(String taskID, int workloadAttribute) 	is to change the workload attribute of a task with given
	 * 															task ID in taskList:ArrayList<Task> to new workload
	 * 															attribute
	 * 
	 * @param taskID
	 * @param workloadAttribute
	 * @return return null if given task ID not exist, edited task otherwise
	 */
	public static Task editWorkload(String taskID, int workloadAttribute) {
		
		int taskIDIndex = findTaskByID(taskID);
		
		if(taskIDIndex == TAG_TASK_NOT_EXIST) {
			Controller.handleError(TASKID_NOT_EXIST);
			
			//fail to edit a task
			return null;
		}
		
		//assume workloadAttribute is within the range of 1-9
		Taskaler.taskList.get(taskIDIndex).changeTaskWorkLoad(Integer.toString(workloadAttribute));
		
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
			Controller.handleError(TASKID_NOT_EXIST);
			
			//fail to edit a task
			return null;
		}
		
		Taskaler.taskList.get(taskIDIndex).changeTaskStatus("Done");
		return Taskaler.taskList.get(taskIDIndex);
	}
	
	/**
	 * find(String tagTypeFIND, String paramFIND)	is to find all the tasks in taskList:ArrayList<Task> with given
	 * 												information(type and parameter)
	 * @param tagTypeFIND
	 * @param paramFIND
	 * @return return a list of tasks
	 */
	public static ArrayList<Task> find(String tagTypeFIND, String paramFIND){
		
		switch(tagTypeFIND.toUpperCase()){
		case "KEYWORD":
			return findByKeyword(paramFIND);
		case "DATE":
			return findByDeadLine(TAG_TYPE_DATE, paramFIND);
		case "MONTH":
			return findByDeadLine(TAG_TYPE_MONTH, paramFIND);
		case "YEAR":
			return findByDeadLine(TAG_TYPE_YEAR, paramFIND);
		case "WORKLOAD":
			return findByWordload(paramFIND);
		}
		return null;
	}
	
	
	
	private static ArrayList<Task> findByWordload(String paramFIND) {
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

	private static ArrayList<Task> findByDeadLine(int tagType, String paramFIND) {

		ArrayList<Task> searchResultList = new ArrayList<Task>();
		
		for(int i = 0; i < Taskaler.taskList.size(); i++){
			if(Taskaler.taskList.get(i).getTaskDeadLine() != null) {
				Calendar taskDeadLine = Taskaler.taskList.get(i).getTaskDeadLine();
				int[] deadline = {taskDeadLine.get(Calendar.DAY_OF_MONTH), taskDeadLine.get(Calendar.MONTH),taskDeadLine.get(Calendar.YEAR)};
				if(deadline[tagType] == Integer.parseInt(paramFIND)) {
					searchResultList.add(Taskaler.taskList.get(i));
				}
			}
		}
		
		return searchResultList;
	}

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
	 * findById(string taskID) function is to find a specify task with give taksID
	 * 
	 * @param taskID
	 * @return	return a task with Task data type
	 */
	private static Task findByID(String taskID) {
		int taskIDIndex = findTaskIndex(taskID);
		
		if(taskIDIndex == TAG_TASK_NOT_EXIST) {
			return null;
		} else {
			return Taskaler.taskList.get(taskIDIndex);
		}
	}
	
	
	/**
	 * 
	 * findTaskByID(String taskID) function is to find a specify task with give taksID
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
	 * findTaskIndex(String taskID) is to find the task index in taskList:ArrayList<Task> with given taskID
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
