package com.burakerol.android.finalprojesi;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Burak-PC on 3.01.2018.
 */

public class urunguncelle extends AppCompatActivity {

    EditText uad,ukad,uadet,ufiyat;
    Button guncelle, kontrol;
    Spinner kategori;
    int id;
    ArrayList<HashMap<String,String>> kategoriisimleri;
    String kadlari[];
    int kategoriler[];

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.urunguncelle);

        Database db=new Database(getApplicationContext());
        kategori=(Spinner) findViewById(R.id.kategoriSpin);
        uad=(EditText) findViewById(R.id.txtad);
        ufiyat=(EditText) findViewById(R.id.txtfiyat);
        uadet=(EditText) findViewById(R.id.txtstok);
        guncelle=(Button) findViewById(R.id.guncelle);
        kontrol=(Button) findViewById(R.id.kontrol);
        kategoriisimleri=db.kategoriListe();

        Intent i = getIntent();
        id=i.getIntExtra("idsi",0);
        final HashMap<String,String> urun = db.urunDetay(id);
        uad.setText(urun.get("u_ad"));
        uadet.setText(urun.get("u_stok"));
        ufiyat.setText(urun.get("uk_ad"));

        /* Kategori Çek*/

        kadlari = new String[kategoriisimleri.size()];
        kategoriler= new int[kategoriisimleri.size()];
        for (int j = 0; j < kategoriisimleri.size(); j++) {
            kadlari[j] = kategoriisimleri.get(j).get("kat_isim");
            kategoriler[j] = Integer.parseInt(kategoriisimleri.get(j).get("kat_id"));
        }

        ArrayAdapter<String> adaptor =new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,kadlari);
        adaptor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        kategori.setAdapter(adaptor);
        kategori.setSelection(adaptor.getPosition(urun.get("u_fiyat")));

        guncelle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String deger1,deger2;int d3,d4;
                deger1=uad.getText().toString().trim();
                deger2=kategori.getSelectedItem().toString();
                d3=Integer.parseInt(ufiyat.getText().toString().trim());
                d4=Integer.parseInt(uadet.getText().toString().trim());
                Database db=new Database(getApplicationContext());
                db.urunGuncelle(deger1,deger2,d3,d4,id);
                Intent i= new Intent(urunguncelle.this,urun.class);
                startActivity(i);

            }
        });

        kontrol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String deneme;
                deneme=urun.get("u_ad")+urun.get("u_fiyat")+urun.get("uk_ad")+urun.get("u_stok");
                Toast.makeText(getApplicationContext(),deneme,Toast.LENGTH_LONG).show();
            }
        });



    }
}
