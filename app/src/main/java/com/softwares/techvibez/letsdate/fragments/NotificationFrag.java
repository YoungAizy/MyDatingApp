package com.softwares.techvibez.letsdate.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.softwares.techvibez.letsdate.R;

public class NotificationFrag extends Fragment {

    private TextView notificationType, notificationFrom;

    public NotificationFrag() {
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

        View v = inflater.inflate(R.layout.fragment_notification, container, false);
        notificationType = v.findViewById(R.id.notification_type);
        notificationFrom = v.findViewById(R.id.notification_from);

        return v;
    }

}
