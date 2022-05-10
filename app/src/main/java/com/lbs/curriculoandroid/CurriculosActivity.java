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
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class CurriculosActivity extends AppCompatActivity {

    private ListView lvCurriculos;
    private List<Curriculo> listCV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_curriculos);

        lvCurriculos = findViewById(R.id.cvCurriculos);

        cvCarregar();

        configLV();

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
        listCV = CurriculoDAO.getCurriculos(this);
        if(listCV.size() == 0){
            Toast.makeText(this,"Nenhum curriculo cadastrado", Toast.LENGTH_LONG).show();
            lvCurriculos.setEnabled(false);
        }else{
            lvCurriculos.setEnabled(true);
        }

        ArrayAdapter adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, listCV);
        lvCurriculos.setAdapter( adapter );
    }

    // Carrega os currículos novamente ao voltar da tela de cadastro
    @Override
    protected void onRestart() {
        super.onRestart();

        // Chama o método para popular a lista e o adapter que será usado
        cvCarregar();
    }

    // Método para configurar o ListView
    private void configLV(){
        lvCurriculos.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Curriculo cvSelected = listCV.get(position);
                Intent intent = new Intent(CurriculosActivity.this, MainActivity.class);
                intent.putExtra("acao", "editar");
                intent.putExtra("idCurriculo", cvSelected.id);
                startActivity(intent);
            }
        });

        lvCurriculos.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Curriculo cvSelected = listCV.get(position);
                excluirCV( cvSelected );
                return true;
            }
        });
    }

    // Método para excluir o currículo
    public void excluirCV(Curriculo curriculo){
        AlertDialog.Builder alerta = new AlertDialog.Builder(this);
        alerta.setIcon(android.R.drawable.ic_input_delete);
        alerta.setTitle("Excluir");
        alerta.setMessage("Confirma exclusão do currículo de " + curriculo.nome + "?");
        alerta.setNeutralButton("Cancelar", null);
        alerta.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                CurriculoDAO.excluir(CurriculosActivity.this, curriculo.id);
                cvCarregar();
                Toast.makeText(CurriculosActivity.this, "Currículo excluído!",
                        Toast.LENGTH_LONG).show();
            }
        });
        alerta.show();
    }


}