package com.example.testapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = HomeActivity.class.getSimpleName();

//    TextView textView;

    GridView mGridView;
    ArrayList<MyGridItem> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

//        textView=findViewById(R.id.testtv);
        mGridView = findViewById(R.id.gridview);

        arrayList.add(new MyGridItem("Search Hospitals", R.drawable.ic_search_hospital));
        arrayList.add(new MyGridItem("First Aid Advice", R.drawable.ic_first_aid));
        arrayList.add(new MyGridItem("Nearby Hospitals", R.drawable.ic_nearby_hospitals));
        arrayList.add(new MyGridItem("Emergency Ambulance", R.drawable.ic_ambulance));
        arrayList.add(new MyGridItem("My Appointment", R.drawable.ic_my_appointments));
        arrayList.add(new MyGridItem("Payment History", R.drawable.ic_payment));
        arrayList.add(new MyGridItem("Insurance", R.drawable.ic_insurance));
        arrayList.add(new MyGridItem("Reports", R.drawable.ic_reports));
        arrayList.add(new MyGridItem("Archive", R.drawable.ic_archive_hospitals));
        arrayList.add(new MyGridItem("Feedback", R.drawable.ic_feedback));
        arrayList.add(new MyGridItem("About Us", R.drawable.ic_about));
        arrayList.add(new MyGridItem("More", R.drawable.ic_more));

        MyGridViewAdapter myGridViewAdapter = new MyGridViewAdapter(this, R.layout.griditem_layout, arrayList);
        mGridView.setAdapter(myGridViewAdapter);


        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Log.v(TAG, "onItemClick: i = " + position);
                switch (position) {
                    case 0:
                        startActivity(new Intent(HomeActivity.this, SearchHospitalActivity.class));
                        break;
                    case 1:
                        break;
                    case 2:
                        if (ContextCompat.checkSelfPermission(HomeActivity.this,
                                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            //Show Request Dialog
                            ActivityCompat.shouldShowRequestPermissionRationale(HomeActivity.this,
                                    Manifest.permission.ACCESS_COARSE_LOCATION);
                            //Request Permission
                            ActivityCompat.requestPermissions(HomeActivity.this,
                                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);

                        } else {
                            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                            String city = hereLocation(location.getLatitude(), location.getLongitude());
//                            textView.setText(city);

                            Log.d(TAG, "onItemClick: cityname = " + city);

                            //create connection
                            try {
                                Connection connection = ConnectionHelper.createConnectionWithDB();
                                Statement statement = connection.createStatement();
                                String QUERY1 = "SELECT * FROM hospitals WHERE Description = " + "'"+city+"'";
                                String tempQUERY1 = "SELECT HospitalId FROM hospitals WHERE Description = " + "'"+city+"'";
                                String QUERY2 = "SELECT * FROM hospitalImages WHERE HospitalId in " + "(" + tempQUERY1 + ")";

                                Intent intent = new Intent(HomeActivity.this, SearchResultActivity.class);
                                intent.putExtra("QUERY1",QUERY1);
                                intent.putExtra("QUERY2",QUERY2);
                                startActivity(intent);

                            } catch (SQLException e) {
                                e.printStackTrace();
                            }

                        }
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    case 5:
                        break;
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(HomeActivity.this,
                            Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }

    private String hereLocation(double lat, double lon) {
        String cityName = "";

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses;
        try {
            addresses = geocoder.getFromLocation(lat, lon, 10);
            if (addresses.size() > 0) {
                for (Address adr : addresses) {
                    if (adr.getLocality() != null && adr.getLocality().length() > 0) {
                        cityName = adr.getLocality();
                        break;
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return cityName;
    }
}
