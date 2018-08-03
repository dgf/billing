package org.aplatanao.billing.desktop;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class DesktopApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Controller.fxml"));
        stage.setTitle("Aplatanao Billing Controller");
        stage.setScene(new Scene(root, 400, 300));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}