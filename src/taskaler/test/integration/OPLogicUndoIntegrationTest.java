package taskaler.test.integration;

import static org.junit.Assert.*;
import org.junit.Test;
import taskaler.archive.Undo;
import taskaler.common.data.Task;
import taskaler.logic.OPLogic;
import taskaler.logic.SearchLogic;

//@author A0111798X
public class OPLogicUndoIntegrationTest {

	/**
	 * Test undo method in the Undo class interacting with OPLogic class
	 * Equivalence Partition: no tasks in the undo stack, more than 1 task in 
	 * the undo stack
	 */
	
	/**
	 * Equivalence Partition: no task in the undo stack.(no command is executed)
	 */
	@Test
	public void test() {
		boolean switch1=false;
		Undo undoObj=new Undo();
		Task result=undoObj.undo();
		if(result==null){
			switch1=true;
		}
		assertTrue(switch1);
	}
	
	/**
	 * Test case: User executes two add commands, a delete command and lastly a undo command.
	 * Equivalence Partition: more than 1 task in the undo stack
	 */
	@Test
	public void test2(){
		boolean switch1=false;
		Undo undoObj=new Undo();
		OPLogic logic= OPLogic.getInstance();
		logic.addObserver(undoObj);
		Task temp1=logic.addTask("Task 1", "Test task 1", "06/11/2014", "09:00", "23:59", "2");
		Task temp2=logic.addTask("Task 2", "Test task 2", "07/11/2014", "19:00", "22:59", "1");
		logic.deleteTask(temp1.getTaskID());
		Task result=undoObj.undo();
		if(result.getTaskName().equals("Task 1")){
			switch1=true;
		}
		assertTrue(switch1);
	}
	/**
	 * Test case: Users executes one add command, a edit command and lastly a undo command.
	 * Equivalence Partition: 1 task in the undo stack
	 */
	@Test
	public void test3(){
		boolean switch1=false;
		Undo undoObj=new Undo();
		OPLogic logic= OPLogic.getInstance();
		logic.addObserver(undoObj);
		Task temp1=logic.addTask("Task 3", "Test task 3", "07/11/2014", "19:00", "22:59", "3");
		logic.editTask(temp1.getTaskID(), "Edited task name", "Edited task 1.");
		undoObj.undo();
		SearchLogic search=new SearchLogic();
		Task result=search.findByID(temp1.getTaskID());
		if(result.getTaskName().equals("Task 3")){
			switch1=true;
		}
		assertTrue(switch1);
	}
}
