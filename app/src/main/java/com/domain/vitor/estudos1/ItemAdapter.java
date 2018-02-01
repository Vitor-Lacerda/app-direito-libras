package com.domain.vitor.estudos1;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


//Adapter pra listview da ListActivity

public class ItemAdapter extends BaseAdapter implements Filterable {



    enum SortMode {
        NAME,
        CATEGORY,
        RARITY
    }


    private Context myContext;
    private LayoutInflater myInflater;
    private ArrayList<Item> dataSource, originalDataSource;
    private SortMode sortMode;
    private MyItemFilter filter;



    public ItemAdapter(Context context, ArrayList<Item> items){
        myContext = context;
        dataSource = items;
        originalDataSource = items;
        myInflater = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        sortMode = SortMode.NAME;

    }

    public void setDataSource(ArrayList<Item> l){
        dataSource = l;
    }

    public ArrayList<Item> getDataSource(){return dataSource;}

    public ArrayList<Item> getOriginalDataSource(){return originalDataSource;}

    public void addCategoryFilter(Item.Category c){
        if(filter == null){
            filter = new MyItemFilter(this);
        }
        filter.addCategoryFilter(c);
    }

    public void removeCategoryFilter(Item.Category c){
        if(filter == null){
            filter = new MyItemFilter(this);
        }
        filter.removeCategoryFilter(c);
    }

    public void addRarityFilter(Item.Rarity c){
        if(filter == null){
            filter = new MyItemFilter(this);
        }
        filter.addRarityFilter(c);
    }

    public void removeRarityFilter(Item.Rarity c){
        if(filter == null){
            filter = new MyItemFilter(this);
        }
        filter.removeRarityFilter(c);
    }

    public void clearFilters(){
        if(filter == null){
            filter = new MyItemFilter(this);
        }
        else {
            filter.clearFilter();
        }
    }

    public  void setReq_attunement(int i){
        if(filter == null){
            filter = new MyItemFilter(this);
        }
        filter.setReq_attunement(i);

    }

    public void setSortMode(SortMode s){sortMode = s;}

    @Override
    public int getCount() {
        return dataSource.size();
    }

    public int getOriginalCount(){return  originalDataSource.size();}

    @Override
    public Object getItem(int position) {
        return dataSource.get(position);
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        Item myItem = (Item)getItem(position);

        convertView = myInflater.inflate(R.layout.list_item_row_layout, parent, false);
        TextView headerView = (TextView) convertView.findViewById(R.id.header_layout_text);
        headerView.setVisibility(View.GONE);
        //Se for o primeiro item faz o header de acordo com o sortMode
        if(position == 0){
            headerView.setText(makeHeaderText(myItem));
            headerView.setVisibility(View.VISIBLE);
        }
        else{
            //Se o item anterior for diferente de acordo com o sortMode e uma 'seçao' nova.
            Item previousItem = (Item)getItem(position-1);
            boolean dif = false;
            switch (sortMode){
                case NAME:
                default:
                    String firstCharMyItem = myItem.item_name.substring(0,1);
                    String previousItemFirstChar = previousItem.item_name.substring(0,1);
                    dif = !firstCharMyItem.equalsIgnoreCase(previousItemFirstChar);
                    break;
                case CATEGORY:
                    dif = (myItem.category != previousItem.category);
                    break;
                case RARITY:
                    dif = (myItem.rarity != previousItem.rarity);
                    break;


            }
            if(dif){
                headerView.setText(makeHeaderText(myItem));
                headerView.setVisibility(View.VISIBLE);
            }
        }

        //Popula a linha da lista
        TextView nameView = (TextView) convertView.findViewById(R.id.item_list_name);
        TextView shortDescriptionView = (TextView) convertView.findViewById(R.id.item_list_short_des);

        nameView.setText(myItem.item_name);
        shortDescriptionView.setText(myItem.details);


        return convertView;

    }

    private String makeHeaderText(Item firstItem){

        String re = "";


        switch (sortMode){
            case NAME:
            default:
                //Header e a primeira letra
                re = firstItem.item_name.substring(0,1);
                break;
            case CATEGORY:
                //Header e a categoria
                re = firstItem.category.toString();
                break;
            case RARITY:
                //Header e a raridade
                re = firstItem.rarity.toString();
                break;


        }
        return re;
    }


    public void sortItemsByName(){


        //Organiza displayedItems em ordem alfabetica.

        Collections.sort(dataSource, new Comparator<Item>() {
            @Override
            public int compare(Item o1, Item o2) {
                return o1.item_name.compareTo(o2.item_name);
            }
        });

        setSortMode(SortMode.NAME);
        this.notifyDataSetChanged();
    }

    public void sortItemsByCategory(){


        //Organiza de acordo com a categoria. Dentro das categorias os itens sao organizados
        //de acordo por raridade e depois por nome.
        Collections.sort(dataSource, new Comparator<Item>() {
            @Override
            public int compare(Item o1, Item o2) {
                if(o1.category == o2.category){
                    if(o1.rarity == o2.rarity){
                        return o1.item_name.compareTo(o2.item_name);
                    }
                    return o1.rarity.compareTo(o2.rarity);
                }
                return o1.category.compareTo(o2.category);

            }
        });
        setSortMode(SortMode.CATEGORY);
        this.notifyDataSetChanged();
    }

    public void sortItemsByRarity(){


        //Organiza de acordo com a raridade. Dentro da raridade sao organizados pela categoria
        //depois por nome.
        Collections.sort(dataSource, new Comparator<Item>() {
            @Override
            public int compare(Item o1, Item o2) {
                if(o1.rarity == o2.rarity){
                    if(o1.category == o2.category){
                        return o1.item_name.compareTo(o2.item_name);
                    }
                    return o1.category.compareTo(o2.category);
                }
                return o1.rarity.compareTo(o2.rarity);
            }
        });
        setSortMode(SortMode.RARITY);
        this.notifyDataSetChanged();

    }

    public void sortBySortMode(){
        switch (sortMode){
            case NAME:
            default:
                sortItemsByName();
                break;
            case CATEGORY:
                sortItemsByCategory();
                break;
            case RARITY:
                sortItemsByRarity();
                break;
        }
    }

    @Override
    public Filter getFilter() {
        if(filter == null){
            filter = new MyItemFilter(this);
        }
        return  filter;
    }



}

class MyItemFilter extends Filter{

    private ArrayList<Item.Category> categories;
    private ArrayList<Item.Rarity> rarities;
    private int req_attunement = 0; //-1 - Sem Attunement/ 0 tanto faz / 1 precisa
    private ItemAdapter mAdapter;

    public MyItemFilter(ItemAdapter adapter){
        categories = new ArrayList<Item.Category>();
        rarities = new ArrayList<Item.Rarity>();
        mAdapter = adapter;
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results = new FilterResults();
        if(categories.size() == 0 && rarities.size() == 0 && constraint.length() == 0 && req_attunement == 0){
            results.values = mAdapter.getOriginalDataSource();
            results.count = mAdapter.getOriginalCount();
        }
        else{
            ArrayList<Item> filteredItems = new ArrayList<Item>();
            for (Item i:mAdapter.getOriginalDataSource()) {
                if(categories.size() == 0 || categories.contains(i.category)){
                    if(rarities.size() == 0 || rarities.contains(i.rarity)) {
                        if(req_attunement == 0 || (req_attunement == 1 && i.requires_attunement) || (req_attunement == -1 && !i.requires_attunement)) {
                            if(constraint.length() == 0 || searchText(i, constraint)) {
                                filteredItems.add(i);
                            }
                        }
                   }
                }
            }

            results.values = filteredItems;
            results.count = filteredItems.size();


        }

        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
            mAdapter.setDataSource((ArrayList<Item>)results.values);
            mAdapter.sortBySortMode();
    }

    private boolean searchText(Item i, CharSequence constraint){
        if(i.item_name.toLowerCase().contains(constraint.toString().toLowerCase())){
            return true;
        }

        if(i.item_type.toLowerCase().contains(constraint.toString().toLowerCase())){
            return true;
        }

        return false;
    }

    public void addCategoryFilter(Item.Category c){if(!categories.contains(c))categories.add(c);}

    public void removeCategoryFilter(Item.Category c){categories.remove(c);}

    public void addRarityFilter(Item.Rarity c){if(!rarities.contains(c))rarities.add(c);}

    public void removeRarityFilter(Item.Rarity c){rarities.remove(c);}

    public void clearFilter(){rarities.clear(); categories.clear(); req_attunement = 0;}

    public void setReq_attunement(int i){req_attunement = i;}
}
