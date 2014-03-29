package io.matthieuhostache.loustics.games.labyrinthe;

/**
 * Created by matthieu on 29/03/14.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class LabyrintheGame extends Activity {
    Labyrinthe labyrinthe;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        Labyrinthe labyrinthe = ( Labyrinthe )getLastNonConfigurationInstance();
        if(this.labyrinthe == null) {
            this.labyrinthe = (Labyrinthe)extras.get("labyrinthe");
        }
        LabyrintheGameView view = new LabyrintheGameView(this);
        view.setLabyrinthe(this.labyrinthe);
        setContentView(view);
    }

    public Object onRetainNonConfigurationInstance() {
        return this.labyrinthe;
    }
}