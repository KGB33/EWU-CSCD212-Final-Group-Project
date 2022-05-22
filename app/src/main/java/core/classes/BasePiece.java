package core.classes;

import core.enums.Color;

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

  public boolean isValidMove(Board b, Move m) {
    // Cannot move to the square it is on.
    if (m.getFromFile() == m.getFile() && m.getFromRank() == m.getRank()) {
      return false;
    }
    // If the move is a capture, check that the captured piece exists & is a different color.
    if (m.isCapture()) {
      BasePiece toSquare = b.getSquare(m.getFile(), m.getRank());
      if (toSquare == null || toSquare.color.equals(this.color)) {
        return false;
      }
    }
    return true;
  }

  public Color getColor(){
    return this.color;
  }

  public String toString() {
    return String.valueOf(this.icon);
  }
}
