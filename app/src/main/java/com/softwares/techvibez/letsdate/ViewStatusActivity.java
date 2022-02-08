package com.softwares.techvibez.letsdate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.softwares.techvibez.letsdate.adapters.StatusAdapter;
import com.softwares.techvibez.letsdate.models.Status;

import java.util.ArrayList;
import java.util.List;

public class ViewStatusActivity extends AppCompatActivity implements StatusAdapter.OnClickListener{

    private List<Status> statusList;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_status);

        //dialog = new Dialog(this);

        statusList = new ArrayList<>();

        RecyclerView recycler = findViewById(R.id.status_recycler);
        recycler.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, true));

        statusList.add(new Status(R.drawable.pic3, "Josh", "Port Elizabeth", "Can't wait to start my weekend"));

        StatusAdapter adapter = new StatusAdapter(this, statusList, this);
        recycler.setAdapter(adapter);
    }


    @Override
    public void onClick(int position) {
       startActivity(new Intent(this, CommentActivity.class));

    }
}
