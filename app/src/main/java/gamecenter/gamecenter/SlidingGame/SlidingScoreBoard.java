package gamecenter.gamecenter.SlidingGame;

import android.support.annotation.NonNull;

import java.io.Serializable;
import gamecenter.gamecenter.ScoreBoard;


public class SlidingScoreBoard extends ScoreBoard implements Serializable  {
    //steps
    private int step;

    SlidingScoreBoard(int level){
        super(level);
        if(level == 3) {
            super.setScore(500);
        }else if(level == 4){
            super.setScore(1000);
        }else if(level == 5){
            super.setScore(2000);
        }
    }

    @Override
    public void calculateScore() {
        setScore(getScore() - step - getTime());
    }

    @Override
    public int compareTo(ScoreBoard o) {
        return 0;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step){this.step = step;}

    void updateStep() {
        this.step += 1;
    }

    public String getType(){
        return "slidingScoreBoard";}

    @NonNull
    @Override
    public String toString(){
        String scoreBoardInfo = "";
        scoreBoardInfo += "                    [ Score Board:    " +  "]" + "\n"
                + "                    Total score:         " + this.getScore() + "\n"
                + "                              " + "\n"
                + "                    Total Step:         " + this.getStep() + "\n"
                + "                              " + "\n"
                + "                    Total time:         " + this.getTime() + "\n";
        return scoreBoardInfo;
    }

}
