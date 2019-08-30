package gamecenter.gamecenter.SlidingGame;

import android.app.Activity;
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

import java.io.Serializable;
import gamecenter.gamecenter.R;
import gamecenter.gamecenter.*;
/**
 * The initial activity for the sliding puzzle tile game.
 */
public class SlidingStartingActivity extends AppCompatActivity implements Serializable {


    /**
     * The board manager.
     */
    private UserManager userManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userManager = new UserManager(this);
        userManager.loadUsersOfUserManagerFromFile();
        setContentView(R.layout.activity_sliding_starting);
        userManager.setCurrentUser(userManager.getUser(getIntent().getExtras().getString("currentUser")));
        addStartButtonListener();
        addLoadButtonListener();
        addScoreHistoryListener();
        addBackToSelectGameButtonListener();
    }

    /**
     * Activate the start button.
     */
    private void addStartButtonListener() {
        Button startButton = findViewById(R.id.GoButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userManager.loadUsersOfUserManagerFromFile();
                if(userManager.getCurrentUser().getCurrentGameManager("currentSliding")!=null){
                    switchToContinuePreviousGameActivity();
                }else {
                    switchToSelectLevel();
                }
            }
        });
    }


    /**
     * Activate the load button.
     */
    private void addLoadButtonListener() {
        Button loadButton = findViewById(R.id.LoadButton);
        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userManager.loadUsersOfUserManagerFromFile();
                if(userManager.getCurrentUser().get_user_saved_games("sliding_file").size()!=0){
                    makeToastLoadedText();
                    switchToLoadSavedGame();
                }else{makeToastNoSavedGameText();}
            }
        });
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
     * Activate the save button.
     */
    private void addBackToSelectGameButtonListener() {
        Button logOutButton = findViewById(R.id.back_to_selectgame);
        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToSelectGameActivity();
            }
        });
    }

    /**
     * Display that no score history.
     */
    private void makeToastNoScoreHistoryText() {
        Toast.makeText(this, "No Score History", Toast.LENGTH_SHORT).show();
    }

    /**
     * Read the temporary board from disk.
     */
    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * Switch to the Select Level Activity view.
     */
    private void switchToSelectLevel() {
        Intent tmp = new Intent(this, SelectSlidingGameLevelActivity.class);
        tmp.putExtra("currentUser",userManager.getCurrentUser().getUserName());
        startActivity(tmp);
    }


    private void switchToLoadSavedGame(){
        Intent tmp = new Intent(this, LoadSavedGameActivity.class);
        tmp.putExtra("currentUser",userManager.getCurrentUser().getUserName());
        tmp.putExtra("loadType", "sliding_file");
        startActivity(tmp);
    }



    private void switchToContinuePreviousGameActivity(){
        Intent tmp = new Intent(this, ContinuePreviousSlidingActivity.class);
        tmp.putExtra("currentUser",userManager.getCurrentUser().getUserName());
        startActivity(tmp);
    }


    private void switchToSelectGameActivity(){
        Intent tmp = new Intent(this, SelectGameActivity.class);
        tmp.putExtra("currentUser",userManager.getCurrentUser().getUserName());
        startActivity(tmp);
    }

    private void addScoreHistoryListener(){
        final LinearLayout mRelativeLayout = findViewById(R.id.sliding_starting);
        Button checkScoreButton = findViewById(R.id.checkScoreHistory);
        final Context mContext = getApplicationContext();
        Activity mActivity = SlidingStartingActivity.this;
        // Initialize a new instance of popup window
        checkScoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
                View customView = inflater.inflate(R.layout.sliding_score_history,null);
                Button closeButton = customView.findViewById(R.id.closeHistory);
                TextView scoreHistory = customView.findViewById(R.id.scoreHistory);
                if(userManager.getCurrentUser().getScoreBoardManager("SlidingScoreManager").getScoreBoardHistory().size()!=0) {
                    scoreHistory.setText(userManager.getCurrentUser().getScoreBoardManager("SlidingScoreManager").toString());
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
