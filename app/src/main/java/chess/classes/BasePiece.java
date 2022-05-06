package chess.classes;

import chess.enums.Color;

/** The parent class for all chess pieces. */
public abstract class BasePiece {
  // A unicode chess icon i.e. "♔", "♟️"", etc.
  protected char icon;
  protected Color color;

  public boolean isValidMove(Board b, Move m) {
    // Cannot move to the square it is on.
    if (m.getFromFile() == m.getFile() && m.getFromRank() == m.getRank()) {
      return false;
    }
    // If the move is a capture, check that the captured piece exists & is a different color.
    if (m.isCapture()) {
      BasePiece toSquare = b.getSquare(m.getRank(), m.getFile());
      BasePiece fromSquare = b.getSquare(m.getFromRank(), m.getFromFile());
      if (toSquare == null || fromSquare == null || toSquare.color == fromSquare.color) {
        return false;
      }
    }
    return true;
  }

  public String toString() {
    return String.valueOf(this.icon);
  }
}
