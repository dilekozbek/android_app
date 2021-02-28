package com.burakerol.android.finalprojesi;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

/**
 * Created by Burak-PC on 16.12.2017.
 */

public class intro extends AppCompatActivity {

    private static int zaman=7000;
    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        img=(ImageView) findViewById(R.id.img);

        Animation myanim= AnimationUtils.loadAnimation(this,R.anim.animationeffect);
        img.startAnimation(myanim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent gecis=new Intent(intro.this,login.class);
                startActivity(gecis);
                finish();
            }
        },zaman);
    }
}