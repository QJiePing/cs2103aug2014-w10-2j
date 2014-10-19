/**
 * 
 */
package taskaler.ui.controller;

/**
 * @author Kiwi
 *
 */
public final class common {

    public enum RectangleColor {
        GREY, GREEN, ORANGE, RED
    }
    
    public static final String FXML_ROOT        = "/taskaler/ui/view/RootView.fxml";
    public static final String FXML_CALENDAR    = "/taskaler/ui/view/CalendarPaneView.fxml";
    public static final String FXML_CELL_DATE   = "/taskaler/ui/view/CellDateView.fxml";
    public static final String FXML_TASK_PANE   = "/taskaler/ui/view/TaskPaneView.fxml";
    public static final String FXML_LIST_PANE   = "/taskaler/ui/view/ListPaneView.fxml";
    
    // Global Constants
    public static final int ZERO_INDEX      = 0;
    public static final int OFFSET_BY_ONE   = 1;
    public static final String EMPTY_STRING = "";
    public static final String PLUS_STRING  = "+";
}
