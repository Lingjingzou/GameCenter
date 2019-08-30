package gamecenter.gamecenter.MoleGame;

import android.support.annotation.NonNull;

import java.io.Serializable;

import gamecenter.gamecenter.GameManager;
import gamecenter.gamecenter.ScoreBoard;

public class MoleScoreBoard extends ScoreBoard implements GameManager, Serializable {

    private int hitNum;
    private int time = 30;

    int getHitNum() {
        return hitNum;
    }

    void setHitNum(int hitNum) {
        this.hitNum = hitNum;
    }

    MoleScoreBoard(int level, String userName){
        super(level);
    }

    public int getTime(){return time;}
    public void setTime(int time){this.time=time;}

    @Override
    public void calculateScore() {
        super.setScore(hitNum*super.getLevel()*100 / 30);
    }

    @Override
    public int getScore() {
        calculateScore();
        return super.getScore();
    }

    public String getType(){
        return "moleScoreBoard";}

    @NonNull
    @Override
    public String toString(){
        String scoreBoardInfo = "";
        scoreBoardInfo += "                    [ Score Board:    " +  "]" + "\n"
                + "                    Total score:         " + this.getHitNum() + "\n"
                + "                    Level:        " + this.getLevel();
        return scoreBoardInfo;
    }

}
