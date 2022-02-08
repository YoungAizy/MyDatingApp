package com.softwares.techvibez.letsdate.ui.home;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.softwares.techvibez.letsdate.CommentActivity;
import com.softwares.techvibez.letsdate.PostActivity;
import com.softwares.techvibez.letsdate.R;
import com.softwares.techvibez.letsdate.ViewStatusActivity;
import com.softwares.techvibez.letsdate.adapters.MatchesAdapter;
import com.softwares.techvibez.letsdate.adapters.NearestPeopleAdapter;
import com.softwares.techvibez.letsdate.adapters.NewPeopleAdapter;
import com.softwares.techvibez.letsdate.adapters.SliderAdapter;
import com.softwares.techvibez.letsdate.models.Images;
import com.softwares.techvibez.letsdate.models.Matches;
import com.softwares.techvibez.letsdate.models.NearestPeople;
import com.softwares.techvibez.letsdate.models.NewPeople;
import com.softwares.techvibez.letsdate.models.TestModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HomeFragment extends Fragment {

    private List<NewPeople> newestList;
    private List<Matches> onlineList, followingList, followersList;
    private List<NearestPeople> nearestPeople;
    private NearestPeopleAdapter nearestPeopleAdapter;
    private MatchesAdapter adi1, adi3, adi4;
    private NewPeopleAdapter adi2;

    private HomeViewModel homeViewModel;
    private DatabaseReference ref;

    Dialog dialog;
    ViewPager viewPager;
    SliderAdapter sliderAdapter;
    List<Images> pics;

    String uid;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home2, container, false);

        Button button = view.findViewById(R.id.view_status);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ViewStatusActivity.class));
            }
        });

        FloatingActionButton addStatus = view.findViewById(R.id.fab_add);
        addStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), PostActivity.class));
            }
        });

        RecyclerView postsRecycler = view.findViewById(R.id.posts_recycler);
        RecyclerView newestRecycler = view.findViewById(R.id.new_acc_recycler);
        RecyclerView onlineRecycler = view.findViewById(R.id.online_recycler);
        RecyclerView followingRecycler = view.findViewById(R.id.following_recycler);
        RecyclerView followersRecycler = view.findViewById(R.id.followers_recycler);

        newestList = new ArrayList<>();
        nearestPeople = new ArrayList<>();
        onlineList = new ArrayList<>();
        followingList = new ArrayList<>();
        followersList = new ArrayList<>();

        postsRecycler.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        newestRecycler.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        onlineRecycler.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        followersRecycler.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        followingRecycler.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));

        nearestPeople.add(new NearestPeople("Josh", "Can't wait to start my weekend!", "pkd", R.drawable.pic3));
        newestList.add(new NewPeople("Brad", null, "kml78", R.drawable.pic6));
        onlineList.add(new Matches("Ted", "kdh5" ,R.drawable.pic2));

        nearestPeopleAdapter = new NearestPeopleAdapter(getContext(), nearestPeople, new NearestPeopleAdapter.ClickListener() {
            @Override
            public void onClick(int position) {
                 Dialog();
            }
        });
        postsRecycler.setAdapter(nearestPeopleAdapter);
        adi1 = new MatchesAdapter(getContext(), onlineList, new MatchesAdapter.MatchOnClick() {
            @Override
            public void OnClick(int t) {
                Profile();

            }
        });
        onlineRecycler.setAdapter(adi1);
        adi2 = new NewPeopleAdapter(getContext(), newestList, new NewPeopleAdapter.ClickListener() {
            @Override
            public void onClick(int t) {
                ViewProfile();
            }
        });
        newestRecycler.setAdapter(adi2);
        adi3 = new MatchesAdapter(getContext(), followingList, new MatchesAdapter.MatchOnClick() {
            @Override
            public void OnClick(int t) {
                Profile();

            }
        });
        followingRecycler.setAdapter(adi3);
        adi4 = new MatchesAdapter(getContext(), followersList, new MatchesAdapter.MatchOnClick() {
            @Override
            public void OnClick(int t) {
                ViewProfile();
            }
        });
        followersRecycler.setAdapter(adi4);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dialog = new Dialog(getContext());
        pics = new ArrayList<>();
        sliderAdapter = new SliderAdapter(getContext(), pics);

        ref = FirebaseDatabase.getInstance().getReference("Posts");

        uid = FirebaseAuth.getInstance().getUid();

        getPosts();
    }

    private void getPosts(){
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    for (DataSnapshot d:
                         dataSnapshot.getChildren()) {

                        String url = d.child("profilePic").getValue().toString();
                        String msg = d.child("status").getValue().toString();
                        String name = d.child("name").getValue().toString();
                        nearestPeople.add(new NearestPeople(name, msg, url, "owner"));
                        nearestPeopleAdapter.notifyDataSetChanged();

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void Dialog(){
        dialog.setContentView(R.layout.dialog_status);

        final ImageView like = dialog.findViewById(R.id.heart);
        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                like.setImageResource(R.drawable.ic_favorite_24dp);
            }
        });

        Button comment = dialog.findViewById(R.id.comment);
        comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                startActivity(new Intent(getContext(), CommentActivity.class));
            }
        });

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    private void ViewProfile(){
        dialog.setContentView(R.layout.profile_preview);
        Button follow = dialog.findViewById(R.id.follow_btn);
        ImageView like = dialog.findViewById(R.id.like_btn);
        follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "You are now following the user", Toast.LENGTH_SHORT).show();
            }
        });
        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Liked", Toast.LENGTH_SHORT).show();
            }
        });



        dialog.show();
    }

    private void Profile(){
        dialog.setContentView(R.layout.dialog_profile);
        Button btn = dialog.findViewById(R.id.view_pictures_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                ViewPics();
            }
        });

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    private void ViewPics(){
        dialog.setContentView(R.layout.popup_images);
        Button close = dialog.findViewById(R.id.close_popup);
        //TextView current = dialog.findViewById(R.id.cureent_pic);
        //TextView all = dialog.findViewById(R.id.pics_all);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        viewPager = dialog.findViewById(R.id.popup_view_pager);

        pics.add(new Images(R.drawable.pic4));
        pics.add(new Images(R.drawable.pic2));
        viewPager.setAdapter(sliderAdapter);

        //current.setText(item);
        //all.setText(pics.size());

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }
}