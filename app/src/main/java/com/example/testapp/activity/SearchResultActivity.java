package com.example.testapp.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testapp.R;
import com.example.testapp.adapter.SearchResultAdapter;
import com.example.testapp.database.DBConnection;
import com.example.testapp.utilCode.Hospital;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class SearchResultActivity extends AppCompatActivity {

    ArrayList<Hospital> hospitalList;
    private String QUERY1;
    private String QUERY2;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

//        hospitalList = initHospital();

        Intent intent = getIntent();
        QUERY1 = intent.getStringExtra("QUERY1");
        QUERY2 = intent.getStringExtra("QUERY2");

        recyclerView = findViewById(R.id.recyclerView_searchResult);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        try {
            Connection connection = DBConnection.getConnection();
            Statement statement1 = connection.createStatement();
            ResultSet rs1 = statement1.executeQuery(QUERY1);

            Statement statement2 = connection.createStatement();
            ResultSet rs2 = statement2.executeQuery(QUERY2);

            hospitalList = new ArrayList<>();
            //Decoding and Setting Image in the imageview
            while (rs2.next() && rs1.next()) {
                Blob blob = rs2.getBlob("HospitalImage");
                byte[] decodeString = blob.getBytes(1, (int) blob.length());
                Bitmap decodebitmap = BitmapFactory.decodeByteArray(decodeString, 0, decodeString.length);


                hospitalList.add(new Hospital(rs1.getString("HospitalId"), rs1.getString("HospitalName"), rs1.getString("Description"), decodebitmap, rs1.getString("Address")));

            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // specify an adapter

            mAdapter = new SearchResultAdapter(hospitalList, this);
            recyclerView.setAdapter(mAdapter);

        }
    }
    // ...

}
