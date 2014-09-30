
public class Task {
	
	private String _taskName;
	private String _taskID;
	private String _taskStatus;
	private String _taskDeadLine;
	private String _taskWorkLoad;
	private String _taskDescription;
	
	
	public Task() {
	}
	
	/*
	 * Here I assume that some parameters may be null
	 */
	public Task(String taskName, String taskID, String taskStatus, String taskDeadLine,
			  	String taskWorkLoad, String taskDescription) {
		if(taskStatus == null){
		    taskStatus = "";
		}
		
		if(taskDeadLine == null){
            taskDeadLine = "";
        }
		
		if(taskWorkLoad == null){
            taskWorkLoad = "";
        }
		
		if(taskDescription == null){
            taskWorkLoad = "";
        }
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
	
	public String getTaskDeadLine() {	return _taskDeadLine; }
	
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
	
	public void changeDeadLine(String newTaskDeadLine) {
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
