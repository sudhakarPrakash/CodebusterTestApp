package com.example.testapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialAutoCompleteTextView;

import java.lang.ref.WeakReference;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    TextInputEditText mName_et, mMobile_et, mEmail_et;
    MaterialAutoCompleteTextView mGender_tv, mCity_tv;
    MaterialButton mRegister_btn;

    private ProgressBar progressBar;
    private static AlertDialog alertDialogProgress;

    //References for DATABASE operations
    static Connection connection = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.app_name);

        mName_et = findViewById(R.id.et_name);
        mMobile_et = findViewById(R.id.et_mobileNo);
        mEmail_et = findViewById(R.id.et_emailId);

        mCity_tv = findViewById(R.id.tv_city);
        mGender_tv = findViewById(R.id.tv_gender);

        mRegister_btn = findViewById(R.id.btn_register);

        //Make Query
        String query = "select CityName from cities";

        //create connnection with the DATABASE
        try {
            connection = ConnectionHelper.createConnectionWithDB();
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
            }else{
                Toast.makeText(this, "connection failure try again", Toast.LENGTH_SHORT).show();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        String[] genderArray = {"Male", "Female"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, genderArray);
        mGender_tv.setThreshold(1);
        mGender_tv.setAdapter(adapter);

        mRegister_btn.setOnClickListener(this);


//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_register:
                if (checkFields()) {
                    AddUser addUser = new AddUser(this);
                    addUser.execute("");
//                    this.finish();   // added to finish this activity after successful registration.
                }

//                User newUser = createNewUser();

                break;
            default:
                break;
        }
    }

    private boolean checkFields() {
        if (mName_et.getText().toString().isEmpty()
                || mCity_tv.getText().toString().isEmpty()
                || mMobile_et.getText().toString().isEmpty()
                || mEmail_et.getText().toString().isEmpty()
                || mGender_tv.getText().toString().isEmpty()) {
            Toast.makeText(this, "fill any empty fields", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(mEmail_et.getText().toString()).matches()) {
            mEmail_et.requestFocus();
            mEmail_et.setError("enter valid email");
            return false;
        } else {
            return true;
        }
    }

    private User createNewUser() {
        User newUser = new User();
        newUser.setName(mName_et.getText().toString())
                .setCity(mCity_tv.getText().toString())
                .setMobileNo(mMobile_et.getText().toString())
                .setEmail(mEmail_et.getText().toString())
                .setGender(mGender_tv.getText().toString());

        return newUser;
    }

    private void openUserProfileActivity() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }




    /* ************************* Inner Class ****************************/
    /* ****************** AddUser extends AsyncTask<> ********************/
    /* ************** to register user on separate thread ****************/

    private static class AddUser extends AsyncTask {
        private WeakReference<RegisterActivity> registerActivity;

        User newUser = null;

        AddUser(RegisterActivity registerActivity) {
            this.registerActivity = new WeakReference<>(registerActivity);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Take reference of RegisterActivity
            RegisterActivity registerActivityContext = registerActivity.get();

            //create newUser using outer Class context
            //outer class context is used because "createNewUser()" is defined in outer class
            //outer class is RegisterActivity
            newUser = registerActivityContext.createNewUser();

            //show registering...  Dialog
            showDialog();


        }

        @Override
        protected Object doInBackground(Object[] objects) {
            //Take reference of RegisterActivity
            RegisterActivity registerActivityContext = registerActivity.get();

                //Insert User into the DATABASE
                try {
                    String query = "Insert into users"
                            + " (UserName,City,MobileNo,EmailId,Gender) "
                            + "values "
                            + "('"
                            + newUser.getName() + "','"
                            + newUser.getCity() + "','"
                            + newUser.getMobileNo() + "','"
                            + newUser.getEmail() + "','"
                            + newUser.getGender() + "')";

                    PreparedStatement preparedStatement = connection.prepareStatement(query);
                    preparedStatement.executeUpdate();
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            return null;
        }


        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);

            alertDialogProgress.dismiss();

            //Take reference of RegisterActivity
            RegisterActivity registerActivityContext = registerActivity.get();
            //open user's profile Activity
            //outer class context is used because "openUserProfileActivity()" is defined in outer class
            //outer class is RegisterActivity
            registerActivityContext.openUserProfileActivity();
        }

        void showDialog(){
            //Take reference of RegisterActivity
            RegisterActivity registerActivityContext = registerActivity.get();

            ProgressBar progressBar;

            AlertDialog.Builder builder = new AlertDialog.Builder(registerActivityContext);
            View view = LayoutInflater.from(registerActivityContext).inflate(R.layout.layout_pb_circular, null);
//            View view = getLayoutInflater().inflate(R.layout.layout_pb_circular, null);
            TextView textView = view.findViewById(R.id.tv_processing);
            String logging = "Registering...";
            textView.setText(logging);
            progressBar = view.findViewById(R.id.pb);
            progressBar.setVisibility(View.VISIBLE);
            builder.setView(view);

            alertDialogProgress = builder.create();
            alertDialogProgress.setCancelable(false);
            alertDialogProgress.setCanceledOnTouchOutside(false);
            alertDialogProgress.setView(view, 0, 30, 0, 30);
            alertDialogProgress.show();
        }
    }

}
