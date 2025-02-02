package com.example.pratical_testing_1.MainActivity.Fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pratical_testing_1.MainActivity.utils.AudioHelper;
import com.example.pratical_testing_1.R;
import com.example.pratical_testing_1.dataBind.Model.AnimalLog;
import com.example.pratical_testing_1.dataBind.SQLite.DatabaseHelper;
import com.example.pratical_testing_1.dataBind.SharedPreference.SharedPrefHelper;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;


public class AddAnimalFragment extends Fragment {

    private EditText etAnimalName, etAnimalSpecies, etAnimalDescription;
    private ShapeableImageView imgAnimal;
    private TextView tvLocation;
    private Button btnRecordAudio, btnStopAudio, btnPlayAudio, btnGetLocation, btnSaveAnimal, btnImageAnimal;
    private String audioFilePath;
    private AudioHelper audioHelper;
    private DatabaseHelper databaseHelper;
    private FusedLocationProviderClient fusedLocationClient;
    private String currentLocation = "Not Captured";
    private Uri imageUri;
    SharedPrefHelper sharedPrefHelper;
    private ActivityResultLauncher<Intent> mediaPickerLauncher;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_animal, container, false);

        // Initialize UI elements
        etAnimalName = view.findViewById(R.id.etAnimalName);
        etAnimalSpecies = view.findViewById(R.id.etAnimalSpecies);
        etAnimalDescription = view.findViewById(R.id.etAnimalDescription);
        tvLocation = view.findViewById(R.id.tvLocation);
        btnRecordAudio = view.findViewById(R.id.btnRecordAudio);
        btnPlayAudio = view.findViewById(R.id.btnPlayAudio);
        btnGetLocation = view.findViewById(R.id.btnGetLocation);
        btnSaveAnimal = view.findViewById(R.id.btnSaveAnimal);
        imgAnimal = view.findViewById(R.id.imgAnimal);
        btnImageAnimal = view.findViewById(R.id.btnImageAnimal);
        btnStopAudio = view.findViewById(R.id.btnStopAudio);

        // Initialize database helper
        databaseHelper = new DatabaseHelper(requireContext());
        sharedPrefHelper = new SharedPrefHelper(getContext());

        // Initialize location client
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext());

        // Open Gallery
        btnImageAnimal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        // Initialize the Media Picker Launcher
        mediaPickerLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        imageUri = result.getData().getData(); // Get Image URI
                        imgAnimal.setImageURI(imageUri); // Display Image in ImageView
                    }
                }
        );

        // Set up audio file path based on timestamp
        String timestamp = String.valueOf(System.currentTimeMillis());
        File audioFile = new File(requireContext().getExternalFilesDir(null), "animal_sound_" + timestamp + ".3gp");
        audioFilePath = audioFile.getAbsolutePath();
        audioHelper = new AudioHelper(audioFilePath);

        // Record Audio
        btnRecordAudio.setOnClickListener(v -> {
            audioHelper.startRecording();
            Toast.makeText(getContext(), "Recording started...", Toast.LENGTH_SHORT).show();
        });

        // Stop Recording when button is clicked again
        btnStopAudio.setOnClickListener(v -> {
            audioHelper.stopRecording();
            Toast.makeText(getContext(), "Recording saved!", Toast.LENGTH_SHORT).show();
        });

        // Play Audio
        btnPlayAudio.setOnClickListener(v -> {
            if (new File(audioFilePath).exists()) {
                audioHelper.playAudio();
            } else {
                Toast.makeText(getContext(), "No recording found", Toast.LENGTH_SHORT).show();
            }
        });

        // Get Current Location
        btnGetLocation.setOnClickListener(v -> getCurrentLocation());

        // Save Animal Log
        btnSaveAnimal.setOnClickListener(v -> saveAnimal());

        return view;
    }

    // Method to get current location
    private void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }

        fusedLocationClient.getLastLocation().addOnSuccessListener(requireActivity(), location -> {
            if (location != null) {
                currentLocation = "Lat: " + location.getLatitude() + ", Lng: " + location.getLongitude();
                tvLocation.setText("Location: " + currentLocation);
            } else {
                tvLocation.setText("Location: Unable to fetch");
            }
        });
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        mediaPickerLauncher.launch(intent);
    }

    // Method to save animal data
    private void saveAnimal() {
        String name = etAnimalName.getText().toString().trim();
        String species = etAnimalSpecies.getText().toString().trim();
        String description = etAnimalDescription.getText().toString().trim();

        if (name.isEmpty() || species.isEmpty() || description.isEmpty()) {
            Toast.makeText(getContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        String imagePath = (imageUri != null) ? imageUri.toString() : "No Image";

        boolean isInserted = databaseHelper.insertAnimal(
                name,
                species,
                description,
                audioFilePath,
                currentLocation,
                imagePath,
                sharedPrefHelper.getKeyId()
        );

        if (isInserted) {
            etAnimalName.setText("");
            etAnimalSpecies.setText("");
            etAnimalDescription.setText("");
            imageUri = null;  // Reset the image URI
            imgAnimal.setImageResource(0);
            audioFilePath = "";


            Snackbar.make(getView(), "Animal saved successfully!", Snackbar.LENGTH_LONG).show();

        } else {
            Snackbar.make(getView(), "Animal saved successfully!", Snackbar.LENGTH_LONG).show();

        }
    }
}