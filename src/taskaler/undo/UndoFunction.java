package taskaler.undo;
import java.util.Stack;
import java.util.LinkedList;
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

	public void saveOperation(Task t, String operation){
		OperationRecords<Task,String> temp= new OperationRecords<Task,String>(t,operation);
		record.push(temp);
	}
	
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

class OperationRecords<T,S>{
	private T t;
	private S op;
	
	public OperationRecords(T tObj,S sObj){
		this.t=tObj;
		this.op=sObj;
	}
	public S getOp(){
		return this.op;
	}
	public T getTask(){
		return this.t;
	}
	
}
