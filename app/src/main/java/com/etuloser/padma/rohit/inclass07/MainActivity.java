package com.etuloser.padma.rohit.inclass07;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.ocpsoft.prettytime.PrettyTime;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public CustomDatabaseManager dm;
    private DatabaseReference mData;

    Spinner pspinner;
    ListView lv;
    ArrayList<String> spinnerArray = new ArrayList<String>();
    List<CustomObject> notelist=new ArrayList<CustomObject>();
    List<CustomObject> notelistt=new ArrayList<CustomObject>();
EditText edxnote;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        dm = new CustomDatabaseManager(this);

        pspinner=(Spinner)findViewById(R.id.spinner);
        edxnote=(EditText)findViewById(R.id.NoteText);

        lv=(ListView)findViewById(R.id.mainListView);
        spinnerArray.add("Priority");
        spinnerArray.add("High");
        spinnerArray.add("Medium");
        spinnerArray.add("Low");

        ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_dropdown_item,
                spinnerArray);
        pspinner.setAdapter(spinnerArrayAdapter);


       // setview();

        mData = FirebaseDatabase.getInstance().getReference();
        DatabaseReference dref = mData.child("Notes");
        dref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                CustomObject co = dataSnapshot.getValue(CustomObject.class);
                notelist.add(co);
                setview();

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        int id = item.getItemId();

        if (id == R.id.showall) {

            setview();
            //Toast.makeText(this,"Showall",Toast.LENGTH_SHORT).show();
            return true;
        }
        else if(id==R.id.showcompleted)
        {setviewbycomplete();
            return true;
        }
        else if(id==R.id.showpending)
        {
            setviewbypending();
            return true;
        }
        else if(id==R.id.sortbyPriority){
            setviewbypriority();
             return true;

        }
        else if(id==R.id.sortbytime)
        {
            setviewbytime();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }




    public void AddNote(View v)
    {
        if(edxnote.getText().length()>0 && pspinner.getSelectedItemId()>0) {

            CustomObject co=new CustomObject();
            co.setNote(edxnote.getText().toString().trim());
            co.setPriority(String.valueOf(pspinner.getSelectedItemId()));
           // PrettyTime p = new PrettyTime();
            co.setUpdate_time(String.valueOf(System.currentTimeMillis()));
            co.setStatus("0");
            mData = FirebaseDatabase.getInstance().getReference();
            DatabaseReference dref = mData.child("Notes").push();
            String key = dref.getKey();
            co.setUid(key);
            dref.setValue(co);

            //dm.save(co);
            //setview();
            Toast.makeText(this, "Successfully added", Toast.LENGTH_SHORT).show();
            edxnote.setText("");
            pspinner.setSelection(0);
            }
        else
        {
            Toast.makeText(this, "Please proper enter note / select a priority", Toast.LENGTH_SHORT).show();

        }

    }



    public void setviewbypriority()
    {
        //notelist=(ArrayList<CustomObject>) dm.getallbypriority();
        Collections.sort(notelist,CustomObject.comparatorPrior);
        CustomAdapter ca=new CustomAdapter(this,R.layout.childlayout,notelist);
        ca.setNotifyOnChange(true);
        lv.setAdapter(ca);
    }


    public void setviewbytime()
    {
       // notelist=(ArrayList<CustomObject>) dm.getallbytime();
        Collections.sort(notelist,CustomObject.comparatortime);
        CustomAdapter ca=new CustomAdapter(this,R.layout.childlayout,notelist);
        ca.setNotifyOnChange(true);
        lv.setAdapter(ca);
    }


    public void setviewbypending()
    {
       // notelist=(ArrayList<CustomObject>) dm.getallbypending();
        List<CustomObject> objectList = new ArrayList<>();
        for (CustomObject co : notelist){

            if (co.getStatus().equals("0")){
                objectList.add(co);

            }

        }
        CustomAdapter ca=new CustomAdapter(this,R.layout.childlayout,objectList);
        ca.setNotifyOnChange(true);
        lv.setAdapter(ca);
    }

    public void setviewbycomplete()
    {
        //notelist=(ArrayList<CustomObject>) dm.getallbycompleted();
        List<CustomObject> objectList = new ArrayList<>();
        for (CustomObject co : notelist){

            if (co.getStatus().equals("1")){
                objectList.add(co);

            }

        }

        //Collections.sort(objectList, CustomObject.comparatorPrior);
        CustomAdapter ca=new CustomAdapter(this,R.layout.childlayout,objectList);
        ca.setNotifyOnChange(true);
        lv.setAdapter(ca);
    }



    public void setview()
    {
        //notelist=(ArrayList<CustomObject>) dm.getAll();
        notelistt.clear();
        notelistt.addAll(notelist);
        if (notelistt.size()>0){
            CustomAdapter ca=new CustomAdapter(this,R.layout.childlayout,notelistt);
            ca.setNotifyOnChange(true);
            lv.setAdapter(ca);
        }

    }

    public void updateStatus(CustomObject g, int pos){

        if (notelistt.contains(g)){

           // mData = FirebaseDatabase.getInstance().getReference();
            DatabaseReference dref = mData.child("Notes").child(g.getUid());
            notelist.set(pos,g);
            dref.setValue(g);
            setview();

        }


    }

    public void deletenote(CustomObject g, int pos){

        if (notelistt.contains(g)){

            // mData = FirebaseDatabase.getInstance().getReference();
            DatabaseReference dref = mData.child("Notes").child(g.getUid());

            notelist.set(pos,g);
            notelist.remove(g);
            dref.removeValue();

            setview();

        }


    }

}
