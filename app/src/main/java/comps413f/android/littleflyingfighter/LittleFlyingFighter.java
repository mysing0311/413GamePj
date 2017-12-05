package comps413f.android.littleflyingfighter;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable.Callback;

/** The flying android. */
public class LittleFlyingFighter extends Sprite {
    private static final float INITIAL_DY = 15;  // Initial velocity in vertical direction
    private float dy;  // y velocity of the flying android object
    /** Constructor. */
    public LittleFlyingFighter(Callback callback, Context context) {
        drawable = (AnimationDrawable) context.getResources().getDrawable(R.drawable.flying_fighter);
        drawable.setCallback(callback);
        dy = INITIAL_DY;
    }

    /** Reset the x, y position of the flying android. */
    public void reset() {
        // Add code here
        // Task 1: Reset the flying android
        // i. Locate it at the center of the arena 
        float x = (LittleFlyingFighterView.arenaWidth - getWidth()) / 2.f;
        float y = (LittleFlyingFighterView.arenaHeight - getHeight()) / 2.f;

        // ii. Update its position
        setPosition(x, y);
    }

    /** Move the flying android upward.*/
    public void fly() {
        curPos.y -= 4 * INITIAL_DY;
    }

    @Override
    /** Move the flying android. */
    public void move() {
        if (dy != 0) {
            // Add code here
            // Task 2: Move the flying android
            // i. Update the new y position of the flying android
            curPos.y += dy;

            // ii. Update the boundary of the flying android drawable
            updateBounds();
        }
    }

    @Override
    /** Evaluate if the flying android is moving out of the arena, i.e., game end. */
    public boolean isOutOfArena() {
        if (curPos.y < 0 || curPos.y > LittleFlyingFighterView.arenaHeight - getHeight())
            return true;
        return false;
    }
}
