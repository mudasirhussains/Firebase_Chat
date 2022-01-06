package com.example.chatapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import com.example.chatapplication.activities.models.AllUsers;
import com.example.chatapplication.adapters.AllUsersAdapter;
import com.example.chatapplication.databinding.ActivityAllUsersBinding;
import com.example.chatapplication.listeners.UserListClick;
import com.example.chatapplication.utilities.Constants;
import com.example.chatapplication.utilities.PreferenceManager;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.messaging.FirebaseMessaging;
import java.util.ArrayList;
import java.util.List;

public class AllUsersActivity extends BaseActivity implements UserListClick {
    private ActivityAllUsersBinding binding;
    private PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAllUsersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        preferenceManager = new PreferenceManager(getApplicationContext());



        getAllUsers();
        clickListeners();

    }

    private void clickListeners() {
        binding.imageBack.setOnClickListener(v -> onBackPressed());
    }



    private void getAllUsers() {
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        database.collection(Constants.KEY_COLLECTION_USERS)
                .get()
                .addOnCompleteListener(task -> {
                    String currentUserId = preferenceManager.getString(Constants.KEY_USER_ID);
                    if (task.isSuccessful() && task.getResult() != null) {
                        List<AllUsers> users = new ArrayList<>();
                        for (QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()) {
                            if (currentUserId.equals(queryDocumentSnapshot.getId())) {
                                continue;
                            }
                            AllUsers allUsers = new AllUsers();
                            allUsers.name = queryDocumentSnapshot.getString(Constants.KEY_NAME);
                            allUsers.email = queryDocumentSnapshot.getString(Constants.KEY_EMAIL);
                            allUsers.token = queryDocumentSnapshot.getString(Constants.KEY_FCM_TOKEN);
                            allUsers.id = queryDocumentSnapshot.getId();
                            users.add(allUsers);
                        }
                        if (users.size() > 0) {
                            AllUsersAdapter allUsersAdapter = new AllUsersAdapter(users, this);
                            binding.recyclerAllUsers.setAdapter(allUsersAdapter);
                            Toast.makeText(getApplicationContext(), "User List Populated Successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Empty User List", Toast.LENGTH_SHORT).show();

                        }
                    }

                })
                .addOnFailureListener(e -> {

                });
    }



    @Override
    public void onUserClicked(AllUsers allUsers) {
        Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
        intent.putExtra(Constants.KEY_USER, allUsers);
        startActivity(intent);
    }

}