package com.lvl80.fxmessenger.messenger;

import com.lvl80.fxmessenger.userTypes.Client;
import javafx.scene.control.TextArea;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class MessengerThread extends Thread{

    private InputStream in;
    private PrintWriter printWriter;
    private TextArea textArea;
    private Boolean isClosed;
    private Client client;

    public MessengerThread(Socket _socket, TextArea _textArea, Client _client){
        try {
            in = _socket.getInputStream();
            printWriter = new PrintWriter(_socket.getOutputStream());
            textArea = _textArea;
            isClosed = false;
            client = _client;
            // Перенос текста в textArea
            textArea.setWrapText(true);
        }catch (IOException e){System.out.println("#Error MessangerThread -> конструктор");}
    }

    // Поток получения и записи сообщений: польз -> сервер -> польз |этот поток|
    @Override
    public void run(){
        // Сообщение при присоединении
        printWriter.write(client.getNickname() + " присоединился к чату");
        printWriter.flush();
        // Постоянный поток приёма и записи в TextArea
        try {
            while (!isClosed){
                byte[] bytes = new byte[32 * 2000];
                String message = new String(bytes, 0, in.read(bytes));
                textArea.appendText(message + "\n\n");
            }
            Thread.currentThread().interrupt();
        }catch (IOException e){System.out.println("#Error MessangerThread -> run()");}
    }

    // Метод закрытия потоков ввода и вывода
    public void stopThread(){
        try {
            isClosed = true;
            in.close();
            printWriter.close();
            Thread.currentThread().interrupt();
        }catch (IOException e){System.out.println("#Error MessangerThread -> stopThread()");}
    }
}
