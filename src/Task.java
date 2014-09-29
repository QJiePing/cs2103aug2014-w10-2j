import java.util.Calendar;


public class Task {
	
	private String _taskName;
	private String _taskID;
	private String _taskStatus;
	private Calendar _taskDeadLine;
	private String _taskWorkLoad;
	private String _taskDescription;
	
	
	public Task() {
	}
	
	/*
	 * Here I assume that some parameters may be null
	 */
	public Task(String taskName, String taskID, String taskStatus, Calendar taskDeadLine,
			  	String taskWorkLoad, String taskDescription) {
		
		_taskName = taskName;
		_taskID = taskID;
		_taskStatus = taskStatus;
		_taskDeadLine = taskDeadLine;
		_taskWorkLoad = taskWorkLoad;
		_taskDescription = taskDescription;
		
	}
	
	
	/**************** Accessors ***********************/
	public String getTaskName() {	return _taskName; }
	
	public String getTaskID() {	return _taskID; }
	
	public String getTaskStatus() {	return _taskStatus; }
	
	public Calendar getTaskDeadLine() {	return _taskDeadLine; }
	
	public String getTaskWorkLoad() {	return _taskWorkLoad; }
	
	public String getTaskDescription() {	return _taskDescription; }
	
	
	/**************** Mutators ************************/
	public void changeTaskName(String newTaskName) {
		_taskName = newTaskName;
	}
	
	/*
	 *  TaskID may be unique
	 *  
	public void changeTaskID(String newTaskID) {
		_taskID =  newTaskID;
	}
	*/
	
	
	public void changeTaskStatus(String newTaskStatus) {
		_taskStatus =  newTaskStatus;
	}
	
	public void changeDeadLine(Calendar newTaskDeadLine) {
		_taskDeadLine =  newTaskDeadLine;
	}
	
	public void changeTaskWorkLoad(String newTaskWordLoad) {
		_taskWorkLoad =  newTaskWordLoad;
	}
	
	public void changeTaskDescription(String newTaskDescription) {
		_taskDescription =  newTaskDescription;
	}
	
	
	// Some other methods below(maybe)
	// ...
}
