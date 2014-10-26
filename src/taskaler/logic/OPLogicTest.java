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

public class OPLogicTest {

	@Test
	public void test() {
		

		SearchLogic search = new SearchLogic();
		//*********************test addTask********************//
		Task task;
		
		/* This is a boundary case for add nothing */
		task = OPLogic.getInstance().addTask(null, null, null, null);
		assertEquals(null, task);
		assertEquals(0, TaskList.getInstance().size());
		
		/* This is a boundary case for add a task with empty task name */
		task = OPLogic.getInstance().addTask(null, "my_description", null, null);
		assertEquals(null, task);
		assertEquals(0, TaskList.getInstance().size());
		
		/* This is a case for adding a task with one parameter - task name*/
		task = OPLogic.getInstance().addTask("my_test1", null, null, null);
		assertEquals("my_test1", task.getTaskName());
		assertEquals(task, search.findByID(task.getTaskID()));
		assertEquals(1, TaskList.getInstance().size());
		
		/* This is a case for adding a task with two parameters - task name, task description*/
		task = OPLogic.getInstance().addTask("my_test2", "my_description", null, null);
		assertEquals("my_test2", task.getTaskName());
		assertEquals("my_description", task.getTaskDescription());
		assertEquals(task, search.findByID(task.getTaskID()));
		assertEquals(2, TaskList.getInstance().size());
		
		task = OPLogic.getInstance().addTask("my_test3", "my_description", "11/11/2011", null);
		assertEquals("my_test3", task.getTaskName());
		assertEquals("my_description", task.getTaskDescription());
		assertEquals("11/11/2011", calendarToString.parseDate(((DeadLineTask) task).getEndTime()));
		assertEquals(task, search.findByID(task.getTaskID()));
		assertEquals(3, TaskList.getInstance().size());
		
		//*********************test deleteTask********************//
		for(int i = 0; i < TaskList.getInstance().size(); i++) {
			if(TaskList.getInstance().get(i) instanceof DeadLineTask) {
				System.out.println("deadline");
			} else if (TaskList.getInstance().get(i) instanceof FloatTask) {
				System.out.println("float");
			} else {
				System.out.println("repeated");
			}
			System.out.println(TaskList.getInstance().get(i).getTaskID() + " " + TaskList.getInstance().get(i).getTaskName());
		}
		String taskID;
		
		System.out.println("*********delete1************");
		/* This is a boundary case for deleting not existed task */
		taskID = "0";
		task = OPLogic.getInstance().deleteTask(taskID);
		assertEquals(null, task);
		
		System.out.println("*********delete2************");
		/* This is a boundary case for deleting not existed task */
		taskID = "4";
		task = OPLogic.getInstance().deleteTask(taskID);
		assertEquals(null, task);
		
		System.out.println("*********delete3************");
		/* This is a case for deleting successfully*/
		taskID = "1";
		task = OPLogic.getInstance().deleteTask(taskID);
		assertEquals(null, search.findByID(taskID));
		assertEquals(2, TaskList.getInstance().size());
		
		
		//*********************test editTask********************//
		System.out.println("*********1************");
		/* This is a boundary case for editing not existed task */
		taskID = "0";
		task = OPLogic.getInstance().editTask(taskID, "test", "descirption");
		assertEquals(null, task);
		assertEquals(null, search.findByID(taskID));
		assertEquals(2, TaskList.getInstance().size());
		
		System.out.println("*********2************");
		/* This is a boundary case for changing both task name and description to null*/
		taskID = "2";
		task = OPLogic.getInstance().editTask(taskID, null, null);
		assertEquals(null, task);
		assertEquals(2, TaskList.getInstance().size());
		
		System.out.println("*********3************");
		/* This is a case for changing task name successfully*/
		taskID = "2";
		task = OPLogic.getInstance().editTask(taskID, "test", null);
		assertEquals(task.getTaskName(), "test");
		assertEquals(2, TaskList.getInstance().size());
		
		System.out.println("*********4************");
		/* This is a case for changing task description successfully*/
		taskID = "2";
		task = OPLogic.getInstance().editTask(taskID, null, "description");
		assertEquals(task.getTaskDescription(), "description");
		assertEquals(2, TaskList.getInstance().size());
		
		System.out.println("*********5************");
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
		assertEquals("22/10/2014", calendarToString.parseDate(((DeadLineTask) search.findByID(taskID)).getEndTime()));
		task = ((DeadLineTask) search.findByID(taskID));
		
		for(int i = 0; i < TaskList.getInstance().size(); i++) {
			if(TaskList.getInstance().get(i) instanceof DeadLineTask) {
				System.out.println("deadline");
			} else if (TaskList.getInstance().get(i) instanceof FloatTask) {
				System.out.println("float");
			} else {
				System.out.println("repeated");
			}
			System.out.println(TaskList.getInstance().get(i).getTaskID() + " " + TaskList.getInstance().get(i).getTaskName());
		}
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
		
		Task task1 = OPLogic.getInstance().setRepeat(taskID, "weekend", "26/10/2014", "24/11/2014");
		Task task2 = OPLogic.getInstance().setRepeat("3", "weekday", "26/10/2014", "24/11/2014");
		for(int i = 0; i < TaskList.getInstance().size(); i++) {
			if(TaskList.getInstance().get(i) instanceof DeadLineTask) {
				System.out.println("deadline");
			} else if (TaskList.getInstance().get(i) instanceof FloatTask) {
				System.out.println("float");
			} else {
				System.out.println("repeated");
			}
			System.out.println(TaskList.getInstance().get(i).getTaskID() + " " + TaskList.getInstance().get(i).getTaskName());
		}
		ArrayList<Calendar> days = ((RepeatedTask) task1).getRepeatedDate();
		for(int i = 0; i < days.size(); i++) {
			System.out.println(days.get(i).getTime());
		}
		System.out.println("************************");
		days = ((RepeatedTask) task2).getRepeatedDate();
		for(int i = 0; i < days.size(); i++) {
			System.out.println(days.get(i).getTime());
		}
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
