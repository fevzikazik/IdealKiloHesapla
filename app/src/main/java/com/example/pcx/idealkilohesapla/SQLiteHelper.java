package com.example.pcx.idealkilohesapla;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SQLiteHelper extends SQLiteOpenHelper {

    private static final int database_VERSION = 1;
    private static final String database_NAME = "OlcumDB";
    private static final String tbl_Olcumler = "olcumler";
    private static final String olcum_ID = "id";
    private static final String boy = "boy";
    private static final String kilo = "kilo";
    private static final String cinsiyet = "cinsiyet";
    private static final String yagsizAgirlik = "yagsizAgirlik";
    private static final String kiloIdeal = "kiloIdeal";
    private static final String vucutYuzeyAlani = "vucutYuzeyAlani";
    private static final String vucutKitleIndexi = "vucutKitleIndexi";
    private static final String DURUM = "DURUM";
    private static final String olcumKayitTarihi = "olcumKayitTarihi";

    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS "
            + tbl_Olcumler + " ("
            + olcum_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + boy + " INTEGER, "
            + kilo + "  INTEGER, "
            + cinsiyet + " TEXT, "
            + yagsizAgirlik + " INTEGER, "
            + kiloIdeal + " INTEGER, "
            + vucutYuzeyAlani + " TEXT, "
            + vucutKitleIndexi + " TEXT, "
            + DURUM + " TEXT, "
            + olcumKayitTarihi + " TEXT )";

    public SQLiteHelper(Context context) {
        //super(context, database_NAME, null, database_VERSION);
        super(context, String.valueOf(new File(Environment.getExternalStorageDirectory(),database_NAME)), null, database_VERSION);
        //super(context, String.valueOf(context.getDatabasePath(database_NAME)), null, database_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void OlcumEkle(Olcum olcum){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues degerler =  new ContentValues();
        degerler.put(boy,olcum.getBoy());
        degerler.put(kilo,olcum.getKilo());
        degerler.put(cinsiyet,olcum.getCinsiyet());
        degerler.put(yagsizAgirlik,olcum.getYagsizAgirlik());
        degerler.put(kiloIdeal,olcum.getKiloIdeal());
        degerler.put(vucutYuzeyAlani,olcum.getVucutYuzeyAlani());
        degerler.put(vucutKitleIndexi,olcum.getVucutKitleIndexi());
        degerler.put(DURUM,olcum.getDURUM());
        degerler.put(olcumKayitTarihi,olcum.getOlcumTarihi());

        db.insert(tbl_Olcumler,null,degerler);
        db.close();
    }

    public List<Olcum> OlcumleriGetir(){
        List<Olcum> olcumler = new ArrayList<>();
        String query = "SELECT * FROM " + tbl_Olcumler + " ORDER BY "+" olcumKayitTarihi "+" DESC";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        Olcum olcum = null;
        if (cursor.moveToFirst()){
            do {
                olcum = new Olcum();
                olcum.setId(Integer.parseInt(cursor.getString(0)));
                olcum.setBoy(Integer.parseInt(cursor.getString(1)));
                olcum.setKilo(Integer.parseInt(cursor.getString(2)));
                olcum.setCinsiyet(cursor.getString(3));
                olcum.setYagsizAgirlik(Integer.parseInt(cursor.getString(4)));
                olcum.setKiloIdeal(Integer.parseInt(cursor.getString(5)));
                olcum.setVucutYuzeyAlani(cursor.getString(6));
                olcum.setVucutKitleIndexi(cursor.getString(7));
                olcum.setDURUM(cursor.getString(8));
                olcum.setOlcumTarihi(cursor.getString(9));
                olcumler.add(olcum);
            }while (cursor.moveToNext());
        }
        return olcumler;
    }


    public void OlcumSil(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(tbl_Olcumler,olcum_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }

}
