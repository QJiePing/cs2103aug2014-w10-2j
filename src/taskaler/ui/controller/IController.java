/**
 * 
 */
package taskaler.ui.controller;

import java.io.IOException;

/**
 * Interface to be implemented by all controllers
 * 
 * @author Cheah Kit Weng, A0059806W
 *
 */
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
}
