package gamecenter.gamecenter;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class ScoreBoardManager implements Serializable {

    private ArrayList<ScoreBoard> scoreBoardHistory;

    ScoreBoardManager(){
        scoreBoardHistory = new ArrayList<>();
    }

    public void addScoreBoard(ScoreBoard scoreBoard) {
        // user's score history is full

        if(scoreBoardHistory.size() == 5){
            Collections.sort(scoreBoardHistory);
            scoreBoardHistory.add(scoreBoard);
            Collections.sort(scoreBoardHistory);
            scoreBoardHistory.remove(0);
        } else {
            scoreBoardHistory.add(scoreBoard);
            Collections.sort(scoreBoardHistory);
        }
    }

    public ArrayList<ScoreBoard> getScoreBoardHistory() {
        return scoreBoardHistory;
    }

    @NonNull
    @Override
    public String toString(){
        String s = "--------  Scores Ranking  --------" + "\n";
        int i = scoreBoardHistory.size()-1;
        while (i>=0 && scoreBoardHistory.get(i) != null){
            s += "Score Ranking:    " + (scoreBoardHistory.size()-i) + "\n"
                    + "Total Score:  " + scoreBoardHistory.get(i).getScore() + "\n"
                    +"\n";
            i--;
        }
        return s;
    }

}
