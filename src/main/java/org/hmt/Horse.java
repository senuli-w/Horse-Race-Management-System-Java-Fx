package org.hmt;

import javafx.scene.image.ImageView;

import java.io.Serializable;

/**
 * Class representing a horse.
 * Implements Serializable to allow objects of this class to be serialized.
 *
 * @author Senuli Wickramage
 */

public class Horse implements Serializable {

    // Fields
    private int id;
    private String horseName;
    private String jockeyName;
    private int horseAge;
    private String breed;
    private String raceRecord;
    private char group;
    private transient ImageView imageView; // Transient field to avoid serialization
    private transient int timing; // Transient field to avoid serialization
    private String imagePath;

    /**
     * Constructor to initialize a horse object.
     * @param id The ID of the horse.
     * @param horseName The name of the horse.
     * @param jockeyName The name of the jockey.
     * @param horseAge The age of the horse.
     * @param breed The breed of the horse.
     * @param raceRecord The race record of the horse.
     * @param group The group of the horse.
     */
    public Horse(int id, String horseName, String jockeyName, int horseAge, String breed, String raceRecord, char group) {
        this.id = id;
        this.horseName = horseName;
        this.jockeyName = jockeyName;
        this.horseAge = horseAge;
        this.breed = breed;
        this.raceRecord = raceRecord;
        this.group = group;
    }

    /**
     * Overrides the toString method to provide a string representation of the horse object.
     * @return A string representation of the horse object.
     */
    @Override
    public String toString() {
        return "Horse{" +
                "id=" + id +
                ", horseName='" + horseName + '\'' +
                ", jockeyName='" + jockeyName + '\'' +
                ", horseAge=" + horseAge +
                ", breed='" + breed + '\'' +
                ", raceRecord='" + raceRecord + '\'' +
                ", group=" + group +
                ", imagePath='" + imagePath + '\'' +
                '}';
    }

    // Getters and setters for fields
    // These methods provide access to private fields
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getHorseName() {
        return horseName;
    }
    public void setHorseName(String horseName) {
        this.horseName = horseName;
    }
    public String getJockeyName() {
        return jockeyName;
    }
    public void setJockeyName(String jockeyName) {
        this.jockeyName = jockeyName;
    }
    public int getHorseAge() {
        return horseAge;
    }
    public void setHorseAge(int horseAge) {
        this.horseAge = horseAge;
    }
    public String getBreed() {
        return breed;
    }
    public void setBreed(String breed) {
        this.breed = breed;
    }
    public String getRaceRecord() {
        return raceRecord;
    }
    public void setRaceRecord(String raceRecord) {
        this.raceRecord = raceRecord;
    }
    public char getGroup() {
        return group;
    }
    public void setGroup(char group) {
        this.group = group;
    }
    public ImageView getImageView() {
        return imageView;
    }
    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
        this.imageView.setFitWidth(100); // Set the fit width of the ImageView
        this.imageView.setPreserveRatio(true); // Set the preserve ratio property of the ImageView
        imagePath = this.imageView.getImage().getUrl(); // Retrieve and set the image path
    }
    public String getImagePath() {
        return imagePath;
    }
    public int getTiming() {
        return timing;
    }
    public void setTiming(int timing) {
        this.timing = timing;
    }
}
