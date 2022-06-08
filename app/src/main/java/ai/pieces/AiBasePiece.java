package ai.pieces;

import ai.CurrentSquare;
import ai.Score;
import core.classes.Board;
import core.classes.Move;

public abstract class AiBasePiece implements Score, CurrentSquare {

    private static final int score = 0;
    private char[] current;


    @Override
    public void setCurrent(char file, char rank) {
        this.current[0] = file;
        this.current[1] = rank;
    }

    @Override
    public char[] getCurrent() {
        return this.current;
    }

    @Override
    public int getScore() {
        return score;
    }

    public boolean isValidMove(Board b, Move m) {
        return (b.getSquare(current[0], current[1])).isValidMove(b, m);
    }
}
