/**
 * 
 */
package taskaler.ui.controller;

import java.io.IOException;
import java.util.HashMap;

import taskaler.ui.model.TextPaneModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;

/**
 * Controller associated with the TitledPaneView
 * 
 * @author Cheah Kit Weng, A0059806W
 *
 */
public class TextPaneController extends TitledPane implements IController {
    // Current model associated with this controller
    private TextPaneModel currentModel = null;

    // FXML Element Bindings
    @FXML
    private TitledPane titledPane;

    @FXML
    private TextArea txtBody;

    /**
     * Default constructor
     * 
     * @param model
     *            Model to be associated with this controller
     * @throws IOException
     *             Thrown if an error is encountered while reading the FXML file
     */
    public TextPaneController(TextPaneModel model) throws IOException {
        currentModel = model;

        initialize(common.FXML_TEXT_PANE);
        update();
    }

    @Override
    public void initialize(String FXML) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(FXML));
        loader.setRoot(this);
        loader.setController(this);
        loader.load();
    }

    @Override
    public void update() throws IOException {
        setTitle(currentModel.title);
        setTextBody(currentModel.textBody);
    }

    /**
     * Method to set the body of the view
     * 
     * @param textBody
     *            Text for the text body
     */
    private void setTextBody(String textBody) {
        txtBody.setText(textBody);
    }

    /**
     * Method to set the title of the view
     * 
     * @param title
     *            Title of the view
     */
    private void setTitle(String title) {
        titledPane.setText(title);
    }

    @Override
    public HashMap<String, String> getState() {
        return currentModel.toHashMap();
    }

}
