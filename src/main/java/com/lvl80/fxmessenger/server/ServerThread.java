package com.lvl80.fxmessenger.server;

import com.lvl80.fxmessenger.userTypes.ClientsList;
import com.lvl80.fxmessenger.connection.MessagesSender;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerThread extends Thread{

    private final Socket socket;
    private final MessagesSender sender;
    private final Boolean isClosed;

    public ServerThread(Socket _socket, ClientsList _clientsList){
        socket = _socket;
        sender = new MessagesSender(_clientsList);
        isClosed = false;
    }

    //  Поток пересылки сообщений: польз -> сервер |этот поток| -> польз
    @Override
    public void run(){
        try {
            while (!isClosed){
                // Получение сообщений от пользователей
                InputStream in = socket.getInputStream();
                byte[] bytes = new byte[32 * 2000];
                String message =  new String(bytes,0, in.read(bytes));
                // Вывод поступающих сообщений в консоль
                System.out.println(message);
                // Рассылка сообщения всем пользователям
                sender.resendMessageToAllUsersFromServer(message, isClosed);
            }
            Thread.currentThread().interrupt();
        }catch (IOException e) {System.out.println("#Error ServerThread -> run()");}
    }

    // Метод закрытия потоков ввода и вывода
    public void stopThread(ServerSocket _serverSocket, Thread _startThread){
        // Способ закрытия, при подключённых пользователях (мин = 1)
        if (_serverSocket != null && _startThread != null) {
            try {
                _startThread.interrupt();
                _serverSocket.close();
            } catch (IOException e) {System.out.println("#Error ServerThread -> stopThread()");}
        }
        else if (_startThread == null && _serverSocket != null){
            try {
                _serverSocket.close();
            }catch (IOException e){System.out.println("#Error ServerThread -> stopThread()");}
        }
        else if (_startThread != null && _serverSocket == null){
            _startThread.interrupt();
        }
    }
}
