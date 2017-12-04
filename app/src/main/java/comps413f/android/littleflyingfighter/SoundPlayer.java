package comps413f.android.littleflyingfighter;
import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

public class SoundPlayer {
    private static SoundPool soundPool;
    private static int bgm;
    private static int gameover;

    public SoundPlayer(Context context){
        soundPool = new SoundPool(2,AudioManager.STREAM_MUSIC,0);
        bgm = soundPool.load(context,R.raw.bgm,1);
        gameover = soundPool.load(context,R.raw.gameover,1);
    }

    public void playBgm(){
        soundPool.play(bgm,0.5f,0.5f,1,1,1.0f);
    }
    public void playGameOver(){
        soundPool.play(gameover,1.0f,1.0f,1,0,1.0f);
    }
}
