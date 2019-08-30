package gamecenter.gamecenter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.Objects;

import gamecenter.gamecenter.MoleGame.MoleMainActivity;
import gamecenter.gamecenter.SlidingGame.*;
import gamecenter.gamecenter.SnakeGame.*;

public class LoadSavedGameActivity extends AppCompatActivity {

    UserManager userManager;
    String loadType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_saved_game);

        userManager = new UserManager(this);
        userManager.loadUsersOfUserManagerFromFile();
        userManager.setCurrentUser(userManager.getUser(Objects.requireNonNull(getIntent().getExtras()).getString("currentUser")));
        loadType = getIntent().getExtras().getString("loadType");

        final LinearLayout linearLayout = findViewById(R.id.saved_game_linear);

        setUpButton(loadType, linearLayout);

    }

    private void setUpButton(final String loadType, LinearLayout linearLayout) {
        for (int i = 0; i < userManager.getCurrentUser().get_user_saved_games(loadType).size(); i++) {
            final Button btn = new Button(this);
            final int finalI = i;
            btn.setText(userManager.getCurrentUser().get_user_saved_games(loadType).get(i));
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switchToGame(userManager.getCurrentUser().get_user_saved_games(loadType).get(finalI));
                }
            });

            linearLayout.setOrientation(LinearLayout.VERTICAL);
            linearLayout.addView(btn);

        }
    }

    /**
     * Switch to the SignUp view to sign up.
     */
    private void switchToGame(String loadGameName) {
        Intent tmp = null;
        if(loadType.equals("sliding_file")) {
            tmp = new Intent(this, SlidingMainActivity.class);
        }else if(loadType.equals("snake_file")) {
            tmp = new Intent(this, SnakeMainActivity.class);
        }else if(loadType.equals("mole_file")){
            tmp = new Intent(this, MoleMainActivity.class);
        }

        assert tmp != null;
        tmp.putExtra("currentUser",userManager.getCurrentUser().getUserName());
        tmp.putExtra("ifLoad", "load");
        tmp.putExtra("loadGameName", loadGameName);
        startActivity(tmp);
    }

}
