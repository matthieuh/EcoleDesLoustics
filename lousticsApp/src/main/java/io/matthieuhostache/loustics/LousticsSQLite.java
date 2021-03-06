package io.matthieuhostache.loustics;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

/**
 * Created by matthieu on 13/03/14.
 */
public class LousticsSQLite extends SQLiteOpenHelper {

    private static final String TABLE_CHILD = "table_child";
    private static final String COL_ID = "ID";
    private static final String COL_PIC = "PIC";
    private static final String COL_NAME = "NAME";
    private static final String COL_POINTS = "POINTS";

    private static final String CREATE_BDD = "CREATE TABLE " + TABLE_CHILD + " ("
            + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL_PIC + " TEXT, "
            + COL_NAME + " TEXT NOT NULL, "
            + COL_POINTS + " TEXT);";

    public LousticsSQLite(Context context, String name, CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //on créé la table à partir de la requête écrite dans la variable CREATE_BDD
        db.execSQL(CREATE_BDD);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //On peut fait ce qu'on veut ici moi j'ai décidé de supprimer la table et de la recréer
        //comme ça lorsque je change la version les id repartent de 0
        db.execSQL("DROP TABLE " + TABLE_CHILD + ";");
        onCreate(db);
    }

}

