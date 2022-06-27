package ai;

import core.classes.BasePiece;
import core.classes.Board;
import core.classes.Move;
import java.text.ParseException;
import java.util.ArrayList;

public class AiFacade {

  Computer ai;
  MoveGen moveGen;

  public AiFacade(Board b) {
    this.ai = new Computer(b);
    this.moveGen = new MoveGen(b);
  }

  public Move playTurn(Board newB) throws ParseException {
    if (newB.isGameOver()) {
      throw new IllegalArgumentException("Cannot move when the game is over.");
    }

    ArrayList<BasePiece> pieces = ai.getPieces();
    ArrayList<ArrayList<Move>> allMoves = moveGen.genAllMoves(pieces);
    return ai.bestMove(allMoves);
  }
}
