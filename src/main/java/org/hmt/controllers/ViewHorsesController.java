package org.hmt.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.hmt.Horse;
import org.hmt.HorseManager;
import org.hmt.MainApplication;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Controller class for viewing horse details.
 *
 * @author Senuli Wickramage
 */

public class ViewHorsesController implements Initializable {
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
    public TableView<Horse> horseTable;
    @FXML
    public TableColumn<Horse, ImageView> thumbnailColumn;
    @FXML
    public TableColumn<Horse, Integer> idColumn;
    @FXML
    public TableColumn<Horse, Character> groupColumn;
    @FXML
    public TableColumn<Horse, String> horseNameColumn;
    @FXML
    public TableColumn<Horse, Integer> horseAgeColumn;
    @FXML
    public TableColumn<Horse, String> horseBreedColumn;
    @FXML
    public TableColumn<Horse, String> jockeyNameColumn;
    @FXML
    public TableColumn<Horse, String> raceRecordColumn;

    ObservableList<Horse> list = FXCollections.observableArrayList(
            HorseManager.getInstance().sortHorses()
    );

    @FXML
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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Set up table columns and cell value factories
        thumbnailColumn.setPrefWidth(100);
        thumbnailColumn.setCellValueFactory(new PropertyValueFactory<Horse, ImageView>("imageView"));
        idColumn.setCellValueFactory(new PropertyValueFactory<Horse, Integer>("id"));
        groupColumn.setCellValueFactory(new PropertyValueFactory<Horse, Character>("group"));
        horseNameColumn.setCellValueFactory(new PropertyValueFactory<Horse, String>("horseName"));
        horseAgeColumn.setCellValueFactory(new PropertyValueFactory<Horse, Integer>("horseAge"));
        horseBreedColumn.setCellValueFactory(new PropertyValueFactory<Horse, String>("breed"));
        jockeyNameColumn.setCellValueFactory(new PropertyValueFactory<Horse, String>("jockeyName"));
        raceRecordColumn.setCellValueFactory(new PropertyValueFactory<Horse, String>("raceRecord"));

        // Populate table with horse data
        horseTable.setItems(list);

        // Update group counts
        updateGroupCounts();
    }


}
