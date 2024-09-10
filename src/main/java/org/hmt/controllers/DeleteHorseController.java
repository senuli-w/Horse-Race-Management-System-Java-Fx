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
 * Controller class for deleting a horse.
 *
 * @author Senuli Wickramage
 */

public class DeleteHorseController implements Initializable {
    @FXML
    public Label groupACount;
    @FXML
    public Label groupBCount;
    @FXML
    public Label groupCCount;
    @FXML
    public Label groupDCount;
    @FXML
    public Label totalCount;
    @FXML
    public TextField searchField;
    @FXML
    public Label searchErrorMsg;
    @FXML
    public HBox horseCard;
    @FXML
    public ImageView horseImageView;
    @FXML
    public Label horseNameLabel;
    @FXML
    public Label horseAgeLabel;
    @FXML
    public Label horseIdLabel;
    @FXML
    public Label horseBreedLabel;
    @FXML
    public Label horseJockeyLabel;
    @FXML
    public Label horseRaceRecordLabel;
    @FXML
    public Label horseGroupLabel;
    @FXML
    public Button deleteButton;
    @FXML
    public Label successMsg;
    private Horse deletingHorse;

    public void updateGroupCounts(){
        groupACount.setText(Integer.toString(HorseManager.getInstance().getGroupA()));
        groupBCount.setText(Integer.toString(HorseManager.getInstance().getGroupB()));
        groupCCount.setText(Integer.toString(HorseManager.getInstance().getGroupC()));
        groupDCount.setText(Integer.toString(HorseManager.getInstance().getGroupD()));

        totalCount.setText(Integer.toString(HorseManager.getInstance().getTotalCount()));
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

    @FXML   // Logic for searching and displaying horse details
    public void searchHorse() {
        deleteButton.setVisible(false);
        horseCard.setVisible(false);
        successMsg.setVisible(false);
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

        // extracting the horse from the horse list
        deletingHorse = HorseManager.getInstance().getHorseById(searchId);

        // does not exist means no horse with that id
        if (deletingHorse == null){
            searchErrorMsg.setText("Invalid Id");
            return;
        }

        horseCard.setVisible(true);
        deleteButton.setVisible(true);

        horseNameLabel.setText("Name: " + deletingHorse.getHorseName());
        horseIdLabel.setText("ID : " + deletingHorse.getId());
        horseAgeLabel.setText("Age : " + deletingHorse.getHorseAge());
        horseBreedLabel.setText("Breed: " + deletingHorse.getBreed());
        horseRaceRecordLabel.setText("Race Record : " + deletingHorse.getRaceRecord());
        horseJockeyLabel.setText("Jockey Name : " + deletingHorse.getJockeyName());
        horseGroupLabel.setText("Group : " + deletingHorse.getGroup());
        horseImageView.setImage(deletingHorse.getImageView().getImage());
    }

    @FXML
    public void deleteHorse() {
        HorseManager.getInstance().deleteHorses(deletingHorse);

        updateGroupCounts();

        successMsg.setVisible(true);
        searchField.clear();
        horseCard.setVisible(false);
        deleteButton.setVisible(false);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateGroupCounts();
        if(HorseManager.getInstance().isRaceStarted()){
            searchErrorMsg.setText("Race Started! Unable to do any changes now!");
        }
    }
}
