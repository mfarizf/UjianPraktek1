package com.example.ujianpraktek;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.nfc.Tag;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper{
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "Notes";
    private static final String TABLE_NAME = "tbl_notes";
    private static final String KEY_JUDUL = "judul";
    private static final String KEY_DESKRIPSI = "deskripsi";
    private static final String KEY_TANGGAL = "tanggal";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createUserTable = "CREATE TABLE "+TABLE_NAME+"("+KEY_JUDUL+" TEXT PRIMARY KEY,"+KEY_TANGGAL+" TEXT,"+KEY_DESKRIPSI+" TEXT"+")";
        System.out.println(createUserTable);
        sqLiteDatabase.execSQL(createUserTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String sql = ("drop table if exists " +TABLE_NAME);
        sqLiteDatabase.execSQL(sql);
        onCreate(sqLiteDatabase);
    }

    public void insert(Notes notes){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_JUDUL,notes.getJudul());
        values.put(KEY_DESKRIPSI,notes.getDeskripsi());
        values.put(KEY_TANGGAL,notes.getTanggal());

        db.insert(TABLE_NAME,null,values);
    }

    public List<Notes> selectNotes(){
        ArrayList<Notes> noteList = new ArrayList<Notes>();
        SQLiteDatabase db = getWritableDatabase();
        String[] columns ={KEY_JUDUL,KEY_TANGGAL,KEY_DESKRIPSI};

        Cursor c =db.query(TABLE_NAME,columns,null,null,null,null,null);

        while (c.moveToNext()){
            String judul=c.getString(0);
            String tanggal = c.getString(1);
            String deskripsi = c.getString(2);

            Notes notes = new Notes();
            notes.setJudul(judul);
            notes.setTanggal(tanggal);
            notes.setDeskripsi(deskripsi);
            noteList.add(notes);
        }
        return noteList;
    }

    public void delete(String judul){
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = KEY_JUDUL+"='"+judul+"'";
        db.delete(TABLE_NAME,whereClause,null);
    }

    public void update(Notes notes){
        SQLiteDatabase db = getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_JUDUL,notes.getJudul());
        values.put(KEY_DESKRIPSI,notes.getDeskripsi());
        values.put(KEY_TANGGAL,notes.getTanggal());
        String whereClause = KEY_JUDUL+"='"+notes.getJudul()+"'";
        db.update(TABLE_NAME,values,whereClause,null);
    }
}
