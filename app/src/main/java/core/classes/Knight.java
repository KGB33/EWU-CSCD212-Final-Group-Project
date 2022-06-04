package core.classes;

import core.enums.Color;

import java.text.ParseException;
import java.util.ArrayList;

/** Knight */
public class Knight extends BasePiece {
  private static char whiteIcon = '\u2658';
  private static char blackIcon = '\u265E';

  private static final int score = 30;

  private char[] current = {'0', '0'};

  public Knight(Color color) {
    if (color == null) {
      throw new IllegalArgumentException();
    }
    this.color = color;
    this.icon = color.equals(Color.WHITE) ? Knight.whiteIcon : Knight.blackIcon;
    this.current = getCurrent();
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
    Move toCheck;
    Move toCheckTake;
    ArrayList<Move> moves = new ArrayList<>();
    for (char file : files)
    {
      for (int i = 1; i <= 8; i++)
      {
        toCheck = Move.parse(Character.toString(current[0]) + current[1] + file + i);
        toCheckTake = Move.parse(Character.toString(current[0]) + current[1] + "x" + file + i);
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
