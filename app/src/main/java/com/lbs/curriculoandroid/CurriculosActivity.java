package com.lbs.curriculoandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

public class CurriculosActivity extends AppCompatActivity {

    private ListView lvLivros;
    private Button btnAdicionar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_curriculos);

        lvLivros = findViewById(R.id.lvLivros);
        btnAdicionar = findViewById(R.id.btnAdicionar);

        btnAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CurriculosActivity.this, MainActivity.class);
                intent.putExtra("acao", "inserir");
                startActivity( intent );
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        carregarLivros();
    }

    private void carregarLivros(){
        List<Curriculo> lista = CurriculoDAO.getLivros(this);

        if( lista.size() == 0 ){
            Curriculo fake = new Curriculo("Nenhum livro cadastrado", "...", null);
            lista.add( fake );
            lvLivros.setEnabled( false );
        }else {
            lvLivros.setEnabled( true );
        }

        ArrayAdapter adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, lista);
        lvLivros.setAdapter( adapter );
    }
}