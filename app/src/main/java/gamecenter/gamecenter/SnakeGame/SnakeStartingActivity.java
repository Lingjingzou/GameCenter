package gamecenter.gamecenter.SnakeGame;

import android.annotation.SuppressLint;
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

import java.util.Objects;

import gamecenter.gamecenter.*;

public class SnakeStartingActivity extends AppCompatActivity {

    /**
     * The user manager.
     */
    private UserManager userManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snake_starting);

        userManager = new UserManager(this);
        userManager.loadUsersOfUserManagerFromFile();
        userManager.setCurrentUser(userManager.getUser(Objects.requireNonNull(getIntent().getExtras()).getString("currentUser")));
        addStartButtonListener();
        addLoadButtonListener();
        addScoreHistoryListener();
        addBackToMainButtonListener();
    }

    /**
     * Activate the start button.
     */
    private void addStartButtonListener() {
        Button startButton = findViewById(R.id.snake_new_game);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userManager.getCurrentUser().getCurrentGameManager("currentSnake")!=null){
                    switchToContinuePreviousSnakeGameActivity();
                }else {
                    switchToSelectSnakeLevel();
                }
            }
        });
    }


    /**
     * Activate the load button.
     */
    private void addLoadButtonListener() {
        Button loadButton = findViewById(R.id.snake_load_saved);
        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userManager.loadUsersOfUserManagerFromFile();
//                if(userManager.getCurrentUser().getHasSavedGame("hasSavedSnake")){
                if(userManager.getCurrentUser().get_user_saved_games("snake_file").size()!=0){
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
        Button startButton = findViewById(R.id.back_to_select_game);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToSelectGame();
            }
        });
    }


    private void switchToContinuePreviousSnakeGameActivity(){
        Intent tmp = new Intent(this, ContinuePreviousSnakeActivity.class);
        tmp.putExtra("currentUser",userManager.getCurrentUser().getUserName());
        userManager.saveUsersOfUserManagerToFile();
        startActivity(tmp);
    }

    /**
     * Switch to the Select Level Activity view.
     */
    private void switchToSelectSnakeLevel() {
        Intent tmp = new Intent(this, SelectSnakeGameLevelActivity.class);
        tmp.putExtra("currentUser",userManager.getCurrentUser().getUserName());
        userManager.saveUsersOfUserManagerToFile();
        startActivity(tmp);
    }

    /**
     * Switch to the Select Level Activity view.
     */
    private void switchToSelectGame() {
        Intent tmp = new Intent(this, SelectGameActivity.class);
        tmp.putExtra("currentUser",userManager.getCurrentUser().getUserName());
        userManager.saveUsersOfUserManagerToFile();
        startActivity(tmp);
    }

    private void switchToLoadSavedGame(){
        Intent tmp = new Intent(this, LoadSavedGameActivity.class);
        tmp.putExtra("currentUser",userManager.getCurrentUser().getUserName());
        tmp.putExtra("loadType", "snake_file");
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
     * Display that no score history.
     */
    private void makeToastNoScoreHistoryText() {
        Toast.makeText(this, "No Score History", Toast.LENGTH_SHORT).show();
    }


    private void addScoreHistoryListener(){
        final LinearLayout mRelativeLayout = findViewById(R.id.snake_starting);
        Button checkScoreButton = findViewById(R.id.snake_score_history);
        final Context mContext = getApplicationContext();
        Activity mActivity = SnakeStartingActivity.this;
        // Initialize a new instance of popup window
        checkScoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
                @SuppressLint("InflateParams") View customView = inflater.inflate(R.layout.snake_score_history,null);
                Button closeButton = customView.findViewById(R.id.closeSnakeHistory);
                TextView scoreHistory = customView.findViewById(R.id.snakeScoreHistory);
                if(userManager.getCurrentUser().getScoreBoardManager("SnakeScoreManager").getScoreBoardHistory().size()!=0) {
                    scoreHistory.setText(userManager.getCurrentUser().getScoreBoardManager("SnakeScoreManager").toString());
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