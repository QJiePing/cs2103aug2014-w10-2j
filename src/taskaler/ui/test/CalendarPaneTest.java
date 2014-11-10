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
//@author A0059806W
public class CalendarPaneTest {

    // Test case 7 parameters
    private static final String MSG_FAILED_7TH = "Failed Test Case 7";
    private static final String MSG_PASSED_7TH = "Test Case 7 passed";

    // Test case 6 parameters
    private static final String MSG_FAILED_6TH = "Failed Test Case 6";
    private static final String MSG_PASSED_6TH = "Test Case 6 passed";
    private static final int NEGATIVE_DAY = -1;
    private static final int NEGATIVE_MONTH = -1;
    private static final int NEGATIVE_YEAR = -1;

    // Test case 5 parameters
    private static final String MSG_FAILED_5TH = "Failed Test Case 5";
    private static final String MSG_PASSED_5TH = "Test Case 5 passed";
    private static final int ZERO_DAY = 0;
    private static final int ZERO_MONTH = 0;
    private static final int ZERO_YEAR = 0;

    // Test case 4 parameters
    private static final String MSG_FAILED_4TH = "Failed Test Case 4";
    private static final String MSG_PASSED_4TH = "Test Case 4 passed";

    // Test case 3 parameters
    private static final String MSG_FAILED_3RD = "Failed Test Case 3";
    private static final String MSG_PASSED_3RD = "Test Case 3 passed";

    // Test case 2 parameters
    private static final String MSG_FAILED_2ND = "Failed Test Case 2";
    private static final String MSG_PASSED_2ND = "Test Case 2 passed";

    // General case parameters
    private static final String MSG_FAILED_GENERAL = "Failed general case";
    private static final String MSG_PASSED_GENERAL = "General Case passed";

    // Special test case constants
    private static final String MSG_DEADLINE_TEST = "This is a test Deadline task";
    private static final String WORKLOAD_MEDIUM = "medium";
    private static final String ID_THREE = "3";
    private static final String MSG_TEST_ON_DEADLINE = "Test on Deadline";
    private static final int COLLECTION_ID_ONE = 1;
    private static final String MSG_REPEATED_TEST = "This is a test repeated task";
    private static final String WORKLOAD_HIGH = "High";
    private static final String ID_TWO = "2";
    private static final String MSG_TEST_ON_REPEATED = "Test on repeated task";
    private static final int OFFSET_BY_ONE_DAY = 1;
    private static final String MSG_FLOATING_TEST = "This is a test floating task";
    private static final String WORKLOAD_LOW = "low";
    private static final String ID_ONE = "1";
    private static final String MSG_TEST_ON_FLOATING = "Test on floating";
    private static final int FEBBURARY = 2;

    // Special rule for JavaFX unit test
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
        deadline.add(Calendar.MONTH, FEBBURARY);

        FloatTask task1 = new FloatTask(MSG_TEST_ON_FLOATING, ID_ONE, true,
                Calendar.getInstance(), WORKLOAD_LOW, MSG_FLOATING_TEST,
                Calendar.getInstance(), Calendar.getInstance());

        
        ArrayList<Calendar> listOfTestDays = new ArrayList<Calendar>();
        Calendar eachDate = Calendar.getInstance();
        eachDate.add(Calendar.DATE, OFFSET_BY_ONE_DAY);
        listOfTestDays.add(eachDate);
        eachDate.add(Calendar.DATE, OFFSET_BY_ONE_DAY);
        listOfTestDays.add(eachDate);
        eachDate.add(Calendar.DATE, OFFSET_BY_ONE_DAY);
        listOfTestDays.add(eachDate);
        
        RepeatedTask task2 = new RepeatedTask(MSG_TEST_ON_REPEATED, ID_TWO,
                true, Calendar.getInstance(), WORKLOAD_HIGH,
                MSG_REPEATED_TEST, Calendar.getInstance(),
                deadline, null, listOfTestDays, deadline, COLLECTION_ID_ONE);

        DeadLineTask task3 = new DeadLineTask(MSG_TEST_ON_DEADLINE, ID_THREE,
                true, Calendar.getInstance(), WORKLOAD_MEDIUM,
                MSG_DEADLINE_TEST, deadline, deadline, deadline);

        result.add(task1);
        result.add(task2);
        result.add(task3);

        return result;
    }

    /**
     * Generates a general case
     * 
     */
    @Test
    public void testCase1() {
        CalendarPaneModel testCase = new CalendarPaneModel();
        testCase.currentCalendar = Calendar.getInstance();
        testCase.currentTaskList = generateList();
        try {
            new CalendarPaneController(testCase);
            assertTrue(MSG_PASSED_GENERAL, true);
        } catch (Exception err) {
            fail(MSG_FAILED_GENERAL);
        }

    }

    /**
     * Boundary test for the "empty" partition in terms on arraylist
     * 
     */
    @Test
    public void testCase2() {
        CalendarPaneModel testCase = new CalendarPaneModel();
        testCase.currentCalendar = Calendar.getInstance();
        testCase.currentTaskList = new ArrayList<Task>();

        try {
            new CalendarPaneController(testCase);
            assertTrue(MSG_PASSED_2ND, true);
        } catch (Exception err) {
            fail(MSG_FAILED_2ND);
        }
    }

    /**
     * Boundary test for the "null" partition in terms on arraylist
     * 
     */
    @Test
    public void testCase3() {
        CalendarPaneModel testCase = new CalendarPaneModel();
        testCase.currentCalendar = Calendar.getInstance();
        testCase.currentTaskList = null;

        try {
            new CalendarPaneController(testCase);
            assertTrue(MSG_PASSED_3RD, true);
        } catch (Exception err) {
            fail(MSG_FAILED_3RD);
        }
    }

    /**
     * Boundary test for the "null" partition in terms on Calendar
     * 
     */
    @Test
    public void testCase4() {
        CalendarPaneModel testCase = new CalendarPaneModel();
        testCase.currentCalendar = null;
        testCase.currentTaskList = generateList();

        try {
            new CalendarPaneController(testCase);
            assertTrue(MSG_PASSED_4TH, true);
        } catch (Exception err) {
            fail(MSG_FAILED_4TH);
        }
    }

    /**
     * Boundary test for the "zero" partition in terms on Calendar
     * 
     */
    @Test
    public void testCase5() {
        CalendarPaneModel testCase = new CalendarPaneModel();
        testCase.currentCalendar = Calendar.getInstance();
        testCase.currentCalendar.set(ZERO_YEAR, ZERO_MONTH, ZERO_DAY);
        testCase.currentTaskList = generateList();

        try {
            new CalendarPaneController(testCase);
            assertTrue(MSG_PASSED_5TH, true);
        } catch (Exception err) {
            fail(MSG_FAILED_5TH);
        }
    }

    /**
     * Boundary test for the "negative" partition in terms on Calendar
     * 
     */
    @Test
    public void testCase6() {
        CalendarPaneModel testCase = new CalendarPaneModel();
        testCase.currentCalendar = Calendar.getInstance();
        testCase.currentCalendar.set(NEGATIVE_YEAR, NEGATIVE_MONTH, NEGATIVE_DAY);
        testCase.currentTaskList = generateList();

        try {
            new CalendarPaneController(testCase);
            assertTrue(MSG_PASSED_6TH, true);
        } catch (Exception err) {
            fail(MSG_FAILED_6TH);
        }
    }

    /**
     * Boundary test for the "Max" partition in terms on Calendar
     * 
     */
    @Test
    public void testCase7() {
        CalendarPaneModel testCase = new CalendarPaneModel();
        testCase.currentCalendar = Calendar.getInstance();
        testCase.currentCalendar.set(Integer.MAX_VALUE, Integer.MAX_VALUE,
                Integer.MAX_VALUE);
        testCase.currentTaskList = generateList();

        try {
            new CalendarPaneController(testCase);
            assertTrue(MSG_PASSED_7TH, true);
        } catch (Exception err) {
            fail(MSG_FAILED_7TH);
        }
    }
    }
