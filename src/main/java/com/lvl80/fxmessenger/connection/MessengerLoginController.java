package com.lvl80.fxmessenger.connection;

import com.lvl80.fxmessenger.messenger.MessengerApp;
import com.lvl80.fxmessenger.userTypes.Client;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.Socket;

public class MessengerLoginController {
    // Данные клиента
    private static Client client;
    // Введённый порт
    private static int port;

    @FXML
    private TextField inputLogin;

    @FXML
    private TextField inputPassword;

    @FXML
    private TextField inputPort;

    // Событие открытия окна с чатом
    // Окно логина -> открыть новый чат -> закрыть окно логина
    @FXML
    public void openMessangerWindow() {
        port = Integer.parseInt(inputPort.getText());
        Socket socket;
        try {
//            if (inputLogin.getText().equals(admin.getLogin()) && inputPassword.getText().equals(admin.getPassword()) ) {
                socket = new Socket("127.0.0.1",port);
                socket.close();
                client = new Client(inputLogin.getText(),inputPassword.getText(),inputLogin.getText());
                // Создание нового окна с чатом
                MessengerApp messengerApp = new MessengerApp();
                messengerApp.start(new Stage());
                // Закрытие окна логина
                MessengerLoginApp.getStage().close();
//            }
        } catch (Exception e){
            System.out.println("Неверный порт");
        }
    }

    public static int getPort(){
        return port;
    }

    public static Client getClient(){
        return client;
    }
}
