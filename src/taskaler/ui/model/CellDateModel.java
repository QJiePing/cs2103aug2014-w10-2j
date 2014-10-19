/**
 * 
 */
package taskaler.ui.model;

import java.util.ArrayList;

import taskaler.ui.controller.common;

/**
 * Model Associated with the CellDate
 * 
 * @author Cheah Kit Weng, A0059806W
 *
 */
public class CellDateModel {

    public int currentDate;

    public int currentNumberOfEvents;

    public ArrayList<common.RectangleColor> currentWorkloads;

    /**
     * Default Constructor
     */
    public CellDateModel() {
        currentDate = 1;
        currentNumberOfEvents = 0;
        currentWorkloads = new ArrayList<common.RectangleColor>();
    }
}
