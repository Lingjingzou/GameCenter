package gamecenter.gamecenter.SlidingGame;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.view.*;
import android.widget.TextView;
import android.graphics.*;
import android.graphics.drawable.*;
import android.widget.PopupWindow;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Observable;
import java.util.Observer;
import gamecenter.gamecenter.*;

import gamecenter.gamecenter.R;
import gamecenter.gamecenter.UserManager;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Toast;

/**
 * The game activity.
 */
public class SlidingMainActivity extends AppCompatActivity implements Observer {

    /**
     * The board manager.
     */
    private BoardManager boardManager;
    private PictureManager pictureManager;
    private UserManager userManager;
    /**
     * The buttons to display.
     */
    private ArrayList<Button> tileButtons;

    /**
     * Constants for swiping directions. Should be an enum, probably.
     */
    public static final int UP = 1;
    public static final int DOWN = 2;
    public static final int LEFT = 3;
    public static final int RIGHT = 4;

    // Grid View and calculated column height and width based on device size
    private GestureDetectGridView gridView;
    private static int columnWidth, columnHeight;

    /**
     * Set up the background image for each button based on the master list
     * of positions, and then call the adapter to set the view.
     */
    // Display
    public void display() {
        updateTileButtons(this);
        gridView.setAdapter(new CustomAdapter(tileButtons, columnWidth, columnHeight));
    }

    /**
     * Initialize two MediaPlayers for SlidingGame.
     */
    MediaPlayer music;
    /**
     *Play hit sound.
     */
    public void playBackgroundMusic() {
        if (music == null){
            music = MediaPlayer.create(this, R.raw.castle_in_the_sky);}
        music.start();
    }

    /**
     *Play background sound.
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
        pictureManager = new PictureManager(this);

        String ifLoad = getIntent().getExtras().getString("ifLoad");
        if(ifLoad!=null){
            String loadGameName = getIntent().getExtras().getString("loadGameName");
            userManager.loadGameOfUserFromFile(userManager.getCurrentUser(), "sliding_file", loadGameName);
        }
        boardManager = (BoardManager) userManager.getCurrentUser().getCurrentGameManager("currentSliding");

        Bitmap picture1 = BitmapFactory.decodeResource(getResources(), R.drawable.tile_1);
        Bitmap picture2 = BitmapFactory.decodeResource(getResources(), R.drawable.tile_2);
        Bitmap picture3 = BitmapFactory.decodeResource(getResources(), R.drawable.tile_3);
        Bitmap picture4 = BitmapFactory.decodeResource(getResources(), R.drawable.tile_4);
        Bitmap picture5 = BitmapFactory.decodeResource(getResources(), R.drawable.tile_5);
        Bitmap picture6 = BitmapFactory.decodeResource(getResources(), R.drawable.tile_6);
        Bitmap picture7 = BitmapFactory.decodeResource(getResources(), R.drawable.tile_7);
        int currentLevel = boardManager.getBoard().getLevel();
        String picName = boardManager.getPicName();
        pictureManager.addPicture(picture1, "p1");
        pictureManager.addPicture(picture2, "p2");
        pictureManager.addPicture(picture3, "p3");
        pictureManager.addPicture(picture4, "p4");
        pictureManager.addPicture(picture5, "p5");
        pictureManager.addPicture(picture6, "p6");
        pictureManager.addPicture(picture7, "p7");
        pictureManager.splitPicture(picName, currentLevel);

        createTileButtons(this);
        setContentView(R.layout.activity_sliding_main);
        final TextView score = findViewById(R.id.ScoreBoard);

        // Add View to activity
        gridView = findViewById(R.id.grid);
        gridView.setNumColumns(boardManager.getBoard().getLevel());
        gridView.setBoardManager(boardManager);
        boardManager.getBoard().addObserver(this);
        Thread t = new Thread(){
            @Override
            public void run(){
                while (!boardManager.puzzleSolved()){
                    try{
                        Thread.sleep(1000);

                        runOnUiThread(new Runnable() {
                            @SuppressLint("SetTextI18n")
                            @Override
                            public void run() {
                                boardManager.getBoard().getSlidingScoreBoard().setTime(boardManager.getBoard().getSlidingScoreBoard().getTime() + 1);
                                score.setText("Step: " + boardManager.getBoard().getSlidingScoreBoard().getStep()
                                        + "          "
                                        + "Time: " + String.valueOf(boardManager.getBoard().getSlidingScoreBoard().getTime()) + "s");

                            }
                        });
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
        };
        t.start();
        // Observer sets up desired dimensions as well as calls our display function
        gridView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        gridView.getViewTreeObserver().removeOnGlobalLayoutListener(
                                this);
                        int displayWidth = gridView.getMeasuredWidth();
                        int displayHeight = gridView.getMeasuredHeight();
                        columnWidth = displayWidth / boardManager.getBoard().getLevel();
                        columnHeight = displayHeight / boardManager.getBoard().getLevel();
                        display();
                    }
                });

        addUndoButtonListener();
        addShowOriginButtonListener();
        addSaveBackButtonListener();
    }

    /**
     * Create the buttons for displaying the tiles.
     *
     * @param context the context
     */
    private void createTileButtons(Context context) {
        Board board = boardManager.getBoard();
        tileButtons = new ArrayList<>();

        for (int row = 0; row != board.getLevel(); row++) {
            for (int col = 0; col != board.getLevel(); col++) {
                Button tmp = new Button(context);
                int tileId = board.getTile(row, col).getId();
                // set the picture piece onto button
                BitmapDrawable bitmapDrawable = new BitmapDrawable(context.getResources(),pictureManager.getPicturePieces().get(tileId));
                tmp.setBackground(bitmapDrawable);
                this.tileButtons.add(tmp);
            }
        }
    }

    /**
     * Update the backgrounds on the buttons to match the tiles.
     */
    private void updateTileButtons(Context context) {
        Board board = boardManager.getBoard();
        int nextPos = 0;
        for (Button b : tileButtons) {
            int row = nextPos / board.getLevel();
            int col = nextPos % board.getLevel();
            int tileId = board.getTile(row, col).getId();
            BitmapDrawable bitmapDrawable = new BitmapDrawable(context.getResources(),pictureManager.getPicturePieces().get(tileId));
            b.setBackground(bitmapDrawable);
            nextPos++;
        }
        if(boardManager.puzzleSolved()){
            music.stop();
            music = null;
            playWinMusic();
            showScorePopUpWindow(boardManager.getBoard().getSlidingScoreBoard());
            userManager.getCurrentUser().setCurrentGameManager("currentSliding", null);
        }
    }

    /**
     * Activate the Undo button.
     */
    private void addUndoButtonListener() {
        Button UndoButton = findViewById(R.id.UndoButton);
        UndoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (boardManager.getBoard().undoSwap() == 0) {
                    makeToastCannotUndoText();
                }
            }
        });
    }

    /**
     * Activate the Save and Back button.
     */
    private void addSaveBackButtonListener() {
        Button saveButton = findViewById(R.id.sliding_save_back);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToSaveGame();
            }
        });
    }


    /**
     * Display that a game was loaded successfully.
     */
    private void makeToastCannotUndoText() {
        Toast.makeText(this, "Reach Undo Limit", Toast.LENGTH_SHORT).show();
    }

    /**
     * Activate the Show original picture button.
     */
    private void addShowOriginButtonListener() {
        final LinearLayout mRelativeLayout = findViewById(R.id.sliding_main);
        Button showOriginButton = findViewById(R.id.showOrigin);
        final Context mContext = getApplicationContext();
        // Initialize a new instance of popup window
        showOriginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
                @SuppressLint("InflateParams") View customView = inflater.inflate(R.layout.show_origin_picture,null);
                Button closeButton = customView.findViewById(R.id.picture_close);
                BitmapDrawable bitmapDrawable = new BitmapDrawable(mContext.getResources(),pictureManager.getExactPicture(boardManager.getPicName()));
                closeButton.setBackground(bitmapDrawable);
                final PopupWindow mPopupWindow = new PopupWindow(
                        customView,
                        LayoutParams.WRAP_CONTENT,
                        LayoutParams.WRAP_CONTENT);
                // close the popup window
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

    @SuppressLint("SetTextI18n")
    private void showScorePopUpWindow(SlidingScoreBoard scoreBoard){
        final LinearLayout mRelativeLayout = findViewById(R.id.sliding_main);
        final Context mContext = getApplicationContext();
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams") View customView = inflater.inflate(R.layout.sliding_win_score_page,null);
        Button backButton = customView.findViewById(R.id.backToMainPage);
        TextView scoreInfo = customView.findViewById(R.id.scoreInfo);
        scoreInfo.setText("                         YOU  WIN           " + "\n" + "           " + "\n" + scoreBoard.toString());
        //record the ScoreBoard
        userManager.getCurrentUser().getScoreBoardManager("SlidingScoreManager").addScoreBoard(boardManager.getBoard().getSlidingScoreBoard());
        userManager.saveUsersOfUserManagerToFile();
        //init final win popup window
        final PopupWindow mPopupWindow = new PopupWindow(
                customView,
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPopupWindow.dismiss();
                switchToStartingActivity(); }});
        mPopupWindow.showAtLocation(mRelativeLayout, Gravity.CENTER,0,0);
    }


    /**
     * Switch to the SignUp view to sign up.
     */
    private void switchToStartingActivity() {
        music.stop();
        Intent tmp = new Intent(this, SlidingStartingActivity.class);
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
        tmp.putExtra("savedType", "Sliding");
        startActivity(tmp);
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
    public void update(Observable o, Object arg) {
        display();
    }

    @Override
    public void onBackPressed() {
        music.stop();
        switchToStartingActivity();
    }

}
