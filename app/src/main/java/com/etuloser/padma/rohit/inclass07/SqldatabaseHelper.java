package com.etuloser.padma.rohit.inclass07;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Rohit on 2/27/2017.
 */

public class SqldatabaseHelper extends SQLiteOpenHelper {

    static final String DB_NAME="mynotedb";
    static final int version=1;

    public SqldatabaseHelper(Context context)
    {
        super(context,DB_NAME, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        CustomObjectTable.onCreate(db);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
CustomObjectTable.onUpgrade(db,oldVersion,newVersion);
    }
}
