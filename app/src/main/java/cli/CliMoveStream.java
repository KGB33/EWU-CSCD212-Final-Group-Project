package cli;

import core.classes.Board;
import core.classes.Move;
import core.interfaces.MoveStream;
import java.io.PrintStream;
import java.text.ParseException;
import java.util.Scanner;

public class CliMoveStream implements MoveStream {
  Scanner kb;
  PrintStream out;

  public CliMoveStream(Scanner input) {
    this(input, System.out);
  }

  public CliMoveStream(Scanner input, PrintStream output) {
    if (input == null || output == null) {
      throw new IllegalArgumentException("Input & Output cannot be null.");
    }
    this.kb = input;
    this.out = output;
  }

  @Override
  public Move getNextMove(Board b) {
    String input;
    Move m = null;
    this.out.println(b.toString());
    this.out.println(
        "\n\nPlease enter a move in Algebraic Notation - including the from rank/file  ");
    do {
      input = this.kb.nextLine().trim();
      try {
        m = Move.parse(input);
      } catch (ParseException e) {
        this.out.println("Could not parse notation, please try again (q to quit).\n");
      }
    } while (m == null);
    return m;
  }
}
