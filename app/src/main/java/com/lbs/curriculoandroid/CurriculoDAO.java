package com.lbs.curriculoandroid;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class CurriculoDAO {

    public static void inserir(Context context, Curriculo curriculo){
        Banco conn = new Banco(context);
        SQLiteDatabase db = conn.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put("titulo", curriculo.getTitulo() );
        valores.put("autor", curriculo.getAutor() );
        valores.put("codGenero", curriculo.getGenero().getId() );

        db.insert("livro", null, valores);

        db.close();
    }


    public static void editar(Context context, Curriculo curriculo){
        Banco conn = new Banco(context);
        SQLiteDatabase db = conn.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put("titulo", curriculo.getTitulo() );
        valores.put("autor", curriculo.getAutor() );
        valores.put("codGenero", curriculo.getGenero().getId() );

        db.update("livro",  valores, " id = " + curriculo.getId(), null);

        db.close();
    }


    public static void excluir(Context context, int idLivro){
        Banco conn = new Banco(context);
        SQLiteDatabase db = conn.getWritableDatabase();

        db.delete("livro", " id = " + idLivro, null);

        db.close();
    }



    public static List<Curriculo> getLivros(Context context){
        Banco conn = new Banco(context);
        SQLiteDatabase db = conn.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                " SELECT l.id, l.titulo, l.autor, g.id, g.nome " +
                        " FROM livro l " +
                        " INNER JOIN genero g ON g.id = l.codGenero " +
                        " ORDER BY l.titulo ",
                null );

        List<Curriculo> lista = new ArrayList<>();

        if( cursor.getCount() > 0 ){
            cursor.moveToFirst();

            do{
                Genero g = new Genero();
                g.setId(  cursor.getInt( 3 ) );
                g.setNome(  cursor.getString( 4 ) );

                Curriculo l = new Curriculo();
                l.setId( cursor.getInt(0));
                l.setTitulo( cursor.getString(1));
                l.setAutor( cursor.getString(2));
                l.setGenero( g );

                lista.add( l );
            }while (cursor.moveToNext());
        }
        return lista;
    }

}
