package com.example.pratical_testing_1.dataBind.Model;


public class AnimalLog {
    private int id;
    private String name;
    private String species;
    private String description;
    private String audioPath;
    private String location;
    private String imagePath;

    public int getAnimalid() {
        return animalid;
    }

    public void setAnimalid(int animalid) {
        this.animalid = animalid;
    }

    private int animalid;

    // Constructor
    public AnimalLog(int id, String name, String species, String description, String audioPath, String location, String imagePath, int animalid) {
        this.id = id;
        this.name = name;
        this.species = species;
        this.description = description;
        this.audioPath = audioPath;
        this.location = location;
        this.imagePath = imagePath;
        this.animalid = animalid;
    }

    // Empty Constructor (for SQLite)
    public AnimalLog() {}

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAudioPath() {
        return audioPath;
    }

    public void setAudioPath(String audioPath) {
        this.audioPath = audioPath;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @Override
    public String toString() {
        return "AnimalLog{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", species='" + species + '\'' +
                ", description='" + description + '\'' +
                ", audioPath='" + audioPath + '\'' +
                ", location='" + location + '\'' +
                ", imagePath='" + imagePath + '\'' +
                '}';
    }
}

