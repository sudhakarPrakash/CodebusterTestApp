package com.example.testapp.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.testapp.R;
import com.example.testapp.database.DBConnection;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.textview.MaterialTextView;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static com.example.testapp.constant.Constants.MAP_VIEW_BUNDLE_KEY;


public class HospitalActivity extends AppCompatActivity implements OnMapReadyCallback, View.OnClickListener {

    Connection connection;
    String markRecent;
    String TITLE;

    AppBarLayout appBarLayout;
    MaterialTextView mSeeFullDescription;
    MaterialTextView mSeeAllServices;
    MaterialTextView mSeeFullInformation;
    private MapView mapView;
    private GoogleMap gmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mSeeFullDescription = findViewById(R.id.mtv_see_full_description);
        mSeeAllServices = findViewById(R.id.mtv_see_all_services);
        mSeeFullInformation = findViewById(R.id.mtv_see_full_important_info);
        mSeeFullDescription.setOnClickListener(this);
        mSeeAllServices.setOnClickListener(this);
        mSeeFullInformation.setOnClickListener(this);

        appBarLayout = findViewById(R.id.app_bar);

        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY);
        }

        mapView = findViewById(R.id.map_view);
        mapView.onCreate(mapViewBundle);
        mapView.getMapAsync(this);

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
        TITLE = bundle.getString("Hospital_Name_Key") + "," + bundle.getString("Hospital_Description_Key");
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


        connection = DBConnection.getConnection();
        try {
            String query = "INSERT INTO recentSearches (HospitalId) VALUES ('" + markRecent + "')";
            Statement statement = connection.createStatement();
            statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(MAP_VIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAP_VIEW_BUNDLE_KEY, mapViewBundle);
        }

        mapView.onSaveInstanceState(mapViewBundle);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    protected void onPause() {
        mapView.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        gmap = googleMap;
        gmap.setMinZoomPreference(12);
        LatLng ny = new LatLng(40.7143528, -74.0059731);
        gmap.moveCamera(CameraUpdateFactory.newLatLng(ny));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.mtv_see_full_description:
                Intent intent = new Intent(this, DetailsTabbedActivity.class);
                intent.putExtra("TITLE", TITLE);
                startActivity(intent);

                break;
            case R.id.mtv_see_all_services:
                break;
            case R.id.mtv_see_full_important_info:
                break;
            default:
                break;
        }
    }
}
