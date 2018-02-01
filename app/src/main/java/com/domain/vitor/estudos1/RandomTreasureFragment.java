package com.domain.vitor.estudos1;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.Random;


public class RandomTreasureFragment extends Fragment {




    public RandomTreasureFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static RandomTreasureFragment newInstance() {
        return new RandomTreasureFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_random_treasure, container, false);
    }

}
