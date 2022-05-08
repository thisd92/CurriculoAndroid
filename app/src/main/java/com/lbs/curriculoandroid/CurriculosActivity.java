package com.lbs.curriculoandroid;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class CurriculosActivity extends AppCompatActivity {

    private ListView cvCurriculo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_curriculos);

        cvCurriculo = findViewById(R.id.cvCurriculos);
    }

    // Menu para cadastrar currículos
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("Cadastrar Currículo");
        menu.add("Editar Currículo");
        menu.add("Excluir Currículo");
        return super.onCreateOptionsMenu(menu);
    }

    // Verifica o item do menu para carregar a activity de cadastro
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if( item.toString().equals("Cadastrar Currículo")){
            carregarForm();
        }else if (item.toString().equals("Editar Currículo")){
            editarForm();
        }else {
            excluirForm();
        }
        return super.onOptionsItemSelected(item);
    }

    // Carrega a activity de cadastro
    public void carregarForm(){
        Intent intent = new Intent(CurriculosActivity.this, MainActivity.class);
        intent.putExtra("acao", "inserir");
        startActivity(intent);
    }

    public void editarForm(){
        Intent intent = new Intent(CurriculosActivity.this, MainActivity.class);
        intent.putExtra("acao", "editar");
        startActivity(intent);
    }

    public void excluirForm(){
        Intent intent = new Intent(CurriculosActivity.this, MainActivity.class);
        intent.putExtra("acao", "excluir");
        startActivity(intent);
    }

    // Carrega os currículos na base ao iniciar
    @Override
    protected void onStart() {
        super.onStart();
        cvCarregar();
    }

    // Método para carregar os currículos
    private void cvCarregar() {
        List<Curriculo> lista = CurriculoDAO.getCurriculos(this);

        if( lista.size() == 0 ){
            Curriculo fake = new Curriculo("Nenhum currículo cadastrado na base de dados",
                    null, null, null, null);
            lista.add( fake );
            cvCurriculo.setEnabled( false );
        }else {
            cvCurriculo.setEnabled( true );
        }

        ArrayAdapter adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, lista);
        cvCurriculo.setAdapter( adapter );
    }

    // Carrega os currículos novamente ao voltar da tela de cadastro
    @Override
    protected void onRestart() {
        super.onRestart();

        // Chama o método para popular a lista e o adapter que será usado
        cvCarregar();
    }

}