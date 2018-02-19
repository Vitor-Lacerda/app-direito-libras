package com.domain.vitor.estudos1;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Godzilla Filmes on 01-Feb-18.
 */

public class VideoThumbAdapter extends BaseAdapter implements Filterable {




    private Context myContext;
    private ArrayList<VideoThumb> listaVideos, listaOriginal;
    private VideoFilter filter;

    /*
    private Integer[] idsImagemVideos = {R.drawable.thumb_tvjustica, R.drawable.thumb_deborahdias};
    private String[] nomesVideos = {"Direito Libras na TV Justiça", "Direito Libras | Deborah Dias"};
    private String[] codigosVideos = {"SG8FlvOXM6w","1iO4roCrwjk"};
    */


    public VideoThumbAdapter(Context c){

        myContext = c;
        listaVideos = fazListaDoJson();
        listaOriginal = listaVideos;


    }
    @Override
    public int getCount() {
        return listaVideos.size();
    }
    @Override
    public VideoThumb getItem(int position) {
        return listaVideos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public ArrayList<VideoThumb> getDataSource(){
        return listaVideos;
    }


    public void setDataSource(ArrayList<VideoThumb> dataSource){
        listaVideos = dataSource;
        this.notifyDataSetChanged();
    }

    public ArrayList<VideoThumb> getListaOriginal(){
        return listaOriginal;
    }

    @Override
    public Filter getFilter() {
        if(filter == null){
            filter = new VideoFilter(this);
        }
        return  filter;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inf = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inf.inflate(R.layout.video_grid_layout,parent,false);

        ImageView btn = (ImageView)convertView.findViewById(R.id.video_grid_img);
        TextView txt = (TextView) convertView.findViewById(R.id.video_grid_txt);

        btn.setImageResource(listaVideos.get(position).getIdDrawableThumb());
        txt.setText(listaVideos.get(position).getNomeVideo());

        return convertView;
    }

    String loadJSONfromAsset()
    {
        String json = null;
        try{
            InputStream is = myContext.getAssets().open("videos.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return json;
    }

    private ArrayList<VideoThumb> fazListaDoJson(){
        ArrayList<VideoThumb> retorno = new ArrayList<VideoThumb>();
        try{
            String json = loadJSONfromAsset();
            JSONObject obj = new JSONObject(json);
            JSONArray videos = obj.getJSONArray("videos");
            for(int i = 0; i<videos.length();i++){
                JSONObject proximoVideo = videos.getJSONObject(i);
                String nome = proximoVideo.getString("nome");
                String nomeThumbnail = proximoVideo.getString("imagemThumb");
                Integer idThumbnail = myContext.getResources().getIdentifier(nomeThumbnail, "drawable", myContext.getPackageName());
                String codigo = proximoVideo.getString("codigo");
                String texto = proximoVideo.getString("texto");

                JSONArray palavras_chave = proximoVideo.getJSONArray("palavras_chave");
                String[] vetor_palavras_chave = new String[palavras_chave.length()];
                for(int k = 0;k<palavras_chave.length();k++){
                    JSONObject elemento = palavras_chave.getJSONObject(k);
                    vetor_palavras_chave[k] = elemento.getString("palavra");
                }

                VideoThumb vt = new VideoThumb(nome,idThumbnail, codigo, texto, vetor_palavras_chave);
                retorno.add(vt);


            }

        }
        catch (JSONException e) {
            e.printStackTrace();
            return retorno;
        }

        return retorno;




    }

    }


    class VideoFilter extends Filter {

        VideoThumbAdapter myAdapter;

        public VideoFilter(VideoThumbAdapter adapter){
            myAdapter = adapter;
        }


        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults retorno = new FilterResults();

            if(constraint.length() == 0){
                retorno.values = myAdapter.getListaOriginal();
                retorno.count = myAdapter.getListaOriginal().size();
            }
            else {
                ArrayList<VideoThumb> filtrados = new ArrayList<VideoThumb>();
                for(VideoThumb vt:myAdapter.getListaOriginal()){
                    if(vt.getNomeVideo().toLowerCase().contains(constraint.toString().toLowerCase()) || BuscaPalavrasChave(vt.getPalavrasChave(), constraint.toString())){
                        filtrados.add(vt);

                    }
                }
                retorno.values = filtrados;
                retorno.count = filtrados.size();

            }

            return retorno;
        }

        protected boolean BuscaPalavrasChave(String[] palavrasChave, String constraint){
            for(String s:palavrasChave){
                if(s.toLowerCase().contentEquals(constraint.toLowerCase())){
                    return true;
                }
            }
            return false;

        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            myAdapter.setDataSource((ArrayList<VideoThumb>) results.values);
        }
    }
