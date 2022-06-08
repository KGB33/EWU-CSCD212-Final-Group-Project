package core.classes;

import core.enums.Color;

/** Knight */
public class Knight extends BasePiece {
  private static char whiteIcon = '\u2658';
  private static char blackIcon = '\u265E';

  public Knight(Color color) {
    if (color == null) {
      throw new IllegalArgumentException();
    }
    this.color = color;
    this.icon = color.equals(Color.WHITE) ? Knight.whiteIcon : Knight.blackIcon;
  }

  @Override
  public boolean isValidMove(Board b, Move m) {
    if (!super.isValidMove(b, m)) {
      return false;
    }
    int rankDelta = Math.abs(m.getFromRank() - m.getRank());
    int fileDelta = Math.abs(m.getFromFile() - m.getFile());
    return (rankDelta == 2 && fileDelta == 1) || (rankDelta == 1 && fileDelta == 2);
  }
}
