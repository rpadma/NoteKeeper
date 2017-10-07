package com.etuloser.padma.rohit.inclass07;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.ocpsoft.prettytime.PrettyTime;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Rohit on 2/27/2017.
 */

public class CustomAdapter extends ArrayAdapter<CustomObject> {
    List<CustomObject> notelist;
    Context mcontext;
    int mres;

    public CustomAdapter(Context context, int resource, List<CustomObject> objects) {
        super(context, resource, objects);
        this.notelist=objects;
        this.mcontext=context;
        this.mres=resource;

    }


    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {


        if(convertView==null)
        {
            LayoutInflater inflater=(LayoutInflater)mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView =inflater.inflate(mres,parent,false);
        }

        final CustomObject g=notelist.get(position);



        TextView tv1=(TextView)convertView.findViewById(R.id.text1);
        TextView tv2=(TextView)convertView.findViewById(R.id.text2);
        TextView tv3=(TextView)convertView.findViewById(R.id.text3);
        CheckBox cbox=(CheckBox)convertView.findViewById(R.id.checkBox);
        final AlertDialog.Builder builder = new AlertDialog.Builder((MainActivity)mcontext);
        tv1.setText(g.getNote());
        tv2.setText(g.getPriority()+" priority");

        if(g.getPriority().equals("1"))
        {
            tv2.setText("High priority");
        }
        else  if(g.getPriority().equals("2"))
        {
            tv2.setText("Medium priority");
        }
        else  if(g.getPriority().equals("3"))
        {
            tv2.setText("Low priority");
        }
        PrettyTime pt=new PrettyTime();
        //tv3.setText(pt.format(new Date(Long.valueOf(g.getUpdate_time()))));
        tv3.setText(pt.format(new Date(Long.valueOf(g.getUpdate_time()))));
        cbox.setChecked(g.getStatus().equals("0")?false:true);
        cbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if ( isChecked ) {

                    g.setStatus("1");

                    ((MainActivity) mcontext).updateStatus(g,position);
                }
                else
                {
                    builder.setTitle(R.string.pendinemsg);
                    builder.setPositiveButton("Yes",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(
                                        DialogInterface dialog,
                                        int whichButton) {

                                    g.setStatus("0");
                                    ((MainActivity) mcontext).updateStatus(g,position);
                                }
                            });

                    builder.setNegativeButton("No",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(
                                        DialogInterface dialog,
                                        int whichButton) {
                                    dialog.cancel();
                                }
                            });

                    builder.create().show();


                }

            }
        });









        convertView.setLongClickable(true);

        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {


                builder.setTitle(R.string.deletemsg);
                builder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(
                                    DialogInterface dialog,
                                    int whichButton) {

                                CustomObject co= notelist.get(position);
                                ((MainActivity) mcontext).deletenote(co,position);

                                notelist.remove(co);
                            }
                        });

                builder.setNegativeButton("No",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(
                                    DialogInterface dialog,
                                    int whichButton) {
                                dialog.cancel();
                            }
                        });

                builder.create().show();


                return true;


            }
        });

        notifyDataSetChanged();

        return convertView;
    }



}
