package chess;

import cli.CLI;
import gui.ChessApplication;
import java.util.Scanner;

public class Main {

  public static void main(String[] args) {
    String key = "";
    for (String str : args) {
      if (str.equals("cli")) {
        key = str;
      }
    }
    if (args.length >= 1 && key.equals("cli")) {
      System.out.println("Starting cli... ");
      Scanner kb = new Scanner(System.in);
      CLI.run(kb);
    } else {
      System.out.println("Starting GUI...");
      ChessApplication.lauchGUI();
    }
  }
}
