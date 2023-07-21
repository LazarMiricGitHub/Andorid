package com.example.myapplication;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class UpdateActivity extends AppCompatActivity {

    EditText cat_description, cat_fact;
    Button update_button, delete_button;
    String id, description, fact;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        cat_description = findViewById(R.id.editTextCatDescription2);
        cat_fact = findViewById(R.id.editTextCatFact2);
        update_button = findViewById(R.id.update_button);
        delete_button = findViewById(R.id.delete_button);

        //Prvo pozivamo element iz recyclerview-a
        getAndSetIntentData();
        //Na actionbar skroz na gornjoj liniji umesto imena projekta stavljamo vrednost da pise
        ActionBar ab = getSupportActionBar();
        if(ab != null){
            ab.setTitle(description);
        }

        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper myDB = new DatabaseHelper(UpdateActivity.this);
                description = cat_description.getText().toString().trim();
                fact = cat_fact.getText().toString().trim();
                //zatim radimo update nakom poziva elementa iz recyclerview-a
                myDB.updateFact(id, description, fact);
            }
        });

        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDialog();
            }
        });
    }
     public void getAndSetIntentData(){
        if(getIntent().hasExtra("id") && getIntent().hasExtra("description") && getIntent().hasExtra("fact")){
            //Uzimamo vrednosti iz Intenta od Main-a
            id = getIntent().getStringExtra("id");
            description = getIntent().getStringExtra("description");
            fact = getIntent().getStringExtra("fact");

            //Podesavamo Intent podatke
            cat_description.setText(description);
            cat_fact.setText(fact);
        }else{
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }
    }
    public void confirmDialog(){
        //Dijalog prozor koji nas pita da li zelimo da izbrisemo fact
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + description + " ?");
        builder.setMessage("Are you sure you want to delete " + description + " ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Uzimamo metodu iz baze za brisanje filma
                DatabaseHelper myDB = new DatabaseHelper(UpdateActivity.this);
                myDB.deleteFact(id);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();
    }
}