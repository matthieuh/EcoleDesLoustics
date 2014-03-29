package io.matthieuhostache.loustics.games.labyrinthe;

/**
 * Created by matthieu on 29/03/14.
 */
import io.matthieuhostache.loustics.R;
import android.os.Bundle;
import android.preference.PreferenceActivity;

public class LabyrinthePref extends PreferenceActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }
}
