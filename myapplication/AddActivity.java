package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class AddActivity extends AppCompatActivity {

    EditText editTextCatDescription, editTextCatFact;
    Button add_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        editTextCatDescription = findViewById(R.id.editTextCatDescription);
        editTextCatFact = findViewById(R.id.editTextCatFact);
        add_button = findViewById(R.id.add_button);

        sendData();
    }

    public void sendData(){
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper myDB = new DatabaseHelper(AddActivity.this);
                myDB.addFact(editTextCatDescription.getText().toString().trim(),
                              editTextCatFact.getText().toString().trim());
            }
        });
    }
}