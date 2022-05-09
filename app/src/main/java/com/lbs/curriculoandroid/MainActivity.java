package com.lbs.curriculoandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Spinner spGeneros;
    private Button btnSalvar;
    private Curriculo cv;
    private String acao;
    private String linguagens = "";
    private CheckBox checkJava, checkJS, checkC,checkPython, checkPHP, checkKotlin;

    private String[] gender = {"Select your gender...", "Female", "Male",
            "Other", "I don't want to talk"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spGeneros = findViewById(R.id.spGenero);

        loadGender();

        acao = getIntent().getStringExtra("acao");

        if(acao.equals("editar")) {
            carregarForm();
        }

        btnSalvar = findViewById(R.id.btnSalvar);
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cadastrarCV();
            }
        });

    }

    private void loadGender(){
        spGeneros.setEnabled(true);
        String[] genero = gender;
        ArrayAdapter adapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1, gender);
        spGeneros.setAdapter(adapter);
    }

    private void cadastrarCV() {
        EditText etNome = new EditText(this);
        EditText etAge = new EditText(this);
        EditText etLinkedin = new EditText(this);
        EditText etGit = new EditText(this);

        etNome = findViewById(R.id.etNome);
        etAge = findViewById(R.id.etIdade);
        etLinkedin = findViewById(R.id.etLinkedin);
        etGit = findViewById(R.id.etGit);

        int posicao = spGeneros.getSelectedItemPosition();
        String genero = new String();
        switch(posicao){
            case 1:
                genero = "Feminino";
                break;
            case 2 :
                genero = "Masculino";
                break;
            case 3:
                genero = "Outro";
                break;
            case 4:
                genero = "Prefiro não dizer";
                break;
            default:
                genero = "";
        }

        String language = new String();
        checkJava = findViewById(R.id.checkJava);
        checkJS = findViewById(R.id.checkJS);
        checkKotlin = findViewById(R.id.checkKotlin);
        checkC = findViewById(R.id.checkC);
        checkPython = findViewById(R.id.checkPython);
        checkPHP = findViewById(R.id.checkPHP);

        if(checkJava.isChecked()){
            linguagens = "Java - ";
        };
        if(checkJS.isChecked()){
            linguagens = linguagens + "JS -";
        };
        if(checkC.isChecked()){
            linguagens = linguagens + "C# -";
        };
        if(checkPython.isChecked()){
            linguagens = linguagens + "Python -";
        };
        if(checkPHP.isChecked()){
            linguagens = linguagens + "PHP -";
        };
        if(checkKotlin.isChecked()){
            linguagens = linguagens + "Kotlin -";
        };

        language = linguagens;

        if (acao.equals("inserir")) {
            cv = new Curriculo();
        }

        cv.setNome(etNome.getText().toString());
        cv.setIdade(etAge.getText().toString());
        cv.setLinkedin(etLinkedin.getText().toString());
        cv.setGithub(etGit.getText().toString());
        cv.setGenero(genero);
        cv.setLinguagens(language);

        if (acao.equals("inserir")) {
            CurriculoDAO.inserir(this, cv);
            Toast.makeText(this, "Currículo cadastrado com sucesso!", Toast.LENGTH_LONG).show();
        }else{
            CurriculoDAO.editar(this, cv);
            Toast.makeText(this, "Currículo atualizado com sucesso!", Toast.LENGTH_LONG).show();
        }
        finish();
    }

    private void carregarForm(){
        int id = getIntent().getIntExtra("idCurriculo",0);
        cv = CurriculoDAO.getCurriculoByID(this, id);

        EditText etNome = new EditText(this);
        EditText etAge = new EditText(this);
        EditText etLinkedin = new EditText(this);
        EditText etGit = new EditText(this);

        etNome.setText(cv.getNome());
        etAge.setText(cv.getIdade());
        etLinkedin.setText(cv.getLinkedin());
        etGit.setText(cv.getGithub());
        etNome.setText(cv.getNome());

        String[] generos = gender;
        for (int i = 1; i < generos.length; i++){
            if(cv.getGenero().equals(generos[i])){
                spGeneros.setSelection(i);

                break;
            }
        }


    }

}