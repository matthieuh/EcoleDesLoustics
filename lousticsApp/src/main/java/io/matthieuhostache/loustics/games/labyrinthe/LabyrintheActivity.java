package io.matthieuhostache.loustics.games.labyrinthe;

import io.matthieuhostache.loustics.R;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class LabyrintheActivity extends ActionBarActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_labyrinthe);

        Button newGame = (Button)findViewById(R.id.bNew);
        Button exit = (Button)findViewById(R.id.bExit);
        newGame.setOnClickListener(this);
        exit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        //check which button was clicked with its id
        switch(view.getId()) {
            case R.id.bExit:
                finish();
                break;
            case R.id.bNew:
                String[] levels = {"Maze 1", "Maze 2", "Maze 3"};
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(getString(R.string.levelSelect));
                builder.setItems(levels, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        Intent labyrintheGame = new Intent(LabyrintheActivity.this,LabyrintheGame.class);  //create an Intent to launch the Game Activity
                        Labyrinthe labyrinthe = LabyrintheCreator.getMaze(item+1);    //use helper class for creating the Maze
                        labyrintheGame.putExtra("labyrinthe", labyrinthe);			//add the maze to the intent which we'll retrieve in the Maze Activity
                        startActivity(labyrintheGame);
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.labyrinthe, menu);
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
