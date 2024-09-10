package org.hmt;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Class responsible for managing horses and the race.
 *
 * @author Senuli Wickramage
 */

public class HorseManager implements Manager {

    private static HorseManager instance = null; // Singleton instance
    private ArrayList<Horse> horseList; // List to store horses
    private int[] groupCounts; // Array to keep track of horse counts in each group
    private final int size; // Size of the race (number of horses per each group)

    // Flags to track race status
    private boolean raceStarted;
    private boolean finalRoundStarted;
    private boolean finalRoundOver;

    private Horse[] finalRoundHorses; // Array to store horses in the final round
    private Horse updatingHorse; // Reference to the horse being updated (during an update operation)
    private final String fileName = "horseDetails.ser"; // File name for serialization

    // Private constructor for the singleton instance
    private HorseManager(){
        size = 4;
        horseList = new ArrayList<>();
        groupCounts = new int[]{0, 0, 0, 0};
        raceStarted = false;
        finalRoundStarted = false;
        finalRoundOver = false;
        finalRoundHorses = new Horse[4];
    }

    // Singleton getInstance method
    public static HorseManager getInstance() {
        if (instance == null) {
            instance = new HorseManager();
        }
        return instance;
    }

    // Other methods that implements the Manager interface
    public boolean isIdTaken(int id){
        for (Horse horse : horseList){
            if(horse.getId() == id){
                return true;
            }
        }
        return false;
    }

    public boolean isGroupCountExceeded(char group){
        int groupCount = 0;
        for (Horse horse : horseList){
            if (horse.getGroup() == group){
                groupCount ++;
            }
        }
        return groupCount >= size; // retunrs true if groupcount is exceeded
    }

    public Horse getHorseById(int id){
        for (Horse horse : horseList){
            if (horse.getId() == id){
                return horse;
            }
        }
        return null;
    }

    public ArrayList<Horse> getHorsesInGroup(char group){
        ArrayList<Horse> newHorseList = new ArrayList<>();

        // if the horse is in the specific group added to the returning arraylist
        for(Horse horse : horseList){
            if (horse.getGroup() == group){
                newHorseList.add(horse);
            }
        }
        return newHorseList;
    }

    // called everytime a change to the horselist is occured (add, update, delete)
    // to keep track of the current horses in the race
    public void updateGroupCounts(){
        groupCounts[0] = 0;
        groupCounts[1] = 0;
        groupCounts[2] = 0;
        groupCounts[3] = 0;
        for(Horse horse : horseList){
            if(horse.getGroup() == 'A'){
                groupCounts[0]++;
            }
            if(horse.getGroup() == 'B'){
                groupCounts[1]++;
            }
            if(horse.getGroup() == 'C'){
                groupCounts[2]++;
            }
            if(horse.getGroup() == 'D'){
                groupCounts[3]++;
            }
        }
    }

    // gives out an array list of horses arranged according to the group A B C D order
    public ArrayList<Horse> getHorsesGroupWise(){
        char[] groups = {'A', 'B', 'C', 'D'};
        ArrayList <Horse> horseListInGroupOrder = new ArrayList<>();
        for(char group : groups){
            horseListInGroupOrder.addAll(instance.getHorsesInGroup(group));
            // getting all the horses in the group and adding it to the returning list
        }
        return horseListInGroupOrder;
    }

    public ArrayList<Horse> sortHorses(){
        int n = horseList.size();
        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                if (horseList.get(j).getId() < horseList.get(minIndex).getId()) {
                    minIndex = j;
                }
            }
            // Swap the found minimum element with the first element
            Horse temp = horseList.get(minIndex);
            horseList.set(minIndex, horseList.get(i));
            horseList.set(i, temp);
        }
        return horseList;
    }

    public void serializeHorseList(){
        // get the list of horses in group order
        ArrayList<Horse> horseListInGroupOrder = getHorsesGroupWise();
        try{
            // Create a FileOutputStream to write to the file
            FileOutputStream fileOutputStream = new FileOutputStream(fileName);
            // Create an ObjectOutputStream to write objects to the FileOutputStream
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

            // Write the serialized list of horses to the ObjectOutputStream
            objectOutputStream.writeObject(horseListInGroupOrder);
            objectOutputStream.close();
            fileOutputStream.close();
        } catch (Exception e){
            // Print stack trace if an exception occurs during serialization
            e.printStackTrace();
        }
    }

    public ArrayList<Horse> deserializeHorseList(){
        ArrayList<Horse> deserializedHorseList = null;
        try{
            // Create a FileInputStream to read from the file
            FileInputStream fileInputStream = new FileInputStream(fileName);
            // Create an ObjectInputStream to read objects from the FileInputStream
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            // Read the serialized list of horses from the ObjectInputStream
            deserializedHorseList = (ArrayList<Horse>) objectInputStream.readObject();
            objectInputStream.close();
            fileInputStream.close();
        } catch (Exception e){
            e.printStackTrace();
        }
        return deserializedHorseList;
    }

    public String addOrUpdateHorses(String id, String name, String jockey, String age, String breed, String raceRecord, String group, boolean update){

        if(instance.isRaceStarted()){
            return ("Race Started! Unable to do any changes now!");
        }

        // creating new variables to store the validated information since all the parameters were taken in as Strings
        int newId, newAge;
        char newGroup;

        // ID validation
        try{
            if(id.trim().isEmpty()){
                return "Enter Id.";
            }
            newId = Integer.parseInt(id);
            if (newId < 0){
                return "Invalid Id.";
            }
            if(!update){
                if(instance.isIdTaken(newId)){
                    return "This Id is already taken.";
                }
            }
        } catch (NumberFormatException e){ // case of entering a character for the id
            return "Invalid Id.";
        }

        // ensuring no input is empty
        // Name validation
        if(name.trim().isEmpty()){
            return "Enter Name.";
        }

        // age validation
        try{
            if(age.trim().isEmpty()){
                return "Enter Age.";
            }
            newAge = Integer.parseInt(age);
        } catch (Exception e){
            return "Invalid Age.";
        }
        if(newAge < 1){
            return "Invalid Age.";
        }

        // breed validation
        if(breed.trim().isEmpty()){
            return "Enter Breed.";
        }

        // jockey name validation
        if(jockey.trim().isEmpty()){
            return "Enter Jockey Name.";
        }

        // race record validation
        if(raceRecord.trim().isEmpty()){
            return "Enter Race Record.";
        }

        // group validation
        group = group.trim().toUpperCase();
        if (group.length() == 1 && "ABCD".contains(group)) {
            newGroup = group.charAt(0);
        } else {
            return ("Enter A or B or C or D for the group.");
        }

        // if it is an updating operation
        // if the user has changed the group this checks if changed group is available
        if(!update){
            if (instance.isGroupCountExceeded(newGroup)){
                return ("Group count Exceeded. Try another group");
            }
        }

        // updating the existing horse if it is an update operation
        if(update){
            updatingHorse.setId(newId);
            updatingHorse.setHorseName(name);
            updatingHorse.setJockeyName(jockey);
            updatingHorse.setHorseAge(newAge);
            updatingHorse.setBreed(breed);
            updatingHorse.setRaceRecord(raceRecord);
            updatingHorse.setGroup(newGroup);
        } // adding a new horse objcet to the horse list if it is an add operation
        else {
            Horse horse = new Horse(newId, name, jockey, newAge, breed, raceRecord, newGroup);
            instance.getHorseList().add(horse);
        }

        // saving the horse
        instance.serializeHorseList();
        instance.updateGroupCounts();

        // giving out the success message
        if(update){
            return ("Horse updated successfully!");
        }
        return ("Successfully saved in the system.");
    }

    public void deleteHorses(Horse deletingHorse){
        // deleting the given horse from the list
        instance.getHorseList().remove(deletingHorse);
        updateGroupCounts();
    }

    public Horse[] select4HorsesRandomly(){
        finalRoundHorses = new Horse[4];

        ArrayList<Horse>[] allHorsesGroupwise = new ArrayList[4];

        // initializing 4 arraylists in the array
        for(int i = 0; i < allHorsesGroupwise.length; i++){
            allHorsesGroupwise[i] = new ArrayList<>();
        }

        // saerializing enuring that the most updated details ara taken
        serializeHorseList();

        // distributing the horses according to the group to the array of arraylists
        for(Horse horse : deserializeHorseList()){
            if (horse.getGroup() == 'A') allHorsesGroupwise[0].add(horse);
            if (horse.getGroup() == 'B') allHorsesGroupwise[1].add(horse);
            if (horse.getGroup() == 'C') allHorsesGroupwise[2].add(horse);
            if (horse.getGroup() == 'D') allHorsesGroupwise[3].add(horse);
        }

        // getting a random index and getting that index from each arraylist
        for(int i = 0; i < finalRoundHorses.length; i++){
            int groupSize = allHorsesGroupwise[i].size();
            finalRoundHorses[i] = allHorsesGroupwise[i].get(new Random().nextInt(groupSize));
        }

        // marking flag as race started
        finalRoundStarted = true;

        return finalRoundHorses;
    }

    public Horse[] selectWinningHorses(){
        ArrayList<Integer> finalTimings = new ArrayList<>();

        while (finalTimings.size() < 4){
            int newValue = new Random().nextInt(61) + 30; // range 30 - 90 (seconds)

            if (finalTimings.contains(newValue)) continue; // to make sure same value is not assigned to two horses

            finalTimings.add(newValue);
        }

        for(int i = 0; i < finalRoundHorses.length; i++){
            finalRoundHorses[i].setTiming(finalTimings.get(i));
        }

        // sorting
        for(int i = 0; i < finalRoundHorses.length; i++){
            for(int j = 0; j < finalRoundHorses.length - i - 1; j++){
                if(finalRoundHorses[j].getTiming() > finalRoundHorses[j + 1].getTiming()){
                    Horse temp = finalRoundHorses[j];
                    finalRoundHorses[j] = finalRoundHorses[j + 1];
                    finalRoundHorses[j + 1] = temp;
                }
            }
        }

        return finalRoundHorses;
    }

    // getters and setters
    public int getTotalCount(){
        return (groupCounts[0] + groupCounts[1] + groupCounts[2] + groupCounts[3]);
    }
    public ArrayList<Horse> getHorseList() {
        return horseList;
    }
    public int getGroupA() {
        return groupCounts[0];
    }
    public int getGroupB() {
        return groupCounts[1];
    }
    public int getGroupC() {
        return groupCounts[2];
    }
    public int getGroupD() {
        return groupCounts[3];
    }
    public boolean isRaceStarted() {
        return raceStarted;
    }

    public void setRaceStarted(boolean raceStarted) {
        this.raceStarted = raceStarted;
    }

    public boolean isFinalRoundNotOver() {
        return !finalRoundOver;
    }
    public void setFinalRoundOver(boolean finalRoundOver) {
        this.finalRoundOver = finalRoundOver;
    }
    public Horse[] getFinalRoundHorses() {
        return finalRoundHorses;
    }
    public boolean isFinalRoundStarted() {
        return finalRoundStarted;
    }
    public Horse getUpdatingHorse() {
        return updatingHorse;
    }
    public void setUpdatingHorse(Horse updatingHorse) {
        this.updatingHorse = updatingHorse;
    }

}
