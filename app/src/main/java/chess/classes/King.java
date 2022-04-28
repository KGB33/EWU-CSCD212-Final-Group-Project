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
}
