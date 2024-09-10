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

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Controller class for displaying winning horses.
 *
 * @author Senuli Wickramage
 */

public class WinningHorsesController implements Initializable {

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
    public Label errorMsg;
    @FXML
    public HBox displayArea;
    @FXML
    public ImageView image2;
    @FXML
    public Label id2;
    @FXML
    public Label horse2;
    @FXML
    public Label jockey2;
    @FXML
    public Label timing2;
    @FXML
    public ImageView image1;
    @FXML
    public Label id1;
    @FXML
    public Label horse1;
    @FXML
    public Label jockey1;
    @FXML
    public Label timing1;
    @FXML
    public ImageView image3;
    @FXML
    public Label id3;
    @FXML
    public Label horse3;
    @FXML
    public Label jockey3;
    @FXML
    public Label timing3;
    @FXML
    public Label group2;
    @FXML
    public Label group1;
    @FXML
    public Label group3;

    public void selectWinningHorses(){
        // Reset error message and hide display area
        errorMsg.setText("");
        displayArea.setVisible(false);

        // Check if the race is started, if not, display an error message and return
        if(!HorseManager.getInstance().isRaceStarted()){
            errorMsg.setText("Race is not started yet!");
            return;
        }

        // Check if the final round is started, if not, display an error message and return
        if(!HorseManager.getInstance().isFinalRoundStarted()){
            errorMsg.setText("No horses are selected for the final Round!");
            return;
        }

        // Get the final round horses
        Horse[] finalHorses;
        // If the final round is not over, select the winning horses; otherwise, use the existing final round horses
        if(HorseManager.getInstance().isFinalRoundNotOver()){
            finalHorses = HorseManager.getInstance().selectWinningHorses();
        } else {
            finalHorses = HorseManager.getInstance().getFinalRoundHorses();
        }

        displayArea.setVisible(true);

        Label[] ids = {id1, id2, id3};
        Label[] groups = {group1, group2, group3};
        ImageView[] images = {image1, image2, image3};
        Label[] horseNames = {horse1, horse2, horse3};
        Label[] jockeys = {jockey1, jockey2, jockey3};
        Label[] timings = {timing1, timing2, timing3};

        // Display the winning horses in the UI
        for(int i = 0; i < 3; i ++){
            ids[i].setText("Id : " + finalHorses[i].getId());
            groups[i].setText("Group : " + finalHorses[i].getGroup());
            images[i].setImage(new Image(finalHorses[i].getImagePath()));
            horseNames[i].setText("Name : " + finalHorses[i].getHorseName());
            jockeys[i].setText("Jockey : " + finalHorses[i].getJockeyName());
            timings[i].setText("Timing : " + finalHorses[i].getTiming());
        }

        // Mark the final round as over
        HorseManager.getInstance().setFinalRoundOver(true);
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
    public void exit(ActionEvent event) {
        System.exit(1);
    }

    @FXML
    public void updateGroupCounts(){
        groupACount.setText(Integer.toString(HorseManager.getInstance().getGroupA()));
        groupBCount.setText(Integer.toString(HorseManager.getInstance().getGroupB()));
        groupCCount.setText(Integer.toString(HorseManager.getInstance().getGroupC()));
        groupDCount.setText(Integer.toString(HorseManager.getInstance().getGroupD()));

        totalCount.setText(Integer.toString(HorseManager.getInstance().getTotalCount()));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Call methods to update group counts and select winning horses
        updateGroupCounts();
        selectWinningHorses();
    }
}
