package com.example.graymatter.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.graymatter.R;

public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.FirendsViewHolder> {

    private String[] names;
    private int[] images;
    private Context context;

    public FriendsAdapter(Context context, String[] names, int[] images){
        this.names = names;
        this.images = images;
        this.context = context;
    }


    @NonNull
    @Override
    public FirendsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_friend, parent, false);
        return new FirendsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FirendsViewHolder holder, int position) {
        holder.textView.setText(names[position]);
        //holder.imageView.setImageResource(images[position]);
    }

    @Override
    public int getItemCount() {
        return names.length;
    }




    public class FirendsViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        ImageView imageView;

        public FirendsViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.friendsNameTextView);
            imageView = itemView.findViewById(R.id.friendsImageView);
        }
    }
}
