package org.hmt.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.hmt.Horse;
import org.hmt.HorseManager;
import org.hmt.MainApplication;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Controller class for searching and updating horse details.
 *
 * @author Senuli Wickramage
 */

public class UpdateHorseSearchController implements Initializable {
    @FXML
    private Label groupACount;
    @FXML
    private Label groupBCount;
    @FXML
    private Label groupCCount;
    @FXML
    private Label groupDCount;
    @FXML
    private Label totalCount;
    @FXML
    private TextField searchField;
    @FXML
    private Label searchErrorMsg;
    @FXML
    private Button goToUpdateButton;
    @FXML
    private Label horseGroupLabel;
    @FXML
    private Label horseRaceRecordLabel;
    @FXML
    private Label horseJockeyLabel;
    @FXML
    private Label horseBreedLabel;
    @FXML
    private Label horseIdLabel;
    @FXML
    private Label horseAgeLabel;
    @FXML
    private Label horseNameLabel;
    @FXML
    private ImageView horseImageView;
    @FXML
    private HBox horseCard;

    @FXML
    public void searchHorse() {
        searchErrorMsg.setText("");

        if(HorseManager.getInstance().isRaceStarted()){
            searchErrorMsg.setText("Race Started! Unable to do any changes now!");
            return;
        }

        int searchId;
        try{
            searchId = Integer.parseInt(searchField.getText());
        } catch (Exception e){
            searchErrorMsg.setText("Please enter a number");
            return;
        }

        // Search for the horse using the entered ID
        HorseManager.getInstance().setUpdatingHorse(HorseManager.getInstance().getHorseById(searchId));
        Horse updatingHorse = HorseManager.getInstance().getUpdatingHorse();

        // Display horse details if found, otherwise show an error message
        if (HorseManager.getInstance().getUpdatingHorse() == null){
            searchErrorMsg.setText("Invalid Id");
            return;
        }

        horseCard.setVisible(true);
        goToUpdateButton.setVisible(true);

        horseNameLabel.setText("Name: " + updatingHorse.getHorseName());
        horseIdLabel.setText("ID : " + updatingHorse.getId());
        horseAgeLabel.setText("Age : " + updatingHorse.getHorseAge());
        horseBreedLabel.setText("Breed: " + updatingHorse.getBreed());
        horseRaceRecordLabel.setText("Race Record : " + updatingHorse.getRaceRecord());
        horseJockeyLabel.setText("Jockey Name : " + updatingHorse.getJockeyName());
        horseGroupLabel.setText("Group : " + updatingHorse.getGroup());
        horseImageView.setImage(updatingHorse.getImageView().getImage());
    }

    // Navigate to the update single horse screen
    @FXML
    public void goToUpdateSingleHorse(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(MainApplication.class.getResource("update-single-horse.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void goToMenu(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(MainApplication.class.getResource("menu.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void exit() {
        System.exit(1);
    }

    private void updateGroupCounts(){
        groupACount.setText(Integer.toString(HorseManager.getInstance().getGroupA()));
        groupBCount.setText(Integer.toString(HorseManager.getInstance().getGroupB()));
        groupCCount.setText(Integer.toString(HorseManager.getInstance().getGroupC()));
        groupDCount.setText(Integer.toString(HorseManager.getInstance().getGroupD()));

        totalCount.setText(Integer.toString(HorseManager.getInstance().getTotalCount()));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Hide horse card and update button initially
        horseCard.setVisible(false);
        goToUpdateButton.setVisible(false);

        // Update group counts
        updateGroupCounts();

        // Show appropriate error message based on race status and horse count
        if(HorseManager.getInstance().isRaceStarted()){
            searchErrorMsg.setText("Race Started! Unable to do any changes now!");
        }
        if(HorseManager.getInstance().getTotalCount() == 0){
            searchErrorMsg.setText("No horses are saved in the system.");
        }
    }
}
