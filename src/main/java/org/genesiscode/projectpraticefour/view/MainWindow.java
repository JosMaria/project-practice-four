package org.genesiscode.projectpraticefour.view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainWindow extends Application {

    public static final String TITLE = "Proyecto - Practica 4";

    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(MainPane.getInstance().getPane());
        stage.setScene(scene);
        stage.setTitle(TITLE);
        stage.show();
    }
}
