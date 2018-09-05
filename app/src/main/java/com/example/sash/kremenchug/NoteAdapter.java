package com.example.sash.kremenchug;

import android.app.LauncherActivity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.squareup.picasso.Picasso;

public class NoteAdapter extends FirestoreRecyclerAdapter<Upload, NoteAdapter.NoteHolder> {

    public NoteAdapter(@NonNull FirestoreRecyclerOptions<Upload> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull NoteHolder holder, int position, @NonNull Upload model) {
        holder.textViewTopic.setText(model.getTopic());
        holder.textViewText.setText(model.getText());

        Context mContext  = holder.imageView.getContext();
        Picasso.with(mContext).load(model.getImage()).centerCrop().fit().into(holder.imageView);
    }

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_item,parent, false);
        return new NoteHolder(v);
    }

    class NoteHolder extends RecyclerView.ViewHolder{
        TextView textViewTopic;
        TextView textViewText;
        ImageView imageView;

        public NoteHolder(View itemView) {
            super(itemView);

            textViewTopic = itemView.findViewById(R.id.text_view_topic);
            textViewText = itemView.findViewById(R.id.text_view_text);
            imageView = itemView.findViewById(R.id.image_view_upload);

        }
    }
}
