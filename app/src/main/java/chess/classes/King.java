package chess.classes;

import chess.enums.Color;

/** Represents a King chess piece. */
public class King extends BasePiece {
  private static char whiteIcon = '♔';
  private static char blackIcon = '♚';

  /**
   * Constructor for a King.
   *
   * @param color The color the piece should be.
   * @throws IllegalArgumentException if color is null.
   */
  public King(final Color color) {
    if (color == null) {
      throw new IllegalArgumentException("Cannot have a null color");
    }
    this.color = color;
    if (color.equals(Color.BLACK)) {
      this.icon = King.blackIcon;
    }
    if (color.equals(Color.WHITE)) {
      this.icon = King.whiteIcon;
    }
  }

  /** A king can move one square in any direction. */
  @Override
  public boolean isValidMove(Board b, Move m) {
    if (!super.isValidMove(b, m)) {
      return false;
    }
    if (Math.abs(m.getFromFile() - m.getFile()) > 1) {
      return false;
    }
    if (Math.abs(m.getFromRank() - m.getRank()) > 1) {
      return false;
    }
    return true;
  }
}
