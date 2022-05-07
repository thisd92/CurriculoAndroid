package com.lbs.curriculoandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Spinner spGeneros;

    private String[] gender = {"Select your gender...", "Female", "Male",
            "Other", "I don't want to talk"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spGeneros = findViewById(R.id.spGenero);

        loadGender();

    }

    private void loadGender(){
        spGeneros.setEnabled(true);
        String[] genero = gender;
        ArrayAdapter adapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1, gender);
        spGeneros.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("Cadastrar Gênero...");
        return super.onCreateOptionsMenu(menu);
    }



}