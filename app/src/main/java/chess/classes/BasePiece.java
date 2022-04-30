package chess.classes;

import chess.enums.Color;

/** The parent class for all chess pieces. */
public abstract class BasePiece {
  // A unicode chess icon i.e. "♔", "♟️"", etc.
  protected char icon;
  protected Color color;

  public abstract boolean isValidMove(char fromFile, char fromRank, char toFile, char toRank);

  public String toString() {
    return String.valueOf(this.icon);
  }
}
