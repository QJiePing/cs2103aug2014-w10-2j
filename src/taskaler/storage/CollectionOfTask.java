package taskaler.storage;

import java.util.ArrayList;

//@author A0111798X
 /**
 *         This class acts as a holder class for gson. Gson requires to know the
 *         type of the object it is converting to json format. ArrayList only
 *         allows to specify one generic type. Using this class, gson will
 *         recognized all the 3 type of task correctly and are able to convert
 *         them all to json format.
 */

public class CollectionOfTask<T, Y, Z> {
	private ArrayList<T> floatArr;
	private ArrayList<Y> DeadLineArr;
	private ArrayList<Z> RepeatedArr;

	public CollectionOfTask() {
		floatArr = new ArrayList<T>();
		DeadLineArr = new ArrayList<Y>();
		RepeatedArr = new ArrayList<Z>();
	}

	/**
	 * Mutator method to change the array list
	 * @param arrList
	 * 				The new array list
	 */
	public void setFloatArr(ArrayList<T> arrList) {
		this.floatArr = arrList;
	}
	/**
	 * Mutator method to change the array list
	 * @param arrList
	 * 				The new array list
	 */
	public void setDeadLineArr(ArrayList<Y> arrList) {
		this.DeadLineArr = arrList;
	}
	/**
	 * Mutator method to change the array list
	 * @param arrList
	 * 				The new array list
	 */
	public void setRepeatedArr(ArrayList<Z> arrList) {
		this.RepeatedArr = arrList;
	}

	/**
	 * Method return the array list of T objects
	 * @return ArrayList<T>
	 */
	public ArrayList<T> getFloatArr() {
		return this.floatArr;
	}
	/**
	 * Method return the array list of Y objects
	 * @return ArrayList<Y>
	 */
	public ArrayList<Y> getDeadLineArr() {
		return this.DeadLineArr;
	}
	/**
	 * Method return the array list of Z objects
	 * @return ArrayList<Z>
	 */
	public ArrayList<Z> getRepeatedArr() {
		return this.RepeatedArr;
	}
}
