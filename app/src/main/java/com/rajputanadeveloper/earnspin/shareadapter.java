package com.rajputanadeveloper.earnspin;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by user on 15-09-2017.
 */

public class shareadapter extends ArrayAdapter<statusword> {
private int mColorResourceid;
public shareadapter(Activity context, ArrayList<statusword> words, int ColorResourceid){
        super(context,0,words );
        mColorResourceid=ColorResourceid;
        }
@NonNull
@Override
public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listitemView=convertView;
        if (listitemView==null) {
        listitemView = LayoutInflater.from(getContext()).inflate(R.layout.item_layout, parent, false);
        }
        statusword currentWord=getItem(position);
String ss=currentWord.getdesc();
       Long l= Long.parseLong(currentWord.gettime());
        String s = new SimpleDateFormat("MMM dd").format(l);

        TextView spanishTextView=(TextView) listitemView.findViewById(R.id.date);
        spanishTextView.setText(s);
        TextView deafaultTextView=(TextView) listitemView.findViewById(R.id.ccccc);
        deafaultTextView.setText(currentWord.getcoin());

    TextView descTextView=(TextView) listitemView.findViewById(R.id.desss);
   if(ss.equals(null)) descTextView.setText("spin");
    else
        descTextView.setText(ss);
//        //setting theme color
//        View textContainer= listitemView.findViewById(R.id.text_container);
//        int color= ContextCompat.getColor(getContext(),mColorResourceid);
//        textContainer.setBackgroundColor(color);
        return listitemView;
        }
        }
