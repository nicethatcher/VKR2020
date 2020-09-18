package com.homechef;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ChefPage extends Activity implements View.OnClickListener {

    private static final String TAG = "ChefPage";
    EditText chefSpeciality;
    TextView chefsName;
    Button save, add;
    ListView listView;
    List<String> items;
    DatabaseReference databaseReference;
    FirebaseAuth mAuth;
    String id;

    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chef_page);

        Intent intent = getIntent();
        id = intent.getStringExtra("ID");

        chefSpeciality = findViewById(R.id.chefsspeciality);
        save = findViewById(R.id.save);
        add = findViewById(R.id.add);
        save.setOnClickListener(this);
        add.setOnClickListener(this);
        chefsName = findViewById(R.id.textviewchefspage);
        listView = findViewById(R.id.list_item);
        listView.setClickable(false);
        items = new ArrayList<>();
        adapter = new ArrayAdapter<String>(ChefPage.this, R.layout.item, items);
        listView.setAdapter(adapter);

        databaseReference = FirebaseDatabase.getInstance().getReference("Chefs");
        databaseReference.child("id");

        Query query = databaseReference;

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Chef chef = dataSnapshot1.getValue(Chef.class);
                    assert chef != null;
                    if (chef.getId().equals(id)) {
                        String name = chef.getName();
                        chefsName.setText(name);
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add:
                String menu = chefSpeciality.getText().toString().trim();
                items.add(menu);
                adapter.notifyDataSetChanged();
                chefSpeciality.setText("");

                break;
            case R.id.save:

                for (String item : items) {
                    DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference("Items").child(id);
                    String idd = databaseReference1.push().getKey();
                    databaseReference1 = databaseReference1.child(idd);
                    Item item1 = new Item(idd, item);
                    databaseReference1.setValue(item1);
                }

                items.clear();
                adapter.notifyDataSetChanged();

                break;
        }
    }

    public void signOut(View view) {
        if (view.getId() == R.id.change) {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(ChefPage.this, ChefLogin.class);
            startActivity(intent);
            finish();
        }

    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}