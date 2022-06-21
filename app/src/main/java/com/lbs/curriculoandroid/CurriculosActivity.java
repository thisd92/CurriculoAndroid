package com.lbs.curriculoandroid;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;

public class CurriculosActivity extends AppCompatActivity {

    private ListView lvCurriculos;

    private final List<Curriculo> lista = new ArrayList<>();
    private ArrayAdapter adapter;

    FirebaseDatabase database;
    DatabaseReference reference;

    ChildEventListener childEventListener;
    Query query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_curriculos);

        lvCurriculos = findViewById(R.id.cvCurriculos);

        lvCurriculos.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Curriculo cvSelected = lista.get(position);
                AlertDialog.Builder alerta = new AlertDialog.Builder(CurriculosActivity.this);
                alerta.setIcon(android.R.drawable.ic_input_delete);
                alerta.setTitle("Excluir");
                alerta.setMessage("Confirma exclusão do currículo de " + cvSelected.getNome() + "?");
                alerta.setNeutralButton("Cancelar", null);
                alerta.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        reference.child("curriculos").child(cvSelected.getId()).removeValue(null);
                        cvCarregar();
                        Toast.makeText(CurriculosActivity.this, "Currículo excluído!",
                                Toast.LENGTH_LONG).show();
                    }
                });
                alerta.show();
                return true;
            }
        });

        // Implementa Swipe para esquerda para adicionar novo currículo
        lvCurriculos.setOnTouchListener(new OnSwipeTouchListener(this){
            @Override
            public void onSwipeLeft(){
                super.onSwipeLeft();
                carregarForm();
            }

            @Override
            public void onSwipeBottom() {
                super.onSwipeBottom();
                Intent intent = new Intent(CurriculosActivity.this, CurriculosActivity.class);
                intent.putExtra("acao", "excluir");
                finish();
                startActivity(intent);
            }
        });

        cvCarregar();

        database = FirebaseDatabase.getInstance();
        reference = database.getReference();

        // ao click, abre nova activity para alterar cadastro
        lvCurriculos.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Curriculo cvSelected = lista.get(position);
                Intent intent = new Intent(CurriculosActivity.this, MainActivity.class);
                intent.putExtra("acao", "editar");
                intent.putExtra("idCurriculo", cvSelected.getId());
                intent.putExtra("nome", cvSelected.getNome());
                intent.putExtra("idade", cvSelected.getIdade());
                intent.putExtra("genero", cvSelected.getGenero());
                intent.putExtra("github", cvSelected.getGithub());
                intent.putExtra("linkedin", cvSelected.getLinkedin());
                intent.putExtra("linguagens", cvSelected.getLinguagens());
                startActivity(intent);
            }
        });
    }

    // Menu para cadastrar currículos
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("Cadastrar Currículo");
        menu.add("Filtrar por Nome");

        return super.onCreateOptionsMenu(menu);
    }

    // Verifica o item do menu para carregar a activity de cadastro
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if( item.toString().equals("Cadastrar Currículo")){
            carregarForm();
        }else{
            filter();
        }
        return super.onOptionsItemSelected(item);
    }

    // Carrega a activity de cadastro
    public void carregarForm(){
        Intent intent = new Intent(CurriculosActivity.this, MainActivity.class);
        intent.putExtra("acao", "inserir");
        startActivity(intent);
    }

    // onStart, carrega os currículos na base do Firebase ao iniciar
    @Override
    protected void onStart() {
        super.onStart();
        cvCarregar();

        lista.clear();
        query = reference.child("curriculos");

        childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Curriculo cv = new Curriculo();
                cv.setId(snapshot.getKey());
                cv.setNome(snapshot.child("nome").getValue(String.class));
                cv.setIdade(snapshot.child("idade").getValue(String.class));
                cv.setGenero(snapshot.child("genero").getValue(String.class));
                cv.setGithub(snapshot.child("github").getValue(String.class));
                cv.setLinkedin(snapshot.child("linkedin").getValue(String.class));
                cv.setLinguagens(snapshot.child("linguagens").getValue(String.class));
                lista.add(cv);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String idCurriculo = snapshot.getKey();
                for(Curriculo cv : lista){
                    if(cv.getId().equals(idCurriculo)) {
                        cv.setNome(snapshot.child("nome").getValue(String.class));
                        cv.setIdade(snapshot.child("idade").getValue(String.class));
                        cv.setGenero(snapshot.child("genero").getValue(String.class));
                        cv.setGithub(snapshot.child("github").getValue(String.class));
                        cv.setLinkedin(snapshot.child("linkedin").getValue(String.class));
                        cv.setLinguagens(snapshot.child("linguagens").getValue(String.class));
                    }
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                String idCurriculo = snapshot.getKey();
                for(Curriculo cv : lista){
                    if(cv.getId().equals(idCurriculo)){
                        lista.remove(cv);
                        break;
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        query.addChildEventListener(childEventListener);
    }

    // Método para carregar os currículos
    private void cvCarregar() {

        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, lista);
        lvCurriculos.setAdapter( adapter );
    }

    // ao parar o aplicativo, o evento para de ser escutado
    @Override
    protected void onStop() {
        super.onStop();
        query.removeEventListener(childEventListener);
    }

    private void filter() {
        AlertDialog.Builder alerta = new AlertDialog.Builder(this);
        alerta.setTitle("Filtro por Nome");
        alerta.setIcon(android.R.drawable.ic_input_add);
        EditText etNome = new EditText(this);
        etNome.setHint("Digite aqui o nome...");
        alerta.setView(etNome);
        alerta.setNeutralButton("Cancelar", null);

        alerta.setPositiveButton("Filtrar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String nome = etNome.getText().toString();
                if (!nome.isEmpty()) {
                    loadFilter(nome);
                }
            }
        });
        alerta.show();
    }

    private void loadFilter(String nome){
        lista.clear();
        Query curriculos = reference.child("curriculos").orderByChild("nome").startAt(nome);

        childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Curriculo cv = new Curriculo();
                cv.setId(snapshot.getKey());
                cv.setNome(snapshot.child("nome").getValue(String.class));
                cv.setIdade(snapshot.child("idade").getValue(String.class));
                cv.setGenero(snapshot.child("genero").getValue(String.class));
                cv.setGithub(snapshot.child("github").getValue(String.class));
                cv.setLinkedin(snapshot.child("linkedin").getValue(String.class));
                cv.setLinguagens(snapshot.child("linguagens").getValue(String.class));
                lista.add(cv);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String idCurriculo = snapshot.getKey();
                for(Curriculo cv : lista){
                    if(cv.getId().equals(idCurriculo)) {
                        cv.setNome(snapshot.child("nome").getValue(String.class));
                        cv.setIdade(snapshot.child("idade").getValue(String.class));
                        cv.setGenero(snapshot.child("genero").getValue(String.class));
                        cv.setGithub(snapshot.child("github").getValue(String.class));
                        cv.setLinkedin(snapshot.child("linkedin").getValue(String.class));
                        cv.setLinguagens(snapshot.child("linguagens").getValue(String.class));
                    }
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                String idCurriculo = snapshot.getKey();
                for(Curriculo cv : lista){
                    if(cv.getId().equals(idCurriculo)){
                        lista.remove(cv);
                        break;
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        curriculos.addChildEventListener(childEventListener);
    }

}