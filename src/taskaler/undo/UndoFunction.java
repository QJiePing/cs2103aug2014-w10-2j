package taskaler.undo;
import java.util.Stack;
import taskaler.common.data.Task;
import taskaler.logic.OPLogic;

/*
 * @author Quek Jie Ping
 */
public class UndoFunction {

	private Stack<OperationRecords<Task,String>> record;

	public UndoFunction(){
		record= new Stack<OperationRecords<Task,String>>();
	}
	/*
	 * This method is only used by the controller component to inform the UndoFunction
	 * what command has been entered so far.
	 * 
	 * @param t
	 * 			Task object that the command interact with
	 * @param operation
	 * 			The String that indicate what is the command executed
	 * 
	 */
	public void saveOperation(Task t, String operation){
		OperationRecords<Task,String> temp= new OperationRecords<Task,String>(t,operation);
		record.push(temp);
	}
	/*
	 * This method is called when the user enter the undo command
	 */
	public void undo(){
		//get the last operation records
		OperationRecords<Task,String> opRecord=record.pop();
		//determine the inverse function
		String operation=inverseFunction(opRecord.getOp());
		Task t= opRecord.getTask();
		switch(operation){
		case "ADD": OPLogic.addTask(t.getTaskDescription(), t.getTaskDescription());
			break;
		case "DELETE": OPLogic.deleteTask(t.getTaskID());
			break;
		case "EDIT": OPLogic.editTask(t.getTaskID(), t.getTaskName(), t.getTaskDescription());
			break;
		}
		
	}
	/*
	 * Helper method to determine the inverse action to perform
	 * @param op
	 * 			The operation of the command
	 */
	private String inverseFunction(String op){
		String result="";
		switch(op){
		case "ADD": result="DELETE";
			break;
		case "DELETE": result= "ADD";
			break;
		case "EDIT": result= "EDIT";
			break;
		}
		return result;
		}
	}
/*
 * This is an record object that will only be instantiated and used by UndoFunction
 */

class OperationRecords<T,S>{
	private T t;
	private S op;
	
	protected OperationRecords(T tObj,S sObj){
		this.t=tObj;
		this.op=sObj;
	}
	protected S getOp(){
		return this.op;
	}
	public T getTask(){
		return this.t;
	}
	
}
