package comps413f.android.littleflyingfighter;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class Index  extends Activity{

    @Override
    public void onCreate(android.os.Bundle savedInstanceState){
    super.onCreate(savedInstanceState);
    setContentView(R.layout.mainmenu);}

    Resources res = getResources();
    String[] menus = res.getStringArray(R.array.mainmenu);
    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
            android.R.layout.simple_list_item_1,
            menus);

    ListView listView = (ListView) findViewById(R.id.mainmenu_list);
    listView.setAdapter(adapter);
    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        final Class<?>[] classes = { FlyingAndroidView.class};

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(Index.this, classes[position]);
            startActivity(intent);
        }
    });
}


    /*final Button button=(Button) button.findViewById();
    button.setOnClickListener(new View.OnClickListener()
    {
        public void onClick(View v)
        {
            Intent launchactivity= new Intent(index.this,MainActivity.class);
            startActivity(launchactivity);
        }
     */

