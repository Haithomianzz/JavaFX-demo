package com.company;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.io.IOException;
public class Main extends Application implements Style {
    Stage window;
    String UserType;
    public void start(Stage stage) throws IOException {
        window = stage;
        window.setTitle("Healthcare System v1.0");
        int[] Resolution = {1920,1080};
        // Layouts
        BorderPane menuPane = new BorderPane();
        BorderPane loginOPane = new BorderPane();
        BorderPane loginFPane = new BorderPane();
        // Scenes
        Scene Menu = new Scene(menuPane,Resolution[0],Resolution[1]);
        Scene LoginOptions = new Scene(loginOPane,Resolution[0],Resolution[1]);
        Scene LoginForm = new Scene(loginFPane,Resolution[0],Resolution[1]);
        // Menu Page
        {
            String[] bLabels = new String[]{"Login", "Settings", "Exit"};
            Label label = new Label("Welcome to the Healthcare System");
            label.setStyle(H1);
            Button[] button = new Button[bLabels.length];
            for (int i = 0; i < button.length; i++) {
                button[i] = new Button(bLabels[i]);
                button[i].setMinSize(bwidth, blength);
                button[i].setStyle(ButtonStyle);
            }
            button[0].setOnAction(e -> stage.setScene(LoginOptions));
            button[2].setOnAction(e -> stage.close());
            VBox mlist = new VBox(50);
            mlist.setStyle(BGColor);
            mlist.setAlignment(Pos.CENTER);
            mlist.getChildren().add(label);
            mlist.getChildren().addAll(button);
            menuPane.setCenter(mlist);
        }
        // Login Options Page
        {
            String[] labels = new String[]{"Admin Login","Physician Login"};
            Button[] button = new Button[labels.length];
            for (int i = 0; i < labels.length; i++) {
                button[i] = new Button(labels[i]);
                button[i].setMinSize(bwidth, blength);
                button[i].setStyle(ButtonStyle);
            }
            button[0].setOnAction(e -> {
                setUserType("Admin");
                window.setScene(LoginForm);
            });
            button[1].setOnAction(e -> {
                setUserType("Doctor");
                window.setScene(LoginForm);
            });
            Button backButton = BackButton();
            backButton.setOnAction(e-> window.setScene(Menu));
            VBox blist = new VBox(50);
            loginOPane.setStyle(BGColor);
            blist.setAlignment(Pos.CENTER);
            blist.getChildren().addAll(button);
            loginOPane.setCenter(blist);
            loginOPane.setBottom(backButton);
        }
        // Login Form Page
        {
            String CORRECT_USERNAME = "admin";
            String CORRECT_PASSWORD = "pass123";
            Label usernameLabel = new Label("Username:");
            usernameLabel.setStyle(H2);
            Label passwordLabel = new Label("Password:");
            passwordLabel.setStyle(H2);
            TextField usernameField = new TextField();
            usernameField.setStyle(H3);
            usernameField.setPrefSize(350,200);
            usernameField.setPromptText("Username");
            PasswordField passwordField = new PasswordField();
            passwordField.setPrefSize(350,200);
            passwordField.setPromptText("Password");
            passwordField.setStyle(H3);
            Label resultLabel = new Label();
            resultLabel.setMaxSize(400,120);
            Button loginButton = new Button("Login");
            loginButton.setMaxSize(200,120);
            Button backButton = BackButton();
            backButton.setOnAction(e-> window.setScene(LoginOptions));
            loginButton.setStyle(ButtonStyle);
            loginButton.setOnAction(e -> {
                String enteredUsername = usernameField.getText();
                String enteredPassword = passwordField.getText();
                if (enteredUsername.equals(CORRECT_USERNAME) && enteredPassword.equals(CORRECT_PASSWORD)) {
                    resultLabel.setText("Successful Login!");
                    resultLabel.setStyle(Success + H2);
                } else {
                    resultLabel.setText("Incorrect Credentials!");
                    resultLabel.setStyle(Warning + H2);
                }
            });
            GridPane form = new GridPane();
            form.add(usernameLabel, 0, 0);
            form.add(usernameField, 1, 0);
            form.add(passwordLabel, 0, 1);
            form.add(passwordField, 1, 1);
            form.add(resultLabel, 1, 2);
            form.add(loginButton, 0, 2);
            form.setHgap(20);
            form.setVgap(25);
            form.setAlignment(Pos.CENTER);
            form.setPadding(new Insets(75,40,75,40));
            form.setStyle("-fx-background-color: #eeeeee");
            form.setMaxSize(680,330);
            loginFPane.setCenter(form);
            loginFPane.setStyle(BGColor);
            loginFPane.setBottom(backButton);
        }
        // DashBoard
        {
            String[] labels = new String[]{"Physician Login","Admin Login"};
        }
        window.setScene(Menu);
        window.show();
    }
    public static void main(String[] args) {
        launch();
    }
    public Button BackButton(){
        Button backbutton = new Button("Back");
        backbutton.setMinSize(bwidth,blength);
        backbutton.setStyle(ButtonStyle);
        return backbutton;
    }
    public String getUserType() {
        return UserType;
    }
    public void setUserType(String userType) {
        this.UserType = userType;
    }
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}