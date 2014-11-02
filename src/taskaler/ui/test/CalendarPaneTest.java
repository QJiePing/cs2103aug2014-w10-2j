/**
 * 
 */
package taskaler.ui.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Calendar;

import org.junit.Rule;
import org.junit.Test;

import taskaler.common.data.DeadLineTask;
import taskaler.common.data.FloatTask;
import taskaler.common.data.RepeatedTask;
import taskaler.common.data.Task;
import taskaler.ui.controller.CalendarPaneController;
import taskaler.ui.model.CalendarPaneModel;

/**
 * Unit Test Class to check the behaviour of Calendar Controller when given
 * malformed models
 * 
 * These test does not test for visual correctness. there is a test driver in
 * the taskaler.ui package to check for visual correctness. This class only
 * checks for any exceptions and errors should a malform model is passed to the
 * controller
 * 
 * @author Cheah Kit Weng, A0059806W
 *
 */
public class CalendarPaneTest {

    private static CalendarPaneController controller = null;

    @Rule
    public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();

    /**
     * Method to generate a task list with no malformed attributes
     * 
     * @return list of tasks
     */
    public ArrayList<Task> generateList() {
        ArrayList<Task> result = new ArrayList<Task>();
        Calendar deadline = Calendar.getInstance();
        deadline.add(Calendar.MONTH, 2);

        FloatTask task1 = new FloatTask("Test on floating", "1", true,
                Calendar.getInstance(), "low", "This is a test floating task",
                Calendar.getInstance(), Calendar.getInstance());

        
        ArrayList<Calendar> listOfTestDays = new ArrayList<Calendar>();
        Calendar eachDate = Calendar.getInstance();
        eachDate.add(Calendar.DATE, 1);
        listOfTestDays.add(eachDate);
        eachDate.add(Calendar.DATE, 1);
        listOfTestDays.add(eachDate);
        eachDate.add(Calendar.DATE, 1);
        listOfTestDays.add(eachDate);
        
        RepeatedTask task2 = new RepeatedTask("Test on repeated task", "2",
                true, Calendar.getInstance(), "High",
                "This is a test repeated task", Calendar.getInstance(),
                deadline, null, listOfTestDays, deadline, 1);

        DeadLineTask task3 = new DeadLineTask("Test on Deadline", "3",
                true, Calendar.getInstance(), "medium",
                "This is a test Deadline task", deadline, deadline, deadline);

        result.add(task1);
        result.add(task2);

        return result;
    }

    /**
     * Generates a general case
     * 
     * @return model with general case
     */
    @Test
    public void testCase1() {
        CalendarPaneModel testCase = new CalendarPaneModel();
        testCase.currentCalendar = Calendar.getInstance();
        testCase.currentTaskList = generateList();
        try {
            controller = new CalendarPaneController(testCase);
            assertTrue("General Case passed", true);
        } catch (Exception err) {
            fail("Failed general case");
        }

    }

    /**
     * Boundary test for the "empty" partition in terms on arraylist
     * 
     * @return model with general case
     */
    @Test
    public void testCase2() {
        CalendarPaneModel testCase = new CalendarPaneModel();
        testCase.currentCalendar = Calendar.getInstance();
        testCase.currentTaskList = new ArrayList<Task>();

        try {
            controller = new CalendarPaneController(testCase);
            assertTrue("Test Case 2 passed", true);
        } catch (Exception err) {
            fail("Failed Test Case 2");
        }
    }

    /**
     * Boundary test for the "null" partition in terms on arraylist
     * 
     * @return model with general case
     */
    @Test
    public void testCase3() {
        CalendarPaneModel testCase = new CalendarPaneModel();
        testCase.currentCalendar = Calendar.getInstance();
        testCase.currentTaskList = null;

        try {
            controller = new CalendarPaneController(testCase);
            assertTrue("Test Case 3 passed", true);
        } catch (Exception err) {
            fail("Failed Test Case 3");
        }
    }

    /**
     * Boundary test for the "null" partition in terms on Calendar
     * 
     * @return model with general case
     */
    @Test
    public void testCase4() {
        CalendarPaneModel testCase = new CalendarPaneModel();
        testCase.currentCalendar = null;
        testCase.currentTaskList = generateList();

        try {
            controller = new CalendarPaneController(testCase);
            assertTrue("Test Case 4 passed", true);
        } catch (Exception err) {
            fail("Failed Test Case 4");
        }
    }

    /**
     * Boundary test for the "zero" partition in terms on Calendar
     * 
     * @return model with general case
     */
    @Test
    public void testCase5() {
        CalendarPaneModel testCase = new CalendarPaneModel();
        testCase.currentCalendar = Calendar.getInstance();
        testCase.currentCalendar.set(0, 0, 0);
        testCase.currentTaskList = generateList();

        try {
            controller = new CalendarPaneController(testCase);
            assertTrue("Test Case 5 passed", true);
        } catch (Exception err) {
            fail("Failed Test Case 5");
        }
    }

    /**
     * Boundary test for the "negative" partition in terms on Calendar
     * 
     * @return model with general case
     */
    @Test
    public void testCase6() {
        CalendarPaneModel testCase = new CalendarPaneModel();
        testCase.currentCalendar = Calendar.getInstance();
        testCase.currentCalendar.set(-1, -1, -1);
        testCase.currentTaskList = generateList();

        try {
            controller = new CalendarPaneController(testCase);
            assertTrue("Test Case 6 passed", true);
        } catch (Exception err) {
            fail("Failed Test Case 6");
        }
    }

    /**
     * Boundary test for the "Max" partition in terms on Calendar
     * 
     * @return model with general case
     */
    @Test
    public void testCase7() {
        CalendarPaneModel testCase = new CalendarPaneModel();
        testCase.currentCalendar = Calendar.getInstance();
        testCase.currentCalendar.set(Integer.MAX_VALUE, Integer.MAX_VALUE,
                Integer.MAX_VALUE);
        testCase.currentTaskList = generateList();

        try {
            controller = new CalendarPaneController(testCase);
            assertTrue("Test Case 7 passed", true);
        } catch (Exception err) {
            fail("Failed Test Case 7");
        }
    }
}
