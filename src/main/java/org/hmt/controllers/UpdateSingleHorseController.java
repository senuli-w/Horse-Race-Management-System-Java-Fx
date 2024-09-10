package org.hmt.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.hmt.Horse;
import org.hmt.HorseManager;
import org.hmt.MainApplication;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Controller class for updating single horse details.
 *
 * @author Senuli Wickramage
 */

public class UpdateSingleHorseController implements Initializable {
    @FXML
    private TextField idField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField ageField;
    @FXML
    private TextField breedField;
    @FXML
    private TextField jockeyNameField;
    @FXML
    private TextField raceRecordField;
    @FXML
    private TextField groupField;
    @FXML
    private Label errorMsg;
    @FXML
    private Pane imagePane;
    @FXML
    private ImageView imageView;
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
    private Horse updatingHorse;
    private Image imgFile;
    private char searchIdGroup;
    private int searchId;

    @FXML
    public void changeImage() {
        FileChooser fc = new FileChooser();
        // Set the title of displayed file dialog
        fc.setTitle("My File Chooser");

        // Set the initial directory for the displayed file dialog
        // user.home refers to the path to the user directory
        fc.setInitialDirectory(new File(System.getProperty("user.home")));

        // Get the extension features used in the displayed file dialog
        fc.getExtensionFilters().clear(); // Removes all the elements from this list
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif"));

        // Set the selected file to null, if no file has been selected
        File file = fc.showOpenDialog(null); // Shows the new File open dialog

        if (file != null){
            // Returns the absolute pathname string of this abstract pathname

            imgFile = new Image(file.toURI().toString());
            // URI represents this abstract pathname
            imageView.setImage(imgFile);
        } else {
            errorMsg.setText("Choose an image file please.");
        }
    }

    @FXML
    public void updateHorse() {
        errorMsg.setText("");
        errorMsg.setStyle("-fx-text-fill: #ff3333");

        String id = idField.getText();
        String name = nameField.getText();
        String jockey = jockeyNameField.getText();
        String age = ageField.getText();
        String breed = breedField.getText();
        String raceRecord = raceRecordField.getText();
        String group = groupField.getText();

        // Display error messages if necessary
        if(imgFile == null){
            errorMsg.setText("Please choose an image of the horse.");
            return;
        }

        String imagePath = imageView.getImage().getUrl();

        // Update the horse details based on user input
        String msg = HorseManager.getInstance().addOrUpdateHorses(id, name, jockey, age, breed, raceRecord, group, true);

        if(!msg.equals("Horse updated successfully!")){
            errorMsg.setText(msg);
            return;
        }

        HorseManager.getInstance().getHorseById(Integer.parseInt(id)).setImageView(new ImageView(new Image(imagePath)));

        errorMsg.setStyle("-fx-text-fill: BLACK");
        errorMsg.setText(msg);
        updateGroupCounts();

        idField.clear();
        nameField.clear();
        ageField.clear();
        breedField.clear();
        jockeyNameField.clear();
        raceRecordField.clear();
        groupField.clear();
        imageView.setImage(null);
    }

    @FXML
    public void goToUpdateHorse(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(MainApplication.class.getResource("update-horse-search.fxml")));
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

    private void updateGroupCounts() {
        groupACount.setText(Integer.toString(HorseManager.getInstance().getGroupA()));
        groupBCount.setText(Integer.toString(HorseManager.getInstance().getGroupB()));
        groupCCount.setText(Integer.toString(HorseManager.getInstance().getGroupC()));
        groupDCount.setText(Integer.toString(HorseManager.getInstance().getGroupD()));
        totalCount.setText(Integer.toString(HorseManager.getInstance().getTotalCount()));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updatingHorse = HorseManager.getInstance().getUpdatingHorse();

        searchId = updatingHorse.getId();
        searchIdGroup = updatingHorse.getGroup();

        // Initialize the form with the horse details
        idField.setText(Integer.toString(updatingHorse.getId()));
        nameField.setText(updatingHorse.getHorseName());
        jockeyNameField.setText(updatingHorse.getJockeyName());
        ageField.setText(Integer.toString(updatingHorse.getHorseAge()));
        raceRecordField.setText(updatingHorse.getRaceRecord());
        breedField.setText(updatingHorse.getBreed());
        groupField.setText(Character.toString(updatingHorse.getGroup()));
        imgFile = updatingHorse.getImageView().getImage();
        imageView.setImage(updatingHorse.getImageView().getImage());

        // Update group counts
        updateGroupCounts();
    }
}

