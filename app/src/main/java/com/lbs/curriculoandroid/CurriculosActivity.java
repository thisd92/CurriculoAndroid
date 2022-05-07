package com.lbs.curriculoandroid;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class CurriculosActivity extends AppCompatActivity {

    private ListView cvCurriculo;
    private Button btnCadastrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_curriculos);

        cvCurriculo = findViewById(R.id.cvCurriculos);
        btnCadastrar = findViewById(R.id.btnCadastrar);


        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CurriculosActivity.this, MainActivity.class);
                intent.putExtra("acao", "inserir");
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        cvCarregar();
    }

    private void cvCarregar() {
        List<Curriculo> lista = CurriculoDAO.getCurriculos(this);

        if( lista.size() == 0 ){
            Curriculo fake = new Curriculo("Nenhum currículo cadastrado na base de dados", null,
                    null, null, null);
            lista.add( fake );
            cvCurriculo.setEnabled( false );
        }else {
            cvCurriculo.setEnabled( true );
        }

        ArrayAdapter adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, lista);
        cvCurriculo.setAdapter( adapter );
    }

}