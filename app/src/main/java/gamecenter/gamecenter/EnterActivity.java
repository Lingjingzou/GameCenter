package gamecenter.gamecenter;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * The enter activity.Users log in
 * in this view.
 */
public class EnterActivity extends AppCompatActivity {

    private UserManager userManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter);
        addLogInButtonListener();
        addSignUpButtonListener();
        userManager = new UserManager(this);
        userManager.loadUsersOfUserManagerFromFile();
    }


    /**
     * Activate the SignUp button.Users switch to SignUp View by
     * clicking this button
     */
    private void addSignUpButtonListener() {
        Button SignUpButton = findViewById(R.id.SignUpButton);
        SignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToSignUp();
            }
        });
    }

    /**
     * Activate the LogIn button.
     */
    private void addLogInButtonListener() {
        Button LogInButton = findViewById(R.id.LogInButton);

        LogInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String logUserName = ((EditText) findViewById(R.id.LogUserName)).getText().toString();
                String logUserPassword = ((EditText) findViewById(R.id.LogUserPassword)).getText().toString();
                //user log in
                if(userManager.userLogIn(logUserName, logUserPassword)==1) {
                    userManager.setCurrentUser(userManager.getUser(logUserName));
                        userManager.saveUsersOfUserManagerToFile();
                        makeToastValidUserText();
                        //go to sliding game
                            switchToSelectGameActivity();
                        }else{ makeToastInValidUserText();}
                ((EditText) findViewById(R.id.LogUserName)).setText("");
                ((EditText) findViewById(R.id.LogUserPassword)).setText("");
            }
        });
    }

    /**
     * Display that the user logged in successfully.
     */
    private void makeToastValidUserText() {
        Toast.makeText(this, "Logged in successfully", Toast.LENGTH_SHORT).show();
    }

    /**
     * Display that the user doesn't exist.
     */
    private void makeToastInValidUserText() {
        Toast.makeText(this, "User doesn't exist or incorrect password", Toast.LENGTH_SHORT).show();
    }

    /**
     * Switch to the SignUp view to sign up.
     */
    private void switchToSignUp() {
        Intent tmp = new Intent(this, SignUpActivity.class);
        startActivity(tmp);
    }

    /**
     * Switch to the Game Main view.
     */
    private void switchToSelectGameActivity() {
        Intent tmp = new Intent(this, SelectGameActivity.class);
        tmp.putExtra("currentUser",userManager.getCurrentUser().getUserName());
        startActivity(tmp);
    }
}