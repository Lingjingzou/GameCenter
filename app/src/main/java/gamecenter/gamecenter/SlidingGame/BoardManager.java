package gamecenter.gamecenter.SlidingGame;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Iterator;

import gamecenter.gamecenter.*;
import gamecenter.gamecenter.User;

/**
 * Manage a board, including swapping tiles, checking for a win, and managing taps.
 */
public class BoardManager implements Serializable, UndoableGameManager {

    /**
     * The board being managed.
     */
    private Board board;
    private Tile blankTile;
    private String picName;
    private User currentUser;
    private int recordLimit;
    private ArrayList<int[][]> recordedSteps = new ArrayList<>();

    /**
     * Manage a new shuffled board.
     */
    BoardManager(int level, int setRecordStepNum) {
        List<Tile> tiles = new ArrayList<>();

        for (int i = 1; i <= level; i++) {
            for (int j = 1; j <= level; j++) {
                Tile tile = new Tile((i - 1) * level + j-1);
                tiles.add(tile);
            }
        }
            blankTile = tiles.get(level * level - 1);

            Collections.shuffle(tiles);
            while(!checkSolvable(tiles)){
                Collections.shuffle(tiles);
            }

            this.board = new Board(tiles, this, level);
            recordLimit = setRecordStepNum;
    }

    //check solvability of tiles
    private boolean checkSolvable(List<Tile> tiles){
        int inversions = 0;
        int i;
        int j;
        for(i = 0; i < tiles.size()-1; i++ ){
            for(j = i+1; j < tiles.size()-1; j++){
                if(tiles.get(j).getId() > tiles.get(i).getId()){
                    inversions+=1;
                }
            }
        }
        return inversions % 2 != 1;
    }

    /**
     * Return the current board.
     */
    public Board getBoard() {
        return board;
    }
    public void setBoard(Board board){this.board = board;}

    void setPicName(String picName){this.picName = picName;}
    String getPicName(){return this.picName;}

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
    public User getCurrentUser(){return currentUser;}

    /**
     * Return whether the tiles are in row-major order.
     *
     * @return whether the tiles are in row-major order
     */
    boolean puzzleSolved() {
        boolean solved = true;

        //Iterate every tiles on board to check if they are in correct order
        @SuppressWarnings("unchecked")
        Iterator<Tile> checkTilesIterator = getBoard().iterator();
        Tile current = checkTilesIterator.next();
        while (checkTilesIterator.hasNext()) {
            Tile next = checkTilesIterator.next();

            //Two tiles are in right order means the later tile's id - the former tile's id = 1
            //noinspection ComparatorResultComparison
            if (current.compareTo(next) != 1) {
                solved = false;
            }
            current = next;
        }
        return solved;
    }

    /**
     * Return whether any of the four surrounding tiles is the blank tile.
     *
     * @param position the tile to check
     * @return whether the tile at position is surrounded by a blank tile
     */
    boolean isValidTap(int position) {

        int row = position / board.getLevel();
        int col = position % board.getLevel();
        int blankId = blankTile.getId();
        // Are any of the 4 the blank tile?
        Tile above = row == 0 ? null : board.getTile(row - 1, col);
        Tile below = row == board.getLevel() - 1 ? null : board.getTile(row + 1, col);
        Tile left = col == 0 ? null : board.getTile(row, col - 1);
        Tile right = col == board.getLevel() - 1 ? null : board.getTile(row, col + 1);

        return (below != null && below.getId() == blankId)
                || (above != null && above.getId() == blankId)
                || (left != null && left.getId() == blankId)
                || (right != null && right.getId() == blankId);
    }

    /**
     * Process a touch at position in the board, swapping tiles as appropriate.
     *
     * @param position the position
     */
    void touchMove(int position) {

        int row = position / board.getLevel();
        int col = position % board.getLevel();
        int blankId = blankTile.getId();

        // tiles is the blank tile, swap by calling Board's swap method.

        Tile above = row == 0 ? null : board.getTile(row - 1, col);
        Tile below = row == board.getLevel() - 1 ? null : board.getTile(row + 1, col);
        Tile left = col == 0 ? null : board.getTile(row, col - 1);
        Tile right = col == board.getLevel() - 1 ? null : board.getTile(row, col + 1);

        //Determine which direction this tile can be moved to.

        if (below != null && below.getId() == blankId) {
            getBoard().swapTiles(row, col, row + 1, col);
        } else if (above != null && above.getId() == blankId) {
            getBoard().swapTiles(row, col, row - 1, col);
        } else if (left != null && left.getId() == blankId) {
            getBoard().swapTiles(row, col, row, col - 1);
        } else if (right != null && right.getId() == blankId) {
            getBoard().swapTiles(row, col, row, col + 1);
        }
    }

    ArrayList<int[][]> getRecordedSteps(){return recordedSteps;}

    @Override
    public void recordStep(Object tilePairCoordinate){
       recordedSteps.add((int[][])tilePairCoordinate);

       //step record list full
       if(recordedSteps.size() == recordLimit+1){
           recordedSteps.remove(0);
       }
    }

    @Override
    public int[][] getLastStep() {
        if (recordedSteps.size() != 0) {
            int[][] lastStepTilePair = recordedSteps.get(recordedSteps.size()-1);
            recordedSteps.remove(recordedSteps.size()-1);
            return lastStepTilePair;
        }
        return null;
    }

}
