package cli;

import ai.AiFacade;
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
    int gameType;

    do {
      System.out.print("Please choose a game type.\n1) Two player\n2) Vs. AI\n3) Quit\n");
      gameType = kb.nextInt();
    } while (gameType < 1 || gameType > 3);

    if (gameType == 1) {
      do {
        out.println(b.toString());
        out.println(
            "\n\nPlease enter a move in Algebraic Notation - including the from rank/file  ");
        do {
          input = kb.nextLine().trim();
        } while (input.equals(""));
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
    } else if (gameType == 2) {
      input = kb.nextLine().trim();
      AiFacade ai = new AiFacade(b);
      boolean moved;
      System.out.println("You are playing as white\n");
      do {
        do {
          moved = false;
          System.out.println(b.toString());
          out.println(
              "\n\nPlease enter a move in Algebraic Notation - including the from rank/file  ");
          do {
            input = kb.nextLine().trim();
          } while (input.equals(""));
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
          } else {
            moved = true;
          }
        } while (!moved);

        System.out.println(b.toString() + "\n\n AI playing \n");

        try {
          ai.playTurn(b);
        } catch (ParseException e) {
          System.out.println("AI move failed");
        }
      } while (!b.isGameOver());
    } else {
      kb.close();
      return;
    }

    kb.close();

    String winningMessage =
        b.getWinner().equals(Color.WHITE) ? "    White Won!  " : "    Black Won!  ";
    out.println("*=*=*=*=*=*=*=*=*=*=");
    out.println("  Congratulations!  ");
    out.println(winningMessage);
    out.println("*=*=*=*=*=*=*=*=*=*=");
  }
}
