package com.lbs.curriculoandroid;

import static java.lang.Integer.parseInt;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AlertDialogLayout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class CurriculosActivity extends AppCompatActivity {

    private ListView cvCurriculo;
    private List<Curriculo> listCV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_curriculos);

        cvCurriculo = findViewById(R.id.cvCurriculos);

        cvCarregar();

        /*cvCurriculo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int idCurriculo = listCV.get(position).getId();
                Intent intent = new Intent(CurriculosActivity.this, MainActivity.class);
                intent.putExtra("idCurriculo", idCurriculo);
                intent.putExtra("acao", "editar");
                startActivity(intent);
            }
        });*/

        /*cvCurriculo.setOnItemLongClickListener((parent, view, position, id) -> {
            excluirCV(position);
            return true;
        });*/

    }

    // Menu para cadastrar currículos
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("Cadastrar Currículo");

        return super.onCreateOptionsMenu(menu);
    }

    // Verifica o item do menu para carregar a activity de cadastro
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if( item.toString().equals("Cadastrar Currículo")){
            carregarForm();
        }
        return super.onOptionsItemSelected(item);
    }

    // Carrega a activity de cadastro
    public void carregarForm(){
        Intent intent = new Intent(CurriculosActivity.this, MainActivity.class);
        intent.putExtra("acao", "inserir");
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
            Curriculo fake = new Curriculo("Nenhum currículo cadastrado na base de dados");
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

    // Método para excluir o currículo
    /*public void excluirCV(int posicao){
        final Curriculo cv = listCV.get(posicao);
        android.app.AlertDialog.Builder alerta = new AlertDialog.Builder(this);
        alerta.setTitle("Excluir");
        alerta.setMessage("Confirma exclusão do currículo de " + cv.getNome() + "?");
        alerta.setNeutralButton("Cancelar", null);
        alerta.setPositiveButton("Sim", (dialog, which) -> {
            CurriculoDAO.excluir(CurriculosActivity.this, cv.getId());
            cvCarregar();
        });
        alerta.show();
    }*/


}