package chess;

import core.classes.Board;
import core.classes.Move;
import gui.ChessApplication;

import java.text.ParseException;
import java.util.Scanner;

public class Main {

  public static void main(String[] args) {
    String key = "";
    for(String str: args){
      if(str.equals("cli")) { key = str; }
    }
    if (args.length >= 1 && key.equals("cli")) {
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
      ChessApplication.lauchGUI();
    }
  }
}
