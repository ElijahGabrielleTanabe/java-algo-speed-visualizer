module com.github.elijahgabrielletanabe {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    opens com.github.elijahgabrielletanabe to javafx.fxml;
    exports com.github.elijahgabrielletanabe;
}
