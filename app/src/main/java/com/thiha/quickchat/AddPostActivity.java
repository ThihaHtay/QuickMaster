package com.thiha.quickchat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AddPostActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);

        FirebaseFirestore db= FirebaseFirestore.getInstance();

        TextInputEditText firstName = findViewById(R.id.firstNameET);
        TextInputEditText lastName = findViewById(R.id.lastNameET);
        TextInputEditText phone =findViewById(R.id.phoneNumberET);
        TextInputEditText bio= findViewById(R.id.bioET);
        MaterialButton addUser = findViewById(R.id.addUser);

        addUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String,Object> postModel = new HashMap<>();
                postModel.put("firstName", Objects.requireNonNull(firstName.getText()).toString());
                postModel.put("lastName",Objects.requireNonNull(lastName.getText()).toString());
                postModel.put("phone",Objects.requireNonNull(phone.getText()).toString());
                postModel.put("bio",Objects.requireNonNull(bio.getText()).toString());
                postModel.put("timestamp",Objects.requireNonNull(Timestamp.now()));

                db.collection("posts").add(postModel).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(AddPostActivity.this,"User added successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddPostActivity.this,"Failed to add user",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


    }

}