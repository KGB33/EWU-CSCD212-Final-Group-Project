package chess.classes;

import chess.enums.Color;

/** The parent class for all chess pieces. */
public abstract class BasePiece {
  /*A unicode chess icon i.e.
   *
   * White King:   \u2654
   * White Queen:  \u2655
   * White Rook:   \u2656
   * White Bishop: \u2657
   * White Knight: \u2658
   * White Pawn:   \u2659
   *
   * Black King:   \u265A
   * Black Queen:  \u265B
   * Black Rook:   \u265C
   * Black Bishop: \u265D
   * Black Knight: \u265E
   * Black Pawn:   \u265F
   *
   */
  protected char icon;
  protected Color color;

  public abstract boolean isValidMove(char fromFile, char fromRank, char toFile, char toRank);

  public String toString() {
    return String.valueOf(this.icon);
  }
}
