package com.lvl80.fxmessenger.server;

import com.lvl80.fxmessenger.userTypes.ClientsList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerController {

    @FXML
    private TextField textField;
    // Сокет сервера
    private ServerSocket serverSocket;
    // Главный экземпляр листа с клиентами
    private final ClientsList clientsList = new ClientsList();
    // Поток, начинающий работу в начале
    private ServerThread serverThread;
    private Thread startThread;

    // Блок, срабатывающий при закрытии окна
    // Метод вызывается из MessangerApp
    @FXML
    public javafx.event.EventHandler<WindowEvent> getCloseEventHandler() {
        return _event -> serverThread.stopThread(serverSocket, startThread);
    }

    // Начало работы сервера
    public void startServer() throws IOException {
        serverSocket = new ServerSocket(Integer.parseInt(textField.getText()));
        System.out.println("Начат сервер " + serverSocket.getLocalPort());
        // Обработчик поступающих клиентов и перенаправление их на новый поток
        // Польз(>) -> Сервер() -> Новый Поток(Сервер(>)) -> Сервер()
        startThread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()){
                try {
                    Socket socket = serverSocket.accept();
                    clientsList.addClient(socket);
                    serverThread = new ServerThread(socket,clientsList);
                    serverThread.start();
                } catch (IOException e){System.out.println("#Error ServerController -> startThread()");}
            }
        });
        startThread.start();
    }
}
