package core.classes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

import java.text.ParseException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class MoveTest {

  @ParameterizedTest(name = "Test parse output matches input -- {0}")
  @ValueSource(
      strings = {
        "Bxe5", "Be5", "Kf3", "exd5", "0-0-0", "0-0", "Rdf8", "R1a3", "Qh4e1", "Qh4xe1", "e8Q",
        "a1Q", "Be5+", "Be5#", "e8Q#", "Ke1e2",
      })
  void testOutputFromToStringMatchesParsedInput(final String input) throws ParseException {
    final Move m = Move.parse(input);
    assertEquals(input, m.toString());
  }

  @ParameterizedTest(name = "Test parse throws parse Error on invalid input -- {0}")
  @ValueSource(strings = {"O-O-O", "O-0", "NxT9", "aaaaa", "A", "Just some random string"})
  void testParseThrowsWhenPassedInvalidInput(final String input) throws ParseException {
    assertThrowsExactly(ParseException.class, () -> Move.parse(input));
  }

  @ParameterizedTest
  @NullAndEmptySource
  void testParseThrowsWhenNotationIsNullOrEmpty(final String input) throws ParseException {
    assertThrows(IllegalArgumentException.class, () -> Move.parse(input));
  }
}
