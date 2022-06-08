package core.classes;

import core.enums.Color;
import java.text.ParseException;
import java.util.ArrayList;

/** The parent class for all chess pieces. */
public abstract class BasePiece {
  /*A unicode chess icon i.e.
   *
   * White King:   \u2654
   * White Queen:  \u2655
   * White Rook:   \u2656
   * White Bishop: \u2657
   * White Knight: \u2658
   * White Pawn:   \u2659
   *
   * Black King:   \u265A
   * Black Queen:  \u265B
   * Black Rook:   \u265C
   * Black Bishop: \u265D
   * Black Knight: \u265E
   * Black Pawn:   \u265F
   *
   */
  protected char icon;
  protected Color color;
  protected char[] current = {'0', '0'};
  protected int score;

  public boolean isValidMove(Board b, Move m) {
    // Cannot move to the square it is on.
    if (m.getFromFile() == m.getFile() && m.getFromRank() == m.getRank()) {
      return false;
    }
    // If the move is a capture, check that the captured piece exists & is a
    // different color. Or if the move is not a capture, make sure that its
    // moving to an empty square.
    BasePiece toSquare = b.getSquare(m.getFile(), m.getRank());
    return (!m.isCapture() && toSquare == null)
        || (m.isCapture() && toSquare != null && !toSquare.color.equals(this.color));
  }

  public boolean isCheck(Board b, char file, char rank) {
    BasePiece toCheck = b.getSquare(file, rank);
    if (toCheck == null) {
      return false;
    }
    if (this.color == toCheck.getColor()) {
      return false;
    }

    return (toCheck.getClass().equals(King.class));
  }

  public String toString() {
    return String.valueOf(this.icon);
  }

  public Color getColor() {
    return this.color;
  }

  public int getScore() {
    return this.score;
  }

  public char[] getCurrent() {
    return this.current;
  }

  public void setCurrent(char file, char rank) {
    this.current[0] = file;
    this.current[1] = rank;
  }
}
