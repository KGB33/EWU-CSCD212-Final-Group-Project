module com.example.chessgui {
    requires javafx.controls;
    requires javafx.fxml;

    /*requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;*/

    //opens chess to javafx.fxml;
    exports com.example.chessgui;
    exports chess;
   // opens chess to javafx.fxml;
    opens com.example.chessgui to javafx.fxml;
}