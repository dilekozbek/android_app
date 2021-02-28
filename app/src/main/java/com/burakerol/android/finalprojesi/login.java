package com.burakerol.android.finalprojesi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class login extends AppCompatActivity {

    Button giris;
    EditText kullanici,sifre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        giris=(Button) findViewById(R.id.btn_giris);
        kullanici=(EditText) findViewById(R.id.txt_kulad);
        sifre=(EditText) findViewById(R.id.txt_sifre);

        final String kulad,txsifre;
        kulad=kullanici.getText().toString().trim();
        txsifre=sifre.getText().toString().trim();

        giris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(kulad=="yonetici" && txsifre=="admin123");
                {
                    Intent git= new Intent(login.this,opmain.class);
                    startActivity(git);
                    finish();
                }
            }
        });



    }
}
