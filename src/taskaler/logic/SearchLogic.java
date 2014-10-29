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
            return findByKeyword(paramFIND);
        case "DATE":
            return findByDeadLine(paramFIND);
        case "WORKLOAD":
            return findByWorkload(paramFIND);
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
    private ArrayList<Task> findByWorkload(String paramFIND) {
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
            String[] date = null;
            
            if(listOfTask[i] instanceof FloatTask){
                // floating task is found via its creation date
                date = calendarToString.toArray(((FloatTask) listOfTask[i]).getTaskCreationDate());
                if (date[0].compareToIgnoreCase(filter[0]) == 0 && date[1].compareToIgnoreCase(filter[1]) == 0 && date[2].compareToIgnoreCase(filter[2]) == 0) {
                    searchResultList.add(listOfTask[i]);
                }
            } else if (listOfTask[i] instanceof DeadLineTask) {
                // only DeadLineTask has deadline attribute
                date = calendarToString.toArray(((DeadLineTask) listOfTask[i]).getDeadline());
                if (date[0].compareToIgnoreCase(filter[0]) == 0 && date[1].compareToIgnoreCase(filter[1]) == 0 && date[2].compareToIgnoreCase(filter[2]) == 0) {
                    searchResultList.add(listOfTask[i]);
                }
            } else if (listOfTask[i] instanceof RepeatedTask) {
                // only RepeatedTask have repeated date attribute
                RepeatedTask currentRepeat = (RepeatedTask) listOfTask[i];
                for (Calendar repeatDate : currentRepeat.getRepeatedDate()) {
                    date = calendarToString.toArray(repeatDate);
                    if (date[0].compareToIgnoreCase(filter[0]) == 0 && date[1].compareToIgnoreCase(filter[1]) == 0 && date[2].compareToIgnoreCase(filter[2]) == 0) {
                        searchResultList.add(listOfTask[i]);
                    }
                }
            }

        }

        return searchResultList;
    }

    /**
     * 
     * compareDeadline(String[] indexOfTask) will compare if passed values are
     * refering to the same deadline
     * 
     * @param deadline1
     *            First deadline
     * @param deadline2
     *            Second deadline
     * @return return true if both are the same deadline; False otherwise
     */
    private boolean compareDeadline(String[] deadline1, String[] deadline2) {
        try {
            for (int i = 0; i < deadline1.length; i++) {
                if (Integer.parseInt(deadline1[i]) != Integer
                        .parseInt(deadline2[i])) {
                    return false;
                }
            }
        } catch (NumberFormatException err) {
            return false;
        }
        return true;
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
            String[] date = null;
            
            if(listOfTask[i] instanceof FloatTask){
                // floating task is found via its creation date
                date = calendarToString.toArray(((FloatTask) listOfTask[i]).getTaskCreationDate());
                if (date[1].compareToIgnoreCase(monthFind) == 0 && date[2].compareToIgnoreCase(yearFind) == 0) {
                    searchResultList.add(listOfTask[i]);
                }
            } else if (listOfTask[i] instanceof DeadLineTask) {
                // only DeadLineTask has deadline attribute
                date = calendarToString.toArray(((DeadLineTask) listOfTask[i]).getDeadline());
                if (date[1].compareToIgnoreCase(monthFind) == 0 && date[2].compareToIgnoreCase(yearFind) == 0) {
                    searchResultList.add(listOfTask[i]);
                }
            } else if (listOfTask[i] instanceof RepeatedTask) {
                // only RepeatedTask have repeated date attribute
                RepeatedTask currentRepeat = (RepeatedTask) listOfTask[i];
                for (Calendar repeatDate : currentRepeat.getRepeatedDate()) {
                    date = calendarToString.toArray(repeatDate);
                    if (date[1].compareToIgnoreCase(monthFind) == 0 && date[2].compareToIgnoreCase(yearFind) == 0) {
                        searchResultList.add(listOfTask[i]);
                    }
                }
            }

        }

        return searchResultList;
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
