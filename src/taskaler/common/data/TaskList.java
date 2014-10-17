/**
 * 
 */
package taskaler.common.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import taskaler.common.util.parser.calendarToString;

/**
 * Singleton Class to hold the global task list
 *
 */
public class TaskList implements Collection<Task> {

    // Special Constants
    public static int TAG_TASK_NOT_EXIST = -1;

    // Static class variables
    private static TaskList instance = null;
    private static ArrayList<Task> taskList = null;

    /**
     * Private constructor
     * 
     */
    private TaskList() {
        taskList = new ArrayList<Task>();
    }

    /**
     * Gets the global task list. Creates a new task list if first call.
     * 
     * @return Global task list
     */
    public static TaskList getInstance() {
        if (taskList == null) {
            instance = new TaskList();
        }

        return instance;
    }

    @Override
    public boolean add(Task task) {
        return taskList.add(task);
    }

    @Override
    public boolean addAll(Collection<? extends Task> collection) {
        return taskList.addAll(collection);
    }

    @Override
    public void clear() {
        taskList.clear();
    }

    @Override
    public boolean contains(Object obj) {
        return taskList.contains(obj);
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        return taskList.containsAll(collection);
    }

    @Override
    public boolean isEmpty() {
        return taskList.isEmpty();
    }

    @Override
    public Iterator<Task> iterator() {
        return taskList.iterator();
    }

    @Override
    public boolean remove(Object obj) {
        return taskList.remove(obj);
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        return taskList.removeAll(collection);
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        return taskList.retainAll(collection);
    }

    @Override
    public int size() {
        return taskList.size();
    }

    @Override
    public Object[] toArray() {
        Object[] array = new Object[taskList.size()];
        for (int i = 0; i < array.length; i++) {
            array[i] = taskList.get(i);
        }
        return array;
    }

    @Override
    public <T> T[] toArray(T[] arg0) {
        for (int i = 0; i < arg0.length; i++) {
            arg0[i] = (T) taskList.get(i);
        }
        return arg0;
    }

    /**
     * Retrieves the task specified by the index
     * 
     * @param i
     *            The index of the task
     * @return The task at the index
     */
    public Task get(int i) {
        return taskList.get(i);
    }

    /**
     * 
     * findByWorkload(String paramFIND) is to find all the tasks in
     * taskList:ArrayList<Task> with same workload given attribute
     * 
     * @param paramFIND
     *            The workload category to be searched
     * @return return list of Tasks (can be empty if nothing is found)
     */
    public static ArrayList<Task> findByWorkload(String paramFIND) {
        ArrayList<Task> searchResultList = new ArrayList<Task>();

        for (int i = 0; i < taskList.size(); i++) {
            if (taskList.get(i).getTaskWorkLoad() != null) {
                if (taskList.get(i).getTaskWorkLoad().equals(paramFIND)) {
                    searchResultList.add(taskList.get(i));
                }
            }
        }

        return searchResultList;
    }

    /**
     * 
     * findTaskByDeadLine(String paramFIND) is to find all the tasks in
     * taskList:ArrayList<Task> with same date given
     * 
     * @param paramFIND
     *            The date to be searched
     * @return return list of Tasks (can be empty if nothing is found)
     */
    public static ArrayList<Task> findTaskByDeadLine(String paramFIND) {

        ArrayList<Task> searchResultList = new ArrayList<Task>();

        for (int i = 0; i < taskList.size(); i++) {
            Task currentTask = taskList.get(i);
            if (currentTask.getTaskDeadLine() != null) {
                String deadLine = calendarToString.parseDate(currentTask
                        .getTaskDeadLine());
                if (deadLine.equals(paramFIND)) {
                    searchResultList.add(taskList.get(i));
                }
            }
        }

        return searchResultList;
    }

    /**
     * 
     * findTaskByKeyword(String paramFIND) is to find all the tasks in
     * taskList:ArrayList<Task> with same keyword given
     * 
     * @param paramFIND
     *            The keyword to find in the task list
     * @return return list of Tasks (can be empty if nothing is found)
     */
    public static ArrayList<Task> findTaskByKeyword(String paramFIND) {
        ArrayList<Task> searchResultList = new ArrayList<Task>();

        for (int i = 0; i < taskList.size(); i++) {
            // assume task name will never be null
            if (taskList.get(i).getTaskName().contains(paramFIND)) {
                searchResultList.add(taskList.get(i));
            }
        }

        return searchResultList;
    }

    /**
     * 
     * findTaskByMonthAndYear(String MonthFIND, String YearFind) is to find all
     * the tasks in taskList:ArrayList<Task> with same month and year given
     * 
     * @param MonthFIND
     *            Month filter
     * @param YearFind
     *            Year filter
     * @return return list of Tasks (can be empty if nothing is found)
     */
    public static ArrayList<Task> findTaskByMonthAndYear(String monthFind,
            String yearFind) {

        ArrayList<Task> searchResultList = new ArrayList<Task>();

        for (int i = 0; i < taskList.size(); i++) {
            Task currentTask = taskList.get(i);
            if (currentTask.getTaskDeadLine() != null) {
                String[] date = calendarToString.toArray(currentTask
                        .getTaskDeadLine());
                if (date[1].compareToIgnoreCase(monthFind) == 0
                        && date[2].compareToIgnoreCase(yearFind) == 0) {
                    searchResultList.add(taskList.get(i));
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
    public static Task findTaskByID(String taskID) {
        int taskIDIndex = findTaskIndex(taskID);

        if (taskIDIndex == TAG_TASK_NOT_EXIST) {
            return null;
        } else {
            return taskList.get(taskIDIndex);
        }
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
        int taskIDIndex = TAG_TASK_NOT_EXIST;

        for (int i = 0; i < taskList.size(); i++) {
            if (taskList.get(i).getTaskID().equals(taskID)) {
                taskIDIndex = i;
            }
        }

        return taskIDIndex;
    }
}
