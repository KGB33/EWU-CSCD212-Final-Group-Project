package core.classes;

import core.enums.Color;

import java.text.ParseException;
import java.util.ArrayList;

/** Represents a King chess piece. */
public class King extends BasePiece {
  private static char whiteIcon = '\u2654';
  private static char blackIcon = '\u265A';

  private static final int score = 50;

  private char[] current = {'0', '0'};

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
    this.current = getCurrent();
  }

  /** A king can move one square in any direction. */
  @Override
  public boolean isValidMove(Board b, Move m) {
    if (!super.isValidMove(b, m)) {
      return false;
    }
    if (Math.abs(m.getFromFile() - m.getFile()) > 1) {
      return false;
    }
    if (Math.abs(m.getFromRank() - m.getRank()) > 1) {
      return false;
    }
    return true;
    // return ((Math.abs(fromFile - toFile) <= 1 ^ Math.abs(fromRank - toRank)
    // <= 1) ^ (Math.abs(fromFile - toFile) <= 1 && Math.abs(fromRank - toRank)
    // <= 1));
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
  // not efficient
  public ArrayList<Move> validMoves(Board b) throws ParseException {
    char[] files = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'};
    char[] ranks = {'8', '7', '6', '5', '4', '3', '2', '1'};
    Move toCheck;
    Move toCheckTake;
    ArrayList<Move> moves = new ArrayList<>();
    for (char file : files)
    {
      for (char rank : ranks)
      {
        toCheck = Move.parse(Character.toString(current[0]) + current[1] + file + rank);
        toCheckTake = Move.parse(Character.toString(current[0]) + current[1] + "x" + file + rank);
        if(isValidMove(b, toCheck))
        {
          moves.add(toCheck);
        }
        else if (isValidMove(b, toCheckTake)) {
          moves.add(toCheckTake);
        }
      }
    }
    return moves;
  }

  @Override
  public int getScore()
  {
    return score;
  }
}
