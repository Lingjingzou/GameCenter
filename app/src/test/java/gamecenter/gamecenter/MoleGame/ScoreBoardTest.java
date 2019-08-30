package gamecenter.gamecenter.MoleGame;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests for the MoleScoreBoard.
 */
public class ScoreBoardTest {
    /**
     * Initialize variables for test.
     */
    private int test_level_1 = 1;
    private String test_user_1 = "Amy";
    private MoleScoreBoard testboard1 = new MoleScoreBoard(test_level_1, test_user_1);

    @Test
    public void setHitNumTest() {
        testboard1.setHitNum(2);
        assertEquals(2, testboard1.getHitNum());
    }

    @Test
    public void setTimeTest() {
        testboard1.setTime(20);
        assertEquals(20, testboard1.getTime());
    }

//    @Before
//    public void SetUpScore() {
//        testboard1.calculateScore();
//    }

    @Test
    public void getScoreTest() {
        testboard1.setHitNum(2);
        assertEquals(6, testboard1.getScore());
    }

    @Test
    public void getType() {
        assertEquals("moleScoreBoard", testboard1.getType());
    }

    @Test
    public void toStringTest() {
        testboard1.setHitNum(2);
        assertEquals("                    [ Score Board:    " +  "]" + "\n"
                + "                    Total score:         " + "2" + "\n"
                + "                    Level:        " + "1", testboard1.toString());
    }


}
