/**
 * 
 */
package taskaler.ui.hook;

import java.util.Observable;
import java.util.Observer;

/**
 * Class to receive a response if a hot-key press is triggered
 * 
 * @author Cheah Kit Weng, A0059806W
 *
 */
public class KeyPressHandler implements Observer {

    @Override
    public void update(Observable arg0, Object arg1) {
        if(arg1 instanceof String){
            if(((String) arg1).compareToIgnoreCase("WM_HOTKEY") == 0){
                System.out.println("Received hot key press");
            }else{
                System.out.println("No Idea who called this event");
            }
        }
    }

}
