package com.example.demo;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;

public class main extends Application implements Style {
    private Stage window;
    private Scene PreviousScene;
    private String UserType;
    public void start(Stage stage) throws IOException {
        window = stage;
        window.setTitle("Healthcare System v1.0");
        // Layouts
        StackPane menuPane = new StackPane();
        BorderPane loginOPane = new BorderPane();
        BorderPane loginFPane = new BorderPane();
        // Scenes
        Scene menu = new Scene(menuPane, 1920, 1080);
        Scene LoginOptions = new Scene(loginOPane,1920,1080);
        Scene LoginForm = new Scene(loginFPane,1920,1080);

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
            button[0].setOnAction(e -> {
                PreviousScene = window.getScene();
                stage.setScene(LoginOptions);
            });
            button[2].setOnAction(e -> stage.close());
            VBox mlist = new VBox(50);
            mlist.setStyle(BGColor);
            mlist.setAlignment(Pos.CENTER);
            mlist.getChildren().add(label);
            mlist.getChildren().addAll(button);
            menuPane.getChildren().addAll(mlist);
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
                PreviousScene = window.getScene();
                window.setScene(LoginForm);
            });
            button[1].setOnAction(e -> {
                setUserType("Doctor");
                PreviousScene = window.getScene();
                window.setScene(LoginForm);
            });
            Button back = BackButton();
            VBox blist = new VBox(50);
            loginOPane.setStyle(BGColor);
            blist.setAlignment(Pos.CENTER);
            blist.getChildren().addAll(button);
            loginOPane.setCenter(blist);
            loginOPane.setBottom(back);
        }
        // Login Form Page
        {
            final String CORRECT_USERNAME = "admin";
            final String CORRECT_PASSWORD = "pass123";
            Label usernameLabel = new Label("Username:");
            usernameLabel.setStyle(H1);
            Label passwordLabel = new Label("Password:");
            passwordLabel.setStyle(H1);
            TextField usernameField = new TextField();
            usernameField.setMaxSize(320,200);
            PasswordField passwordField = new PasswordField();
            passwordField.setMaxSize(320,200);
            Label resultLabel = new Label();
            resultLabel.setStyle(H1);
            Button loginButton = new Button("Login");
            Button backButton = BackButton();
            loginButton.setStyle(ButtonStyle);
            loginButton.setOnAction(e -> {
                String enteredUsername = usernameField.getText();
                String enteredPassword = passwordField.getText();
                if (enteredUsername.equals(CORRECT_USERNAME) && enteredPassword.equals(CORRECT_PASSWORD)) {
                    resultLabel.setText("Login successful!");
                } else {
                    resultLabel.setText("Login failed. Please check your credentials." + getUserType());
                }
            });
            VBox form = new VBox(10);
            form.getChildren().addAll(usernameLabel, usernameField, passwordLabel, passwordField, loginButton, resultLabel);
            form.setAlignment(Pos.CENTER);
            form.setPrefSize(400,400);
            loginFPane.setCenter(form);
            loginFPane.setBottom(backButton);
        }
        // DashBoard
        {
            String[] labels = new String[]{"Physician Login","Admin Login"};
        }
        window.setScene(menu);
        window.show();
    }
    public static void main(String[] args) {
        launch();
    }
    public Button BackButton(){
        Button backbutton = new Button("Back");
        backbutton.setMinSize(bwidth,blength);
        backbutton.setStyle(ButtonStyle);
        backbutton.setOnAction(e -> {
            Scene temp = window.getScene();
            window.setScene(PreviousScene);
            PreviousScene = temp;
        });
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