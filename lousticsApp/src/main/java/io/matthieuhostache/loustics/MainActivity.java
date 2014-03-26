package io.matthieuhostache.loustics;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends ActionBarActivity {

    private Button createNewChild;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createNewChild = (Button) findViewById(R.id.addchild);
        listView = (ListView) findViewById(R.id.listView);

        List<HashMap<String, ?>> childrenList = new ArrayList<HashMap<String, ?>>();
        ChildDB childDB = new ChildDB(this);
        childDB.open();

        childrenList.addAll(childDB.getChildren());
        System.out.println("childrenList : "+ childrenList);

        ChildrenListAdapter adapter = new ChildrenListAdapter(this, childrenList, R.layout.view_child, new String[] {"Name","Pic"},
                new int[] {R.id.item_name,R.id.item_image });
        listView.setAdapter(adapter);
        childDB.close();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                HashMap<String, ?> map = (HashMap<String, ?>) listView.getItemAtPosition(position);

                Intent MySpaceActivityIntent = new Intent(MainActivity.this, MySpaceActivity.class);
                int Id = (Integer)map.get("Id");
                System.out.println("theId :" + Id);
                MySpaceActivityIntent.putExtra("Id",Id);
                startActivityForResult(MySpaceActivityIntent, 1);
            }
        });

        createNewChild.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                Intent AddChildActivityIntent = new Intent(MainActivity.this, AddChildActivity.class);
                startActivityForResult(AddChildActivityIntent, 1);
            }

        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }

}
