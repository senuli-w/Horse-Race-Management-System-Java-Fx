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
import org.hmt.HorseManager;
import org.hmt.MainApplication;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Controller class for adding a horse.
 *
 * @author Senuli Wickramage
 */

public class AddHorseController implements Initializable {
    // FXML fields
    @FXML
    public TextField groupField;
    @FXML
    public TextField raceRecordField;
    @FXML
    public TextField jockeyNameField;
    @FXML
    public TextField breedField;
    @FXML
    public TextField ageField;
    @FXML
    public TextField nameField;
    @FXML
    public TextField idField;
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
    public ImageView imageView;
    @FXML
    public Label errorMsg;
    @FXML
    public Pane imagePane;
    public Image imgFile;

    // Methods for handling actions/events
    @FXML
    public void chooseImage() {
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
            imagePane.setStyle("-fx-border-color: transparent;");
        } else {
            errorMsg.setText("Choose an image file please.");
        }
    }

    @FXML
    public void addHorse() {
        errorMsg.setText("");
        errorMsg.setStyle("-fx-text-fill: #ff3333");

        String id = idField.getText();
        String name = nameField.getText();
        String jockey = jockeyNameField.getText();
        String age = ageField.getText();
        String breed = breedField.getText();
        String raceRecord = raceRecordField.getText();
        String group = groupField.getText();

        if (imgFile == null){
            errorMsg.setText("Please choose an image of the horse.");
            return;
        }
        String imagePath = imgFile.getUrl();

        String msg = HorseManager.getInstance().addOrUpdateHorses(id, name, jockey, age, breed, raceRecord, group, false);

        if(!msg.equals("Successfully saved in the system.")){
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
        imgFile = null;
        imagePane.setStyle("-fx-border-color: #d3d3d3");
    }

    // Load the main menu
    @FXML
    public void goToMenu(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(MainApplication.class.getResource("menu.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void exit(){
        System.exit(1);
    }

    // Updates the group counts displayed on the UI.
    private void updateGroupCounts() {
        groupACount.setText(Integer.toString(HorseManager.getInstance().getGroupA()));
        groupBCount.setText(Integer.toString(HorseManager.getInstance().getGroupB()));
        groupCCount.setText(Integer.toString(HorseManager.getInstance().getGroupC()));
        groupDCount.setText(Integer.toString(HorseManager.getInstance().getGroupD()));
        totalCount.setText(Integer.toString(HorseManager.getInstance().getTotalCount()));
    }

    // Initialize controller
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateGroupCounts();
        if(HorseManager.getInstance().isRaceStarted()){
            errorMsg.setText("Race Started! Unable to do any changes now!");
        }
    }
}
