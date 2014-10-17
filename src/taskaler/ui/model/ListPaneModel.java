/**
 * 
 */
package taskaler.ui.model;

import java.util.ArrayList;

import taskaler.common.data.Task;

/**
 * Model Associated with the ListPane
 * 
 * @author Cheah Kit Weng, A0059806W
 *
 */
public class ListPaneModel {

    public String currentTitle;

    public ArrayList<Task> currentItemList;

    public ListPaneModel() {
        currentTitle = "";
        currentItemList = new ArrayList<Task>();
    }
}
