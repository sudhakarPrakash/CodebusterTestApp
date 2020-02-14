package com.example.testapp.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testapp.R;
import com.example.testapp.utilCode.Doctor;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.shape.CornerFamily;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.List;

public class DoctorsRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Doctor> mdoctorList = new ArrayList<>();
    private Context mContext;

    public DoctorsRecyclerAdapter(Context mContext, List<Doctor> mdoctorList) {
        this.mContext = mContext;
        this.mdoctorList = mdoctorList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item_doctor, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mdoctorList.size();
    }


    /****************INNER CLASS**********************/
    private class ViewHolder extends RecyclerView.ViewHolder {
        private ShapeableImageView doctorImage;
        private MaterialTextView doctorName;
        private MaterialTextView doctorSpecialization;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            doctorImage = itemView.findViewById(R.id.doctor_image);
            doctorImage.setShapeAppearanceModel(doctorImage.getShapeAppearanceModel()
                    .toBuilder()
                    .setAllCorners(CornerFamily.ROUNDED, 30)
                    .build());

            doctorName = itemView.findViewById(R.id.doctor_name);
            doctorSpecialization = itemView.findViewById(R.id.doctor_specialization);
        }

        void bind(int position) {
            doctorImage.setImageBitmap(mdoctorList.get(position).getmDoctorImage());
            doctorName.setText(mdoctorList.get(position).getmDoctorName());
            doctorSpecialization.setText(mdoctorList.get(position).getmDoctorSpecialization());
        }

    }
}
