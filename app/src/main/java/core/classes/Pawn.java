package core.classes;

import core.enums.Color;

import java.text.ParseException;
import java.util.ArrayList;

/** Pawn */
public class Pawn extends BasePiece {
    private static char whiteIcon = '\u2659';
    private static char blackIcon = '\u265F';

    private static final int score = 10;

    private char[] current = {'0', '0'};

    /**
     * Creates a Pawn with the provided color.
     *
     * @param color The color the pawn will be.
     * @throws IllegalArgumentException if color is null
     */
    public Pawn(final Color color) {
        if (color == null) {
            throw new IllegalArgumentException("Color cannot be null.");
        }
        this.color = color;
        this.icon = color.equals(Color.BLACK) ? Pawn.blackIcon : Pawn.whiteIcon;
    }

    /**
     * Returns true if the given move is valid for a pawn.
     *
     * @param b The board representing the state of the game.
     * @param m The move to check.
     * @throws IllegalArgumentException if any args are null.
     */
    @Override
    public boolean isValidMove(final Board b, final Move m) {
        if (!super.isValidMove(b, m)) {
            return isValidEnPassant(b, m);
        }

        int rankDelta = m.getRank() - m.getFromRank();
        if (rankDelta == 0) {
            return false;
        }
        int rankDir = Math.abs(rankDelta) / rankDelta;
        // Check that the pawn moves the correct direction.
        if (this.color.equals(Color.WHITE) && !(rankDir > 0)) {
            return false;
        }
        if (this.color.equals(Color.BLACK) && !(rankDir < 0)) {
            return false;
        }
        // If a pawn moves two squares forward, it must be on it's starting square.
        if (Math.abs(rankDelta) != 1 && !isValidTwoSquare(b, m)) {
            return false;
        }

        int fileDelta = Math.abs(m.getFile() - m.getFromFile());
        if (fileDelta > 1) {
            return false;
        }
        // Ensure that the pawn moved the right direction
        return (!(m.isCapture() ^ (fileDelta == 1)));
    }

    private boolean isValidTwoSquare(Board b, Move m) {
        if (m.isCapture()) {
            return false;
        }
        int rankDelta = m.getRank() - m.getFromRank();
        int rankDir = Math.abs(rankDelta) / rankDelta;
        if (Math.abs(rankDelta) != 2) {
            return false;
        }

        // Check that the pawn moved from the starting square.
        char expectedRank = this.color.equals(Color.WHITE) ? '2' : '7';
        if (expectedRank != m.getFromRank()) {
            return false;
        }

        // Check that the square its jumping over is empty
        if (b.getSquare(m.getFile(), (char) (m.getRank() - rankDir)) != null) {
            return false;
        }
        return true;
    }

    private boolean isValidEnPassant(Board b, Move m) {
        char expectedRank = this.color.equals(Color.WHITE) ? '5' : '4';

        // toSquare must be empty and captured piece must exist & be a different
        // color & a pawn
        BasePiece takenSquare = b.getSquare(m.getFile(), expectedRank);
        if (b.getSquare(m.getFile(), m.getRank()) != null
                || takenSquare == null
                || takenSquare.color.equals(this.color)
                || !(takenSquare instanceof Pawn)) {
            return false;
        }

        // Capturing piece must be on the 3rd rank
        if (expectedRank != m.getFromRank()) {
            return false;
        }
        // Captured Pawn must have just moved two squares.
        if (b.getTurnNumber() == 0) {
            return false;
        }
        Move prevousMove = b.getMoves().get(b.getTurnNumber() - 1);
        if (prevousMove.getMovedPiece() == 0
                && Math.abs(prevousMove.getRank() - prevousMove.getFromRank()) == 2
                && b.getSquare(prevousMove.getFile(), prevousMove.getRank()) == takenSquare) {
            return true;
        }

        return false;
    }

    /** Checks if a given move is en-passant. */
    public boolean isEnPassant(Board b, Move m) {
        if (!super.isValidMove(b, m)) {
            return isValidEnPassant(b, m);
        }
        return false;
    }

    @Override
    public char[] getCurrent() {
        return this.current;
    }

    @Override
    public void setCurrent(char file, char rank) {
        this.current[0] = file;
        this.current[1] = rank;
    }



    @Override
    public int getScore()
    {
        return score;
    }
}