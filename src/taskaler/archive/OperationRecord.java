/**
 * 
 */
package taskaler.archive;

/**
 * @author Quek Jie Ping, A0111798X
 * 
 *         This purpose of this class is to have a generic object which can
 *         store both Task object and String object as a single record.
 * 
 * @param <T>
 *            A generic type which we used for Task Object
 * @param <S>
 *            A generic type which we used for String Object
 */
public class OperationRecord<T, S> {
	private T t;
	private S op;

	public OperationRecord(T tObj, S sObj) {
		this.t = tObj;
		this.op = sObj;
	}

	/**
	 * Mutators Method
	 */
	public S getOp() {
		return this.op;
	}

	public T getTask() {
		return this.t;
	}

}
