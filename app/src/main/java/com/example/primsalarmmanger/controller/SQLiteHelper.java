package com.example.primsalarmmanger.controller;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SQLiteHelper extends SQLiteOpenHelper {

    private static final String dbName = "Prims";
    private static final int dbVersion = 1;

    public SQLiteHelper(Context context) {
        super(context, dbName, null, dbVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String tbPenanganan = "CREATE TABLE rule(kode CHAR(2), nomor INT PRIMARY KEY);";
        String query = "INSERT INTO rule VALUES('A1',75);";
        db.execSQL(tbPenanganan);
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+dbName);
        onCreate(db);
    }
}
