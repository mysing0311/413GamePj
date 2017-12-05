package comps413f.android.littleflyingfighter;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.preference.PreferenceManager;

public class MainActivity extends Activity {
    /** The animation view. */
    private LittleFlyingFighterView animationView;
    private long downtime = System.currentTimeMillis();
    private long eventime = System.currentTimeMillis()+100;
    float x = 0.0f;
    float y =0.0f;
    int metaState = 0;
    public MotionEvent evt = MotionEvent.obtain(downtime,eventime,MotionEvent.ACTION_DOWN,x,y,metaState);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        animationView = new LittleFlyingFighterView(this);
        setContentView(animationView);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public boolean onTouchtoResume(android.view.MotionEvent evt) {
        if (evt.getAction() == android.view.MotionEvent.ACTION_DOWN) {
            animationView.resume();

        }return true;}

    /**
     * Handles the option menu selection. This method is called when an options
     * menu item is selected.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.action_restart:
            animationView.newGame(false);
            break;

        case R.id.action_pause:
            animationView.pause();
            onTouchtoResume(evt);

        break;
        }
        return false;
    }

    /** Resumes the animation. This method is called when the activity is resumed. */
    @Override
    protected void onResume() {
        super.onResume();
        animationView.resume();
    }

    /** Pauses the animation. This method is called when the activity is paused. */
    @Override
    protected void onPause() {
        super.onPause();
        animationView.pause();
    }
}
