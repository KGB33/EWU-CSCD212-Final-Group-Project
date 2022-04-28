package chess212.classes;

import static org.junit.jupiter.api.Assertions.assertEquals;

import chess212.enums.Color;
import org.junit.jupiter.api.Test;

public class KingTest {

  @Test
  void testBlackToString() {
    King k = new King(Color.BLACK);
    assertEquals("♚", k.toString());
  }

  @Test
  void testWhiteToString() {
    King k = new King(Color.WHITE);
    assertEquals("♔", k.toString());
  }
}
