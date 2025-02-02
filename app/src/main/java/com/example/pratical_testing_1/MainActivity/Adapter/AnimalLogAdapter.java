package com.example.pratical_testing_1.MainActivity.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.pratical_testing_1.dataBind.Model.AnimalLog;
import com.example.pratical_testing_1.dataBind.SQLite.DatabaseHelper;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.imageview.ShapeableImageView;

import java.io.IOException;
import java.util.List;
import com.example.pratical_testing_1.R;

public class AnimalLogAdapter extends RecyclerView.Adapter<AnimalLogAdapter.CardViewHolder>  {

    private Context context;
    private List<AnimalLog> AnimalLogList;
    private OnAnimalEditListener animalEditListener;


    public interface OnAnimalEditListener {
        void onEditAnimal(AnimalLog animalLog, int position);
        void onImageSelect(int position);
    }

    public AnimalLogAdapter(
            Context context,
            List<AnimalLog> AnimalLogList,
            OnAnimalEditListener listener) {

        this.context = context;
        this.AnimalLogList = AnimalLogList;
        this.animalEditListener = listener;
    }

    @NonNull
    @Override
    public AnimalLogAdapter.CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_animal, parent, false);
        return new AnimalLogAdapter.CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnimalLogAdapter.CardViewHolder holder, int position) {
        AnimalLog animalLog = AnimalLogList.get(position);

        holder.tvAnimalName.setText(animalLog.getName());
        holder.tvAnimalSpecies.setText(animalLog.getSpecies());
        holder.tvAnimalLocation.setText(animalLog.getLocation());

        if (animalLog.getImagePath() != null) {
            Glide.with(context)
                    .load(animalLog.getImagePath())
                    .into(holder.imgAnimal); // Replace with your ImageView reference
        }

        holder.animalCard.setOnClickListener(view -> {
            if (animalEditListener != null) {
                animalEditListener.onEditAnimal(animalLog, holder.getAdapterPosition());
            }
        });

        holder.btnPlayAnimalSound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String audioPath = animalLog.getAudioPath();

                if (audioPath != null && !audioPath.isEmpty()) {
                    MediaPlayer mediaPlayer = new MediaPlayer();
                    try {
                        mediaPlayer.setDataSource(audioPath);
                        mediaPlayer.prepare();
                        mediaPlayer.start();
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(view.getContext(), "Error playing audio", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(view.getContext(), "No audio recorded", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return AnimalLogList.size();
    }

    public void setAnimalLogList(List<AnimalLog> NewAnimalLogList) {
        AnimalLogList.clear();
        AnimalLogList.addAll(NewAnimalLogList);
        notifyDataSetChanged();
    }

    public void appendAnimalLogList(List<AnimalLog> newAnimalLogList) {
        int previousSize = this.AnimalLogList.size();
        this.AnimalLogList.addAll(newAnimalLogList);  // Add new AnimalLogList to the existing list
        notifyItemRangeInserted(previousSize, newAnimalLogList.size()); // Notify for new range
    }
    public void appendComment(AnimalLog newAnimalLog) {
        int previousSize = this.AnimalLogList.size();
        this.AnimalLogList.add(newAnimalLog);  // Add new newAnimalLog to the existing list
        notifyItemRangeInserted(previousSize, 1); // Notify for new range
    }
    public static class CardViewHolder extends RecyclerView.ViewHolder {

        MaterialCardView animalCard;
        TextView tvAnimalName, tvAnimalSpecies, tvAnimalLocation;
        ShapeableImageView imgAnimal;
        Button btnPlayAnimalSound;

        public CardViewHolder(@NonNull View itemView) {
            super(itemView);
            animalCard = itemView.findViewById(R.id.animalCard);

            tvAnimalName = itemView.findViewById(R.id.tvAnimalName);
            tvAnimalSpecies = itemView.findViewById(R.id.tvAnimalSpecies);
            tvAnimalLocation = itemView.findViewById(R.id.tvAnimalLocation);

            imgAnimal = itemView.findViewById(R.id.imgAnimal);

            btnPlayAnimalSound = itemView.findViewById(R.id.btnPlayAnimalSound);

        }
    }

    public void updateSelectedImage(Uri imageUri, int position) {
        AnimalLog animalLog = AnimalLogList.get(position);
        animalLog.setImagePath(imageUri.toString());
        notifyItemChanged(position);
    }


}
