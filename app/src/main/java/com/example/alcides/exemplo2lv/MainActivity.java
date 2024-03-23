package com.example.alcides.exemplo2lv;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    Aluno aluno;
    DecimalFormat formato = new DecimalFormat("0.00");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_list) {
            Intent i = new Intent(getApplicationContext(),ListaDeAlunos.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void gravarAluno(View v){
        aluno = new Aluno();
        aluno.setRgm(((EditText) findViewById(R.id.edt_rgm)).getText().toString());
        aluno.setNome(((EditText) findViewById(R.id.edt_nome)).getText().toString());
        aluno.setNota1(Float.parseFloat(((EditText) findViewById(R.id.edt_nota1)).getText().toString()));
        aluno.setNota2(Float.parseFloat(((EditText) findViewById(R.id.edt_nota2)).getText().toString()));

        if (new AlunoDAO(getApplicationContext()).insert(aluno))
            ((TextView) findViewById(R.id.resp)).setText("O aluno foi cadastrado.");
        else
            ((TextView) findViewById(R.id.resp)).setText("Por favor, verifique os dados.");
        limparCampos();
    }

    public void consultarAluno(View v){
        aluno = new AlunoDAO(getApplicationContext()).select(((EditText) findViewById(R.id.edt_rgm)).getText().toString());
        if (aluno != null) {
            ((EditText) findViewById(R.id.edt_nome)).setText(aluno.getNome());
            ((EditText) findViewById(R.id.edt_nota1)).setText(aluno.getNota1() + "");
            ((EditText) findViewById(R.id.edt_nota2)).setText(aluno.getNota2() + "");
        } else
            ((TextView) findViewById(R.id.resp)).setText("Aluno não encontrado pelo RGM.");
    }

    public void alterarAluno(View v){
        aluno = new Aluno();
        aluno.setRgm(((EditText) findViewById(R.id.edt_rgm)).getText().toString());
        aluno.setNome(((EditText) findViewById(R.id.edt_nome)).getText().toString());
        aluno.setNota1(Float.parseFloat(((EditText) findViewById(R.id.edt_nota1)).getText().toString()));
        aluno.setNota2(Float.parseFloat(((EditText) findViewById(R.id.edt_nota2)).getText().toString()));

        if (new AlunoDAO(getApplicationContext()).update(aluno))
            ((TextView) findViewById(R.id.resp)).setText("O aluno foi alterado.");
        else
            ((TextView) findViewById(R.id.resp)).setText("Por favor, verifique os dados.");
        limparCampos();
    }

    public void excluirAluno(View v){
        if (new AlunoDAO(getApplicationContext()).delete(((EditText) findViewById(R.id.edt_rgm)).getText().toString()))
            ((TextView) findViewById(R.id.resp)).setText("O aluno foi eliminado.");
        else
            ((TextView) findViewById(R.id.resp)).setText("Por favor, verifique o RGM do aluno a eliminar.");
        limparCampos();
    }

    public void listarTodos(View v) {
        String todos = "\n" + (new AlunoDAO(getApplicationContext()).listarTodos());
        ((TextView) findViewById(R.id.resp)).setText(todos);
    }

    public void limparCampos(){
        ((EditText) findViewById(R.id.edt_rgm)).setText("");
        ((EditText) findViewById(R.id.edt_nome)).setText("");
        ((EditText) findViewById(R.id.edt_nota1)).setText("");
        ((EditText) findViewById(R.id.edt_nota2)).setText("");
    }
}
