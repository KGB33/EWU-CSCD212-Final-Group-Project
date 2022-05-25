package core.classes;

import core.enums.Color;

/** Pawn */
public class Pawn extends BasePiece {
  private static char whiteIcon = '\u2659';
  private static char blackIcon = '\u265F';

  /**
   * Creates a Pawn with the provided color.
   *
   * @param color The color the pawn will be.
   * @throws IllegalArgumentException if color is null
   */
  public Pawn(final Color color) {
    if (color == null) {
      throw new IllegalArgumentException("Color cannot be null.");
    }
    this.icon = color.equals(Color.BLACK) ? Pawn.blackIcon : Pawn.whiteIcon;
  }

  /**
   * Returns true if the given move is valid for a pawn.
   *
   * @param b The board representing the state of the game.
   * @param m The move to check.
   * @throws IllegalArgumentException if any args are null.
   */
  @Override
  public boolean isValidMove(final Board b, final Move m) {
    return false;
  }
}
