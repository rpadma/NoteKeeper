package com.etuloser.padma.rohit.inclass07;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Rohit on 2/27/2017.
 */

public class CustomObjectTable {

    static final String TABLENAME="Notes";
    // columanames

    static final String _id="id";
    static final String _note="note";
    static final String _priority="priority";
    static  final String _update_intime="update_time";
    static final String _status="status";

    static public void onCreate(SQLiteDatabase db)
    {

StringBuilder sb=new StringBuilder();
        sb.append("CREATE TABLE  "+TABLENAME+" (");
        sb.append(_id+" integer primary key autoincrement, ");
        sb.append(_note+"  text  not null, ");
        sb.append(_priority+" text not null, ");
        sb.append(_update_intime+" text not null, ");
        sb.append(_status+" text ");
        sb.append(");");

        try
        {

            db.execSQL(sb.toString());
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
    }

static public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion)
{
    db.execSQL("DROP TABLE IF EXIST "+TABLENAME);
    CustomObjectTable.onCreate(db);

}

}
