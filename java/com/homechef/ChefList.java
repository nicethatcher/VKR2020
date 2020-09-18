package com.homechef;

import android.app.Activity;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ChefList extends ArrayAdapter<Chef> {

    private Activity context;
    private List<Chef> chefsList;
    TextView tv1;


    public ChefList(Activity context,int list_item,List<Chef> chefsList){
        super(context,R.layout.list_item,chefsList);
        this.context = context;
        this.chefsList = chefsList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return myCustomSpinner(position, convertView, parent);

    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return myCustomSpinner(position, convertView, parent);
    }

    private View myCustomSpinner(int position, @Nullable View myView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View listViewItem = inflater.inflate(R.layout.list_item,parent,false);

        tv1 = listViewItem.findViewById(R.id.tv);

        Chef chef = chefsList.get(position);
        tv1.setText(chef.getName());
        return listViewItem;
    }
}
