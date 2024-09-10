package org.hmt.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.hmt.Horse;
import org.hmt.HorseManager;
import org.hmt.MainApplication;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Controller class for visualizing horse race results.
 *
 * @author Senuli Wickramage
 */

public class VisualizeHorsesController implements Initializable {

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
    public BarChart<String, Integer> horseChart;
    @FXML
    public Label errorMsg;

    public void displayHorseChart(){
        // Check race status and display corresponding error message

        // If the race is not started, show a message and return
        if(!HorseManager.getInstance().isRaceStarted()){
            errorMsg.setText("Race is not started yet!");
            return;
        }

        // If horses for the final round are not selected, show a message and return
        if(!HorseManager.getInstance().isFinalRoundStarted()){
            errorMsg.setText("Horses are not selected for the final round!");
            return;
        }

        // If the final round is not over yet, show a message and return
        if(HorseManager.getInstance().isFinalRoundNotOver()){
            errorMsg.setText("Final race is not over yet!");
            return;
        }
        // Get winning horses for the final round
        Horse[] winningHorses = HorseManager.getInstance().getFinalRoundHorses();

        // Create a series for the chart
        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        series.setName("Time Taken");

        // Add data for each winning horse to the series
        series.getData().add(new XYChart.Data<>("2nd Place - " + winningHorses[1].getHorseName() + "-" +
                winningHorses[1].getId() + "-" + winningHorses[1].getGroup(), winningHorses[1].getTiming()));
        series.getData().add(new XYChart.Data<>("1st Place - " + winningHorses[0].getHorseName() + "-" +
                winningHorses[0].getId() + "-" + winningHorses[0].getGroup(), winningHorses[0].getTiming()));
        series.getData().add(new XYChart.Data<>("3rd Place - " + winningHorses[2].getHorseName() + "-" +
                winningHorses[2].getId() + "-" + winningHorses[2].getGroup(), winningHorses[2].getTiming()));

        // Add the series to the chart
        horseChart.getData().add(series);
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
        updateGroupCounts();
        displayHorseChart();
    }
}
