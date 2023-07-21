package com.example.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton add_button;
    ImageView empty_ImageView;
    TextView empty_TextView;
    DatabaseHelper databaseHelper;
    ArrayList<String> cat_id, cat_description, cat_fact;
    CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        add_button = findViewById(R.id.add_button);
        empty_ImageView = findViewById(R.id.empty_imageView);
        empty_TextView = findViewById(R.id.no_data_textView);
        databaseHelper = new DatabaseHelper(MainActivity.this);
        cat_id = new ArrayList<>();
        cat_description = new ArrayList<>();
        cat_fact = new ArrayList<>();
        sendData();
        storeDataInArray();
        customAdapter = new CustomAdapter(MainActivity.this, this, cat_id, cat_description, cat_fact);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Stavljamo da je Main aktiviti code 1 koji smo prosledili u customadapter
        if(requestCode == 1){
            recreate();
        }
    }

    public void sendData(){
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });
    }

    public void storeDataInArray(){
        //Proverava u recyclerView da li postoje neki podaci i ubacuje ih
        Cursor cursor = databaseHelper.readAllData();
        //Ukoliko cursor nema podataka onda je 0
        if(cursor.getCount() == 0){
            //Ukoliko nema nikakvih podataka u rv onda stavljamo korpu i natpis no data da se vide u rv
            empty_ImageView.setVisibility(View.VISIBLE);
            empty_TextView.setVisibility(View.VISIBLE);
        }else{
            //moveToNext cita red po red u tabeli entiteta
            while(cursor.moveToNext()){
                cat_id.add(cursor.getString(0));
                cat_description.add(cursor.getString(1));
                cat_fact.add(cursor.getString(2));
            }
            //Ukoliko imamo podatke u rv stavljamo da nestanu imageview korpe i natpis sa rv
            empty_ImageView.setVisibility(View.GONE);
            empty_TextView.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        //Ubacujemo nas resource meni u actionbar preko inflater-a
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.delete_all){
            //Brisemo sve podatke iz recyclerView-a preko ... u actionbar-u
            confirmDialog();
        }
        return super.onOptionsItemSelected(item);
    }
    public void confirmDialog(){
        //Dijalog prozor koji nas pita da li zelimo da izbrisemo sve fact-ove
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete All ?");
        builder.setMessage("Are you sure you want to delete all Data ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Uzimamo metodu iz baze za brisanje fact-a
                DatabaseHelper myDB = new DatabaseHelper(MainActivity.this);
                myDB.deleteAllFacts();
                //Refresuje instancu naseg aktivitija kao i recreate() samo bez crnog skrina kad izbrisemo sve podatke
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
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