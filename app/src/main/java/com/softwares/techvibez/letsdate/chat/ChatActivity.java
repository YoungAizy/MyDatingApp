package com.softwares.techvibez.letsdate.chat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.softwares.techvibez.letsdate.R;
import com.softwares.techvibez.letsdate.database.ui.ChatViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class ChatActivity extends AppCompatActivity {

    private EditText input;
    private List<Chats> message;

    private MessagesAdapter chatsAdapter;

    private CollectionReference db;
    private ChatViewModel viewModel;

    private String chatId, UserId, ChatID,name, last, contactId, url;

    Intent replyIntent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Toolbar toolbar = findViewById(R.id.chat_toolbar);
        setSupportActionBar(toolbar);

        SharedPreferences pref = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);

        message = new ArrayList<>();

        replyIntent = new Intent();

        String sex = pref.getString("sex", "Male");

        //UserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        UserId = "owner";
        name = getIntent().getExtras().get("name").toString();
        contactId = getIntent().getExtras().get("id").toString();
        url = getIntent().getExtras().get("url").toString();
        chatId = UserId + "+" + contactId;

        Toast.makeText(this, chatId, Toast.LENGTH_SHORT).show();

        /*String url = getIntent().getExtras().get("imgUrl").toString();
        ChatID = chatId + UserId;
        db = FirebaseFirestore.getInstance().collection("users").document(sex).collection("UIDS")
                .document(UserId).collection("Contacts");*/


        input = findViewById(R.id.message_input);
        TextView Chat_Name = findViewById(R.id.chat_name);
        Chat_Name.setText(name);

        RecyclerView messages = findViewById(R.id.chat_recycler);
        LinearLayoutManager linear = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        messages.setLayoutManager(linear);

        chatsAdapter = new MessagesAdapter(this,"owner") ;

        viewModel = ViewModelProviders.of(this).get(ChatViewModel.class);
        viewModel.getChat(chatId).observe(this, new Observer<List<Chats>>() {
            @Override
            public void onChanged(List<Chats> chats) {
                chatsAdapter.setList(chats);
            }
        });
        messages.setAdapter(chatsAdapter);

        //checkMessages();
    }


    public void Send(View v){
        String text = input.getText().toString();
        //UploadMessage(text);
        Chats chat = new Chats(0, "owner",text, chatId);
        viewModel.insert(chat);
        //message.add(new Chats(text, "owner"));
        //chatsAdapter.notifyDataSetChanged();
        last = text;
        input.setText("");
        //contacts.add(new Contacts(name, null, chatId, text));
    }

    public void Go(View v) {
        //startActivity(new Intent(this, MatchesActivity.class));
        //finish();
    }

    @Override
    public void onBackPressed() {

        //Toast.makeText(this, "Back pressed", Toast.LENGTH_SHORT).show();
        if (last != null){

            replyIntent.putExtra("extra", last);
            replyIntent.putExtra("id", contactId);
            replyIntent.putExtra("url", url);
            replyIntent.putExtra("name", name);
            setResult(RESULT_OK, replyIntent);
        }

        super.onBackPressed();
    }

    public void UploadMessage(String s1){
        int random = new Random().nextInt(300);
        String messageID = UserId + random;

        DocumentReference data = db.document(chatId).collection(ChatID).document(messageID);

        Map<String, Object> messages = new HashMap<>();

        messages.put("Id", UserId);
        messages.put("message", s1);
        messages.put("active", true);

        data.set(messages).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(ChatActivity.this, "Message Uploaded", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ChatActivity.this, "Message Upload Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void checkMessages(){

        db.document(chatId).collection(ChatID).whereEqualTo("active", true).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @javax.annotation.Nullable FirebaseFirestoreException e) {
                if ( e != null){
                    Toast.makeText(ChatActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    return;
                }

                for (QueryDocumentSnapshot doc : queryDocumentSnapshots){
                    if (doc.get("Id") != null){
                        String msg = doc.getString("message");
                        String id = doc.getString("Id");


                        message.add(new Chats(msg, id ));

                    }
                }
            }
        });

    }
}
