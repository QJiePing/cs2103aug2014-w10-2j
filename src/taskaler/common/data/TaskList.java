/**
 * 
 */
package taskaler.common.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import taskaler.common.util.parser.calendarToString;

/**
 * Singleton Class to hold the global task list
 *
 */
public class TaskList implements Collection<Task> {

    // Special Constants
    public static int TAG_TASK_NOT_EXIST = -1;

    // Static class variables
    private static TaskList instance = null;
    private static ArrayList<Task> taskList = null;

    /**
     * Private constructor
     * 
     */
    private TaskList() {
        taskList = new ArrayList<Task>();
    }

    /**
     * Gets the global task list. Creates a new task list if first call.
     * 
     * @return Global task list
     */
    public static TaskList getInstance() {
        if (taskList == null) {
            instance = new TaskList();
        }

        return instance;
    }

    @Override
    public boolean add(Task task) {
        return taskList.add(task);
    }

    @Override
    public boolean addAll(Collection<? extends Task> collection) {
        return taskList.addAll(collection);
    }

    @Override
    public void clear() {
        taskList.clear();
    }

    @Override
    public boolean contains(Object obj) {
        return taskList.contains(obj);
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        return taskList.containsAll(collection);
    }

    @Override
    public boolean isEmpty() {
        return taskList.isEmpty();
    }

    @Override
    public Iterator<Task> iterator() {
        return taskList.iterator();
    }

    @Override
    public boolean remove(Object obj) {
        return taskList.remove(obj);
    }

    /**
     * Mutator to remove an element by its index
     * 
     * @param index
     *            Index of element to be removed
     * @return returns true if operation was successful; False otherwise
     */
    public Task remove(int index) {
        return taskList.remove(index);
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        return taskList.removeAll(collection);
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        return taskList.retainAll(collection);
    }

    @Override
    public int size() {
        return taskList.size();
    }

    @Override
    public Object[] toArray() {
        Object[] array = new Object[taskList.size()];
        for (int i = 0; i < array.length; i++) {
            array[i] = taskList.get(i);
        }
        return array;
    }

    @Override
    public <T> T[] toArray(T[] collection) {
        for (int i = 0; i < collection.length; i++) {
            collection[i] = (T) taskList.get(i);
        }
        return collection;
    }

    public ArrayList<Task> toArray(ArrayList<Task> collection) {
        for (int i = 0; i < taskList.size(); i++) {
            collection.add(taskList.get(i).clone());
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
        return taskList.get(i);
    }
}
