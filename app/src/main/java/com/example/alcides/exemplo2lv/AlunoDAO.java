package com.example.alcides.exemplo2lv;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class AlunoDAO {
    private Context ctx;

    public AlunoDAO(Context ctx) {
        this.ctx = ctx;
    }

    public boolean insert(Aluno aluno) {
        SQLiteDatabase db = new HelperDB(ctx).getWritableDatabase(); //conecta e abre o banco para gravação
        //insere alunos no ContentValues
        ContentValues values = new ContentValues();
        values.put("rgm", aluno.getRgm());
        values.put("nome",aluno.getNome());
        values.put("nota1", aluno.getNota1());
        values.put("nota2", aluno.getNota2());
        //insert retorna o ID da linha inserido ou -1 para erro
        //se der erro retorna FALSE senão TRUE
        return (db.insert(HelperDB.TABELA, null, values) > 0);
    }

    public boolean delete(String rgm) {
        SQLiteDatabase db = new HelperDB(ctx).getWritableDatabase(); //conecta e abre o banco para gravação
        String[] whereArgs = new String[]{rgm};
        //delete retorna o num de linhas deletadas ou 0 para nenhuma
        //se der erro retorna FALSE senão TRUE
        return (db.delete(HelperDB.TABELA, "rgm=?", whereArgs) > 0);
    }

    public boolean update(Aluno aluno) {
        SQLiteDatabase db = new HelperDB(ctx).getWritableDatabase(); //conecta e abre o banco para gravação
        ContentValues values = new ContentValues();
        values.put("rgm", aluno.getRgm());
        values.put("nome",aluno.getNome());
        values.put("nota1", aluno.getNota1());
        values.put("nota2", aluno.getNota2());
        String[] whereArgs = new String[]{aluno.getRgm()};
        return (db.update(HelperDB.TABELA,values , "rgm=?" ,whereArgs) > 0);
    }

    public Aluno select(String rgm) {
        SQLiteDatabase db = new HelperDB(ctx).getReadableDatabase(); //conecta e abre o banco para leitura
        String[] selectionArgs = new String[]{rgm};
        Aluno aluno=new Aluno();
        Cursor cursor = db.query(HelperDB.TABELA, null, "rgm= ?", selectionArgs, null, null, null);
        if (cursor.getCount()>0)
        {
            cursor.moveToNext();
            aluno.setRgm(rgm);
            //exemplos com os índices dos campos
            aluno.setNome(cursor.getString(1));
            aluno.setNota1(cursor.getFloat(2));
            aluno.setNota2(cursor.getFloat(3));
            return aluno;
        }
        return null;
    }

    public List<Aluno> listarAlunos() {
        SQLiteDatabase db = new HelperDB(ctx).getReadableDatabase(); //conecta e abre o banco para leitura
        List<Aluno> lista = new ArrayList<Aluno>();

        Cursor cursor = db.query(HelperDB.TABELA, null, null, null, null, null, "rgm");
        while (cursor.moveToNext()) {
            Aluno aluno = new Aluno();
            //exemplos capturando o indice dos campos a partir do nome
            aluno.setRgm(cursor.getString(cursor.getColumnIndex("rgm")));
            aluno.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            aluno.setNota1(cursor.getFloat(cursor.getColumnIndex("nota1")));
            aluno.setNota2(cursor.getFloat(cursor.getColumnIndex("nota2")));
            //adiciona o aluno na lista
            lista.add(aluno);
        }
        return lista;
    }

    public String listarTodos() {
        String todos = "";
        SQLiteDatabase db = new HelperDB(ctx).getReadableDatabase(); //conecta e abre o banco para leitura
        Cursor cursor = db.query(HelperDB.TABELA, null, null, null, null, null, "rgm");
        while (cursor.moveToNext()) {
            todos += cursor.getString(cursor.getColumnIndex("rgm")) + ", " +
                     cursor.getString(cursor.getColumnIndex("nome")) + ", " +
                     cursor.getFloat(cursor.getColumnIndex("nota1")) + ", " +
                     cursor.getFloat(cursor.getColumnIndex("nota2")) + "\n";
        }
        return todos;
    }

}
