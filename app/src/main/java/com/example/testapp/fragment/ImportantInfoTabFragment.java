package com.example.testapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.testapp.R;
import com.example.testapp.viewModel.ImportantInfoViewModel;

public class ImportantInfoTabFragment extends Fragment {

    private ImportantInfoViewModel mViewModel;

    public static ImportantInfoTabFragment newInstance() {
        return new ImportantInfoTabFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.important_info_tab_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ImportantInfoViewModel.class);
        // TODO: Use the ViewModel
    }

}
