package com.domain.vitor.estudos1;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.HashMap;


public class EncounterDifficultyFragment extends Fragment {



    private HashMap<Integer, Integer[]> xpThresholds;
    private HashMap<Integer, Float> multipliers;
    private HashMap<String,Integer> xp_by_cr;


    private int[] difficultyThresholds = new int[4];
    private int[] playersValues = new int[5];
    private int[] levelsValues = new int[5];
    private int[] enemiesValues = new int[5];
    private int[] crValues = new int[5];

    int levelsCount, CRCount;

    LinearLayout mainLayout;



    public EncounterDifficultyFragment() {
        // Required empty public constructor
        xpThresholds = new HashMap<>();
        setxpThresholds();
        multipliers = new HashMap<>();
        setMultipliers();
        xp_by_cr = new HashMap<>();
        setxpByCr();
        levelsCount = 1;
        CRCount = 1;


    }


    public static EncounterDifficultyFragment newInstance() {
        EncounterDifficultyFragment fragment = new EncounterDifficultyFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        levelsCount = CRCount = 1;


        // Inflate the layout for this fragment
        mainLayout = (LinearLayout)inflater.inflate(R.layout.fragment_encounter_difficulty, container, false);

        /*Configura a parte do Grupo*/

        setPlayerSpinners(mainLayout,true);

        Button add_level_button = (Button)mainLayout.findViewById(R.id.add_level_button);

        add_level_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(levelsCount<5) {
                    LayoutInflater mInflater = LayoutInflater.from(v.getContext());
                    LinearLayout dropdowns = (LinearLayout) mInflater.inflate(R.layout.encounter_info_dropdowns, (ViewGroup) v.getRootView(), false);
                    LinearLayout drops_layout = (LinearLayout) mainLayout.findViewById(R.id.player_dropdowns_container);
                    setPlayerSpinners(dropdowns,false);
                    CalculateDifficultyThresholds();
                    CalculateEncounterDifficulty();
                    drops_layout.addView(dropdowns);
                    levelsCount++;
                    if(levelsCount>=5){
                        v.setVisibility(View.GONE);
                    }
                }

            }
        });


        /*Configura a parte dos inimigos*/
        final LinearLayout monsterLayouts = (LinearLayout)mainLayout.findViewById(R.id.enemy_dropdowns_container);

        setMonsterSpinners(monsterLayouts,true);

        Button add_cr_button = (Button)mainLayout.findViewById(R.id.add_cr_button);

        add_cr_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CRCount<5) {
                    LayoutInflater mInflater = LayoutInflater.from(v.getContext());
                    LinearLayout dropdowns = (LinearLayout) mInflater.inflate(R.layout.encounter_info_dropdowns, (ViewGroup) v.getRootView(), false);
                   // LinearLayout drops_layout = (LinearLayout) mainLayout.findViewById(R.id.enemy_dropdowns_container);
                    setMonsterSpinners(dropdowns,false);
                    CalculateDifficultyThresholds();
                    CalculateEncounterDifficulty();
                    monsterLayouts.addView(dropdowns);
                    CRCount++;
                    if(CRCount>=5){
                        v.setVisibility(View.GONE);
                    }
                }

            }
        });








        /*Configura as informacoes embaixo*/
        CalculateDifficultyThresholds();
        CalculateEncounterDifficulty();





        return mainLayout;
    }

    private void setPlayerSpinners(final View layout,  boolean first){
        Spinner count_spinner = (Spinner)layout.findViewById(R.id.count_dropdown);
        Spinner level_spinner = (Spinner)layout.findViewById(R.id.level_dropdown);

        ArrayAdapter<CharSequence> playerDropdownAdapter = ArrayAdapter.createFromResource(layout.getContext(), R.array.player_dropdown,android.R.layout.simple_spinner_item);
        playerDropdownAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        count_spinner.setAdapter(playerDropdownAdapter);
        level_spinner.setAdapter(playerDropdownAdapter);

        AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                CalculateDifficultyThresholds();
                CalculateEncounterDifficulty();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };

        count_spinner.setOnItemSelectedListener(itemSelectedListener);
        level_spinner.setOnItemSelectedListener(itemSelectedListener);





        //Botao pra tirar a linha
        ImageButton remove_button = (ImageButton)layout.findViewById(R.id.remove_level);
        if(!first) {
            remove_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((ViewGroup) layout.getParent()).removeView(layout);
                    CalculateDifficultyThresholds();
                    CalculateEncounterDifficulty();
                    levelsCount--;
                    if(levelsCount<5){
                        mainLayout.findViewById(R.id.add_level_button).setVisibility(View.VISIBLE);
                    }
                }
            });
        }
        else{
            remove_button.setVisibility(View.GONE);
        }


        //CalculateDifficultyThresholds();


    }

    private void setMonsterSpinners(final View layout, boolean first){
        ((TextView)layout.findViewById(R.id.player_spinner_text)).setText(getString(R.string.enemies));
        ((TextView)layout.findViewById(R.id.level_spinner_text)).setText(getString(R.string.cr));

        Spinner count_spinner = (Spinner)layout.findViewById(R.id.count_dropdown);
        Spinner level_spinner = (Spinner)layout.findViewById(R.id.level_dropdown);

        ArrayAdapter<CharSequence> monsterDropdownAdapter = ArrayAdapter.createFromResource(layout.getContext(), R.array.enemy_dropdown,android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> crDropdownAdapter = ArrayAdapter.createFromResource(layout.getContext(), R.array.cr_dropdown,android.R.layout.simple_spinner_item);
        monsterDropdownAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        count_spinner.setAdapter(monsterDropdownAdapter);
        level_spinner.setAdapter(crDropdownAdapter);

        AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                CalculateEncounterDifficulty();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };

        count_spinner.setOnItemSelectedListener(itemSelectedListener);
        level_spinner.setOnItemSelectedListener(itemSelectedListener);





        //Botao pra tirar a linha
        ImageButton remove_button = (ImageButton)layout.findViewById(R.id.remove_level);
        if(!first) {
            remove_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((ViewGroup) layout.getParent()).removeView(layout);
                    CalculateEncounterDifficulty();
                    CRCount--;
                    if(CRCount<5){
                        mainLayout.findViewById(R.id.add_cr_button).setVisibility(View.VISIBLE);
                    }
                }
            });
        }
        else{
            remove_button.setVisibility(View.GONE);
        }


        //CalculateEncounterDifficulty();
    }

    private void CalculateDifficultyThresholds(){
        LinearLayout drops_layout = (LinearLayout)mainLayout.findViewById(R.id.player_dropdowns_container);

        for(int c = 0;c<4;c++){
            difficultyThresholds[c] = 0;
        }

        for(int i = 0; i<drops_layout.getChildCount();i++) {
            Spinner level = (Spinner)drops_layout.getChildAt(i).findViewById(R.id.level_dropdown);
            Spinner count = (Spinner)drops_layout.getChildAt(i).findViewById(R.id.count_dropdown);

            int levelInt = level.getSelectedItemPosition()+1;
            int countInt = count.getSelectedItemPosition()+1;

            difficultyThresholds[0] += xpThresholds.get(levelInt)[0] * countInt;
            difficultyThresholds[1] += xpThresholds.get(levelInt)[1] * countInt;
            difficultyThresholds[2] += xpThresholds.get(levelInt)[2] * countInt;
            difficultyThresholds[3] += xpThresholds.get(levelInt)[3] * countInt;

        }


        ((TextView)mainLayout.findViewById(R.id.easy_number)).setText(Integer.toString(difficultyThresholds[0]));
        ((TextView)mainLayout.findViewById(R.id.medium_number)).setText(Integer.toString(difficultyThresholds[1]));
        ((TextView)mainLayout.findViewById(R.id.hard_number)).setText(Integer.toString(difficultyThresholds[2]));
        ((TextView)mainLayout.findViewById(R.id.deadly_number)).setText(Integer.toString(difficultyThresholds[3]));


    }

    private void CalculateEncounterDifficulty(){
        int xp = 0;
        int xpA = 0;
        int totalCount = 0;

        LinearLayout drops_layout = (LinearLayout)mainLayout.findViewById(R.id.enemy_dropdowns_container);

        for(int i = 0; i<drops_layout.getChildCount();i++) {
            Spinner level = (Spinner)drops_layout.getChildAt(i).findViewById(R.id.level_dropdown);
            Spinner count = (Spinner)drops_layout.getChildAt(i).findViewById(R.id.count_dropdown);


            int countInt = count.getSelectedItemPosition()+1;

            xp += xp_by_cr.get(level.getSelectedItem().toString())*countInt;
            totalCount+=countInt;

        }

        if(totalCount >15) totalCount = 15;

        xpA = Math.round(xp * multipliers.get(totalCount));


        ((TextView)mainLayout.findViewById(R.id.xp_value_text)).setText(Integer.toString(xp));
        ((TextView)mainLayout.findViewById(R.id.xpa_value_text)).setText(Integer.toString(xpA));

        TextView difText = (TextView)mainLayout.findViewById(R.id.dif_text);

        if(xpA >= difficultyThresholds[3]){
            difText.setText(getString(R.string.deadly));
        }
        else if(xpA >= difficultyThresholds[2]){
            difText.setText(getString(R.string.hard));
        }
        else if(xpA >= difficultyThresholds[1]){
            difText.setText(getString(R.string.medium));
        }
        else if(xpA >= difficultyThresholds[0]){
            difText.setText(getString(R.string.easy));
        }
        else {
            difText.setText(getString(R.string.trivial));
        }





    }


/*
    @Override
    public void onPause(){
        super.onPause();
        /*
        LinearLayout player_drops_layout = (LinearLayout)mainLayout.findViewById(R.id.player_dropdowns_container);
        for(int i = 0; i<player_drops_layout.getChildCount();i++) {
            Spinner level = (Spinner)player_drops_layout.getChildAt(i).findViewById(R.id.level_dropdown);
            Spinner count = (Spinner)player_drops_layout.getChildAt(i).findViewById(R.id.count_dropdown);

            levelsValues[i] = level.getSelectedItemPosition();
            playersValues[i] = count.getSelectedItemPosition();

        }

        LinearLayout enemy_drops_layout = (LinearLayout)mainLayout.findViewById(R.id.enemy_dropdowns_container);
        for(int i = 0; i<enemy_drops_layout.getChildCount();i++) {
            Spinner level = (Spinner)enemy_drops_layout.getChildAt(i).findViewById(R.id.level_dropdown);
            Spinner count = (Spinner)enemy_drops_layout.getChildAt(i).findViewById(R.id.count_dropdown);

            crValues[i] = level.getSelectedItemPosition();
            enemiesValues[i] = count.getSelectedItemPosition();


        }

    }*/



    //Metodos de configuracao

    private void setxpThresholds(){
        xpThresholds.put(1,new Integer[]{25,50,75,100});
        xpThresholds.put(2,new Integer[]{50,100,150,200});
        xpThresholds.put(3,new Integer[]{75,150,225,400});
        xpThresholds.put(4,new Integer[]{125,250,375,500});
        xpThresholds.put(5,new Integer[]{250,500,750,1100});
        xpThresholds.put(6,new Integer[]{300,600,900,1400});
        xpThresholds.put(7,new Integer[]{350,750,1100,1700});
        xpThresholds.put(8,new Integer[]{450,900,1400,2100});
        xpThresholds.put(9,new Integer[]{550,1100,1600,2400});
        xpThresholds.put(10,new Integer[]{600,1200,1900,2800});
        xpThresholds.put(11,new Integer[]{800,1600,2400,3600});
        xpThresholds.put(12,new Integer[]{1000,2000,3000,4500});
        xpThresholds.put(13,new Integer[]{1100,2200,3400,5100});
        xpThresholds.put(14,new Integer[]{1250,2500,3800,5700});
        xpThresholds.put(15,new Integer[]{1400,2800,4300,6400});
        xpThresholds.put(16,new Integer[]{1600,3200,4800,7200});
        xpThresholds.put(17,new Integer[]{2000,3900,5900,8800});
        xpThresholds.put(18,new Integer[]{2100,4200,6300,9500});
        xpThresholds.put(19,new Integer[]{2400,4900,7300,10900});
        xpThresholds.put(20,new Integer[]{2800,5700,8500,12700});

    }

    private void setMultipliers(){
        multipliers.put(1,1f);
        multipliers.put(2,1.5f);
        multipliers.put(3,2f);
        multipliers.put(4,2f);
        multipliers.put(5,2f);
        multipliers.put(6,2f);
        multipliers.put(7,2.5f);
        multipliers.put(8,2.5f);
        multipliers.put(9,2.5f);
        multipliers.put(10,2.5f);
        multipliers.put(11,3f);
        multipliers.put(12,3f);
        multipliers.put(13,3f);
        multipliers.put(14,3f);
        multipliers.put(15,4f);
    }

    private void setxpByCr(){
        xp_by_cr.put("0",10);
        xp_by_cr.put("1/8",25);
        xp_by_cr.put("1/4",50);
        xp_by_cr.put("1/2",100);
        xp_by_cr.put("1",200);
        xp_by_cr.put("2",450);
        xp_by_cr.put("3",700);
        xp_by_cr.put("4",1100);
        xp_by_cr.put("5",1800);
        xp_by_cr.put("6",2300);
        xp_by_cr.put("7",2900);
        xp_by_cr.put("8",3900);
        xp_by_cr.put("9", 5000);
        xp_by_cr.put("10", 5900);
        xp_by_cr.put("11",7200);
        xp_by_cr.put("12",8400);
        xp_by_cr.put("13",10000);
        xp_by_cr.put("14",11500);
        xp_by_cr.put("15",13000);
        xp_by_cr.put("16",15000);
        xp_by_cr.put("17",18000);
        xp_by_cr.put("18",20000);
        xp_by_cr.put("19",22000);
        xp_by_cr.put("20",25000);
        xp_by_cr.put("21",33000);
        xp_by_cr.put("22",41000);
        xp_by_cr.put("23", 50000);
        xp_by_cr.put("24", 62000);
        xp_by_cr.put("25",75000);
        xp_by_cr.put("26",90000);
        xp_by_cr.put("27",105000);
        xp_by_cr.put("28",120000);
        xp_by_cr.put("29",135000);
        xp_by_cr.put("30",155000);

    }


}
