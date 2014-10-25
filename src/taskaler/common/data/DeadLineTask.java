package taskaler.common.data;

import java.util.Calendar;

/*
 * @author Quek Jie Ping
 */
public class DeadLineTask extends Task{

	private Calendar _startTime;
	private Calendar _endTime;

	public DeadLineTask(){
		super();
	}
	/**
	 * Overloaded constructor to create a new DeadLineTask object
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
	 * @param start
	 * 		      Start time of the task
	 * @param end
	 * 		      End time of the task
	 */
	public DeadLineTask(String taskName, String taskID, String taskStatus,Calendar creationDate, String taskWorkLoad,
			String taskDescription, Calendar start, Calendar end){
		super(taskName,taskID,taskStatus,creationDate,taskWorkLoad,taskDescription);
		this._startTime=start;
		this._endTime=end;
	}
	
	/**
	 * Clone a new deadline task with the same attribute of the calling deadline task object
	 * @return DeadLineTask
	 */
	public DeadLineTask clone(){
		DeadLineTask newTask = new DeadLineTask(this.getTaskName(), this.getTaskID(), this.getTaskStatus(),this.getTaskCreationDate(), this.getTaskWorkLoad(), this.getTaskDescription(),this.getStartTime(),this.getEndTime());
	    return newTask;
	}
	
	
	/**************** Accessor ***********************/
	public Calendar getStartTime(){
		return this._startTime;
	}
	public Calendar getEndTime(){
		return this._endTime;
	}
	/**************** Mutators ************************/
	public void setStartTime(Calendar start){
		this._startTime=start;
	}
	public void setEndTime(Calendar end){
		this._endTime=end;
	}
}
