package gamecenter.gamecenter.SnakeGame;

import java.io.Serializable;
import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;

import gamecenter.gamecenter.*;

public class SnakeManager implements UndoableGameManager, Serializable {
    private Snake snake = new Snake();
    private int speedLevel;
    private String userName;
    private SnakeScoreBoard snakeScoreBoard;
    private ArrayList<Snake> recordedSteps = new ArrayList<>();
    private int recordLimit;

    SnakeManager(int level, String userName, int recordLimit) {
        this.speedLevel = level;
        this.userName = userName;
        snakeScoreBoard = new SnakeScoreBoard(speedLevel, userName);
        this.recordLimit = recordLimit*3;
        PointBean head = new PointBean(GridBean.getGridNumPerRow() / 2, GridBean.getGridNumPerCol() / 2);
        snake.getSnakeBody().add(head);
    }

    SnakeScoreBoard getSnakeScoreBoard() {
        return snakeScoreBoard;
    }

    int getSpeedLevel() {
        return speedLevel;
    }

    Snake getSnake() {
        return snake;
    }

    void setSnake(Snake snake) {
        this.snake = snake;
    }

    @Override
    public void recordStep(Object recordSnake) {
        recordedSteps.add((Snake) recordSnake);
        if(recordedSteps.size()==recordLimit+1){
            recordedSteps.remove(0);
        }
    }

    @Override
    public Snake getLastStep() {
        int i = 3;
        while (i > 0) {
            if (recordedSteps.size() != 0) {
                recordedSteps.remove(recordedSteps.size() - 1);
            }
            i--;
        }
        if (recordedSteps.size() != 0) {
            Snake snake = recordedSteps.get(recordedSteps.size() - 1);
            recordedSteps.remove(recordedSteps.size() - 1);
            return snake;
        }
        return null;
    }
}
