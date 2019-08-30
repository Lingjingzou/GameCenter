package gamecenter.gamecenter.MoleGame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import gamecenter.gamecenter.*;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;



public class SelectMoleGameLevelActivity extends AppCompatActivity{

    private UserManager userManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_mole_game_level);
        userManager = new UserManager(this);
        userManager.loadUsersOfUserManagerFromFile();
        userManager.setCurrentUser(userManager.getUser(getIntent().getExtras().getString("currentUser")));
        addListenerOnStartButton();
    }

    public void addListenerOnStartButton() {
        Button startButton = findViewById(R.id.start_mole);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RatingBar ratingBar = findViewById(R.id.moleRatingBar);
                int level = Math.round(ratingBar.getRating());
                switchToMoleGame(level);
            }
        });
    }

    /**
     * Switch to the SlidingMainActivity view to play the game.
     */
    private void switchToMoleGame(int level) {
        //create a new mole manager.
        MoleManager newMole = new MoleManager(level, userManager.getCurrentUser().getUserName());
        userManager.getCurrentUser().setCurrentGameManager("currentMole", newMole);
        userManager.saveUsersOfUserManagerToFile();
        Intent tmp = new Intent(this, MoleMainActivity.class);
        tmp.putExtra("currentUser", userManager.getCurrentUser().getUserName());
        startActivity(tmp);
    }

    private void switchToStartingActivity() {
        Intent tmp = new Intent(this, MoleStartingActivity.class);
        tmp.putExtra("currentUser", userManager.getCurrentUser().getUserName());
        startActivity(tmp);
    }

    @Override
    public void onBackPressed() {
        switchToStartingActivity();
    }

}
