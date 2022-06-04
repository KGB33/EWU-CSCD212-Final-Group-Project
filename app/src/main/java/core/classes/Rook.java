package core.classes;

import core.enums.Color;

import java.text.ParseException;
import java.util.ArrayList;

/** Rook */
public class Rook extends BasePiece {
  private static char whiteIcon = '\u2656';
  private static char blackIcon = '\u265C';

  private static final int score = 60;

  private char[] current = {'0', '0'};

  public Rook(final Color color) {
    if (color == null) {
      throw new IllegalArgumentException();
    }
    this.color = color;
    this.icon = color.equals(Color.WHITE) ? Rook.whiteIcon : Rook.blackIcon;
    this.current = getCurrent();
  }

  @Override
  public boolean isValidMove(Board b, Move m) {
    if (!super.isValidMove(b, m)) {
      return false;
    }

    int rankDelta = Math.abs(m.getFromRank() - m.getRank());
    int fileDelta = Math.abs(m.getFromFile() - m.getFile());
    if (!(rankDelta == 0 ^ fileDelta == 0)) {
      return false;
    }
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
  // not efficient
  public ArrayList<Move> validMoves(Board b) throws ParseException {
    char[] files = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'};
    char[] ranks = {'8', '7', '6', '5', '4', '3', '2', '1'};
    Move toCheck;
    Move toCheckTake;
    ArrayList<Move> moves = new ArrayList<>();
    for (char file : files) {
      for (char rank : ranks) {
        toCheck = Move.parse(Character.toString(current[0]) + current[1] + file + rank);
        toCheckTake = Move.parse(Character.toString(current[0]) + current[1] + "x" + file + rank);
        if (isValidMove(b, toCheck)) {
          moves.add(toCheck);
        } else if (isValidMove(b, toCheckTake)) {
          moves.add(toCheckTake);
        }
      }
    }
    return moves;
  }

  @Override
  public void canCheck(Board b, Move m) throws ParseException {
    char[] files = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'};
    char[] ranks = {'8', '7', '6', '5', '4', '3', '2', '1'};

    Move toCheck;

    if (m.isCapture()) {return;}

    for (char file : files) {
      for (char rank : ranks) {
        if (super.isCheck(b, file, rank)) {
          toCheck = Move.parse(String.valueOf(m.getFile()) + m.getRank() + "x" + file + rank);
          if (isValidMove(b, toCheck)) {
            m.setCheck(true);
            m.setMoveScore(100);
          }
        }
      }
    }
  }

  @Override
  public int getScore()
  {
    return score;
  }
}
