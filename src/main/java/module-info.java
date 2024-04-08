module hellofx {
    requires javafx.controls;
    requires javafx.fxml;

    opens domain to javafx.fxml; // Gives access to fxml files
    exports domain; // Exports the class inheriting from javafx.application.Application
}