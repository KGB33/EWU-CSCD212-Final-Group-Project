package core.classes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.enums.Color;
import java.text.ParseException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/** RookTest */
public class RookTest {

  @Test
  void testBlackToString() {
    Rook r = new Rook(Color.BLACK);
    assertEquals("♜", r.toString());
  }

  @Test
  void testWhiteToString() {
    Rook r = new Rook(Color.WHITE);
    assertEquals("♖", r.toString());
  }

  @ParameterizedTest(name = "Rook.isValidMove method {0} (valid)")
  @ValueSource(
      strings = {
        "Rd4d5", "Rd4e4", "Rd4d3", "Rd4c4", "Rd4d8", "Rd4h4", "Rd4d1", "Rd4a4",
      })
  void testIsValidMove(String notationIn) throws ParseException {
    Board b = Board.createEmtpyBoard();
    Move m = Move.parse(notationIn);
    Rook r = new Rook(Color.WHITE);
    assertTrue(r.isValidMove(b, m));
  }

  @ParameterizedTest(name = "Rook.isValidMove method {0} (invalid)")
  @ValueSource(
      strings = {
        "Rd4a1", "Rd4g1", "Rd4h8", "Rd4a7", "Rd4c3", "Rd4e3", "Rd4e5", "Rd4c5", "Rd4e6", "Rd4f3"
      })
  void testIsInValidMove(String notationIn) throws ParseException {
    Board b = Board.createEmtpyBoard();
    Move m = Move.parse(notationIn);
    Rook r = new Rook(Color.WHITE);
    assertFalse(r.isValidMove(b, m));
  }

  @Test
  void testCannotJumpOverPeicesOfTheSameColor() throws ParseException {
    Board b = Board.createEmtpyBoard();
    Rook r = new Rook(Color.WHITE);
    Move m = Move.parse("Ra1a8");
    b.setSquare('a', '4', new Rook(Color.WHITE));
    b.setSquare('a', '1', r);
    assertFalse(r.isValidMove(b, m));
  }

  @Test
  void testCannotJumpOverPeicesOfDiffenetColors() throws ParseException {
    Board b = Board.createEmtpyBoard();
    Rook r = new Rook(Color.BLACK);
    Move m = Move.parse("Ra1a8");
    b.setSquare('a', '4', new Rook(Color.WHITE));
    b.setSquare('a', '1', r);
    assertFalse(r.isValidMove(b, m));
  }

  @Test
  void testCannotCaputreSameTeam() throws ParseException {
    Board b = Board.createEmtpyBoard();
    Rook r = new Rook(Color.WHITE);
    Move m = Move.parse("Ra1xa8");
    b.setSquare('a', '8', new Rook(Color.WHITE));
    b.setSquare('a', '1', r);
    assertFalse(r.isValidMove(b, m));
  }

  @Test
  void testCaptureOpposingPieces() throws ParseException {
    Board b = Board.createEmtpyBoard();
    Rook r = new Rook(Color.BLACK);
    Move m = Move.parse("Ra1xa8");
    b.setSquare('a', '8', new Rook(Color.WHITE));
    b.setSquare('a', '1', r);
    assertTrue(r.isValidMove(b, m));
  }
}
