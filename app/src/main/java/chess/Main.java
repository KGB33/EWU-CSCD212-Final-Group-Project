package chess;

import core.classes.Board;
import core.classes.Move;
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
}
