package com.example.chatapplication.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatapplication.activities.models.AllUsers;
import com.example.chatapplication.activities.models.ChatMessage;
import com.example.chatapplication.databinding.ListitemAllUserBinding;
import com.example.chatapplication.listeners.UserListClick;

import java.util.List;

public class AllUsersAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private final List<AllUsers> allUsers;
    private final UserListClick userListener;

    public AllUsersAdapter(List<AllUsers> allUsers, UserListClick userListener) {
        this.allUsers = allUsers;
        this.userListener = userListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AllUsersAdapter.UsersViewHolder(
                ListitemAllUserBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((AllUsersAdapter.UsersViewHolder) holder).setData(allUsers.get(position));
    }

    @Override
    public int getItemCount() {
        return allUsers.size();
    }

    class UsersViewHolder extends RecyclerView.ViewHolder {

        private final ListitemAllUserBinding binding;

        UsersViewHolder(ListitemAllUserBinding listitemAllUserBinding) {
            super(listitemAllUserBinding.getRoot());
            binding = listitemAllUserBinding;
        }

       public void setData(AllUsers allUsers) {
            binding.textAllUserName.setText(allUsers.name);
            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    userListener.onUserClicked(allUsers);
                }
            });
        }
    }
}
