package taskaler.archive;
import java.util.Stack;

/*
 * @author Quek Jie Ping
 */
public class UndoFunction {
	
	private OperationRecords opRecord;

	public UndoFunction(){
		opRecord= new OperationRecords();
	}
	
	public void saveOperation(String operation){
		opRecord.addRecord(operation);
	}
}

class OperationRecords{
	private Stack<String> record;

	public OperationRecords(){
		record= new Stack<String>();
	}
	
	public void addRecord(String r){
		record.push(r);
	}
	public void removedRecord(){
		record.pop();
	}
}
