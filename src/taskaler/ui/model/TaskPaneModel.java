/**
 * 
 */
package taskaler.ui.model;

import java.util.HashMap;

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
    public static final String TASK_NAME_ATTRIBUTE = "TASKNAME";
    public static final String NOT_DONE_VALUE = "NOT DONE";
    public static final String DONE_VALUE = "DONE";

    public String taskName;
    public String taskID;
    public boolean taskStatus;
    public String taskDueDate;
    public int taskWorkload;
    public String taskDescription;

    /**
     * Default constructor
     * 
     */
    public TaskPaneModel() {
        taskName = "";
        taskID = "";
        taskStatus = true;
        taskDueDate = "";
        taskWorkload = common.RECTANGLE_COLOR_GREY;
        taskDescription = "None";
    }

    @Override
    public HashMap<String, String> toHashMap() {
        HashMap<String, String> result = new HashMap<String, String>();
        result.put(VIEW_ATTRIBUTE, VIEW_TASK_PANE);
        result.put(TASK_NAME_ATTRIBUTE, taskName);
        result.put(TASK_ID_ATTRIBUTE, taskID);
        if(taskStatus){
            result.put(TASK_STATUS_ATTRIBUTE, DONE_VALUE);
        }else{
            result.put(TASK_STATUS_ATTRIBUTE, NOT_DONE_VALUE);
        }
        result.put(TASK_DUE_DATE_ATTRIBUTE, taskDueDate);
        result.put(TASK_WORKLOAD_ATTRIBUTE, taskWorkload + common.EMPTY_STRING);
        result.put(TASK_DESCRIPTION_ATTRIBUTE, taskDescription);
        return result;
    }

}
