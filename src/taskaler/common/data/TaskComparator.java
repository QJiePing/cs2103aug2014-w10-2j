/**
 * 
 */
package taskaler.common.data;

import java.util.Comparator;

/**
 * @author Kiwi
 * @param <T>
 *
 */
public class TaskComparator implements Comparator<Task> {

    @Override
    public int compare(Task arg0, Task arg1) {
        int result = 0;
        int ID0 = Integer.parseInt(arg0.getTaskID());
        int ID1 = Integer.parseInt(arg1.getTaskID());
        if(ID0 < ID1){
            return -1;
        }else if(ID0 > ID1){
            return 1;
        }
        return result;
    }

}
