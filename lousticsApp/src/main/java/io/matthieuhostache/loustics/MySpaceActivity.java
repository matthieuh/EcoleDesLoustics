package io.matthieuhostache.loustics;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.matthieuhostache.loustics.games.GamesActivity;
import io.matthieuhostache.loustics.matieres.MatieresActivity;

public class MySpaceActivity extends ActionBarActivity {

    private ImageView childPic;
    private TextView spaceChildName;
    private ListView categoriesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_space);

        childPic = (ImageView) findViewById(R.id.childPic);
        spaceChildName = (TextView) findViewById(R.id.spaceChildName);
        categoriesList = (ListView) findViewById(R.id.categoriesList);


        int childId = getIntent().getExtras().getInt("Id");




        ChildDB childDB = new ChildDB(this);
        childDB.open();
        Child currentChild = childDB.getChildWithId(childId);
        childDB.close();
        String child_name = currentChild.getName();
        System.out.println("child_name : " + child_name);
        String child_pic = currentChild.getPicPath();

        spaceChildName.setText(child_name);
        Bitmap image = BitmapFactory.decodeFile(child_pic);
        childPic.setImageBitmap(image);
        initCategories();

    }

    public void initCategories() {
        List<HashMap<String, String>> listCategories = new ArrayList<HashMap<String, String>>();


        HashMap<String, String> map;
        map = new HashMap<String, String>();
        map.put("cat_image", String.valueOf(R.drawable.matieres));
        map.put("cat_name", "Apprendre");
        map.put("cat_number", "");
        listCategories.add(map);

        map = new HashMap<String, String>();
        map.put("cat_image", String.valueOf(R.drawable.games));
        map.put("cat_name", "Jeux débloqués");
        map.put("cat_number", "( 0 )");
        listCategories.add(map);

        ListAdapter adapter = new SimpleAdapter(this, listCategories, R.layout.view_category, new String[] {"cat_image","cat_name","cat_number"},
                new int[] {R.id.cat_image,R.id.cat_name,R.id.cat_number });
        categoriesList.setAdapter(adapter);

        categoriesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                HashMap<String, ?> map = (HashMap<String, ?>) categoriesList.getItemAtPosition(position);

                Intent CatActivityIntent = null;
                switch (position) {
                    case 0:
                        CatActivityIntent = new Intent(MySpaceActivity.this, MatieresActivity.class);
                        break;
                    case 1:
                        CatActivityIntent = new Intent(MySpaceActivity.this, GamesActivity.class);
                        break;
                }
                startActivityForResult(CatActivityIntent, 1);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my_space, menu);
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
            View rootView = inflater.inflate(R.layout.fragment_my_space, container, false);
            return rootView;
        }
    }

}
