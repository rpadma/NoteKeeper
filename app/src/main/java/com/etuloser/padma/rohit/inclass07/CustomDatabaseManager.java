package com.etuloser.padma.rohit.inclass07;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rohit on 2/27/2017.
 */

public class CustomDatabaseManager  {


    private Context context;
    private SQLiteDatabase db;
    private SqldatabaseHelper dbHelper;
    private CustomDAO cDAO;


    public CustomDatabaseManager(Context context) {
        this.context = context;
        dbHelper = new SqldatabaseHelper(this.context);
        db = dbHelper.getWritableDatabase();
        cDAO = new CustomDAO(db);
    }


    public long save(CustomObject obj) {
        return cDAO.save(obj);
    }

    public boolean update(CustomObject obj) {
        return cDAO.Update(obj);
    }

    public boolean delete(CustomObject obj) {
        return cDAO.Delete(obj);
    }

    public CustomObject get(long _id) {
        return cDAO.get(_id);
    }

    public List<CustomObject> getAll() {
        return cDAO.getall();
    }

    public List<CustomObject> getallbypending() {return cDAO.getallbypending();}
    public List<CustomObject> getallbycompleted() {return cDAO.getallbycompleted();}
    public List<CustomObject> getallbytime() {return cDAO.getallbytime();}
    public List<CustomObject> getallbypriority() {return cDAO.getallbypriority();}







}
