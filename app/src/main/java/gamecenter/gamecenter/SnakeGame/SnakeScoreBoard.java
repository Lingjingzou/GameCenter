package gamecenter.gamecenter.SnakeGame;

import java.io.Serializable;

import gamecenter.gamecenter.ScoreBoard;

public class SnakeScoreBoard extends ScoreBoard implements Serializable {
    private String type = "snakeScoreBoard";

    SnakeScoreBoard(int level, String userName){
        super(level);
    }

    @Override
    public int getScore(){
        calculateScore();
        return super.getScore();
    }

    @Override
    public void calculateScore() {
        super.setScore(super.getTime()*super.getLevel());
    }

    public String getType(){return type;}

}
