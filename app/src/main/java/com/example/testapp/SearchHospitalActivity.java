package com.example.testapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialAutoCompleteTextView;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class SearchHospitalActivity extends AppCompatActivity implements View.OnClickListener {

    ArrayList<Hospital> hospitalList;
    private MaterialAutoCompleteTextView mCity_tv;
    private MaterialButton mSearch_Btn;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_hospital);

        mSearch_Btn = findViewById(R.id.btn_search);
        mCity_tv = findViewById(R.id.tv_city);
        mRecyclerView = findViewById(R.id.recyclerView_recentSearches);

        mSearch_Btn.setOnClickListener(this);
        mCity_tv.setOnClickListener(this);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        mRecyclerView.setLayoutManager(layoutManager);


        //Make Query
        String query = "select CityName from cities";

        //create connnection with the DATABASE
        try {
            Connection connection = ConnectionHelper.createConnectionWithDB();
            if(connection!=null) {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery();

                ArrayList<String> cityList = new ArrayList<>();
                while (resultSet.next()) {
                    cityList.add(resultSet.getString("CityName"));
                }
                preparedStatement.close();
                resultSet.close();

                ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, cityList);
                mCity_tv.setThreshold(1);
                mCity_tv.setAdapter(adapter);


                String q1 = "SELECT * FROM hospitals where HospitalId in (SELECT * FROM recentSearches)";
                String q2 = "SELECT HospitalImage FROM hospitalImages where HospitalId in (SELECT * FROM recentSearches)";

                Statement stmt1 = connection.createStatement();
                Statement stmt2 = connection.createStatement();

                ResultSet rs1 = stmt1.executeQuery(q1);
                ResultSet rs2 = stmt2.executeQuery(q2);

                hospitalList = new ArrayList<>();
                //Decoding and Setting Image in the imageview
                while (rs1.next() && rs2.next()) {
                    Blob blob = rs2.getBlob("HospitalImage");
                    byte[] decodeString = blob.getBytes(1, (int) blob.length());
                    Bitmap decodebitmap = BitmapFactory.decodeByteArray(decodeString, 0, decodeString.length);

                    Hospital hospital = new Hospital();
                    hospital.setHospitalId(rs1.getString("HospitalId"));
                    hospital.setHospitalName(rs1.getString("HospitalName"));
                    hospital.setDescription(rs1.getString("Description"));
                    hospital.setHospitalImage(decodebitmap);
                    hospitalList.add(hospital);

                }

            }else{
                Toast.makeText(this, "connection failure try again", Toast.LENGTH_SHORT).show();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            mAdapter = new RecentSearchAdapter(hospitalList, this);
            mRecyclerView.setAdapter(mAdapter);
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_search:
                String QUERY1= "SELECT top 14 HospitalId,HospitalName,Description,Address FROM hospitals";
                String QUERY2= "select top 14 HospitalImage from hospitalImages";
                Intent intent = new Intent(SearchHospitalActivity.this, SearchResultActivity.class);
                intent.putExtra("QUERY1",QUERY1);
                intent.putExtra("QUERY2",QUERY2);
                startActivity(intent);
                break;
            case R.id.tv_city:
                break;
            default:
                break;

        }

    }
}
