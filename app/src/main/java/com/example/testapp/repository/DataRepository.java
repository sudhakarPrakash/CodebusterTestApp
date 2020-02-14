package com.example.testapp.repository;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.testapp.database.DBConnection;
import com.example.testapp.utilCode.Doctor;
import com.example.testapp.utilCode.HospiatlServices;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DataRepository {

    private static final String TAG = DataRepository.class.getSimpleName();
    private static DataRepository mInstance;
    private HandlerThread handlerThread;
    private Handler handler;
    private Connection connection = DBConnection.getConnection();
    private MutableLiveData<String> hospitalDescription = new MutableLiveData<>();
    private MutableLiveData<HospiatlServices> servicesAvailable = new MutableLiveData<>();
    private MutableLiveData<List<Doctor>> doctorsList = new MutableLiveData<>();
    private MutableLiveData<String[]> importantInfo = new MutableLiveData<>();


    private DataRepository() {

        handlerThread = new HandlerThread("MyHandlerThread");
        handlerThread.start();
        handler = new Handler(handlerThread.getLooper());

        generateHospitalDescription();
        generateServicesAvailable();
        generateDoctorsList();
        generateImportantInfoLiveData();

    }

    public static DataRepository getInstance() {
//        synchronized (DataRepository.class) {
        return mInstance != null ? mInstance : new DataRepository();
//        }
    }

    public MutableLiveData<String> getDescriptionLiveData() {
        return hospitalDescription;
    }

    public MutableLiveData<HospiatlServices> getServicesLiveData() {
        return servicesAvailable;
    }

    public MutableLiveData<List<Doctor>> getDoctorsLiveData() {
        return doctorsList;
    }

    public MutableLiveData<String[]> getImportantInfoLiveData() {
        return importantInfo;
    }


    private void generateHospitalDescription() {
    }

    private void generateServicesAvailable() {
    }

    private void generateDoctorsList() {

        handler.post(new Runnable() {
            @Override
            public void run() {
                String QUERY = "Select * from doctors";
                List<Doctor> allDoctor = new ArrayList<>();
                try {

//                    Thread.sleep(5000);
                    Statement stmt = connection.createStatement();
                    ResultSet rs = stmt.executeQuery(QUERY);

                    while (rs.next()) {

                        Blob blob = rs.getBlob("DoctorImage");
                        byte[] decodeString = blob.getBytes(1, (int) blob.length());
                        Bitmap decodebitmap = BitmapFactory.decodeByteArray(decodeString, 0, decodeString.length);

                        allDoctor.add(new Doctor(decodebitmap, rs.getString("DoctorName"), rs.getString("DoctorSpecialization")));
                    }
                    doctorsList.postValue(allDoctor);
                    Log.d(TAG, "triggered: posted");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void generateImportantInfoLiveData() {
    }

}