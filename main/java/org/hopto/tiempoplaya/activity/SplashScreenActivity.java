package org.hopto.tiempoplaya.activity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by jpenaab on 28/02/2019.
 */

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((AnimationDrawable) getWindow().getDecorView().getBackground()).start();

        Intent intent = new Intent(getApplicationContext(), TPMainActivityLogin.class);
        startActivity(intent);

    }
}
