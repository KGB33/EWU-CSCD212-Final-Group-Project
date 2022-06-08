package ai;

import ai.pieces.AiBasePiece;
import core.classes.BasePiece;
import core.classes.Board;
import core.classes.King;
import core.classes.Move;
import core.enums.Color;

import java.text.ParseException;
import java.util.ArrayList;

public class MoveGen {

  private final Board b;

  public MoveGen(Board b) {
    this.b = b;
  }

  public ArrayList<Move> validMoves(Board b, AiBasePiece piece) throws ParseException {
    char[] files = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'};
    char[] ranks = {'8', '7', '6', '5', '4', '3', '2', '1'};
    char[] current = piece.getCurrent();

    Move toCheck;
    Move toCheckTake;
    ArrayList<Move> moves = new ArrayList<>();

    for (char file : files) {
      for (char rank : ranks) {
        toCheck = Move.parse(Character.toString(current[0]) + current[1] + file + rank);
        toCheckTake = Move.parse(Character.toString(current[0]) + current[1] + "x" + file + rank);
        if (piece.isValidMove(b, toCheck)) {
          moves.add(toCheck);
        } else if (piece.isValidMove(b, toCheckTake)) {
          moves.add(toCheckTake);
        }
      }
    }
    return moves;
  }

  public void canCheck(Board b, Move m, AiBasePiece piece) throws ParseException {
    char[] files = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'};
    char[] ranks = {'8', '7', '6', '5', '4', '3', '2', '1'};

    Move toCheck;

    if (m.isCapture()) {
      return;
    }

    for (char file : files) {
      for (char rank : ranks) {
        if (isCheck(b, file, rank)) {
          toCheck = Move.parse(String.valueOf(m.getFile()) + m.getRank() + "x" + file + rank);
          if (piece.isValidMove(b, toCheck)) {
            m.setCheck(true);
            m.setMoveScore(100);
          }
        }
      }
    }
  }

  public boolean isCheck(Board b, char file, char rank) {
    BasePiece toCheck = b.getSquare(file, rank);
    if (toCheck == null) {
      return false;
    }
    if (Color.BLACK == toCheck.getColor()) {
      return false;
    }

    return (toCheck.getClass().equals(King.class));
  }

  public ArrayList<ArrayList<Move>> genAllMoves(ArrayList<AiBasePiece> pieces) throws ParseException {
    ArrayList<ArrayList<Move>> allMoves = new ArrayList<>(16);

    for (AiBasePiece piece : pieces) {
      ArrayList<Move> tmp = validMoves(b, piece);

      for (Move m : tmp) {
        canCheck(b, m, piece);
      }
      allMoves.add(tmp);
    }

    return allMoves;
  }
}
