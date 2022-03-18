package com.lvl80.fxmessenger.connection;

import com.lvl80.fxmessenger.database.DatabaseManager;
import com.lvl80.fxmessenger.messenger.MessengerApp;
import com.lvl80.fxmessenger.userTypes.Client;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class MessengerLoginController {
    // Данные клиента
    private static Client client;
    // Введённый порт
    private static int port;
    // Никнейм клиента
    private static String nickname;

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
        try {
            // Проверка введённых данных (логин и пароль)
            if (checkLoginData()) {
                // Проверка корректности порта
                Socket socket = new Socket("127.0.0.1",port);
                socket.close();
                // Создание экземпляра с данными клиента
                client = new Client(nickname,inputLogin.getText(),inputPassword.getText());
                // Создание нового окна с чатом
                MessengerApp messengerApp = new MessengerApp();
                messengerApp.start(new Stage());
                // Закрытие окна логина
                MessengerLoginApp.getStage().close();
            }
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

    // Проверка введённых данных (логин и пароль) путём перебора БД:
    // цикл() -> DBclient(...) == new Client(...) -> ? true : false
    public boolean checkLoginData(){
        // Получение нужных данных через SQL-запрос
        ResultSet resultSet = DatabaseManager.getResultSet("Nickname, Login, Password");
            try {
                // Цикл обхода БД
                while (Objects.requireNonNull(resultSet).next()) {
                    // Клинет взятый из БД
                    Client databaseClient = new Client(
                            resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getString(3)
                    );
                    // При совпадении одобрить авторизацию
                    if (inputLogin.getText().equals(databaseClient.getLogin()) && inputPassword.getText().equals(databaseClient.getPassword())) {
                        nickname = resultSet.getString(1);
                        return true;
                    }
                }
            } catch (SQLException e) {System.out.println("#Error MessengerLoginController -> checkLoginData()");}
        return false;
    }
}
