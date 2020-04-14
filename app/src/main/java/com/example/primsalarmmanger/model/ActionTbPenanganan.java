package com.example.primsalarmmanger.model;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class ActionTbPenanganan {

    SQLiteDatabase db;

    public ActionTbPenanganan(SQLiteDatabase db) {
        this.db = db;
    }

    public void updateData(String kode, String nomor) {
        String query = "UPDATE rule SET kode = '" + kode + "' WHERE nomor = " + nomor + ";";
        db.execSQL(query);
    }

    public String select(String nomor){
        String query = "SELECT *FROM rule WHERE nomor = "+nomor+";";
        Cursor cursor = db.rawQuery(query, null);
        String kode = null;
        if (cursor.moveToNext()){
            kode = cursor.getString(0);
        }
        return kode;
    }

}
