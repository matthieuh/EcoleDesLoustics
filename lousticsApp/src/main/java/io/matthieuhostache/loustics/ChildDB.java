package io.matthieuhostache.loustics;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by matthieu on 13/03/14.
 */
public class ChildDB {
    private static final int VERSION_BDD = 3;
    private static final String NOM_BDD = "child.db";

    private static final String TABLE_CHILD = "table_child";
    private static final String COL_ID = "ID";
    private static final int NUM_COL_ID = 0;
    private static final String COL_PIC = "PIC";
    private static final int NUM_COL_PIC = 1;
    private static final String COL_NAME = "NAME";
    private static final int NUM_COL_NAME = 2;
    private static final String COL_POINTS = "POINTS";
    private static final int NUM_COL_POINTS = 3;

    private SQLiteDatabase bdd;

    private LousticsSQLite lousticsSQLite;

    public ChildDB(Context context){
        //On créer la BDD et sa table
        lousticsSQLite = new LousticsSQLite(context, NOM_BDD, null, VERSION_BDD);
    }

    public void open(){
        //on ouvre la BDD en écriture
        bdd = lousticsSQLite.getWritableDatabase();
    }

    public void close(){
        //on ferme l'accès à la BDD
        bdd.close();
    }

    public SQLiteDatabase getBDD(){
        return bdd;
    }

    public long insertChild(Child child){
        //Création d'un ContentValues (fonctionne comme une HashMap)
        ContentValues values = new ContentValues();
        //on lui ajoute une valeur associée à une clé (qui est le nom de la colonne dans laquelle on veut mettre la valeur)
        values.put(COL_PIC, child.getPicPath());
        values.put(COL_NAME, child.getName());
        values.put(COL_POINTS, child.getPoints());

        //on insère l'objet dans la BDD via le ContentValues
        return bdd.insert(TABLE_CHILD, null, values);
    }

    public int updateChild(int id, Child child){
        //La mise à jour d'un enfant dans la BDD fonctionne plus ou moins comme une insertion
        //il faut simple préciser quelle enfant on doit mettre à jour grâce à l'ID
        ContentValues values = new ContentValues();
        values.put(COL_PIC, child.getPicPath());
        values.put(COL_NAME, child.getName());
        values.put(COL_POINTS, child.getPoints());
        return bdd.update(TABLE_CHILD, values, COL_ID + " = " +id, null);
    }

    public int removeChildWithId(int id){
        //Suppression d'un enfant de la BDD grâce à l'ID
        return bdd.delete(TABLE_CHILD, COL_ID + " = " +id, null);
    }

    public Child getChildWithPicPath(String picPath){
        //Récupère dans un Cursor les valeurs correspondants à un enfant contenu dans la BDD (ici on sélectionne le enfant grâce à son titre)
        Cursor c = bdd.query(TABLE_CHILD, new String[] {COL_ID, COL_PIC, COL_NAME, COL_POINTS}, COL_PIC + " LIKE \"" + picPath +"\"", null, null, null, null);
        return cursorToChild(c);
    }

    public Child getChildWithId(int id){
        //Récupère dans un Cursor les valeurs correspondants à un enfant contenu dans la BDD (ici on sélectionne le enfant grâce à son titre)
        Cursor c = bdd.query(TABLE_CHILD, new String[] {COL_ID, COL_PIC, COL_NAME, COL_POINTS}, COL_ID + " LIKE \"" + id +"\"", null, null, null, null);
        return cursorToChild(c);
    }

    public Child getChildWithName(String name){
        //Récupère dans un Cursor les valeurs correspondants à un enfant contenu dans la BDD (ici on sélectionne le enfant grâce à son titre)
        Cursor c = bdd.query(TABLE_CHILD, new String[] {COL_ID, COL_PIC, COL_NAME, COL_POINTS}, COL_NAME + " LIKE \"" + name +"\"", null, null, null, null);
        return cursorToChild(c);
    }

    //Récupère tout les enfants
    public ArrayList<HashMap<String, ?>> getChildren(){
        ArrayList<HashMap<String, ?>> list = new ArrayList<HashMap<String, ?>>();
        Cursor c = bdd.rawQuery("select * from table_child",null);
        System.out.println("cursor : " + c.getCount());
        while(c.moveToNext()){
                HashMap<String, Object> temp = new HashMap<String, Object>();
                temp.put("Id", c.getInt(0));
                temp.put("Pic", c.getString(1));
                temp.put("Name", c.getString(2));
                temp.put("Points", c.getString(3));
                list.add(temp);
                System.out.println("id : " + c.getInt(0));
                System.out.println("pic : " + c.getString(1));
                System.out.println("Name : " + c.getString(2));
                System.out.println("Points : " + c.getString(3));
        }

        return list;
    }

    //Cette méthode permet de convertir un cursor en un enfant
    private Child cursorToChild(Cursor c){
        //si aucun élément n'a été retourné dans la requête, on renvoie null
        if (c.getCount() == 0)
            return null;

        //Sinon on se place sur le premier élément
        c.moveToFirst();
        //On créé un enfant
        Child child = new Child();
        //on lui affecte toutes les infos grâce aux infos contenues dans le Cursor
        child.setId(c.getInt(NUM_COL_ID));
        child.setPicPath(c.getString(NUM_COL_PIC));
        child.setName(c.getString(NUM_COL_NAME));
        child.setPoints(c.getInt(NUM_COL_POINTS));

        //On retourne l'enfant
        return child;
    }
}
