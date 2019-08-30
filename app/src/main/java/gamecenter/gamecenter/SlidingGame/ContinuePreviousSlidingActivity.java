package gamecenter.gamecenter.SlidingGame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import gamecenter.gamecenter.R;
import gamecenter.gamecenter.UserManager;

public class ContinuePreviousSlidingActivity extends AppCompatActivity {

    private UserManager userManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_continue_previous_sliding);

        //init a new user manager, auto load from .ser to get all the data
        userManager = new UserManager(this);
        userManager.loadUsersOfUserManagerFromFile();
        userManager.setCurrentUser(userManager.getUser(getIntent().getExtras().getString("currentUser")));
        addContinueGameButtonListener();
        addNotContinueGameButtonListener();
    }


    private void addContinueGameButtonListener() {
        Button continueButton = findViewById(R.id.continueSlidingButton);
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToGame();
            }
        });
    }

    private void addNotContinueGameButtonListener() {
        Button notContinueButton = findViewById(R.id.NotContinueSlidingButton);
        notContinueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //go to game main menu page
                userManager.getCurrentUser().setCurrentGameManager("currentSliding", null);
                userManager.saveUsersOfUserManagerToFile();
                switchToSelectGameLevel();
            }
        });
    }

    private void switchToGame() {
        Intent tmp = new Intent(this, SlidingMainActivity.class);
        tmp.putExtra("currentUser",userManager.getCurrentUser().getUserName());
        startActivity(tmp);
    }


    private void switchToSelectGameLevel() {
        Intent tmp = new Intent(this, SelectSlidingGameLevelActivity.class);
        tmp.putExtra("currentUser",userManager.getCurrentUser().getUserName());
        startActivity(tmp);
    }


}
