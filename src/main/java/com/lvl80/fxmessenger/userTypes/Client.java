package com.lvl80.fxmessenger.userTypes;

public class Client {

    private final String login;
    private final String password;
    private final String nickname;

    public Client(String _nickname, String _login, String _password){
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
