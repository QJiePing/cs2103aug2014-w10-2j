/**
 * 
 */
package taskaler.common.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;

import taskaler.common.util.parser.calendarToString;
import taskaler.common.data.TaskComparator;

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
        if(task == null){
            return false;
        }
        boolean result = taskList.add(task);
        Comparator<Task> c = new TaskComparator();
        taskList.sort(c);
        return result;
    }

    @Override
    public boolean addAll(Collection<? extends Task> collection) {
        if(collection == null){
            return false;
        }
        boolean result = taskList.addAll(collection);
        Comparator<Task> c = new TaskComparator();
        taskList.sort(c);
        return result;
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

    /**
     * Sets the object located at index i to another object
     * 
     * @param i
     *            Index of element
     * @param task
     *            The new object to override with
     * @return The old object
     */
    public Task set(int i, Task task) {
        return taskList.set(i, task);
    }
}
