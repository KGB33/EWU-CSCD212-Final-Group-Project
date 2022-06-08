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

  public String playTurn(Board newB) throws ParseException {
    if (newB.isGameOver()) {
      return null;
    }

    ArrayList<BasePiece> pieces = ai.getPieces();
    ArrayList<ArrayList<Move>> allMoves = moveGen.genAllMoves(pieces);
    newB.move(ai.bestMove(allMoves));
    return ai.bestMove(allMoves).toString();
  }
}
