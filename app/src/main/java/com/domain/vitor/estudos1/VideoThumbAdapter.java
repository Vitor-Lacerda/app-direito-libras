package com.domain.vitor.estudos1;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Godzilla Filmes on 01-Feb-18.
 */

public class VideoThumbAdapter extends BaseAdapter {




    private Context myContext;

    private Integer[] idsImagemVideos = {R.drawable.thumb_tvjustica, R.drawable.thumb_deborahdias};
    private String[] nomesVideos = {"Direito Libras na TV Justiça", "Direito Libras | Deborah Dias"};
    private String[] codigosVideos = {"SG8FlvOXM6w","1iO4roCrwjk"};


    public VideoThumbAdapter(Context c){
        myContext = c;
    }
    @Override
    public int getCount() {
        return idsImagemVideos.length;
    }
    @Override
    public String getItem(int position) {
        return codigosVideos[position];
    }
    @Override
    public long getItemId(int position) {
        return 0;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inf = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inf.inflate(R.layout.video_grid_layout,parent,false);

        ImageView btn = (ImageView)convertView.findViewById(R.id.video_grid_img);
        TextView txt = (TextView) convertView.findViewById(R.id.video_grid_txt);

        btn.setImageResource(idsImagemVideos[position]);
        txt.setText(nomesVideos[position]);

        return convertView;
    }





    }
