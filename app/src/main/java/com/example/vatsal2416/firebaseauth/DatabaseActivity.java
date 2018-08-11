package com.example.vatsal2416.firebaseauth;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DatabaseActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);

        Button readButton = findViewById(R.id.btnRead);
        Button insertButton = findViewById(R.id.btnInsert);

        final EditText textGender = findViewById(R.id.editTextGender);
        final EditText textMobileNo = findViewById(R.id.editTextMobileNo);
        final EditText textQualification = findViewById(R.id.editTextQualification);
        final EditText textAddress = findViewById(R.id.editTextAddress);
        final ListView lview = findViewById(R.id.lview);
        //readButton onClickListener
        readButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("users");
                final List<String> list = new ArrayList<>();
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Iterable<DataSnapshot> data = dataSnapshot.getChildren();
                        for(DataSnapshot data1 : data){
                            String msg = null;
                            msg = msg +"\n"+data1.getValue();
                            //Iterable<DataSnapshot> data2 = data1.getChildren();
                            //for(DataSnapshot data3 : data2){
                             //   msg = msg +"\n"+data1.getValue();
                           // }
                            list.add(msg);
                        }
                        textGender.setText("Reading data:");
                        ArrayAdapter<String> myadapter = new ArrayAdapter<String>
                                (DatabaseActivity.this,android.R.layout.simple_list_item_1,list);
                                lview.setAdapter(myadapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        //insertButton onClickListener
        insertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("users");
                DatabaseReference myRef_child = myRef.child(FirebaseAuth.getInstance().getUid());
                myRef_child.child("Gender").setValue(textGender.getText().toString());
                myRef_child.child("MobileNo").setValue(textMobileNo.getText().toString());
                myRef_child.child("Qualification").setValue(textQualification.getText().toString());
                myRef_child.child("Address").setValue(textAddress.getText().toString());

            }
        });
    }
}
