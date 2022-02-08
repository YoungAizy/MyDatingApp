package com.softwares.techvibez.letsdate.adapters;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.softwares.techvibez.letsdate.R;
import com.softwares.techvibez.letsdate.chat.ChatActivity;
import com.softwares.techvibez.letsdate.database.Contacts;

import java.util.Collections;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.VHolder> {
    private Context context;
    private List<Contacts> people = Collections.emptyList();
    private ClickEvent clickEvent;

    public ContactsAdapter(Context context, ClickEvent clickEvent) {
        this.context = context;
        this.clickEvent = clickEvent;
    }

    public void setList(List<Contacts> people){
        this.people = people;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_contact, parent, false);
        return new VHolder(v, clickEvent);
    }

    @Override
    public void onBindViewHolder(@NonNull VHolder holder, int position) {
        holder.name.setText(people.get(position).getName());
        holder.message.setText(people.get(position).getText());

        Glide.with(context)
                .load(people.get(position).getImgUrl()).override(360, 360).diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.mipmap.ic_launcher_round)
                .circleCrop()
                .into(holder.img);


    }

    @Override
    public int getItemCount() {
        return people.size();
    }

    static class VHolder extends RecyclerView.ViewHolder{
        private TextView name, message;
        private CircleImageView img;
        ClickEvent clickEvent;

        VHolder(View itemView, final ClickEvent clickEvent) {
            super(itemView);

            name = itemView.findViewById(R.id.name_text);
            message = itemView.findViewById(R.id.text_message);
            img = itemView.findViewById(R.id.abc);
            this.clickEvent = clickEvent;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickEvent.onClick(getAdapterPosition());
                }
            });

        }
    }

    public interface ClickEvent{
        void onClick(int t);
    }
}
