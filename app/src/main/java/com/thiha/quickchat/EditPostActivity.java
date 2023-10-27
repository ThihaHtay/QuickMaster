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
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class EditPostActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_post);
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        TextInputEditText firstName = findViewById(R.id.firstNameET);
        TextInputEditText lastName = findViewById(R.id.lastNameET);
        TextInputEditText phone =findViewById(R.id.phoneNumberET);
        TextInputEditText bio= findViewById(R.id.bioET);

        MaterialButton save = findViewById(R.id.save);
        MaterialButton delete = findViewById(R.id.delete);

        firstName.setText(App.postModel.getFirstName());
        lastName.setText(App.postModel.getLastName());
        phone.setText(App.postModel.getPhone());
        bio.setText(App.postModel.getBio());


        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.collection("posts").document(App.postModel.getId()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(EditPostActivity.this,"User deleted successfully",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EditPostActivity.this,"Failed to delete user",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, Object> post = new HashMap<>();
                post.put("firstName", Objects.requireNonNull(firstName.getText()).toString());
                post.put("lastName", Objects.requireNonNull(lastName.getText()).toString());
                post.put("phone",Objects.requireNonNull(phone.getText()).toString());
                post.put("bio",Objects.requireNonNull(bio.getText()).toString());

                db.collection("posts").document(App.postModel.getId()).set(post).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(EditPostActivity.this,"Saved successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EditPostActivity.this,"Failed to save changes", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });
    }

}
