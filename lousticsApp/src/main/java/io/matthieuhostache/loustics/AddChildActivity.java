package io.matthieuhostache.loustics;

import android.content.Intent;
import android.graphics.Matrix;
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
import android.widget.EditText;
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
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_TAKE_PHOTO = 1;

    private File picFile;
    private ImageView picture;
    private Button openCamera;
    private Button createChild;
    private EditText addChildName;
    private String mCurrentPhotoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_child);

        openCamera = (Button)findViewById(R.id.openCamera);
        createChild = (Button)findViewById(R.id.createChild);
        picture = (ImageView)findViewById(R.id.childPic);
        addChildName = (EditText)findViewById(R.id.addChildName);

        openCamera.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                dispatchTakePictureIntent();
            }

        });

        createChild.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                createChild();
            }

        });
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photoFile));
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        System.out.println("new child 2: " + mCurrentPhotoPath);
        setPic();
        return image;
    }

    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            setPic();
        }
    }*/

    private void setPic() {
        // Get the dimensions of the View
        int targetW = picture.getWidth();
        int targetH = picture.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        picture.setImageBitmap(bitmap);
    }

    public void createChild(){
        //if (!picFile.getAbsolutePath().equals("")) {
            ChildDB childDB = new ChildDB(this);

            Child newChild = new Child(addChildName.getText().toString(), mCurrentPhotoPath);
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

}
