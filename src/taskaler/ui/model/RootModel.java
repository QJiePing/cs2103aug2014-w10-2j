/**
 * 
 */
package taskaler.ui.model;

import java.util.HashMap;

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

    public String notification;
    
    public String input;
    
    public RootModel(){
        notification = "";
        input = "";
    }

    @Override
    public HashMap<String, String> toHashMap() {
        HashMap<String, String> result = new HashMap<String, String>();
        result.put(VIEW_ATTRIBUTE, VIEW_ROOT);
        result.put(NOTIFICATION_ATTRIBUTE, notification);
        result.put(INPUT_ATTRIBUTE, input);
        return null;
    }
}
