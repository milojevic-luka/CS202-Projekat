module com.luka.main {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;
    requires java.sql;
    requires io.github.cdimascio.dotenv.java;

//    opens com.luka.main to javafx.fxml;
//    exports com.luka.main;
    opens application to javafx.fxml;
    exports application;
    exports application.controllers;
    opens application.controllers to javafx.fxml;
}