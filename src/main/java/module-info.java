module org.hmt {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.hmt to javafx.fxml;
    exports org.hmt;
    exports org.hmt.controllers;
    opens org.hmt.controllers to javafx.fxml;
}