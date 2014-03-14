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
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;


public class AddChildActivity extends ActionBarActivity {
    private static final int REQUEST_IMAGE_CAPTURE = 1;
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
        //if (!picFile.getAbsolutePath().equals("")) {
            ChildDB childDB = new ChildDB(this);
            Child newChild = new Child(picFile.getAbsolutePath());
            System.out.println("new child : " + newChild.getPicPath());
            //On ouvre la base de données pour écrire dedans
            childDB.open();
            //On insère le livre que l'on vient de créer
            childDB.insertChild(newChild);
            //Pour vérifier que l'on a bien créé notre livre dans la BDD
            //on extrait le livre de la BDD grâce au chemin de l'img que l'on a créé précédemment
            Child childFromDb = childDB.getChildWithPicPath(newChild.getPicPath());
            //Si un livre est retourné (donc si le livre à bien été ajouté à la BDD)
            if(childFromDb != null){
                //On affiche les infos du livre dans un Toast
                Toast.makeText(this, childFromDb.toString(), Toast.LENGTH_LONG).show();
                //On modifie le titre du livre
                //childFromDb.setTitre("J'ai modifié le titre du livre");
                //Puis on met à jour la BDD
                //childDB.updateChild(childFromDb.getId(), childFromDb);
            } else {
                Toast.makeText(this,"Erreur !", Toast.LENGTH_LONG).show();
            }
            childDB.close();
            Intent MainActivityIntent = new Intent(AddChildActivity.this, MainActivity.class);
            startActivityForResult(MainActivityIntent, 1);
        //}

    }

    public void displayImage(){
        Bitmap bitmap = null;
        bitmap = BitmapFactory.decodeFile(picFile.getAbsolutePath());
        picture.setImageBitmap(bitmap);

    }

    public void takePhoto(){
        // L'endroit où sera enregistrée la photo
        // Remarquez que mFichier est un attribut de ma classe
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        picFile = new File(Environment.getExternalStorageDirectory(), "photo"+timeStamp+".jpg");
        // On récupère ensuite l'URI associée au fichier
        Uri fileUri = Uri.fromFile(picFile);
        // Maintenant, on créer l'intent
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Et on déclare qu'on veut que l'image soit enregistrée là où pointe l'URI
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
                Bitmap image = BitmapFactory.decodeFile(picFile.getAbsolutePath());
                picture.setImageBitmap(image);
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
