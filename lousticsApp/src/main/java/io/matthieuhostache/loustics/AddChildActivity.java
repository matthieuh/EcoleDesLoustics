package io.matthieuhostache.loustics;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
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

import android.widget.Button;
import android.widget.ImageView;
import android.R.bool;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;


public class AddChildActivity extends ActionBarActivity {
    private static final int PHOTO_RESULT = 0;
    private File picFile;
    private ImageView picture;
    private Button openCamera;
    private Button createChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_child);

        openCamera = (Button)findViewById(R.id.openCamera);
        createChild = (Button)findViewById(R.id.createChild);
        picture = (ImageView)findViewById(R.id.childPic);

        openCamera.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                takePhoto();
            }

        });

        createChild.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                createChild();
            }

        });
    }

    public void createChild(){
        if (!picFile.getAbsolutePath().matches("")) {
            ChildDB childDB = new ChildDB(this);
            Child newChild = new Child(picFile.getAbsolutePath());
            System.out.println("new child : " + newChild.getId());
            //On ouvre la base de données pour écrire dedans
            childDB.open();
            //On insère le livre que l'on vient de créer
            childDB.insertChild(newChild);
            //Pour vérifier que l'on a bien créé notre livre dans la BDD
            //on extrait le livre de la BDD grâce au titre du livre que l'on a créé précédemment
            Child childFromDb = childDB.getChildWithId(newChild.getId());
            //Si un livre est retourné (donc si le livre à bien été ajouté à la BDD)
            if(childFromDb != null){
                //On affiche les infos du livre dans un Toast
                Toast.makeText(this, childFromDb.toString(), Toast.LENGTH_LONG).show();
                //On modifie le titre du livre
                //childFromDb.setTitre("J'ai modifié le titre du livre");
                //Puis on met à jour la BDD
                childDB.updateChild(childFromDb.getId(), childFromDb);
            }
        }

    }

    public void displayImage(){
        Bitmap bitmap = null;
        bitmap = BitmapFactory.decodeFile(picFile.getAbsolutePath());
        picture.setImageBitmap(bitmap);

    }

    public void takePhoto(){
        // L'endroit où sera enregistrée la photo
        // Remarquez que mFichier est un attribut de ma classe
        picFile = new File(Environment.getExternalStorageDirectory(), "photo.jpg");
        // On récupère ensuite l'URI associée au fichier
        Uri fileUri = Uri.fromFile(picFile);
        // Maintenant, on crée l'intent
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Et on déclare qu'on veut que l'image soit enregistrée là où pointe l'URI
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

        // Enfin, on lance l'intent pour que l'application de photo se lance
        startActivityForResult(intent, PHOTO_RESULT);
        //onActivityResult(PHOTO_RESULT, RESULT_OK, intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == PHOTO_RESULT && resultCode == RESULT_OK) {

            if (data != null) {
                if (data.hasExtra("data"));

                Bitmap thumbnail = data.getParcelableExtra("data");
                picture.setImageBitmap(thumbnail);

            } else {

                Bitmap image = BitmapFactory.decodeFile(picFile.getAbsolutePath());
                picture.setImageBitmap(image);
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_child, menu);
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
            View rootView = inflater.inflate(R.layout.fragment_add_child, container, false);
            return rootView;
        }
    }

}
