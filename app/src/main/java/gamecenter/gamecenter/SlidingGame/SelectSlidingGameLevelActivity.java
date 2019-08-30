package gamecenter.gamecenter.SlidingGame;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.*;
import android.graphics.*;
import android.net.Uri;

import java.io.FileNotFoundException;

import gamecenter.gamecenter.R;
import gamecenter.gamecenter.*;

public class SelectSlidingGameLevelActivity extends AppCompatActivity {

    private static final int LOCAL_IMAGE_CODE = 1;
    private static final String IMAGE_TYPE = "image/*";

    private int currentLevel;
    private SeekBar gameLevel;
    private EditText setUndoNum;
    private int settedUndoNum;

    private UserManager userManager;
    private PictureManager pictureManager;
    private boolean isUploaded = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_sliding_game_level);
        userManager = new UserManager(this);
        userManager.loadUsersOfUserManagerFromFile();
        pictureManager = new PictureManager(this);
        gameLevel = findViewById(R.id.GameLevelBar);
        setUndoNum = (EditText) findViewById(R.id.UndoStepNum);
        userManager.setCurrentUser(userManager.getUser(getIntent().getExtras().getString("currentUser")));


        addPic1Listener();
        addPic2Listener();
        addPic3Listener();
        addPic4Listener();
        addPic5Listener();
        addPic6Listener();
        addPic7Listener();
        addPic8Listener();
        addUploadImageListener();
    }

    /**
     * Activate the picture load button.
     */
    private void addPic1Listener() {
        Button GoButton = findViewById(R.id.picture1);
        GoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentLevel = gameLevel.getProgress() + 3;
                settedUndoNum = Integer.parseInt(setUndoNum.getText().toString());
                switchToGame("p1");
            }
        });
    }


    /**
     * Activate the picture load button.
     */
    private void addPic2Listener() {
        Button GoButton = findViewById(R.id.picture2);
        GoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentLevel = gameLevel.getProgress() + 3;
                settedUndoNum = Integer.parseInt(setUndoNum.getText().toString());
                switchToGame("p2");
            }
        });
    }

    /**
     * Activate the picture load button.
     */
    private void addPic3Listener() {
        Button GoButton = findViewById(R.id.picture3);
        GoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentLevel = gameLevel.getProgress() + 3;
                settedUndoNum = Integer.parseInt(setUndoNum.getText().toString());
                switchToGame("p3");
            }
        });
    }

    /**
     * Activate the picture load button.
     */
    private void addPic4Listener() {
        Button GoButton = findViewById(R.id.picture4);
        GoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentLevel = gameLevel.getProgress() + 3;
                settedUndoNum = Integer.parseInt(setUndoNum.getText().toString());
                switchToGame("p4");
            }
        });
    }

    /**
     * Activate the picture load button.
     */
    private void addPic5Listener() {
        Button GoButton = findViewById(R.id.picture5);
        GoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentLevel = gameLevel.getProgress() + 3;
                settedUndoNum = Integer.parseInt(setUndoNum.getText().toString());
                switchToGame("p5");
            }
        });
    }

    /**
     * Activate the picture load button.
     */
    private void addPic6Listener() {
        Button GoButton = findViewById(R.id.picture6);
        GoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentLevel = gameLevel.getProgress() + 3;
                settedUndoNum = Integer.parseInt(setUndoNum.getText().toString());
                switchToGame("p6");
            }
        });
    }


    /**
     * Activate the picture load button.
     */
    private void addPic7Listener() {
        Button GoButton = findViewById(R.id.picture7);
        GoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentLevel = gameLevel.getProgress() + 3;
                settedUndoNum = Integer.parseInt(setUndoNum.getText().toString());
                switchToGame("p7");
            }
        });
    }

    /**
     * Activate the picture load button.
     */
    private void addPic8Listener() {
        Button GoButton = findViewById(R.id.picture8);
        GoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isUploaded) {
                    currentLevel = gameLevel.getProgress() + 3;
                    settedUndoNum = Integer.parseInt(setUndoNum.getText().toString());
                    switchToGame("p8");
                }else{makeToastNoUploadedImageText();}
            }
        });
    }

    /**
     * Display that no ranking yet.
     */
    private void makeToastNoUploadedImageText() {
        Toast.makeText(this, "No uploaded image", Toast.LENGTH_SHORT).show();
    }

    /**
     * Switch to the SlidingMainActivity view to play the game.
     */
    private void switchToGame(String picName) {
        //create a new board manager
        BoardManager boardManager = new BoardManager(currentLevel, settedUndoNum);
        boardManager.setPicName(picName);
        boardManager.setCurrentUser(userManager.getCurrentUser());
        userManager.getCurrentUser().setCurrentGameManager("currentSliding", boardManager);
        userManager.saveUsersOfUserManagerToFile();
        Intent tmp = new Intent(this, SlidingMainActivity.class);
        tmp.putExtra("currentUser", userManager.getCurrentUser().getUserName());
        startActivity(tmp);
    }

    //============== upload image ================

    /**
     * Activate the picture load button.
     */
    private void addUploadImageListener() {
        Button uploadButton = findViewById(R.id.upload_picture);
        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType(IMAGE_TYPE);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, LOCAL_IMAGE_CODE);
                }
        });
    }


    public static Bitmap getBitmapByUri(Uri uri,ContentResolver cr){
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(cr
                    .openInputStream(uri));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return bitmap;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            String url = "";
            Bitmap bitmap;
            Uri uri = data.getData();
            url = uri.toString().substring(
                    uri.toString().indexOf("///") + 2);
            Log.e("uri", uri.toString());
            if (url.contains(".jpg") && url.contains(".png")) {
                Toast.makeText(this, "PLEASE SELECT IMAGE", Toast.LENGTH_SHORT).show();
                return;
            }
            ContentResolver cr = this.getContentResolver();
            bitmap = getBitmapByUri(uri, cr);
            BitmapDrawable bitmapDrawable = new BitmapDrawable(this.getResources(),bitmap);
            Button picture8 = findViewById(R.id.picture8);
            picture8.setBackground(bitmapDrawable);
            pictureManager.addPicture(bitmap, "p8");
            isUploaded = true;
        }
    }

    /**
     * Switch to the SignUp view to sign up.
     */
    private void switchToStartingActivity() {
        Intent tmp = new Intent(this, SlidingStartingActivity.class);
        tmp.putExtra("currentUser", userManager.getCurrentUser().getUserName());
        startActivity(tmp);
    }

    @Override
    public void onBackPressed() {
        switchToStartingActivity();
    }

}
