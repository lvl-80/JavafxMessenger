package com.lvl80.fxmessenger.userTypes;

public class Client {

    private String login;
    private String password;
    private String nickname;

    public Client(String _login, String _password, String _nickname){
        login = _login;
        password = _password;
        nickname = _nickname;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getNickname() {
        return nickname;
    }
}
