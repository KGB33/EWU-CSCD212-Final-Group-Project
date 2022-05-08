package chess.classes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import chess.enums.Color;
import java.text.ParseException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/** BishopTest */
public class BishopTest {

  @Test
  void testBlackToString() {
    Bishop b = new Bishop(Color.BLACK);
    assertEquals("♝", b.toString());
  }

  @Test
  void testWhiteToString() {
    Bishop b = new Bishop(Color.WHITE);
    assertEquals("♗", b.toString());
  }

  @ParameterizedTest(name = "Bishop.isValidMove method {0} (valid)")
  @ValueSource(strings = {"Foo"})
  void testIsValidMove(String notationIn) throws ParseException {
    assertFalse(true);
  }

  @ParameterizedTest(name = "Bishop.isValidMove method {0} (invalid)")
  @ValueSource(strings = {"Foo"})
  void testIsInValidMove(String notationIn) throws ParseException {
    assertFalse(true);
  }

  @Test
  void testCannotJumpOverPeicesOfTheSameColor() throws ParseException {
    assertFalse(true);
  }

  @Test
  void testCannotJumpOverPeicesOfDiffenetColors() throws ParseException {
    assertFalse(true);
  }

  @Test
  void testCannotCaputreSameTeam() throws ParseException {
    assertFalse(true);
  }

  @Test
  void testCaptureOpposingPieces() throws ParseException {
    assertFalse(true);
  }
}
