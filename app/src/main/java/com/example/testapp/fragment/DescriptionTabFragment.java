package com.example.testapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.testapp.R;
import com.example.testapp.viewModel.DescriptionFragmentViewModel;

/**
 * A placeholder fragment containing a simple view.
 */
public class DescriptionTabFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private DescriptionFragmentViewModel descriptionFragmentViewModel;

    public static DescriptionTabFragment newInstance(int index) {
        DescriptionTabFragment fragment = new DescriptionTabFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        descriptionFragmentViewModel = new ViewModelProvider(this).get(DescriptionFragmentViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        descriptionFragmentViewModel.setIndex(index);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.description_tab_fragment, container, false);
        final TextView textView = root.findViewById(R.id.section_label);
        descriptionFragmentViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}