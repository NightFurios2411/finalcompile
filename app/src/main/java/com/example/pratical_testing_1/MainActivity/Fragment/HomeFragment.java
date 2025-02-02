package com.example.pratical_testing_1.MainActivity.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.pratical_testing_1.AuthActivity.AuthActivity;
import com.example.pratical_testing_1.MainActivity.MainActivity;
import com.example.pratical_testing_1.R;
import com.example.pratical_testing_1.dataBind.SharedPreference.SharedPrefHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeFragment extends Fragment {

    Button buttonCat, buttonDog, buttonLogout;
    SharedPrefHelper sharedPrefHelper;
    TextView heading;
    private ActivityResultLauncher<Intent> mediaPickerLauncher;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        buttonCat = view.findViewById(R.id.buttonCat);
        buttonDog = view.findViewById(R.id.buttonDog);

        heading = view.findViewById(R.id.heading);
        buttonLogout = view.findViewById(R.id.logout);

        sharedPrefHelper = new SharedPrefHelper(getContext());
        heading.setText(sharedPrefHelper.getUserName());


        buttonCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.to_catFragment);
            }
        });
        buttonDog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.to_dogFragment);
            }
        });

        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPrefHelper.clearProfileData();
                Intent intent = new Intent(getActivity(), AuthActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });




        return view;
    }
}