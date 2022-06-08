package ai;

import core.classes.Board;
import core.classes.Move;
import core.interfaces.MoveStream;
import java.text.ParseException;

public class AiMoveStream implements MoveStream {

  @Override
  public Move getNextMove(Board b) {
    AiFacade aif = new AiFacade(b);
    try {
      return aif.playTurn(b);
    } catch (ParseException e) {
      return Move.surrender();
    }
  }
}
