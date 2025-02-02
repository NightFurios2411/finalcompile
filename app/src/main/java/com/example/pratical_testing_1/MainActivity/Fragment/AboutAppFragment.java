package com.example.pratical_testing_1.MainActivity.Fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.pratical_testing_1.MainActivity.Adapter.AnimalLogAdapter;
import com.example.pratical_testing_1.R;
import com.example.pratical_testing_1.dataBind.Model.AnimalLog;
import com.example.pratical_testing_1.dataBind.SQLite.DatabaseHelper;
import com.example.pratical_testing_1.dataBind.SharedPreference.SharedPrefHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.List;

public class AboutAppFragment extends Fragment {

    private RecyclerView recyclerViewAnimals;
    private FloatingActionButton fabAddAnimal;
    private AnimalLogAdapter animalLogAdapter;
    private DatabaseHelper databaseHelper;
    private List<AnimalLog> animalLogList;
    private static final int PICK_IMAGE_REQUEST = 1;
    private int selectedPosition;
    View viewpopup;
    private EditText etEditAnimalName, etEditAnimalSpecies, etEditAnimalDescription, etEditAnimalLocation;

    private Button btnChangeImage, btnCancelEdit, btnUpdateAnimal;
    private ShapeableImageView imgEditAnimal;
    private Uri selectedImageUri;
    SharedPrefHelper sharedPrefHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_about_app_fragment, container, false);

        // Initialize RecyclerView and FAB
        recyclerViewAnimals = view.findViewById(R.id.recyclerViewAnimals);
        fabAddAnimal = view.findViewById(R.id.fabAddAnimal);

        // Initialize database helper
        databaseHelper = new DatabaseHelper(requireContext());
        sharedPrefHelper = new SharedPrefHelper(getContext());


        // Set up RecyclerView
        recyclerViewAnimals.setLayoutManager(new LinearLayoutManager(requireContext()));
        loadAnimalLogs(); // Load data into RecyclerView

        // Floating Action Button Click Listener
        fabAddAnimal.setOnClickListener(v -> {
            // Navigate to AddAnimalFragment
            Navigation.findNavController(view).navigate(R.id.to_addAnimalFragment);
        });

        return view;
    }

    // Method to fetch data from SQLite and display it in RecyclerView
    private void loadAnimalLogs() {
        animalLogList = databaseHelper.getAllAnimalLogsByUserId(Integer.parseInt(sharedPrefHelper.getKeyId()));
        animalLogAdapter = new AnimalLogAdapter(requireContext(), animalLogList, new AnimalLogAdapter.OnAnimalEditListener() {
            @Override
            public void onEditAnimal(AnimalLog animalLog, int position) {
                showEditAnimalDialog(animalLog, position);
            }

            @Override
            public void onImageSelect(int position) {
                selectedPosition = position;
                openGallery();
            }
        });

        recyclerViewAnimals.setAdapter(animalLogAdapter);
    }

    private void showEditAnimalDialog(AnimalLog animalLog, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        initializeUIAlert();
        builder.setView(viewpopup);

        etEditAnimalName.setText(animalLog.getName());
        etEditAnimalSpecies.setText(animalLog.getSpecies());
        etEditAnimalDescription.setText(animalLog.getDescription());
        etEditAnimalLocation.setText(animalLog.getLocation());

        if (animalLog.getImagePath() != null) {
            Glide.with(requireContext())
                    .load(animalLog.getImagePath())
                    .into(imgEditAnimal);
        }

        AlertDialog dialog = builder.create();
        dialog.show();

        btnChangeImage.setOnClickListener(view1 -> openGallery());

        btnUpdateAnimal.setOnClickListener(v -> {
            String newName = etEditAnimalName.getText().toString().trim();
            String newSpecies = etEditAnimalSpecies.getText().toString().trim();
            String newDescription = etEditAnimalDescription.getText().toString().trim();
            String newLocation = etEditAnimalLocation.getText().toString().trim();

            if (newName.isEmpty() || newSpecies.isEmpty() || newDescription.isEmpty()) {
                Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            animalLog.setName(newName);
            animalLog.setSpecies(newSpecies);
            animalLog.setDescription(newDescription);
            animalLog.setLocation(newLocation);

            // 🔥 Apply the new image path ONLY if the user has selected one
            if (selectedImageUri != null) {
                animalLog.setImagePath(selectedImageUri.toString());
            }

            boolean isUpdated = databaseHelper.updateAnimal(animalLog);
            if (isUpdated) {
                animalLogAdapter.notifyItemChanged(position);
                Toast.makeText(requireContext(), "Animal updated successfully!", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            } else {
                Toast.makeText(requireContext(), "Failed to update animal", Toast.LENGTH_SHORT).show();
            }
        });

        btnCancelEdit.setOnClickListener(v -> dialog.dismiss());
    }
    private void initializeUIAlert(){

        LayoutInflater inflater = LayoutInflater.from(requireContext());
        viewpopup = inflater.inflate(R.layout.editanimalpopup, null);

        etEditAnimalName = viewpopup.findViewById(R.id.etEditAnimalName);
        etEditAnimalSpecies = viewpopup.findViewById(R.id.etEditAnimalSpecies);
        etEditAnimalDescription = viewpopup.findViewById(R.id.etEditAnimalDescription);
        etEditAnimalLocation = viewpopup.findViewById(R.id.etEditAnimalLocation);
        imgEditAnimal = viewpopup.findViewById(R.id.imgEditAnimal);
        btnChangeImage = viewpopup.findViewById(R.id.btnChangeAnimalImage);
        btnCancelEdit = viewpopup.findViewById(R.id.btnCancelEdit);
        btnUpdateAnimal = viewpopup.findViewById(R.id.btnUpdateAnimal);

    }

    @Override
    public void onResume() {
        super.onResume();
        loadAnimalLogs(); // Refresh list when fragment is resumed
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            selectedImageUri = data.getData();
            imgEditAnimal.setImageURI(selectedImageUri); // Display Image in ImageView Alert Dialog
        }
    }


}