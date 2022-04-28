package chess.classes;

import static org.junit.jupiter.api.Assertions.assertEquals;

import chess.enums.Color;
import org.junit.jupiter.api.Test;

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
}
