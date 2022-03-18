package com.lvl80.fxmessenger.messenger;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MessengerApp extends Application{
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MessengerApp.class.getResource("Messenger.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setResizable(false);
        stage.setTitle("Messanger");
        stage.setScene(scene);
        stage.show();
        // Привязка события закрытия окна
        MessengerController controller = fxmlLoader.getController();
        stage.setOnCloseRequest(controller.getCloseEventHandler());
    }
}