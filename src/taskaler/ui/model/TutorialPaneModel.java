/**
 * 
 */
package taskaler.ui.model;

import java.util.HashMap;

import taskaler.ui.controller.common;

/**
 * Model Associated with the TutorialPane
 * 
 * @author Cheah Kit Weng, A0059806W
 *
 */
//@author A0059806W
public class TutorialPaneModel implements IModel {

    // Special Constants
    private static final String PAGE_ATTRIBUTE = "PAGE";
    
    // Class variables
    public int page = common.ZERO_INDEX;
    
    public TutorialPaneModel(){
        page = common.ZERO_INDEX;
    }
    @Override
    public HashMap<String, String> toHashMap() {
        HashMap<String, String> result = new HashMap<String, String>();
        result.put(VIEW_ATTRIBUTE, VIEW_TUTORIAL_PANE);
        result.put(PAGE_ATTRIBUTE, page + common.EMPTY_STRING);
        return result;
    }

}
