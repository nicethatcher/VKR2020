package com.homechef;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;

public class MainActivity extends Activity implements View.OnClickListener {

    Button eat, cook;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        eat = findViewById(R.id.choiceEat);
        cook = findViewById(R.id.choiceCook);
        eat.setOnClickListener(this);
        cook.setOnClickListener(this);
    }

    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.choiceEat:
                Intent i1 = new Intent(MainActivity.this, UserLogin.class);
                startActivity(i1);
                finish();
                break;

            case R.id.choiceCook:
                Intent i2 = new Intent(MainActivity.this, ChefLogin.class);
                startActivity(i2);
                finish();
                break;
        }
    }
}
