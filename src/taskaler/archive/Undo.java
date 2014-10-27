package taskaler.archive;

import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Observable;
import java.util.Observer;
import java.util.Stack;

import taskaler.common.data.Task;
import taskaler.logic.OPLogic;

/**
 * @author Quek Jie Ping, A0111798X
 */
public class Undo implements Observer {

	private Stack<OperationRecord<Task, String>> record;
	private OPLogic crudLogic = null;

	/**
	 * constructor
	 */
	public Undo() {
		record = new Stack<OperationRecord<Task, String>>();
		crudLogic = OPLogic.getInstance();
	}
	
	/**
	 * Save all the operation execute by the user and task affected by the command
	 * into a stack
	 * @param t
	 * 			Task that is affected
	 * @param operation
	 * 			Command that has been execute on the task
	 */
	public void saveOperation(Task t, String operation) {
		OperationRecord<Task, String> temp = new OperationRecord<Task, String>(
				t, operation);
		record.push(temp);
	}
	
	/**
	 * Performs undo operation for taskaler
	 * @return Task
	 */

	public Task undo() {
		/**
		 *  get the last operation records
		 */
		OperationRecord<Task, String> opRecord = record.pop();
		/**
		 *  determine the inverse function
		 */
		String operation = inverseFunction(opRecord.getOp());

		Task t = opRecord.getTask();
		Task result = null;
		switch (operation) {
		case "ADD":
			result = crudLogic.addTask(t);
			break;
		case "DELETE":
			result = crudLogic.deleteTask(t);
			break;
		case "EDIT":
			result = crudLogic.editTask(t);
			break;
		}
		return result;
	}
	
	/**
	 * Method to help determine the opposite commands or inverse the supplied commands
	 * @param op
	 * 			commands to be inversed.
	 * @return String
	 */

	private String inverseFunction(String op) {
		String result = "";
		switch (op) {
		case "ADD":
			result = "DELETE";
			break;
		case "DELETE":
			result = "ADD";
			break;
		case "EDIT":
			result = "EDIT";
			break;
		}
		return result;
	}
	
	/**
	 * Method to display the view of all the item in the undo stack.
	 * Listing the 
	 * @return ArrayList<Operation<Task,String>>
	 */
	public ArrayList<OperationRecord<Task,String>> viewUndo(){
		ListIterator<OperationRecord<Task,String>> itr=record.listIterator();
		return reverseOrder(itr);
	}
	
	/**
	 * This is to reverse the order of the stack for viewing purposes.
	 * Showing the latest pushed object at the first index of the array list.
	 * @param itr
	 * 			Iterator of the undo stack
	 * @return ArrayList<OperationRecord<Task,String>>
	 */
	private ArrayList<OperationRecord<Task, String>> reverseOrder(
			ListIterator<OperationRecord<Task, String>> itr) {
		ArrayList<OperationRecord<Task,String>> undoView= new ArrayList<OperationRecord<Task,String>>();
		Stack<OperationRecord<Task,String>>temp=new Stack<OperationRecord<Task,String>>();
		while(itr.hasNext()){
			temp.push(itr.next());
		}
		while(!temp.isEmpty()){
			undoView.add(temp.pop());
		}
		return undoView;
	}
	
	/**
	 * Override the update method of observer object to listen for any commands executed
	 * by the user.
	 * Only ADD, DELETE and EDIT operation task will be saved by undo function
	 */
	@Override
	public void update(Observable arg0, Object arg1) {
		if(arg1 instanceof OperationRecord<?, ?>){
			OperationRecord<Task, String> currentRecord = (OperationRecord<Task, String>) arg1;
			if(currentRecord.getOp().compareToIgnoreCase("UNDO") != 0){
				record.push(currentRecord);
			}
		}

	}
}
