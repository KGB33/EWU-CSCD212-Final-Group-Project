package gui;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ChessApplication extends Application {
  @Override
  public void start(Stage stage) throws IOException {
    FXMLLoader fxmlLoader =
        new FXMLLoader(ChessApplication.class.getResource("/gui/ChessGui.fxml"));
    Scene scene = new Scene(fxmlLoader.load(), 700, 600);
    stage.setTitle("Chess!");
    stage.setScene(scene);
    stage.show();
  }

  public static void lauchGUI() {
    launch();
  }
}
