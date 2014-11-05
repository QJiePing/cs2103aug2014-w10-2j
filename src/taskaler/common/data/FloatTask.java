package taskaler.common.data;

import java.util.Calendar;


 //@author A0111798X

public class FloatTask extends Task {

	public FloatTask() {
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
	 * @param creationDate
	 *            Creation date of the task
	 * @param taskWorkLoad
	 *            Workload of the task
	 * @param taskDescription
	 *            Description of the task
	 * @param start
	 *            Start time of the task
	 * @param end
	 *            End time of the task
	 */
	public FloatTask(String taskName, String taskID, boolean taskStatus,
			Calendar creationDate, String taskWorkLoad, String taskDescription,
			Calendar start, Calendar end) {
		super(taskName, taskID, taskStatus, creationDate, taskWorkLoad,
				taskDescription, start, end);
	}

	/**
	 * Clone a new float task with the same attribute of the calling float task
	 * object
	 * 
	 * @return FloatTask
	 */
	@Override
	public FloatTask clone() {
		FloatTask newTask = new FloatTask(this.getTaskName(), this.getTaskID(),
				this.getTaskStatus(), this.getTaskCreationDate(),
				this.getTaskWorkLoad(), this.getTaskDescription(),
				this.getStartTime(), this.getEndTime());
		return newTask;
	}

}
