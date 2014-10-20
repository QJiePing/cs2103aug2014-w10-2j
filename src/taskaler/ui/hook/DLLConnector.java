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
    public static AtomicReference<Boolean> isStopped = new AtomicReference<Boolean>();

    // the ASCII hot key; 0x42 is 'b'
    private static final int WM_HOTKEY = 0x42;

    // Is ALT key required
    private static final boolean MOD_ALT = true;

    // DLL API to be called
    private native boolean isHotKeyPressed();

    private native boolean RegisterHotKey(int key, boolean isAltNeeded);
    
    private native boolean UnregisterHotKey();

    @Override
    public void run() {
        isStopped.set(false);
        RegisterHotKey(WM_HOTKEY, MOD_ALT);
        while (!isStopped.get()) {
            if (isHotKeyPressed()) {
                setChanged();
                notifyObservers("WM_HOTKEY");
            }
        }
        new DLLConnector().UnregisterHotKey();
        return;

    }

    // Should not be used; Only meant for the generation of header file
    public static void main(String[] args) {
        new DLLConnector().RegisterHotKey(WM_HOTKEY, MOD_ALT);
        new DLLConnector().isHotKeyPressed();
        new DLLConnector().UnregisterHotKey();
    }

}
