package core.classes;

import java.text.ParseException;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Represents the algebraic notation of a move.
 *
 * <p>https://en.wikipedia.org/wiki/Algebraic_notation_(chess)
 *
 * <p>Note: Consider turning Parse method into a MoveFactory
 */
public final class Move {
  // Consider changing rank, file, and movedPiece to Enums.
  private char rank;
  private char file;
  private char movedPiece;

  private boolean isCapture;
  private boolean isCheck;
  private boolean isCheckmate;

  // Less likely moves
  private char fromFile;
  private char fromRank;
  private char pawnPromotedTo;

  // Castels
  private boolean isQueenSideCastle;
  private boolean isKingSideCastle;

  /** All possible characters in algebraic notation. */
  private static final Set<Character> validCharacters =
      // file     +  ranks     + pieces  + other
      ("abcdefgh" + "12345678" + "KQBNR" + "x0-+#")
          .chars()
          .mapToObj(e -> (char) e)
          .collect(Collectors.toSet());

  /** Default constructor is private, use Move.parse(String notation) to generate a move. */
  private Move() {}

  /**
   * Parses a Move from an algebraic notation string.
   *
   * @param notationIn the algebraic notation for the given move.
   * @return a Move object representing the notation.
   * @throws IllegalArgumentException if notation is null.
   * @throws ParseException if the given notation cannot be parsed
   */
  public static Move parse(final String notationIn) throws ParseException {
    String notation = notationIn;
    if (notation == null || notation.isEmpty()) {
      throw new IllegalArgumentException("Notation cannot be null or empty.");
    }

    // Ensure all characters in the notation are valid.
    if (!Move.validCharacters.containsAll(
        notation.chars().mapToObj(e -> (char) e).collect(Collectors.toSet()))) {
      throw new ParseException(notation, 0);
    }
    Move m = new Move();
    // Special cases
    if (notation.equals("0-0-0")) {
      m.isQueenSideCastle = true;
      return m;
    }
    if (notation.equals("0-0")) {
      m.isKingSideCastle = true;
      return m;
    }

    // Get Check / checkmate from end of string
    if ('+' == notation.charAt(notation.length() - 1)) {
      m.isCheck = true;
      notation = notation.substring(0, notation.length() - 1);
    }
    if ('#' == notation.charAt(notation.length() - 1)) {
      m.isCheckmate = true;
      notation = notation.substring(0, notation.length() - 1);
    }

    // Check if a pawn is promoted
    if (Character.isUpperCase(notation.charAt(notation.length() - 1))) {
      m.pawnPromotedTo = notation.charAt(notation.length() - 1);
      notation = notation.substring(0, notation.length() - 1);
    }

    // Get Moved piece - If the first letter is not capitalized a pawn moved.
    if (Character.isUpperCase(notation.charAt(0))) {
      m.movedPiece = notation.charAt(0);
      notation = notation.substring(1);
    }

    // Check if the move is a capture
    if (notation.indexOf('x') != -1) {
      m.isCapture = true;
      notation = notation.replace("x", "");
    }

    // Get Rank / File moved to
    m.rank = notation.charAt(notation.length() - 1);
    m.file = notation.charAt(notation.length() - 2);
    notation = notation.substring(0, notation.length() - 2);

    // Check if the to rank/file is notated.
    if (notation.length() == 0) {
      return m;
    }

    // Get from file
    if (Character.isLetter(notation.charAt(0))) {
      m.fromFile = notation.charAt(0);
      notation = notation.substring(1);
    }
    if (notation.length() == 0) {
      return m;
    }

    if (Character.isDigit(notation.charAt(0))) {
      m.fromRank = notation.charAt(0);
      notation = notation.substring(1);
    }
    if (notation.length() == 0) {
      return m;
    }

    if (notation.length() != 0) {
      throw new ParseException(notation, 0);
    }

    return m;
  }

  /** Returns the algebraic notation for a given move. */
  public String toString() {
    if (this.isKingSideCastle) {
      return "0-0";
    }
    if (this.isQueenSideCastle) {
      return "0-0-0";
    }
    return ""
        + (this.movedPiece == '\u0000' ? "" : this.movedPiece)
        + (this.fromFile == '\u0000' ? "" : this.fromFile)
        + (this.fromRank == '\u0000' ? "" : this.fromRank)
        + (this.isCapture ? "x" : "")
        + this.file
        + this.rank
        + (this.pawnPromotedTo == '\u0000' ? "" : this.pawnPromotedTo)
        + (this.isCheck ? "+" : "")
        + (this.isCheckmate ? "#" : "");
  }

  public char getRank() {
    return rank;
  }

  public char getFile() {
    return file;
  }

  public char getMovedPiece() {
    return movedPiece;
  }

  public boolean isCapture() {
    return isCapture;
  }

  public boolean isCheck() {
    return isCheck;
  }

  public boolean isCheckmate() {
    return isCheckmate;
  }

  public char getFromFile() {
    return fromFile;
  }

  public char getFromRank() {
    return fromRank;
  }

  public char getPawnPromotedTo() {
    return pawnPromotedTo;
  }

  public boolean isQueenSideCastle() {
    return isQueenSideCastle;
  }

  public boolean isKingSideCastle() {
    return isKingSideCastle;
  }
}
