package chess.classes;

import chess.enums.Color;

/** Rook */
public class Rook extends BasePiece {
  private static char whiteIcon = '\u2656';
  private static char blackIcon = '\u265C';

  public Rook(final Color color) {
    if (color == null) {
      throw new IllegalArgumentException();
    }
    this.color = color;
    this.icon = color.equals(Color.WHITE) ? Rook.whiteIcon : Rook.blackIcon;
  }

  @Override
  public boolean isValidMove(Board b, Move m) {
    if (!super.isValidMove(b, m)) {
      return false;
    }
    return true;
  }
}
