package com.thiha.quickchat;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.thiha.quickchat.adapter.PostAdapter;
import com.thiha.quickchat.model.PostModel;

import java.util.ArrayList;


public class PostFragment extends Fragment {
    RecyclerView recyclerView;
    FloatingActionButton add,refresh;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_post, container, false);
        recyclerView = view.findViewById(R.id.recycler);
        add=view.findViewById(R.id.addUser);
        refresh=view.findViewById(R.id.refresh);
        setupRecyclerView();
        return (view);
    }
    void  setupRecyclerView(){
        FirebaseFirestore db=FirebaseFirestore.getInstance();
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddPostActivity.class);

                // Start the activity
                getActivity().startActivity(intent);
            }
        });
        db.collection("posts").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    ArrayList<PostModel> arrayList = new ArrayList<>();
                    for(QueryDocumentSnapshot doc: task.getResult()){
                        PostModel postModel=doc.toObject(PostModel.class);
                        postModel.setId(doc.getId());
                        arrayList.add(postModel);
                    }
                    PostAdapter adapter = new PostAdapter(getActivity(), arrayList);
                    recyclerView.setAdapter(adapter);
                    adapter.setOnItemClickListener(new PostAdapter.OnItemClickListener() {
                        @Override
                        public void onClick(PostModel postModel) {
                            App.postModel = postModel;
                            Intent intent = new Intent(getActivity(), EditPostActivity.class);

                            // Start the activity
                            getActivity().startActivity(intent);
                        }
                    });
                }
                
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), "Failed to get all user list", Toast.LENGTH_SHORT).show();
            }
        });

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.collection("posts").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            ArrayList<PostModel> arrayList = new ArrayList<>();
                            for(QueryDocumentSnapshot doc: task.getResult()){
                                PostModel postModel=doc.toObject(PostModel.class);
                                postModel.setId(doc.getId());
                                arrayList.add(postModel);
                            }
                            PostAdapter adapter = new PostAdapter(getActivity(), arrayList);
                            recyclerView.setAdapter(adapter);
                            adapter.setOnItemClickListener(new PostAdapter.OnItemClickListener() {
                                @Override
                                public void onClick(PostModel postModel) {
                                    App.postModel = postModel;
                                    Intent intent = new Intent(getActivity(), EditPostActivity.class);

                                    // Start the activity
                                    getActivity().startActivity(intent);
                                }
                            });
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "Failed to get all user list", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }
}