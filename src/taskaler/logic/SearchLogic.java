/**
 * 
 */
package taskaler.logic;

import java.util.ArrayList;
import java.util.Calendar;

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
    public static ArrayList<Task> find(String tagTypeFIND, String paramFIND) {

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
    private static ArrayList<Task> findByWorkload(String paramFIND) {
        ArrayList<Task> searchResultList = new ArrayList<Task>();

        for (int i = 0; i < TaskList.getInstance().size(); i++) {
            if (TaskList.getInstance().get(i).getTaskWorkLoad() != null) {
                if (TaskList.getInstance().get(i).getTaskWorkLoad()
                        .equals(paramFIND)) {
                    searchResultList.add(TaskList.getInstance().get(i));
                }
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
    public static ArrayList<Task> findByDeadLine(String paramFIND) {

        ArrayList<Task> searchResultList = new ArrayList<Task>();
        String[] filter = paramFIND.split("/");
        for (int i = 0; i < TaskList.getInstance().size(); i++) {
            if (TaskList.getInstance().get(i).getTaskDeadLine() != null) {
                String[] deadLine = calendarToString.toArray(TaskList
                        .getInstance().get(i).getTaskDeadLine());
                if (compareDeadline(filter, deadLine)) {
                    searchResultList.add(TaskList.getInstance().get(i));
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
    private static boolean compareDeadline(String[] deadline1,
            String[] deadline2) {
        try{
            for(int i = 0; i < deadline1.length; i++){
                if(Integer.parseInt(deadline1[i]) != Integer.parseInt(deadline2[i])){
                    return false;
                }
            }
        }catch(NumberFormatException err){
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
    private static ArrayList<Task> findByKeyword(String paramFIND) {
        ArrayList<Task> searchResultList = new ArrayList<Task>();

        for (int i = 0; i < TaskList.getInstance().size(); i++) {
            // assume task name will never be null
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
    public static ArrayList<Task> findByMonthAndYear(String monthFind,
            String yearFind) {

        ArrayList<Task> searchResultList = new ArrayList<Task>();

        for (int i = 0; i < TaskList.getInstance().size(); i++) {
            if (TaskList.getInstance().get(i).getTaskDeadLine() != null) {
                String[] date = calendarToString.toArray(TaskList.getInstance()
                        .get(i).getTaskDeadLine());
                if (date[1].equals(monthFind) && date[2].equals(yearFind)) {
                    searchResultList.add(TaskList.getInstance().get(i));
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
    public static Task findByID(String taskID) {
        int taskIDIndex = findTaskIndex(taskID);

        if (taskIDIndex == common.TAG_TASK_NOT_EXIST) {
            return null;
        } else {
            return TaskList.getInstance().get(taskIDIndex);
        }
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
