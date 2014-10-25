package taskaler.common.data;

import java.util.ArrayList;
import java.util.Calendar;

/*
 * @author Quek Jie Ping
 */
public class RepeatedTask extends Task {
	
	private Calendar _startTime;
	private Calendar _endTime;
	private ArrayList<Calendar> _repeatedDate;
	private Calendar _endRepeatedDate;
	private int _collectionID;
	
	public RepeatedTask(){
	}
	/**
	 * Overloaded constructor to create a new RepeatedTask object
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
	 * @param repeatedDate
	 * 		      Date of the task to be place on a specific day of the calendar repeatedly
	 * @param endRepeatedDate
	 * 		      After this date, this repeated task will be not be shown in the calendar
	 * @param ID
	 * 		      This is ID for the repeated task
	 */
	public RepeatedTask(String taskName, String taskID, String taskStatus,Calendar creationDate, String taskWorkLoad, 
			String taskDescription, Calendar start, Calendar end, ArrayList<Calendar> repeatedDate,Calendar endRepeatedDate,int ID){
		
		super(taskName,taskID,taskStatus,creationDate,taskWorkLoad,taskDescription);
		this._startTime=start;
		this._endTime=end;
		this._repeatedDate=repeatedDate;
		this._endRepeatedDate=endRepeatedDate;
		this._collectionID=ID;
	}
	
	/**
	 * Clone a new repeated task with the same attribute of the calling repeated task object
	 * @return RepeatedTask
	 */
	public RepeatedTask clone(){
		RepeatedTask newTask = new RepeatedTask(this.getTaskName(), this.getTaskID(), this.getTaskStatus(),this.getTaskCreationDate(), this.getTaskWorkLoad(), this.getTaskDescription(),
				this.getStartTime(),this.getEndTime(),this.getRepeatedDate(),this.getEndRepeatedDate(),this.getCollectiveID());
	    return newTask;
	}
	
	/**************** Accessor ***********************/
	public Calendar getStartTime(){
		return this._startTime;
	}
	public Calendar getEndTime(){
		return this._endTime;
	}
	public ArrayList<Calendar> getRepeatedDate(){
		return this._repeatedDate;
	}
	public Calendar getEndRepeatedDate(){
		return this._endRepeatedDate;
	}
	public int getCollectiveID(){
		return this._collectionID;
	}
	
	/**************** Mutators ************************/
	public void setStartTime(Calendar start){
		this._startTime=start;
	}
	public void setEndTime(Calendar end){
		this._endTime=end;
	}
	public void setRepeatedDate(ArrayList<Calendar> repeat){
		this._repeatedDate=repeat;
	}
	public void setEndRepeatedDate(Calendar endRepeat){
		this._endRepeatedDate=endRepeat;
	}
	public void setCollectiveID(int ID){
		this._collectionID=ID;
	}
}
