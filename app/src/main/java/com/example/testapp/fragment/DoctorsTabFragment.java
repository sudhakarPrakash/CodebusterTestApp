package com.example.testapp.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testapp.R;
import com.example.testapp.adapter.DoctorsRecyclerAdapter;
import com.example.testapp.utilCode.Doctor;
import com.example.testapp.viewModel.DoctorsViewModel;

import java.util.List;

public class DoctorsTabFragment extends Fragment {

    private static final String TAG = DoctorsTabFragment.class.getSimpleName();
    ContentLoadingProgressBar mProgressBar;
    private DoctorsViewModel mViewModel;
    private RecyclerView mRecyclerView;
    private DoctorsRecyclerAdapter mRecyclerAdapter;

    public static DoctorsTabFragment newInstance() {
        return new DoctorsTabFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.doctors_tab_fragment, container, false);
        mRecyclerView = root.findViewById(R.id.recyclerView_doctorList);
        mProgressBar = root.findViewById(R.id.pb);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(DoctorsViewModel.class);
        // TODO: Use the ViewModel

        final Observer<List<Doctor>> observer = new Observer<List<Doctor>>() {
            @Override
            public void onChanged(List<Doctor> doctors) {
                Log.d(TAG, "onChanged: triggered");

                showProgressBar();
                initRecyclerView(doctors);
                hideProgressBar();
            }
        };

        mViewModel.getLiveData().observe(getViewLifecycleOwner(), observer);

    }

    private void initRecyclerView(List<Doctor> doctors) {
        mRecyclerAdapter = new DoctorsRecyclerAdapter(getActivity(), doctors);
        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mRecyclerAdapter);
    }

    private void showProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        mProgressBar.setVisibility(View.INVISIBLE);
    }

}
