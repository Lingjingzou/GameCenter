package gamecenter.gamecenter.SnakeGame;

import java.io.Serializable;

class GridBean implements Serializable {

        private static final int gridNumPerRow = 17;
        private static final int gridNumPerCol = 25;
        private int heightLineLength;
        private int widthLineLength;
        private int beanWidth;//width of every bean

        GridBean() {
            heightLineLength = 1540;
            widthLineLength = 1070;
            beanWidth = heightLineLength / gridNumPerCol;//num of bean
        }

        static int getGridNumPerRow() {
            return gridNumPerRow;
        }

        static int getGridNumPerCol() {
        return gridNumPerCol;
    }

        int getHeightLineLength() {
            return heightLineLength;
        }

        int getWidthLineLength() {
        return widthLineLength;
    }

        int getBeanWidth() {
            return beanWidth;
        }
}
