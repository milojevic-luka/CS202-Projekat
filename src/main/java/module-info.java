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
    requires org.jsoup;
    requires java.desktop;

    opens application to javafx.fxml;
    exports application;
    opens application.controllers to javafx.fxml;
    exports application.controllers;
    opens application.entities to javafx.fxml;
    exports application.entities;

}