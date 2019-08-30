package gamecenter.gamecenter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Objects;

import gamecenter.gamecenter.MoleGame.MoleStartingActivity;
import gamecenter.gamecenter.SlidingGame.SlidingStartingActivity;
import gamecenter.gamecenter.SnakeGame.SnakeStartingActivity;

public class SaveGameActivity extends AppCompatActivity {

    UserManager userManager;
    String savedType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_game);

        savedType = Objects.requireNonNull(getIntent().getExtras()).getString("savedType");
        userManager = new UserManager(this);
        userManager.loadUsersOfUserManagerFromFile();
        userManager.setCurrentUser(userManager.getUser(Objects.requireNonNull(getIntent().getExtras()).getString("currentUser")));
        addSaveButtonListener();
    }

    /**
     * Activate the SignUp button.Users switch to SignUp View by
     * clicking this button
     */
    private void addSaveButtonListener() {
        Button saveButton = findViewById(R.id.confirm_save);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText savedName = findViewById(R.id.saved_game_name);
                String fileName = savedName.getText().toString() + ".ser";

                if(savedType.equals("Sliding")) {
                    userManager.saveGameOfUserToFile(userManager.getCurrentUser(), "sliding_file", fileName);
                    userManager.getCurrentUser().setCurrentGameManager("currentSliding", null);
                }else if(savedType.equals("Snake")){
                    userManager.saveGameOfUserToFile(userManager.getCurrentUser(), "snake_file", fileName);
                    userManager.getCurrentUser().setCurrentGameManager("currentSnake", null);
                }else if(savedType.equals("Mole")){
                    //save current game into .ser file
                    userManager.saveGameOfUserToFile(userManager.getCurrentUser(), "mole_file", fileName);
                    //remove current game
                    userManager.getCurrentUser().setCurrentGameManager("currentMole", null);
                }
                userManager.saveUsersOfUserManagerToFile();
                switchToStarting();
            }
        });
    }

    /**
     * Switch to the SignUp view to sign up.
     */
    private void switchToStarting() {
        Intent tmp = null;
        if(savedType.equals("Sliding")) {
            tmp = new Intent(this, SlidingStartingActivity.class);
        }else if(savedType.equals("Snake")){
            tmp = new Intent(this, SnakeStartingActivity.class);
        }else if(savedType.equals("Mole")){
            tmp = new Intent(this, MoleStartingActivity.class);
        }

        assert tmp != null;
        tmp.putExtra("currentUser", userManager.getCurrentUser().getUserName());
        startActivity(tmp);
    }

}
