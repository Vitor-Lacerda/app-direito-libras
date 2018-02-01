package com.domain.vitor.estudos1;

import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

//Fragmento da ItemDetailPagerActivity que mostra os detalhes de um item da lista.

public class ItemDetailFragment extends Fragment {
    private Item myItem;

    private OnFragmentInteractionListener mListener;

    public ItemDetailFragment() {
        // Required empty public constructor
    }


    public static ItemDetailFragment newInstance(Item item) {
        ItemDetailFragment fragment = new ItemDetailFragment();
        fragment.myItem = item;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        LinearLayout mainLayout = (LinearLayout)inflater.inflate(R.layout.item_detail_fragment, container, false);


        TextView nameView = (TextView)mainLayout.findViewById(R.id.item_detail_name);
        TextView attributesView = (TextView)mainLayout.findViewById(R.id.item_detail_details);
        TextView descriptionView = (TextView)mainLayout.findViewById(R.id.item_detail_description);
        TextView pageView = (TextView)mainLayout.findViewById(R.id.item_detail_page);

        nameView.setText(myItem.item_name);
        attributesView.setText(myItem.details);
        descriptionView.setText(Html.fromHtml(myItem.description));
        pageView.setText(getResources().getString(R.string.dmg_book_name) +" " + myItem.pageNumber);

        if(myItem.table != null){

            LinearLayout scrollableLayout = (LinearLayout)mainLayout.findViewById(R.id.item_detail_scroll_layout);
            LinearLayout.LayoutParams tableParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);


            TableLayout tableLayout = new TableLayout(this.getContext());
            tableLayout.setLayoutParams(tableParams);

            for(int i = 0; i<myItem.table.rows;i++)
            {

                TableRow newRow = new TableRow(this.getContext());
                newRow.setWeightSum(1);
                if(i%2 != 0) {
                    newRow.setBackgroundColor(getResources().getColor(R.color.tableBackground));
                }

                for(int j = 0; j<myItem.table.columns;j++)
                {

                    TableRow.LayoutParams colParams = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT,1f/myItem.table.columns);
                    colParams.setMargins(0,(int)getResources().getDimension(R.dimen.text_margin),(int)getResources().getDimension(R.dimen.text_margin),(int)getResources().getDimension(R.dimen.text_margin));
                    TextView colTextView = new TextView(this.getContext());
                    colTextView.setText(Html.fromHtml(myItem.table.stringArray[i][j]));
                    colTextView.setLayoutParams(colParams);

                    if(i == 0){
                        colTextView.setTypeface(Typeface.DEFAULT_BOLD);
                    }

                    newRow.addView(colTextView);

                }
                tableLayout.addView(newRow);
            }


            scrollableLayout.addView(tableLayout);

        }

        return mainLayout;

    }



    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
