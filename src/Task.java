
public class Task {
	
	private String _taskName = null;
	private String _taskID = null;
	private String _taskStatus = null;
	private String _taskDeadLine = null;
	private String _taskWorkLoad = null;
	private String _taskDescription = null;
	
	
	public Task() {
	}
	
	/*
	 * Here I assume that some parameter may be null
	 */
	public Task(String taskName, String taskID, String taskStatus, String taskDeadLine,
			  	String taskWorkLoad, String taskDescription) {
		
		_taskName = taskName;
		_taskID = taskID;
		_taskStatus = taskStatus;
		_taskDeadLine = taskDeadLine;
		_taskWorkLoad = taskWorkLoad;
		_taskDescription = taskDescription;
		
	}
	
	public String getTaskName() {	return _taskName; }
	
	public String getTaskID() {	return _taskID; }
	
	public String getTaskStatus() {	return _taskStatus; }
	
	public String getTaskDeadLine() {	return _taskDeadLine; }
	
	public String getTaskWorkLoad() {	return _taskWorkLoad; }
	
	public String getTaskDescription() {	return _taskDescription; }
	
	
	// Some other methods below(maybe)
	// ...
}
