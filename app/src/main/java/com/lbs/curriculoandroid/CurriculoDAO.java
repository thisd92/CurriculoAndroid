package com.lbs.curriculoandroid;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class CurriculoDAO {

    public static void inserir(Context context, Curriculo cv) {
        Banco conn = new Banco(context);
        SQLiteDatabase db = conn.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put("nome", cv.getNome());
        valores.put("idade", cv.getIdade());
        valores.put("genero", cv.getGenero());
        valores.put("linkedin", cv.getLinkedin());
        valores.put("github", cv.getGithub());


        db.insert("curriculos", null, valores);

        db.close();
    }


    public static void editar(Context context, Curriculo cv) {
        Banco conn = new Banco(context);
        SQLiteDatabase db = conn.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put("nome", cv.getNome());
        valores.put("idade", cv.getIdade());
        valores.put("genero", cv.getGenero());
        valores.put("linkedin", cv.getLinkedin());
        valores.put("github", cv.getGithub());

        db.update("curriculos", valores, " id = " + cv.getId(), null);

        db.close();
    }


    public static void excluir(Context context, int idCurriculo) {
        Banco conn = new Banco(context);
        SQLiteDatabase db = conn.getWritableDatabase();

        db.delete("curriculos", " id = " + idCurriculo, null);

        db.close();
    }

    public static List<Curriculo> getCurriculos(Context context) {
        Banco conn = new Banco(context);
        SQLiteDatabase db = conn.getReadableDatabase();

        /*Cursor cursor = db.rawQuery(
                " SELECT cv.id, cv.nome, cv.idade, cv.genero, cv.linkedin, cv.github " +
                        " FROM curriculos cv " +
                        " ORDER BY cv.nome ",
                null );*/

        List<Curriculo> lista = new ArrayList<>();

        /*if( cursor.getCount() > 0 ){
            cursor.moveToFirst();

            do{

                Curriculo cv = new Curriculo();
                cv.setId( cursor.getInt(0));
                cv.setNome( cursor.getString(1));
                cv.setIdade( cursor.getInt(2));
                cv.setGenero( cursor.getString(3));
                cv.setLinkedin( cursor.getString(4));
                cv.setGithub( cursor.getString(5));

                lista.add( cv );
            }while (cursor.moveToNext());
        }*/
        return lista;
    }
}