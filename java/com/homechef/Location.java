package com.homechef;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

public class Location extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location);
    }

    public void savelocation(View view){
        Intent intent = new Intent(Location.this,Payment.class);
        Toast.makeText(getApplicationContext(),"Location saved successfully",Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }

}
