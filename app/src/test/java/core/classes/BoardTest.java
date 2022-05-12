package core.classes;

import static org.junit.jupiter.api.Assertions.assertTrue;

import core.enums.Color;
import org.junit.jupiter.api.BeforeEach;

/** BoardTest */
public class BoardTest {
  Board b;

  @BeforeEach
  void setupBoard() {
    Board newBoard = new Board();
    this.b = newBoard;
  }

  void testKingIsOnStartingSquare() {
    BasePiece p = this.b.getSquare('1', 'e');
    assertTrue(p.getClass() == King.class);
    assertTrue(p.color == Color.WHITE);
  }
}
