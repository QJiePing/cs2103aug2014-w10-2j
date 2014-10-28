package taskaler.common.data;

import java.util.Calendar;

/*
 * @author Quek Jie Ping
 */
public class DeadLineTask extends Task{
    
    private Calendar _deadLine;

	public DeadLineTask(){
		super();
	}
	/**
	 * Overloaded constructor to create a new DeadLineTask object
	 *
	 * 
	 * @param taskName
	 * 				Name of Task
	 * @param taskID
	 * 				ID of the task
	 * @param taskStatus
	 * 				Status of the task
	 * @param creationDate
	 * 				Creation date of the task
	 * @param taskWorkLoad
	 * 				Workload of the task
	 * @param taskDescription
	 * 				Description of the task
	 * @param deadline
	 * 				Deadline of the task
	 * @param start
	 * 				Start time of the task
	 * @param end
	 * 				end time of the task
	 */
	
	public DeadLineTask(String taskName, String taskID, String taskStatus,Calendar creationDate, String taskWorkLoad,
			String taskDescription, Calendar deadline, Calendar start, Calendar end){
		super(taskName,taskID,taskStatus,creationDate,taskWorkLoad,taskDescription,start,end);
		this._deadLine=deadline;
	}
	
	/**
	 * Clone a new deadline task with the same attribute of the calling deadline task object
	 * @return DeadLineTask
	 */
	public DeadLineTask clone(){
		DeadLineTask newTask = new DeadLineTask(this.getTaskName(), this.getTaskID(), this.getTaskStatus(),this.getTaskCreationDate(), 
		        this.getTaskWorkLoad(), this.getTaskDescription(),this.getDeadline(), this.getStartTime(), this.getEndTime());
	    return newTask;
	}
	
	
	/**************** Accessor ***********************/
	public Calendar getDeadline(){
	    return this._deadLine;
	}

	/**************** Mutators ************************/
	public void setDeadline(Calendar date){
	    this._deadLine = date;
	}
}
