module com.lvl80.fxmessenger {
    requires javafx.controls;
    requires javafx.fxml;
//    requires java.sql;

//    requires org.kordamp.bootstrapfx.core;

    opens com.lvl80.fxmessenger.connection to javafx.fxml;
    exports com.lvl80.fxmessenger.connection;

    opens com.lvl80.fxmessenger.server to javafx.fxml;
    exports com.lvl80.fxmessenger.server;

    opens com.lvl80.fxmessenger.messenger to javafx.fxml;
    exports com.lvl80.fxmessenger.messenger;

    opens com.lvl80.fxmessenger.userTypes to javafx.fxml;
    exports com.lvl80.fxmessenger.userTypes;

//    opens com.lvl80.fxmessenger.database to java.sql;
//    exports com.lvl80.fxmessenger.database;
}