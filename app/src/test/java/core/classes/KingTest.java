package core.classes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.enums.Color;
import java.text.ParseException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class KingTest {

  @Test
  void testBlackToString() {
    final King k = new King(Color.BLACK);
    assertEquals("♚", k.toString());
  }

  @Test
  void testWhiteToString() {
    final King k = new King(Color.WHITE);
    assertEquals("♔", k.toString());
  }

  @ParameterizedTest(name = "King isValidMove {0}{1} to {2}{3} (valid)")
  @CsvSource({"Kb2a1", "Kb2a2", "Kb2a3", "Kb2b1", "Kb2b3", "Kb2c1", "Kb2c2", "Kb2c3", "Ke1e2"})
  void testIsValidMove(String notationIn) throws ParseException {
    Board b = new Board();
    Move m = Move.parse(notationIn);
    King k = new King(Color.WHITE);
    assertTrue(k.isValidMove(b, m));
  }

  @ParameterizedTest(name = "King isValidMove {0}{1} to {2}{3} (invalid)")
  @CsvSource({"Kb2a4", "Kb2h2", "Kb2h4"})
  void testIsInValidMove(String notationIn) throws ParseException {
    Board b = new Board();
    Move m = Move.parse(notationIn);
    King k = new King(Color.WHITE);
    assertFalse(k.isValidMove(b, m));
  }

  @Test
  void testCannotMoveToCurrentSquare() throws ParseException {
    Board b = new Board();
    Move m = Move.parse("Kb2b2");
    King k = new King(Color.WHITE);
    assertFalse(k.isValidMove(b, m));
  }
}
