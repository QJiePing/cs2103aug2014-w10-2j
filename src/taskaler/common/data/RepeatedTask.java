package taskaler.common.data;

import java.util.ArrayList;
import java.util.Calendar;

//@author A0111798X

public class RepeatedTask extends Task {
    
    private String _pattern;
	private ArrayList<Calendar> _repeatedDate;
	private Calendar _endRepeatedDate;
	private int _collectionID;

	public RepeatedTask() {
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
	 *            Start time of the task
	 * @param end
	 *            End time of the task
	 * @param repeatedDate
	 *            Date of the task to be place on a specific day of the calendar
	 *            repeatedly
	 * @param endRepeatedDate
	 *            After this date, this repeated task will be not be shown in
	 *            the calendar
	 * @param ID
	 *            This is ID for the repeated task
	 */
	public RepeatedTask(String taskName, String taskID, boolean taskStatus,
			Calendar creationDate, String taskWorkLoad, String taskDescription,
			Calendar start, Calendar end, String pattern, ArrayList<Calendar> repeatedDate,
			Calendar endRepeatedDate, int ID) {

		super(taskName, taskID, taskStatus, creationDate, taskWorkLoad,
				taskDescription, start, end);
		this._pattern = pattern;
		this._repeatedDate = repeatedDate;
		this._endRepeatedDate = endRepeatedDate;
		this._collectionID = ID;
	}

	/**
	 * Clone a new repeated task with the same attribute of the calling repeated
	 * task object
	 * 
	 * @return RepeatedTask
	 */
	@Override
	public RepeatedTask clone() {
		RepeatedTask newTask = new RepeatedTask(this.getTaskName(),
				this.getTaskID(), this.getTaskStatus(),
				this.getTaskCreationDate(), this.getTaskWorkLoad(),
				this.getTaskDescription(), this.getStartTime(),
				this.getEndTime(), this.getPattern(), this.getRepeatedDate(),
				this.getEndRepeatedDate(), this.getCollectiveID());
		return newTask;
	}

	/**
	 * Method to return the String pattern
	 * @return String
	 */
	public String getPattern() {
	    return this._pattern;
	}
	/**
	 * Method to return an array list of all the date(Calendar object)
	 * @return ArrayList<Calendar>
	 */
	public ArrayList<Calendar> getRepeatedDate() {
		return this._repeatedDate;
	}
	/**
	 * Method to return the last date of the repeating date.
	 * @return Calendar
	 */
	public Calendar getEndRepeatedDate() {
		return this._endRepeatedDate;
	}
	/**
	 * Method to return the collective ID of repeatedTask.
	 * @return int
	 */
	public int getCollectiveID() {
		return this._collectionID;
	}

	/**
	 * A mutator method that change the pattern attributes
	 * @param pattern
	 * 				The new string pattern
	 */
	public void setPattern(String pattern){
	    this._pattern = pattern;
	}
	/**
	 * A mutator method that change the array list of dates(Calendar object)
	 * @param repeat 
	 * 			The new array list of dates.
	 */
	public void setRepeatedDate(ArrayList<Calendar> repeat) {
		this._repeatedDate = repeat;
	}
	/**
	 * A mutator method that change the date of the last repeated date
	 * @param endRepeat
	 * 				The new last repeated date.
	 */
	public void setEndRepeatedDate(Calendar endRepeat) {
		this._endRepeatedDate = endRepeat;
	}
	/**
	 * A mutator method that change the collective ID
	 * @param ID
	 * 			The new collective ID for the repeatedTask.
	 */
	public void setCollectiveID(int ID) {
		this._collectionID = ID;
	}
	
	/**
	 * Change the pattern string to English representation string
	 * @param pattern
	 * 				The string pattern of the repeatedTask 
	 * @return String
	 */
	public static String patternToEnglish(String pattern){
	    String[] patternSplit = pattern.split("\\s+");
	    if(patternSplit.length == 1){
	        switch(pattern){
	        case "WEEKEND":
	            return "Weekends";
	        case "WEEKDAY":
	            return "Weekdays"; 
	        case "LAST":
	            return "Every last day";
	        }
	    } else if(patternSplit.length == 2){
	        String every = "Every ";
	        String type = patternSplit[1];
	        int variable = 0;
	        try{
	            variable = Integer.parseInt(patternSplit[0]);
	        } catch(Exception e){
	            return pattern;
	        }
	        switch(type){
	        case "DAYOFWEEK":
	            switch(variable){
	            case 1:
	                return every + "SUN";
	            case 2:
	                return every + "MON";
	            case 3:
	                return every + "TUE";
	            case 4:
	                return every + "WED";
	            case 5:
	                return every + "THU";
	            case 6:
	                return every + "FRI";
	            case 7:
	                return every + "SAT";
	            }
	        case "DAY":
	            switch(variable){
	            case 1:
	                return "Daily";
	            case 2:
	                return every + "2 days";
	            }
	        case "WEEK":
	            switch(variable){
	            case 1:
	                return "Weekly";
	            case 2:
	                return every + "2 weeks";
	            }
	        case "MONTH":
	            switch(variable){
	            case 1:
	                return "Monthly";
	            }
	        case "YEAR":
	            switch(variable){
	            case 1:
	                return "Yearly";
	            }
	        }
	    }
        return pattern;
	}
}
