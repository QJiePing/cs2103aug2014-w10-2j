/**
 * 
 */
package taskaler.common.data;

import java.util.Calendar;

//@author A0111798X
public abstract class Task {

	/**
	 * Possible workload values
	 */
	public static final String WORKLOAD_NONE = "0";
	public static final String WORKLOAD_LOW = "1";
	public static final String WORKLOAD_MEDIUM = "2";
	public static final String WORKLOAD_HIGH = "3";

	public enum WorkloadProperty {
		NONE, LOW, MEDIUM, HIGH
	}

	private String _taskName;
	private String _taskID;
	private boolean _taskStatus;
	private Calendar _taskStartTime;
	private Calendar _taskEndTime;
	private Calendar _creationDate;
	private WorkloadProperty _taskWorkLoad;
	private String _taskDescription;

	public Task() {
	}

	/**
	 * Overloaded constructor to create a new Task object
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
	public Task(String taskName, String taskID, boolean taskStatus,
			Calendar creationDate, String taskWorkLoad, String taskDescription,
			Calendar startTime, Calendar endTime) {

		_taskName = taskName;
		_taskID = taskID;
		_taskStatus = taskStatus;
		_taskStartTime = startTime;
		_taskEndTime = endTime;
		_creationDate = creationDate;
		_taskWorkLoad = workloadFromString(taskWorkLoad);
		_taskDescription = taskDescription;

	}

	/**
	 * Method to return the task name.
	 * @return String
	 */
	public String getTaskName() {
		return _taskName;
	}
	/**
	 * Method to return the task id.
	 * @return String
	 */
	public String getTaskID() {
		return _taskID;
	}
	/**
	 * Method to return the task status.
	 * @return boolean
	 */
	public boolean getTaskStatus() {
		return _taskStatus;
	}
	/**
	 * Method to return the start time of the task.
	 * @return Calendar
	 */
	public Calendar getStartTime() {
		return _taskStartTime;
	}
	/**
	 * Method to return the end time of the task.
	 * @return Calendar
	 */
	public Calendar getEndTime() {
		return _taskEndTime;
	}
	/**
	 * Method to return the task workload value.
	 * @return String
	 */
	public String getTaskWorkLoad() {
		return workloadToString(_taskWorkLoad);
	}
	/**
	 * Method to return the task creation date.
	 * @return Calendar
	 */
	public Calendar getTaskCreationDate() {
		return _creationDate;
	}
	/**
	 * Method to return the task description.
	 * @return String
	 */
	public String getTaskDescription() {
		return _taskDescription;
	}

	/**
	 * A mutator method to change the task name.
	 * @param newTaskName
	 * 				The new task name of the task.
	 */
	public void changeTaskName(String newTaskName) {
		_taskName = newTaskName;
	}

	/**
	 * A mutator method to change the task's status.
	 * @param newTaskStatus
	 * 				The new status of the task.
	 */
	public void changeTaskStatus(boolean newTaskStatus) {
		_taskStatus = newTaskStatus;
	}
	/**
	 * A mutator method to change the start time of the task.
	 * @param newTime
	 * 				The new start time of the task.
	 */
	public void changeStartTime(Calendar newTime) {
		_taskStartTime = newTime;
	}
	/**
	 * A mutator method to change the end time of the task.
	 * @param newTime
	 * 				The new end time of the task.
	 */
	public void changeEndTime(Calendar newTime) {
		_taskEndTime = newTime;
	}
	/**
	 * A mutator method to change the task workload value.
	 * @param newTaskWordLoad
	 * 				The new workload value of the task.
	 */
	public void changeTaskWorkLoad(String newTaskWordLoad) {
		_taskWorkLoad = workloadFromString(newTaskWordLoad);
	}
	/**
	 * A mutator method to change the task description.
	 * @param newTaskDescription
	 * 				The new description of the task.
	 */
	public void changeTaskDescription(String newTaskDescription) {
		_taskDescription = newTaskDescription;
	}

	/**
	 * Class methods
	 */
	
	/**
	 * Abstract method to create a new task object with the same values To be
	 * implemented by the subclass
	 */
	@Override
	public abstract Task clone();

	/**
	 * Method to map a string to workload property
	 * 
	 * @param input
	 *            String to be mapped
	 * @return WorkloadProperty
	 */
	protected static WorkloadProperty workloadFromString(String input) {
		if (input.compareToIgnoreCase(WORKLOAD_HIGH) == 0) {
			return WorkloadProperty.HIGH;
		} else if (input.compareToIgnoreCase(WORKLOAD_MEDIUM) == 0) {
			return WorkloadProperty.MEDIUM;
		} else if (input.compareToIgnoreCase(WORKLOAD_LOW) == 0) {
			return WorkloadProperty.LOW;
		} else {
			return WorkloadProperty.NONE;
		}
	}

	/**
	 * Method to convert a workload property to its string representation
	 * 
	 * @param workload
	 *            Property to be converted
	 * @return String
	 */
	protected static String workloadToString(WorkloadProperty workload) {
		if (workload == null) {
			return WORKLOAD_NONE;
		}
		switch (workload) {
		case HIGH:
			return WORKLOAD_HIGH;
		case MEDIUM:
			return WORKLOAD_MEDIUM;
		case LOW:
			return WORKLOAD_LOW;
		default:
			return WORKLOAD_NONE;
		}
	}
}