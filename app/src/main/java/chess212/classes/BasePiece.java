package chess212.classes;

import chess212.enums.Color;

public abstract class BasePiece {
  // A unicode chess icon i.e. "♔", "♟️"", etc.
  protected static char whiteIcon;
  protected static char blackIcon;
  protected char icon;
  protected Color color;

  public String toString() {
    return String.valueOf(this.icon);
  }
}
