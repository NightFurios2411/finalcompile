package com.example.pratical_testing_1.dataBind.Cache;

import com.example.pratical_testing_1.dataBind.Model.AnimalLog;

import java.util.ArrayList;
import java.util.List;

public class AnimalLogCache {
    private static AnimalLogCache instance;
    private List<AnimalLog> AnimalLogList;

    private AnimalLogCache() {
        AnimalLogList = new ArrayList<>();
    }

    public static synchronized AnimalLogCache getInstance() {
        if (instance == null) {
            instance = new AnimalLogCache();
        }
        return instance;
    }
    public List<AnimalLog> getAnimalLog() {
        return AnimalLogList;
    }

    public void setAnimalLogList(List<AnimalLog> newAnimalLogList) {
        this.AnimalLogList.clear();
        this.AnimalLogList.addAll(newAnimalLogList);
    }

    public void addAnimalLog(AnimalLog newAnimalLog) {
        AnimalLogList.add(0, newAnimalLog); //new post to first index
    }

    public boolean isEmpty() {
        return AnimalLogList.isEmpty();
    }

    public void clearCache() {
        AnimalLogList.clear();
    }

}
