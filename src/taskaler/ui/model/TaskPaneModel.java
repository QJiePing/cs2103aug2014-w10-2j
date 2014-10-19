/**
 * 
 */
package taskaler.ui.model;
import taskaler.ui.controller.common;

/**
 * Model Associated with the TaskPane
 * 
 * @author Cheah Kit Weng, A0059806W
 *
 */
public class TaskPaneModel {
    
    public String taskName;
    
    public String taskID;
    
    public String taskStatus;
    
    public String taskDueDate;
    
    public common.RectangleColor taskWorkload;
    
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
        taskWorkload = common.RectangleColor.GREY;
        taskDescription = "None";
    }
}
