package chess;

import ai.AiFacade;
import core.classes.Board;
import core.classes.Move;
import ai.Computer;
import java.text.ParseException;
import java.util.Objects;
import java.util.Scanner;

/** Entry point for the chess app. */
public class Main {

  public static void main(final String[] args) {
    Scanner kb = new Scanner(System.in);
    Board b = new Board();
    Move m = null;
    String input;
    int gameType;

    do {
      System.out.print("Please choose a game type.\n1) Two player\n2) Vs. AI\n3) Quit\n");
      gameType = kb.nextInt();
    }while (gameType < 1 || gameType > 3);

    if (gameType == 1) {
      input = kb.nextLine().trim();
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
          System.out.println("Could not move the piece, please try again (q to quit).\n");
        }
      } while (true);
    }

    if (gameType == 2) {
      input = kb.nextLine().trim();
      AiFacade ai = new AiFacade(b);
      boolean moved;
      System.out.println("You are playing as white\n");
      do {
        do {
          moved = false;
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
            System.out.println("Could not move the piece, please try again (q to quit).\n");
          }
          else {
            moved = true;
          }
        }while (!b.move(Objects.requireNonNull(m)) && !moved);

        System.out.println(b.toString() + "\n\n AI playing \n");

        if (input.equals("q")) {
          break;
        }
        try {
          ai.playTurn(b);
        } catch (ParseException e) {
          System.out.println("AI move failed");
        }
      } while (true);
    }
    kb.close();
  }
}


