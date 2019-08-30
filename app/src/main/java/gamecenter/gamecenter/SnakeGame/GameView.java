package gamecenter.gamecenter.SnakeGame;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;
import java.util.List;

import gamecenter.gamecenter.R;

@SuppressLint("ViewConstructor")
public class GameView extends View {
    private boolean isFailed = false;
    private boolean save = false;
    private Paint paint = new Paint();
    private GridBean gridBean;
    private SnakeManager snakeManager;
    private Control control = Control.UP;
    private Context context;

    public GameView(Context context) {
        super(context);
        init();
        this.context = context;
    }

    private void init() {
        // init the grid
        gridBean = new GridBean();

    }

    public SnakeManager getSnakeManager(){return snakeManager;}

    public void setSnakeManager(SnakeManager snakeManager){
        this.snakeManager = snakeManager;
        control = snakeManager.getSnake().getCurrentDirection();
    }

    @Override
    protected void onDraw(Canvas canvas) {

        //show failed text
        if(isFailed){
            paint.setTextSize(50);
            paint.setColor(Color.BLACK);
            canvas.drawText("You Are Dead ~TAT~",200,200,paint);
            return;
        }

        //show grid
        if (gridBean != null) {
            paint.setColor(Color.WHITE);
            drawGrid(canvas);
        }

        //show snake
        if (snakeManager.getSnake() != null) {
            drawSnake(canvas);
        }

        drawSetUp(canvas);
    }

    private void drawSetUp(Canvas canvas) {

        paint.setTextSize(35);
        paint.setColor(Color.WHITE);
        canvas.drawText("level: "+ snakeManager.getSpeedLevel()+"     Time: " + String.valueOf(snakeManager.getSnakeScoreBoard().getTime()) + "s"
                + "      Score: " + snakeManager.getSnakeScoreBoard().getScore(), 10, 1500, paint);

        paint.setColor(Color.BLACK);
        canvas.drawRect(0, 1500,550, 1600 , paint);

        paint.setTextSize(50);
        paint.setColor(Color.WHITE);
        canvas.drawText("Undo", 200, 1550, paint);

        paint.setColor(Color.WHITE);
        canvas.drawRect(550,1500, 1100, 1600, paint);

        paint.setTextSize(50);
        paint.setColor(Color.BLACK);
        canvas.drawText("Save Game", 700, 1550, paint);

    }

    private void drawSnake(Canvas canvas) {
        Snake snake = snakeManager.getSnake();
        for (PointBean point : snake.getSnakeBody()) {
            int startX = gridBean.getBeanWidth() * point.getX();
            int startY = gridBean.getBeanWidth() * point.getY();
            Bitmap snakeBody = BitmapFactory.decodeResource(getResources(), R.drawable.tile_9);
            canvas.drawBitmap(snakeBody, startX, startY, null);
        }

        //record previous snake
        Snake recordSnake = new Snake();
        for(PointBean snakeBody: snakeManager.getSnake().getSnakeBody()){
            PointBean newBody = new PointBean(snakeBody.getX(), snakeBody.getY());
            recordSnake.addBody(newBody);
        }
        recordSnake.setCurrentDirection(snakeManager.getSnake().getCurrentDirection());
        snakeManager.recordStep(recordSnake);
    }

    private void drawGrid(Canvas canvas) {
        //vertical line
        for (int i = 0; i <= GridBean.getGridNumPerRow(); i++) {
            int startX = gridBean.getBeanWidth() * i;
            int startY = 0;
            int stopY = gridBean.getHeightLineLength();//
            canvas.drawLine(startX, startY, startX, stopY, paint);
        }
        //horiontal line
        for (int i = 0; i <= GridBean.getGridNumPerCol(); i++) {
            int startX = 0;
            int stopX =  gridBean.getWidthLineLength();
            int startY = gridBean.getBeanWidth() * i;
            canvas.drawLine(startX, startY, stopX, startY, paint);
        }
    }

    /**
     *
     * @param isAdd if is add
     * @return return 0 if failed, 1 saved, 2 paused, 3 general
     */
    public int refreshView(boolean isAdd){

            //the snake
            List<PointBean> snakeBeanList = snakeManager.getSnake().getSnakeBody();
            //the head of snake
            PointBean head = snakeBeanList.get(0);
            PointBean pointNew = null;

            //change direction of snake head
            if (control == Control.LEFT) {
                pointNew = new PointBean(head.getX() - 1, head.getY());
            } else if (control == Control.RIGHT) {
                pointNew = new PointBean(head.getX() + 1, head.getY());
            } else if (control == Control.UP) {
                pointNew = new PointBean(head.getX(), head.getY() - 1);
            } else if (control == Control.DOWN) {
                pointNew = new PointBean(head.getX(), head.getY() + 1);
            }

            //snake body grow or not
            if (pointNew != null) {
                snakeBeanList.add(0, pointNew);
                if (!isAdd) {
                    snakeBeanList.remove(snakeBeanList.get(snakeBeanList.size() - 1));
                }
            }

            if (isFailed()) {
                isFailed = true;
                invalidate();
                return 0;
            }
            invalidate();
        // repaint view

            if (save){
                return 1;
            }

        return 3;
    }

    //snake touch the constraints
    private boolean isFailed(){
        if (snakeManager.getSnake().getSnakeBody().get(0).getY() == 0 && control == Control.UP ) {
            return true;
        } else if ( snakeManager.getSnake().getSnakeBody().get(0).getX()  == 0 && control == Control.LEFT) {
            return true;
        } else if (snakeManager.getSnake().getSnakeBody().get(0).getY() == GridBean.getGridNumPerCol() - 1 && control == Control.DOWN  ) {
            return true;
        } else
            return snakeManager.getSnake().getSnakeBody().get(0).getX() == GridBean.getGridNumPerRow() - 1 && control == Control.RIGHT;
    }

    int x;
    int y;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) { //movement direction
        int action = event.getAction() & MotionEvent.ACTION_MASK;

        if (action == KeyEvent.ACTION_DOWN) {
            x = (int) (event.getX());
            y = (int) (event.getY());
        }
            if (action == KeyEvent.ACTION_UP) {
                int x = (int) (event.getX());
                int y = (int) (event.getY());

                //a new direction
                Control control = null;
                //direction
                if (Math.abs(x - this.x) > Math.abs(y - this.y)) {
                    if (x > this.x) {
                        control = Control.RIGHT;
                        //move right
                    }
                    if (x < this.x) {
                        control = Control.LEFT;
                        //move left
                    }
                } else if (Math.abs(x - this.x) < Math.abs(y - this.y)) {
                    if (y < this.y) {
                        control = Control.UP;
                        //move up
                    }
                    if (y > this.y) {
                        control = Control.DOWN;
                        //move down
                    }
                } else if (this.y <= 1500){
                    control = snakeManager.getSnake().getCurrentDirection();

                    // Undo button
                }else if (this.x <= 550){
                        Snake newSnake = snakeManager.getLastStep();
                        if(newSnake!=null) {
                            snakeManager.setSnake(newSnake);
                        }else{
                            Toast.makeText(context, "Cannot Undo", Toast.LENGTH_SHORT).show();
                        }
                    control = snakeManager.getSnake().getCurrentDirection();

                 // save button
                }else if (this.x > 550){
                    save = true;
                    control = snakeManager.getSnake().getCurrentDirection();
                }

                //cannot go up or down when snake is already going up or down
                if (this.control == Control.UP || this.control == Control.DOWN) {
                    if (!(control == Control.UP || control == Control.DOWN)) {
                        this.control = control;
                        snakeManager.getSnake().setCurrentDirection(control);
                    }

                    //cannot go left or right when snake is already going left or right
                } else if (this.control == Control.LEFT || this.control == Control.RIGHT) {
                    if (!(control == Control.LEFT || control == Control.RIGHT)) {
                        this.control = control;
                        snakeManager.getSnake().setCurrentDirection(control);
                    }
                }
            }

        return super.onTouchEvent(event);
    }

}
