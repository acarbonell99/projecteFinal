module com.projecte.tpv {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.core;
    requires org.kordamp.bootstrapfx.core;

    requires eu.hansolo.tilesfx;
    requires java.sql;
    requires org.mongodb.driver.sync.client;
    requires org.mongodb.bson;
    requires org.mongodb.driver.core;

    opens com.projecte.tpv to javafx.fxml;
    exports com.projecte.tpv;
    opens com.projecte.tpv.model to javafx.fxml;
    exports com.projecte.tpv.model;
}