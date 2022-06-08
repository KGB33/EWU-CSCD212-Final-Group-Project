package core.classes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.enums.Color;
import java.text.ParseException;
import org.junit.jupiter.api.Test;

/** BoardTest */
public class BoardTest {

  @Test
  void testCheckmateWhiteKingTwoBlkRooks() throws ParseException {
    Board b = Board.createEmtpyBoard();
    b.setSquare('h', '5', new King(Color.WHITE));
    b.setSquare('g', '1', new Rook(Color.BLACK));
    b.setSquare('c', '2', new Rook(Color.BLACK));
    b.setSquare('a', '8', new King(Color.BLACK));
    b.move(Move.parse("Rc2h2"));
    b.move(Move.parse("Kh5h6"));
    b.move(Move.parse("Rh2xh6"));
    assertTrue(b.isGameOver());
    assertEquals(Color.BLACK, b.getWinner());
  }
}
