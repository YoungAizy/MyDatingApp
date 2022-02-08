package com.softwares.techvibez.letsdate.chat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.softwares.techvibez.letsdate.R;

import java.util.Collections;
import java.util.List;

public class MessagesAdapter extends RecyclerView.Adapter {
    private static  final int MESSAGE_SENT = 1;
    private static  final int MESSAGE_RECEIVED = 2;

    private Context context;
    private List<Chats> messages =  Collections.emptyList();
    private String ownerUid;

    public MessagesAdapter(Context context, List<Chats> messages, String ownerUid) {
        this.context = context;
        this.messages = messages;
        this.ownerUid = ownerUid;
    }

    public MessagesAdapter(Context context, String ownerUid) {
        this.context = context;
        this.ownerUid = ownerUid;
    }

    public void setList(List<Chats> messages){
       this.messages = messages;
       notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        Chats msg =  messages.get(position);

        if(msg.getSenderId().equals(ownerUid)){
            return MESSAGE_SENT;

        }else {
            return MESSAGE_RECEIVED;
        }
       // return 0;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v;

        if (i == MESSAGE_SENT){
            v = LayoutInflater.from(context).inflate(R.layout.sent_message, viewGroup, false);
            return new SentMessageHolder(v);
        }else{
            v= LayoutInflater.from(context).inflate(R.layout.received_message, viewGroup, false);
            return new ReceivedMessageHolder(v);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        Chats message = messages.get(i);

        switch (viewHolder.getItemViewType()){
            case MESSAGE_SENT:
                ((SentMessageHolder) viewHolder).bind(message);
                break;
            case MESSAGE_RECEIVED:
                ((ReceivedMessageHolder) viewHolder).bind(message);
        }

    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    private class ReceivedMessageHolder extends RecyclerView.ViewHolder{
        TextView received;

        ReceivedMessageHolder(@NonNull View itemView) {
            super(itemView);
            received = itemView.findViewById(R.id.recieved);
        }

        void bind(Chats message){
            received.setText(message.getText());
        }
    }

    private class SentMessageHolder extends RecyclerView.ViewHolder{
        TextView sent;

        SentMessageHolder(@NonNull View itemView) {
            super(itemView);
            sent = itemView.findViewById(R.id.sent);
        }

        void bind(Chats message){
            sent.setText(message.getText());

        }
    }
}
