package gamecenter.gamecenter.SnakeGame;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.Objects;

import gamecenter.gamecenter.R;
import gamecenter.gamecenter.SaveGameActivity;
import gamecenter.gamecenter.UserManager;
import gamecenter.gamecenter.ScoreBoard;

public class SnakeMainActivity extends AppCompatActivity {

    private UserManager userManager;

    private static final int WHAT_REFRESH = 300;
    private int time = 300;// time between every refresh
    private int level;

    private int count = 0 ;
    private GameView gameView;
    private boolean isFailed = false;
    private boolean isSave = false;
    private Thread t;

    /**
     * Initialize two MediaPlayers for SlidingGame.
     */
    MediaPlayer music;
    /**
     *Play background sound.
     */
    public void playBackgroundMusic() {
        if (music == null){
            music = MediaPlayer.create(this, R.raw.spirted_away);}
        music.start();
    }

    /**
     *Play win sound.
     */
    public void playWinMusic() {
        if (music == null){
            music = MediaPlayer.create(this, R.raw.win);}
        music.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        playBackgroundMusic();

        userManager = new UserManager(this);
        userManager.loadUsersOfUserManagerFromFile();
        userManager.setCurrentUser(userManager.getUser(Objects.requireNonNull(getIntent().getExtras()).getString("currentUser")));

        String ifLoad = getIntent().getExtras().getString("ifLoad");
        if(ifLoad!=null){
            String loadGameName = getIntent().getExtras().getString("loadGameName");
            userManager.loadGameOfUserFromFile(userManager.getCurrentUser(), "snake_file", loadGameName);
        }

        SnakeManager snakeManager = (SnakeManager) userManager.getCurrentUser().getCurrentGameManager("currentSnake");
        gameView = new GameView(this);
        gameView.setSnakeManager(snakeManager);
        level = snakeManager.getSpeedLevel();
        setContentView(gameView);

        Bitmap background = BitmapFactory.decodeResource(getResources(), R.drawable.snake_game_background);
        BitmapDrawable bitmapDrawable = new BitmapDrawable(this.getResources(), background);
        gameView.setBackground(bitmapDrawable);
        gameView.setClickable(true);

        t = new Thread(){
            @Override
            public void run(){
                while (!isFailed ){
                    if(t.isInterrupted()){
                        break;
                    }
                    try{
                        Thread.sleep(1000);

                        runOnUiThread(new Runnable() {
                            @SuppressLint("SetTextI18n")
                            @Override
                            public void run() {
                                gameView.getSnakeManager().getSnakeScoreBoard().setTime(gameView.getSnakeManager().getSnakeScoreBoard().getTime() + 1);
                            }
                        });
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
        };
        t.start();

        sendControlMessage();
    }

    private void sendControlMessage(){
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                handler.sendEmptyMessage(WHAT_REFRESH);
            }
        },time);
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {

            super.handleMessage(msg);
            if(WHAT_REFRESH == msg.what){
                boolean isAdd = false;
                count ++ ;
                if(count % (20/level) == 0){
                    //snake grow
                    isAdd = true;
                    if(time - (20/level) >0){
                        time = time - (20/level);
                    }
                }
                //check if the game failed
                int condition = gameView.refreshView(isAdd);
                if(condition ==0){
                    isFailed = true;
                }else if(condition==1){
                    isSave = true;
                }
                if(!isFailed && !isSave){ //GAME CONTINUE
                    sendControlMessage();
                    userManager.getCurrentUser().setCurrentGameManager("currentSnake", gameView.getSnakeManager());
                    userManager.saveUsersOfUserManagerToFile();
                }else if(!isSave){
                    userManager.getCurrentUser().setCurrentGameManager("currentSnake", null);
                    t.interrupt();
                    userManager.saveUsersOfUserManagerToFile();
                    music.stop();
                    music = null;
                    playWinMusic();
                    showScorePopUpWindow(gameView.getSnakeManager().getSnakeScoreBoard());
                }else if(!isFailed){
                    switchToSaveGame();
                }
            }
        }
    };

    @SuppressLint("SetTextI18n")
    private void showScorePopUpWindow(ScoreBoard scoreBoard){
        final Context mContext = getApplicationContext();
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams") View customView = inflater.inflate(R.layout.snake_win_score_page,null);
        Button backButton = customView.findViewById(R.id.backToSnakeMainPage);
        TextView scoreInfo = customView.findViewById(R.id.snakeScoreInfo);
        scoreInfo.setText("                         YOU  DIED           " + "\n" + "           " + "\n" + scoreBoard.toString());
        //record the ScoreBoard
        userManager.getCurrentUser().getScoreBoardManager("SnakeScoreManager").addScoreBoard(gameView.getSnakeManager().getSnakeScoreBoard());
        userManager.saveUsersOfUserManagerToFile();
        //init final win popup window
        final PopupWindow mPopupWindow = new PopupWindow(
                customView,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPopupWindow.dismiss();
                switchToSnakeStartingActivity(); }});
        mPopupWindow.showAtLocation(gameView, Gravity.CENTER,0,0);
    }

    /**
     * Dispatch onPause() to fragments.
     */
    @Override
    protected void onPause() {
        super.onPause();
        userManager.saveUsersOfUserManagerToFile();
    }

    @Override
    public void onBackPressed() {
        t.interrupt();
        music.stop();
        switchToSnakeStartingActivity();
    }

    /**
     * Switch to the SignUp view to sign up.
     */
    private void switchToSnakeStartingActivity() {
        music.stop();
        Intent tmp = new Intent(this, SnakeStartingActivity.class);
        tmp.putExtra("currentUser", userManager.getCurrentUser().getUserName());
        startActivity(tmp);
    }

    /**
     * Switch to the SignUp view to sign up.
     */
    private void switchToSaveGame() {
        music.stop();
        Intent tmp = new Intent(this, SaveGameActivity.class);
        tmp.putExtra("currentUser", userManager.getCurrentUser().getUserName());
        tmp.putExtra("savedType", "Snake");
        startActivity(tmp);
    }

}