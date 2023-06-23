module cafe.cafeshop {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires jasperreports;

    opens cafe.cafeshop to javafx.fxml;
    exports cafe.cafeshop;
}