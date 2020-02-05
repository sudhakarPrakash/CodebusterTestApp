package com.example.testapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.MyViewHolder> {
    private ArrayList<Hospital> hospitalList;
    private Context context;


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder

    // Provide a suitable constructor (depends on the kind of dataset)
    SearchResultAdapter(ArrayList<Hospital> myDataset, Context context) {
        hospitalList = myDataset;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rv_item_layout, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element


        final Hospital hospital = hospitalList.get(position);
        holder.hospitalName.setText(hospital.getHospitalName());
        holder.description.setText(hospital.getDescription());
        holder.imageView.setImageBitmap(hospital.getHospitalImage());
        holder.hospitalAddress.setText(hospital.getHospitalAddress());
        holder.hospitalId = hospital.getHospitalId();

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Bundle bundle = new Bundle();

                bundle.putString("Hospital_Id_Key",holder.hospitalId);
                bundle.putString("Hospital_Name_Key", holder.hospitalName.getText().toString());
                bundle.putString("Hospital_Description_Key", holder.description.getText().toString());

                //Convert Bitmap to byte array to put into bundle to send through intent
                //to the HospitalActivity
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                Bitmap bitmap_hospital = hospital.getHospitalImage();
                bitmap_hospital.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream.toByteArray();

                bundle.putByteArray("Image",byteArray);

                Intent intent = new Intent(view.getContext(), HospitalActivity.class);
                intent.putExtras(bundle);
                view.getContext().startActivity(intent);

            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        if (hospitalList != null) {
            return hospitalList.size();
        } else {
            return 0;
        }
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        // each data item is just a string in this case
        View view;
        MaterialTextView hospitalName;
        MaterialTextView description;
        ImageView imageView;
        MaterialTextView hospitalAddress;
        String hospitalId;
        private ItemClickListener itemClickListener;


        MyViewHolder(View v) {
            super(v);
            view = v;
            hospitalName = v.findViewById(R.id.hospitalName);
            description = v.findViewById(R.id.hospitalDescription);
            imageView = v.findViewById(R.id.iv_hospital);
            hospitalAddress = v.findViewById(R.id.hospitalAddress);

            v.setOnClickListener(this);
        }

        void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View view) {
            itemClickListener.onClick(view, getAdapterPosition());
        }
    }
}

