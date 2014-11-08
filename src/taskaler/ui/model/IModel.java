/**
 * 
 */
package taskaler.ui.model;

import java.util.HashMap;

/**
 * Interface to be inherited by models
 * 
 * @author Cheah Kit Weng, A0059806W
 *
 */
public interface IModel {
    public static final String VIEW_ATTRIBUTE ="VIEW";
    public static final String VIEW_CALENDAR_PANE ="CalendarPane";
    public static final String VIEW_CELL_DATE ="CalendarPane";
    public static final String VIEW_LIST_PANE ="ListPane";
    public static final String VIEW_ROOT ="Root";
    public static final String VIEW_TASK_PANE ="TaskPane";
    public static final String VIEW_TEXT_PANE ="TextPane";
    public static final String VIEW_TUTORIAL_PANE ="TutorialPane";
    public abstract HashMap<String, String> toHashMap();
}
