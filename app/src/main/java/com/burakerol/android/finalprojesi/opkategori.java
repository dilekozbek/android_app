package com.burakerol.android.finalprojesi;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Burak-PC on 26.12.2017.
 */

public class opkategori extends AppCompatActivity {

    EditText katad;
    Button ekle;
    ArrayList<HashMap<String,String>> kategoriler;
    ArrayAdapter<String> adapter;
    String kategoriadlar[];
    int kategori_idler[];
    int id;
    ListView lv;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kategori);

        katad=(EditText) findViewById(R.id.katad);
        ekle=(Button) findViewById(R.id.katekle);
        lv=(ListView) findViewById(R.id.listView);

        ekle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int onay = 0;
                String isim;
                isim=katad.getText().toString().trim();
                Database db = new Database(getApplicationContext());

                kategoriler = db.kategoriListe();
                kategoriadlar = new String[kategoriler.size()];
                for (int i = 0; i < kategoriler.size(); i++) {
                    if(isim.equals(kategoriler.get(i).get("kat_isim")))
                    {
                        onay=1;
                        Toast.makeText(getApplicationContext(),"Bu kategori adı sistemimizde mevcuttur. Lütfen değiştirin",Toast.LENGTH_LONG).show();
                    }
                }
                if(onay!=1)
                {
                    db.KategoriEkleme(isim);
                    db.close();

                    finish();
                    startActivity(getIntent());}

            }
        });

        /*Veri Çekme*/
        Database db = new Database(getApplicationContext());
        kategoriler = db.kategoriListe();
        if (kategoriler.size() == 0) {
            Toast.makeText(getApplicationContext(), "Henüz kategori eklenmemiş", Toast.LENGTH_SHORT).show();

        } else {

            kategoriadlar = new String[kategoriler.size()];
            kategori_idler = new int[kategoriler.size()];
            for (int i = 0; i < kategoriler.size(); i++) {
                kategoriadlar[i] = kategoriler.get(i).get("kat_isim");
                kategori_idler[i] = Integer.parseInt(kategoriler.get(i).get("kat_id"));
            }
            adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,kategoriadlar);
            lv.setAdapter(adapter);

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    id = (int) kategori_idler[i];
                    // Toast.makeText(getApplicationContext(), "İd="+id, Toast.LENGTH_SHORT).show();

                    AlertDialog.Builder uyari = new AlertDialog.Builder(opkategori.this);
                    uyari.setTitle("Seçenekler");
                    uyari.setMessage("Lütfen Yapmak istediğiniz İşlemi seçiniz");
                    uyari.setNeutralButton("Güncelle", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface uyari, int which){
                            Intent intent= new Intent(opkategori.this,katguncelle.class);
                            intent.putExtra("idsi",id);
                            startActivity(intent);
                        }

                    });
                    uyari.setPositiveButton("Sil", new DialogInterface.OnClickListener(){
                        public void onClick(DialogInterface uyari, int which){
                            AlertDialog.Builder uyari2 = new AlertDialog.Builder(opkategori.this);
                            uyari2.setTitle("Uyarı");
                            uyari2.setMessage("Kategoriyi silmek istediğinizden emin misiniz?");
                            uyari2.setNegativeButton("Hayır", new DialogInterface.OnClickListener(){
                                public void onClick(DialogInterface uyari, int which){
                                    Toast.makeText(getApplicationContext(), "Silme işlemi iptal edildi", Toast.LENGTH_SHORT).show();
                                }
                            });
                            uyari2.setPositiveButton("Evet", new DialogInterface.OnClickListener(){
                                public void onClick(DialogInterface uyari, int which){
                                    Database db= new Database(getApplicationContext());
                                    db.katsil(id);
                                    Intent intent = new Intent(getApplicationContext(),opkategori.class);
                                    startActivity(intent);
                                    finish();
                                }
                            });
                            uyari2.show();
                        }
                    });
                    uyari.show();
                }
            });
        }


    }
}
