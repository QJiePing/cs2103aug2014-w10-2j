/**
 * 
 */
package taskaler.logic;

import java.util.ArrayList;
import java.util.Calendar;

import taskaler.common.data.DeadLineTask;
import taskaler.common.data.FloatTask;
import taskaler.common.data.RepeatedTask;
import taskaler.common.data.Task;
import taskaler.common.data.TaskList;
import taskaler.common.util.parser.calendarToString;

/**
 * @author Weng Yuan
 *
 */
public class SearchLogic {
    /**
     * find(String tagTypeFIND, String paramFIND) is to find all the tasks in
     * taskList:ArrayList<Task> with same information(type and parameter) given
     * 
     * @param tagTypeFIND
     * @param paramFIND
     * @return return a list of tasks
     */
    public ArrayList<Task> find(String tagTypeFIND, String paramFIND) {

        switch (tagTypeFIND.toUpperCase()) {
        case "KEYWORD":
            return incompleteKeywordSearch(paramFIND);
        case "DATE":
            return incompleteDeadLineSearch(paramFIND);
        case "WORKLOAD":
            return incompleteWorkLoadSearch(paramFIND);
        }
        return null;
    }

    /**
     * 
     * findByWorkload(String paramFIND) is to find all the tasks in
     * taskList:ArrayList<Task> with same workload given attribute
     * 
     * @param paramFIND
     * @return return list of Tasks (can be empty if nothing is found)
     */
    public ArrayList<Task> findByWorkload(String paramFIND) {
        ArrayList<Task> searchResultList = new ArrayList<Task>();

        for (int i = 0; i < TaskList.getInstance().size(); i++) {
            if (TaskList.getInstance().get(i).getTaskWorkLoad()
                    .equals(paramFIND)) {
                searchResultList.add(TaskList.getInstance().get(i));
            }
        }

        return searchResultList;
    }
    
    /**
     * 
     * findByWorkload(String paramFIND) is to find all the incomplete tasks in
     * taskList:ArrayList<Task> with same workload given attribute
     * 
     * @param paramFIND
     * @return return list of Tasks (can be empty if nothing is found)
     */
    public ArrayList<Task> incompleteWorkLoadSearch(String paramFIND) {
    	ArrayList<Task> searchResultList = new ArrayList<Task>();

        for (int i = 0; i < TaskList.getInstance().size(); i++) {
        	Task targetTask = TaskList.getInstance().get(i);
            if (!targetTask.getTaskStatus() && targetTask.getTaskWorkLoad().equals(paramFIND)) {
                searchResultList.add(targetTask);
            }
        }

        return searchResultList;
    }
    
    /**
     * 
     * findByWorkload(String paramFIND) is to find all the completed tasks in
     * taskList:ArrayList<Task> with same workload given attribute
     * 
     * @param paramFIND
     * @return return list of Tasks (can be empty if nothing is found)
     */
    public ArrayList<Task> completedWorkLoadSearch(String paramFIND) {
    	ArrayList<Task> searchResultList = new ArrayList<Task>();

        for (int i = 0; i < TaskList.getInstance().size(); i++) {
        	Task targetTask = TaskList.getInstance().get(i);
            if (targetTask.getTaskStatus() && targetTask.getTaskWorkLoad().equals(paramFIND)) {
                searchResultList.add(targetTask);
            }
        }

        return searchResultList;
    }

    /**
     * 
     * findByDeadLine(String paramFIND) is to find all the tasks in
     * taskList:ArrayList<Task> with same date given
     * 
     * @param paramFIND
     * @return return list of Tasks (can be empty if nothing is found)
     */
    public ArrayList<Task> findByDeadLine(String paramFIND) {

        ArrayList<Task> searchResultList = new ArrayList<Task>();
        String[] filter = paramFIND.split("/");

        Task[] listOfTask = TaskList.getInstance().toArray();

		for (int i = 0; i < listOfTask.length; i++) {

			Task targetTask = listOfTask[i];
			if(checkTargetTask(filter, targetTask)) {
				searchResultList.add(targetTask);
			}

		}

        return searchResultList;
    }
    
    
    /**
     * 
     * findByDeadLine(String paramFIND) is to find all the incomplete tasks in
     * taskList:ArrayList<Task> with same date given
     * 
     * @param paramFIND
     * @return return list of Tasks (can be empty if nothing is found)
     */
    public ArrayList<Task> incompleteDeadLineSearch(String paramFIND) {

        ArrayList<Task> searchResultList = new ArrayList<Task>();
        String[] filter = paramFIND.split("/");

        Task[] listOfTask = TaskList.getInstance().toArray();

		for (int i = 0; i < listOfTask.length; i++) {
			
			Task targetTask = listOfTask[i];
			if(!targetTask.getTaskStatus() && checkTargetTask(filter, targetTask)) {
				searchResultList.add(targetTask);
			}

		}

        return searchResultList;
    }
    
    
    /**
     * 
     * findByDeadLine(String paramFIND) is to find all the completed tasks in
     * taskList:ArrayList<Task> with same date given
     * 
     * @param paramFIND
     * @return return list of Tasks (can be empty if nothing is found)
     */
    public ArrayList<Task> completedDeadLineSearch(String paramFIND) {

        ArrayList<Task> searchResultList = new ArrayList<Task>();
        String[] filter = paramFIND.split("/");

        Task[] listOfTask = TaskList.getInstance().toArray();

		for (int i = 0; i < listOfTask.length; i++) {
			
			Task targetTask = listOfTask[i];
			if(targetTask.getTaskStatus() && checkTargetTask(filter, targetTask)) {
				searchResultList.add(targetTask);
			}

		}

        return searchResultList;
    }

    
    /**
     * 
     * checkTargetTask(String[] filter, Task targetTask) is to find check whether a given task
     * match the deadline in given filter
     * 
     * @param paramFIND
     * @return return true if both are the same deadline; False otherwise
     */
    
	private boolean checkTargetTask(String[] filter, Task targetTask) {
		String[] date = null;
		
		if (targetTask instanceof FloatTask) {
			// floating task is found via its creation date
			date = calendarToString.toArray(((FloatTask) targetTask)
					.getTaskCreationDate());
			if(compareDeadline(filter, date)) {
				return true;
			}
			
		} else if (targetTask instanceof DeadLineTask) {
			// only DeadLineTask has deadline attribute
			date = calendarToString.toArray(((DeadLineTask) targetTask)
					.getDeadline());
			if(compareDeadline(filter, date)) {
				return true;
			}
			
		} else if (targetTask instanceof RepeatedTask) {
			// only RepeatedTask have repeated date attribute
			RepeatedTask currentRepeat = (RepeatedTask) targetTask;
			for (Calendar repeatDate : currentRepeat.getRepeatedDate()) {
				date = calendarToString.toArray(repeatDate);
				if(compareDeadline(filter, date)) {
					return true;
				}
			}
		}
		
		return false;
	}

    /**
     * compareDeadline(String[] indexOfTask, Task[] listOfTask, int i, String[] date) 
     * will compare if passed values are
     * referring to the same deadline
     * 
     * @param filter
     * @param listOfTask
     * @param i
     * @param date
     * @return return true if both are the same deadline; False otherwise
     */
	private boolean compareDeadline(String[] deadline1, String[] deadline2) {
		
		if (deadline1[common.TAG_TYPE_DATE].compareToIgnoreCase(deadline2[common.TAG_TYPE_DATE]) == 0
				&& deadline1[common.TAG_TYPE_MONTH].compareToIgnoreCase(deadline2[common.TAG_TYPE_MONTH]) == 0
				&& deadline1[common.TAG_TYPE_YEAR].compareToIgnoreCase(deadline2[common.TAG_TYPE_YEAR]) == 0) {
			return true;
		}
		
		return false;
	}


    /**
     * 
     * findByKeyword(String paramFIND) is to find all the tasks in
     * taskList:ArrayList<Task> with same keyword given
     * 
     * @param paramFIND
     * @return return list of Tasks (can be empty if nothing is found)
     */
    private ArrayList<Task> findByKeyword(String paramFIND) {
        ArrayList<Task> searchResultList = new ArrayList<Task>();

        for (int i = 0; i < TaskList.getInstance().size(); i++) {
            if (TaskList.getInstance().get(i).getTaskName().contains(paramFIND)) {
                searchResultList.add(TaskList.getInstance().get(i));
            }
        }

        return searchResultList;
    }

   
    
    /**
     * 
     * findByKeyword(String paramFIND) is to find all the incomplete tasks in
     * taskList:ArrayList<Task> with same keyword given
     * 
     * @param paramFIND
     * @return return list of Tasks (can be empty if nothing is found)
     */
    
    private ArrayList<Task> incompleteKeywordSearch(String paramFIND) {
        ArrayList<Task> searchResultList = new ArrayList<Task>();

        for (int i = 0; i < TaskList.getInstance().size(); i++) {
            Task targetTask = TaskList.getInstance().get(i);
            if (!targetTask.getTaskStatus() && targetTask.getTaskName().contains(paramFIND)) {
                searchResultList.add(targetTask);
            }
        }

        return searchResultList;
    }
    
    
    /**
     * 
     * findByKeyword(String paramFIND) is to find all the incomplete tasks in
     * taskList:ArrayList<Task> with same keyword given
     * 
     * @param paramFIND
     * @return return list of Tasks (can be empty if nothing is found)
     */
    
    private ArrayList<Task> completedKeywordSearch(String paramFIND) {
        ArrayList<Task> searchResultList = new ArrayList<Task>();

        for (int i = 0; i < TaskList.getInstance().size(); i++) {
            Task targetTask = TaskList.getInstance().get(i);
            if (targetTask.getTaskStatus() && targetTask.getTaskName().contains(paramFIND)) {
                searchResultList.add(targetTask);
            }
        }

        return searchResultList;
    }
    
    
    /**
     * 
     * findByMonthAndYear(String MonthFIND, String YearFind) is to find all the
     * tasks in taskList:ArrayList<Task> with same month and year given
     * 
     * @param MonthFIND
     * @param YearFind
     * @return return list of Tasks (can be empty if nothing is found)
     */
    public ArrayList<Task> findByMonthAndYear(String monthFind, String yearFind) {

        ArrayList<Task> searchResultList = new ArrayList<Task>();
        Task[] listOfTask = TaskList.getInstance().toArray();

        for (int i = 0; i < listOfTask.length; i++) {
            Task targetTask = listOfTask[i];
            if(checkTargetTaskMY(monthFind, yearFind, targetTask)) {
            	searchResultList.add(targetTask);
            }

        }

        return searchResultList;
    }
    
    /**
     * 
     * findByMonthAndYear(String MonthFIND, String YearFind) is to find all the
     * incomplete tasks in taskList:ArrayList<Task> with same month and year given
     * 
     * @param MonthFIND
     * @param YearFind
     * @return return list of Tasks (can be empty if nothing is found)
     */
    public ArrayList<Task> IncompleteMonthAndYearSearch(String monthFind, String yearFind) {

        ArrayList<Task> searchResultList = new ArrayList<Task>();
        Task[] listOfTask = TaskList.getInstance().toArray();

        for (int i = 0; i < listOfTask.length; i++) {
            Task targetTask = listOfTask[i];
            if(!targetTask.getTaskStatus() && checkTargetTaskMY(monthFind, yearFind, targetTask)) {
            	searchResultList.add(targetTask);
            }

        }

        return searchResultList;
    }

    /**
     * 
     * findByMonthAndYear(String MonthFIND, String YearFind) is to find all the
     * incomplete tasks in taskList:ArrayList<Task> with same month and year given
     * 
     * @param MonthFIND
     * @param YearFind
     * @return return list of Tasks (can be empty if nothing is found)
     */
    public ArrayList<Task> completedMonthAndYearSearch(String monthFind, String yearFind) {

        ArrayList<Task> searchResultList = new ArrayList<Task>();
        Task[] listOfTask = TaskList.getInstance().toArray();

        for (int i = 0; i < listOfTask.length; i++) {
            Task targetTask = listOfTask[i];
            if(targetTask.getTaskStatus() && checkTargetTaskMY(monthFind, yearFind, targetTask)) {
            	searchResultList.add(targetTask);
            }

        }

        return searchResultList;
    }

    
    /**
     * 
     * checkTargetTaskMY(String[] filter, Task targetTask) is to find check whether a given task
     * match the month and year of the deadline in given filter
     * 
     * @param paramFIND
     * @return return true if both are the same deadline; False otherwise
     */
	private boolean checkTargetTaskMY(String monthFind, String yearFind, Task targetTask) {
        String[] date = null;
		if(targetTask instanceof FloatTask){
		    // floating task is found via its creation date
		    date = calendarToString.toArray(((FloatTask) targetTask).getTaskCreationDate());
			if (date[common.TAG_TYPE_MONTH].compareToIgnoreCase(monthFind) == 0
					&& date[common.TAG_TYPE_YEAR]
							.compareToIgnoreCase(yearFind) == 0) {
				return true;
			}
			
		} else if (targetTask instanceof DeadLineTask) {
		    // only DeadLineTask has deadline attribute
		    date = calendarToString.toArray(((DeadLineTask) targetTask).getDeadline());
		    if (date[common.TAG_TYPE_MONTH].compareToIgnoreCase(monthFind) == 0
					&& date[common.TAG_TYPE_YEAR]
							.compareToIgnoreCase(yearFind) == 0) {
				return true;
			}
		    
		} else if (targetTask instanceof RepeatedTask) {
		    // only RepeatedTask have repeated date attribute
		    RepeatedTask currentRepeat = (RepeatedTask) targetTask;
		    for (Calendar repeatDate : currentRepeat.getRepeatedDate()) {
		        date = calendarToString.toArray(repeatDate);
		        if (date[common.TAG_TYPE_MONTH].compareToIgnoreCase(monthFind) == 0
						&& date[common.TAG_TYPE_YEAR]
								.compareToIgnoreCase(yearFind) == 0) {
					return true;
				}
		    }
		}
		
		return false;
	}

    /**
     * 
     * findById(string taskID) function is to find a specify task with same
     * taksID given
     * 
     * @param taskID
     * @return return a task with Task data type
     */
    public Task findByID(String taskID) {
        int taskIDIndex = findTaskByID(taskID);

        if (taskIDIndex == common.TAG_TASK_NOT_EXIST) {
            return null;
        }

        return TaskList.getInstance().get(taskIDIndex);
    }

    /**
     * 
     * findTaskByID(String taskID) function is to find a specify task with same
     * taksID given
     * 
     * @param taskID
     * @return return task ID
     */
    protected static int findTaskByID(String taskID) {
        int taskIDIndex = findTaskIndex(taskID);

        return taskIDIndex;
    }

    /**
     * 
     * findTaskIndex(String taskID) is to find the task index in
     * taskList:ArrayList<Task> with same taskID given
     * 
     * @param taskID
     * @return return index of the task in taskList
     */
    private static int findTaskIndex(String taskID) {
        int taskIDIndex = common.TAG_TASK_NOT_EXIST;
        for (int i = 0; i < TaskList.getInstance().size(); i++) {
            if (TaskList.getInstance().get(i).getTaskID().equals(taskID)) {
                taskIDIndex = i;
            }
        }
        return taskIDIndex;
    }
}
