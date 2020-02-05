package com.example.testapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class RecentSearchAdapter extends RecyclerView.Adapter<RecentSearchAdapter.MyViewHolder> {
    private ArrayList<Hospital> hospitalList;
    private Context context;

    public RecentSearchAdapter(ArrayList<Hospital> hospitalList, Context context) {
        this.hospitalList = hospitalList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rv_item_recent_searches_layout, parent, false);
        RecentSearchAdapter.MyViewHolder vh = new RecentSearchAdapter.MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @SuppressLint("UnsafeExperimentalUsageError")
    @Override
    public void onBindViewHolder(final RecentSearchAdapter.MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element


        final Hospital hospital = hospitalList.get(position);
        holder.hospitalName.setText(hospital.getHospitalName());
        holder.sImageView.setImageBitmap(hospital.getHospitalImage());
        holder.hospitalId = hospital.getHospitalId();
        holder.Description = hospital.getDescription();

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Bundle bundle = new Bundle();

                bundle.putString("Hospital_Id_Key",holder.hospitalId);
                bundle.putString("Hospital_Description_Key",hospital.getDescription());
                bundle.putString("Hospital_Name_Key", holder.hospitalName.getText().toString());

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
        ShapeableImageView sImageView;
        String hospitalId;
        String Description;
        private ItemClickListener itemClickListener;


        MyViewHolder(View v) {
            super(v);
            view = v;
            sImageView = v.findViewById(R.id.siv_hospitalImage);
            hospitalName = v.findViewById(R.id.mtv_hospitalName);

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
