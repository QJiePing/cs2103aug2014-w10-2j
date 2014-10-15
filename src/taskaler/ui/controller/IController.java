/**
 * 
 */
package taskaler.ui.controller;

/**
 * Interface to be implmented by all controllers
 * 
 * @author Cheah Kit Weng, A0059806W
 *
 */
public interface IController{
    
    /**
     * Method to initalize the controller
     * 
     */
    public abstract void initialize();

    /**
     * Method to set the title of the view
     * 
     * @param title
     *            String to set the title to
     */
    public abstract void setTitle(String title);

    /**
     * Method to set the body of the content
     * 
     * @param content
     *            Content to set the body to
     */
    public abstract void setContent(String content);
}
