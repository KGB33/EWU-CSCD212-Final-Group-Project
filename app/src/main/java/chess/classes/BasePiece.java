package chess.classes;

import chess.enums.Color;

/** The parent class for all chess pieces. */
public abstract class BasePiece {
  // A unicode chess icon i.e. "♔", "♟️"", etc.
  protected char icon;
  protected Color color;

  public boolean isValidMove(Board b, Move m) {
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
