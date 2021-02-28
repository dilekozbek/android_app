package com.burakerol.android.finalprojesi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Burak-PC on 16.12.2017.
 */

public class Database extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION=1;
    private static final String DATABASE_NAME= "veritabani";
    /*Tablolar*/
    /*
    private static final String db_Siparis="siparis";
    /*tablo isimleri bitiş*/

    //Kategori Sütunlar
    private static final String db_Kategori="kategori";
    private static final String kat_id = "kat_id";
    private static final String kat_ad = "kat_isim";

    //Ürün Sütunlar
    private static final String db_Urun="urun";
    private static final String urun_id = "u_id";
    private static final String urun_ad = "u_ad";
    private static final String urun_stok = "u_stok";
    private static final String urun_fiyat = "u_fiyat";
    private static final String urun_kategoriad = "uk_ad";

    //Sipariş Sütunlar
    public static final String siparis_id = "s_id";
    public static final String siparis_masa = "s_masa";
    public static final String siparis_kisi = "s_kul";
    public static final String siparis_top = "s_top";

    //Kullanıcı Sütunlar
    private static final String db_Kullanici="kullanici";
    private static String kullanici_id = "kid";
    private static String kullanici_isim = "kul_ad";
    private static String kullanici_soyadi = "kul_soyad";
    private static String kullanici_adi = "kul_giris";
    private static String kullanici_sifre = "kul_sifre";
    private static String kullanici_mail = "kul_mail";
    private static String kullanici_telefon = "kul_tel";


   public Database(Context c){
       super(c,DATABASE_NAME,null,DATABASE_VERSION);
   }

   @Override
    public void onCreate(SQLiteDatabase db)
   {
       //Kategori Tablosu oluşum
       String katolustur ="CREATE TABLE " + db_Kategori + "("
               + kat_id + " INTEGER PRIMARY KEY AUTOINCREMENT,"
               + kat_ad+ " TEXT" + ")";
       db.execSQL(katolustur);
       //Ürün Tablosu oluşum
       String uruntablo ="CREATE TABLE " + db_Urun + "("
               + urun_id + " INTEGER PRIMARY KEY AUTOINCREMENT,"
               + urun_ad + " TEXT,"
               + urun_stok + " INTEGER,"
               + urun_kategoriad + " TEXT,"
               + urun_fiyat + " INTEGER" + ")";
       db.execSQL(uruntablo);
      /* //Sipariş Tablosu oluşum
       db.execSQL("CREATE TABLE "+ db_Siparis + " (" + siparis_id+ "INTEGER PRIMARY KEY AUTOINCREMENT," +
               siparis_kisi + "TEXT not null," + siparis_top + "INTEGER," + siparis_masa + "TEXT not null" + ");");*/
       //Kullanıcı Tablosu oluşum
       String CREATE_TABLE ="CREATE TABLE " + db_Kullanici + "("
               + kullanici_id + " INTEGER PRIMARY KEY AUTOINCREMENT,"
               + kullanici_isim + " TEXT,"
               + kullanici_soyadi + " TEXT,"
               + kullanici_adi + " TEXT,"
               + kullanici_sifre + " TEXT,"
               + kullanici_mail + " TEXT,"
               + kullanici_telefon + " TEXT" + ")";
       db.execSQL(CREATE_TABLE);
   }
    public void PersonelVeriEkle(String isim, String soyad, String mail, String kullaniciad, String tel, String sifre){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(kullanici_isim,isim.trim());
        cv.put(kullanici_soyadi,soyad.trim());
        cv.put(kullanici_adi,kullaniciad.trim());
        cv.put(kullanici_sifre,sifre.trim());
        cv.put(kullanici_mail,mail.trim());
        cv.put(kullanici_telefon,tel.trim());
        db.insert(db_Kullanici,null,cv); // Veriyi ekle
        db.close();
    }
    @Override
    public void onUpgrade(SQLiteDatabase data,int oldVersion,int newVersion)
   {

   }

   /*PERSONEL İŞLEMLERİ */
    public ArrayList<HashMap<String,String>> Uye_Listesi(){
        SQLiteDatabase db= this.getReadableDatabase();
        String SQL ="select * from " + db_Kullanici;
        Cursor okuyucu=db.rawQuery(SQL,null);
        ArrayList<HashMap<String,String>> uyelistesi = new ArrayList<HashMap<String,String>>();
        if(okuyucu.moveToFirst()){
            do {
                HashMap<String,String> veriler = new HashMap<String, String>();
                for(int i=0;i<okuyucu.getColumnCount();i++){
                    veriler.put(okuyucu.getColumnName(i),okuyucu.getString(i));
                }
                uyelistesi.add(veriler);
            }while (okuyucu.moveToNext());
        }
        db.close();
        return uyelistesi;
    }

    public void Uye_Sil(int id){

        SQLiteDatabase db=this.getWritableDatabase();
        db.delete(db_Kullanici,kullanici_id + " = ? ",
                new String[] {String.valueOf(id)} );

        db.close();
    }
    public HashMap<String, String> Uye_Detay(int id){

        HashMap<String,String> uye = new HashMap<String, String>();
        String SQL="SELECT *FROM " + db_Kullanici+ " WHERE "+ kullanici_id+"=" +id;
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cr = db.rawQuery(SQL,null);
        cr.moveToFirst();

        if (cr.getCount()>0){
            uye.put(kullanici_adi,cr.getString(1));
            uye.put(kullanici_soyadi,cr.getString(2));
            uye.put(kullanici_isim,cr.getString(3));
            uye.put(kullanici_sifre,cr.getString(4));
            uye.put(kullanici_telefon,cr.getString(5));
            uye.put(kullanici_mail,cr.getString(6));
        }
        cr.close();
        db.close();
        return uye;

    }
    public void PersonelGuncelle(String isim, String soyad, String kullaniciad, String sifre, String tel, String mail, int id){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues degerler = new ContentValues();
        degerler.put(kullanici_isim,isim);
        degerler.put(kullanici_soyadi,soyad);
        degerler.put(kullanici_adi,kullaniciad);
        degerler.put(kullanici_sifre,sifre);
        degerler.put(kullanici_telefon,tel);
        degerler.put(kullanici_mail,mail);
        db.update(db_Kullanici, degerler, kullanici_id + " = ?",
                new String[] {String.valueOf(id)});

    }

    /*KATEGORİ İŞLEMLERİ*/

    public void KategoriEkleme(String katad){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(kat_ad,katad.trim());
        db.insert(db_Kategori,null,cv); // Veriyi ekle
        db.close();
    }
    public ArrayList<HashMap<String,String>> kategoriListe(){
        SQLiteDatabase db= this.getReadableDatabase();
        String SQL ="select * from " + db_Kategori;
        Cursor okuyucu=db.rawQuery(SQL,null);
        ArrayList<HashMap<String,String>> kategorilistesi = new ArrayList<HashMap<String,String>>();
        if(okuyucu.moveToFirst()){
            do {
                HashMap<String,String> veriler = new HashMap<String, String>();
                for(int i=0;i<okuyucu.getColumnCount();i++){
                    veriler.put(okuyucu.getColumnName(i),okuyucu.getString(i));
                }
                kategorilistesi.add(veriler);
            }while (okuyucu.moveToNext());
        }
        db.close();

        return kategorilistesi;
    }
    public void katsil(int id){

        SQLiteDatabase db=this.getWritableDatabase();
        db.delete(db_Kategori,kat_id + " = ? ",
                new String[] {String.valueOf(id)} );

        db.close();
    }

    public HashMap<String, String> kategoriDetay(int id){

        HashMap<String,String> uye = new HashMap<String, String>();
        String SQL="SELECT *FROM " + db_Kategori+ " WHERE "+ kat_id+"=" +id;
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cr = db.rawQuery(SQL,null);
        cr.moveToFirst();

        if (cr.getCount()>0){
            uye.put(kat_ad,cr.getString(1));
        }
        cr.close();
        db.close();
        return uye;

    }
    public void kategoriGuncelle(String kategoriad, int id){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues degerler = new ContentValues();
        degerler.put(kat_ad,kategoriad);
        db.update(db_Kategori, degerler, kat_id+ " = ?",
                new String[] {String.valueOf(id)});

    }

    /*ÜRÜN İŞLEMLERİ*/


    public void urunEkle(String urunad,String katad,int stok,int fiyat){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(urun_ad,urunad.trim());
        cv.put(urun_stok,stok);
        cv.put(urun_fiyat,fiyat);
        cv.put(urun_kategoriad,katad.trim());
        db.insert(db_Urun,null,cv); // Veriyi ekle
        db.close();
    }

    public ArrayList<HashMap<String,String>> urunListe(){
        SQLiteDatabase db= this.getReadableDatabase();
        String SQL ="select * from " + db_Urun;
        Cursor okuyucu=db.rawQuery(SQL,null);
        ArrayList<HashMap<String,String>> urunListesi = new ArrayList<HashMap<String,String>>();
        if(okuyucu.moveToFirst()){
            do {
                HashMap<String,String> veriler = new HashMap<String, String>();
                for(int i=0;i<okuyucu.getColumnCount();i++){
                    veriler.put(okuyucu.getColumnName(i),okuyucu.getString(i));
                }
                urunListesi.add(veriler);
            }while (okuyucu.moveToNext());
        }
        db.close();

        return urunListesi;
    }
    public void urunSil(int id){

        SQLiteDatabase db=this.getWritableDatabase();
        db.delete(db_Urun,urun_id+ " = ? ",
                new String[] {String.valueOf(id)} );

        db.close();
    }
    public HashMap<String, String> urunDetay(int id){

        HashMap<String,String> urun = new HashMap<String, String>();
        String SQL="SELECT * FROM " + db_Urun+ " WHERE "+ urun_id +"=" +id;
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cr = db.rawQuery(SQL,null);
        cr.moveToFirst();

        if (cr.getCount()>0){
            urun.put(urun_ad,cr.getString(1));
            urun.put(urun_stok,cr.getString(2));
            urun.put(urun_fiyat,cr.getString(3));
            urun.put(urun_kategoriad,cr.getString(4));
        }
        cr.close();
        db.close();
        return urun;

    }
    public void urunGuncelle(String uisim,String kategoriad,int ufiyat,int ustok,  int id){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues degerler = new ContentValues();
        degerler.put(urun_kategoriad,kategoriad);
        degerler.put(urun_ad,uisim);
        degerler.put(urun_fiyat,ufiyat);
        degerler.put(urun_stok,ustok);
        db.update(db_Urun, degerler, urun_id+ " = ?",
                new String[] {String.valueOf(id)});

    }
    /* MASA İŞLEMLERİ*/

}