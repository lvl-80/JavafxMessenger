package com.lvl80.fxmessenger.connection;

import com.lvl80.fxmessenger.userTypes.Client;
import com.lvl80.fxmessenger.userTypes.ClientsList;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class MessagesSender {
    // Лист со всеми клиентами для перебора и рассылки
    private ClientsList clientsList = null;

    public MessagesSender(){}

    public MessagesSender(ClientsList _clientsList){
        clientsList = _clientsList;
    }

    // Отправка сообущений всем юзерам с сервера
    // [польз1, польз2, польз3] -> пользN -> пользN.отправить(сообщение)
    // если пользователя нет, то:
    // [польз1, польз2, польз3] -> пользN == null -> [польз1, польз2, польз3].remove(пользN)
    public synchronized void resendMessageToAllUsersFromServer(String _message, Boolean _isClosed) throws IOException {
        for (Socket _socket : clientsList.getClientsList()){
            if (!_isClosed) {
                OutputStream out = _socket.getOutputStream();
                PrintWriter printWriter = new PrintWriter(out);
                printWriter.write(_message);
                printWriter.flush();
            }
            else clientsList.removeClient(_socket);


        }
    }

    // Отправка сообщений серверу со стороны клиента
    // польз -> _полеВвода.получитьТекст() -> сервер
    public synchronized void sendMessageToServerFromUser(Client _client, Socket _socket, TextField _textField){
        try {
            String message = _client.getNickname() + ": " + _textField.getText();
            PrintWriter printWriter = new PrintWriter(_socket.getOutputStream());
            printWriter.write(message);
            printWriter.flush();
            System.out.println("Отправлено сообщение " + _textField.getText());
        }catch (IOException e){System.out.println("#Error MessagesSender -> sendMessageToServerFromUser()");}
    }
}
