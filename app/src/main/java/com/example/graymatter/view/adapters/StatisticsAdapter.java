package com.example.graymatter.view.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.graymatter.R;
import com.example.graymatter.view.FragmentChangeListener;

//TODO This is basically a copy of RecyclerViewAdapter, so should prob do sum smart abstractions
// instead
public class StatisticsAdapter extends RecyclerView.Adapter<StatisticsAdapter.StatisticsViewHolder> {
    private final String[] nameArray;
    private final String[] colors;
    private final int[] logos;
    private final Context context;
    private FragmentChangeListener listener;

    public StatisticsAdapter (Context context, String[] gameName, String[] colors, int[] logos) {
        this.context = context;
        nameArray = gameName;
        this.logos = logos;
        this.colors = colors;
    }

    /**
     * Initializes the ViewHolders, an object that hold the view and represents it
     * @return it returns the viewholder
     */
    @NonNull
    @Override
    public StatisticsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.statistics_game_card, parent, false);

        listener = (FragmentChangeListener) parent.getContext();

        return new StatisticsViewHolder(view);
    }

    /**
     * This method is called for each ViewHolder to bind it to the adapter
     * @param holder represents the specific viewHolder
     * @param position represents the position of the view
     */
    @Override
    public void onBindViewHolder(@NonNull StatisticsViewHolder holder, final int position) {
        holder.title.setText(nameArray[position]);
        holder.logo.setImageResource(logos[position]);
        holder.mainCard.setBackgroundColor(Color.parseColor(colors[position]));

        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if (position == 0)
                {
                    listener.reactionTestClicked();
                }
                else if (position == 1)
                {
                    listener.chimpTestClicked();
                }
                else if (position == 2)
                {
                    listener.visualGameClicked();
                }
                else if (position == 3)
                {
                    listener.ToHClicked();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return logos.length;
    }

    /**
     * a class that navigate to the necessary components that are being modified
     */
    public class StatisticsViewHolder extends RecyclerView.ViewHolder{
        private final TextView title;
        private final ImageView logo;
        private final LinearLayout mainCard;
        private final ConstraintLayout mainLayout;

        public StatisticsViewHolder (@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.statisticsGamesTxt);
            logo = itemView.findViewById(R.id.statisticsGameLogo);
            mainCard = itemView.findViewById(R.id.statisticsMainCard);
            mainLayout = itemView.findViewById(R.id.statisticsMainLayout);
        }
    }


}