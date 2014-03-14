package io.matthieuhostache.loustics;

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
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MySpaceActivity extends ActionBarActivity {

    private ImageView childPic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_space);

        childPic = (ImageView)findViewById(R.id.childPic);

        String picChild = getIntent().getExtras().getString("picChild");
        System.out.println("picChild : " + picChild);

        Bitmap image = BitmapFactory.decodeFile(picChild);
        childPic.setImageBitmap(image);

        initCategories();

    }

    public void initCategories() {
        List<HashMap<String, String>> listCategories = new ArrayList<HashMap<String, String>>();
        ListView categoriesList = (ListView) findViewById(R.id.categoriesList);

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
