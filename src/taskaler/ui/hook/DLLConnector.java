/**
 * 
 */
package taskaler.ui.hook;

import java.util.Observable;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Class to connect to the external DLL library and waits for the hot key event
 * 
 * @author Cheah Kit Weng, A0059806W
 *
 */
public class DLLConnector extends Observable implements Runnable {

    // Flag to stop the thread
    public static AtomicReference<Boolean> isStopped =new AtomicReference<Boolean>();
    
    // the ASCII hot key; 0x42 is 'b'
    private static final int WM_HOTKEY = 0x42;
    
    // Is ALT key required
    private static final boolean MOD_ALT = true;
    
    // DLL API to be called
    private native boolean isHotKeyPressed(int key, boolean isAltNeeded);
    
    @Override
    public void run() {
        isStopped.set(false);
        while(!isStopped.get()){
            if(isHotKeyPressed(WM_HOTKEY, MOD_ALT)){
                setChanged();
                notifyObservers("WM_HOTKEY");
            }
        }

    }
    
    // Should not be used; Only meant for the generation of header file
    public static void main(String[] args) {
       new DLLConnector().isHotKeyPressed(WM_HOTKEY, MOD_ALT);
    }

}
