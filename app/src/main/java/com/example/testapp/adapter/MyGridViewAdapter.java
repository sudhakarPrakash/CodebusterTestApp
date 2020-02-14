package com.example.testapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.testapp.R;
import com.example.testapp.utilCode.MyGridItem;

import java.util.ArrayList;

public class MyGridViewAdapter extends ArrayAdapter {

    private ArrayList<MyGridItem> gridItemList;
    private LayoutInflater inflater;

    public MyGridViewAdapter(@NonNull Context context, int resource, ArrayList<MyGridItem> gridItemList) {
        super(context, resource, gridItemList);
        this.gridItemList = gridItemList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        view = inflater.inflate(R.layout.griditem_layout, null);
        ImageView imageview = view.findViewById(R.id.iv_gridItem);
        TextView textView = view.findViewById(R.id.tv_gridItem);
        textView.setText(gridItemList.get(position).getGridItemName());
        imageview.setImageResource(gridItemList.get(position).getGridItemImage());
        return view;
    }
}
