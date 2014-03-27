package io.matthieuhostache.loustics.matieres;

import android.content.Intent;
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
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.matthieuhostache.loustics.R;
import io.matthieuhostache.loustics.games.GamesActivity;

public class MatieresActivity extends ActionBarActivity {

    private ListView exercicesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matieres);

        List<HashMap<String, String>> listExercices = new ArrayList<HashMap<String, String>>();
        exercicesList = (ListView) findViewById(R.id.exercicesList);

        // un item
        HashMap<String, String> map;
        map = new HashMap<String, String>();
        map.put("nom", "Addition");
        map.put("img", String.valueOf(R.drawable.ic_launcher));
        listExercices.add(map);

        // un item
        map = new HashMap<String, String>();
        map.put("nom", "Soustraction");
        map.put("img", String.valueOf(R.drawable.ic_launcher));
        listExercices.add(map);

        ListAdapter adapter = new SimpleAdapter(this, listExercices, R.layout.view_category, new String[] {"nom", "img"},
                new int[] {R.id.cat_name, R.id.cat_image });
        exercicesList.setAdapter(adapter);

        exercicesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                HashMap<String, ?> map = (HashMap<String, ?>) exercicesList.getItemAtPosition(position);

                Intent CalculActivityIntent = new Intent(MatieresActivity.this, CalculActivity.class);
                startActivityForResult(CalculActivityIntent, 1);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.matieres, menu);
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

}
