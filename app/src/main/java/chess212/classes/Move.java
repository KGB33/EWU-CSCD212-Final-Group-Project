package chess212.classes;

import java.text.ParseException;

/**
 * Represents the algebraic notation of a move.
 *
 * <p>https://en.wikipedia.org/wiki/Algebraic_notation_(chess)
 */
public class Move {
  // TODO: Consider changing rank, file, and movedPiece to Enums.
  private int rank;
  private char file;
  private char movedPiece;

  private boolean isCapture;
  private boolean isCheck;
  private boolean isCheckmate;

  // Less likely moves
  private char fromFile;
  private char fromRank;
  private char pawnPromotedTo;

  /** Default constructor is private, use Move.parse(String notation) to generate a move. */
  private Move() {}
  ;

  /**
   * Parses a Move from an algebraic notation string.
   *
   * @param notation the algebraic notation for the given move.
   * @return a Move object representing the notation.
   * @throws IllegalArgumentException if notation is null.
   * @throws ParseException if the given notation cannot be parsed
   */
  public static Move parse(String notation) {
    return new Move();
  }

  /** Returns the algebraic notation for a given move. */
  public String toString() {
    return "";
  }
}
