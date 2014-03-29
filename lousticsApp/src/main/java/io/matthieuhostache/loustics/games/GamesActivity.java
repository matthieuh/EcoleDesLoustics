package io.matthieuhostache.loustics.games;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.matthieuhostache.loustics.R;
import io.matthieuhostache.loustics.games.labyrinthe.LabyrintheActivity;

public class GamesActivity extends ActionBarActivity {

    private ListView gamesList;
    private int childId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games);

        childId = getIntent().getExtras().getInt("Id");

        List<HashMap<String, String>> listGames = new ArrayList<HashMap<String, String>>();
        gamesList = (ListView) findViewById(R.id.gamesList);

        // un item
        HashMap<String, String> map;
        map = new HashMap<String, String>();
        map.put("nom", "Labyrinthe");
        map.put("img", String.valueOf(R.drawable.ic_launcher));
        map.put("ptsToUnlock", "50");
        listGames.add(map);

        ListAdapter adapter = new SimpleAdapter(this, listGames, R.layout.view_category, new String[] {"nom", "img"},
                new int[] {R.id.cat_name, R.id.cat_image });
        gamesList.setAdapter(adapter);

        gamesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                HashMap<String, ?> map = (HashMap<String, ?>) gamesList.getItemAtPosition(position);
                Intent GameIntent = null;
                switch(position) {
                    case 0:
                        GameIntent = new Intent(GamesActivity.this, LabyrintheActivity.class);
                        break;
                }

                Bundle extras = new Bundle();
                extras.putInt("Id",childId);
                GameIntent.putExtras(extras);
                startActivityForResult(GameIntent, 1);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.games, menu);
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
            View rootView = inflater.inflate(R.layout.fragment_games, container, false);
            return rootView;
        }
    }

}
