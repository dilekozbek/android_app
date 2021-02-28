package com.burakerol.android.finalprojesi;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Burak-PC on 26.12.2017.
 */

public class personelguncelle extends AppCompatActivity {

    EditText ad, soyadi, kadi, sfr, email, telefon;
    Button guncelle;
    ArrayList<HashMap<String, String>> uyeler;
    String uye_kadlari[];
    int id;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personelguncelle);

        guncelle=(Button) findViewById(R.id.btnguncelle);

        ad=(EditText) findViewById(R.id.txtad);
        soyadi=(EditText) findViewById(R.id.txtsoyad);
        kadi=(EditText) findViewById(R.id.txtkul);
        sfr=(EditText) findViewById(R.id.txtsifre);
        telefon=(EditText) findViewById(R.id.txttel);
        email=(EditText) findViewById(R.id.txtmail);

        Intent i = getIntent();
        id = i.getIntExtra("idsi", 0);

        Database db= new Database(getApplicationContext());
        HashMap<String, String> uyedetayi = db.Uye_Detay(id);
        ad.setText(uyedetayi.get("kul_ad"));
        soyadi.setText(uyedetayi.get("kul_soyad"));
        kadi.setText(uyedetayi.get("kul_giris"));
        sfr.setText(uyedetayi.get("kul_sifre"));
        telefon.setText(uyedetayi.get("kul_mail"));
        email.setText(uyedetayi.get("kul_tel"));

        guncelle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int onay=0;
                String deger1, deger2, deger3, deger4,deger5,deger6;
                deger1 = kadi.getText().toString();
                deger2 = soyadi.getText().toString();
                deger3 = ad.getText().toString();
                deger4=sfr.getText().toString();
                deger5=telefon.getText().toString();
                deger6=email.getText().toString();
                Database db = new Database(getApplicationContext());
                String kontrol;
                kontrol=kadi.toString().trim();

                uyeler = db.Uye_Listesi();
                uye_kadlari = new String[uyeler.size()];
                for (int i = 0; i < uyeler.size(); i++) {
                    if(kontrol.equals(uyeler.get(i).get("kul_giris")))
                    {
                        onay=1;
                        Toast.makeText(getApplicationContext(),"Bu kullanıcı adı sistemimizde mevcuttur. Lütfen değiştirin",Toast.LENGTH_LONG).show();
                    }
                }

                if(onay!=1)
                {
                db.PersonelGuncelle(deger1,deger2,deger3,deger4,deger5,deger6,id);
                Intent i= new Intent(personelguncelle.this,personel.class);
                startActivity(i);
                }
            }
        });


    }
}