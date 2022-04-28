package chess.classes;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class MoveTest {

  @ParameterizedTest(name = "Test parse output matches input -- {0}")
  @ValueSource(
      strings = {
        "Bxe5", "Be5", "Kf3", "exd5", "0-0-0", "0-0", "Rdf8", "R1a3", "Qh4e1", "Qh4xe1", "e8Q",
        "a1Q", "Be5+", "Be5#"
      })
  void testOutputFromToStringMatchesParsedInput(final String input) {
    final Move m = Move.parse(input);
    assertEquals(input, m.toString());
  }
}
