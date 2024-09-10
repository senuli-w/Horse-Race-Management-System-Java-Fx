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
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.hmt.HorseManager;
import org.hmt.MainApplication;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Controller class for starting the race.
 *
 * @author Senuli Wickramage
 */

public class StartRaceController implements Initializable {
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
    public VBox beforeInstructions;
    @FXML
    public Label errorMsg;
    @FXML
    public VBox afterInstructions;
    @FXML
    public Button startRaceButton;
    @FXML
    public ImageView raceImage;

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
    public void exit(ActionEvent event) {
        System.exit(1);
    }

    @FXML
    public void startRace(ActionEvent event) {
        // Display instructions before the race and hide after-race instructions
        beforeInstructions.setVisible(true);
        afterInstructions.setVisible(false);
        errorMsg.setText("");

        // Check if there are enough horses to start the race
        if(Integer.parseInt(groupACount.getText()) < 1 ||
                Integer.parseInt(groupBCount.getText()) < 1 ||
                Integer.parseInt(groupCCount.getText()) < 1 ||
                Integer.parseInt(groupDCount.getText()) < 1) {

            // If there are not enough horses, display an error message and return
            errorMsg.setText("No enough horses to start the race!");
            return;
        }

        // Otherwise, mark the race as started and display after-race instructions
        HorseManager.getInstance().setRaceStarted(true);
        beforeInstructions.setVisible(false);
        afterInstructions.setVisible(true);
        startRaceButton.setVisible(false);
        raceImage.setVisible(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (HorseManager.getInstance().isRaceStarted()){
            HorseManager.getInstance().setRaceStarted(true);
            beforeInstructions.setVisible(false);
            afterInstructions.setVisible(true);
            startRaceButton.setVisible(false);
            raceImage.setVisible(true);
        }
        updateGroupCounts();
    }
}
