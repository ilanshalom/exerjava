package com.example.alcides.exemplo2lv;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class HelperDB extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    public static final String TABELA = "alunos";    //nome da tabela
    private static final String DATABASE_NAME = "escola";   //nome do BD

    private static final String TABLE_CREATE = "create table " +
                                            TABELA +
                            " (rgm varchar(10) PRIMARY KEY,nome varchar(100), "+
                            " nota1 float, nota2 float);";

    HelperDB(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
        // TODO Auto-generated method stub
    }
}