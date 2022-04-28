package chess212.classes;

import chess212.enums.Color;

public class King extends BasePiece {
  private static char whiteIcon = '♔';
  private static char blackIcon = '♚';

  public King(Color color) {
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
