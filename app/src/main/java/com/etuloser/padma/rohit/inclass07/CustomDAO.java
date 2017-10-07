package com.etuloser.padma.rohit.inclass07;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Rohit on 2/27/2017.
 */

public class CustomDAO {

 private SQLiteDatabase sdb;

    public CustomDAO(SQLiteDatabase sdb) {
        this.sdb = sdb;
    }

    public  long save(CustomObject co)
    {
        ContentValues cv=new ContentValues();
        cv.put(CustomObjectTable._note,co.getNote());
        cv.put(CustomObjectTable._priority,co.getPriority());
        cv.put(CustomObjectTable._status,"0");
        cv.put(CustomObjectTable._update_intime, String.valueOf(System.currentTimeMillis()));

        return sdb.insert(CustomObjectTable.TABLENAME,null,cv);

    }

   public boolean Update(CustomObject co)
   {

       ContentValues cv=new ContentValues();
       cv.put(CustomObjectTable._update_intime,co.getUpdate_time());
       cv.put(CustomObjectTable._status,co.getStatus());
       cv.put(CustomObjectTable._priority,co.getPriority());


return sdb.update(CustomObjectTable.TABLENAME,cv,CustomObjectTable._id+"=?",new String[]{co.get_id()+" "})>0;

   }

    public  boolean Delete(CustomObject co)
    {
        return sdb.delete(CustomObjectTable.TABLENAME,CustomObjectTable._id+"=?",new String[]{co.get_id()+" "})>0;


    }

    public  CustomObject get(long id)      // primary key
    {  CustomObject co=null;

        Cursor c= sdb.query(true,CustomObjectTable.TABLENAME,new String[]{CustomObjectTable._id,CustomObjectTable._note,CustomObjectTable._priority,CustomObjectTable._status,CustomObjectTable._update_intime},CustomObjectTable._id+" =?",
                new String[]{id+" "},null,null,null,null,null);

if(c!=null&&c.moveToFirst())
{
co=buildfromcursor(c);
if(!c.isClosed())
{
    c.close();
}

}

        return co;
    }


    private CustomObject buildfromcursor(Cursor c)
        {
     CustomObject co=null;

            if(c!=null)
            {
                co=new CustomObject();
                co.set_id(c.getLong(0));
                co.setNote(c.getString(1));
                co.setPriority(c.getString(2));
                co.setStatus(c.getString(3));
                co.setUpdate_time(c.getString(4));


            }

            return co;

    }

    public List<CustomObject> getall()
    {

        List<CustomObject> colist=new ArrayList<CustomObject>();
        Cursor c= sdb.query(CustomObjectTable.TABLENAME,new String[]{CustomObjectTable._id,CustomObjectTable._note,
                CustomObjectTable._priority,CustomObjectTable._status,CustomObjectTable._update_intime},null,null,null,null,null);
        if(c!=null && c.moveToFirst())
        {
            do {
                CustomObject co=buildfromcursor(c);
                if(co!=null)
                {
                    colist.add(co);
                }
            }while(c.moveToNext());
            if(!c.isClosed())
            {
                c.close();
            }
        }

        return colist;
    }

    public List<CustomObject> getallbytime()
    {

        List<CustomObject> colist=new ArrayList<CustomObject>();
        Cursor c= sdb.query(CustomObjectTable.TABLENAME,new String[]{CustomObjectTable._id,CustomObjectTable._note,
                CustomObjectTable._priority,CustomObjectTable._status,CustomObjectTable._update_intime},null,null,null,null,CustomObjectTable._update_intime);
        if(c!=null && c.moveToFirst())
        {
            do {
                CustomObject co=buildfromcursor(c);
                if(co!=null)
                {
                    colist.add(co);
                }
            }while(c.moveToNext());
            if(!c.isClosed())
            {
                c.close();
            }
        }

        return colist;
    }

    public List<CustomObject> getallbypriority()
    {

        List<CustomObject> colist=new ArrayList<CustomObject>();
        Cursor c= sdb.query(CustomObjectTable.TABLENAME,new String[]{CustomObjectTable._id,CustomObjectTable._note,
                CustomObjectTable._priority,CustomObjectTable._status,CustomObjectTable._update_intime},null,null,null,null,CustomObjectTable._priority);
        if(c!=null && c.moveToFirst())
        {
            do {
                CustomObject co=buildfromcursor(c);
                if(co!=null)
                {
                    colist.add(co);
                }
            }while(c.moveToNext());
            if(!c.isClosed())
            {
                c.close();
            }
        }

        return colist;
    }

    public List<CustomObject> getallbypending()
    {

        List<CustomObject> colist=new ArrayList<CustomObject>();
        Cursor c= sdb.query(CustomObjectTable.TABLENAME,new String[]{CustomObjectTable._id,CustomObjectTable._note,CustomObjectTable._priority,CustomObjectTable._status,CustomObjectTable._update_intime},CustomObjectTable._status+" =?",new String[]{"0" },null,null,null,null);
        if(c!=null && c.moveToFirst())
        {
            do {
                CustomObject co=buildfromcursor(c);
                if(co!=null)
                {
                    colist.add(co);
                }
            }while(c.moveToNext());
            if(!c.isClosed())
            {
                c.close();
            }
        }

        return colist;
    }

    public List<CustomObject> getallbycompleted()
    {

        List<CustomObject> colist=new ArrayList<CustomObject>();
        Cursor c= sdb.query(CustomObjectTable.TABLENAME,new String[]{CustomObjectTable._id,CustomObjectTable._note,CustomObjectTable._priority,CustomObjectTable._status,CustomObjectTable._update_intime},CustomObjectTable._status+" =?",new String[]{"1" },null,null,null,null);
        if(c!=null && c.moveToFirst())
        {
            do {
                CustomObject co=buildfromcursor(c);
                if(co!=null)
                {
                    colist.add(co);
                }
            }while(c.moveToNext());
            if(!c.isClosed())
            {
                c.close();
            }
        }

        return colist;
    }





}
