package gamecenter.gamecenter.SnakeGame;

import java.io.Serializable;

public class PointBean implements Serializable {
        private int x;
        private int y;

        PointBean(int x, int y) {
            this.x = x;
            this.y = y;
        }

        int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }
}
