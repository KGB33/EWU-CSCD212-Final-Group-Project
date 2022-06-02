package cli;

import core.classes.Board;
import core.classes.Move;
import core.enums.Color;
import java.io.PrintStream;
import java.text.ParseException;
import java.util.Scanner;

/** Entry point for the chess app. */
public class CLI {

  public static void run(Scanner kb) {
    CLI.run(kb, System.out);
  }

  public static void run(Scanner kb, PrintStream out) {
    Board b = new Board();
    Move m;
    String input;
    do {
      out.println(b.toString());
      out.println("\n\nPlease enter a move in Algebraic Notation - including the from rank/file  ");
      input = kb.nextLine().trim();
      if (input.equals("q")) {
        kb.close();
        return;
      }
      try {
        m = Move.parse(input);
      } catch (ParseException e) {
        out.println("Could not parse notation, please try again (q to quit).\n");
        continue;
      }
      if (!b.move(m)) {
        out.println("Could not move the piece, please try again (q to quit).\n");
      }

    } while (!b.isGameOver());
    kb.close();

    String winningMessage =
        b.getWinner().equals(Color.WHITE) ? "    White Won!  " : "    Black Won!  ";
    out.println("*=*=*=*=*=*=*=*=*=*=");
    out.println("  Congratulations!  ");
    out.println(winningMessage);
    out.println("*=*=*=*=*=*=*=*=*=*=");
  }
}
