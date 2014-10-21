/**
 * 
 */
package taskaler.archive;

/**
 * @author Quek Jie Ping, A0111798X
 *
 */
public class OperationRecord<T, S> {
    private T t;
    private S op;

    public OperationRecord(T tObj, S sObj) {
        this.t = tObj;
        this.op = sObj;
    }

    public S getOp() {
        return this.op;
    }

    public T getTask() {
        return this.t;
    }

}

