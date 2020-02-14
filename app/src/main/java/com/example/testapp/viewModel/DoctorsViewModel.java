package com.example.testapp.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.testapp.repository.DataRepository;
import com.example.testapp.utilCode.Doctor;

import java.util.List;

public class DoctorsViewModel extends ViewModel {

    private MutableLiveData<List<Doctor>> liveData;
    private DataRepository mRepo;

    public DoctorsViewModel() {
        mRepo = DataRepository.getInstance();
        liveData = mRepo.getDoctorsLiveData();
    }

    public LiveData<List<Doctor>> getLiveData() {
        return liveData;
    }

}