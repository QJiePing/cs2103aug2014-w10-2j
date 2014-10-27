/**
 * 
 */
package taskaler.ui.model;

import java.util.HashMap;

import taskaler.ui.controller.common;

/**
 * Model Associated with the Root
 * 
 * @author Cheah Kit Weng, A0059806W
 *
 */
public class RootModel implements IModel{

    // State Attributes
    public static final String INPUT_ATTRIBUTE = "INPUT";
    public static final String NOTIFICATION_ATTRIBUTE = "NOTIFICATION";
    public static final String TOTAL_NOT_DONE_ATTRIBUTE = "TOTALNOTDONE";
    public static final String TOTAL_FLOATING_ATTRIBUTE = "TOTALFLOATING";

    public String notification;
    
    public String input;
    
    public int totalNotDone;
    
    public int totalFloating;
    
    public RootModel(){
        notification = "";
        input = "";
        totalNotDone = 0;
        totalFloating = 0;
    }

    @Override
    public HashMap<String, String> toHashMap() {
        HashMap<String, String> result = new HashMap<String, String>();
        result.put(VIEW_ATTRIBUTE, VIEW_ROOT);
        result.put(NOTIFICATION_ATTRIBUTE, notification);
        result.put(INPUT_ATTRIBUTE, input);
        result.put(TOTAL_NOT_DONE_ATTRIBUTE, totalNotDone + common.EMPTY_STRING);
        result.put(TOTAL_FLOATING_ATTRIBUTE, totalFloating + common.EMPTY_STRING);
        return null;
    }
}
