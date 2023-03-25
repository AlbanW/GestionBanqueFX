module permissions {
    requires javafx.controls;
    requires javafx.fxml;

    opens modele to javafx.base;
    opens vues to javafx.fxml;
    exports pnt;
}