package taskaler.archive;

import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Observable;
import java.util.Observer;
import java.util.Stack;

import taskaler.common.data.Task;
import taskaler.logic.OPLogic;

//@author A0111798X

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
     * Save all the operation execute by the user and task affected by the
     * command into a stack
     * 
     * @param t
     *            Task that is affected
     * @param operation
     *            Command that has been execute on the task
     */
    public void saveOperation(Task t, String operation) {
        OperationRecord<Task, String> temp = new OperationRecord<Task, String>(
                t, operation);
        record.push(temp);
    }

    /**
     * Performs undo operation for taskaler
     * 
     * @return Task
     */

    public Task undo() {
    	
    	if(record.size()<1){
    		return null;
    	}
        /**
         * get the last operation records
         */
        OperationRecord<Task, String> opRecord = record.pop();
        /**
         * determine the inverse function
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
     * Method to help determine the opposite commands or inverse the supplied
     * commands
     * 
     * @param op
     *            commands to be inversed.
     * @return String
     */

    public String inverseFunction(String op) {
        String result = "";
        switch (op) {
        case "ADD":
            result = "DELETE";
            break;
        case "DELETE":
            result = "ADD";
            break;
        case "EDIT":
        case "DATE":
        case "COMPLETE":
        case "WORKLOAD":
        case "REPEAT":
        case "TIME":
            result = "EDIT";
            break;
        }
        return result;
    }

    /**
     * Method to display the view of all the item in the undo stack. Listing the
     * 
     * @return ArrayList<Operation<Task,String>>
     */
    public ArrayList<OperationRecord<Task, String>> viewUndo() {
        ListIterator<OperationRecord<Task, String>> itr = record.listIterator();
        return reverseOrder(itr);
    }

    public String stackToDisplay() {
        String toDisplay = "";
        ArrayList<OperationRecord<Task, String>> viewStack = viewUndo();
        for (int i = 0; i < viewStack.size(); i++) {
            Task task = viewStack.get(i).getTask();
            String taskName = task.getTaskName();
            String taskID = task.getTaskID();
            String op = viewStack.get(i).getOp();
            String msg = "Performed \"" + op + "\" on Task " + taskName
                    + " (ID=" + taskID + ")";
            if (i == viewStack.size() - 1) {
                toDisplay = toDisplay + msg;
            } else {
                toDisplay = toDisplay + msg + "\n\n";
            }
        }
        return toDisplay;
    }

    /**
     * This is to reverse the order of the stack for viewing purposes. Showing
     * the latest pushed object at the first index of the array list.
     * 
     * @param itr
     *            Iterator of the undo stack
     * @return ArrayList<OperationRecord<Task,String>>
     */
    private ArrayList<OperationRecord<Task, String>> reverseOrder(
            ListIterator<OperationRecord<Task, String>> itr) {
        /**
         * Holder variables to make the last item in the stack to be the first
         * index of the ArrayList.
         */
        ArrayList<OperationRecord<Task, String>> undoView = new ArrayList<OperationRecord<Task, String>>();
        Stack<OperationRecord<Task, String>> temp = new Stack<OperationRecord<Task, String>>();
        /**
         * Transferring items using the holder variables to make the last item
         * in the iterator to be the first item
         */
        while (itr.hasNext()) {
            temp.push(itr.next());
        }
        while (!temp.isEmpty()) {
            undoView.add(temp.pop());
        }
        return undoView;
    }

    /**
     * Override the update method of observer object to listen for any commands
     * executed by the user. Only ADD, DELETE and EDIT operation task will be
     * saved by undo function
     */
    @Override
    public void update(Observable arg0, Object arg1) {
        if (arg1 instanceof OperationRecord<?, ?>) {
            OperationRecord<Task, String> currentRecord = (OperationRecord<Task, String>) arg1;
            if (currentRecord.getOp().compareToIgnoreCase("UNDO") != 0) {
                record.push(currentRecord);
            }
        }

    }
}
