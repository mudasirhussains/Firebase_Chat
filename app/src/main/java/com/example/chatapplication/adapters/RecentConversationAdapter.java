package com.example.chatapplication.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatapplication.activities.models.AllUsers;
import com.example.chatapplication.activities.models.ChatMessage;
import com.example.chatapplication.databinding.ListContainerRecentConversationBinding;
import com.example.chatapplication.databinding.ListitemAllUserBinding;
import com.example.chatapplication.listeners.ConversationListener;
import com.example.chatapplication.listeners.UserListClick;

import java.util.List;

public class RecentConversationAdapter extends RecyclerView.Adapter<RecentConversationAdapter.ConversationViewHolder> {
    private final List<ChatMessage> chatMessages;
    private final ConversationListener conversationListener;

    public RecentConversationAdapter(List<ChatMessage> chatMessages, ConversationListener conversationListener) {
        this.chatMessages = chatMessages;
        this.conversationListener = conversationListener;
    }


    @NonNull
    @Override
    public ConversationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecentConversationAdapter.ConversationViewHolder(
                ListContainerRecentConversationBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ConversationViewHolder holder, int position) {
        ((RecentConversationAdapter.ConversationViewHolder) holder).setData(chatMessages.get(position));
    }


    @Override
    public int getItemCount() {
        return chatMessages.size();
    }

    class ConversationViewHolder extends RecyclerView.ViewHolder {

        ListContainerRecentConversationBinding binding;

        ConversationViewHolder(ListContainerRecentConversationBinding listContainerRecentConversationBinding) {
            super(listContainerRecentConversationBinding.getRoot());
            binding = listContainerRecentConversationBinding;
        }

        public void setData(ChatMessage chatMessages) {
            binding.textRecentUserName.setText(chatMessages.conversationName);
            binding.textRecentMessage.setText(chatMessages.message);
            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AllUsers users = new AllUsers();
                    users.id = chatMessages.conversationId;
                    users.name = chatMessages.conversationName;
                    conversationListener.onUserClicked(users);
                }
            });
        }
    }
}
