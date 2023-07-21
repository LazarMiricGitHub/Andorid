package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;


public class CatActivity extends AppCompatActivity {

    private Button button_fact, button_add_fact;
    private TextView labelCat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cat);
        button_fact = findViewById(R.id.buttonFact);
        button_fact = findViewById(R.id.buttonFact);
        button_add_fact = findViewById(R.id.buttonAddFact);
        labelCat = findViewById(R.id.textViewDescription);

        button_fact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Api.getJSON("https://catfact.ninja/fact", new ReadDataHandler(){
                    public void handleMessage(Message msg){
                        String reply = getJson();
                        try {
                            JSONObject array = new JSONObject(reply);
                            Cat cat = Cat.parseJSONObject(array);
                            labelCat.setText("Fact about cats\n\n");
                            String display = cat.getDescription() + " " + "fact #" + cat.getNumber() + "\n";
                            labelCat.append(display);

                        } catch(Exception e){

                        }
                    }
                });
                labelCat.setText("Loading...");
            }
        });
        button_add_fact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CatActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
