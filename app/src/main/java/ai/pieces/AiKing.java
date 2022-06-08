package ai.pieces;

import ai.CurrentSquare;
import ai.Score;
import core.classes.King;
import core.enums.Color;

public class AiKing extends AiBasePiece implements Score, CurrentSquare {

    private static final int score = 100;
    private char[] current;

    public AiKing() {}

}
