package com.example.demo;

import javafx.application.Application;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.Serializable;

public class main extends Application implements Style {
    private Stage window;
    private Scene PreviousScene;
    public void start(Stage stage) throws IOException {
        window = stage;
        window.setTitle("Healthcare System v1.0");
        // Layouts
        StackPane loginPane = new StackPane();
        StackPane menuPane = new StackPane();
        // Scenes
        Scene LoginOptions = new Scene(loginPane,1920,1080);
        Scene menu = new Scene(menuPane, 1920, 1080);
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
            VBox blist = new VBox(50);
            blist.setStyle(BGColor);
            blist.setAlignment(Pos.CENTER);
            blist.getChildren().add(label);
            blist.getChildren().addAll(button);
            menuPane.getChildren().addAll(blist);
        }
        // Login Page
        {
            String[] labels = new String[]{"Admin Login","Patient Login","New Patient"};
            Button[] button = new Button[labels.length];
            for (int i = 0; i < labels.length; i++) {
                button[i] = new Button(labels[i]);
                button[i].setMinSize(bwidth, blength);
                button[i].setStyle(ButtonStyle);
            }
            VBox blist = new VBox(50);
            blist.setStyle(BGColor);
            blist.setAlignment(Pos.CENTER);
            blist.getChildren().addAll(button);
            loginPane.getChildren().addAll(blist,BackButton());
        }
        // DashBoard
        {

        }
        window.setScene(menu);
        window.show();
    }
    public static void main(String[] args) {
        launch();
    }
    public StackPane BackButton(){
        StackPane layout = new StackPane();
        Button backbutton = new Button("Back");
        backbutton.setMinSize(bwidth,blength);
        backbutton.setStyle(ButtonStyle);
        backbutton.setOnAction(e -> {
            Scene temp = window.getScene();
            window.setScene(PreviousScene);
            PreviousScene = temp;
        });
        layout.getChildren().addAll(backbutton);
        layout.setAlignment(Pos.BOTTOM_LEFT);
        return layout;
    }
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}