package core.classes;

import core.enums.Color;

/** Queen */
public class Queen extends BasePiece {
  private static char whiteIcon = '\u2655';
  private static char blackIcon = '\u265B';

  private static final int score = 80;

  private char[] current = {'0', '0'};

  public Queen(final Color color) {
    if (color == null) {
      throw new IllegalArgumentException();
    }
    this.color = color;
    if (color.equals(Color.BLACK)) {
      this.icon = Queen.blackIcon;
    }
    if (color.equals(Color.WHITE)) {
      this.icon = Queen.whiteIcon;
    }
    this.current = getCurrent();
  }

  @Override
  public boolean isValidMove(Board b, Move m) {
    if (!super.isValidMove(b, m)) {
      return false;
    }
    int rankDelta = Math.abs(m.getFromRank() - m.getRank());
    int fileDelta = Math.abs(m.getFromFile() - m.getFile());
    if (!(rankDelta == 0 ^ fileDelta == 0 ^ fileDelta == rankDelta)) {
      return false;
    }

    // ensure that no pieces are being hopped over
    int rankNorm = rankDelta != 0 ? (m.getRank() - m.getFromRank()) / rankDelta : 0;
    int fileNorm = fileDelta != 0 ? (m.getFile() - m.getFromFile()) / fileDelta : 0;
    for (int r = m.getFromRank() + rankNorm, f = m.getFromFile() + fileNorm;
        (char) r != m.getRank() || (char) f != m.getFile();
        r = r + rankNorm, f = f + fileNorm) {
      if (b.getSquare((char) f, (char) r) != null) {
        return false;
      }
    }
    return true;
  }

  @Override
  public char[] getCurrent() {
    return this.current;
  }

  @Override
  public void setCurrent(char file, char rank) {
    this.current[0] = file;
    this.current[1] = rank;
  }

  @Override
  public int getScore() {
    return score;
  }
}
