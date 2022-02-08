package com.softwares.techvibez.letsdate;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.softwares.techvibez.letsdate.chat.Chats;

import java.util.List;

public class Messages extends FirestoreRecyclerAdapter<Chats, Messages.MyViewHolder> {

    private String ownerUid;
    private Context context;
    List<Chats> messages;

    public Messages(@NonNull FirestoreRecyclerOptions<Chats> options, String ownerUid, Context context, List<Chats> messages) {
        super(options);
        this.ownerUid = ownerUid;
        this.context = context;
        this.messages = messages;
    }

    @Override
    protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull Chats model) {

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
