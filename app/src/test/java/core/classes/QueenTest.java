package core.classes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.enums.Color;
import java.text.ParseException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/** QueenTest */
public class QueenTest {

  @Test
  void testBlackToString() {
    Queen q = new Queen(Color.BLACK);
    assertEquals("♛", q.toString());
  }

  @Test
  void testWhiteToString() {
    Queen q = new Queen(Color.WHITE);
    assertEquals("♕", q.toString());
  }

  @ParameterizedTest(name = "Queen isValidMove {0} (valid)")
  @ValueSource(
      strings = {
        "Qd4c5", "Qd4d5", "Qd4e5", "Qd4e4", "Qd4e3", "Qd4d3", "Qd4c3", "Qd4c4", "Qd4a7", "Qd4d8",
        "Qd4h8", "Qd4h4", "Qd4g1", "Qd4d1", "Qd4a1", "Qd4a4",
      })
  void testIsValidMove(String notationIn) throws ParseException {
    Board b = Board.createEmtpyBoard();
    Move m = Move.parse(notationIn);
    Queen q = new Queen(Color.WHITE);
    assertTrue(q.isValidMove(b, m));
  }

  @ParameterizedTest(name = "Queen isValidMove {0} (invalid)")
  @ValueSource(strings = {"Qd4e6", "Qd4f3"})
  void testIsInValidMove(String notationIn) throws ParseException {
    Board b = Board.createEmtpyBoard();
    Move m = Move.parse(notationIn);
    Queen q = new Queen(Color.WHITE);
    assertFalse(q.isValidMove(b, m));
  }

  @Test
  void testCannotJumpOverPeicesOfTheSameColor() throws ParseException {
    Board b = Board.createEmtpyBoard();
    b.setSquare('a', '4', new Queen(Color.WHITE));
    Move m = Move.parse("Qa1a8");
    Queen q = new Queen(Color.WHITE);
    assertFalse(q.isValidMove(b, m));
  }

  @Test
  void testCannotJumpOverPeicesOfDiffenetColors() throws ParseException {
    Board b = Board.createEmtpyBoard();
    b.setSquare('a', '4', new Queen(Color.BLACK));
    b.setSquare('a', '1', new Queen(Color.WHITE));
    Move m = Move.parse("Qa1a8");
    Queen q = new Queen(Color.WHITE);
    assertFalse(q.isValidMove(b, m));
  }

  @Test
  void testCannotCaputreSameTeam() throws ParseException {
    Board b = Board.createEmtpyBoard();
    b.setSquare('a', '4', new Queen(Color.WHITE));
    Move m = Move.parse("Qa1xa4");
    Queen q = new Queen(Color.WHITE);
    assertFalse(q.isValidMove(b, m));
  }

  @Test
  void testCaptureOpposingPieces() throws ParseException {
    Board b = Board.createEmtpyBoard();
    b.setSquare('a', '4', new Queen(Color.BLACK));
    Move m = Move.parse("Qa1xa4");
    Queen q = new Queen(Color.WHITE);
    assertTrue(q.isValidMove(b, m));
  }
}
