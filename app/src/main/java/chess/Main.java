package chess;

import core.classes.Board;
import core.classes.Move;
import core.enums.Color;
import java.text.ParseException;
import java.util.Scanner;

/** Entry point for the chess app. */
public class Main {

  public static void main(final String[] args) {
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
        kb.close();
        return;
      }
      try {
        m = Move.parse(input);
      } catch (ParseException e) {
        System.out.println("Could not parse notation, please try again (q to quit).\n");
        continue;
      }
      if (!b.move(m)) {
        System.out.println("Could not move the piece, please try again (q to quit).\n");
      }

    } while (!b.isGameOver());
    kb.close();

    String winningMessage =
        b.getWinner().equals(Color.WHITE) ? "    White Won!  " : "    Black Won!  ";
    System.out.println("*=*=*=*=*=*=*=*=*=*=");
    System.out.println("  Congratulations!  ");
    System.out.println(winningMessage);
    System.out.println("*=*=*=*=*=*=*=*=*=*=");
  }
}
