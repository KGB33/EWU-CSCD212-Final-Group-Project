package core.classes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import core.enums.Color;
import java.text.ParseException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class PawnTest {

  @Test
  void testBlackToString() {
    final Pawn k = new Pawn(Color.BLACK);
    assertEquals("♟", k.toString());
  }

  @Test
  void testWhiteToString() {
    final Pawn k = new Pawn(Color.WHITE);
    assertEquals("♙", k.toString());
  }

  @Test
  void testEnPassant() {
    fail();
  }

  @ParameterizedTest
  @ValueSource(strings = {"foo"})
  void testIsValidMove(String notationIn) throws ParseException {
    fail();
  }

  @ParameterizedTest
  @ValueSource(strings = {"bar"})
  void testIsInValidMove(String notationIn) throws ParseException {
    fail();
  }

  @Test
  void testCannotJumpOverPeicesOfTheSameColor() throws ParseException {
    Board b = Board.createEmtpyBoard();
    b.setSquare('a', '4', new Pawn(Color.WHITE));
    Move m = Move.parse("Qa1a8");
    Pawn q = new Pawn(Color.WHITE);
    assertFalse(q.isValidMove(b, m));
  }

  @Test
  void testCannotJumpOverPeicesOfDiffenetColors() throws ParseException {
    Board b = Board.createEmtpyBoard();
    b.setSquare('a', '4', new Pawn(Color.BLACK));
    b.setSquare('a', '1', new Pawn(Color.WHITE));
    Move m = Move.parse("Qa1a8");
    Pawn q = new Pawn(Color.WHITE);
    assertFalse(q.isValidMove(b, m));
  }

  @Test
  void testCannotCaputreSameTeam() throws ParseException {
    Board b = Board.createEmtpyBoard();
    b.setSquare('a', '4', new Pawn(Color.WHITE));
    Move m = Move.parse("Qa1xa4");
    Pawn q = new Pawn(Color.WHITE);
    assertFalse(q.isValidMove(b, m));
  }

  @Test
  void testCaptureOpposingPieces() throws ParseException {
    Board b = Board.createEmtpyBoard();
    b.setSquare('a', '4', new Pawn(Color.BLACK));
    Move m = Move.parse("Qa1xa4");
    Pawn q = new Pawn(Color.WHITE);
    assertTrue(q.isValidMove(b, m));
  }
}
