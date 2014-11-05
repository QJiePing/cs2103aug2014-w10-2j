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

	/**
	 * Constructor
	 */
	public CollectionOfTask() {
		floatArr = new ArrayList<T>();
		DeadLineArr = new ArrayList<Y>();
		RepeatedArr = new ArrayList<Z>();
	}

	/**
	 * Mutator methods
	 */
	public void setFloatArr(ArrayList<T> arrList) {
		this.floatArr = arrList;
	}

	public void setDeadLineArr(ArrayList<Y> arrList) {
		this.DeadLineArr = arrList;
	}

	public void setRepeatedArr(ArrayList<Z> arrList) {
		this.RepeatedArr = arrList;
	}

	/**
	 * Accessor Method
	 */
	public ArrayList<T> getFloatArr() {
		return this.floatArr;
	}

	public ArrayList<Y> getDeadLineArr() {
		return this.DeadLineArr;
	}

	public ArrayList<Z> getRepeatedArr() {
		return this.RepeatedArr;
	}
}
