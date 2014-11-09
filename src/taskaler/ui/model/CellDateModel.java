/**
 * 
 */
package taskaler.ui.model;

import java.util.HashMap;

import taskaler.ui.controller.common;

/**
 * Model Associated with the CellDate
 * 
 * @author Cheah Kit Weng, A0059806W
 *
 */
//@author A0059806W
public class CellDateModel implements IModel{

    // State Attributes
    public static final String CURRENT_WORKLOAD_FILTERS_ATTRIBUTE = "CURRENTWORKLOADFILTERS";
    public static final String CURRENT_NUMBER_OF_EVENTS_ATTRIBUTE = "CURRENTNUMBEROFEVENTS";
    public static final String CURRENT_DATE_ATTRIBUTE = "CURRENTDATE";

    public int currentDate;

    public int currentNumberOfEvents;

    public int currentWorkloadFilters;

    /**
     * Default Constructor
     */
    public CellDateModel() {
        currentDate = 1;
        currentNumberOfEvents = 0;
        currentWorkloadFilters = common.RECTANGLE_COLOR_GREY;
    }

    @Override
    public HashMap<String, String> toHashMap() {
        HashMap<String, String> result = new HashMap<String, String>();
        result.put(VIEW_ATTRIBUTE, VIEW_CELL_DATE);
        result.put(CURRENT_DATE_ATTRIBUTE, currentDate + common.EMPTY_STRING);
        result.put(CURRENT_NUMBER_OF_EVENTS_ATTRIBUTE, currentNumberOfEvents + common.EMPTY_STRING);
        result.put(CURRENT_WORKLOAD_FILTERS_ATTRIBUTE, currentWorkloadFilters + common.EMPTY_STRING);
        return result;
    }
}
