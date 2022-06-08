package chess;

import cli.CLI;
import core.classes.GameControler;
import core.enums.Color;
import core.interfaces.MoveStream;
import gui.ChessApplication;
import java.util.Scanner;

public class Main {

  public static void main(String[] args) {
    // Parse Flags
    boolean cliFlag = false;
    boolean twoPlayerFlag = false;
    for (String str : args) {
      if (str.equals("cli")) {
        cliFlag = true;
      }
      if (str.equals("two-player")) {
        twoPlayerFlag = true;
      }
    }
    // Create MoveStreams
    // FIXME: Hardcoded player1/2
    MoveStream p1 = MoveStreamFactory.create("cli");
    MoveStream p2 = MoveStreamFactory.create("ai");

    // Start Game
    GameControler gc = new GameControler(p1, p2);
    Color winner = gc.play();
    System.out.println(winner.toString() + " WON!");

    if (args.length >= 1 && "foo".equals("cli")) {
      System.out.println("Starting cli... ");
      Scanner kb = new Scanner(System.in);
      CLI.run(kb);
    } else {
      System.out.println("Starting GUI...");
      ChessApplication.lauchGUI();
    }
  }
}
