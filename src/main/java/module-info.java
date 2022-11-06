module org.genesiscode.projectpraticefour {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.genesiscode.projectpraticefour to javafx.fxml;
    exports org.genesiscode.projectpraticefour;
}