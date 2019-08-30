package gamecenter.gamecenter.SlidingGame;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import gamecenter.gamecenter.*;

import static org.junit.Assert.*;

public class BoardAndTileTest {

    private SlidingScoreBoard slidingScoreBoard3 = new SlidingScoreBoard(3);
    private SlidingScoreBoard slidingScoreBoard4 = new SlidingScoreBoard(4);
    private SlidingScoreBoard slidingScoreBoard5 = new SlidingScoreBoard(5);
    private BoardManager boardManager3 = new BoardManager(3,3);
    private BoardManager boardManager4 = new BoardManager(4,3);
    private BoardManager boardManager5 = new BoardManager(5,3);
    private Board board3;
    private Board board4;
    private Board board5;
    private Tile testTile;

    // =====  setUp  =====
    private List<Tile> makeTiles(int numTiles) {
        List<Tile> tiles = new ArrayList<>();
        for (int tileNum = 0; tileNum != numTiles; tileNum++) {
            tiles.add(new Tile(tileNum));
        }
        return tiles;
    }

    @Before
    public void setUpTile(){
        testTile = new Tile(1);
    }

    // =====  tests for Tile =====
    @Test
    public void getIdTest() {
        assertEquals(1, testTile.getId());
    }

    @Test
    public void testSetId() {
        testTile.setId(2);
        assertEquals(2, testTile.getId());
    }

    @Test
    public void testTilecompareTo() {
        Tile testTile1 = new Tile(3);
        assertTrue(testTile.compareTo(testTile1) > 0);
    }


    // =====  tests for board  =====
    @Before
    public void setUpBoard(){
        board3 = new Board(makeTiles(3*3), boardManager3, 3);
        board4 = new Board(makeTiles(4*4), boardManager4, 4);
        board5 = new Board(makeTiles(5*5), boardManager5, 5);
        boardManager3.setBoard(board3);
        boardManager4.setBoard(board4);
        boardManager5.setBoard(board5);
        board3.setSlidingScoreBoard(slidingScoreBoard3);
        board4.setSlidingScoreBoard(slidingScoreBoard4);
        board5.setSlidingScoreBoard(slidingScoreBoard5);
    }

    @Test
    public void getSlidingScoreBoardTest() {
        assertEquals(slidingScoreBoard3, board3.getSlidingScoreBoard());
        assertEquals(slidingScoreBoard4, board4.getSlidingScoreBoard());
        assertEquals(slidingScoreBoard5, board5.getSlidingScoreBoard());
    }

    @Test
    public void iteratorTest() {
        int i = 0;
        int j = 0;
        int k = 0;
        Iterator<Tile> iter3 = board3.iterator();
        while(iter3.hasNext()){
            assertEquals(i, iter3.next().getId());
            i++;
        }
        Iterator<Tile> iter4 = board4.iterator();
        while(iter4.hasNext()){
            assertEquals(j, iter4.next().getId());
            j++;
        }
        Iterator<Tile> iter5 = board5.iterator();
        while(iter5.hasNext()){
            assertEquals(k, iter5.next().getId());
            k++;
        }
    }

    @Test
    public void getTileTest() {
        assertEquals(0, board3.getTile(0,0).getId());
        assertEquals(1, board3.getTile(0,1).getId());
        assertEquals(14, board4.getTile(3,2).getId());
        assertEquals(15, board4.getTile(3,3).getId());
        assertEquals(23, board5.getTile(4,3).getId());
        assertEquals(24, board5.getTile(4,4).getId());
    }

    @Test
    public void getLevelTest() {
        assertEquals(3,board3.getLevel());
        assertEquals(4,board4.getLevel());
        assertEquals(5,board5.getLevel());
    }

    @Test
    public void swapTilesTest() {
        board4.swapTiles(0,0,0,1);
        assertEquals(1, board4.getTile(0,0).getId());
        assertEquals(0, board4.getTile(0,1).getId());
    }

    @Test
    public void undoSwap() {
        board4.swapTiles(0,0,0,1);
        board4.undoSwap();
        assertEquals(0,board4.getTile(0,0).getId());
        assertEquals(1,board4.getTile(0,1).getId());
    }


    // ===== tests for board manager =====
    @Test
    public void getBoardTest() {
        assertEquals(board4, boardManager4.getBoard());
    }

    @Test
    public void getAndSetPicNameTest() {
        boardManager4.setPicName("oo");
        assertEquals("oo", boardManager4.getPicName());
    }

    @Test
    public void setAndGetCurrentUserTest() {
        User user = new User("A", "123");
        boardManager4.setCurrentUser(user);
        assertEquals(user, boardManager4.getCurrentUser());
    }

    /**
     * Test whether isValidHelp works.
     */
    @Test
    public void testIsValidTap() {
        assertTrue(boardManager3.isValidTap(7));
        assertTrue(boardManager3.isValidTap(5));
        assertFalse(boardManager3.isValidTap(0));
    }

        /**
     * Test whether swapping the last two tiles works.
     */
    @Test
    public void testSwapLastTwo() {
        int id1 = boardManager4.getBoard().getTile(3,2).getId();
        int id2 = boardManager4.getBoard().getTile(3,3).getId();
        boardManager4.getBoard().swapTiles(3,3,3,2);
        assertEquals(id1, boardManager4.getBoard().getTile(3,3).getId());
        assertEquals(id2,boardManager4.getBoard().getTile(3,2).getId());
    }

        /**
     * Test whether swapping the first two tiles works.
     */
    @Test
    public void testSwapFirstTwo() {
        int id1 = boardManager4.getBoard().getTile(0,0).getId();
        int id2 = boardManager4.getBoard().getTile(0,1).getId();
        boardManager4.getBoard().swapTiles(0,0,0,1);
        assertEquals(id1, boardManager4.getBoard().getTile(0,1).getId());
        assertEquals(id2,boardManager4.getBoard().getTile(0,0).getId());
    }

        /**
     * Test whether swapping two tiles makes a solved board unsolved.
     */
    @Test
    public void testIsSolved() {
        assertTrue(boardManager4.puzzleSolved());
        boardManager4.getBoard().swapTiles(0,0,0,1);
        assertFalse(boardManager4.puzzleSolved());
    }

    // =====  tests for SlidingScoreBoard  =====
    @Test
    public void calculateScore() {
        board3.swapTiles(0,0,1,1);
        board3.getSlidingScoreBoard().setTime(2);
        board3.getSlidingScoreBoard().calculateScore();
        assertEquals(496, board3.getSlidingScoreBoard().getScore());
    }

    @Test
    public void compareTo() {
        SlidingScoreBoard s = new SlidingScoreBoard(3);
        s.setStep(3);

        ////?????
        assertEquals(0,board3.getSlidingScoreBoard().compareTo(s));
    }

    @Test
    public void getStep() {
        assertEquals(0,board3.getSlidingScoreBoard().getStep());
    }

    @Test
    public void setStep() {
        assertEquals(0,board3.getSlidingScoreBoard().getStep());
        board3.getSlidingScoreBoard().setStep(2);
        assertEquals(2,board3.getSlidingScoreBoard().getStep());
    }

    @Test
    public void updateStep() {
        assertEquals(0,board3.getSlidingScoreBoard().getStep());
        board3.swapTiles(0,0,1,1);
        assertEquals(1,board3.getSlidingScoreBoard().getStep());
    }

    @Test
    public void getType() {
        assertEquals("slidingScoreBoard", board3.getSlidingScoreBoard().getType());
    }

    @Test
    public void toStringTest(){
        assertEquals( "                    [ Score Board:    " +  "]" + "\n"
                + "                    Total score:         " + 500 + "\n"
                + "                              " + "\n"
                + "                    Total Step:         " + 0 + "\n"
                + "                              " + "\n"
                + "                    Total time:         " + 0 + "\n", board3.getSlidingScoreBoard().toString());
    }

    @Test
    public void noRecordStepTest(){
        assertNull(boardManager3.getLastStep());
        assertNull(boardManager4.getLastStep());
        assertNull(boardManager5.getLastStep());
    }


    @Test
    public void oneRecordStepTest() {
        board3.swapTiles(0,0,0,1);
        board4.swapTiles(3,3,2,3);
        board5.swapTiles(2,1,3,1);
        assertEquals(1, boardManager3.getRecordedSteps().size());
        assertEquals(1, boardManager4.getRecordedSteps().size());
        assertEquals(1, boardManager5.getRecordedSteps().size());
    }

    @Test
    public void fullRecordStepTest() {
        assertNull(boardManager3.getLastStep());
        board3.swapTiles(0,0,0,1);
        assertEquals(1,boardManager3.getRecordedSteps().size());
        board3.swapTiles(1,1,2,1);
        assertEquals(2,boardManager3.getRecordedSteps().size());
        board3.swapTiles(2,1,2,2);
        assertEquals(3,boardManager3.getRecordedSteps().size());
        board3.swapTiles(1,0,2,0);
        assertEquals(3,boardManager3.getRecordedSteps().size());
        board3.swapTiles(1,1,2,1);
        assertEquals(3,boardManager3.getRecordedSteps().size());
    }


    @Test
    public void getLastStepTest(){
        assertNull(boardManager3.getLastStep());
        assertNull(boardManager4.getLastStep());
        assertNull(boardManager5.getLastStep());
    }

}