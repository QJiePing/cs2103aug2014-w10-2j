/**
 * 
 */
package taskaler.ui.model;

import java.util.HashMap;

import taskaler.common.data.Task;
import taskaler.ui.controller.common;

/**
 * Model Associated with the TaskPane
 * 
 * @author Cheah Kit Weng, A0059806W
 *
 */
public class TaskPaneModel implements IModel {

    // State Attributes
    public static final String TASK_DESCRIPTION_ATTRIBUTE = "TASKDESCRIPTION";
    public static final String TASK_WORKLOAD_ATTRIBUTE = "TASKWORKLOAD";
    public static final String TASK_DUE_DATE_ATTRIBUTE = "TASKDUEDATE";
    public static final String TASK_STATUS_ATTRIBUTE = "TASKSTATUS";
    public static final String TASK_ID_ATTRIBUTE = "TASKID";
    public static final String TASK_TYPE_ATTRIBUTE = "TASKTYPE";
    public static final String TASK_NAME_ATTRIBUTE = "TASKNAME";
    public static final String TASK_START_TIME_ATTRIBUTE = "TASKSTARTTIME";
    public static final String TASK_END_TIME_ATTRIBUTE = "TASKENDTIME";
    public static final String NOT_DONE_VALUE = "NOT DONE";
    public static final String DONE_VALUE = "DONE";

    public String taskName;
    public String taskID;
    public boolean taskStatus;
    public String taskDate;
    public int taskWorkload;
    public String taskDescription;
    public String taskPattern;
    public String taskStartTime;
    public String taskEndTime;
    public Task task;

    /**
     * Default constructor
     * 
     */
    public TaskPaneModel() {
        taskName = "";
        taskID = "";
        taskStatus = true;
        taskDate = "";
        taskWorkload = common.RECTANGLE_COLOR_GREY;
        taskDescription = "None";
        taskPattern = "";
        taskStartTime = "Not Set";
        taskEndTime = "Not Set";
        task = null;
    }

    @Override
    public HashMap<String, String> toHashMap() {
        HashMap<String, String> result = new HashMap<String, String>();
        result.put(VIEW_ATTRIBUTE, VIEW_TASK_PANE);
        result.put(TASK_NAME_ATTRIBUTE, taskName);
        result.put(TASK_ID_ATTRIBUTE, taskID);
        if (taskStatus) {
            result.put(TASK_STATUS_ATTRIBUTE, DONE_VALUE);
        } else {
            result.put(TASK_STATUS_ATTRIBUTE, NOT_DONE_VALUE);
        }
        result.put(TASK_DUE_DATE_ATTRIBUTE, taskDate);
        result.put(TASK_WORKLOAD_ATTRIBUTE, taskWorkload + common.EMPTY_STRING);
        result.put(TASK_DESCRIPTION_ATTRIBUTE, taskDescription);
        result.put(TASK_START_TIME_ATTRIBUTE, taskStartTime);
        result.put(TASK_END_TIME_ATTRIBUTE, taskEndTime);
        result.put(TASK_TYPE_ATTRIBUTE, task.getClass().getCanonicalName());
        return result;
    }

}
