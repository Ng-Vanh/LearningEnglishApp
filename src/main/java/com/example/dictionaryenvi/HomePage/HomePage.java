package com.example.dictionaryenvi.HomePage;

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

import static com.example.dictionaryenvi.Account.Login.currentUser;

public class HomePage {
    @FXML
    private ImageView logOutBtn, userInfoBtn;

    /**
     * Move to dictionary.
     */

    public void goToDictionary(MouseEvent mouseEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/dictionaryenvi/MainDictionary/MainDictionary.fxml"));
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

    /**
     * Move to LoginScreen and logout.
     */
    public void clickLogOut(MouseEvent mouseEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/dictionaryenvi/Account/Login/FXML/Login.fxml"));
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

    /**
     * Shows information about current user: name, score.
     */
    public void clickShowUserInfo(MouseEvent mouseEvent) {
        Image avatarImage = new Image(getClass().getResource("/com/example/dictionaryenvi/HomePage/image/userAvt.png").toExternalForm());
        ImageView avatarImageView = new ImageView(avatarImage);

        User usInfo = UserDataAccess.getInstance().getUserInfo(currentUser.getUsername());
        String fullName = usInfo.getFirstName() + " " + usInfo.getLastName();
        Label fullNameLabel = new Label("Name: " + fullName);
        int score1 = usInfo.getScoreGame1();
        Label scoreLabel1 = new Label("Score: " + score1);

        VBox userInfoBox = new VBox(10);
        userInfoBox.setAlignment(Pos.CENTER);
        userInfoBox.getChildren().addAll(avatarImageView, fullNameLabel, scoreLabel1);

        Dialog<Void> dialog = new Dialog<>();
        dialog.getDialogPane().setMinWidth(360);
        dialog.getDialogPane().setContent(userInfoBox);
        dialog.initStyle(StageStyle.UNDECORATED);

        String cssFile = getClass().getResource("/com/example/dictionaryenvi/HomePage/UserInfo.css").toExternalForm();
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

    /**
     * Move to study with some exercise.
     */
    public void goToExercise(MouseEvent mouseEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/dictionaryenvi/Exercise/ExerciseScene/FXML/ExerciseScene.fxml"));
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

    /**
     * Move to learn word with some topic.
     */
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

    /**
     * Move to learn words follow each day.
     */
    public void learnWordOfDay(MouseEvent mouseEvent) {
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

    /**
     * Show rank of all users.
     */
    public void showRanking(MouseEvent mouseEvent) {
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Ranking");

        ButtonType closeButtonType = new ButtonType("Close", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(closeButtonType);
        String cssFile = getClass().getResource("/com/example/dictionaryenvi/HomePage/Ranking.css").toExternalForm();
        dialog.getDialogPane().getStylesheets().add(cssFile);
        dialog.getDialogPane().setMinWidth(460);
        String myUsername = currentUser.getUsername();


        ArrayList<User> dataUsers = UserDataAccess.getInstance().selectAll();
        ObservableList<User> userList = FXCollections.observableArrayList();
        TableView<User> tableView = new TableView<>();
        resizeTableView(tableView,false);
        TableColumn<User, Integer> rankCol = new TableColumn<>("Rank");
        rankCol.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(tableView.getItems().indexOf(cellData.getValue()) + 1));
        rankCol.setResizable(false);
        rankCol.setPrefWidth(60);


        TableColumn<User, String> firstNameCol = new TableColumn<>("First Name");
        firstNameCol.setCellValueFactory(new PropertyValueFactory<User, String>("firstName"));


        TableColumn<User, String> lastNameCol = new TableColumn<>("Last Name");
        lastNameCol.setCellValueFactory(new PropertyValueFactory<User, String>("lastName"));

        TableColumn<User, String> usernameCol = new TableColumn<>("Username");
        usernameCol.setCellValueFactory(new PropertyValueFactory<User, String>("username"));
        usernameCol.setResizable(false);
        usernameCol.setPrefWidth(100);


        TableColumn<User, Integer> scoreGame1Col = new TableColumn<>("Score");
        scoreGame1Col.setCellValueFactory(new PropertyValueFactory<User, Integer>("scoreGame1"));
        scoreGame1Col.setResizable(false);
        scoreGame1Col.setPrefWidth(70);


        tableView.getColumns().addAll(rankCol,usernameCol, firstNameCol, lastNameCol, scoreGame1Col);
        userList.addAll(dataUsers);

        tableView.getColumns().remove(firstNameCol);
        tableView.getColumns().remove(lastNameCol);

        TableColumn<User, String> fullNameCol = new TableColumn<>("Name");
        fullNameCol.setCellValueFactory(cellData -> {
            User user = cellData.getValue();
            String fullName = user.getFirstName() + " " + user.getLastName();
            return new ReadOnlyObjectWrapper<>(fullName);
        });
        fullNameCol.setResizable(false);
        fullNameCol.setPrefWidth(250);
        tableView.getColumns().add(2, fullNameCol);
        tableView.setItems(userList);

        tableView.setRowFactory(tv -> new TableRow<User>() {
            @Override
            protected void updateItem(User user, boolean empty) {
                super.updateItem(user, empty);

                if (user == null || user.getUsername() == null) {
                    return;
                }
                int userRank = tableView.getItems().indexOf(user) + 1;

                if (user.getUsername().equals(myUsername)) {
                    setStyle("-fx-font-weight: bold; -fx-font-style: italic;" + getRowColor(userRank));
                } else {
                    String rowColor = getRowColor(userRank);
                    setStyle(rowColor);
                }
            }

            private String getRowColor(int rank) {
                switch (rank) {
                    case 1:
                        return "-fx-background-color: linear-gradient(to bottom, #FAFAD2, #FFD700)";
                    case 2:
                        return "-fx-background-color: linear-gradient(to bottom, #F0EFEC, #BDBCBA)";
                    case 3:
                        return "-fx-background-color: linear-gradient(to bottom, #FFE7C3, #B08D57)";
                    default:
                        return "-fx-background-color: white";
                }
            }
        });
        for (TableColumn<?, ?> col : tableView.getColumns()) {
            col.setSortable(false);
        }

        VBox vBox = new VBox(tableView);
        dialog.getDialogPane().setContent(vBox);
        dialog.initStyle(StageStyle.UNDECORATED);
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.75), dialog.getDialogPane());
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);
        fadeTransition.play();
        dialog.showAndWait();
    }

    private void resizeTableView(TableView<User> tableView, boolean b) {
        tableView.getColumns().forEach(column -> column.setResizable(b));
    }


}
