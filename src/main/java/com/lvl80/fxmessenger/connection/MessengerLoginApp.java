package com.lvl80.fxmessenger.connection;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MessengerLoginApp extends Application {

    private static Stage stage;

    @Override
    public void start(Stage _stage) throws IOException {
        stage = _stage;
        FXMLLoader fxmlLoader = new FXMLLoader(MessengerLoginApp.class.getResource("MessengerLogin.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        _stage.setResizable(false);
        _stage.setTitle("Login");
        _stage.setScene(scene);
        _stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    // Получаем stage для дальнейшего закрытия в методе openMessangerWindow()
    public static Stage getStage(){
        return stage;
    }
}