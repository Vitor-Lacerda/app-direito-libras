package com.domain.vitor.estudos1;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.lang.reflect.Array;
import java.util.ArrayList;

//Essa atividade mostra uma lista de items magicos, um de cada vez, atraves dos Fragments.
//Recebe no intent uma lista de itens e um int que a posicao do primeiro item a ser mostrado.

public class ItemDetailPagerActivity extends FragmentActivity {


    private ViewPager mViewPager;

    private PagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail_pager);

        //Pega a lista e a posicao do intent.
        Bundle b = this.getIntent().getExtras();
        ArrayList<Item> items = new ArrayList<Item>();
        int itemPosition = 0;
        if(b!=null){
            items = b.getParcelableArrayList(getResources().getString(R.string.intent_item_list_key));
            itemPosition = b.getInt(getResources().getString(R.string.intent_item_index_key));
        }

        mViewPager = (ViewPager) findViewById(R.id.item_detail_pager);
        mPagerAdapter = new ItemDetailPagerAdapter(getSupportFragmentManager(), items);
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setCurrentItem(itemPosition, true);


    }
}

class ItemDetailPagerAdapter extends FragmentStatePagerAdapter {

    private ArrayList<Item> items;

    public ItemDetailPagerAdapter(FragmentManager fm) {
        super(fm);
        items = new ArrayList<Item>();
    }

    public ItemDetailPagerAdapter(FragmentManager fm, ArrayList<Item> list){
        this(fm);
        items = list;
    }

    @Override
    public Fragment getItem(int position) {
        return ItemDetailFragment.newInstance(items.get(position));
    }

    @Override
    public int getCount() {
        return items.size();
    }
}
