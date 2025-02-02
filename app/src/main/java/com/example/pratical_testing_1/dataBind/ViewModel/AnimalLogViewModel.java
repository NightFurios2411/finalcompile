package com.example.pratical_testing_1.dataBind.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.pratical_testing_1.dataBind.Model.AnimalLog;
import com.example.pratical_testing_1.dataBind.SQLite.DatabaseHelper;

import java.util.List;

public class AnimalLogViewModel extends ViewModel {
    private MutableLiveData<List<AnimalLog>> logs = new MutableLiveData<>();

    public LiveData<List<AnimalLog>> getLogs() {
        return logs;
    }

    public void fetchLogs(DatabaseHelper db) {
        List<AnimalLog> logList = db.getAllAnimalLogs();
        logs.setValue(logList);
    }
}

