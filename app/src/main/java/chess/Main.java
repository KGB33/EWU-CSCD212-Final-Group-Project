package chess;

import core.classes.Board;
import core.classes.Move;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.ParseException;
import java.util.Scanner;

public class Main extends Application {
  @Override
  public void start(Stage stage) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("ChessGui.fxml"));
    Scene scene = new Scene(fxmlLoader.load(), 700, 600);
    stage.setTitle("Hello!");
    stage.setScene(scene);
    stage.show();
  }

  public static void main(String[] args) {
    if (args.length >= 1 && args[0].equals("cli")) {
      System.out.println("Starting cli... ");
      Scanner kb = new Scanner(System.in);
      Board b = new Board();
      Move m;
      String input;
      do {
        System.out.println(b.toString());
        System.out.println(
                "\n\nPlease enter a move in Algebraic Notation - including the from rank/file  ");
        input = kb.nextLine().trim();
        if (input.equals("q")) {
          break;
        }
        try {
          m = Move.parse(input);
        } catch (ParseException e) {
          System.out.println("Could not parse notation, please try again (q to quit).\n");
          continue;
        }
        if (!b.move(m)) {
          System.out.println("Could not move the peice, please try again (q to quit).\n");
        }

      } while (true);
      kb.close();
    }
    else {
      System.out.println("Starting GUI...");
      launch();
    }
  }
}