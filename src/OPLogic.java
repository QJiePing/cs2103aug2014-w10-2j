import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;


public class OPLogic extends Logic {

	public static String ADD_NO_CONTENT = "No content is added";
	public static String EDIT_NO_CONTENT = "NO content, cannot be edited";
	public static String TASKID_NOT_EXIST = "Task ID no exist";
	
	public static int TAG_TYPE_DATE = 0;
	public static int TAG_TYPE_MONTH = 1;
	public static int TAG_TYPE_YEAR = 2;
	
	
	public static boolean addTask(String name_ADD, String description_ADD) {
		
		//assume task name cannot be null
		if(name_ADD == null) {
			Controller.handleError(ADD_NO_CONTENT);
			
			//fail to add a new task
			return false;
		} else {
			
			if(description_ADD == null) {
				description_ADD = "";
			}
			
			Taskaler.taskID++;
			
			Task newTask = new Task(name_ADD, Integer.toString(Taskaler.taskID), "Not Done", null, "", description_ADD);
			Taskaler.taskList.add(newTask);
			Taskaler.ui.displayTask(newTask);
			
			//success to add a new task
			return true;
		}
		
	}
	
	public static boolean deleteTask(String taskID_DELETE) {
		
		int taskIDIndex = findTaskByID(taskID_DELETE);
		
		if(taskIDIndex == -1) {
			Controller.handleError(TASKID_NOT_EXIST);
			
			//fail to delete a task
			return false;
		}
		
		Taskaler.taskList.remove(taskIDIndex);
		return true;
	
	}

	
	public static boolean editTask(String taskID_EDIT, String name_EDIT,
			String description_EDIT) {
		
		int taskIDIndex = findTaskByID(taskID_EDIT);
		
		if(taskIDIndex == -1) {
			Controller.handleError(TASKID_NOT_EXIST);
			
			//fail to edit a task
			return false;
		} else if (name_EDIT == null && description_EDIT == null) {
			Controller.handleError(EDIT_NO_CONTENT);
			
			//fail to edit a task
			return false;
		}
		
		
		//assume name will not change to null
		if(name_EDIT != null) {
			Taskaler.taskList.get(taskIDIndex).changeTaskName(name_EDIT);
		}
		
		//assume description will not change to null
		if(description_EDIT != null) {
			Taskaler.taskList.get(taskIDIndex).changeTaskDescription(description_EDIT);
		}
		
		return true;
		
	}
	
	public static boolean editDate(String taskID, int day, int month, int year) {
		
		Calendar newDeadLine = new GregorianCalendar(year, month, day);
		
		int taskIDIndex = findTaskByID(taskID);
		
		if(taskIDIndex == -1) {
			Controller.handleError(TASKID_NOT_EXIST);
			
			//fail to edit a task
			return false;
		}
		
		Taskaler.taskList.get(taskIDIndex).changeDeadLine(newDeadLine);
		
		return true;
	}
	
	public static boolean editWorkload(String taskID, int workloadAttribute) {
		
		int taskIDIndex = findTaskByID(taskID);
		
		if(taskIDIndex == -1) {
			Controller.handleError(TASKID_NOT_EXIST);
			
			//fail to edit a task
			return false;
		}
		
		//assume workloadAttribute is within the range of 1-9
		Taskaler.taskList.get(taskIDIndex).changeTaskWorkLoad(Integer.toString(workloadAttribute));
		
		return true;
	}
	
	public static boolean switchTag(String taskID) {
		int taskIDIndex = findTaskByID(taskID);
		
		if(taskIDIndex == -1) {
			Controller.handleError(TASKID_NOT_EXIST);
			
			//fail to edit a task
			return false;
		}
		
		Taskaler.taskList.get(taskIDIndex).changeTaskStatus("Done");
		return false;
	}
	
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

	private static int findTaskByID(String taskID) {
		
		int taskIDIndex = -1;
		for(int i = 0; i < Taskaler.taskList.size(); i++){
			if(Taskaler.taskList.get(i).getTaskID().equals(taskID)) {
				taskIDIndex = i;
			}
		}
		
		return taskIDIndex;
	}
	
}
