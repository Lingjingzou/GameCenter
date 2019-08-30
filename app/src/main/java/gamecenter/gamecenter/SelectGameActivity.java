
package gamecenter.gamecenter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Objects;

import gamecenter.gamecenter.MoleGame.MoleStartingActivity;
import gamecenter.gamecenter.SlidingGame.SlidingStartingActivity;
import gamecenter.gamecenter.SnakeGame.*;

public class SelectGameActivity extends AppCompatActivity {

    private UserManager userManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_game);
        userManager = new UserManager(this);
        userManager.loadUsersOfUserManagerFromFile();
        userManager.setCurrentUser(userManager.getUser(Objects.requireNonNull(getIntent().getExtras()).getString("currentUser")));
        addSlidingGameButtonListener();
        addSnakeGameButtonListener();
        addMoleGameButtonListener();
        addLogOutButtonListener();
    }

    /**
     * Activate the SignUp button.Users switch to SignUp View by
     * clicking this button
     */
    private void addSlidingGameButtonListener() {
        Button SignUpButton = findViewById(R.id.sliding_game);
        SignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToSlidingGame();
            }
        });
    }

    /**
     * Activate the SignUp button.Users switch to SignUp View by
     * clicking this button
     */
    private void addSnakeGameButtonListener() {
        Button SignUpButton = findViewById(R.id.snake_game);
        SignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToSnakeGame();
            }
        });
    }

    private void addMoleGameButtonListener() {
        Button SignUpButton = findViewById(R.id.mole_game);
        SignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToMoleGame();
            }
        });
    }


    /**
     * Activate the save button.
     */
    private void addLogOutButtonListener() {
        Button logOutButton = findViewById(R.id.logout);
        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeToastUserLogOutText();
                switchToEnterActivity();
            }
        });
    }


    /**
     * Switch to the SignUp view to sign up.
     */
    private void switchToSlidingGame() {
        Intent tmp = new Intent(this, SlidingStartingActivity.class);
        tmp.putExtra("currentUser",userManager.getCurrentUser().getUserName());
        startActivity(tmp);
    }


    /**
     * Switch to the SignUp view to sign up.
     */
    private void switchToEnterActivity() {
        Intent tmp = new Intent(this, EnterActivity.class);
        startActivity(tmp);
    }

    /**
     * Switch to the SignUp view to sign up.
     */
    private void switchToSnakeGame() {
        Intent tmp = new Intent(this, SnakeStartingActivity.class);
        tmp.putExtra("currentUser",userManager.getCurrentUser().getUserName());
        startActivity(tmp);
    }


    private void switchToMoleGame() {
        Intent tmp = new Intent(this, MoleStartingActivity.class);
        tmp.putExtra("currentUser",userManager.getCurrentUser().getUserName());
        startActivity(tmp);
    }

    /**
     * Display that a user exists.
     */
    private void makeToastUserLogOutText() {
        Toast.makeText(this, "User: " + userManager.getCurrentUser().getUserName() + " Logged Out", Toast.LENGTH_SHORT).show();
    }
}
