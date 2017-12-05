package comps413f.android.littleflyingfighter;

import android.content.Context;
import android.graphics.Canvas;

/** The obstacles. */
public class Obstacles {
    /** Pair of obstacles. */
    private Obstacle [] obstacles = new Obstacle[2];

    /** Constructor. */
    public Obstacles(Context context) {
        int x = LittleFlyingFighterView.arenaWidth;
        int minDistance = (int) (0.25 * LittleFlyingFighterView.arenaHeight);
        int maxDistance = (int) (0.3 * LittleFlyingFighterView.arenaHeight);
        int distance = minDistance + (int) (Math.random() * (maxDistance - minDistance));
        int minY = (int) (0.1 * LittleFlyingFighterView.arenaHeight);
        int maxY = (int) (0.9 * LittleFlyingFighterView.arenaHeight) - distance;
        int y = minY + (int) (Math.random() * (maxY - minY));
        obstacles[0] = new Obstacle(context, R.drawable.cylinder_2);//upper
        obstacles[0].setPosition(x, y - obstacles[0].getHeight());
        obstacles[1] = new Obstacle(context, R.drawable.cylinder_1);//lower
        obstacles[1].setPosition(x, y + distance);
    }

    public void move() {
        for (Obstacle obstacle:obstacles) {
            obstacle.move();
        }
    }

    public void drawOn(Canvas canvas) {
        for (Obstacle obstacle:obstacles) {
            obstacle.drawOn(canvas);
        }
    }

    public boolean isOutOfArena() {
        if (obstacles[0].isOutOfArena())
            return true;
        return false;
    }

    public boolean collideWith(Sprite sprite) {
        for (Obstacle obstacle:obstacles) {
            if (obstacle.collideWith(sprite))
                return true;
        }
        return false;
    }

    class Obstacle extends Sprite {
        private float dx = Background.SpeedXMagnitude;
        public Obstacle(Context context, int resId) {
            drawable = context.getResources().getDrawable(resId);
        }

        @Override
        public void move() {
            if (dx != 0) {
                // Update the new x position of the obstacle
                curPos.x += dx;

                // Update the boundary of the obstacle drawable
                updateBounds();
            }
        }

        @Override
        public boolean isOutOfArena() {
            if (getCurPos().x + getWidth() < 0)
                return true;
            return false;
        }
    }
}