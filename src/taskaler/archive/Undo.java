package taskaler.archive;

import java.util.Observable;
import java.util.Observer;
import java.util.Stack;

import taskaler.common.data.Task;
import taskaler.logic.OPLogic;

/**
 * @author Quek Jie Ping
 */
public class Undo implements Observer {

    private Stack<OperationRecord<Task, String>> record;
    private OPLogic crudLogic = null;

    public Undo() {
        record = new Stack<OperationRecord<Task, String>>();
        crudLogic = OPLogic.getInstance();
    }

    public void saveOperation(Task t, String operation) {
        OperationRecord<Task, String> temp = new OperationRecord<Task, String>(
                t, operation);
        record.push(temp);
    }

    public Task undo() {
        // get the last operation records
        OperationRecord<Task, String> opRecord = record.pop();
        // determine the inverse function
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
