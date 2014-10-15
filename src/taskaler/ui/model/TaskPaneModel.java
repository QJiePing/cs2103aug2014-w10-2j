/**
 * 
 */
package taskaler.ui.model;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import taskaler.common.enumerate.RECTANGLE_COLOR;

/**
 * Model Associated with the TaskPane
 * 
 * @author Cheah Kit Weng, A0059806W
 *
 */
public class TaskPaneModel {
    
    // Special constants
    private static final String REG_MONTH_YEAR = "dd/MM/yyyy";
    
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
    
    
    /**
     * Custom calendar parser
     * 
     * @param c Calendar object to be parsed into string
     * @return 
     */
    public static String parseDate(Calendar c){
        SimpleDateFormat formatter = new SimpleDateFormat(REG_MONTH_YEAR);
        String deadline = formatter.format(c.getTime());
        
        return deadline;
    }
}
