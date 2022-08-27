module schedulingapplication.schedulingapplication {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens schedulingapplication.schedulingapplication to javafx.fxml;
    opens schedulingapplication.schedulingapplication.models to javafx.fxml;
    opens schedulingapplication.schedulingapplication.utilities to javafx.fxml;
    exports schedulingapplication.schedulingapplication;
    exports schedulingapplication.schedulingapplication.controllers;
    exports schedulingapplication.schedulingapplication.models;
    exports schedulingapplication.schedulingapplication.utilities;
    opens schedulingapplication.schedulingapplication.controllers to javafx.fxml;
}