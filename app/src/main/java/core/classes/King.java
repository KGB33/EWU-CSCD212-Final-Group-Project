package core.classes;

import core.enums.Color;

/** Represents a King chess piece. */
public class King extends BasePiece {
  private static char whiteIcon = '\u2654';
  private static char blackIcon = '\u265A';

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
    // return ((Math.abs(fromFile - toFile) <= 1 ^ Math.abs(fromRank - toRank)
    // <= 1) ^ (Math.abs(fromFile - toFile) <= 1 && Math.abs(fromRank - toRank)
    // <= 1));
  }
}
