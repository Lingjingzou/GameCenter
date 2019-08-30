package gamecenter.gamecenter.SlidingGame;

import android.content.Context;
import android.widget.Toast;


class MovementController  {

    private BoardManager boardManager = null;

    MovementController() {
    }

    /**
     *Return this board.
     * @param boardManager BoardManager
     */
    void setBoardManager(BoardManager boardManager) {
        this.boardManager = boardManager;
    }

    /**
     * Return information to users when they win the game or touch an invalid tile.
     * @param context Context
     * @param position int
     */
    void processTapMovement(Context context, int position) {
        if (boardManager.isValidTap(position)) {
            boardManager.touchMove(position);
            if (boardManager.puzzleSolved()) {
                Toast.makeText(context, "YOU WIN!", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(context, "Invalid Tap", Toast.LENGTH_SHORT).show();
        }
    }

}
