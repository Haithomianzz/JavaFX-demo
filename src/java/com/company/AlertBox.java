package com.company;

import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AlertBox implements Style {
    public static void alert(String title, String message,String buttonmessage) {
        Stage window = new Stage();
        window.setTitle(title);
        window.setResizable(false);
        Label label = new Label(message);
        label.setStyle(H2);
        label.setWrapText(true);

        Button button = new Button(buttonmessage);
        button.setStyle(ButtonStyle);
        button.setOnAction(e -> window.close());
        VBox vbox = new VBox(20,label, button);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(20,20,20,20));
        window.setScene(new Scene(vbox));
        window.show();
    }
}
