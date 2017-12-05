package comps413f.android.littleflyingfighter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;


public class Background {
    static final int SpeedXMagnitude = -20;
    private static int speedX = 0;
    private Bitmap background;
    private static final int bgY = 0; 
    private static int bgX = 0, bgX2;


    public Background(Context context) {
        background = BitmapFactory.decodeResource(context.getResources(), R.drawable.background_city);
        int scaledWidth = (int) (background.getWidth() * (LittleFlyingFighterView.arenaHeight / (float) background.getHeight()));
        background = Bitmap.createScaledBitmap(background, scaledWidth, LittleFlyingFighterView.arenaHeight, true);
    }


    public void stop(boolean stop) {
        if (stop)
            speedX = 0;
        else
            speedX = SpeedXMagnitude;
    }


    public void drawOn(Canvas canvas) {
        if (background != null) {
            bgX += speedX;
            bgX2 = background.getWidth() + bgX;
            if (bgX2 <= 0) {
                bgX = 0;
                canvas.drawBitmap(background, bgX, bgY, null);
            } else {
                canvas.drawBitmap(background, bgX, bgY, null);
                canvas.drawBitmap(background, bgX2, bgY, null);
            }
        }
    }
}
