package com.projecte.tpv;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.io.IOException;
import java.sql.SQLException;

public class HelloApplication extends Application {
    public static Stage st = null;

    @Override
    public void start(Stage stage) throws IOException {
        st = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("inici.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        stage.setTitle("Inici");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }



    public static void main(String[] args) {
        launch();
    }
}