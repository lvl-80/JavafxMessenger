package com.lvl80.fxmessenger.messenger;

import com.lvl80.fxmessenger.userTypes.Client;
import com.lvl80.fxmessenger.connection.MessagesSender;
import com.lvl80.fxmessenger.connection.MessengerLoginController;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.stage.*;

import java.io.IOException;
import java.net.Socket;

public class MessengerController {

    @FXML
    private TextField textField;

    @FXML
    private TextArea textArea;

    // Сокет этого клиента
    private Socket socket;
    // Класс отправки сообщений
    private MessagesSender sender;
    // Поток приёма и добавления сообщений
    private MessengerThread thread;
    // Клиент, отправляющий сообщения
    private Client client;

    // Блок, срабатывающий при открытии нового окна
    @FXML
    public void initialize(){
        try {
            socket = new Socket("127.0.0.1", MessengerLoginController.getPort());
            sender = new MessagesSender();
            client = MessengerLoginController.getClient();
            // Установка никнейма пользователя в чате
            textField.setPromptText(client.getNickname() + "'s message");
            // Обработчик поступающих сообщений
            thread = new MessengerThread(socket, textArea, client);
            thread.start();
        } catch (IOException e) {System.out.println("#Error MessageController -> initialize");}
    }

    // Блок, срабатывающий при закрытии окна
    // Метод вызывается из MessangerApp
    @FXML
    public javafx.event.EventHandler<WindowEvent> getCloseEventHandler() {
        return _event -> thread.stopThread();
    }

    // Отправка сообщений на сервер через класс MessagesSender
    // Используется Кнопкой: кнопка -> нажатие -> sendMessage()
    public void sendMessage(){
        sender.sendMessageToServerFromUser(client ,socket, textField);
        textField.clear();
    }

    // Отправка сообщений ENTER
    @FXML
    public void formKeyPressed(KeyEvent _evt) {
        if (_evt.getCode() == KeyCode.ENTER)
            sendMessage();
    }
}