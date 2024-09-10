package org.hmt;

import java.util.ArrayList;

/**
 * Interface defining operations for managing horses.
 *
 * @author Senuli Wickramage
 */

public interface Manager {

    /**
     * Adds or updates a horse in the system.
     * @param id The ID of the horse.
     * @param name The name of the horse.
     * @param jockey The jockey of the horse.
     * @param age The age of the horse.
     * @param breed The breed of the horse.
     * @param raceRecord The race record of the horse.
     * @param group The group of the horse.
     * @param update True if updating an existing horse, false if adding a new horse.
     * @return A message indicating success or failure.
     */
    String addOrUpdateHorses(String id, String name, String jockey, String age, String breed, String raceRecord, String group, boolean update);

    /**
     * Deletes a horse from the system.
     * @param deletingHorse The horse to be deleted.
     */
    void deleteHorses(Horse deletingHorse);

    /**
     * Sorts the list of horses.
     * @return The sorted list of horses.
     */
    ArrayList<Horse> sortHorses ();

    /**
     * Serializes the list of horses to a file.
     */
    void serializeHorseList();

    /**
     * Deserializes the list of horses from a file.
     * @return The list of deserialized horses.
     */
    ArrayList<Horse> deserializeHorseList();

    /**
     * Selects four horses randomly.
     * @return An array of four randomly selected horses.
     */
    Horse[] select4HorsesRandomly();

    /**
     * Selects the winning horses.
     * @return An array of winning horses.
     */
    Horse[] selectWinningHorses();


    /**
     * Gets the list of horses grouped by their group.
     * @return The list of horses grouped by their group.
     */
    ArrayList<Horse> getHorsesGroupWise();

    /**
     * Updates the counts of horses in each group.
     */
    void updateGroupCounts();

    /**
     * Gets the list of horses in the specified group.
     * @param group The group for which to get the horses.
     * @return The list of horses in the specified group.
     */
    ArrayList<Horse> getHorsesInGroup(char group);

    /**
     * Gets a horse by its ID.
     * @param id The ID of the horse.
     * @return The horse with the specified ID, or null if not found.
     */
    Horse getHorseById(int id);

    /**
     * Checks if the count of horses in the specified group exceeds the limit.
     * @param group The group to check.
     * @return True if the count exceeds the limit, otherwise false.
     */
    boolean isGroupCountExceeded(char group);

    /**
     * Checks if a given ID is already taken by another horse.
     * @param id The ID to check.
     * @return True if the ID is taken, otherwise false.
     */
    boolean isIdTaken(int id);
}
