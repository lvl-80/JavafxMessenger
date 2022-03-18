package com.lvl80.fxmessenger.server;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ServerApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ServerApp.class.getResource("Server.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setResizable(false);
        stage.setTitle("Server");
        stage.setScene(scene);
        stage.show();
        // Привязка события закрытия окна
        ServerController controller = fxmlLoader.getController();
        stage.setOnCloseRequest(controller.getCloseEventHandler());
    }

    public static void main(String[] args) {
        launch();
    }
}