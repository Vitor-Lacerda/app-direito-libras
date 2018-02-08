package com.domain.vitor.estudos1;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import com.google.android.youtube.player.YouTubePlayerView;

public class MainActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {
    public static final String YOUTUBE_API_KEY = "AIzaSyDaUat1a90HaOhWdj4MzJ2Qz4Q9f7BPlD8";

    private YouTubePlayerView ypv;
    private YouTubePlayer player;

    private GridView myGrid;
    private VideoThumbAdapter myAdapter;

    private String videoCode = "";
    private boolean isInitialized = false;

    AnimationDrawable anim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       /* ImageView teste = (ImageView) findViewById(R.id.teste_anim);
        teste.setImageResource(R.drawable.animation);
        anim = (AnimationDrawable)teste.getDrawable(); */

        myGrid = (GridView) findViewById(R.id.grid_video_view);
        myAdapter = new VideoThumbAdapter(this.getBaseContext());
        myGrid.setAdapter(myAdapter);

        myGrid.setOnItemClickListener(new GridView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                FrameLayout videoFrame = (FrameLayout) findViewById(R.id.video_frame);
                videoFrame.setVisibility(View.VISIBLE);

                TextView txt = (TextView)findViewById(R.id.texto_do_video);
                txt.setText(myAdapter.getItem(position).getTextoVideo());

                ypv = (YouTubePlayerView)findViewById(R.id.youtube_view);
                videoCode = myAdapter.getItem(position).getCodigoYoutube();
                if(!isInitialized) {
                    ypv.initialize(YOUTUBE_API_KEY, MainActivity.this);
                }
                else{
                    player.loadVideo(videoCode);
                }
            }
        });

        EditText searchBar = (EditText)findViewById(R.id.grid_search_bar);
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                myAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        SetButtons();



    }

    private void SetButtons() {
        final LinearLayout filterLayout = (LinearLayout)findViewById(R.id.filter_button_layout);
        ImageButton abrirfiltros = (ImageButton) findViewById(R.id.btn_abrir_filtros);
        abrirfiltros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(filterLayout.getVisibility() == View.GONE){
                    filterLayout.setVisibility(View.VISIBLE);
                }
                else{
                    filterLayout.setVisibility(View.GONE);
                }
            }
        });

        ImageButton filtro_aulas = (ImageButton) findViewById(R.id.btn_filtro_aulas);
        filtro_aulas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myAdapter.getFilter().filter("aula");
                filterLayout.setVisibility(View.GONE);

            }
        });

        ImageButton filtro_direito = (ImageButton) findViewById(R.id.btn_filtro_direito);
        filtro_direito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myAdapter.getFilter().filter("direito");
                filterLayout.setVisibility(View.GONE);
            }
        });

        ImageButton filtro_processo = (ImageButton) findViewById(R.id.btn_filtro_processo);
        filtro_processo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myAdapter.getFilter().filter("processo");
                filterLayout.setVisibility(View.GONE);
            }
        });

        ImageButton filtro_noticias = (ImageButton) findViewById(R.id.btn_filtro_noticias);
        filtro_noticias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myAdapter.getFilter().filter("noticia");
                filterLayout.setVisibility(View.GONE);
            }
        });




    }

    /*
    @Override
    public void onWindowFocusChanged (boolean hasFocus){
        super.onWindowFocusChanged(hasFocus);
        anim.start();

    }
    */





    @Override
    public void onBackPressed(){
        FrameLayout videoFrame = (FrameLayout) findViewById(R.id.video_frame);
        if(videoFrame.getVisibility() == View.VISIBLE){
            videoFrame.setVisibility(View.GONE);
            if(isInitialized) {
                player.pause();
            }
        }
        else {
            super.onBackPressed();
        }
    }

    @Override
    public void onInitializationSuccess(Provider provider, YouTubePlayer youTubePlayer, boolean b) {
       player = youTubePlayer;
        player.loadVideo(videoCode);
        isInitialized = true;
    }

    @Override
    public void onInitializationFailure(Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

    }
}
