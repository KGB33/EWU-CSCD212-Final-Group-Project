package ai;


import core.classes.Board;

import java.text.ParseException;

public class AiFacade {

    Computer ai;

    public AiFacade(Board b) {
        this.ai = new Computer(b);
    }





    public void playTurn(Board newB) throws ParseException {
        //add gameover check
        ai.boardUpdate(newB);
        ai.getPieces();
        ai.genAllMoves();
        ai.playMove();
    }
}
