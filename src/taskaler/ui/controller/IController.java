/**
 * 
 */
package taskaler.ui.controller;

import java.io.IOException;
import java.util.HashMap;

/**
 * Interface to be implemented by all controllers
 * 
 * @author Cheah Kit Weng, A0059806W
 *
 */
//@author A0059806W
public interface IController {

    /**
     * Method to initialize the view
     * 
     * @throws IOException
     *             Thrown if an error is encountered while loading FXML file
     * 
     */
    public abstract void initialize(String FXML) throws IOException;

    /**
     * Method to update the view
     * 
     * @throws IOException
     *             Thrown if an IO error is encountered while updating a view
     */
    public abstract void update() throws IOException;

    /**
     * Method to get the current state of the view
     * 
     * @return HashMap representation of the current view
     */
    public abstract HashMap<String, String> getState();
}
