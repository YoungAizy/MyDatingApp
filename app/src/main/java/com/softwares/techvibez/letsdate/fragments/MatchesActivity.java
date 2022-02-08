package com.softwares.techvibez.letsdate.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.softwares.techvibez.letsdate.MainActivity;
import com.softwares.techvibez.letsdate.R;
import com.softwares.techvibez.letsdate.adapters.ContactsAdapter;
import com.softwares.techvibez.letsdate.adapters.MatchesAdapter;
import com.softwares.techvibez.letsdate.chat.ChatActivity;
import com.softwares.techvibez.letsdate.database.Contacts;
import com.softwares.techvibez.letsdate.database.ui.LMViewModel;
import com.softwares.techvibez.letsdate.models.Matches;
import com.softwares.techvibez.letsdate.settings.SettingsActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class  MatchesActivity extends AppCompatActivity implements MatchesAdapter.MatchOnClick {
    private RecyclerView matches_recycler, contacts_recycler;
    private List<Contacts> contact;
    private List<Matches> matches;
    private MatchesAdapter adi1;
    private ContactsAdapter adi2;

    private SharedPreferences pref;

    private DatabaseReference db;
    private FirebaseUser user;
    private String uid, sex, chatId;

    private LMViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.softwares.techvibez.letsdate.R.layout.tab_chats);

        user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();
        db = FirebaseDatabase.getInstance().getReference("users");
        pref = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);

        sex = pref.getString("sex", "Male");

        matches = new ArrayList<>();
        contact = new ArrayList<>();

        matches_recycler = findViewById(com.softwares.techvibez.letsdate.R.id.matches_recycler);
        LinearLayoutManager layout = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        matches_recycler.setLayoutManager(layout);

        contacts_recycler = findViewById(R.id.contacts_recycler);
        LinearLayoutManager manager = new LinearLayoutManager(this, RecyclerView.VERTICAL, true);
        contacts_recycler.setLayoutManager(manager);

        //matches.add(new Matches("Andrew", R.drawable.pic3, "kUcal8"));
        //matches.add(new Matches("James", R.drawable.pic8, "kUcal8"));
        //matches.add(new Matches("Drake", R.drawable.pic4, "kUcal8"));
        //matches.add(new Matches("You", R.drawable.pic7, "kUcal8"));

        adi1 = new MatchesAdapter(this, matches, this);
        matches_recycler.setAdapter(adi1);
        adi2 = new ContactsAdapter(this, new ContactsAdapter.ClickEvent() {
            @Override
            public void onClick(int t) {
                Intent i = new Intent(MatchesActivity.this, ChatActivity.class);
                i.putExtra("name", matches.get(t).getName());
                i.putExtra("id", matches.get(t).getId());
                i.putExtra("url", matches.get(t).getUrl());
                startActivityForResult(i, 7);
            }
        });

        viewModel= ViewModelProviders.of(this).get(LMViewModel.class);
        viewModel.getContacts().observe(this, new Observer<List<Contacts>>() {
            @Override
            public void onChanged(List<Contacts> contacts) {
                adi2.setList(contacts);

                contact = contacts;
            }
        });

        Query();



        contacts_recycler.setAdapter(adi2);

    }

    public void Query(){
        DatabaseReference matchRef = db.child(sex).child(uid).child("Matches");
        matchRef.orderByChild("Match").equalTo(true).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                @Nullable
                String name, url;

                for (DataSnapshot doc : dataSnapshot.getChildren()){
                    if (doc.exists() && ! doc.child("Name").getValue().toString().isEmpty()){
                        Map<Object, Object> data = (Map) doc.getValue();


                        name = data.get("Name").toString();
                        String id = doc.getKey();

                        url = data.get("imageUrl").toString();
                        matches.add(new Matches(name, url, id));
                        adi1.notifyDataSetChanged();

                        Toast.makeText(MatchesActivity.this, url, Toast.LENGTH_SHORT).show();

                        //adi1.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MatchesActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null){
            chatId = data.getStringExtra("id");
            String text = data.getStringExtra("extra");
            String url =data.getStringExtra("url");
            String name = data.getStringExtra("name");
            Contacts contact = new Contacts(chatId, name, text, url);
            viewModel.insertContacts(contact);
            Toast.makeText(this, "data retrieved", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "Active", Toast.LENGTH_SHORT).show();
        }
    }



    public void Settings(View v){
        Intent i = new Intent(this, SettingsActivity.class);
        startActivity(i);
        finish();
    }

    public void Home(View v){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();

        super.onBackPressed();
    }

    @Override
    public void OnClick(int t) {
        Intent i = new Intent(this, ChatActivity.class);
        i.putExtra("name", matches.get(t).getName());
        i.putExtra("id", matches.get(t).getId());
        i.putExtra("url", matches.get(t).getUrl());
        startActivityForResult(i, 9);

    }
}
