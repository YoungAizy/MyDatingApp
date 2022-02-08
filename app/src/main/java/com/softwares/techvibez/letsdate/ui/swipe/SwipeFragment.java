package com.softwares.techvibez.letsdate.ui.swipe;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;
import com.softwares.techvibez.letsdate.R;
import com.softwares.techvibez.letsdate.SampleActivity;
import com.softwares.techvibez.letsdate.adapters.arrayAdapter;
import com.softwares.techvibez.letsdate.fragments.MatchesActivity;
import com.softwares.techvibez.letsdate.models.TestModel;
import com.softwares.techvibez.letsdate.settings.SettingsActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SwipeFragment extends Fragment {

    private DrawerLayout drawer;
    private SwipeFlingAdapterView stackview;
    private List<TestModel> lists;
    private String id;

    private arrayAdapter adapter;

    private String uid;
    private String sexx;
    private SharedPreferences pref ;
    View root;

    public SwipeFragment() {
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.sample, container, false);

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        QueryMatches();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    private void QueryMatches(){

        drawer = root.findViewById(R.id.drawer_layout);
        stackview = root.findViewById(R.id.card_stack_view);

        pref = getActivity().getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        sexx = pref.getString("sex", "Male");

        final DatabaseReference db = FirebaseDatabase.getInstance().getReference("users");
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();


        lists = new ArrayList<>();
        TestModel.setOwnerId(uid);

        adapter = new arrayAdapter(getContext(), R.layout.item_spot, lists);

        stackview.setAdapter(adapter);
        stackview.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                lists.remove(0);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onLeftCardExit(Object o) {
                TestModel item = (TestModel) o;
                String name = item.getName();
                id = item.getUserId();

                Map<Object, Object> userInfo = new HashMap<>();
                userInfo.put("Name", name);

                db.child(sexx).child(uid).child("Nopes").child(id).setValue(userInfo);
                Toast.makeText(getContext(), "Disliked" + name, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onRightCardExit(Object o) {
                TestModel item = (TestModel) o;
                id = item.getUserId();
                String Name = item.getName();
                String imgUrl = item.getUrl();

                Map<String, Object> userInfo= new HashMap<>();
                userInfo.put("Name", Name);
                userInfo.put("imageUrl", imgUrl);
                userInfo.put("Match", true);

                //String key = FirebaseDatabase.getInstance().getReference("Chats").push().getKey();
                db.child(sexx).child(uid).child("Matches").child(id).setValue(userInfo);

                Toast.makeText(getContext(), "Liked " + Name + imgUrl, Toast.LENGTH_SHORT).show();
                //docRef.document(sexx).collection("UIDS").document(uid).collection("Matches").document(id).set(userInfo);
            }

            @Override
            public void onAdapterAboutToEmpty(int i) {

            }

            @Override
            public void onScroll(float v) {

            }
        });


        if (sexx.equals("Female")){
            //CollectionReference doc = docRef.document("Male").collection("UIDS");
            db.child("Male").orderByChild("Male").equalTo(true).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()){
                        String testName;
                        for (DataSnapshot data:
                             dataSnapshot.getChildren()) {
                            Map<Object, Object> info = (Map) data.getValue();
                            String url =info.get("Url").toString();
                            testName = info.get("Name").toString();
                            lists.add(new TestModel(testName, "Italy", url, data.getKey()));
                            adapter.notifyDataSetChanged();

                            //Toast.makeText(, "", Toast.LENGTH_SHORT).show();
                        }

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(getContext(), "Listen failed", Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            db.child("Female").orderByChild("Female").equalTo(true).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()){
                        String testName;
                        for (DataSnapshot data:
                                dataSnapshot.getChildren()) {
                            Map<Object, Object> info = (Map) data.getValue();
                            String url = info.get("Url").toString();
                            testName = info.get("Name").toString();
                            lists.add(new TestModel(testName, "Italy", url, data.getKey()));
                            adapter.notifyDataSetChanged();
                        }

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(getContext(), "Listen failed", Toast.LENGTH_SHORT).show();
                }
            });

        }
    }
}