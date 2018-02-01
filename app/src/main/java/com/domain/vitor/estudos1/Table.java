package com.domain.vitor.estudos1;


import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Table implements Parcelable {

    public int rows,columns;
    public String[][] stringArray;

    public Table(int _rows, int _columns, String[][] content){
        rows = _rows;
        columns = _columns;
        stringArray = content;
    }


    protected Table(Parcel in) {
        rows = in.readInt();
        columns = in.readInt();
        stringArray = new String[rows][];
        for(int i = 0; i< rows;i++){
            stringArray[i] = in.createStringArray();
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(rows);
        dest.writeInt(columns);
        for(int i = 0; i<rows; i++){
            dest.writeStringArray(stringArray[i]);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Table> CREATOR = new Creator<Table>() {
        @Override
        public Table createFromParcel(Parcel in) {
            return new Table(in);
        }

        @Override
        public Table[] newArray(int size) {
            return new Table[size];
        }
    };
}
