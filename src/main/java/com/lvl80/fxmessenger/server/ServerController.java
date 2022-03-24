package com.lvl80.fxmessenger.server;

import com.lvl80.fxmessenger.userTypes.ClientsList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerController {

    @FXML
    private TextField inputPort;

    @FXML
    private Label labelStatus;

    @FXML
    private Label labelPort;

    @FXML
    private Button buttonStart;

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
        return _event -> {
            if (serverThread != null)
                serverThread.stopThread(serverSocket, startThread);
            else {
                try {
                    serverSocket.close();
                    startThread.interrupt();
                } catch (IOException e) {System.out.println("#Error ServerController -> getCloseEventHandler()");}
            }
        };
    }

    // Начало работы сервера
    public void startServer() throws IOException {
        if (serverSocket == null) {
            serverSocket = new ServerSocket(Integer.parseInt(inputPort.getText()));
            labelPort.setText(inputPort.getText());
            // Установка стилей на старте
            labelStatus.setTextFill(Color.GREEN);
            labelStatus.setText("online");
            buttonStart.setText("stop");
            // Сообщение о старте сервера
            System.out.println("Начат сервер " + serverSocket.getLocalPort());
            // Обработчик поступающих клиентов и перенаправление их на новый поток
            // Польз(>) -> Сервер() -> Новый Поток(Сервер(>)) -> Сервер()
            startThread = new Thread(() -> {
                while (!Thread.currentThread().isInterrupted()) {
                    try {
                        Socket socket = serverSocket.accept();
                        clientsList.addClient(socket);
                        serverThread = new ServerThread(socket, clientsList);
                        serverThread.start();
                    } catch (IOException e) {
                        System.out.println("#Error ServerController -> startThread()");
                    }
                }
            });
            startThread.start();
        } else {
            // Закрытие существующего сокета
            getCloseEventHandler();
            serverSocket.close();
            serverSocket = null;
            // Установка стилей по окончанию
            labelPort.setText("0000");
            labelStatus.setTextFill(Color.RED);
            labelStatus.setText("offline");
            buttonStart.setText("start");
        }
    }
}
