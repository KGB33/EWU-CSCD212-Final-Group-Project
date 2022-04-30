package chess.classes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import chess.enums.Color;
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
  @CsvSource({
    "b,2,a,1", "b,2,a,2", "b,2,a,3", "b,2,b,1", "b,2,b,3", "b,2,c,1", "b,2,c,2", "b,2,c,3"
  })
  void testIsValidMove(char fromFile, char fromRank, char toFile, char toRank) {
    King k = new King(Color.WHITE);
    assertTrue(k.isValidMove(fromFile, fromRank, toFile, toRank));
  }

  @ParameterizedTest(name = "King isValidMove {0}{1} to {2}{3} (invalid)")
  @CsvSource({
    "b,2,a,4", "b,2,h,2", "b,2,h,4",
  })
  void testIsInValidMove(char fromFile, char fromRank, char toFile, char toRank) {
    King k = new King(Color.WHITE);
    assertFalse(k.isValidMove(fromFile, fromRank, toFile, toRank));
  }

  @Test
  void testCannotMoveToCurrentSquare() {
    King k = new King(Color.WHITE);
    assertFalse(k.isValidMove('b', '2', 'b', '2'));
  }
}
