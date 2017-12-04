package comps413f.android.littleflyingfighter;

import android.widget.*;
import android.view.*;
import android.content.*;
import android.app.Activity;





public class Index  extends Activity{


    public void onCreate(android.os.Bundle savedInstanceState){
    super.onCreate(savedInstanceState);
    setContentView(FlyingAndroidView());}


    final Button button=(Button) button.findViewById();
    button.setOnClickListener(new View.OnClickListener()
    {
        public void onClick(View v)
        {
            Intent launchactivity= new Intent(index.this,MainActivity.class);
            startActivity(launchactivity);
        }

