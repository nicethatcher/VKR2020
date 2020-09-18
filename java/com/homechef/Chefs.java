package com.homechef;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
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

public class Chefs extends Activity {

    DatabaseReference databaseReference,databaseReference1,databaseReference2;
    private FirebaseAuth auth;
    ListView listView;
    String id;
    TextView username1;
    List<Chef> chefList;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chefs);
        Intent intent = getIntent();
        id = intent.getStringExtra("ID");
        username1 = findViewById(R.id.usernameid);
        listView = findViewById(R.id.listView);
        chefList = new ArrayList<>();
        auth = FirebaseAuth.getInstance();

        databaseReference = FirebaseDatabase.getInstance().getReference("Chefs");
        databaseReference1 = FirebaseDatabase.getInstance().getReference("Users");
        databaseReference2 = FirebaseDatabase.getInstance().getReference("Items");
        databaseReference1.child("id");
        databaseReference.child("id");
        databaseReference2.child("id").child("id");

        listView.setClickable(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, final long id) {

                Chef chef = chefList.get(position);
                final String id1 = chef.getId();
                DatabaseReference innerDataRef=databaseReference2.child(id1);
                innerDataRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot2) {
                        if(dataSnapshot2.hasChildren()){
                            Intent intent = new Intent(Chefs.this, Order.class);
                            intent.putExtra("ID",id1);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Query query = databaseReference1;

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                chefList.clear();

                for (DataSnapshot chefsnapshot1 : dataSnapshot.getChildren()) {
                    Chef chef = chefsnapshot1.getValue(Chef.class);
                    chefList.add(chef);
                }
                ChefList adapter = new ChefList(Chefs.this, R.layout.list_item, chefList);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot1) {
                for (DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()){
                    User user = dataSnapshot2.getValue(User.class);
                    if (user.getId().equals(id)){
                        String name = user.getName();
                        username1.setText(name);
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError1) {

            }
        });


    }

    public void signOut(View view) {
        if (view.getId() == R.id.changeu) {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(Chefs.this, UserLogin.class);
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