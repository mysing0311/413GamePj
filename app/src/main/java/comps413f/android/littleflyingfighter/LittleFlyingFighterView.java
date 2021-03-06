package comps413f.android.littleflyingfighter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

/** View of flying android animation. */
public class LittleFlyingFighterView extends SurfaceView {
    /** Delay in each animation cycle, in ms. */
    private static final int CYCLE_DELAY = 30;
    /** Text size. */
    private static final int TEXT_SIZE = 60;

    /** Width and height of the arena. */
    public static int arenaWidth;
    public static int arenaHeight;
    private SoundPlayer sound;

    /** Animation object, the flying android. */
    private LittleFlyingFighter littleFlyingFighter;
    /** List of obstacles objects, i.e., pairs of pipes. */
    private Vector<Obstacles> obstacles = new Vector<Obstacles>();
    /** Scrolling background of the view. */
    Background background;

    /** Timer for the game loop. */
    private Timer timer = null;

    private Context context;

    /** Start time of the game. */
    private long startTime = 0;
    /** Pause time of the game. */
    private long pauseTime = 0;
    /** Total time elapsed of the game. */
    private float totalTime = 0;
    /** Obstacle creation time. */
    private float obstacleCreationTime;

    /** Whether the game is over. */
    private boolean gameOver;

    /** Whether the game is paused and waiting for touching to start. */
    private boolean waitForTouch = true;
    /** Saving and handling of user input of touch events. */
    public class UserInput {
        /** Whether there is a user input present. */
        boolean present = false;

        /**
         * Sets the user input mouse event for later processing. This method is
         * called in event handlers, i.e., in the main UI thread.
         */
        synchronized void save(MotionEvent event) {
            present = true;
        }

        /**
         * Handles the user input to move the flying android upward. This method is
         * called in the thread of the game loop.
         */
        synchronized void handle() {
            if (present) {
                if (waitForTouch) {
                    waitForTouch = false;
                    startTime = System.currentTimeMillis();
                    background.stop(false);
                    ((AnimationDrawable)(littleFlyingFighter.getDrawable())).start();
                    sound = new SoundPlayer(context);

                }
                else {
                    littleFlyingFighter.fly();
                    sound.playBgm();
                }

                present = false;
            }
        }
    }

    private UserInput userInput = new UserInput();


    private class AnimationTask extends TimerTask {
        @Override
        public void run() {
            userInput.handle();

            if (!gameOver && !waitForTouch) {
                createObstacles();
                littleFlyingFighter.move();
                if (littleFlyingFighter.isOutOfArena()) {
                    gameOver();
                }
                else {
                    for (int i = 0; i < obstacles.size(); i++) {
                        obstacles.get(i).move();
                        if (obstacles.get(i).collideWith(littleFlyingFighter)) {
                            gameOver();
                            break;
                        }
                        if (obstacles.get(i).isOutOfArena())
                            obstacles.remove(i);


                        }
                    }
                }


            Canvas canvas = getHolder().lockCanvas();
            if (canvas != null) {
                background.drawOn(canvas);
                for (int i = 0; i < obstacles.size(); i++) {
                    obstacles.get(i).drawOn(canvas);
                }
                littleFlyingFighter.drawOn(canvas);
                drawGameText(canvas);
                getHolder().unlockCanvasAndPost(canvas);
            }
        }

        private Paint textPaint = new Paint();
        private void drawGameText(Canvas canvas) {
            Resources res = getResources();
            textPaint.setColor(Color.BLACK);
            textPaint.setTextSize(TEXT_SIZE);
            if (gameOver) {
                if (startTime > 0) {
                    totalTime += (System.currentTimeMillis() - startTime);
                    startTime = 0;
                }
                float gameTime = totalTime / 1000.0f;
                textPaint.setTextSize(2 * TEXT_SIZE);
                textPaint.setTextAlign(Paint.Align.CENTER);
                canvas.drawText(res.getString(R.string.game_over), getWidth() / 2, getHeight() / 2, textPaint);
                canvas.drawText(res.getString(R.string.time_elapse, gameTime), getWidth() / 2, getHeight() / 2 + (2 * TEXT_SIZE), textPaint);

            }
            else if (waitForTouch) {
                textPaint.setTextSize(2 * TEXT_SIZE);
                textPaint.setTextAlign(Paint.Align.CENTER);
                canvas.drawText(res.getString(R.string.start), getWidth() / 2, getHeight() / 4, textPaint);
            }
            else {
                textPaint.setTextSize(TEXT_SIZE);
                textPaint.setTextAlign(Paint.Align.LEFT);
                float gameTime = (System.currentTimeMillis() - startTime + totalTime) / 1000.0f;
                canvas.drawText(res.getString(R.string.time_elapse, gameTime), TEXT_SIZE, TEXT_SIZE, textPaint);
            }
        }
    }

    public void createObstacles() {
        float gameTime = (System.currentTimeMillis() - startTime + totalTime);
        float timeDiff = gameTime - obstacleCreationTime;
        if (obstacleCreationTime == -1 || timeDiff > ((Math.random()*1000) + 2000)) {
            obstacleCreationTime = gameTime;
            Obstacles o = new Obstacles(context);
            obstacles.add(o);
        }
    }

    public boolean onTouchtoRestart(android.view.MotionEvent evt) {
        if (evt.getAction() == android.view.MotionEvent.ACTION_DOWN) {
            newGame(true);
        }return true;}

    public void gameOver() {
        gameOver = true;
        ((AnimationDrawable)(littleFlyingFighter.getDrawable())).stop();
        background.stop(true);
        sound.playGameOver();

    }

    public void resume() {
        if (timer == null)
            timer = new Timer();
        timer.schedule(new AnimationTask(), 0, CYCLE_DELAY);
    }

    public void pause() {
        totalTime += (System.currentTimeMillis() - startTime);
        waitForTouch = true;
        background.stop(true);
        ((AnimationDrawable) (littleFlyingFighter.getDrawable())).stop();
        timer.cancel();
        timer = null;
    }

    public void newGame(boolean newGame) {
        if (newGame) {
            arenaWidth = getWidth();
            arenaHeight = getHeight();

            background = new Background(context);
            littleFlyingFighter = new LittleFlyingFighter(this, context);
        }

        gameOver = false;
        waitForTouch = true;
        totalTime = 0;
        startTime = -1;
        obstacleCreationTime = -1;
        obstacles.clear();
        littleFlyingFighter.reset();

        ((AnimationDrawable)(littleFlyingFighter.getDrawable())).stop();
        background.stop(true);
    }

    public LittleFlyingFighterView(Context context) {
        super(context);
        this.context = context;

        
        setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                userInput.save(event);
                return true;
            }
        });

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                while (getWidth() == 0)
                    ; // Wait for layout
                newGame(true);
            }
        }, 0);


        }


    protected boolean verifyDrawable(Drawable who) {
        super.verifyDrawable(who);
        return who == littleFlyingFighter.getDrawable();
    }

    public void invalidateDrawable(Drawable drawable) {
    }
}
