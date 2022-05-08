package chess.classes;

import chess.enums.Color;

/** Bishop */
public class Bishop extends BasePiece {
  private static char whiteIcon = '\u2657';
  private static char blackIcon = '\u265D';

  public Bishop(final Color color) {
    if (color == null) {
      throw new IllegalArgumentException();
    }
    this.color = color;
    this.icon = color.equals(Color.WHITE) ? Bishop.whiteIcon : Bishop.blackIcon;
  }

  @Override
  public boolean isValidMove(Board b, Move m) {
    if (!super.isValidMove(b, m)) {
      return false;
    }
    return true;
  }
}
