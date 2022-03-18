package com.lvl80.fxmessenger.userTypes;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientsList {
    private final List<Socket> clientsList;
    public ClientsList(){
        clientsList = new ArrayList<>();
    }
    public synchronized List<Socket> getClientsList(){
        return clientsList;
    }
    public synchronized void addClient(Socket _socket){
        clientsList.add(_socket);
    }
    public synchronized void removeClient(Socket _socket){
        clientsList.remove(_socket);
    }
}
