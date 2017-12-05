package comps413f.android.littleflyingfighter;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable.Callback;


public class LittleFlyingFighter extends Sprite {
    private static final float INITIAL_DY = 15;
    private float dy;

    public LittleFlyingFighter(Callback callback, Context context) {
        drawable = (AnimationDrawable) context.getResources().getDrawable(R.drawable.flying_fighter);
        drawable.setCallback(callback);
        dy = INITIAL_DY;
    }


    public void reset() {
        float x = (LittleFlyingFighterView.arenaWidth - getWidth()) / 2.f;
        float y = (LittleFlyingFighterView.arenaHeight - getHeight()) / 2.f;
        setPosition(x, y);
    }


    public void fly() {
        curPos.y -= 4 * INITIAL_DY;
    }

    @Override
    public void move() {
        if (dy != 0) {
            curPos.y += dy;
            updateBounds();
        }
    }

    @Override
    public boolean isOutOfArena() {
        if (curPos.y < 0 || curPos.y > LittleFlyingFighterView.arenaHeight - getHeight())
            return true;
        return false;
    }
}
