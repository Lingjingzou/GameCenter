package gamecenter.gamecenter.MoleGame;

import java.io.Serializable;

import gamecenter.gamecenter.GameManager;
import gamecenter.gamecenter.ScoreBoard;

public class MoleManager implements GameManager ,Serializable {
    private int level;
    private String username;
    private MoleScoreBoard moleScoreBoard;

    MoleManager(int level, String username) {
        this.level = level;
        moleScoreBoard = new MoleScoreBoard(level,username);
    }

    public int getLevel() {
        return level;
    }

    MoleScoreBoard getMoleScoreBoard() {
        return moleScoreBoard;
    }
}
