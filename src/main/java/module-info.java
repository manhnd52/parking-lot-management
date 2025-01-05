module QLBDX {
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.swing;
    requires java.naming;
    requires org.hibernate.orm.core;
    requires jakarta.persistence;
    requires javafx.web;
    requires java.sql;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;

    opens model to org.hibernate.orm.core;
    opens UI.Staff.Login to javafx.fxml;
    exports UI.Staff.Login;
    opens UI.Staff.MainMenu to javafx.fxml;
    exports UI.Staff.MainMenu;
    opens UI.Student to javafx.fxml;
    exports UI.Student;
    exports UI;
    opens UI to javafx.fxml;


}