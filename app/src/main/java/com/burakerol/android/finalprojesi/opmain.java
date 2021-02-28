package com.burakerol.android.finalprojesi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

/**
 * Created by Burak-PC on 19.12.2017.
 */

public class opmain extends AppCompatActivity {

    ImageButton personel,urun,masa,kategori;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opmain);

        personel=(ImageButton) findViewById(R.id.personelbtn);
        urun=(ImageButton) findViewById(R.id.urunbtn);
        kategori=(ImageButton) findViewById(R.id.katbtn);
        masa=(ImageButton) findViewById(R.id.masabtn);

        personel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent personelgit=new Intent(opmain.this,personel.class);
                startActivity(personelgit);
                finish();
            }
        });


        urun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent urungit=new Intent(opmain.this,urun.class);
                startActivity(urungit);
                finish();
            }
        });
/* Buton linkleri
        masa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent masagit=new Intent(opmain.this,masa.class);
                startActivity(masagit);
                finish();
            }
        });
*/
        kategori.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent kategorigit=new Intent(opmain.this,opkategori.class);
                startActivity(kategorigit);
                finish();
            }
        });




    }
}

