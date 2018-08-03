package org.aplatanao.billing.desktop;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.aplatanao.billing.rest.client.api.DefaultApi;

public class Controller {

    @FXML
    private TextField url;

    @FXML
    private TextArea response;

    @FXML
    private void connect(ActionEvent event) {
        response.setText(new DefaultApi().getTasks().toString());
}
}
