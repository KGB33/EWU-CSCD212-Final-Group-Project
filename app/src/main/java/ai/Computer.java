package ai;

import ai.pieces.*;
import core.classes.*;
import core.classes.Board;
import core.enums.Color;
import java.util.ArrayList;

public class Computer {

  final char[] ranks = {'8', '7', '6', '5', '4', '3', '2', '1'};
  final char[] files = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'};

  ArrayList<AiBasePiece> aiBasePieces;

  private final Board b;

  public Computer(Board b) {
    this.b = b;
  }

  public void initPieces() {
    aiBasePieces.add(new AiRook());
    aiBasePieces.get(0).setCurrent('a', '8');
    new AiKnight().setCurrent('b', '8');
    new AiBishop().setCurrent('c', '8');
    new AiKing().setCurrent('e', '8');
    new AiQueen().setCurrent('d', '8');
    new AiBishop().setCurrent('f', '8');
    new AiKnight().setCurrent('g', '8');
    new AiRook().setCurrent('h', '8');
    for (char file : files) {
      new AiPawn().setCurrent(file, '7');
    }
  }

  public ArrayList<AiBasePiece> getPieces() {
    ArrayList<AiBasePiece> pieces = new ArrayList<>(16);

    for (char file : files) {
      for (char rank : ranks) {
        if (b.getSquare(file, rank) == null) {
          continue;
        }
        if (((b.getSquare(file, rank)).getColor()).equals(Color.BLACK)) {
          switch ((b.getSquare(file, rank)).toString()) {
            case "♛":
              pieces.add(AiQueen);
            case "♚":
            case "♝":
            case "♞":
            case "♜":
            case "♟":
          }
        }
      }
    }

    return pieces;
  }

  public Move bestMove(ArrayList<ArrayList<Move>> allMoves) {
    int bestMoveScore = 0;
    Move bestMove = null;

    for (ArrayList<Move> pieceMoves : allMoves) {
      for (Move move : pieceMoves) {

        if (move.isCapture()) {
          move.setMoveScore((b.getSquare(move.getFile(), move.getRank())).getScore());
        }
        if (move.getMoveScore() > bestMoveScore) {
          bestMoveScore = move.getMoveScore();
          bestMove = move;
        }
      }
    }

    if (bestMoveScore == 0) {
      int randPiece = (int) (Math.random() * allMoves.size());
      int randMove = (int) (Math.random() * ((allMoves.get(randPiece)).size()));

      while ((allMoves.get(randPiece)).isEmpty()) {
        randPiece = (int) (Math.random() * allMoves.size());
        randMove = (int) (Math.random() * ((allMoves.get(randPiece)).size()));
      }
      bestMove = (allMoves.get(randPiece)).get(randMove);
    }

    return bestMove;
  }
}
