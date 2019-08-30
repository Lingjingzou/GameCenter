package gamecenter.gamecenter.MoleGame;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import gamecenter.gamecenter.*;

public class MoleStartingActivity extends AppCompatActivity {

    /**
     *The user manger
     */
    private UserManager userManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mole_starting);

        userManager = new UserManager(this);
        userManager.loadUsersOfUserManagerFromFile();
        userManager.setCurrentUser(userManager.getUser(getIntent().getExtras().getString("currentUser")));
        addStartButtonListener();
        addLoadButtonListener();
        addScoreHistoryListener();
        addBackToMainButtonListener();
    }

    /**
     * Activate the start button.
     */
    private void addStartButtonListener() {
        Button startButton = findViewById(R.id.mole_new);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userManager.getCurrentUser().getCurrentGameManager("currentMole")!=null){
                    switchToContinuePreviousMoleGameActivity();
                }else {
                    switchToSelectMoleLevel();
                }
            }
        });
    }


    /**
     * Activate the load button.
     */
    private void addLoadButtonListener() {
        Button loadButton = findViewById(R.id.mole_load);
        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userManager.loadUsersOfUserManagerFromFile();
                if(userManager.getCurrentUser().get_user_saved_games("mole_file").size()!=0){
                    makeToastLoadedText();
                    switchToLoadSavedGame();
                }else{makeToastNoSavedGameText();}
            }
        });
    }

    /**
     * Activate the start button.
     */
    private void addBackToMainButtonListener() {
        Button startButton = findViewById(R.id.mole_back_to_select_game);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToSelectGame();
            }
        });
    }


    private void switchToContinuePreviousMoleGameActivity(){
        Intent tmp = new Intent(this, ContinuePreviousMoleActivity.class);
        tmp.putExtra("currentUser",userManager.getCurrentUser().getUserName());
        startActivity(tmp);
    }

    /**
     * Switch to the Select Level Activity view.
     */
    private void switchToSelectMoleLevel() {
        Intent tmp = new Intent(this, SelectMoleGameLevelActivity.class);
        tmp.putExtra("currentUser",userManager.getCurrentUser().getUserName());
        startActivity(tmp);
    }

    /**
     * Switch to the Select Level Activity view.
     */
    private void switchToSelectGame() {
        Intent tmp = new Intent(this, SelectGameActivity.class);
        tmp.putExtra("currentUser",userManager.getCurrentUser().getUserName());
        startActivity(tmp);
    }

    private void switchToLoadSavedGame(){
        Intent tmp = new Intent(this, LoadSavedGameActivity.class);
        tmp.putExtra("currentUser",userManager.getCurrentUser().getUserName());
        tmp.putExtra("loadType", "mole_file");
        startActivity(tmp);
    }


    /**
     * Display that a game was loaded successfully.
     */
    private void makeToastLoadedText() {
        Toast.makeText(this, "Loaded Game", Toast.LENGTH_SHORT).show();
    }

    /**
     * Display that a game was loaded successfully.
     */
    private void makeToastNoSavedGameText() {
        Toast.makeText(this, "No saved game", Toast.LENGTH_SHORT).show();
    }

    /**
     * Display that a game was saved successfully.
     */
    private void makeToastSavedText() {
        Toast.makeText(this, "Game Saved", Toast.LENGTH_SHORT).show();
    }

    /**
     * Display that no game can be saved.
     */
    private void makeToastCannotSaveText() {
        Toast.makeText(this, "No current game can be saved", Toast.LENGTH_SHORT).show();
    }

    /**
     * Display that no score history.
     */
    private void makeToastNoScoreHistoryText() {
        Toast.makeText(this, "No Score History", Toast.LENGTH_SHORT).show();
    }

    private void addScoreHistoryListener(){
        final LinearLayout mRelativeLayout = findViewById(R.id.mole_starting);
        Button checkScoreButton = findViewById(R.id.mole_score_history);
        final Context mContext = getApplicationContext();
        // Initialize a new instance of popup window
        checkScoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
                @SuppressLint("InflateParams") View customView = inflater.inflate(R.layout.mole_score_history,null);
                Button closeButton = customView.findViewById(R.id.buttonback);
                TextView scoreHistory = customView.findViewById(R.id.moleScoreHistory);
                if(userManager.getCurrentUser().getScoreBoardManager("MoleScoreManager").getScoreBoardHistory().size()!=0) {
                    scoreHistory.setText(userManager.getCurrentUser().getScoreBoardManager("MoleScoreManager").toString());
                }else{makeToastNoScoreHistoryText();}

                final PopupWindow mPopupWindow = new PopupWindow(
                        customView,
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);

                // Set a click listener for the popup window close button
                closeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mPopupWindow.dismiss();
                    }
                });
                mPopupWindow.showAtLocation(mRelativeLayout, Gravity.CENTER,0,0);
            }

        });
    }

}
