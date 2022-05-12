package core.classes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.enums.Color;
import java.text.ParseException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/** KnightTest */
public class KnightTest {

  @Test
  void testBlackToString() {
    Knight n = new Knight(Color.BLACK);
    assertEquals("♞", n.toString());
  }

  @Test
  void testWhiteToString() {
    Knight n = new Knight(Color.WHITE);
    assertEquals("♘", n.toString());
  }

  @ParameterizedTest(name = "Knight isValidMove {0} (valid)")
  @ValueSource(strings = {"Nd4b5", "Nd4c6", "Nd4e6", "Nd4f5", "Nd4f3", "Nd4e2", "Nd4c2", "Nd4b3"})
  void testIsValidMove(String notationIn) throws ParseException {
    Board b = Board.createEmtpyBoard();
    Move m = Move.parse(notationIn);
    Knight n = new Knight(Color.WHITE);
    assertTrue(n.isValidMove(b, m));
  }

  @ParameterizedTest(name = "Knight isValidMove {0} (invalid)")
  @ValueSource(strings = {"Nd4d4", "Nd4d8", "Nd4h8", "Nd4d2", "Nd4b2", "Nd4g4", "Nd4e3", "Nd4d3"})
  void testIsInValidMove(String notationIn) throws ParseException {
    Board b = Board.createEmtpyBoard();
    Move m = Move.parse(notationIn);
    Knight n = new Knight(Color.WHITE);
    assertFalse(n.isValidMove(b, m));
  }

  @Test
  void testCannotCaputreSameTeam() throws ParseException {
    Board b = Board.createEmtpyBoard();
    Knight n = new Knight(Color.WHITE);
    b.setSquare('b', '5', new Knight(Color.WHITE));
    b.setSquare('d', '4', n);
    Move m = Move.parse("Nd4xb5");
    assertFalse(n.isValidMove(b, m));
  }

  @Test
  void testCaptureOpposingPieces() throws ParseException {
    Board b = Board.createEmtpyBoard();
    Knight n = new Knight(Color.WHITE);
    b.setSquare('b', '5', new Knight(Color.BLACK));
    b.setSquare('d', '4', n);
    Move m = Move.parse("Nd4xb5");
    assertTrue(n.isValidMove(b, m));
  }
}
