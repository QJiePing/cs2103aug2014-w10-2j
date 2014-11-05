package taskaler.testUnits;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Calendar;

import org.junit.AfterClass;
import org.junit.Test;

import taskaler.archive.OperationRecord;
import taskaler.archive.Undo;
import taskaler.common.data.Task;
import taskaler.common.data.DeadLineTask;
import taskaler.common.data.FloatTask;
import taskaler.common.data.RepeatedTask;

//@author A0111798X

public class UndoTestUnit {

	/**
	 * Equivalence partition: empty stack, not empty stack (more than 1
	 * operationRecord object)
	 */

	/**
	 * Testing viewUndo function of the Undo class 
	 * Equivalence partition:Stack<OperationRecord<Task,String>> is empty
	 */
	@Test
	public void test1() {
		Undo undoObj = new Undo();
		ArrayList<OperationRecord<Task, String>> result = undoObj.viewUndo();
		assertTrue(result.isEmpty());
	}

	/**
	 * Testing viewUndo function of the Undo class 
	 * Equivalence partition:Stack<OperationRecord<Task,String>> is not empty and contains
	 * more than 1 task.
	 */
	@Test
	public void test2() {
		/**
		 * Prepare all the data for testing
		 */
		Undo undoObj = new Undo();
		Calendar temp = Calendar.getInstance();
		temp.set(Calendar.YEAR, Calendar.DECEMBER, Calendar.MONDAY);
		ArrayList<Calendar> arrCal = new ArrayList<Calendar>();
		arrCal.add(temp);
		FloatTask floatTask = new FloatTask("Task1", "1", true,
				Calendar.getInstance(), "5", "description1",
				Calendar.getInstance(), Calendar.getInstance());
		RepeatedTask repeatTask = new RepeatedTask("Task3", "3", true,
				Calendar.getInstance(), "2", "description2",
				Calendar.getInstance(), Calendar.getInstance(), "weekly",
				arrCal, Calendar.getInstance(), 5);
		DeadLineTask deadlineTask = new DeadLineTask("Task2", "2", false,
				Calendar.getInstance(), "1", "description2",
				Calendar.getInstance(), Calendar.getInstance(),
				Calendar.getInstance());
		/**
		 * Testing
		 */
		undoObj.saveOperation(floatTask, "ADD");
		undoObj.saveOperation(repeatTask, "DELTE");
		undoObj.saveOperation(deadlineTask, "EDIT");

		ArrayList<OperationRecord<Task, String>> result = undoObj.viewUndo();
		boolean switch1 = false;
		boolean switch2 = false;
		boolean switch3 = false;
		if (result.get(0).getTask().equals(deadlineTask))
			switch1 = true;
		if (result.get(1).getTask().equals(repeatTask))
			switch2 = true;
		if (result.get(2).getTask().equals(floatTask))
			switch3 = true;
		assertTrue(switch1 && switch2 && switch3);

	}
	
	/**
	 * Testing inverse functon of Undo class
	 * Equivalence Partition: empty command
	 */
	@Test
	public void test3(){
		boolean switch1=false;
		Undo undoObj = new Undo();
		String result=undoObj.inverseFunction(" ");
		if(result.isEmpty()){
			switch1=true;
		}
		assertTrue(switch1);
	}
	/**
	 * Testing inverse function of Undo class 
	 * Equivalence Partition: Non empty command
	 */
	@Test
	public void test4() {
		boolean switch1=false;
		Undo undoObj = new Undo();
		String result=undoObj.inverseFunction("ADD");
		String result2=undoObj.inverseFunction("DELETE");
		String result3=undoObj.inverseFunction("EDIT");
		String result4=undoObj.inverseFunction("DATE");
		String result5=undoObj.inverseFunction("WORKLOAD");
		
		if(result.equals("DELETE") && result2.equals("ADD") && result3.equals("EDIT") && result4.equals("EDIT") 
				&& result5.equals("EDIT")){
			switch1=true;
		}
		assertTrue(switch1);
	}
}
