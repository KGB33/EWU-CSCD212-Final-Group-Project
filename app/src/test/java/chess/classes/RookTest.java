package chess.classes;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.text.ParseException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/** RookTest */
public class RookTest {

  @Test
  void testBlackToString() {
    assertFalse(true);
  }

  @Test
  void testWhiteToString() {
    assertFalse(true);
  }

  @ParameterizedTest(name = "Rook.isValidMove method {0} (valid)")
  @ValueSource(strings = {"Foo"})
  void testIsValidMove(String notationIn) throws ParseException {
    assertFalse(true);
  }

  @ParameterizedTest(name = "Rook.isValidMove method {0} (invalid)")
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
