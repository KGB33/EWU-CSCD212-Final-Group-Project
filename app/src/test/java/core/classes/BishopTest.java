package core.classes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.enums.Color;
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
  @ValueSource(
      strings = {
        "Rd4a1", "Rd4g1", "Rd4h8", "Rd4a7", "Rd4c3", "Rd4e3", "Rd4e5", "Rd4c5",
      })
  void testIsValidMove(String notationIn) throws ParseException {
    Board b = Board.createEmtpyBoard();
    Move m = Move.parse(notationIn);
    Bishop bishop = new Bishop(Color.WHITE);
    assertTrue(bishop.isValidMove(b, m));
  }

  @ParameterizedTest(name = "Bishop.isValidMove method {0} (invalid)")
  @ValueSource(
      strings = {
        "Rd4d5", "Rd4e4", "Rd4d3", "Rd4c4", "Rd4d8", "Rd4h4", "Rd4d1", "Rd4a4", "Rd4e6", "Rd4f3"
      })
  void testIsInValidMove(String notationIn) throws ParseException {
    Board b = Board.createEmtpyBoard();
    Move m = Move.parse(notationIn);
    Bishop bishop = new Bishop(Color.WHITE);
    assertFalse(bishop.isValidMove(b, m));
  }

  @Test
  void testCannotJumpOverPeicesOfTheSameColor() throws ParseException {
    Board b = Board.createEmtpyBoard();
    Bishop bishop = new Bishop(Color.WHITE);
    Move m = Move.parse("Ba1h8");
    b.setSquare('a', '1', bishop);
    b.setSquare('d', '4', new Bishop(Color.WHITE));
    assertFalse(bishop.isValidMove(b, m));
  }

  @Test
  void testCannotJumpOverPeicesOfDiffenetColors() throws ParseException {
    Board b = Board.createEmtpyBoard();
    Bishop bishop = new Bishop(Color.WHITE);
    Move m = Move.parse("Ba1h8");
    b.setSquare('a', '1', bishop);
    b.setSquare('d', '4', new Bishop(Color.BLACK));
    assertFalse(bishop.isValidMove(b, m));
  }

  @Test
  void testCannotCaputreSameTeam() throws ParseException {
    Board b = Board.createEmtpyBoard();
    Bishop bishop = new Bishop(Color.WHITE);
    Move m = Move.parse("Ba1xh8");
    b.setSquare('a', '1', bishop);
    b.setSquare('h', '8', new Bishop(Color.WHITE));
    assertFalse(bishop.isValidMove(b, m));
  }

  @Test
  void testCaptureOpposingPieces() throws ParseException {
    Board b = Board.createEmtpyBoard();
    Bishop bishop = new Bishop(Color.WHITE);
    Move m = Move.parse("Ba1xh8");
    b.setSquare('a', '1', bishop);
    b.setSquare('h', '8', new Bishop(Color.BLACK));
    assertTrue(bishop.isValidMove(b, m));
  }
}
