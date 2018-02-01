package com.domain.vitor.estudos1;

import android.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

public class OldMain extends AppCompatActivity {


    private String[] screensNames;
    private DrawerLayout mDrawerLayout;
    private ListView drawerList;


    private RandomTreasureFragment randomTreasureFragment;
    private ItemListFragment magicItemListFragment;
    private EncounterDifficultyFragment encounterDifficultyFragment;
    private QuickReferenceFragment quickReferenceFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.old_activity_main);




        screensNames = getResources().getStringArray(R.array.nav_string_array);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        drawerList = (ListView)findViewById(R.id.nav_drawer);
        drawerList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, screensNames));

        /*On Click Listener dos items de navegacao*/
        drawerList.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), screensNames[position], Toast.LENGTH_SHORT).show();

                if(position == 0) {
                    if(randomTreasureFragment == null) {
                        randomTreasureFragment = RandomTreasureFragment.newInstance();
                    }
                    ChangeFragment(randomTreasureFragment);
                }
                //Lista de Itens magicos
                if(position == 1){
                    if(magicItemListFragment == null) {
                       magicItemListFragment = ItemListFragment.newInstance();
                    }
                    ChangeFragment(magicItemListFragment);
                }
                if(position == 2){
                    if(quickReferenceFragment == null) {
                        quickReferenceFragment = QuickReferenceFragment.newInstance();
                    }
                    ChangeFragment(quickReferenceFragment);

                }
                //Dificuldade de encontros
                if(position == 3){

                    if(encounterDifficultyFragment == null) {
                        encounterDifficultyFragment = EncounterDifficultyFragment.newInstance();
                    }
                    ChangeFragment(encounterDifficultyFragment);
                }

                mDrawerLayout.closeDrawer(Gravity.LEFT);
            }
        });

        /*Listeners de quando a drawer abre/fecha*/
        mDrawerLayout.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {
                ((ImageButton)findViewById(R.id.open_nav_button)).setImageDrawable(getResources().getDrawable(R.drawable.closemenutab));
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                ((ImageButton)findViewById(R.id.open_nav_button)).setImageDrawable(getResources().getDrawable(R.drawable.ic_action_name));
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });

    }

    public void onClickSideMenu(View v){
        if(mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
            mDrawerLayout.closeDrawer(Gravity.LEFT, true);
        }else {
            mDrawerLayout.openDrawer(Gravity.LEFT, true);
        }
    }

    private void ChangeFragment(android.app.Fragment target){
        android.app.FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if(target.isAdded()){
            transaction.show(target);
        }else{
            transaction.replace(R.id.main_content_frame,target);
        }
        transaction.commit();
    }




}
