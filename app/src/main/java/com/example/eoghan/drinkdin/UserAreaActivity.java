package com.example.eoghan.drinkdin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserAreaActivity extends AppCompatActivity implements View.OnClickListener {


    private FirebaseAuth firebaseAuth;
    private Button buttonLogout;
    private TextView tvWelcome;
    private DatabaseReference databaseReference;

    private Button btnSave;
    private EditText editTextDrink;
    private EditText editTextBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_area);

        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        databaseReference = FirebaseDatabase.getInstance().getReference();


        FirebaseUser user = firebaseAuth.getCurrentUser();

        editTextDrink = (EditText) findViewById(R.id.etDrink);
        editTextBar = (EditText) findViewById(R.id.etBar);
        tvWelcome = (TextView) findViewById(R.id.welcomeTV);
        buttonLogout = (Button) findViewById(R.id.lgoutBtn);
        btnSave = (Button) findViewById(R.id.saveBtn);
        buttonLogout.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        tvWelcome.setText("welcome" +user.getEmail());


    }


    private void saveUserInfo() {
        String name = editTextDrink.getText().toString().trim();
        String add = editTextBar.getText().toString().trim();

        UserInformation userInformation = new UserInformation(name, add);

        FirebaseUser user = firebaseAuth.getCurrentUser();

        databaseReference.child(user.getUid()).setValue(userInformation);
        Toast.makeText(this, "info saved", Toast.LENGTH_LONG).show();


    }
    @Override
    public void onClick(View v) {
        if(v == buttonLogout){
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        if (v == btnSave){
            saveUserInfo();
        }
    }
}