package gamecenter.gamecenter.MoleGame;

import org.junit.Before;
import org.junit.Test;
import gamecenter.gamecenter.SlidingGame.BoardManager;
import static org.junit.Assert.*;

/**
 * Tests for MoleManager
 */
public class MoleManagerTest {

    private int test_level_1 = 1;
    private String test_user_1 = "Amy";
    private MoleManager test_manager = new MoleManager(test_level_1, test_user_1);

    @Test
    public void getLevelTest() {
        assertEquals(1, test_manager.getLevel());
    }

    @Test
    public void getMoleScoreBoard(){
        MoleScoreBoard MB = test_manager.getMoleScoreBoard();
        assertEquals(1, MB.getLevel());
    }

}
