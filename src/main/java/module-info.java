module hellofx {
    requires javafx.controls;
    requires javafx.fxml;
    requires io.cucumber.messages;

    opens domain to javafx.fxml; // Gives access to fxml files
    exports domain;
    exports app;
    opens app to javafx.fxml; // Exports the class inheriting from javafx.application.Application
}