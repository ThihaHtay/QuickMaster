package com.thiha.quickchat.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.thiha.quickchat.R;
import com.thiha.quickchat.model.PostModel;
import com.thiha.quickchat.utils.FirebaseUtil;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {
        Context context;
        ArrayList<PostModel> arrayList;

        OnItemClickListener onItemClickListener;

        public PostAdapter(Context context,ArrayList<PostModel> arrayList){
            this.context=context;
            this.arrayList=arrayList;
        }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view= LayoutInflater.from(context).inflate(R.layout.post_list_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(MessageFormat.format("{0} {1}",arrayList.get(position).getFirstName(),
                arrayList.get(position).getLastName()));
        holder.phone.setText(arrayList.get(position).getPhone());
        holder.bio.setText(arrayList.get(position).getBio());

        holder.timestamp.setText(FirebaseUtil.posttimestampToString(arrayList.get(position).getTimestamp()));

        holder.itemView.setOnClickListener(view -> onItemClickListener.onClick(arrayList.get(position)));
        int color_code = getRandomColor();
        holder.cardView.setCardBackgroundColor(holder.itemView.getResources().getColor(color_code,null));
    }
    private int getRandomColor(){
        List<Integer> colorCode = new ArrayList<>();

        colorCode.add(R.color.c1);
        colorCode.add(R.color.c2);
        colorCode.add(R.color.c3);
        colorCode.add(R.color.c4);
        colorCode.add(R.color.c7);
        colorCode.add(R.color.c6);

        Random random = new Random();
        int random_color = random.nextInt(colorCode.size());
        return colorCode.get(random_color);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
            TextView name,phone,bio,timestamp;
        MaterialCardView cardView;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                name=itemView.findViewById(R.id.list_item_name);
                phone=itemView.findViewById(R.id.list_item_phone);
                bio=itemView.findViewById(R.id.list_item_bio);
                timestamp=itemView.findViewById(R.id.timestamp);
                cardView=itemView.findViewById(R.id.cardId);
            }
        }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener{
            void onClick(PostModel postModel);
    }
}
