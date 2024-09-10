package org.hmt.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.hmt.Horse;
import org.hmt.HorseManager;
import org.hmt.MainApplication;

import java.io.*;
import java.net.URL;
import java.util.*;

/**
 * Controller class for managing the selection of final round horses.
 *
 * @author Senuli Wickramage
 */

public class SelectFinalRoundController implements Initializable {
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
    private ImageView gAImage;
    @FXML
    private Label gAId;
    @FXML
    private Label gAHorse;
    @FXML
    private Label gAJockey;
    @FXML
    private ImageView gBImage;
    @FXML
    private Label gBId;
    @FXML
    private Label gBHorse;
    @FXML
    private Label gBJockey;
    @FXML
    private ImageView gCImage;
    @FXML
    private Label gCId;
    @FXML
    private Label gCHorse;
    @FXML
    private Label gCJockey;
    @FXML
    private ImageView gDImage;
    @FXML
    private Label gDId;
    @FXML
    private Label gDHorse;
    @FXML
    private Label gDJockey;
    @FXML
    private Label errorMsg;
    @FXML
    private HBox displayArea;

    public void selectRandomHorses(){
        // Clear previous error messages and hide display area
        errorMsg.setText("");
        displayArea.setVisible(false);

        if(!HorseManager.getInstance().isRaceStarted()){
            errorMsg.setText("Race has not started yet! Start the race and come back!");
            return;
        }

        Horse[] finalHorses;
        if(HorseManager.getInstance().isFinalRoundStarted()){
            finalHorses = HorseManager.getInstance().getFinalRoundHorses();
        } else {
            finalHorses = HorseManager.getInstance().select4HorsesRandomly();
        }

        displayArea.setVisible(true);

        Label[] ids = {gAId, gBId, gCId, gDId};
        ImageView[] images = {gAImage, gBImage, gCImage, gDImage};
        Label[] horses = {gAHorse, gBHorse, gCHorse, gDHorse};
        Label[] jockeys = {gAJockey, gBJockey, gCJockey, gDJockey};

        for(int i = 0; i < ids.length; i++){
            ids[i].setText("Id : " + finalHorses[i].getId());
            images[i].setImage(new Image(finalHorses[i].getImagePath()));
            horses[i].setText("Name : " + finalHorses[i].getHorseName());
            jockeys[i].setText("Jockey : " + finalHorses[i].getJockeyName());
        }
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

    private void updateGroupCounts() {
        groupACount.setText(Integer.toString(HorseManager.getInstance().getGroupA()));
        groupBCount.setText(Integer.toString(HorseManager.getInstance().getGroupB()));
        groupCCount.setText(Integer.toString(HorseManager.getInstance().getGroupC()));
        groupDCount.setText(Integer.toString(HorseManager.getInstance().getGroupD()));
        totalCount.setText(Integer.toString(HorseManager.getInstance().getTotalCount()));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Call methods to select random horses and update group counts
        selectRandomHorses();
        updateGroupCounts();
    }
}
