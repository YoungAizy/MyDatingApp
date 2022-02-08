package com.softwares.techvibez.letsdate.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.softwares.techvibez.letsdate.R;
import com.softwares.techvibez.letsdate.adapters.MatchesAdapter;
import com.softwares.techvibez.letsdate.adapters.NearestPeopleAdapter;
import com.softwares.techvibez.letsdate.adapters.NewPeopleAdapter;
import com.softwares.techvibez.letsdate.models.Matches;
import com.softwares.techvibez.letsdate.models.NearestPeople;
import com.softwares.techvibez.letsdate.models.NewPeople;

import java.util.ArrayList;
import java.util.List;

public class HomeFrag extends Fragment {

    RecyclerView nearestRecycler, onlineRecycler, newestRecycler;
    private List<NewPeople> newestList;
    private List<Matches> onlineList;
    private List<NearestPeople> nearestPeople;
    private NearestPeopleAdapter nearestPeopleAdapter;
    private MatchesAdapter adi1;
    private NewPeopleAdapter adi2;


    public HomeFrag() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home2, container, false);

        return view;
    }


}
