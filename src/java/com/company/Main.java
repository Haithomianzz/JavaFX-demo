package com.company;

import com.functions.Doctor;
import com.functions.EmergencyPatient;
import com.functions.NormalPatient;
import com.functions.Patient;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Set;

import static com.database.Handler.verifyCredentials;

public class Main extends Application implements Style {
    Stage window;
    String SessionID;
    public void start(Stage stage) {
        window = stage;
        window.setTitle("Healthcare System v1.0");
        int[] Resolution = {1920, 1080};
        // Layouts
        BorderPane menuPane = new BorderPane();
        BorderPane loginOPane = new BorderPane();
        BorderPane loginFPane = new BorderPane();
        VBox adminDashboard = new VBox();
        VBox doctorDashboard = new VBox(20);
        VBox doctorsT = new VBox(10);
        VBox patientsT = new VBox(10);
        // Scenes
        Scene Menu = new Scene(menuPane, Resolution[0], Resolution[1]);
        Scene LoginOptions = new Scene(loginOPane, Resolution[0], Resolution[1]);
        Scene LoginForm = new Scene(loginFPane, Resolution[0], Resolution[1]);
        Scene AdminDashboard = new Scene(adminDashboard, Resolution[0], Resolution[1]);
        Scene DoctorDashboard = new Scene(doctorDashboard, Resolution[0],Resolution[1]);
        Scene DoctorsTable = new Scene(doctorsT, Resolution[0], Resolution[1]);
        Scene PatientsTable = new Scene(patientsT, Resolution[0], Resolution[1]);

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
            button[0].setOnAction(_ -> stage.setScene(LoginOptions));
            button[2].setOnAction(_ -> stage.close());
            VBox mlist = new VBox(50);
            mlist.setStyle(BGColor);
            mlist.setAlignment(Pos.CENTER);
            mlist.getChildren().add(label);
            mlist.getChildren().addAll(button);
            menuPane.setCenter(mlist);
        }
        // Login Options Page
        {
            String[] labels = new String[]{"Admin Login", "Physician Login"};
            Button[] button = new Button[labels.length];
            for (int i = 0; i < labels.length; i++) {
                button[i] = new Button(labels[i]);
                button[i].setMinSize(bwidth, blength);
                button[i].setStyle(ButtonStyle);
            }
            button[0].setOnAction(_ -> window.setScene(LoginForm));
            button[1].setOnAction(_ -> window.setScene(LoginForm));
            Button backButton = BackButton();
            backButton.setOnAction(_ -> window.setScene(Menu));
            VBox blist = new VBox(50);
            loginOPane.setStyle(BGColor);
            blist.setAlignment(Pos.CENTER);
            blist.getChildren().addAll(button);
            loginOPane.setCenter(blist);
            loginOPane.setBottom(backButton);
        }
        // Login Form Page
        {
            Label usernameLabel = new Label("Username:");
            usernameLabel.setStyle(H2);
            Label passwordLabel = new Label("Password:");
            passwordLabel.setStyle(H2);

            TextField usernameField = new TextField();
            usernameField.setStyle(H3);
            usernameField.setPrefSize(350, 200);
            usernameField.setPromptText("Username");

            PasswordField passwordField = new PasswordField();
            passwordField.setPrefSize(350, 200);
            passwordField.setPromptText("Password");

            passwordField.setStyle(H3);
            Label resultLabel = new Label();
            resultLabel.setMaxSize(400, 120);
            Button loginButton = new Button("Login");

            loginButton.setMaxSize(200, 120);

            Button backButton = BackButton();

            backButton.setOnAction(_ -> window.setScene(LoginOptions));

            loginButton.setStyle(ButtonStyle);

            loginButton.setOnAction(_ -> {
                String enteredUsername = usernameField.getText();
                String enteredPassword = passwordField.getText();

                if (verifyCredentials(enteredUsername, enteredPassword)) {
                    resultLabel.setText("Successful Login!");
                    resultLabel.setStyle(Success + H2);
                    window.setScene(AdminDashboard);
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
            form.setPadding(new Insets(40, 40, 40, 40));
            form.setStyle("-fx-background-color: #eeeeee");
            form.setMaxSize(680, 330);
            loginFPane.setCenter(form);
            loginFPane.setStyle(BGColor);
            loginFPane.setBottom(backButton);
        }
        // Admin DashBoard
        {
                adminDashboard.setStyle("-fx-background-color: #f0f0f0;");
                adminDashboard.setAlignment(Pos.CENTER);

                Label doctorLabel = new Label("Doctors");
                GridPane.setHalignment(doctorLabel, HPos.CENTER);
                Button doctorsButton = new Button();
                doctorsButton.setGraphic(new ImageView(new Image(DOCTORS_ICON)));
                doctorsButton.setStyle(Style.ButtonStyle);
                doctorsButton.setOnAction(_ -> window.setScene(DoctorsTable));

                Label patientLabel = new Label("Patients");
                GridPane.setHalignment(patientLabel, HPos.CENTER);
                Button patientsButton = new Button();
                patientsButton.setGraphic(new ImageView(new Image(PATIENTS_ICON)));
                patientsButton.setStyle(Style.ButtonStyle);
                patientsButton.setOnAction(_ -> window.setScene(PatientsTable));

                Label appointmentLabel = new Label("Appointments");
                GridPane.setHalignment(appointmentLabel, HPos.CENTER);
                Button appointmentsButton = new Button();
                appointmentsButton.setGraphic(new ImageView(new Image(APPTS_ICON)));
                appointmentsButton.setStyle(Style.ButtonStyle);
                //appointmentsButton.setOnAction(e -> window.setScene(AppointmentsTable));

                Label roomLabel = new Label("Rooms");
                GridPane.setHalignment(roomLabel, HPos.CENTER);
                Button roomsButton = new Button();
                roomsButton.setGraphic(new ImageView(new Image(ROOM_ICON)));
                roomsButton.setStyle(Style.ButtonStyle);
                //roomsButton.setOnAction(e -> window.setScene(RoomsTable));

                Label logoutLabel = new Label("Log Out");
                GridPane.setHalignment(logoutLabel, HPos.CENTER);
                Button logoutButton = new Button();
                logoutButton.setGraphic(new ImageView(new Image(LOGOUT_ICON)));
                logoutButton.setStyle(Style.ButtonStyle);
                logoutButton.setOnAction(_ -> window.setScene(Menu));


                GridPane adminGrid = new GridPane();
                adminGrid.setHgap(20);
                adminGrid.setVgap(20);
                adminGrid.setPadding(new Insets(40, 40, 40, 40));
                adminGrid.setStyle(H2);
                adminGrid.add(doctorsButton, 0, 0);
                adminGrid.add(patientsButton, 1, 0);
                adminGrid.add(appointmentsButton, 2, 0);
                adminGrid.add(doctorLabel, 0, 1);
                adminGrid.add(patientLabel, 1, 1);
                adminGrid.add(appointmentLabel, 2, 1);
                adminGrid.add(roomsButton, 0, 2);
                adminGrid.add(logoutButton, 2, 2);
                adminGrid.add(roomLabel, 0, 3);
                adminGrid.add(logoutLabel,2,3);

                adminDashboard.getChildren().addAll(adminGrid);
        }
        // Doctor DashBoard
        {
            doctorDashboard.setStyle("-fx-background-color: #f0f0f0;");
            doctorDashboard.setAlignment(Pos.CENTER);

            Label doctorProfLabel = new Label("Doctors Profile");
            GridPane.setHalignment(doctorProfLabel, HPos.CENTER);
            Button doctorProfile = new Button();
            doctorProfile.setGraphic(new ImageView(new Image(Style.DOCTORS_ICON)));
            doctorProfile.setStyle(Style.ButtonStyle);
            //doctorProfile.setOnAction(e -> window.setScene(doctorProfileTable));

            Label patientLabel = new Label("Patients");
            GridPane.setHalignment(patientLabel, HPos.CENTER);
            Button patientProfile = new Button();
            patientProfile.setGraphic(new ImageView(new Image(Style.PATIENTS_ICON)));
            patientProfile.setStyle(Style.ButtonStyle);
            // patientProfile.setOnAction(e -> window.setScene(patientProfileTable));

            Label  apptSchedLabel = new Label("Appointments Schedule");
            GridPane.setHalignment(apptSchedLabel, HPos.CENTER);
            Button apptSchedule = new Button();
            apptSchedule.setGraphic(new ImageView(new Image(Style.APPTS_ICON)));
            apptSchedule.setStyle(Style.ButtonStyle);
            // apptSchedule.setOnAction (e -> window.setScene(apptScheduleCal);

            Label diagnosisLabel = new Label("Diagnoses");
            GridPane.setHalignment(diagnosisLabel, HPos.CENTER);
            Button writeDiagnosis = new Button();
            writeDiagnosis.setGraphic(new ImageView(new Image(Style.DIAGNOSIS_ICON)));
            writeDiagnosis.setStyle(Style.ButtonStyle);
            // writeDiagnosis.setOnAction(e -> )

            Label logoutLabel = new Label("Log Out");
            GridPane.setHalignment(logoutLabel, HPos.CENTER);
            Button logoutButton = new Button();
            logoutButton.setGraphic(new ImageView(new Image(Style.LOGOUT_ICON)));
            logoutButton.setStyle(Style.ButtonStyle);
            logoutButton.setOnAction(_ -> window.setScene(Menu));

            GridPane doctorGrid = new GridPane();
            doctorGrid.setHgap(20);
            doctorGrid.setVgap(20);
            doctorGrid.setPadding(new Insets(40, 40, 40, 40));
            doctorGrid.setStyle(H2);
            doctorGrid.add(doctorProfile, 0, 0);
            doctorGrid.add(patientProfile, 1, 0);
            doctorGrid.add( apptSchedule, 2, 0);
            doctorGrid.add(doctorProfLabel,0,1);
            doctorGrid.add(patientLabel,1,1);
            doctorGrid.add(apptSchedLabel,2,1);
            doctorGrid.add(writeDiagnosis, 0, 2);
            doctorGrid.add(logoutButton, 2, 2);
            doctorGrid.add(diagnosisLabel,0,3);
            doctorGrid.add(logoutLabel,2,3);

            doctorDashboard.getChildren().addAll(doctorGrid);
        }
        // Patients Table
        {
            Label PatLabel = new Label("Patients Table");
            PatLabel.setStyle(TableLabel);

            ObservableList<Patient> patients = FXCollections.observableArrayList();
            patients.addAll(getPatients());
            TableView<Patient> patientsTable = new TableView<>(patients);
            patientsTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            patientsTable.setFixedCellSize(40);
            patientsTable.setPrefHeight(995);

            TableColumn<Patient,Integer> idCol = new TableColumn<>("ID");
            idCol.setCellValueFactory(new PropertyValueFactory<>("ID"));
            idCol.setMinWidth(80);
            idCol.setStyle(H3);

            TableColumn<Patient, String> nameCol = new TableColumn<>("Full Name");
            nameCol.setMinWidth(400);
            nameCol.setStyle(H3);
            nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

            TableColumn<Patient, String> phoneCol = new TableColumn<>("Phone No.");
            phoneCol.setMinWidth(200);
            phoneCol.setStyle(H3);
            phoneCol.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

            TableColumn<Patient, String> addressCol = new TableColumn<>("Address");
            addressCol.setMinWidth(300);
            addressCol.setStyle(H3);
            addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));

            TableColumn<Patient, String> genderCol = new TableColumn<>("Gender");
            genderCol.setMinWidth(150);
            genderCol.setStyle(H3);
            genderCol.setCellValueFactory(new PropertyValueFactory<>("gender"));

            TableColumn<Patient, Integer> roomCol = new TableColumn<>("Room");
            roomCol.setMinWidth(100);
            roomCol.setStyle(H3);
            roomCol.setCellValueFactory(new PropertyValueFactory<>("roomNumber"));

            patientsTable.getColumns().addAll(idCol,nameCol, phoneCol, addressCol, genderCol,roomCol);

            double tableWidth = idCol.getMinWidth() + nameCol.getMinWidth()+ phoneCol.getMinWidth() + addressCol.getMinWidth() + genderCol.getMinWidth() + roomCol.getMinWidth();
            patientsTable.setMaxWidth(tableWidth+15);

            HBox bar = new HBox(42);
            bar.setAlignment(Pos.CENTER);
            String[] labels = new String[]{"Back", "Save", "Edit", "Add", "Delete"};
            Button[] button = new Button[labels.length];
            for (int i = 0; i < labels.length; i++) {
                button[i] = new Button(labels[i]);
                button[i].setPrefSize(210, 80);
                button[i].setStyle(ButtonStyle);
            }
            button[0].setOnAction(_ -> window.setScene(AdminDashboard));
            button[1].setOnAction(_ -> {
                if (savePatients(patients))
                    AlertBox.alert("Save Successful","Successfully saved Patient Information to server...","Got it");
                else
                    AlertBox.alert("Save Failed","Save Failed, Something went wrong","Got it");
            });
            button[2].setOnAction(_ -> {
                if (patientsTable.getSelectionModel().getSelectedItems().size() == 1)
                    editPatient(patientsTable.getSelectionModel().getSelectedItems(), 1);
                else
                    AlertBox.alert("Warning", "Please Select one Patient at a time!", "Got it");

            });

            button[3].setOnAction(_ -> editPatient(patients, 0));
            button[4].setOnAction(_ -> {
                if (!patientsTable.getSelectionModel().getSelectedItems().isEmpty()) {
                    patients.removeAll(patientsTable.getSelectionModel().getSelectedItems());
                    patientsTable.getSelectionModel().clearSelection();
                } else
                    AlertBox.alert("Warning", "No Patient Selected, Please Select Patient(s) to Delete!", "Got it");
            });
            bar.getChildren().addAll(button);
            patientsT.getChildren().addAll(PatLabel, patientsTable, bar);
            patientsT.setStyle("-fx-background-color: #FFFFFF");
            patientsT.setAlignment(Pos.CENTER);
        }
        // Doctors Table
        {
            Label DocLabel = new Label("Doctors Table");
            DocLabel.setStyle(TableLabel);
            TableColumn<Doctor, Integer> docCol = new TableColumn<>("ID");
            docCol.setPrefWidth(100);
            docCol.setStyle(H3);
            docCol.setCellValueFactory(new PropertyValueFactory<>("ID"));

            TableColumn<Doctor, String> nameCol = new TableColumn<>("Full Name");
            nameCol.setPrefWidth(500);
            nameCol.setStyle(H3);
            nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

            TableColumn<Doctor, String> phoneCol = new TableColumn<>("Phone Number");
            phoneCol.setPrefWidth(250);
            phoneCol.setStyle(H3);
            phoneCol.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

            TableColumn<Doctor, String> departmentCol = new TableColumn<>("Department");
            departmentCol.setStyle(H3);
            departmentCol.setPrefWidth(350);
            departmentCol.setCellValueFactory(new PropertyValueFactory<>("specialty"));

            ObservableList<Doctor> doctors = FXCollections.observableArrayList();
            doctors.addAll(getDoctors());
            TableView<Doctor> doctorsTable = new TableView<>(doctors);
            doctorsTable.getColumns().addAll(docCol, phoneCol, nameCol, departmentCol);
            doctorsTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            doctorsTable.setMaxWidth(docCol.getWidth() + phoneCol.getWidth() + nameCol.getWidth() + departmentCol.getWidth() + 15);
            doctorsTable.setPrefHeight(995);
            doctorsTable.setFixedCellSize(40);
            HBox bar = new HBox(42);
            bar.setAlignment(Pos.CENTER);
            String[] labels = new String[]{"Back", "Save", "Edit", "Add", "Delete"};
            Button[] button = new Button[labels.length];
            for (int i = 0; i < labels.length; i++) {
                button[i] = new Button(labels[i]);
                button[i].setPrefSize(210, 80);
                button[i].setStyle(ButtonStyle);
            }
            button[0].setOnAction(_ -> window.setScene(AdminDashboard));
            button[1].setOnAction(_ -> {
                if(saveDoctors(doctors))
                    AlertBox.alert("Save Successful","Successfully saved Doctor Information to server...","Got it");
                else
                    AlertBox.alert("Save Failed","Save Failed, Something went wrong","Got it");
            });
            button[2].setOnAction(_ -> {
                if (doctorsTable.getSelectionModel().getSelectedItems().size() == 1)
                    editDoctor(doctorsTable.getSelectionModel().getSelectedItems(), 1);
                else
                    AlertBox.alert("Warning", "Please Select one Doctor at a time!", "Got it");

            });

            button[3].setOnAction(_ -> editDoctor(doctors, 0));
            button[4].setOnAction(_ -> {
                if (!doctorsTable.getSelectionModel().getSelectedItems().isEmpty()) {
                    doctors.removeAll(doctorsTable.getSelectionModel().getSelectedItems());
                    doctorsTable.getSelectionModel().clearSelection();
                } else
                    AlertBox.alert("Warning", "No Doctor Selected, Please Select Doctor(s) to Delete!", "Got it");
            });
            bar.getChildren().addAll(button);
            doctorsT.getChildren().addAll(DocLabel, doctorsTable, bar);
            doctorsT.setStyle("-fx-background-color: #FFFFFF");
            doctorsT.setAlignment(Pos.CENTER);
        }
        window.setScene(Menu);
        window.show();
    }
    public static void main(String[] args) {
        launch();
    }
    public void editPatient(ObservableList<Patient> patients, int op) {
        Stage window = new Stage();
        window.setTitle("Adding Patient");
        window.setResizable(false);
        window.initModality(Modality.APPLICATION_MODAL);
        final Patient currentPatient;
        Label label = new Label();
        label.setStyle(H1);
        label.setAlignment(Pos.CENTER);
        label.setText("Adding New Patient");
        label.setPadding(new Insets(20,20,0,20));
        HBox buttons = new HBox(30);
        GridPane grid = new GridPane();
        VBox layout = new VBox(5,label,grid,buttons);
        layout.setAlignment(Pos.CENTER);
        grid.setAlignment(Pos.CENTER);
        buttons.setAlignment(Pos.CENTER);
        grid.setHgap(20);
        grid.setVgap(5);
        grid.setMinHeight(465);
        grid.setPadding(new Insets(20,20,20,20));

        if (patients.isEmpty())
            currentPatient = null;
        else {
            currentPatient = patients.getFirst();
        }
        String[] forms = new String[]{"Full Name","Home Address","Phone Number","Gender","Symptoms","Payment Method"};
        Label[] labels = new Label[forms.length];
        TextField[] fields = new TextField[forms.length];
        for (int i = 0; i < forms.length; i++) {
            labels[i] = new Label(forms[i] + ":");
            labels[i].setStyle(H2);
            fields[i] = new TextField();
            fields[i].setPromptText(forms[i]);
            fields[i].setStyle(H3);
            fields[i].setMaxSize(400,200);
            grid.add(labels[i],0,i);
            grid.add(fields[i],1,i);
        }
        Label emergency = new Label("Emergency:");
        emergency.setStyle(H2);
        CheckBox emergencyCheck = new CheckBox();
        emergencyCheck.setSelected(false);
        emergencyCheck.setStyle(Checkmark);
        grid.add(emergency,0,6);
        grid.add(emergencyCheck,1,6);
        Label roomLabel = new Label("Room:");
        roomLabel.setStyle(H2);
        TextField[] roomField = new TextField[1];
        roomField[0] = new TextField();
        roomField[0].setStyle(H3);
        roomField[0].setPromptText("Room Number");
        emergencyCheck.selectedProperty().addListener((_, _, newValue) -> {
            if (newValue) {
                grid.add(roomLabel,0,7);
                grid.add(roomField[0],1,7);
            }
            else {
                grid.getChildren().removeAll(roomLabel,roomField[0]);
            }
        });
        fields[2].textProperty().addListener((_, _, newValue) -> {
            if (!newValue.matches("\\d*"))
                fields[2].setText(newValue.replaceAll("\\D", ""));
        });
        roomField[0].textProperty().addListener((_, _, newValue) -> {
            if (!newValue.matches("\\d*"))
                roomField[0].setText(newValue.replaceAll("\\D", ""));
        });
        if (op == 1){
            if (currentPatient != null){
                window.setTitle("Edit Patient");
                label.setText("Patient #"+ currentPatient.getID());
                fields[0].setText(currentPatient.getName());
                fields[1].setText(currentPatient.getAddress());
                fields[2].setText(currentPatient.getPhoneNumber());
                fields[3].setText(currentPatient.getGender());
                fields[4].setText(currentPatient.getSymptoms());
                fields[5].setText(currentPatient.getPaymentMethod());
                if(currentPatient.isEmergency()){
                    roomField[0].setText(currentPatient.getRoomNumber());
                    emergencyCheck.setSelected(true);
                }
            }
        }
        Button confirmButton = new Button("Confirm");
        confirmButton.setStyle(ButtonStyle);
        confirmButton.setOnAction(_ -> {
            if (checkEmptyForm(fields)) {
                AlertBox.alert("Warning","Please add the missing Information","Got it");
            }
            else {
                if (op == 0) {
                    if (emergencyCheck.isSelected()){
                        if (checkEmptyForm(roomField)){
                            AlertBox.alert("Warning","Please add the missing Information","Got it");
                        }
                        else {
                            Patient newPatient = new  EmergencyPatient(fields[0].getText(),fields[1].getText(),fields[2].getText(),
                                    fields[3].getText(),fields[4].getText(),fields[5].getText(),Integer.parseInt(roomField[0].getText()),emergencyCheck.isSelected());
                            patients.add(newPatient);
                        }
                    }
                    else{
                        Patient newPatient = new NormalPatient(fields[0].getText(),fields[1].getText(),fields[2].getText(),
                                fields[3].getText(),fields[4].getText(),fields[5].getText(),emergencyCheck.isSelected());
                        patients.add(newPatient);
                    }
                }
                else {
                    if (currentPatient != null) {
                        currentPatient.EditPatient(fields[0].getText(),fields[1].getText(),fields[2].getText(), fields[3].getText(),fields[4].getText(),fields[5].getText(),roomField[0].getText(),emergencyCheck.isSelected());
                    }
                }
                window.close();
            }
        });
        Button cancelButton = new Button("Cancel");
        cancelButton.setStyle(ButtonStyle);
        cancelButton.setOnAction(_ -> window.close());
        buttons.getChildren().addAll(confirmButton,cancelButton);
        window.setScene(new Scene(layout));
        window.show();
    }
    public void editDoctor(ObservableList<Doctor> doctors, int op) {
        Stage window = new Stage();
        window.setTitle("Editing Doctor");
        window.setResizable(false);
        window.initModality(Modality.APPLICATION_MODAL);

        Doctor currentDoctor;
        if (doctors.isEmpty())
            currentDoctor = null;
        else {
            currentDoctor = doctors.getFirst();
        }
        Label label = new Label();
        label.setStyle(H1);
        label.setAlignment(Pos.CENTER);
        label.setText("Add Doctor");
        label.setPadding(new Insets(20,20,0,20));

        HBox buttons = new HBox(30);
        GridPane grid = new GridPane();
        VBox layout = new VBox(5,label,grid,buttons);
        layout.setAlignment(Pos.CENTER);
        grid.setAlignment(Pos.CENTER);
        buttons.setAlignment(Pos.CENTER);
        grid.setHgap(20);
        grid.setVgap(5);
        grid.setPadding(new Insets(20,20,20,20));

        String[] forms = new String[]{"Full Name","Home Address","Phone Number","Department"};
        Label[] labels = new Label[forms.length];
        TextField[] fields = new TextField[forms.length];
        for (int i = 0; i < forms.length; i++) {
            labels[i] = new Label(forms[i] + ":");
            labels[i].setStyle(H2);
            fields[i] = new TextField();
            fields[i].setPromptText(forms[i]);
            fields[i].setStyle(H3);
            fields[i].setMaxSize(400,200);
            grid.add(labels[i],0,i);
            grid.add(fields[i],1,i);
        }

        fields[2].textProperty().addListener((_, _, newValue) -> {
            if (!newValue.matches("\\d*"))
                fields[2].setText(newValue.replaceAll("\\D", ""));
        });


        if (op == 1){
            if (currentDoctor != null){
                window.setTitle("Edit Doctor");
                label.setText("Doctor #"+ currentDoctor.getID());
                fields[0].setText(currentDoctor.getName());
                fields[1].setText(currentDoctor.getAddress());
                fields[2].setText(currentDoctor.getPhoneNumber());
                fields[3].setText(currentDoctor.getSpecialty());
            }
        }
        Button confirmButton = new Button("Confirm");
        confirmButton.setStyle(ButtonStyle);
        confirmButton.setOnAction(_ -> {
            if (checkEmptyForm(fields)) {
                AlertBox.alert("Warning","Please add the missing Information","Got it");
            }
            else {
                if (op == 0) {
                    Doctor newDoctor = new Doctor(fields[0].getText(),fields[1].getText(),fields[2].getText(),fields[3].getText());
                    doctors.add(newDoctor);
                }
                else {
                    if (currentDoctor != null) {
                        currentDoctor.EditDoctor(fields[0].getText(),fields[1].getText(),fields[2].getText(),fields[3].getText());
                    }
                }
                window.close();
            }
        });
        Button cancelButton = new Button("Cancel");
        cancelButton.setStyle(ButtonStyle);
        cancelButton.setOnAction(_ -> window.close());
        buttons.getChildren().addAll(confirmButton,cancelButton);
        window.setScene(new Scene(layout));
        window.show();
    }
    public Set<Doctor> getDoctors(){return Doctor.loadToHashSet();}
    public Set<Patient> getPatients(){return Patient.loadToHashSet();}
    public Boolean savePatients(ObservableList<Patient> patients){
        Patient.updateSet(patients);
        if (Patient.save()){
            System.out.println("Save Successful");
            return true;
        }
        else{
            System.out.println("Save Failed");
            return false;
        }
    }
    public Boolean saveDoctors(ObservableList<Doctor> doctors){
        Doctor.updateSet(doctors);
        if (Doctor.save()){
            System.out.println("Save Successful");
            return true;
        }
        else {
            System.out.println("Save Failed");
            return false;
        }
    }
    public Button BackButton(){
        Button backbutton = new Button("Back");
        backbutton.setMinSize(bwidth,blength);
        backbutton.setStyle(ButtonStyle);
        return backbutton;
    }
    public boolean checkEmptyForm(TextField[] fields) {
        for (TextField field : fields) {
            if (field.getText().isEmpty())
                return true;
        }
        return false;
    }
}
