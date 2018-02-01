package com.domain.vitor.estudos1;



import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;


//Classe dos itens magicos que sao mostrados

public class Item implements Parcelable {




    public enum Category {
        ARMOR("Armor"),//0
        POTION("Potion"),//1
        RING("Ring"),//2
        ROD("Rod"),//3
        SCROLL("Scroll"),//4
        STAFF("Staff"),//5
        WAND("Wand"),//6
        WEAPON("Weapon"),//7
        WONDROUS("Wondrous item");//8

        private final String description;

        private Category(String s) {
            description = s;
        }

        @Override
        public String toString(){
            return description;
        }
    }

    public enum Rarity {
        COMMON("Common"),//0
        UNCOMMON("Uncommon"),//1
        RARE("Rare"),//2
        VERY_RARE("Very rare"),//3
        LEGENDARY("Legendary"),//4
        VARIES("rarity varies");//5

        private final String description;

        private Rarity(String s) {
            description = s;
        }

        @Override
        public String toString(){
            return description;
        }
    }



    public boolean requires_attunement;
    public String item_name, item_type, attunement_description, description;
    public int pageNumber;
    public Rarity rarity;
    public Category category;
    public String details;
    public Table table;



    //Construtor itens sem tabela

    public Item(String _name, int page, Category _cat, String _type, Rarity _rarity, boolean req_attunement, String _attunement, String _description) {
        item_name = _name;
        pageNumber = page;
        category = _cat;
        item_type = _type;
        rarity = _rarity;
        requires_attunement = req_attunement;
        attunement_description = _attunement;
        description = _description;

        String short_description = category.toString();
        if(!_type.isEmpty()){
            short_description = short_description.concat(" (" + item_type + ")");
        }
        short_description = short_description.concat(", " + rarity.toString());
        if(requires_attunement){
            short_description = short_description.concat(" (requires attunement");
            if(!attunement_description.isEmpty()){
                short_description = short_description.concat(" " + attunement_description);
            }
            short_description = short_description.concat(")");
        }

        details = short_description;
        table = null;

    }


    //Construtor itens com tabela.
    public Item(String _name, int page, Category _cat, String _subType, Rarity _rarity, boolean req_attunement, String _attunement,
                String _description, int rows, int columns, String[][] tableContent) {

        this(_name, page, _cat, _subType, _rarity, req_attunement, _attunement, _description);


        table = new Table(rows, columns, tableContent);


    }


    //Metodos da interface Parcelable

    protected Item(Parcel in) {
        requires_attunement = in.readByte() != 0;
        item_name = in.readString();
        item_type = in.readString();
        attunement_description = in.readString();
        description = in.readString();
        pageNumber = in.readInt();
        details = in.readString();
        table = in.readParcelable(Table.class.getClassLoader());

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (requires_attunement ? 1 : 0));
        dest.writeString(item_name);
        dest.writeString(item_type);
        dest.writeString(attunement_description);
        dest.writeString(description);
        dest.writeInt(pageNumber);
        dest.writeString(details);
        dest.writeParcelable(table, flags);

    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Item> CREATOR = new Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };
}
