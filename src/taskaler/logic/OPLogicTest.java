package taskaler.logic;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Calendar;

import org.junit.Test;

import taskaler.common.data.DeadLineTask;
import taskaler.common.data.FloatTask;
import taskaler.common.data.RepeatedTask;
import taskaler.common.data.Task;
import taskaler.common.data.TaskList;
import taskaler.common.util.parser.calendarToString;

//@author A0099778X
public class OPLogicTest {

	@Test
	public void test() {
		

		SearchLogic search = new SearchLogic();
		//*********************test addTask********************//
		Task task;
		
		/* This is a boundary case for add nothing */
		task = OPLogic.getInstance().addTask(null, null, null, null, null, null);
		assertEquals(null, task);
		assertEquals(0, TaskList.getInstance().size());
		assertEquals(0, TaskList.getInstance().getNumOfIncomplete());
		
		/* This is a case for adding a task with one parameter - task name*/
		task = OPLogic.getInstance().addTask("my_test1", null, null, null, null, null);
		assertEquals("my_test1", task.getTaskName());
		assertEquals(task, search.findByID(task.getTaskID()));
		assertEquals(1, TaskList.getInstance().size());
		assertEquals(1, TaskList.getInstance().getNumOfIncomplete());
		
		/* This is a case for adding a task with two parameters - task name, task description*/
		task = OPLogic.getInstance().addTask("my_test2", "my_description", null, null, null, null);
		assertEquals("my_test2", task.getTaskName());
		assertEquals("my_description", task.getTaskDescription());
		assertEquals(task, search.findByID(task.getTaskID()));
		assertEquals(2, TaskList.getInstance().size());
		assertEquals(2, TaskList.getInstance().getNumOfIncomplete());
		
		task = OPLogic.getInstance().addTask("my_test3", "my_description", "11/11/2011", null, null, null);
		assertEquals("my_test3", task.getTaskName());
		assertEquals("my_description", task.getTaskDescription());
		assertEquals("11/11/2011", calendarToString.parseDate(((DeadLineTask) task).getDeadline()));
		assertEquals(task, search.findByID(task.getTaskID()));
		assertEquals(3, TaskList.getInstance().size());
		assertEquals(3, TaskList.getInstance().getNumOfIncomplete());
		
		//*********************test deleteTask********************//
		
		/* This is a boundary case for deleting not existed task */
		String taskID = "0";
		task = OPLogic.getInstance().deleteTask(taskID);
		assertEquals(null, task);
		assertEquals(3, TaskList.getInstance().getNumOfIncomplete());
		
		/* This is a boundary case for deleting not existed task */
		taskID = "4";
		task = OPLogic.getInstance().deleteTask(taskID);
		assertEquals(null, task);
		assertEquals(3, TaskList.getInstance().getNumOfIncomplete());
		
		/* This is a case for deleting successfully*/
		taskID = "1";
		task = OPLogic.getInstance().deleteTask(taskID);
		assertEquals(null, search.findByID(taskID));
		assertEquals(2, TaskList.getInstance().size());
		assertEquals(2, TaskList.getInstance().getNumOfIncomplete());
		
		
		//*********************test editTask********************//
		/* This is a boundary case for editing not existed task */
		taskID = "0";
		task = OPLogic.getInstance().editTask(taskID, "test", "descirption");
		assertEquals(null, task);
		assertEquals(null, search.findByID(taskID));
		assertEquals(2, TaskList.getInstance().size());
		assertEquals(2, TaskList.getInstance().getNumOfIncomplete());
		
		/* This is a boundary case for changing both task name and description to null*/
		taskID = "2";
		task = OPLogic.getInstance().editTask(taskID, null, null);
		assertEquals(null, task);
		assertEquals(2, TaskList.getInstance().size());
		assertEquals(2, TaskList.getInstance().getNumOfIncomplete());
		
		/* This is a case for changing task name successfully*/
		taskID = "2";
		task = OPLogic.getInstance().editTask(taskID, "test", null);
		assertEquals(task.getTaskName(), "test");
		assertEquals(2, TaskList.getInstance().size());
		assertEquals(2, TaskList.getInstance().getNumOfIncomplete());
		
		/* This is a case for changing task description successfully*/
		taskID = "2";
		task = OPLogic.getInstance().editTask(taskID, null, "description");
		assertEquals(task.getTaskDescription(), "description");
		assertEquals(2, TaskList.getInstance().size());
		assertEquals(2, TaskList.getInstance().getNumOfIncomplete());
		
		/* This is a case for changing both task name and description successfully*/
		taskID = "2";
		task = OPLogic.getInstance().editTask(taskID, "t", "d");
		assertEquals(task.getTaskName(), "t");
		assertEquals(task.getTaskDescription(), "d");
		assertEquals(2, TaskList.getInstance().size());
		
		
		//*********************test editDate********************//
		
		/* This is a boundary case for changing deadline of not existed task*/
		taskID = "1";
		task = OPLogic.getInstance().editDate(taskID, "22/10/2014");
		assertEquals(null, task);
		
		/* This is a case for changing deadline successfully*/
		taskID = "2";
		task = OPLogic.getInstance().editDate(taskID, "22/10/2014");
		assertEquals("22/10/2014", calendarToString.parseDate(((DeadLineTask) search.findByID(taskID)).getDeadline()));
		
		//*********************test editWorkload********************//
		
		/* This is a boundary case for changing workload of not existed task*/
		taskID = "1";
		task = OPLogic.getInstance().editWorkload(taskID, "2");
		assertEquals(null, task);
		
		/* This is a case for changing workload successfully*/
		taskID = "2";
		assertEquals("0", search.findByID(taskID).getTaskWorkLoad());
		task = OPLogic.getInstance().editWorkload(taskID, "2");
		assertEquals("2", search.findByID(taskID).getTaskWorkLoad());
		
		
		//*********************test setRepeat********************//
		Task task1 = OPLogic.getInstance().setRepeat(taskID, "weekend", "26/10/2014", "24/11/2014");
		Task task2 = OPLogic.getInstance().setRepeat("3", "weekday", "26/10/2014", "24/11/2014");
		assertEquals(true, (task1 instanceof RepeatedTask));
		assertEquals(true, (task2 instanceof RepeatedTask));
	
		//*********************test editStatus********************//
		
		/* This is a boundary case for changing work status of not existed task */
		taskID = "1";
		task = OPLogic.getInstance().switchTag(taskID);
		assertEquals(null, task);
		assertEquals(2, TaskList.getInstance().getNumOfIncomplete());

		/* This is a case for changing work status successfully */
		taskID = "2";
		assertEquals(false, search.findByID(taskID).getTaskStatus());
		task = OPLogic.getInstance().switchTag(taskID);
		assertEquals(true, search.findByID(taskID).getTaskStatus());
		assertEquals(1, TaskList.getInstance().getNumOfIncomplete());
		
		/* This is a case for changing work status back to incomplete successfully */
		taskID = "2";
		assertEquals(true, search.findByID(taskID).getTaskStatus());
		task = OPLogic.getInstance().switchTag(taskID);
		assertEquals(false, search.findByID(taskID).getTaskStatus());
		assertEquals(2, TaskList.getInstance().getNumOfIncomplete());
		
	}

}
