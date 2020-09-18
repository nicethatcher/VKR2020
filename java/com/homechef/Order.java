package com.homechef;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class  Order extends Activity implements View.OnClickListener{

    private static final String TAG = "Order";
    Button button;
    String id;
    LinearLayout listView;
    DatabaseReference databaseReference;
    Context context;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order);
        FirebaseDatabase database;
        context=this;
        findViewById(R.id.order).setOnClickListener(this);
        button = findViewById(R.id.order);
        button.setOnClickListener(this);
        listView = findViewById(R.id.checkList);
        Intent intent = getIntent();
        id = intent.getStringExtra("ID");
        databaseReference = FirebaseDatabase.getInstance().getReference("Items").child(id);
        databaseReference.child(id);
    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listView.removeAllViews();
                for (DataSnapshot order : dataSnapshot.getChildren()){
                    Item item = order.getValue(Item.class);
                    CheckBox checkBox=new CheckBox(context);
                    checkBox.setText(item.getItemName());
                    listView.addView(checkBox);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.order: {
                Toast.makeText(getApplicationContext(), "Proceeding to accept Location", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Order.this,Location.class);
                startActivity(intent);
                for(int i=0;i<listView.getChildCount();i++)
                {
                    CheckBox checkBox=(CheckBox)listView.getChildAt(i);
                    checkBox.setChecked(false);
                }
            }
        }
    }
}
