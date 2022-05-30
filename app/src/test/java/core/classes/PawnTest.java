package core.classes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

  /**
   * For En-passant to be a valid move.
   *
   * <ul>
   *   <li>The capturing pawn must have advanced exactly three ranks to perform this move.
   *   <li>The captured pawn must have moved two squares in one move, landing right next to the
   *       capturing pawn.
   *   <li>The en passant capture must be performed on the turn immediately after the pawn being
   *       captured moves.
   * </ul>
   *
   * <p>From: https://www.chess.com/terms/en-passant
   */
  @Test
  void testEnPassant() throws ParseException {
    Board b = Board.createEmtpyBoard();
    Pawn p = new Pawn(Color.WHITE);
    b.setSquare('d', '7', new Pawn(Color.BLACK));
    // Capturing pawn has advanced 3 ranks (2 -> 5)
    b.setSquare('e', '5', p);
    // Captured pawn advanced two squares in one
    // move, landing next to the capturing pawn
    b.move(Move.parse("d7d5"));
    // Next move is en-passant
    assertTrue(p.isValidMove(b, Move.parse("e5xd6")));
  }

    @Test
  void testEnPassantMovesToCorrectRank() throws ParseException {
    Board b = Board.createEmtpyBoard();
    Pawn p = new Pawn(Color.WHITE);
    b.setSquare('d', '7', new Pawn(Color.BLACK));
    // Capturing pawn has advanced 3 ranks (2 -> 5)
    b.setSquare('e', '5', p);
    // Captured pawn advanced two squares in one
    // move, landing next to the capturing pawn
    b.move(Move.parse("d7d5"));
    // Next move is en-passant
    assertFalse(p.isValidMove(b, Move.parse("e5xd7")));
    assertFalse(p.isValidMove(b, Move.parse("e5xd4")));
    assertFalse(p.isValidMove(b, Move.parse("e5xd3")));
    assertFalse(p.isValidMove(b, Move.parse("e5xd5")));
  }

  @ParameterizedTest(name = "isValidMove (valid) {0}")
  @ValueSource(strings = {"d2d4", "a2a3", "a2xb3", "c4c5"})
  void testIsValidMove(String notationIn) throws ParseException {
    Board b = Board.createEmtpyBoard();
    b.setSquare('b', '3', new Pawn(Color.BLACK));
    Pawn p = new Pawn(Color.WHITE);
    assertTrue(p.isValidMove(b, Move.parse(notationIn)));
  }

  @ParameterizedTest(name = "isValidMove (invalid) {0}")
  @ValueSource(
      strings = {
        "a4a2", "a3a5", "a4a3", "c3xe5", "a2g5", "a2xg5", "a7g5", "a2b3", "a2c3", "a2c4", "a2f2", "g2xh4"
      })
  void testIsInValidMove(String notationIn) throws ParseException {
    Board b = Board.createEmtpyBoard();
    b.setSquare('e', '5', new Pawn(Color.BLACK));
    b.setSquare('h', '4', new Pawn(Color.BLACK));
    Pawn p = new Pawn(Color.WHITE);
    assertFalse(p.isValidMove(b, Move.parse(notationIn)));
  }

  @Test
  void testCannotJumpOverPeicesOfTheSameColor() throws ParseException {
    Board b = Board.createEmtpyBoard();
    b.setSquare('a', '3', new Pawn(Color.WHITE));
    Move m = Move.parse("a2a4");
    Pawn p = new Pawn(Color.WHITE);
    assertFalse(p.isValidMove(b, m));
  }

  @Test
  void testCannotJumpOverPeicesOfDiffenetColors() throws ParseException {
    Board b = Board.createEmtpyBoard();
    b.setSquare('a', '3', new Pawn(Color.BLACK));
    Move m = Move.parse("a2a4");
    Pawn p = new Pawn(Color.WHITE);
    assertFalse(p.isValidMove(b, m));
  }

  @Test
  void testCannotCaputreSameTeam() throws ParseException {
    Board b = Board.createEmtpyBoard();
    b.setSquare('a', '4', new Pawn(Color.WHITE));
    Move m = Move.parse("b3xa4");
    Pawn p = new Pawn(Color.WHITE);
    assertFalse(p.isValidMove(b, m));
  }

  @Test
  void testCaptureOpposingPieces() throws ParseException {
    Board b = Board.createEmtpyBoard();
    b.setSquare('a', '4', new Pawn(Color.BLACK));
    Move m = Move.parse("b3xa4");
    Pawn p = new Pawn(Color.WHITE);
    assertTrue(p.isValidMove(b, m));
  }
}
