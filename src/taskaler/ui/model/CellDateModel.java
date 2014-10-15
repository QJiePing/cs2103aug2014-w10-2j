/**
 * 
 */
package taskaler.ui.model;

import java.util.ArrayList;

import taskaler.common.enumerate.RECTANGLE_COLOR;

/**
 * Model Associated with the CellDate
 * 
 * @author Cheah Kit Weng, A0059806W
 *
 */
public class CellDateModel extends AbstractModel{
    
    public int currentDate;
    
    public int currentNumberOfEvents;
    
    public ArrayList<RECTANGLE_COLOR> currentWorkloads;
    
    /**
     * Default Constructor
     */
    public CellDateModel(){
        currentDate = 1;
        currentNumberOfEvents = 0;
        currentWorkloads = new ArrayList<RECTANGLE_COLOR>();
    }
}
