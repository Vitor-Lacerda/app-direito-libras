package com.domain.vitor.estudos1;


import android.opengl.Visibility;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.HashMap;


public class QuickReferenceFragment extends Fragment {


    private View mainLayout;

    private HashMap<Integer, String[]> buttonText;

    public QuickReferenceFragment() {
        // Required empty public constructor

    }


    // TODO: Rename and change types and number of parameters
    public static QuickReferenceFragment newInstance() {
        QuickReferenceFragment fragment = new QuickReferenceFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SetQuickReferences();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        mainLayout = inflater.inflate(R.layout.fragment_quick_reference, container, false);

        SetButtons();




        return mainLayout;
    }

    private void SetButtons(){
        Button backBtn = (Button)mainLayout.findViewById(R.id.quick_ref_back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetInfoVisibility(View.GONE);
            }
        });

        View.OnClickListener btnListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = buttonText.get(v.getId())[0];
                String info = buttonText.get(v.getId())[1];
                PopulateTexts(name, info);
            }
        };

        ImageButton btn = (ImageButton)mainLayout.findViewById(R.id.long_jump_btn);
        btn.setOnClickListener(btnListener);
        btn = (ImageButton)mainLayout.findViewById(R.id.high_jump_btn);
        btn.setOnClickListener(btnListener);
        btn = (ImageButton)mainLayout.findViewById(R.id.fall_dmg_btn);
        btn.setOnClickListener(btnListener);
        btn = (ImageButton)mainLayout.findViewById(R.id.grapple_btn);
        btn.setOnClickListener(btnListener);
        btn = (ImageButton)mainLayout.findViewById(R.id.shove_btn);
        btn.setOnClickListener(btnListener);
        btn = (ImageButton)mainLayout.findViewById(R.id.disarm_btn);
        btn.setOnClickListener(btnListener);
        btn = (ImageButton)mainLayout.findViewById(R.id.climb_btn);
        btn.setOnClickListener(btnListener);
        btn = (ImageButton)mainLayout.findViewById(R.id.overrun_btn);
        btn.setOnClickListener(btnListener);
        btn = (ImageButton)mainLayout.findViewById(R.id.shove_aside_btn);
        btn.setOnClickListener(btnListener);
        btn = (ImageButton)mainLayout.findViewById(R.id.tumble_btn);
        btn.setOnClickListener(btnListener);
        btn = (ImageButton)mainLayout.findViewById(R.id.mounted_btn);
        btn.setOnClickListener(btnListener);
        btn = (ImageButton)mainLayout.findViewById(R.id.temp_hp_btn);
        btn.setOnClickListener(btnListener);
        btn = (ImageButton)mainLayout.findViewById(R.id.improvised_btn);
        btn.setOnClickListener(btnListener);
        btn = (ImageButton)mainLayout.findViewById(R.id.uw_combat_btn);
        btn.setOnClickListener(btnListener);


    }

    private void PopulateTexts(String name, String info){
        ((TextView)mainLayout.findViewById(R.id.quick_ref_title)).setText(name);
        ((TextView)mainLayout.findViewById(R.id.quick_ref_info)).setText(info);

        SetInfoVisibility(View.VISIBLE);
    }

    private void SetInfoVisibility(int v){
        mainLayout.findViewById(R.id.info_frame).setVisibility(v);
    }

    private void SetQuickReferences() {
        buttonText = new HashMap<>();
        buttonText.put(R.id.long_jump_btn, new String[]{getString(R.string.ljump), getString(R.string.ljump_info)});
        buttonText.put(R.id.high_jump_btn, new String[]{getString(R.string.hjump), getString(R.string.hjump_info)});
        buttonText.put(R.id.fall_dmg_btn, new String[]{getString(R.string.falling), getString(R.string.fall_info)});
        buttonText.put(R.id.grapple_btn, new String[]{getString(R.string.grapple), getString(R.string.grapple_info)});
        buttonText.put(R.id.shove_btn, new String[]{getString(R.string.shove), getString(R.string.shove_info)});
        buttonText.put(R.id.disarm_btn, new String[]{getString(R.string.disarm), getString(R.string.disarm_info)});
        buttonText.put(R.id.climb_btn, new String[]{getString(R.string.climb_bigger_creature), getString(R.string.climb_creature_info)});
        buttonText.put(R.id.overrun_btn, new String[]{getString(R.string.overrun), getString(R.string.overrun_info)});
        buttonText.put(R.id.shove_aside_btn, new String[]{getString(R.string.shove_aside), getString(R.string.shove_aside_info)});
        buttonText.put(R.id.tumble_btn, new String[]{getString(R.string.tumble), getString(R.string.tumble_info)});
        buttonText.put(R.id.uw_combat_btn, new String[]{getString(R.string.underwater_combat), getString(R.string.under_combat_info)});
        buttonText.put(R.id.mounted_btn, new String[]{getString(R.string.mounted), getString(R.string.mounted_info)});
        buttonText.put(R.id.temp_hp_btn, new String[]{getString(R.string.temp_hp), getString(R.string.temp_hp_info)});
        buttonText.put(R.id.improvised_btn, new String[]{getString(R.string.improvised_weapon), getString(R.string.improvised_weapon_info)});


    }

}
