package taskaler.logic;

import static org.junit.Assert.*;

import org.junit.Test;

import taskaler.common.data.Task;
import taskaler.common.data.TaskList;
import taskaler.common.util.parser.calendarToString;

public class OPLogicTest {

	@Test
	public void test() {
		

		SearchLogic search = new SearchLogic();
		//*********************test addTask********************//
		Task task;
		
		/* This is a boundary case for add nothing */
		task = OPLogic.getInstance().addTask(null, null);
		assertEquals(null, task);
		assertEquals(0, TaskList.getInstance().size());
		
		/* This is a boundary case for add a task with empty task name */
		task = OPLogic.getInstance().addTask(null, "my_description");
		assertEquals(null, task);
		assertEquals(0, TaskList.getInstance().size());
		
		/* This is a case for adding a task with one parameter - task name*/
		task = OPLogic.getInstance().addTask("my_test", null);
		assertEquals("my_test", task.getTaskName());
		assertEquals(task, search.findByID(task.getTaskID()));
		assertEquals(1, TaskList.getInstance().size());
		
		/* This is a case for adding a task with two parameters - task name, task description*/
		task = OPLogic.getInstance().addTask("my_test", "my_description");
		assertEquals("my_test", task.getTaskName());
		assertEquals("my_description", task.getTaskDescription());
		assertEquals(task, search.findByID(task.getTaskID()));
		assertEquals(2, TaskList.getInstance().size());
		
		
		//*********************test deleteTask********************//
		String taskID;
		
		/* This is a boundary case for deleting not existed task */
		taskID = "0";
		task = OPLogic.getInstance().deleteTask(taskID);
		assertEquals(null, task);
		
		/* This is a boundary case for deleting not existed task */
		taskID = "3";
		task = OPLogic.getInstance().deleteTask(taskID);
		assertEquals(null, task);
		
		/* This is a case for deleting successfully*/
		taskID = "1";
		task = OPLogic.getInstance().deleteTask(taskID);
		assertEquals(null, search.findByID(taskID));
		assertEquals(1, TaskList.getInstance().size());
		
		
		//*********************test editTask********************//
		
		/* This is a boundary case for editing not existed task */
		taskID = "0";
		task = OPLogic.getInstance().editTask(taskID, "test", "descirption");
		assertEquals(null, task);
		assertEquals(null, search.findByID(taskID));
		assertEquals(1, TaskList.getInstance().size());
		
		/* This is a boundary case for changing both task name and description to null*/
		taskID = "2";
		task = OPLogic.getInstance().editTask(taskID, null, null);
		assertEquals(null, task);
		assertEquals(1, TaskList.getInstance().size());
		
		/* This is a case for changing task name successfully*/
		taskID = "2";
		task = OPLogic.getInstance().editTask(taskID, "test", null);
		assertEquals(task.getTaskName(), "test");
		assertEquals(1, TaskList.getInstance().size());
		
		/* This is a case for changing task description successfully*/
		taskID = "2";
		task = OPLogic.getInstance().editTask(taskID, null, "description");
		assertEquals(task.getTaskDescription(), "description");
		assertEquals(1, TaskList.getInstance().size());
		
		/* This is a case for changing both task name and description successfully*/
		taskID = "2";
		task = OPLogic.getInstance().editTask(taskID, "t", "d");
		assertEquals(task.getTaskName(), "t");
		assertEquals(task.getTaskDescription(), "d");
		assertEquals(1, TaskList.getInstance().size());
		
		
		//*********************test editDate********************//
		
		/* This is a boundary case for changing deadline of not existed task*/
		taskID = "1";
		task = OPLogic.getInstance().editDate(taskID, "22/10/2014");
		assertEquals(null, task);
		
		/* This is a case for changing deadline successfully*/
		taskID = "2";
		assertEquals("21/10/2014", calendarToString.parseDate(search.findByID(taskID).getTaskDeadLine()));
		task = OPLogic.getInstance().editDate(taskID, "22/10/2014");
		assertEquals("22/10/2014", calendarToString.parseDate(search.findByID(taskID).getTaskDeadLine()));
		
		
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
		
		
		//*********************test editWorkload********************//
		
		/* This is a boundary case for changing work status of not existed task */
		taskID = "1";
		task = OPLogic.getInstance().switchTag(taskID);
		assertEquals(null, task);

		/* This is a case for changing work status successfully */
		taskID = "2";
		assertEquals("Not Done", search.findByID(taskID).getTaskStatus());
		task = OPLogic.getInstance().switchTag(taskID);
		assertEquals("Done", search.findByID(taskID).getTaskStatus());
		
	}

}
