package taskaler.test.units;

import static org.junit.Assert.*;

import org.junit.Test;

import taskaler.common.data.DeadLineTask;
import taskaler.common.data.RepeatedTask;
import taskaler.common.data.Task;
import taskaler.common.data.TaskList;
import taskaler.common.util.parser.calendarToString;
import taskaler.logic.OPLogic;
import taskaler.logic.SearchLogic;

//@author A0099778X
public class OPLogicTest {
	
	public static int DEFAULT_TASK_SIZE = 0;
	public static int TASK_SIZE_ONE = 1;
	public static int TASK_SIZE_TWO = 2;
	public static int TASK_SIZE_THREE = 3;
	public static int DEFAULT_NUM_INCOMPLETED = 0;
	public static int NUM_INCOMPLETED_ONE = 1;
	public static int NUM_INCOMPLETED_TWO = 2;
	public static int NUM_INCOMPLETED_THREE = 3;
	public static String TEST_FILE_NAME1 = "my_test1";
	public static String TEST_FILE_NAME2 = "my_test2";
	public static String TEST_FILE_NAME3 = "my_test3";
	public static String TEST_FILE_NAME4 = "test";
	public static String TEST_FILE_NAME5 = "t";
	public static String TEST_DESCRIPTION1 = "my_description";
	public static String TEST_DESCRIPTION2 = "description";
	public static String TEST_DESCRIPTION3 = "d";
	public static String TEST_DATE1 = "11/11/2011";
	public static String TEST_DATE2 = "22/10/2011";
	public static String TEST_DATE3 = "26/10/2014";
	public static String TEST_DATE4 = "24/11/2014";
	
	
	public static String TEST_TASK_ID0 = "0";
	public static String TEST_TASK_ID1 = "1";
	public static String TEST_TASK_ID2 = "2";
	public static String TEST_TASK_ID3 = "3";
	public static String TEST_TASK_ID4 = "4";
	
	public static String TEST_WORKLOAD0 = "0";
	public static String TEST_WORKLOAD1 = "1";
	public static String TEST_WORKLOAD2 = "2";
	public static String TEST_WORKLOAD3 = "3";
	
	public static String TEST_PATTERN_WEEKDAY = "weekday";
	public static String TEST_PATTERN_WEEKEND = "weekend";
	
	@Test
	public void test() {
		
		

		SearchLogic search = new SearchLogic();
		//*********************test addTask********************//
		Task task;
		
		/* This is a boundary case for add nothing */
		task = OPLogic.getInstance().addTask(null, null, null, null, null, null);
		assertEquals(null, task);
		assertEquals(DEFAULT_TASK_SIZE, TaskList.getInstance().size());
		assertEquals(DEFAULT_NUM_INCOMPLETED, TaskList.getInstance().getNumOfIncomplete());
		
		/* This is a case for adding a task with one parameter - task name*/
		task = OPLogic.getInstance().addTask(TEST_FILE_NAME1, null, null, null, null, null);
		assertEquals(TEST_FILE_NAME1, task.getTaskName());
		assertEquals(task, search.findByID(task.getTaskID()));
		assertEquals(TASK_SIZE_ONE, TaskList.getInstance().size());
		assertEquals(NUM_INCOMPLETED_ONE, TaskList.getInstance().getNumOfIncomplete());
		
		/* This is a case for adding a task with two parameters - task name, task description*/
		task = OPLogic.getInstance().addTask(TEST_FILE_NAME2, TEST_DESCRIPTION1, null, null, null, null);
		assertEquals(TEST_FILE_NAME2, task.getTaskName());
		assertEquals(TEST_DESCRIPTION1, task.getTaskDescription());
		assertEquals(task, search.findByID(task.getTaskID()));
		assertEquals(TASK_SIZE_TWO, TaskList.getInstance().size());
		assertEquals(NUM_INCOMPLETED_TWO, TaskList.getInstance().getNumOfIncomplete());
		
		task = OPLogic.getInstance().addTask(TEST_FILE_NAME3, TEST_DESCRIPTION1, TEST_DATE1, null, null, null);
		assertEquals(TEST_FILE_NAME3, task.getTaskName());
		assertEquals(TEST_DESCRIPTION1, task.getTaskDescription());
		assertEquals(TEST_DATE1, calendarToString.parseDate(((DeadLineTask) task).getDeadline()));
		assertEquals(task, search.findByID(task.getTaskID()));
		assertEquals(TASK_SIZE_THREE, TaskList.getInstance().size());
		assertEquals(NUM_INCOMPLETED_THREE, TaskList.getInstance().getNumOfIncomplete());
		
		//*********************test deleteTask********************//
		
		/* This is a boundary case for deleting not existed task */
		String taskID = TEST_TASK_ID0;
		task = OPLogic.getInstance().deleteTask(taskID);
		assertEquals(null, task);
		assertEquals(NUM_INCOMPLETED_THREE, TaskList.getInstance().getNumOfIncomplete());
		
		/* This is a boundary case for deleting not existed task */
		taskID = TEST_TASK_ID4;
		task = OPLogic.getInstance().deleteTask(taskID);
		assertEquals(null, task);
		assertEquals(NUM_INCOMPLETED_THREE, TaskList.getInstance().getNumOfIncomplete());
		
		/* This is a case for deleting successfully*/
		taskID = TEST_TASK_ID1;
		task = OPLogic.getInstance().deleteTask(taskID);
		assertEquals(null, search.findByID(taskID));
		assertEquals(TASK_SIZE_TWO, TaskList.getInstance().size());
		assertEquals(NUM_INCOMPLETED_TWO, TaskList.getInstance().getNumOfIncomplete());
		
		
		//*********************test editTask********************//
		/* This is a boundary case for editing not existed task */
		taskID = TEST_TASK_ID0;
		task = OPLogic.getInstance().editTask(taskID, TEST_FILE_NAME4, TEST_DESCRIPTION2);
		assertEquals(null, task);
		assertEquals(null, search.findByID(taskID));
		assertEquals(TASK_SIZE_TWO, TaskList.getInstance().size());
		assertEquals(NUM_INCOMPLETED_TWO, TaskList.getInstance().getNumOfIncomplete());
		
		/* This is a boundary case for changing both task name and description to null*/
		taskID = TEST_TASK_ID2;
		task = OPLogic.getInstance().editTask(taskID, null, null);
		assertEquals(null, task);
		assertEquals(TASK_SIZE_TWO, TaskList.getInstance().size());
		assertEquals(NUM_INCOMPLETED_TWO, TaskList.getInstance().getNumOfIncomplete());
		
		/* This is a case for changing task name successfully*/
		taskID = TEST_TASK_ID2;
		task = OPLogic.getInstance().editTask(taskID, TEST_FILE_NAME4, null);
		assertEquals(task.getTaskName(), TEST_FILE_NAME4);
		assertEquals(TASK_SIZE_TWO, TaskList.getInstance().size());
		assertEquals(NUM_INCOMPLETED_TWO, TaskList.getInstance().getNumOfIncomplete());
		
		/* This is a case for changing task description successfully*/
		taskID = TEST_TASK_ID2;
		task = OPLogic.getInstance().editTask(taskID, null, TEST_DESCRIPTION2);
		assertEquals(task.getTaskDescription(), TEST_DESCRIPTION2);
		assertEquals(TASK_SIZE_TWO, TaskList.getInstance().size());
		assertEquals(NUM_INCOMPLETED_TWO, TaskList.getInstance().getNumOfIncomplete());
		
		/* This is a case for changing both task name and description successfully*/
		taskID = TEST_TASK_ID2;
		task = OPLogic.getInstance().editTask(taskID, TEST_FILE_NAME5, TEST_DESCRIPTION3);
		assertEquals(task.getTaskName(), TEST_FILE_NAME5);
		assertEquals(task.getTaskDescription(), TEST_DESCRIPTION3);
		assertEquals(NUM_INCOMPLETED_TWO, TaskList.getInstance().size());
		
		
		//*********************test editDate********************//
		
		/* This is a boundary case for changing deadline of not existed task*/
		taskID = TEST_TASK_ID1;
		task = OPLogic.getInstance().editDate(taskID, TEST_DATE2);
		assertEquals(null, task);
		
		/* This is a case for changing deadline successfully*/
		taskID = TEST_TASK_ID2;
		task = OPLogic.getInstance().editDate(taskID, TEST_DATE2);
		assertEquals(TEST_DATE2, calendarToString.parseDate(((DeadLineTask) search.findByID(taskID)).getDeadline()));
		
		//*********************test editWorkload********************//
		
		/* This is a boundary case for changing workload of not existed task*/
		taskID = TEST_TASK_ID1;
		task = OPLogic.getInstance().editWorkload(taskID, TEST_WORKLOAD2);
		assertEquals(null, task);
		
		/* This is a case for changing workload successfully*/
		taskID = TEST_TASK_ID2;
		assertEquals(TEST_WORKLOAD0, search.findByID(taskID).getTaskWorkLoad());
		task = OPLogic.getInstance().editWorkload(taskID, TEST_WORKLOAD2);
		assertEquals(TEST_WORKLOAD2, search.findByID(taskID).getTaskWorkLoad());
		
		
		//*********************test setRepeat********************//
		Task task1 = OPLogic.getInstance().setRepeat(taskID, TEST_PATTERN_WEEKEND, TEST_DATE3, TEST_DATE4);
		Task task2 = OPLogic.getInstance().setRepeat(TEST_TASK_ID3, TEST_PATTERN_WEEKDAY, TEST_DATE3, TEST_DATE4);
		assertEquals(true, (task1 instanceof RepeatedTask));
		assertEquals(true, (task2 instanceof RepeatedTask));
	
		//*********************test editStatus********************//
		
		/* This is a boundary case for changing work status of not existed task */
		taskID = TEST_TASK_ID1;
		task = OPLogic.getInstance().switchTag(taskID);
		assertEquals(null, task);
		assertEquals(NUM_INCOMPLETED_TWO, TaskList.getInstance().getNumOfIncomplete());

		/* This is a case for changing work status successfully */
		taskID = TEST_TASK_ID2;
		assertEquals(false, search.findByID(taskID).getTaskStatus());
		task = OPLogic.getInstance().switchTag(taskID);
		assertEquals(true, search.findByID(taskID).getTaskStatus());
		assertEquals(NUM_INCOMPLETED_ONE, TaskList.getInstance().getNumOfIncomplete());
		
		/* This is a case for changing work status back to incomplete successfully */
		taskID = TEST_TASK_ID2;
		assertEquals(true, search.findByID(taskID).getTaskStatus());
		task = OPLogic.getInstance().switchTag(taskID);
		assertEquals(false, search.findByID(taskID).getTaskStatus());
		assertEquals(NUM_INCOMPLETED_TWO, TaskList.getInstance().getNumOfIncomplete());
		
	}

}
