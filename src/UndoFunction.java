import java.util.Stack;

/*
 * @author Quek Jie Ping
 */
public class UndoFunction {
	
	private History history;

	public UndoFunction(){
		history= new History();
	}
	
	public void saveOperation(String operation){
		history.addRecord(operation);
	}
}

class History{
	private Stack<String> historyRecord;

	public History(){
		historyRecord= new Stack<String>();
	}
	
	public void addRecord(String record){
		historyRecord.push(record);
	}
	public void removedRecord(){
		historyRecord.pop();
	}
}
