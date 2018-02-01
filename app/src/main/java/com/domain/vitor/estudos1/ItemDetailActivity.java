package com.domain.vitor.estudos1;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

//Atividade obsoleta. Foi trocada por ItemDetailPagerActivity


public class ItemDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_detail_fragment);

        Bundle b = this.getIntent().getExtras();
        if(b!=null){
            Item myItem = (Item)b.getSerializable("ITEM_KEY");

            TextView nameView = (TextView)findViewById(R.id.item_detail_name);
            TextView attributesView = (TextView)findViewById(R.id.item_detail_details);
            TextView descriptionView = (TextView)findViewById(R.id.item_detail_description);

            nameView.setText(myItem.item_name);
            attributesView.setText(myItem.details);
            descriptionView.setText(myItem.description);

            if(myItem.table != null){
                LinearLayout mainLayout = (LinearLayout)findViewById(R.id.item_detail_scroll_layout);

                LinearLayout.LayoutParams tableParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);


                TableLayout tableLayout = new TableLayout(this);
                tableLayout.setLayoutParams(tableParams);

                for(int i = 0; i<myItem.table.rows;i++)
                {

                    TableRow newRow = new TableRow(this);
                    newRow.setWeightSum(1);
                    if(i%2 != 0) {
                        newRow.setBackgroundColor(getResources().getColor(R.color.tableBackground));
                    }

                    for(int j = 0; j<myItem.table.columns;j++)
                    {

                        TableRow.LayoutParams colParams = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT,1f/myItem.table.columns);
                        colParams.setMargins(0,(int)getResources().getDimension(R.dimen.text_margin),(int)getResources().getDimension(R.dimen.text_margin),(int)getResources().getDimension(R.dimen.text_margin));
                        TextView colTextView = new TextView(this);
                        colTextView.setText(myItem.table.stringArray[i][j]);
                        colTextView.setLayoutParams(colParams);

                        if(i == 0){
                            colTextView.setTypeface(Typeface.DEFAULT_BOLD);
                        }

                        newRow.addView(colTextView);

                    }
                    tableLayout.addView(newRow);
                }


                mainLayout.addView(tableLayout);

            }


        }



    }

}
