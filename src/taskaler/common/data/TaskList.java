/**
 * 
 */
package taskaler.common.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;

import taskaler.common.data.TaskComparator;
import taskaler.logic.common;

/**
 * Singleton Class to hold the global task list
 *
 */
public class TaskList implements Collection<Task> {

    // Special Constants
    public static int TAG_TASK_NOT_EXIST = -1;
    public static int DEFAULT_TASK_ID = 0;
    
    public static String DEADLINE_TASK = "deadline";
    public static String FLOAT_TASK = "float";
    public static String REPEATED_TASK = "repeated";

    // Static class variables
    private static TaskList instance = null;
    private static ArrayList<DeadLineTask> deadlineTaskList = null;
    private static ArrayList<FloatTask> floatTaskList = null;
    private static ArrayList<RepeatedTask> repeatedTaskList = null;
    
    private static int numOfIncompleteTasks = common.DEFAULT_NUM_OF_INCOMPLETE;

    /**
     * Private constructor
     * 
     */
    private TaskList() {
    	deadlineTaskList = new ArrayList<DeadLineTask>();
    	floatTaskList = new ArrayList<FloatTask>();
    	repeatedTaskList = new ArrayList<RepeatedTask>();
    }

    /**
     * Gets the global task list. Creates a new task list if first call.
     * 
     * @return Global task list
     */
    public static TaskList getInstance() {
        if (instance == null) {
            instance = new TaskList();
        }

        return instance;
    }

    @Override
	public boolean add(Task task) {
        if(task == null){
            return false;
        }
        boolean result = false;
        if(task instanceof DeadLineTask) {
        	result = deadlineTaskList.add((DeadLineTask) task);
        	Comparator<Task> c = new TaskComparator();
        	deadlineTaskList.sort(c);
            
        } else if (task instanceof FloatTask) {
        	result = floatTaskList.add((FloatTask) task);
        	Comparator<Task> c = new TaskComparator();
        	floatTaskList.sort(c);
        	
        } else if (task instanceof RepeatedTask) {
        	result = repeatedTaskList.add((RepeatedTask) task);
        	Comparator<Task> c = new TaskComparator();
        	repeatedTaskList.sort(c);
        }
        
        
        return result;
    }

    @Override
	public boolean addAll(Collection<? extends Task> collection) {
        if(collection == null){
            return false;
        }
        boolean result = false;
        if(collection instanceof DeadLineTask) {
        	result = deadlineTaskList.addAll((Collection<? extends DeadLineTask>) collection);
        	Comparator<Task> c = new TaskComparator();
        	deadlineTaskList.sort(c);
            
        } else if (collection instanceof FloatTask) {
        	result = floatTaskList.addAll((Collection<? extends FloatTask>) collection);
        	Comparator<Task> c = new TaskComparator();
        	floatTaskList.sort(c);
        	
        } else if (collection instanceof RepeatedTask) {
        	result = repeatedTaskList.addAll((Collection<? extends RepeatedTask>) collection);
        	Comparator<Task> c = new TaskComparator();
        	repeatedTaskList.sort(c);
        }
        return result;
    }
    
    public boolean addAll(ArrayList<Object> array){
        boolean result = true;
        if(array == null){
            result = false;
            return result;
        }
        ArrayList<FloatTask> floatArr=(ArrayList<FloatTask>)array.get(0);
        ArrayList<DeadLineTask> deadlineArr=(ArrayList<DeadLineTask>)array.get(1);
        ArrayList<RepeatedTask> repeatArr=(ArrayList<RepeatedTask>)array.get(2);
        if(floatArr != null){
            result = floatTaskList.addAll(floatArr);
        }
        if(deadlineArr != null){
            result = deadlineTaskList.addAll(deadlineArr);
        }
        if(repeatArr != null){
            result = repeatedTaskList.addAll(repeatArr);
        }
        return result;
    }
    
    @Override
	public void clear() {
		deadlineTaskList.clear();
		floatTaskList.clear();
		repeatedTaskList.clear();
	}
    
    @Override
    public boolean contains(Object obj) {
    	boolean contain = false;
    	
    	if(obj instanceof DeadLineTask) {
    		contain = deadlineTaskList.contains(obj);
        } else if (obj instanceof FloatTask) {
        	contain = floatTaskList.contains(obj);
        } else if (obj instanceof RepeatedTask) {
        	contain = repeatedTaskList.contains(obj);
        }
    	
		return contain;
    }
    
    @Override
    public boolean containsAll(Collection<?> collection) {
    	boolean containAll = false;
    	
    	if(collection instanceof DeadLineTask) {
    		containAll = deadlineTaskList.containsAll(collection);
        } else if (collection instanceof FloatTask) {
        	containAll = floatTaskList.containsAll(collection);
        } else if (collection instanceof RepeatedTask) {
        	containAll = repeatedTaskList.containsAll(collection);
        }
    	
		return containAll;
    }
    
    @Override
    public boolean isEmpty() {
		return deadlineTaskList.isEmpty() && floatTaskList.isEmpty()
				&& repeatedTaskList.isEmpty();
    }
    
    @Override
    public Iterator<Task> iterator() {
		return null;
    }
    
    @Override
    public boolean remove(Object obj) {
    	if(obj instanceof DeadLineTask) {
    		return deadlineTaskList.remove(obj);
    	} else if(obj instanceof FloatTask) {
    		return floatTaskList.remove(obj);
    	} else {
    		return repeatedTaskList.remove(obj);
    	}
    }
    
    
    /**
     * Mutator to remove an element by its index
     * 
     * @param index
     *            Index of element to be removed
     * @return returns true if operation was successful; False otherwise
     */
    public Task remove(int index) {
    	if(index < floatTaskList.size()) {
    		return floatTaskList.remove(index);
    	} else if(index < floatTaskList.size() + deadlineTaskList.size()) {
    		return deadlineTaskList.remove(index-floatTaskList.size());
    	} else {
    		return repeatedTaskList.remove(index-floatTaskList.size()-deadlineTaskList.size());
    	}
    }
    
    @Override
    public boolean removeAll(Collection<?> collection) {
    	if(collection instanceof DeadLineTask) {
    		return deadlineTaskList.removeAll(collection);
    	} else if(collection instanceof FloatTask) {
    		return floatTaskList.removeAll(collection);
    	} else {
    		return repeatedTaskList.removeAll(collection);
    	}
    }
    
    @Override
    public boolean retainAll(Collection<?> collection) {
    	if(collection instanceof DeadLineTask) {
    		return deadlineTaskList.retainAll(collection);
    	} else if(collection instanceof FloatTask) {
    		return floatTaskList.retainAll(collection);
    	} else {
    		return repeatedTaskList.retainAll(collection);
    	}	
    }
    
    @Override
    public int size() {
		return deadlineTaskList.size() + floatTaskList.size()
				+ repeatedTaskList.size();
    }
    
    @Override
    public Task[] toArray() {
        Task[] array = new Task[this.size()];
        for (int i = 0; i < array.length; i++) {
        	if(i < floatTaskList.size()) {
        		array[i] = floatTaskList.get(i);
        	} else if(i < floatTaskList.size() + deadlineTaskList.size()) {
        		array[i] = deadlineTaskList.get(i - floatTaskList.size());
        	} else {
        		array[i] = repeatedTaskList.get(i-floatTaskList.size()-deadlineTaskList.size());
        	}
        }
        return array;
    }
    
	@Override
    public <T> T[] toArray(T[] collection) {
        for (int i = 0; i < collection.length; i++) {
            if(i < floatTaskList.size()) {
            	collection[i] = (T) floatTaskList.get(i);
        	} else if(i < floatTaskList.size() + deadlineTaskList.size()) {
        		collection[i] = (T) deadlineTaskList.get(i - floatTaskList.size());
        	} else {
        		collection[i] = (T) repeatedTaskList.get(i-floatTaskList.size()-deadlineTaskList.size());
        	}
        }
        return collection;
    }
    
	public ArrayList<Task> toArray(ArrayList<Task> collection) {
        for (int i = 0; i < this.size(); i++) {
            collection.add(this.get(i).clone());
        }
        return collection;
    }
	
    public ArrayList<DeadLineTask> deadlineToArray() {
    	ArrayList<DeadLineTask> collection = new ArrayList<DeadLineTask>();
        for (int i = 0; i < deadlineTaskList.size(); i++) {
            collection.add(deadlineTaskList.get(i).clone());
        }
        return collection;
    }
    
    public ArrayList<FloatTask> floatToArray() {
    	ArrayList<FloatTask> collection = new ArrayList<FloatTask>();
        for (int i = 0; i < floatTaskList.size(); i++) {
            collection.add(floatTaskList.get(i).clone());
        }
        return collection;
    }
    
    public ArrayList<RepeatedTask> repeatedToArray() {
    	ArrayList<RepeatedTask> collection = new ArrayList<RepeatedTask>();
        for (int i = 0; i < repeatedTaskList.size(); i++) {
            collection.add(repeatedTaskList.get(i).clone());
        }
        return collection;
    }
    
    /**
     * Retrieves the task specified by the index
     * 
     * @param i
     *            The index of the task
     * @return The task at the index
     */
    public Task get(int i) {
    	if(i < floatTaskList.size()) {
    		return floatTaskList.get(i);
    	} else if(i < floatTaskList.size() + deadlineTaskList.size()) {
    		return deadlineTaskList.get(i - floatTaskList.size());
    	} else {
    		return repeatedTaskList.get(i-floatTaskList.size()-deadlineTaskList.size());
    	}
    }
    
    
    
    public int maxTaskID() {
    	int maxID = DEFAULT_TASK_ID;
    	
    	if(!deadlineTaskList.isEmpty()) {
    		String maxDeadlineTaskID = deadlineTaskList.get(deadlineTaskList.size()-1).getTaskID();
    		maxID = Math.max(maxID, Integer.parseInt(maxDeadlineTaskID));
    	}
    	
    	if(!floatTaskList.isEmpty()) {
    		String maxFloatTaskID = floatTaskList.get(floatTaskList.size()-1).getTaskID();
    		maxID = Math.max(maxID, Integer.parseInt(maxFloatTaskID));
    	}
    	
    	if(!repeatedTaskList.isEmpty()) {
    		String maxRepeatedTaskID = repeatedTaskList.get(repeatedTaskList.size()-1).getTaskID();
    		maxID = Math.max(maxID, Integer.parseInt(maxRepeatedTaskID));
    	}
    	
    	return maxID;
    	
    }
    
    public int getNumOfIncomplete() {
    	if(numOfIncompleteTasks == common.DEFAULT_NUM_OF_INCOMPLETE) {
    		if(this != null && this.size() != 0) {
				for (int i = 0; i < this.size(); i++) {
					if (!this.get(i).getTaskStatus()) {
						numOfIncompleteTasks++;
					}
				}
    		}
    	}
    	
    	return numOfIncompleteTasks;
    }
    
    public void incrementNumOfIncomplete() {
    	numOfIncompleteTasks++;
    }
    
    public void decrementNumOfIncomplete() {
    	if(numOfIncompleteTasks > 0) {
    		numOfIncompleteTasks--;
    	}
    }
    
    
    public void defaultNumOfIncomplete() {
    	numOfIncompleteTasks = 0;
    }
    
    


}
