package com.example.dictionaryenvi;

import com.backend.Connection.UserDataAccess;
import com.backend.User.User;
import javafx.animation.FadeTransition;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;

import static com.example.dictionaryenvi.Login.currentUser;

public class HomePage {
    @FXML
    private ImageView logOutBtn, userInfoBtn;

    public void goToDictionary(MouseEvent mouseEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainDictionary/MainDictionary.fxml"));
        try {
            Parent root = loader.load();
            Scene scene =new Scene(root);
            Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void clickLogOut(MouseEvent mouseEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Login/Login.fxml"));
        try {
            Parent root = loader.load();
            Scene scene =new Scene(root);
            Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void clickShowUserInfo(MouseEvent mouseEvent) {
        Image avatarImage = new Image(getClass().getResource("/com/example/dictionaryenvi/HomePage/image/userAvt.png").toExternalForm());
        ImageView avatarImageView = new ImageView(avatarImage);

        String fullName = currentUser.getFirstName() + " " + currentUser.getLastName();
        Label fullNameLabel = new Label("Name: " + fullName);
        int score1 = currentUser.getScoreGame1();
        int score2 = currentUser.getScoreGame2();
        Label scoreLabel1 = new Label("Dictation: " + score1);
        Label scoreLabel2 = new Label("MultipleChoice: " + score2);

        VBox userInfoBox = new VBox(10);
        userInfoBox.setAlignment(Pos.CENTER);
        userInfoBox.getChildren().addAll(avatarImageView, fullNameLabel, scoreLabel1, scoreLabel2);

        Dialog<Void> dialog = new Dialog<>();
        dialog.getDialogPane().setMinWidth(360);
        dialog.getDialogPane().setContent(userInfoBox);
        dialog.initStyle(StageStyle.UNDECORATED);

        String cssFile = getClass().getResource("HomePage/UserInfo.css").toExternalForm();
        dialog.getDialogPane().getStylesheets().add(cssFile);

        // Tạo nút đóng dialog
        Button closeButton = new Button("");
        closeButton.setOnAction(event -> dialog.close());
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);

        // Tạo hiệu ứng Fade cho Dialog
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.75), dialog.getDialogPane());
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);
        fadeTransition.play();

        dialog.showAndWait();
    }


    public void goToExercise(MouseEvent mouseEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/dictionaryenvi/Exercise/ExerciseSelection/FXML/ExerciseSelection.fxml"));
        try {
            Parent root = loader.load();
            Scene scene =new Scene(root);
            Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void goToLearnWord(MouseEvent mouseEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/dictionaryenvi/TopicWord/TopicWord.fxml"));
        try {
            Parent root = loader.load();

            FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.75), root);
            fadeTransition.setFromValue(0.0);
            fadeTransition.setToValue(1.0);
            fadeTransition.play();

            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void showRanking(MouseEvent mouseEvent) {
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Ranking");

        ButtonType closeButtonType = new ButtonType("Close", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(closeButtonType);
        String cssFile = getClass().getResource("HomePage/Ranking.css").toExternalForm();
        dialog.getDialogPane().getStylesheets().add(cssFile);
        dialog.getDialogPane().setMinWidth(456);
        String myUsername = currentUser.getUsername();


        ArrayList<User> dataUsers = UserDataAccess.getInstance().selectAll();
        ObservableList<User> userList = FXCollections.observableArrayList();
        TableView<User> tableView = new TableView<>();
        TableColumn<User, Integer> rankCol = new TableColumn<>("Rank");
        rankCol.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(tableView.getItems().indexOf(cellData.getValue()) + 1));
        TableColumn<User, String> firstNameCol = new TableColumn<>("First Name");
        firstNameCol.setCellValueFactory(new PropertyValueFactory<User, String>("firstName"));

        TableColumn<User, String> lastNameCol = new TableColumn<>("Last Name");
        lastNameCol.setCellValueFactory(new PropertyValueFactory<User, String>("lastName"));

        TableColumn<User, String> usernameCol = new TableColumn<>("Username");
        usernameCol.setCellValueFactory(new PropertyValueFactory<User, String>("username"));


        TableColumn<User, Integer> scoreGame1Col = new TableColumn<>("Score Game 1");
        scoreGame1Col.setCellValueFactory(new PropertyValueFactory<User, Integer>("scoreGame1"));

        TableColumn<User, Integer> scoreGame2Col = new TableColumn<>("Score Game 2");
        scoreGame2Col.setCellValueFactory(new PropertyValueFactory<User, Integer>("scoreGame2"));

        tableView.getColumns().addAll(rankCol,usernameCol, firstNameCol, lastNameCol, scoreGame1Col, scoreGame2Col);
        userList.addAll(dataUsers);

        tableView.getColumns().remove(firstNameCol);
        tableView.getColumns().remove(lastNameCol);

        TableColumn<User, String> fullNameCol = new TableColumn<>("Full Name");
        fullNameCol.setCellValueFactory(cellData -> {
            User user = cellData.getValue();
            String fullName = user.getFirstName() + " " + user.getLastName();
            return new ReadOnlyObjectWrapper<>(fullName);
        });

        tableView.getColumns().add(2, fullNameCol);
        tableView.setItems(userList);

        tableView.setRowFactory(tv -> new TableRow<User>() {
            @Override
            protected void updateItem(User user, boolean empty) {
                super.updateItem(user, empty);

                if (user == null || user.getUsername() == null) {
                    return;
                }

                if (user.getUsername().equals(myUsername)) {
                    setStyle("-fx-background-color: linear-gradient(to right, yellow , #F93535);");
                }
            }
        });

        VBox vBox = new VBox(tableView);
        dialog.getDialogPane().setContent(vBox);
        dialog.initStyle(StageStyle.UNDECORATED);
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.75), dialog.getDialogPane());
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);
        fadeTransition.play();
        dialog.showAndWait();
    }
}
