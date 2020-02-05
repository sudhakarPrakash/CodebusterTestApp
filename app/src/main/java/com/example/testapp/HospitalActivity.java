package com.example.testapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.appbar.AppBarLayout;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class HospitalActivity extends AppCompatActivity {

    Connection connection;
    String markRecent;

    AppBarLayout appBarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        appBarLayout = findViewById(R.id.app_bar);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        byte[] byteArray = bundle.getByteArray("Image");
        if (byteArray != null) {
            Bitmap hospitalBitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            ImageView imageView = new ImageView(this);
            imageView.setImageBitmap(hospitalBitmap);
            appBarLayout.setBackground(imageView.getDrawable());
        }

        assert bundle != null;
        final String TITLE = bundle.getString("Hospital_Name_Key") + "," + bundle.getString("Hospital_Description_Key");
        setTitle(TITLE);
        markRecent = bundle.getString("Hospital_Id_Key");

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });


        connection = ConnectionHelper.createConnectionWithDB();
        try {
            String query = "INSERT INTO recentSearches (HospitalId) VALUES ('" + markRecent + "')";
            Statement statement = connection.createStatement();
            statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
