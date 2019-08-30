package gamecenter.gamecenter.SnakeGame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import gamecenter.gamecenter.*;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import java.util.Objects;

public class SelectSnakeGameLevelActivity extends AppCompatActivity {

    private UserManager userManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_snake_game_level);
        userManager = new UserManager(this);
        userManager.loadUsersOfUserManagerFromFile();
        userManager.setCurrentUser(userManager.getUser(Objects.requireNonNull(getIntent().getExtras()).getString("currentUser")));
        addListenerOnStartButton();
    }

    public void addListenerOnStartButton() {
        Button startButton = findViewById(R.id.start_snake);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RatingBar ratingBar = findViewById(R.id.ratingBar);
                int level = Math.round(ratingBar.getRating());
                switchToSnakeGame(level);
            }
        });
    }

    /**
     * Switch to the SlidingMainActivity view to play the game.
     */
    private void switchToSnakeGame(int level) {
        EditText recordLimitNum = findViewById(R.id.recordLimit);
        int recordLimit = Integer.parseInt(recordLimitNum.getText().toString());
        //create a new board manager
        SnakeManager newSnakeManager = new SnakeManager(level, userManager.getCurrentUser().getUserName(), recordLimit);
        userManager.getCurrentUser().setCurrentGameManager("currentSnake", newSnakeManager);
        userManager.saveUsersOfUserManagerToFile();
        Intent tmp = new Intent(this, SnakeMainActivity.class);
        tmp.putExtra("currentUser", userManager.getCurrentUser().getUserName());
        startActivity(tmp);
    }
}
