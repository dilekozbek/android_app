package com.burakerol.android.finalprojesi;

import android.content.DialogInterface;
import android.content.Intent;
import android.opengl.Visibility;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Burak-PC on 16.12.2017.
 */

public class personel extends AppCompatActivity {

    EditText ad,soyadi,kadi,sfr,email,telefon;
    Button kayit;
    ListView lv;
    ArrayList<HashMap<String,String>> uyeler;
    ArrayAdapter<String> adapter;
    String uye_adlari[],uye_soyadlari[],uye_kadlari[],uye_tel[],uye_mail[],uye_sifre[];
    int uye_idleri[];
    int id;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personel);

        ad=(EditText) findViewById(R.id.txtad);
        soyadi=(EditText) findViewById(R.id.txtsoyad);
        kadi=(EditText) findViewById(R.id.txtkul);
        sfr=(EditText) findViewById(R.id.txtsifre);
        email=(EditText) findViewById(R.id.txtmail);
        telefon=(EditText) findViewById(R.id.txttel);
        kayit=(Button) findViewById(R.id.btnkayit);
        lv = (ListView) findViewById(R.id.lv);


        kayit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int onay = 0;
                String isim,mail,soyad,kullaniciad,tel,sifre;
                isim=ad.getText().toString().trim();
                soyad=soyadi.getText().toString().trim();
                kullaniciad=kadi.getText().toString().trim();
                sifre=sfr.getText().toString().trim();
                tel=telefon.getText().toString().trim();
                mail=email.getText().toString().trim();
                Database db = new Database(getApplicationContext());

                uyeler = db.Uye_Listesi();
                uye_kadlari = new String[uyeler.size()];
                for (int i = 0; i < uyeler.size(); i++) {
                    if(kullaniciad.equals(uyeler.get(i).get("kul_giris")))
                    {
                        onay=1;
                        Toast.makeText(getApplicationContext(),"Bu kullanıcı adı sistemimizde mevcuttur. Lütfen değiştirin",Toast.LENGTH_LONG).show();
                    }
                }
                if(onay!=1)
                {
                db.PersonelVeriEkle (isim,soyad,mail, kullaniciad,tel,sifre);
                db.close();

                finish();
                startActivity(getIntent());}

            }
        });
        /*Veri Çekme*/
        Database db = new Database(getApplicationContext());
        uyeler = db.Uye_Listesi();
        if (uyeler.size() == 0) {
            Toast.makeText(getApplicationContext(), "Henüz üye eklenmemiş", Toast.LENGTH_SHORT).show();

        } else {

            uye_adlari = new String[uyeler.size()];
            uye_soyadlari= new String[uyeler.size()];
            uye_kadlari = new String[uyeler.size()];
            uye_sifre = new String[uyeler.size()];
            uye_mail = new String[uyeler.size()];
            uye_tel = new String[uyeler.size()];
            uye_idleri = new int[uyeler.size()];

            for (int i = 0; i < uyeler.size(); i++) {
                uye_adlari[i] = uyeler.get(i).get("kul_ad");
                uye_soyadlari[i] = uyeler.get(i).get("kul_soyad");
                uye_kadlari[i] = uyeler.get(i).get("kul_giris");
                uye_sifre[i] = uyeler.get(i).get("kul_sifre");
                uye_mail[i] = uyeler.get(i).get("kul_mail");
                uye_tel[i] = uyeler.get(i).get("kul_tel");
                uye_idleri[i] = Integer.parseInt(uyeler.get(i).get("kid"));
            }
            adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,uye_kadlari);
            lv.setAdapter(adapter);

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    id = (int) uye_idleri[i];
                    // Toast.makeText(getApplicationContext(), "İd="+id, Toast.LENGTH_SHORT).show();

                    AlertDialog.Builder uyari = new AlertDialog.Builder(personel.this);
                    uyari.setTitle("Seçenekler");
                    uyari.setMessage("Lütfen Yapmak istediğiniz İşlemi seçiniz");
                    uyari.setNeutralButton("Güncelle", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface uyari, int which){
                            Intent intent= new Intent(personel.this,personelguncelle.class);
                            intent.putExtra("idsi",id);
                            startActivity(intent);

                        }

                    });
                    uyari.setPositiveButton("Sil", new DialogInterface.OnClickListener(){
                        public void onClick(DialogInterface uyari, int which){
                            AlertDialog.Builder uyari2 = new AlertDialog.Builder(personel.this);
                            uyari2.setTitle("Uyarı");
                            uyari2.setMessage("Üye kaydını silmek istediğinizden emin misiniz?");
                            uyari2.setNegativeButton("Hayır", new DialogInterface.OnClickListener(){
                                public void onClick(DialogInterface uyari, int which){
                                    Toast.makeText(getApplicationContext(), "Silme işlemi iptal edildi", Toast.LENGTH_SHORT).show();
                                }
                            });
                            uyari2.setPositiveButton("Evet", new DialogInterface.OnClickListener(){
                                public void onClick(DialogInterface uyari, int which){
                                    Database db= new Database(getApplicationContext());
                                    db.Uye_Sil(id);
                                    Intent intent = new Intent(getApplicationContext(),personel.class);
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
