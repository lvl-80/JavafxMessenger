CREATE DATABASE Messenger;
USE Messenger;
CREATE TABLE Users(
    Id INT PRIMARY KEY AUTO_INCREMENT,
    Nickname VARCHAR(60),
    Login VARCHAR(60),
    Password VARCHAR(60)
);
