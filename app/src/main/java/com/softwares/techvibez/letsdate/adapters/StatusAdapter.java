package com.softwares.techvibez.letsdate.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.softwares.techvibez.letsdate.R;
import com.softwares.techvibez.letsdate.models.Status;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class StatusAdapter extends RecyclerView.Adapter<StatusAdapter.MyViewHolder> {

    Context context;
    List<Status> statusList;
    OnClickListener listener;

    public StatusAdapter(Context context, List<Status> statusList, OnClickListener listener) {
        this.context = context;
        this.statusList = statusList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.dialog_status, parent, false), listener);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        holder.name.setText(statusList.get(position).getName());
        holder.city.setText(statusList.get(position).getCity());
        holder.msg.setText(statusList.get(position).getMsg());
        holder.dp.setImageResource(statusList.get(position).getImgId());

        holder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.like.setImageResource(R.drawable.ic_favorite_24dp);
            }
        });

    }

    @Override
    public int getItemCount() {
        return statusList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        CircleImageView dp;
        TextView name, city, msg;
        ImageView like;
        OnClickListener listener;
        Button comment;

        public MyViewHolder(@NonNull View itemView, final OnClickListener listener) {
            super(itemView);
            dp = itemView.findViewById(R.id.status_dp);
            name = itemView.findViewById(R.id.status_handler);
            city = itemView.findViewById(R.id.status_city);
            msg = itemView.findViewById(R.id.status_msg);
            like = itemView.findViewById(R.id.heart);
            comment = itemView.findViewById(R.id.comment);
            this.listener = listener;

            comment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(getAdapterPosition());
                }
            });

        }
    }

    public interface OnClickListener{
        void onClick(int position);
    }
}
