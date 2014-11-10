/**
 * 
 */
package taskaler.ui.model;

import java.util.ArrayList;
import java.util.HashMap;

import taskaler.common.data.Task;

/**
 * Model Associated with the ListPane
 * 
 * @author Cheah Kit Weng, A0059806W
 *
 */
//@author A0059806W
public class ListPaneModel implements IModel{

    // State Attributes
    public static final String CURRENT_ITEM_LIST_ATTRIBUTE = "CURRENTITEMLIST";
    public static final String CURRENT_TITLE_ATTRIBUTE = "CURRENTTITLE";

    public String currentTitle;

    public ArrayList<Task> currentItemList;
    
    public ArrayList<String> currentSubHeaders;
    
    public ArrayList<ArrayList<Task>> arrayOfTaskLists;

    public ListPaneModel() {
        currentTitle = "";
        currentItemList = new ArrayList<Task>();
        currentSubHeaders = new ArrayList<String>();
        arrayOfTaskLists = new ArrayList<ArrayList<Task>>();
    }

    @Override
    public HashMap<String, String> toHashMap() {
        HashMap<String, String> result = new HashMap<String, String>();
        result.put(VIEW_ATTRIBUTE, VIEW_LIST_PANE);
        result.put(CURRENT_TITLE_ATTRIBUTE, currentTitle);
        result.put(CURRENT_ITEM_LIST_ATTRIBUTE, currentItemList.toString());
        return result;
    }
}
