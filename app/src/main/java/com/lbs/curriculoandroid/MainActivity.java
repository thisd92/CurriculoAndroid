package com.lbs.curriculoandroid;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private Spinner spGeneros;
    private Curriculo cv;
    private String acao;
    private String linguagens = "";
    private CheckBox checkJava, checkJS, checkC,checkPython, checkPHP, checkKotlin;
    private EditText etNome, etAge, etLinkedin, etGit;

    private final String[] gender = {"Select your gender...", "Female", "Male",
            "Other", "I don't want to talk"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spGeneros = findViewById(R.id.spGenero);
        etNome = findViewById(R.id.etNome);
        etAge = findViewById(R.id.etIdade);
        etLinkedin = findViewById(R.id.etLinkedin);
        etGit = findViewById(R.id.etGit);
        checkJava = findViewById(R.id.checkJava);
        checkJS = findViewById(R.id.checkJS);
        checkKotlin = findViewById(R.id.checkKotlin);
        checkC = findViewById(R.id.checkC);
        checkPython = findViewById(R.id.checkPython);
        checkPHP = findViewById(R.id.checkPHP);

        loadGender();

        acao = getIntent().getStringExtra("acao");

        if(acao.equals("editar")) {
            //carregarForm();
            cv = new Curriculo();

            cv.setId(getIntent().getExtras().getString("idCurriculo"));
            etNome.setText(getIntent().getExtras().getString("nome"));
            etAge.setText(getIntent().getExtras().getString("idade"));
            etLinkedin.setText(getIntent().getExtras().getString("linkedin"));
            etGit.setText(getIntent().getExtras().getString("github"));

            String genero = cv.setGeneros(getIntent().getExtras().getString("genero"));
            int idGen;
            switch(genero){
                case "Feminino":
                    idGen = 1;
                    break;
                case "Masculino":
                    idGen = 2;
                    break;
                case "Outro":
                    idGen = 3;
                    break;
                case "Prefiro não dizer":
                    idGen = 4;
                    break;
                default:
                    idGen = 0;
            }
            spGeneros.setSelection(idGen);

            String linguagens = cv.setLinguagem(getIntent().getExtras().getString("linguagens"));
            if(linguagens.contains("Java")){
                checkJava.setChecked(true);
            }
            if(linguagens.contains("JS")){
                checkJS.setChecked(true);
            }
            if(linguagens.contains("C#")){
                checkC.setChecked(true);
            }
            if(linguagens.contains("PHP")){
                checkPHP.setChecked(true);
            }
            if(linguagens.contains("Python")){
                checkPython.setChecked(true);
            }
            if(linguagens.contains("Kotlin")){
                checkKotlin.setChecked(true);
            }

        }

        Button btnSalvar = findViewById(R.id.btnSalvar);
        btnSalvar.setOnClickListener(v -> cadastrarCV());

    }

    private void loadGender(){
        spGeneros.setEnabled(true);
        ArrayAdapter adapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1, gender);
        spGeneros.setAdapter(adapter);
    }

    // Método para salvar o currículo
    private void cadastrarCV() {

        etNome = findViewById(R.id.etNome);
        etAge = findViewById(R.id.etIdade);
        etLinkedin = findViewById(R.id.etLinkedin);
        etGit = findViewById(R.id.etGit);

        // Tratamento do Spinner para transformar em texto para salvar no banco
        int posicao = spGeneros.getSelectedItemPosition();
        String genero;
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

        // Tratamento para concatenar o valor dos checkbox e salvar no banco
        checkJava = findViewById(R.id.checkJava);
        checkJS = findViewById(R.id.checkJS);
        checkKotlin = findViewById(R.id.checkKotlin);
        checkC = findViewById(R.id.checkC);
        checkPython = findViewById(R.id.checkPython);
        checkPHP = findViewById(R.id.checkPHP);

        if(checkJava.isChecked()){
            linguagens = "Java-";
        }
        if(checkJS.isChecked()){
            linguagens = linguagens + "JS-";
        }
        if(checkC.isChecked()){
            linguagens = linguagens + "C#-";
        }
        if(checkPython.isChecked()){
            linguagens = linguagens + "Python-";
        }
        if(checkPHP.isChecked()){
            linguagens = linguagens + "PHP-";
        }
        if(checkKotlin.isChecked()){
            linguagens = linguagens + "Kotlin-";
        }

        // Se a ação for inserir, irá gerar um novo currículo
        if (acao.equals("inserir")) {
            cv = new Curriculo();
        }

        cv.setNome(etNome.getText().toString());
        cv.setIdade(etAge.getText().toString());
        cv.setLinkedin(etLinkedin.getText().toString());
        cv.setGithub(etGit.getText().toString());
        cv.setGenero(genero);
        cv.setLinguagens(linguagens);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference();

        // Se a ação passada no Intent for inserir, irá salvar no banco, se não, vai dar update
        if (acao.equals("inserir")) {
        //    CurriculoDAO.inserir(this, cv);
            reference.child("curriculos").push().setValue(cv);
            Toast.makeText(this, "Currículo cadastrado com sucesso!", Toast.LENGTH_LONG).show();
        }else{
        //    CurriculoDAO.editar(this, cv);
            reference.child("curriculos").child(cv.getId()).setValue(cv);
            Toast.makeText(this, "Currículo atualizado com sucesso!", Toast.LENGTH_LONG).show();
        }
        finish();
    }

}