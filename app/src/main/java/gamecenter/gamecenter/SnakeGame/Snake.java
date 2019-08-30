package gamecenter.gamecenter.SnakeGame;

import java.io.Serializable;
import java.util.LinkedList;

class Snake implements Serializable {

    private LinkedList<PointBean> snakeBody = new LinkedList<>();
    private Control currentDirection = Control.UP;

    LinkedList<PointBean> getSnakeBody(){return snakeBody;}

    void addBody(PointBean body){snakeBody.add(body);}

    Control getCurrentDirection() {
        return this.currentDirection;
    }

    void setCurrentDirection(Control currentDirection) {
        this.currentDirection = currentDirection;
    }

}
