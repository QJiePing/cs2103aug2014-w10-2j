/**
 * 
 */
package taskaler.ui.model;

import taskaler.common.enumerate.RECTANGLE_COLOR;

/**
 * Model Associated with the TaskPane
 * 
 * @author Cheah Kit Weng, A0059806W
 *
 */
public class TaskPaneModel extends AbstractModel{
    
    public String taskName;
    
    public String taskID;
    
    public String taskStatus;
    
    public String taskDueDate;
    
    public RECTANGLE_COLOR taskWorkload;
    
    public String taskDescription;
    
    /**
     * Default constructor
     * 
     */
    public TaskPaneModel(){
        taskName = "";
        taskID = "";
        taskStatus = "";
        taskDueDate = "";
        taskWorkload = RECTANGLE_COLOR.GREY;
        taskDescription = "None";
    }
}
