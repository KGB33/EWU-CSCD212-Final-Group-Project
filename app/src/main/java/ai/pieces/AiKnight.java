package ai.pieces;

import ai.CurrentSquare;
import ai.Score;
import core.classes.Knight;
import core.enums.Color;

public class AiKnight extends AiBasePiece implements Score, CurrentSquare {

    private static final int score = 30;
    private char[] current;

    public AiKnight() {}

}
