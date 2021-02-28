package com.burakerol.android.finalprojesi;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.HashMap;

/**
 * Created by Burak-PC on 3.01.2018.
 */

public class katguncelle extends AppCompatActivity {

    EditText isim;
    Button guncelle;
    int id;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.katguncelle);

        guncelle=(Button) findViewById(R.id.buttonguncelle);
        isim=(EditText) findViewById(R.id.katad);
        Intent i=getIntent();
        id=i.getIntExtra("idsi",0);

        Database db=new Database(getApplicationContext());
        HashMap<String, String> kategoridetay = db.kategoriDetay(id);
        isim.setText(kategoridetay.get("kat_isim"));

        guncelle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String deger1=isim.getText().toString().trim();
                Database db=new Database(getApplicationContext());
                db.kategoriGuncelle(deger1,id);
                Intent i= new Intent(katguncelle.this,opkategori.class);
                startActivity(i);


            }
        });

    }
}
