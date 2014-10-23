package taskaler.common.data;

/*
 * @author Quek Jie Ping
 */
public class FloatTask extends Task{

	public FloatTask(){
		super();
	}
	/**
     * Overloaded constructor to create a new FloatTask object
     * 
     * @param taskName
     *            Name of the task
     * @param taskID
     *            ID of the task
     * @param taskStatus
     *            Status of the task
     * @param taskWorkLoad
     *            Workload of the task
     * @param taskDescription
     *            Description of the task
     */
	public FloatTask(String taskName, String taskID, String taskStatus, String taskWorkLoad, 
			String taskDescription){
		super(taskName,taskID,taskStatus,taskWorkLoad,taskDescription);
	}
	
	/**
	 * Clone a new float task with the same attribute of the calling float task object
	 * @return FloatTask
	 */
	public FloatTask clone(){
		FloatTask newTask = new FloatTask(this.getTaskName(), this.getTaskID(), this.getTaskStatus(), this.getTaskWorkLoad(), this.getTaskDescription());
	    return newTask;
	}
	
}
