package gamecenter.gamecenter;

import android.support.annotation.NonNull;

import java.io.Serializable;

public abstract class ScoreBoard implements Serializable, Comparable<ScoreBoard>   {
    private int time;
    private int score;
    private int level;

    public ScoreBoard(int level){
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getScore(){return score;}

    public void setScore(int score){this.score = score;}

    public int getTime() { return time; }

    public void setTime(int time) { this.time = time; }

    public abstract void calculateScore();

    public int compareTo(ScoreBoard o) {
        return this.getScore() - o.getScore();
    }

    public void minusScore(int minus){score -= minus;}

    public String getType(){
        return "ScoreBoard";}

    @NonNull
    @Override
    public String toString(){
        String scoreBoardInfo = "";
        scoreBoardInfo += "                    [ Score Board:    " +  "]" + "\n"
                + "                    Total score:         " + this.getScore() + "\n"
                + "                              " + "\n"
                + "                    Total time:         " + this.getTime() + "\n"
                + "                              " + "\n"
                + "                    Level:        " + this.getLevel();
        return scoreBoardInfo;
    }
}
