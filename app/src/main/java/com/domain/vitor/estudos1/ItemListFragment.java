package com.domain.vitor.estudos1;

import android.app.Fragment;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


//Essa activity mostra uma lista dos itens magicos do DMG

public class ItemListFragment extends Fragment {

    private ItemAdapter myAdapter;

    private final Item.Category[] categoryValues = Item.Category.values();
    private final Item.Rarity[] rarityValues = Item.Rarity.values();
    private CharSequence searchSequence;
    private boolean sideMenuOpen = false;

    //construtor vazio exigido
    public ItemListFragment(){

    }

    //Metodo fabrica, pra fazer uma nova passando parametro
    public static ItemListFragment newInstance() {
        ItemListFragment fragment = new ItemListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        LinearLayout mainLayout = (LinearLayout)inflater.inflate(R.layout.activity_item_list, container, false);
        ListView listView = (ListView)mainLayout.findViewById(R.id.magic_item_list);

        setButtons(mainLayout);
        // ArrayList<Item> items = makeItemList();
        ArrayList<Item> jsonItems = makeItemListFromJSON(); //Lista original igual a do JSON.
        searchSequence = "";


        myAdapter = new ItemAdapter(mainLayout.getContext(), jsonItems);
        listView.setAdapter(myAdapter);
        myAdapter.sortItemsByName();

        listView.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent();
                Bundle b = new Bundle();

                //Abre a ItemDetailPagerActivity passando displayedItems como a lista e posicao do clique.
                b.putParcelableArrayList(getResources().getString(R.string.intent_item_list_key), myAdapter.getDataSource());
                b.putInt(getResources().getString(R.string.intent_item_index_key), position);
                i.setClass(view.getContext(), ItemDetailPagerActivity.class);

                i.putExtras(b);
                startActivity(i);
            }
        });

        return mainLayout;
    }

    private void setButtons(final View mainView){
        Button byName = (Button)mainView.findViewById(R.id.sort_by_name_button);
        byName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myAdapter.sortItemsByName();
            }
        });

        Button byCategory = (Button)mainView.findViewById(R.id.sort_by_category_button);
        byCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myAdapter.sortItemsByCategory();
            }
        });

        Button byRariry = (Button)mainView.findViewById(R.id.sort_by_rarity_button);
        byRariry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myAdapter.sortItemsByRarity();
            }
        });

        final EditText searchText = (EditText)mainView.findViewById(R.id.list_search_bar);
        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchSequence = s;
                myAdapter.getFilter().filter(searchSequence);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        Button openFilters = (Button)mainView.findViewById(R.id.open_filters_button);
        View.OnClickListener openCloseFilters = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOpenFiltersClick(mainView);
            }
        };
        openFilters.setOnClickListener(openCloseFilters);

        Button okFilters = (Button)mainView.findViewById(R.id.ok_filters_button);
        okFilters.setOnClickListener(openCloseFilters);

        Button clearFilters = (Button)mainView.findViewById(R.id.clear_filters_button);
        clearFilters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClearFiltersClick(mainView);
            }
        });

        View.OnClickListener checkBoxClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCheckBoxFilterClick(mainView,v);
            }
        };

        CheckBox checkBox = (CheckBox)mainView.findViewById(R.id.check_armor);
        checkBox.setOnClickListener(checkBoxClickListener);
        checkBox = (CheckBox)mainView.findViewById(R.id.check_potion);
        checkBox.setOnClickListener(checkBoxClickListener);
        checkBox = (CheckBox)mainView.findViewById(R.id.check_ring);
        checkBox.setOnClickListener(checkBoxClickListener);
        checkBox = (CheckBox)mainView.findViewById(R.id.check_rod);
        checkBox.setOnClickListener(checkBoxClickListener);
        checkBox = (CheckBox)mainView.findViewById(R.id.check_scroll);
        checkBox.setOnClickListener(checkBoxClickListener);
        checkBox = (CheckBox)mainView.findViewById(R.id.check_staff);
        checkBox.setOnClickListener(checkBoxClickListener);
        checkBox = (CheckBox)mainView.findViewById(R.id.check_wand);
        checkBox.setOnClickListener(checkBoxClickListener);
        checkBox = (CheckBox)mainView.findViewById(R.id.check_weapon);
        checkBox.setOnClickListener(checkBoxClickListener);
        checkBox = (CheckBox)mainView.findViewById(R.id.check_wondrous);
        checkBox.setOnClickListener(checkBoxClickListener);

        checkBox = (CheckBox)mainView.findViewById(R.id.check_common);
        checkBox.setOnClickListener(checkBoxClickListener);
        checkBox = (CheckBox)mainView.findViewById(R.id.check_uncommon);
        checkBox.setOnClickListener(checkBoxClickListener);
        checkBox = (CheckBox)mainView.findViewById(R.id.check_rare);
        checkBox.setOnClickListener(checkBoxClickListener);
        checkBox = (CheckBox)mainView.findViewById(R.id.check_very_rare);
        checkBox.setOnClickListener(checkBoxClickListener);
        checkBox = (CheckBox)mainView.findViewById(R.id.check_legendary);
        checkBox.setOnClickListener(checkBoxClickListener);

        checkBox = (CheckBox)mainView.findViewById(R.id.check_requires_att);
        checkBox.setOnClickListener(checkBoxClickListener);
        checkBox = (CheckBox)mainView.findViewById(R.id.check_no_att);
        checkBox.setOnClickListener(checkBoxClickListener);






    }



    public void onCheckBoxFilterClick(View main, View v){


        boolean checked = ((CheckBox) v).isChecked();

        switch (v.getId()){
            case R.id.check_armor:
                if(checked) {
                    myAdapter.addCategoryFilter(Item.Category.ARMOR);
                }
                else {
                    myAdapter.removeCategoryFilter(Item.Category.ARMOR);
                }
                break;
            case R.id.check_potion:
                if(checked) {
                    myAdapter.addCategoryFilter(Item.Category.POTION);
                }
                else {
                    myAdapter.removeCategoryFilter(Item.Category.POTION);
                }
                break;
            case R.id.check_ring:
                if(checked) {
                    myAdapter.addCategoryFilter(Item.Category.RING);
                }
                else {
                    myAdapter.removeCategoryFilter(Item.Category.RING);
                }
                break;
            case R.id.check_rod:
                if(checked) {
                    myAdapter.addCategoryFilter(Item.Category.ROD);
                }
                else {
                    myAdapter.removeCategoryFilter(Item.Category.ROD);
                }
                break;
            case R.id.check_scroll:
                if(checked) {
                    myAdapter.addCategoryFilter(Item.Category.SCROLL);
                }
                else {
                    myAdapter.removeCategoryFilter(Item.Category.SCROLL);
                }
                break;
            case R.id.check_staff:
                if(checked) {
                    myAdapter.addCategoryFilter(Item.Category.STAFF);
                }
                else {
                    myAdapter.removeCategoryFilter(Item.Category.STAFF);
                }
                break;
            case R.id.check_wand:
                if(checked) {
                    myAdapter.addCategoryFilter(Item.Category.WAND);
                }
                else {
                    myAdapter.removeCategoryFilter(Item.Category.WAND);
                }
                break;
            case R.id.check_weapon:
                if(checked) {
                    myAdapter.addCategoryFilter(Item.Category.WEAPON);
                }
                else {
                    myAdapter.removeCategoryFilter(Item.Category.WEAPON);
                }
                break;
            case R.id.check_wondrous:
                if(checked) {
                    myAdapter.addCategoryFilter(Item.Category.WONDROUS);
                }
                else {
                    myAdapter.removeCategoryFilter(Item.Category.WONDROUS);
                }
                break;

            case R.id.check_common:
                if(checked) {
                    myAdapter.addRarityFilter(Item.Rarity.COMMON);
                }
                else {
                    myAdapter.removeRarityFilter(Item.Rarity.COMMON);
                }
                break;
            case R.id.check_uncommon:
                if(checked) {
                    myAdapter.addRarityFilter(Item.Rarity.UNCOMMON);
                }
                else {
                    myAdapter.removeRarityFilter(Item.Rarity.UNCOMMON);
                }
                break;
            case R.id.check_rare:
                if(checked) {
                    myAdapter.addRarityFilter(Item.Rarity.RARE);
                }
                else {
                    myAdapter.removeRarityFilter(Item.Rarity.RARE);
                }
                break;
            case R.id.check_very_rare:
                if(checked) {
                    myAdapter.addRarityFilter(Item.Rarity.VERY_RARE);
                }
                else {
                    myAdapter.removeRarityFilter(Item.Rarity.VERY_RARE);
                }
                break;
            case R.id.check_legendary:
                if(checked) {
                    myAdapter.addRarityFilter(Item.Rarity.LEGENDARY);
                }
                else {
                    myAdapter.removeRarityFilter(Item.Rarity.LEGENDARY);
                }
                break;
            case R.id.check_requires_att:
                if(checked){
                    myAdapter.setReq_attunement(1);
                    ((CheckBox)main.findViewById(R.id.check_no_att)).setChecked(false);
                }
                else{
                    myAdapter.setReq_attunement(0);
                }
                break;

            case R.id.check_no_att:
                if(checked){
                    myAdapter.setReq_attunement(-1);
                    ((CheckBox)main.findViewById(R.id.check_requires_att)).setChecked(false);
                }
                else{
                    myAdapter.setReq_attunement(0);
                }
                break;


        }

        Filter f = myAdapter.getFilter();
        f.filter(searchSequence);
        //myAdapter.sortBySortMode();

    }


    public void onOpenFiltersClick(View v) {
        //Abrir e fechar tela de filtros
        LinearLayout fl = (LinearLayout)v.findViewById(R.id.filter_layout);
        if(fl.getVisibility() == View.VISIBLE){
            fl.setVisibility(View.GONE);
        }
        else{
            fl.setVisibility(View.VISIBLE);
        }
    }

    public void onClearFiltersClick(View v){

        LinearLayout catChecks = (LinearLayout)v.findViewById(R.id.category_checks);
        LinearLayout rareChecks = (LinearLayout)v.findViewById(R.id.rarity_checks);

        //Pula a 0 que e o texto
        for(int i = 1; i<catChecks.getChildCount(); i++){
            ((CheckBox)catChecks.getChildAt(i)).setChecked(false);
        }

        //Pula a 0 que e o texto
        for(int j = 1; j<rareChecks.getChildCount(); j++){
            try {
                ((CheckBox) rareChecks.getChildAt(j)).setChecked(false);
            }
            catch (Exception e){
                //Faz nada mesmo
            }
        }

        myAdapter.clearFilters();
        myAdapter.getFilter().filter(searchSequence);
    }


    public void onClickSideMenu(View v){
        if(!sideMenuOpen){
            sideMenuOpen = true;
            ((ImageButton)v).setImageDrawable(getResources().getDrawable(R.drawable.closemenutab));
            //findViewById(R.id.side_menu).setVisibility(View.VISIBLE);
        }
        else{
            sideMenuOpen = false;
            ((ImageButton)v).setImageDrawable(getResources().getDrawable(R.drawable.ic_action_name));
        }

    }

    /*
    ArrayList<Item> makeItemList(){

        ArrayList<Item> items = new ArrayList<Item>();
        //NAME, CATEGORY, SUBTYPE, RARITY, ATTUNEMENT, ATTUNEMENT DESCRIPTION, DESCRIPTION
        items.add(new Item("Adamantine Armor", Item.Category.ARMOR, "medium or heavy, not hide", Item.Rarity.UNCOMMON, false, "", "This suit of armor is reinforced with adamantine, one " +
                "of the hardest substances in existence. While you're " +
                "wearing it, any critical hit against you becomes a " +
                "normal hit."));


        String[][] alchemyJugTable = new String[11][2];

        alchemyJugTable[0][0] = "Liquid"; alchemyJugTable[0][1] = "Max Amount";
        alchemyJugTable[1][0] = "Acid"; alchemyJugTable[1][1] = "8 ounces";
        alchemyJugTable[2][0] = "Basic poison"; alchemyJugTable[2][1] = "1/2 ounce";
        alchemyJugTable[3][0] = "Beer "; alchemyJugTable[3][1] = "4 gallons";
        alchemyJugTable[4][0] = "Honey"; alchemyJugTable[4][1] = "1 gallon";
        alchemyJugTable[5][0] = "Mayonnaise"; alchemyJugTable[5][1] = "2 gallons";
        alchemyJugTable[6][0] = "Oil"; alchemyJugTable[6][1] = "1 quart";
        alchemyJugTable[7][0] = "Vinegar"; alchemyJugTable[7][1] = "2 gallons";
        alchemyJugTable[8][0] = "Water,fresh"; alchemyJugTable[8][1] = "8 gallons";
        alchemyJugTable[9][0] = "Water, salt"; alchemyJugTable[9][1] = "12 gallons";
        alchemyJugTable[10][0] = "Wine"; alchemyJugTable[10][1] = "1 gallon";




        items.add(new Item("Alchemy Jug", Item.Category.WONDROUS, "", Item.Rarity.UNCOMMON, false, "", "This ceramic jug appears to be able to hold a gallon " +
                "of liquid and weighs 12 pounds whether full or empty. " +
                "Sloshing sounds can be heard from within the jug when " +
                "it is shaken, even if the jug is empty. " +
                "You can use an action and name one liquid from the " +
                "table below to cause the jug to produce the chosen " +
                "liquid. Afterward, you can uncork the jug as an action " +
                "and pour that liquid out, up to 2 gallons per minute. The " +
                "maximum amount of liquid the jug can produce depends " +
                "on the liquid you named. " +
                "Once the jug starts producing a liquid, it can't produce " +
                "a different one, or more of one that has reached its " +
                "maximum, until the next dawn.",11,2,alchemyJugTable));





        items.add(new Item("Ammunition +1", Item.Category.WEAPON, "any ammunition", Item.Rarity.UNCOMMON, false, "", "You have a +1 bonus to attack and damage rolls made " +
                "with this piece of magic ammunition. Once it hits " +
                "a target, the ammunition is no longer magical."));
        items.add(new Item("Ammunition +2", Item.Category.WEAPON, "any ammunition", Item.Rarity.RARE, false, "", "You have a +2 bonus to attack and damage rolls made " +
                "with this piece of magic ammunition. Once it hits " +
                "a target, the ammunition is no longer magical."));
        items.add(new Item("Ammunition +3", Item.Category.WEAPON, "any ammunition", Item.Rarity.VERY_RARE, false, "", "You have a +3 bonus to attack and damage rolls made " +
                "with this piece of magic ammunition. Once it hits " +
                "a target, the ammunition is no longer magical."));
        items.add(new Item("Amulet of Health", Item.Category.WONDROUS, "", Item.Rarity.RARE, true, "", "Your Constitution score is 19 while you wear this " +
                "amulet. It has no effect on you if your Constitution is " +
                "already 19 or higher."));
        items.add(new Item("Amulet of Proof Against Detection and Location", Item.Category.WONDROUS, "", Item.Rarity.UNCOMMON, true, "", "While wearing this amulet, you are hidden from " +
                "divination magic. You can't be targeted by such magic or " +
                "perceived through magical scrying sensors."));
        items.add(new Item("Amulet of the Planes", Item.Category.WONDROUS, "", Item.Rarity.VERY_RARE, true, "", "While wearing this amulet, you can use an action to " +
                "name a location that you are familiar with on another " +
                "plane of existence. Then make a DC 15 Intelligence " +
                "check. On a successful check, you cast the plane shift " +
                "spell. On a failure, you and each creature and object " +
                "within 15 feet of you travel to a random destination. " +
                "Roll a dlOO. On a 1- 60, you travel to a random location " +
                "on the plane you named. On a 61- 100, you travel to a " +
                "randomly determined plane of existence."));

        return items;

    }
    */

    String loadJSONfromAsset()
    {
        String json = null;
        try{
            InputStream is = getActivity().getAssets().open("items.json");
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

    ArrayList<Item> makeItemListFromJSON()
    {
        ArrayList<Item> items = new ArrayList<Item>();

        try {
            String json = loadJSONfromAsset();
            JSONObject obj = new JSONObject(json);
            JSONArray magicItems = obj.getJSONArray("magicItems");

            for(int i = 0; i<magicItems.length(); i++){
                JSONObject nextItem = magicItems.getJSONObject(i);
                String name = nextItem.getString("name");
                int page = nextItem.getInt("page");
                Item.Category category = categoryValues[nextItem.getInt("category")];
                String type = nextItem.getString("type");
                Item.Rarity rarity = rarityValues[nextItem.getInt("rarity")];
                boolean req_attunement = nextItem.getBoolean("req_attunement");
                String attunement = nextItem.getString("attunement");
                String description = nextItem.getString("description");
                if(nextItem.getBoolean("table")){
                    int r = nextItem.getInt("rows");
                    int c = nextItem.getInt("cols");

                    String[][] table = new String[r][c];


                    JSONArray content = nextItem.getJSONArray("table_contents");
                    for(int k = 0; k<content.length();k++) {
                        for (int col = 0; col < c; col++) {
                            JSONObject cObj = content.getJSONObject(k);
                            String colContent = cObj.getString("col" + col);
                            table[k][col] = colContent;
                        }
                    }

                    Item newItem = new Item(name, page, category, type, rarity, req_attunement, attunement, description, r, c, table);
                    items.add(newItem);
                }
                else{
                    Item newItem = new Item(name, page, category, type, rarity, req_attunement, attunement, description);
                    items.add(newItem);
                }


            }



        } catch (JSONException e) {
            e.printStackTrace();
            return items;
        }

        return items;


    }

}
