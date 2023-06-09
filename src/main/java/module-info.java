module cafe.cafeshop {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens cafe.cafeshop to javafx.fxml;
    exports cafe.cafeshop;
}