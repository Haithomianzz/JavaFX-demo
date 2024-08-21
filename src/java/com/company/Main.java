package com.company;

import com.functions.Appointment;
import com.functions.Doctor;
import com.functions.EmergencyPatient;
import com.functions.NormalPatient;
import com.functions.Patient;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Set;

import static com.database.Handler.verifyCredentials;

public class Main extends Application implements Style {
    private Stage window;
    private static Doctor SessionDoctor;
    private final ObservableList<Patient> SessionPatients = FXCollections.observableArrayList();
    private YearMonth currentMonth;
    private GridPane calendarGrid;
    private Label monthLabel;
    private static String UserType;

    public void start(Stage stage) {
        window = stage;
        window.setTitle("Healthcare System v1.0");
        int[] Resolution = {1920, 1080};
        // Layouts
        StackPane menuPane = new StackPane();
        StackPane loginOPane = new StackPane();
        StackPane loginFPane = new StackPane();
        VBox adminDashboard = new VBox();
        VBox doctorDashboard = new VBox();
        BorderPane doctorsT = new BorderPane();
        BorderPane patientsT = new BorderPane();
        BorderPane doctorPatientsT = new BorderPane();
        BorderPane appointmentsT = new BorderPane();
        BorderPane roomsT = new BorderPane();
        BorderPane schedule = new BorderPane();
        // Scenes
        Scene Menu = new Scene(menuPane, Resolution[0], Resolution[1]);
        Scene LoginOptions = new Scene(loginOPane, Resolution[0], Resolution[1]);
        Scene LoginForm = new Scene(loginFPane, Resolution[0], Resolution[1]);
        Scene AdminDashboard = new Scene(adminDashboard, Resolution[0], Resolution[1]);
        Scene DoctorDashboard = new Scene(doctorDashboard, Resolution[0], Resolution[1]);
        Scene DoctorsTable = new Scene(doctorsT, Resolution[0], Resolution[1]);
        Scene PatientsTable = new Scene(patientsT, Resolution[0], Resolution[1]);
        Scene DoctorPatientsTable = new Scene(doctorPatientsT, Resolution[0], Resolution[1]);
        Scene AppointmentsTable = new Scene(appointmentsT, Resolution[0], Resolution[1]);
        Scene ApptSchedule = new Scene(schedule, Resolution[0], Resolution[1]);
        Scene RoomsTable = new Scene(roomsT, Resolution[0], Resolution[1]);
        // Synced Data Set Between Scenes
        ObservableList<Patient> patients = FXCollections.observableArrayList();
        // Navigation Bar Buttons (Admin User)
        // Navigation Bar Buttons (Doctor User)
        // Same logout as admin^

        // Menu Page
        {
            Image backgroundImage = new Image("file:src/java/com/company/resources/background.png");
            ImageView backgroundImageView = new ImageView(backgroundImage);
            backgroundImageView.setFitWidth(1920);
            backgroundImageView.setFitHeight(1080);
            backgroundImageView.setPreserveRatio(true);

            String[] bLabels = new String[]{"Login", "Exit"};
            Label label = new Label("Welcome to the Healthcare System");
            label.setStyle(H1);
            Button[] button = new Button[bLabels.length];
            for (int i = 0; i < button.length; i++) {
                button[i] = new Button(bLabels[i]);
                button[i].setMinSize(bwidth, blength);
                button[i].setStyle(ButtonStyle);
            }
            button[0].setOnAction(_ -> stage.setScene(LoginOptions));
            button[1].setOnAction(_ -> stage.close());


            VBox frame = new VBox(50);
            frame.setStyle(FrameStyle);
            frame.setAlignment(Pos.CENTER);
            frame.getChildren().add(label);
            frame.getChildren().addAll(button);

            frame.setMaxWidth(750);
            frame.setMaxHeight(450);

            menuPane.getChildren().addAll(backgroundImageView, frame);
        }
        // Login Options Page
        {
            Image backgroundImage = new Image("file:src/java/com/company/resources/background.png");
            ImageView backgroundImageView = new ImageView(backgroundImage);
            backgroundImageView.setFitWidth(1920);
            backgroundImageView.setFitHeight(1080);
            backgroundImageView.setPreserveRatio(true);

            String[] labels = new String[]{"Admin Login", "Physician Login"};
            Button[] button = new Button[labels.length];
            for (int i = 0; i < labels.length; i++) {
                button[i] = new Button(labels[i]);
                button[i].setMinSize(bwidth, blength);
                button[i].setStyle(ButtonStyle);
            }
            button[0].setOnAction(_ -> window.setScene(LoginForm));
            button[1].setOnAction(_ -> window.setScene(LoginForm));
            VBox frame = new VBox(50);
            frame.setStyle(FrameStyle);
            frame.setAlignment(Pos.CENTER);
            frame.getChildren().addAll(button);

            frame.setMaxWidth(750);
            frame.setMaxHeight(450);

            Button backButton = BackButton();
            backButton.setOnAction(_ -> window.setScene(Menu));

            StackPane.setAlignment(frame, Pos.CENTER);
            StackPane.setAlignment(backButton, Pos.BOTTOM_LEFT);
            loginOPane.getChildren().addAll(backgroundImageView, frame, backButton);
        }
        // Login Form Page
        {
            Image backgroundImage = new Image("file:src/java/com/company/resources/background.png");
            ImageView backgroundImageView = new ImageView(backgroundImage);
            backgroundImageView.setFitWidth(1920);
            backgroundImageView.setFitHeight(1080);
            backgroundImageView.setPreserveRatio(true);
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
                    usernameField.clear();
                    passwordField.clear();
                    if (getUserType().equals("Admin"))
                        window.setScene(AdminDashboard);
                    else if (getUserType().equals("Doctor")) {
                        for (Patient p : getPatients()) {
                            if (p.getDoctorInCharge() == SessionDoctor.getID())
                                SessionPatients.add(p);
                        }
                        updateCalendar();
                        window.setScene(DoctorDashboard);
                    }
                    resultLabel.setText("");
                    resultLabel.setStyle("");
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

            StackPane frame = new StackPane();
            frame.setStyle(FrameStyle);
            frame.setPadding(new Insets(20));
            frame.getChildren().add(form);
            frame.setMaxWidth(750);
            frame.setMaxHeight(450);

            loginFPane.getChildren().addAll(backgroundImageView, frame, backButton);
            StackPane.setAlignment(backButton, Pos.BOTTOM_LEFT);
        }
        // Admin DashBoard
        {
            adminDashboard.setStyle("-fx-background-color: #f0f0f0;");
            adminDashboard.setAlignment(Pos.CENTER);
            Label header = new Label("   Admin Dashboard");
            header.setStyle(Header);
            header.setMaxWidth(Double.MAX_VALUE);

            HBox headerBox = new HBox();
            headerBox.getChildren().add(header);
            headerBox.setStyle("-fx-background-color: #003366;");
            headerBox.setAlignment(Pos.CENTER_LEFT);
            headerBox.setPrefHeight(60);
            headerBox.setMaxWidth(Double.MAX_VALUE);

            Label doctorLabel = new Label("Doctors");
            GridPane.setHalignment(doctorLabel, HPos.CENTER);
            Button doctors_Button = new Button();
            doctors_Button.setGraphic(new ImageView(new Image(DOCTORS_ICON)));
            doctors_Button.setStyle(Style.ButtonStyle);
            doctors_Button.setOnAction(_ -> window.setScene(DoctorsTable));

            Label patientLabel = new Label("Patients");
            GridPane.setHalignment(patientLabel, HPos.CENTER);
            Button patients_Button = new Button();
            patients_Button.setGraphic(new ImageView(new Image(PATIENTS_ICON)));
            patients_Button.setStyle(Style.ButtonStyle);
            patients_Button.setOnAction(_ -> { window.setScene(PatientsTable); patients.clear(); patients.addAll(getPatients());});

            Label appointmentLabel = new Label("Appointments");
            GridPane.setHalignment(appointmentLabel, HPos.CENTER);
            Button appointments_Button = new Button();
            appointments_Button.setGraphic(new ImageView(new Image(APPTS_ICON)));
            appointments_Button.setStyle(Style.ButtonStyle);
            appointments_Button.setOnAction(_ -> window.setScene(AppointmentsTable));

            Label roomLabel = new Label("Rooms");
            GridPane.setHalignment(roomLabel, HPos.CENTER);
            Button rooms_Button = new Button();
            rooms_Button.setGraphic(new ImageView(new Image(ROOM_ICON)));
            rooms_Button.setStyle(Style.ButtonStyle);
            rooms_Button.setOnAction(_ -> { window.setScene(RoomsTable);
                patients.clear();
                for (Patient p : getPatients())
                    if (!p.getRoomNumber().equals("N/A"))
                     patients.add(p);
            });

            Label logoutLabel = new Label("Log Out");
            GridPane.setHalignment(logoutLabel, HPos.CENTER);
            Button logout_Button = new Button();
            logout_Button.setGraphic(new ImageView(new Image(LOGOUT_ICON)));
            logout_Button.setStyle(Style.ButtonStyle);
            logout_Button.setOnAction(_ -> window.setScene(Menu));

            GridPane adminGrid = new GridPane();
            adminGrid.setHgap(20);
            adminGrid.setVgap(20);
            adminGrid.setPadding(new Insets(40, 40, 40, 40));
            adminGrid.setStyle(H2);
            adminGrid.add(doctors_Button, 0, 0);
            adminGrid.add(patients_Button, 1, 0);
            adminGrid.add(appointments_Button, 2, 0);
            adminGrid.add(doctorLabel, 0, 1);
            adminGrid.add(patientLabel, 1, 1);
            adminGrid.add(appointmentLabel, 2, 1);
            adminGrid.add(rooms_Button, 0, 2);
            adminGrid.add(logout_Button, 2, 2);
            adminGrid.add(roomLabel, 0, 3);
            adminGrid.add(logoutLabel, 2, 3);

            adminDashboard.getChildren().addAll(adminGrid);
            VBox mainLayout = new VBox();
            mainLayout.getChildren().addAll(headerBox, adminGrid);
            mainLayout.setStyle(BGColor);
            adminDashboard.getChildren().clear();
            adminDashboard.getChildren().add(mainLayout);
        }
        // Doctor DashBoard
        {
            doctorDashboard.setStyle("-fx-background-color: #f0f0f0;");
            doctorDashboard.setAlignment(Pos.CENTER);
            Label header = new Label("   Physician Dashboard");
            header.setStyle(Header);
            header.setMaxWidth(Double.MAX_VALUE);

            HBox headerBox = new HBox();
            headerBox.getChildren().add(header);
            headerBox.setStyle("-fx-background-color: #003366;");
            headerBox.setAlignment(Pos.CENTER_LEFT);
            headerBox.setPrefHeight(60);
            headerBox.setMaxWidth(Double.MAX_VALUE);

            Label doctorProfLabel = new Label("Doctors Profile");
            GridPane.setHalignment(doctorProfLabel, HPos.CENTER);
            Button doctorProfile = new Button();
            doctorProfile.setGraphic(new ImageView(new Image(Style.DOCTORS_ICON)));
            doctorProfile.setStyle(Style.ButtonStyle);
            doctorProfile.setOnAction(_ -> {
                ObservableList<Doctor> doctors = FXCollections.observableArrayList();
                doctors.add(SessionDoctor);
                editDoctor(doctors, 1);
            });
            Label patientLabel = new Label("Patients");
            GridPane.setHalignment(patientLabel, HPos.CENTER);

            Button patientProfile = new Button();
            patientProfile.setGraphic(new ImageView(new Image(Style.PATIENTS_ICON)));
            patientProfile.setStyle(Style.ButtonStyle);
            patientProfile.setOnAction(_ -> window.setScene(DoctorPatientsTable));

            Label apptSchedLabel = new Label("Appointments Schedule");
            GridPane.setHalignment(apptSchedLabel, HPos.CENTER);
            Button apptSchedule = new Button();
            apptSchedule.setGraphic(new ImageView(new Image(Style.APPTS_ICON)));
            apptSchedule.setStyle(Style.ButtonStyle);
            apptSchedule.setOnAction(_ -> window.setScene(ApptSchedule));

            Label diagnosisLabel = new Label("Diagnoses");
            GridPane.setHalignment(diagnosisLabel, HPos.CENTER);
            Button writeDiagnosis = new Button();
            writeDiagnosis.setGraphic(new ImageView(new Image(Style.DIAGNOSIS_ICON)));
            writeDiagnosis.setStyle(Style.ButtonStyle);
            writeDiagnosis.setOnAction(_ -> DiagnoseWindow());

            Label logoutLabel = new Label("Log Out");
            GridPane.setHalignment(logoutLabel, HPos.CENTER);
            Button logout_Button = new Button();
            logout_Button.setGraphic(new ImageView(new Image(Style.LOGOUT_ICON)));
            logout_Button.setStyle(ButtonStyle);
            logout_Button.setOnAction(_ -> window.setScene(Menu));

            GridPane doctorGrid = new GridPane();
            doctorGrid.setHgap(20);
            doctorGrid.setVgap(20);
            doctorGrid.setPadding(new Insets(40, 40, 40, 40));
            doctorGrid.setStyle(H2);
            doctorGrid.add(doctorProfile, 0, 0);
            doctorGrid.add(patientProfile, 1, 0);
            doctorGrid.add(apptSchedule, 2, 0);
            doctorGrid.add(doctorProfLabel, 0, 1);
            doctorGrid.add(patientLabel, 1, 1);
            doctorGrid.add(apptSchedLabel, 2, 1);
            doctorGrid.add(writeDiagnosis, 0, 2);
            doctorGrid.add(logout_Button, 2, 2);
            doctorGrid.add(diagnosisLabel, 0, 3);
            doctorGrid.add(logoutLabel, 2, 3);

            doctorDashboard.getChildren().addAll(doctorGrid);

            VBox mainLayout = new VBox();
            mainLayout.getChildren().addAll(headerBox, doctorGrid);
            mainLayout.setStyle(BGColor);
            doctorDashboard.getChildren().clear();
            doctorDashboard.getChildren().add(mainLayout);
        }
        // Patients Table (ADMIN)
        {
            Label PatLabel = new Label("Patients Table");
            PatLabel.setStyle(TableLabel);

            TableView<Patient> patientsTable = new TableView<>(patients);
            patientsTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            patientsTable.setFixedCellSize(50);
            patientsTable.setPrefHeight(995);

            TableColumn<Patient, Integer> idCol = new TableColumn<>("ID");
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


            patientsTable.getColumns().addAll(idCol, nameCol, phoneCol, addressCol, genderCol);

            double tableWidth = idCol.getMinWidth() + nameCol.getMinWidth() + phoneCol.getMinWidth() + addressCol.getMinWidth() + genderCol.getMinWidth();
            patientsTable.setMaxWidth(tableWidth + 15);


            String[] labels = new String[]{"Back", "Save", "Edit", "Add", "Delete"};
            Button[] button = new Button[labels.length];
            for (int i = 0; i < labels.length; i++) {
                button[i] = new Button(labels[i]);
                button[i].setPrefSize(210, 80);
                button[i].setStyle(ButtonStyle);
            }
            button[0].setOnAction(_ -> window.setScene(AdminDashboard));
            button[1].setOnAction(_ -> AlertBox.alert("Save Successful", "Successfully saved Patient Information to server...", "Got it"));
            button[2].setOnAction(_ -> {
                if (patientsTable.getSelectionModel().getSelectedItems().size() == 1)
                    editPatient(patientsTable.getSelectionModel().getSelectedItems(), 1);
                else
                    AlertBox.alert("Warning", "Please Select one Patient at a time!", "Got it");
            });
            button[3].setOnAction(_ -> editPatient(patients, 0));
            button[4].setOnAction(_ -> {
                if (!patientsTable.getSelectionModel().getSelectedItems().isEmpty()) {
                    for (Patient p : patientsTable.getSelectionModel().getSelectedItems()) {
                        patients.remove(p);
                        Patient.delete(p);
                    }
                    patientsTable.getSelectionModel().clearSelection();
                } else
                    AlertBox.alert("Warning", "No Patient Selected, Please Select Patient(s) to Delete!", "Got it");
            });
            HBox bar = new HBox(42, button);
            bar.setAlignment(Pos.CENTER);
            patientsT.getChildren().addAll(PatLabel, patientsTable, bar);

            VBox tableContent = new VBox(20);
            tableContent.setAlignment(Pos.CENTER);
            tableContent.getChildren().addAll(PatLabel, patientsTable, bar);

            VBox sideMenu = new VBox(20);
            sideMenu.setPadding(new Insets(20));
            sideMenu.setStyle("-fx-background-color: #3a4f63;");
            sideMenu.setPrefWidth(300);

            Button doctorsButton = new Button("Doctors");
            doctorsButton.setStyle(Style.ButtonStyle);
            doctorsButton.setPrefWidth(250);
            doctorsButton.setOnAction(_ -> window.setScene(DoctorsTable));

            Button appointmentsButton = new Button("Appointments");
            appointmentsButton.setStyle(Style.ButtonStyle);
            appointmentsButton.setPrefWidth(250);
            appointmentsButton.setOnAction(_ -> window.setScene(AppointmentsTable));

            Button roomsButton = new Button("Rooms");
            roomsButton.setStyle(Style.ButtonStyle);
            roomsButton.setPrefWidth(250);
            roomsButton.setOnAction(_ -> {
                window.setScene(RoomsTable);
                patients.clear();
                for (Patient p : getPatients())
                    if (!p.getRoomNumber().equals("N/A"))
                        patients.add(p);
            });
            Button adminBack = new Button("Back");
            adminBack.setStyle(ButtonStyle);
            adminBack.setPrefWidth(250);
            adminBack.setOnAction(_ -> window.setScene(AdminDashboard));

            Button logoutButton = new Button("Logout");
            logoutButton.setStyle(Style.ButtonStyle);
            logoutButton.setPrefWidth(250);
            logoutButton.setOnAction(_ -> window.setScene(Menu));
            sideMenu.getChildren().addAll(doctorsButton, appointmentsButton, roomsButton,adminBack, logoutButton);

            patientsT.setLeft(sideMenu);
            patientsT.setCenter(tableContent);
            patientsT.setRight(new VBox());
            patientsT.setBottom(new HBox());
        }
        // Rooms table (ADMIN)
        {
            for (Patient p : getPatients())
                if (!p.getRoomNumber().equals("N/A"))
                    patients.add(p);
            Label roomsLabel = new Label("Rooms Table");
            roomsLabel.setStyle(TableLabel);

            TableView<Patient> roomsTable = new TableView<>(patients);
            roomsTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
            roomsTable.setFixedCellSize(50);
            roomsTable.setPrefHeight(995);

            TableColumn<Patient, Integer> roomCol = new TableColumn<>("Room");
            roomCol.setMinWidth(100);
            roomCol.setStyle(H3);
            roomCol.setCellValueFactory(new PropertyValueFactory<>("roomNumber"));

            TableColumn<Patient, Integer> idCol = new TableColumn<>("ID");
            idCol.setCellValueFactory(new PropertyValueFactory<>("ID"));
            idCol.setMinWidth(80);
            idCol.setStyle(H3);

            TableColumn<Patient, String> nameCol = new TableColumn<>("Full Name");
            nameCol.setMinWidth(400);
            nameCol.setStyle(H3);
            nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

            TableColumn<Patient, String> symptomsCol = new TableColumn<>("Symptoms");
            symptomsCol.setMinWidth(400);
            symptomsCol.setStyle(H3);
            symptomsCol.setCellValueFactory(new PropertyValueFactory<>("symptoms"));

            TableColumn<Patient, Boolean> emergencyCol = new TableColumn<>("Emergency");
            emergencyCol.setMinWidth(300);
            emergencyCol.setStyle(H3);
            emergencyCol.setCellValueFactory(new PropertyValueFactory<>("emergency"));

            double tableWidth = roomCol.getMinWidth() + idCol.getMinWidth() + nameCol.getMinWidth() + symptomsCol.getMinWidth() + emergencyCol.getMinWidth();
            roomsTable.setMaxWidth(tableWidth + 15);

            roomsTable.getColumns().addAll(roomCol, idCol, nameCol, symptomsCol, emergencyCol);

            String[] labels = new String[]{"Back", "Save", "Vacate"};
            Button[] button = new Button[labels.length];
            for (int i = 0; i < labels.length; i++) {
                button[i] = new Button(labels[i]);
                button[i].setPrefSize(210, 80);
                button[i].setStyle(ButtonStyle);
            }
            button[0].setOnAction(_ -> window.setScene(AdminDashboard));
            button[1].setOnAction(_ -> AlertBox.alert("Save Successful", "Successfully saved Room Information to server...", "Got it"));
            button[2].setOnAction(_ -> {
                if (!roomsTable.getSelectionModel().getSelectedItems().isEmpty()) {
                    Patient p = roomsTable.getSelectionModel().getSelectedItems().getFirst();
                    roomsTable.getSelectionModel().getSelectedItem().vacate();
                    patients.remove(p);
                    AlertBox.alert("Vacating Rooms", "Patient vacated successfully...\nSuccessfully saved Room Information to server...", "Got it");
                    roomsTable.getSelectionModel().clearSelection();
                } else
                    AlertBox.alert("Warning", "No Room Selected, Please Select Room(s) to Vacate", "Got it");
            });
            HBox bar = new HBox(42, button);
            bar.setAlignment(Pos.CENTER);
            roomsT.getChildren().addAll(roomsLabel, roomsTable, bar);

            VBox tableContent = new VBox(20);
            tableContent.setAlignment(Pos.CENTER);
            tableContent.getChildren().addAll(roomsLabel, roomsTable, bar);

            VBox sideMenu = new VBox(20);
            sideMenu.setPadding(new Insets(20));
            sideMenu.setStyle("-fx-background-color: #3a4f63;");
            sideMenu.setPrefWidth(300);

            Button doctorsButton = new Button("Doctors");
            doctorsButton.setStyle(Style.ButtonStyle);
            doctorsButton.setPrefWidth(250);
            doctorsButton.setOnAction(_ -> window.setScene(DoctorsTable));

            Button patientsButton = new Button("Patients");
            patientsButton.setStyle(Style.ButtonStyle);
            patientsButton.setPrefWidth(250);
            patientsButton.setOnAction(_ -> {
                window.setScene(PatientsTable);
                patients.clear();
                patients.addAll(getPatients());
            });

            Button appointmentsButton = new Button("Appointments");
            appointmentsButton.setStyle(Style.ButtonStyle);
            appointmentsButton.setPrefWidth(250);
            appointmentsButton.setOnAction(_ -> window.setScene(AppointmentsTable));

            Button adminBack = new Button("Back");
            adminBack.setStyle(ButtonStyle);
            adminBack.setPrefWidth(250);
            adminBack.setOnAction(_ -> window.setScene(AdminDashboard));

            Button logoutButton = new Button("Logout");
            logoutButton.setStyle(Style.ButtonStyle);
            logoutButton.setPrefWidth(250);
            logoutButton.setOnAction(_ -> window.setScene(Menu));

            sideMenu.getChildren().addAll(doctorsButton, patientsButton, appointmentsButton,adminBack, logoutButton);

            roomsT.setLeft(sideMenu);
            roomsT.setCenter(tableContent);
            roomsT.setRight(new VBox());
            roomsT.setBottom(new HBox());
        }
        // Doctor's Patients Table (DOCTOR)
        {
            Label PatLabel = new Label("Patients Table");
            PatLabel.setStyle(TableLabel);

            TableView<Patient> patientsTable = new TableView<>(SessionPatients);
            patientsTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
            patientsTable.setFixedCellSize(50);
            patientsTable.setPrefHeight(995);

            TableColumn<Patient, Integer> idCol = new TableColumn<>("ID");
            idCol.setCellValueFactory(new PropertyValueFactory<>("ID"));
            idCol.setMinWidth(80);
            idCol.setStyle(H3);

            TableColumn<Patient, String> nameCol = new TableColumn<>("Full Name");
            nameCol.setMinWidth(400);
            nameCol.setStyle(H3);
            nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

            TableColumn<Patient, Boolean> emergencyCol = new TableColumn<>("Emergency");
            emergencyCol.setMinWidth(300);
            emergencyCol.setStyle(H3);
            emergencyCol.setCellValueFactory(new PropertyValueFactory<>("emergency"));

            TableColumn<Patient, String> roomCol = new TableColumn<>("Room");
            roomCol.setMinWidth(100);
            roomCol.setStyle(H3);
            roomCol.setCellValueFactory(new PropertyValueFactory<>("roomNumber"));

            patientsTable.getColumns().addAll(idCol, nameCol, emergencyCol, roomCol);
            double tableWidth = idCol.getMinWidth() + nameCol.getMinWidth() + emergencyCol.getWidth() + roomCol.getMinWidth();
            patientsTable.setMaxWidth(tableWidth + 15);

            String[] labels = new String[]{"Back", "Save", "Discharge"};
            Button[] button = new Button[labels.length];
            for (int i = 0; i < labels.length; i++) {
                button[i] = new Button(labels[i]);
                button[i].setPrefSize(210, 80);
                button[i].setStyle(ButtonStyle);
            }
            button[0].setOnAction(_ -> window.setScene(DoctorDashboard));
            button[1].setOnAction(_ -> AlertBox.alert("Save Successful", "Successfully saved Patient Information to server...", "Got it"));
            button[2].setOnAction(_ -> {
                if (!patientsTable.getSelectionModel().getSelectedItems().isEmpty()) {
                    Patient p = patientsTable.getSelectionModel().getSelectedItems().getFirst();
                    if (p.isEmergency()){
                        p.discharge();
                        AlertBox.alert("Discharging Patients", "Patient discharged successfully...\nSuccessfully saved Patient Information to server...", "Got it");
                        patientsTable.refresh();
                    }
                    else
                        AlertBox.alert("Warning", "Patient already Discharged!", "Got it");
                } else
                    AlertBox.alert("Warning", "No Patient Selected to discharge!", "Got it");
            });
            HBox bar = new HBox(42, button);
            bar.setAlignment(Pos.CENTER);
            doctorPatientsT.getChildren().addAll(PatLabel, patientsTable, bar);

            VBox tableContent = new VBox(20);
            tableContent.setAlignment(Pos.CENTER);
            tableContent.getChildren().addAll(PatLabel, patientsTable, bar);

            VBox sideMenu = new VBox(10);
            sideMenu.setPadding(new Insets(20));
            sideMenu.setStyle("-fx-background-color: #3a4f63;");
            sideMenu.setPrefWidth(300);

            Button doctorProfileButton = new Button("Doctor Profile");
            doctorProfileButton.setStyle(ButtonStyle);
            doctorProfileButton.setPrefWidth(250);
            doctorProfileButton.setOnAction(_ -> {
                ObservableList<Doctor> doctors = FXCollections.observableArrayList();
                doctors.add(SessionDoctor);
                editDoctor(doctors, 1);
            });

            Button appointmentSchedButton = new Button("Appointments");
            appointmentSchedButton.setStyle(ButtonStyle);
            appointmentSchedButton.setPrefWidth(250);
            appointmentSchedButton.setOnAction(_ -> window.setScene(ApptSchedule));

            Button doctorBack = new Button("Back");
            doctorBack.setStyle(ButtonStyle);
            doctorBack.setPrefWidth(250);
            doctorBack.setOnAction(_ -> window.setScene(DoctorDashboard));

            Button diagnosesButton = new Button("Diagnoses");
            diagnosesButton.setStyle(ButtonStyle);
            diagnosesButton.setPrefWidth(250);
            diagnosesButton.setOnAction(_ -> DiagnoseWindow());

            Button logoutButton = new Button("Logout");
            logoutButton.setStyle(Style.ButtonStyle);
            logoutButton.setPrefWidth(250);
            logoutButton.setOnAction(_ -> window.setScene(Menu));

            sideMenu.getChildren().addAll(doctorProfileButton, appointmentSchedButton, diagnosesButton,doctorBack, logoutButton);

            doctorPatientsT.setLeft(sideMenu);
            doctorPatientsT.setCenter(tableContent);
            doctorPatientsT.setRight(new VBox());
            doctorPatientsT.setBottom(new HBox());
        }
        // Appointments Table (ADMIN)
        {
            Label AptLabel = new Label("Appointments");
            AptLabel.setStyle(TableLabel);

            TableColumn<Appointment, Integer> appointmentCol = new TableColumn<>("Appointment #");
            appointmentCol.setMinWidth(300);
            appointmentCol.setStyle(H3);
            appointmentCol.setCellValueFactory(new PropertyValueFactory<>("ID"));

            TableColumn<Appointment, Integer> patientCol = new TableColumn<>("Patient #");
            patientCol.setMinWidth(300);
            patientCol.setStyle(H3);
            patientCol.setCellValueFactory(new PropertyValueFactory<>("patientId"));

            TableColumn<Appointment, Integer> doctorCol = new TableColumn<>("Doctor #");
            doctorCol.setMinWidth(300);
            doctorCol.setStyle(H3);
            doctorCol.setCellValueFactory(new PropertyValueFactory<>("doctorId"));

            TableColumn<Appointment, String> dateCol = new TableColumn<>("Date");
            dateCol.setMinWidth(300);
            dateCol.setStyle(H3);
            dateCol.setCellValueFactory(new PropertyValueFactory<>("DateString"));

            ObservableList<Appointment> appointments = FXCollections.observableArrayList();
            appointments.addAll(getAppointments());
            TableView<Appointment> appointmentsTable = new TableView<>(appointments);

            appointmentsTable.getColumns().addAll(appointmentCol, patientCol, doctorCol,dateCol);
            appointmentsTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            appointmentsTable.setMaxWidth(appointmentCol.getWidth() + patientCol.getWidth() + dateCol.getWidth() + doctorCol.getWidth() + 15);
            appointmentsTable.setPrefHeight(995);
            appointmentsTable.setFixedCellSize(50);

            String[] labels = new String[]{"Back", "Save", "Edit", "Add", "Delete"};
            Button[] button = new Button[labels.length];
            for (int i = 0; i < labels.length; i++) {
                button[i] = new Button(labels[i]);
                button[i].setPrefSize(210, 80);
                button[i].setStyle(ButtonStyle);
            }
            button[0].setOnAction(_ -> window.setScene(AdminDashboard));
            button[1].setOnAction(_ -> AlertBox.alert("Save Successful", "Successfully saved Appointment Information to server...", "Got it"));
            button[2].setOnAction(_ -> {
                if (appointmentsTable.getSelectionModel().getSelectedItems().size() == 1) {
                    editAppointment(appointmentsTable.getSelectionModel().getSelectedItems(), 1);
                    appointmentsTable.refresh();
                } else
                    AlertBox.alert("Warning", "Please Select one Appointment at a time!", "Got it");
            });
            button[3].setOnAction(_ -> {
                editAppointment(appointments, 0);
                appointmentsTable.refresh();
            });
            button[4].setOnAction(_ -> {
                if (!appointmentsTable.getSelectionModel().getSelectedItems().isEmpty()) {
                    for (Appointment d : appointmentsTable.getSelectionModel().getSelectedItems()) {
                        appointments.remove(d);
                        Appointment.delete(d);
                        appointmentsTable.refresh();
                    }
                    appointmentsTable.getSelectionModel().clearSelection();
                } else
                    AlertBox.alert("Warning", "No Appointment Selected, Please Select Appointment(s) to Delete!", "Got it");
            });
            HBox bar = new HBox(42, button);
            bar.setAlignment(Pos.CENTER);
            appointmentsT.getChildren().addAll(AptLabel, appointmentsTable, bar);

            VBox tableContent = new VBox(20);
            tableContent.setAlignment(Pos.CENTER);
            tableContent.getChildren().addAll(AptLabel, appointmentsTable, bar);

            VBox sideMenu = new VBox(20);
            sideMenu.setPadding(new Insets(20));
            sideMenu.setStyle("-fx-background-color: #3a4f63;");
            sideMenu.setPrefWidth(300);

            // Buttons
            Button doctorsButton = new Button("Doctors");
            doctorsButton.setStyle(Style.ButtonStyle);
            doctorsButton.setPrefWidth(250);
            doctorsButton.setOnAction(_ -> window.setScene(DoctorsTable));

            Button patientsButton = new Button("Patients");
            patientsButton.setStyle(Style.ButtonStyle);
            patientsButton.setPrefWidth(250);
            patientsButton.setOnAction(_ -> {
                window.setScene(PatientsTable);
                patients.clear();
                patients.addAll(getPatients());
            });

            Button roomsButton = new Button("Rooms");
            roomsButton.setStyle(Style.ButtonStyle);
            roomsButton.setPrefWidth(250);
            roomsButton.setOnAction(_ -> {
                window.setScene(RoomsTable);
                patients.clear();
                for (Patient p : getPatients())
                    if (!p.getRoomNumber().equals("N/A"))
                        patients.add(p);
            });
            Button adminBack = new Button("Back");
            adminBack.setStyle(ButtonStyle);
            adminBack.setPrefWidth(250);
            adminBack.setOnAction(_ -> window.setScene(AdminDashboard));

            Button logoutButton = new Button("Logout");
            logoutButton.setStyle(Style.ButtonStyle);
            logoutButton.setPrefWidth(250);
            logoutButton.setOnAction(_ -> window.setScene(Menu));

            sideMenu.getChildren().addAll(doctorsButton,patientsButton,roomsButton,adminBack, logoutButton);
            appointmentsT.setLeft(sideMenu);
            appointmentsT.setCenter(tableContent);
            appointmentsT.setRight(new VBox());
            appointmentsT.setBottom(new HBox());
        }
        // Doctors Table (DOCTOR)
        {
            Label DocLabel = new Label("Doctors Table");
            DocLabel.setStyle(TableLabel);

            TableColumn<Doctor, Integer> docCol = new TableColumn<>("ID");
            docCol.setMinWidth(100);
            docCol.setStyle(H3);
            docCol.setCellValueFactory(new PropertyValueFactory<>("ID"));

            TableColumn<Doctor, String> nameCol = new TableColumn<>("Full Name");
            nameCol.setMinWidth(500);
            nameCol.setStyle(H3);
            nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

            TableColumn<Doctor, String> phoneCol = new TableColumn<>("Phone Number");
            phoneCol.setMinWidth(250);
            phoneCol.setStyle(H3);
            phoneCol.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

            TableColumn<Doctor, String> departmentCol = new TableColumn<>("Department");
            departmentCol.setStyle(H3);
            departmentCol.setMinWidth(350);
            departmentCol.setCellValueFactory(new PropertyValueFactory<>("specialty"));

            ObservableList<Doctor> doctors = FXCollections.observableArrayList();
            doctors.addAll(getDoctors());
            TableView<Doctor> doctorsTable = new TableView<>(doctors);
            doctorsTable.getColumns().addAll(docCol, phoneCol, nameCol, departmentCol);
            doctorsTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            doctorsTable.setMaxWidth(docCol.getWidth() + phoneCol.getWidth() + nameCol.getWidth() + departmentCol.getWidth() + 15);
            doctorsTable.setPrefHeight(995);
            doctorsTable.setFixedCellSize(50);

            String[] labels = new String[]{"Back", "Save", "Edit", "Add", "Delete"};
            Button[] button = new Button[labels.length];
            for (int i = 0; i < labels.length; i++) {
                button[i] = new Button(labels[i]);
                button[i].setPrefSize(210, 80);
                button[i].setStyle(ButtonStyle);
            }
            button[0].setOnAction(_ -> window.setScene(AdminDashboard));
            button[1].setOnAction(_ -> AlertBox.alert("Save Successful", "Successfully saved Doctor Information to server...", "Got it"));
            button[2].setOnAction(_ -> {
                if (doctorsTable.getSelectionModel().getSelectedItems().size() == 1) {
                    editDoctor(doctorsTable.getSelectionModel().getSelectedItems(), 1);
                    doctorsTable.refresh();
                } else
                    AlertBox.alert("Warning", "Please Select one Doctor at a time!", "Got it");
            });

            button[3].setOnAction(_ -> {
                editDoctor(doctors, 0);
                doctorsTable.refresh();
            });
            button[4].setOnAction(_ -> {
                if (!doctorsTable.getSelectionModel().getSelectedItems().isEmpty()) {
                    for (Doctor d : doctorsTable.getSelectionModel().getSelectedItems()) {
                        doctors.remove(d);
                        Doctor.delete(d);
                        doctorsTable.refresh();
                    }
                    doctorsTable.getSelectionModel().clearSelection();
                } else
                    AlertBox.alert("Warning", "No Doctor Selected, Please Select Doctor(s) to Delete!", "Got it");
            });
            HBox bar = new HBox(42, button);
            bar.setAlignment(Pos.CENTER);
            doctorsT.getChildren().addAll(DocLabel, doctorsTable, bar);

            VBox tableContent = new VBox(20);
            tableContent.setAlignment(Pos.CENTER);
            tableContent.getChildren().addAll(DocLabel, doctorsTable, bar);

            VBox sideMenu = new VBox(20);
            sideMenu.setPadding(new Insets(20));
            sideMenu.setStyle("-fx-background-color: #3a4f63;");
            sideMenu.setPrefWidth(300);

            Button patientsButton = new Button("Patients");
            patientsButton.setStyle(Style.ButtonStyle);
            patientsButton.setPrefWidth(250);
            patientsButton.setOnAction(_ -> {
                window.setScene(PatientsTable);
                patients.clear();
                patients.addAll(getPatients());
            });

            Button appointmentsButton = new Button("Appointments");
            appointmentsButton.setStyle(Style.ButtonStyle);
            appointmentsButton.setPrefWidth(250);
            appointmentsButton.setOnAction(_ -> window.setScene(AppointmentsTable));

            Button roomsButton = new Button("Rooms");
            roomsButton.setStyle(Style.ButtonStyle);
            roomsButton.setPrefWidth(250);
            roomsButton.setOnAction(_ -> {
                window.setScene(RoomsTable);
                patients.clear();
                for (Patient p : getPatients())
                    if (!p.getRoomNumber().equals("N/A"))
                        patients.add(p);
            });

            Button adminBack = new Button("Back");
            adminBack.setStyle(ButtonStyle);
            adminBack.setPrefWidth(250);
            adminBack.setOnAction(_ -> window.setScene(AdminDashboard));

            Button logoutButton = new Button("Logout");
            logoutButton.setStyle(Style.ButtonStyle);
            logoutButton.setPrefWidth(250);
            logoutButton.setOnAction(_ -> window.setScene(Menu));
            sideMenu.getChildren().addAll(patientsButton, appointmentsButton, roomsButton,adminBack, logoutButton);

            doctorsT.setLeft(sideMenu);
            doctorsT.setCenter(tableContent);
            doctorsT.setRight(new VBox());
            doctorsT.setBottom(new HBox());

        }
        // Appointments Schedule (DOCTOR)
        {
            currentMonth = YearMonth.now();
            calendarGrid = new GridPane();
            monthLabel = new Label();

            VBox sideMenu = new VBox(10);
            sideMenu.setPadding(new Insets(20));
            sideMenu.setStyle("-fx-background-color: #3a4f63;");
            sideMenu.setPrefWidth(300);

            Button doctorProfileButton = new Button("Doctor Profile");
            doctorProfileButton.setStyle(ButtonStyle);
            doctorProfileButton.setPrefWidth(250);
            doctorProfileButton.setOnAction(_ -> {
                ObservableList<Doctor> doctors = FXCollections.observableArrayList();
                doctors.add(SessionDoctor);
                editDoctor(doctors, 1);
            });
            Button doctorPatientsButton = new Button("Patients");
            doctorPatientsButton.setStyle(ButtonStyle);
            doctorPatientsButton.setPrefWidth(250);
            doctorPatientsButton.setOnAction(_ -> window.setScene(DoctorPatientsTable));

            Button doctorBack = new Button("Back");
            doctorBack.setStyle(ButtonStyle);
            doctorBack.setPrefWidth(250);
            doctorBack.setOnAction(_ -> window.setScene(DoctorDashboard));

            Button diagnosesButton = new Button("Diagnoses");
            diagnosesButton.setStyle(ButtonStyle);
            diagnosesButton.setPrefWidth(250);
            diagnosesButton.setOnAction(_ -> DiagnoseWindow());

            Button logoutButton = new Button("Logout");
            logoutButton.setStyle(Style.ButtonStyle);
            logoutButton.setPrefWidth(250);
            logoutButton.setOnAction(_ -> window.setScene(Menu));

            sideMenu.getChildren().addAll(doctorProfileButton, doctorPatientsButton, diagnosesButton,doctorBack, logoutButton);

            HBox headerBox = new HBox();
            headerBox.setAlignment(Pos.CENTER);
            headerBox.setStyle("-fx-background-color: #003366;");
            headerBox.setPrefHeight(75);
            headerBox.setMaxWidth(Double.MAX_VALUE);

            Label header = new Label("Appointments");
            header.setStyle(Header);
            header.setMaxWidth(Double.MAX_VALUE);

            headerBox.getChildren().add(header);


            HBox monthNavBox = new HBox(50);
            monthNavBox.setAlignment(Pos.CENTER);
            Button prevMonthButton = new Button("<");
            Button nextMonthButton = new Button(">");

            updateMonthLabel();

            prevMonthButton.setOnAction(_ -> {
                currentMonth = currentMonth.minusMonths(1);
                updateCalendar();
            });

            nextMonthButton.setOnAction(_ -> {
                currentMonth = currentMonth.plusMonths(1);
                updateCalendar();
            });

            monthNavBox.getChildren().addAll(prevMonthButton, monthLabel, nextMonthButton);
            monthNavBox.setStyle("-fx-background-color: #f0f0f0;");


            VBox calendarContent = new VBox();
            calendarContent.setSpacing(10);
            calendarContent.setPadding(new Insets(20));
            calendarContent.setStyle("-fx-background-color: #ffffff;");
            calendarContent.getChildren().addAll(monthNavBox, calendarGrid);
            calendarContent.setAlignment(Pos.CENTER);
            calendarGrid.setAlignment(Pos.CENTER);

            schedule.setTop(headerBox);
            schedule.setLeft(sideMenu);
            schedule.setCenter(calendarContent);


            schedule.setBottom(new HBox());
            schedule.setRight(new HBox());


        }
        window.setScene(Menu);
        window.show();
    }

    public static void main(String[] args) {
        launch();
    }

    private void DiagnoseWindow() {
        Stage window = new Stage();
        VBox diagnosisM = new VBox(20);
        window.setTitle("Diagnoses Menu");
        window.initModality(Modality.APPLICATION_MODAL);
        Label diagnosisLabel = new Label("Diagnoses");
        diagnosisLabel.setStyle(H1);
        diagnosisLabel.setAlignment(Pos.CENTER);
        TreeItem<String> root = new TreeItem<>();
        root.setExpanded(true);
        TreeItem<String> diagnosed = makeBranch("Diagnosed ✅", root);
        TreeItem<String> undiagnosed = makeBranch("Undiagnosed 📄", root);
        for (Patient patient : SessionPatients) {
            if (patient.getDiagnosis() != null)
                makeBranch(patient.getName(), diagnosed);
            else
                makeBranch(patient.getName(), undiagnosed);
        }
        TreeView<String> diagnosisList = new TreeView<>(root);
        diagnosisList.setShowRoot(false);
        diagnosisList.setStyle(H3);
        diagnosisList.setPrefWidth(500);
        diagnosisList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        String[] labels = new String[]{"View", "Diagnose", "Edit", "Back"};
        Button[] button = new Button[labels.length];
        for (int i = 0; i < labels.length; i++) {
            button[i] = new Button(labels[i]);
            button[i].setPrefSize(210, 80);
            button[i].setStyle(ButtonStyle);
        }
        button[0].setOnAction(_ -> {
            if (diagnosisList.getSelectionModel().getSelectedItem().getParent() == diagnosed)
                for (Patient patient : SessionPatients) {
                    if (patient.getName().equals(diagnosisList.getSelectionModel().getSelectedItem().getValue()))
                        Diagnose(patient, 0);
                }
            else if (diagnosisList.getSelectionModel().getSelectedItem().getParent() == undiagnosed)
                AlertBox.alert("Warning", "Please Select a diagnosed Patient...", "Got it");
            else
                AlertBox.alert("Warning", "No Patient Selected, Please Select Patient to view...", "Got it");
        });
        button[1].setOnAction(_ -> {
            if (diagnosisList.getSelectionModel().getSelectedItem().getParent() == undiagnosed)
                for (Patient patient : SessionPatients) {
                    if (patient.getName().equals(diagnosisList.getSelectionModel().getSelectedItem().getValue())) {
                        Diagnose(patient, 1);
                        moveBranch(diagnosisList.getSelectionModel().getSelectedItem(), diagnosed);
                    }
                }
            else if (diagnosisList.getSelectionModel().getSelectedItem().getParent() == diagnosed)
                AlertBox.alert("Warning", "Please Select an Undiagnosed Patient...", "Got it");
            else
                AlertBox.alert("Warning", "No Patient Selected, Please Select Patient to diagnose...", "Got it");
        });
        button[2].setOnAction(_ -> {
            if (diagnosisList.getSelectionModel().getSelectedItem().getParent() == diagnosed)
                for (Patient patient : SessionPatients) {
                    if (patient.getName().equals(diagnosisList.getSelectionModel().getSelectedItem().getValue()))
                        Diagnose(patient, 1);
                }
            else if (diagnosisList.getSelectionModel().getSelectedItem().getParent() == undiagnosed)
                AlertBox.alert("Warning", "Please Select a Diagnosed Patient...", "Got it");
            else
                AlertBox.alert("Warning", "No Patient Selected, Please Select Patient to view...", "Got it");
        });
        button[3].setOnAction(_ -> window.close());
        VBox bar = new VBox(26, button);
        bar.setAlignment(Pos.CENTER);
        HBox middle = new HBox(20, diagnosisList, bar);
        middle.setAlignment(Pos.CENTER);
        diagnosisM.getChildren().addAll(diagnosisLabel, middle);
        diagnosisM.setAlignment(Pos.CENTER);
        diagnosisM.setPadding(new Insets(20, 20, 20, 20));
        Scene DiagnosisMenu = new Scene(diagnosisM);
        window.setScene(DiagnosisMenu);
        window.show();
    }

    private void Diagnose(Patient patient, int mode) {
        Stage window = new Stage();
        window.setTitle("Diagnosing Patient");
        window.initModality(Modality.APPLICATION_MODAL);

        HBox buttons = new HBox(30);
        GridPane grid = new GridPane();
        Separator separator1 = new Separator();
        separator1.setOrientation(Orientation.HORIZONTAL);
        separator1.setStyle("-fx-border-style: solid; -fx-border-width: 2; -fx-background-color: #000000; -fx-border-color: #000000;");

        Label symptomHeaderLabel = new Label("Symptoms:");
        symptomHeaderLabel.setStyle(H3);
        symptomHeaderLabel.setAlignment(Pos.CENTER);

        Label symptomLabel = new Label(patient.getSymptoms());
        symptomLabel.setStyle(H3 + "-fx-border-style: solid; -fx-border-width: 2; -fx-border-color: #000000;");
        symptomLabel.setMinSize(850, 200);
        symptomLabel.setAlignment(Pos.TOP_LEFT);

        Label diagnoHeaderLabel = new Label("Diagnosis:");
        diagnoHeaderLabel.setStyle(H3);
        diagnoHeaderLabel.setAlignment(Pos.CENTER);

        Label diagnoLabel = new Label(patient.getDiagnosis());
        diagnoLabel.setStyle(H3 + "-fx-border-style: solid; -fx-border-width: 2; -fx-border-color: #000000;");
        diagnoLabel.setMinSize(850, 200);
        diagnoLabel.setAlignment(Pos.TOP_LEFT);

        TextField diagnoText = new TextField(patient.getDiagnosis());
        diagnoText.setStyle(H3 + "-fx-border-style: solid; -fx-border-width: 2; -fx-border-color: #000000;");
        diagnoText.setPromptText("Write Diagnosis Here:");
        diagnoText.setMinSize(850, 200);
        diagnoText.setPrefColumnCount(1);
        diagnoText.setAlignment(Pos.TOP_LEFT);
        VBox layout = new VBox(10, grid, separator1, symptomHeaderLabel, symptomLabel, diagnoHeaderLabel);
        if (mode == 0)
            layout.getChildren().addAll(diagnoLabel, buttons);
        if (mode == 1)
            layout.getChildren().addAll(diagnoText, buttons);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20, 20, 20, 20));
        ColumnConstraints col1 = new ColumnConstraints();
        ColumnConstraints col2 = new ColumnConstraints();
        col1.setPercentWidth(55);
        col2.setPercentWidth(45);
        grid.setAlignment(Pos.CENTER);
        grid.setVgap(5);
        grid.setMinWidth(850);
        grid.getColumnConstraints().addAll(col1, col2);
        buttons.setAlignment(Pos.CENTER);
        String[] data = new String[]{"Full Name: " + patient.getName(), "Patient ID:" + patient.getID(),
                "Phone Number: " + patient.getPhoneNumber(), "Gender: " + patient.getGender()};
        Label[] labels = new Label[data.length];
        for (int i = 0; i < data.length; i++) {
            labels[i] = new Label(data[i]);
            labels[i].setStyle(H3);
            grid.add(labels[i], i % 2, i / 2);
        }
        if (patient.isEmergency()) {
            String[] emergData = new String[]{"Emergency: Yes", "Room Number: " + patient.getRoomNumber()};
            Label[] emergLabels = new Label[emergData.length];
            for (int i = 0; i < emergData.length; i++) {
                emergLabels[i] = new Label(emergData[i]);
                emergLabels[i].setStyle(H3);
                grid.add(emergLabels[i], i, 2);

            }
        }
        Button cancelButton = new Button("Cancel");
        cancelButton.setStyle(ButtonStyle);
        cancelButton.setOnAction(_ -> window.close());
        Button confirmButton = new Button("Confirm");
        confirmButton.setStyle(ButtonStyle);
        confirmButton.setOnAction(_ -> {
            if (mode == 1) {
                patient.setDiagnosis(diagnoText.getText());
                Patient.diagnose(patient);
            }
            window.close();
        });
        buttons.getChildren().addAll(confirmButton, cancelButton);
        buttons.setPadding(new Insets(20, 0, 0, 0));
        window.setScene(new Scene(layout));
        window.show();
    }

    private void updateMonthLabel() {
        monthLabel.setText(currentMonth.getMonth() + " " + currentMonth.getYear());
    }

    private void updateCalendar() {
        calendarGrid.getChildren().clear();

        LocalDate firstDayOfMonth = currentMonth.atDay(1);
        int dayOfWeek = firstDayOfMonth.getDayOfWeek().getValue(); // 1=Monday, 7=Sunday
        int daysInMonth = currentMonth.lengthOfMonth();

        String[] dayNames = {"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};
        for (int i = 0; i < dayNames.length; i++) {
            Label dayHeader = new Label(dayNames[i]);
            dayHeader.setStyle("-fx-font-weight: bold; -fx-padding: 5px; -fx-font-size: 30px;");
            calendarGrid.add(dayHeader, i, 0);
            GridPane.setHalignment(dayHeader, HPos.CENTER);
        }


        for (int day = 1; day <= daysInMonth; day++) {
            Button dayButton = new Button(String.valueOf(day));
            dayButton.setPrefSize(150, 150);
            if (Appointment.isThereAppointment(currentMonth.atDay(day))) {
                Appointment A = Appointment.atDate(currentMonth.atDay(day));
                if (A != null){
                    if(A.getDoctorId() == SessionDoctor.getID()){
                        dayButton.setStyle("-fx-background-color: lime");
                        ObservableList<Appointment> Aptment = FXCollections.observableArrayList();
                        Aptment.add(A);
                        dayButton.setOnAction(_ -> editAppointment(Aptment,2));
                    }
                }
            }
            calendarGrid.add(dayButton, (dayOfWeek - 1 + day - 1) % 7, (dayOfWeek - 1 + day - 1) / 7 + 1);
        }

        updateMonthLabel();

    }

    private void moveBranch(TreeItem<String> branch, TreeItem<String> target) {
        branch.getParent().getChildren().remove(branch);
        target.getChildren().add(branch);
    }

    private TreeItem<String> makeBranch(String title, TreeItem<String> parent) {
        TreeItem<String> branch = new TreeItem<>(title);
        branch.setExpanded(true);
        parent.getChildren().add(branch);
        return branch;
    }

    private void editPatient(ObservableList<Patient> patients, int mode) {
        Stage window = new Stage();
        window.setTitle("Adding Patient");
        window.setResizable(false);
        window.initModality(Modality.APPLICATION_MODAL);
        final Patient currentPatient;
        Label label = new Label();
        label.setStyle(H1);
        label.setAlignment(Pos.CENTER);
        label.setText("Adding New Patient");
        HBox buttons = new HBox(30);
        GridPane grid = new GridPane();
        VBox layout = new VBox(5, label, grid, buttons);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20, 20, 20, 20));
        grid.setAlignment(Pos.CENTER);
        buttons.setAlignment(Pos.CENTER);
        grid.setHgap(20);
        grid.setVgap(5);
        grid.setMinHeight(500);
        grid.setPadding(new Insets(20, 20, 20, 20));
        if (patients.isEmpty())
            currentPatient = null;
        else {
            currentPatient = patients.getFirst();
        }
        String[] forms = new String[]{"Full Name", "Home Address", "Phone Number", "Gender", "Symptoms", "Payment Method"};
        Label[] labels = new Label[forms.length];
        TextField[] fields = new TextField[forms.length];
        for (int i = 0; i < forms.length; i++) {
            labels[i] = new Label(forms[i] + ":");
            labels[i].setStyle(H2);
            fields[i] = new TextField();
            fields[i].setPromptText(forms[i]);
            fields[i].setStyle(H3);
            fields[i].setMaxSize(400, 200);
            grid.add(labels[i], 0, i);
            grid.add(fields[i], 1, i);
        }
        Label doctorIC = new Label("Physician:");
        doctorIC.setStyle(H2);

        ChoiceBox<String> Doctors = new ChoiceBox<>();
        Doctors.getItems().add("Doctor...");
        Doctors.getSelectionModel().select("Doctor...");
        for (Doctor d : getDoctors())
            Doctors.getItems().add(d.getName());
        Doctors.setStyle(H3);
        grid.add(doctorIC, 0, 6);
        grid.add(Doctors, 1, 6);
        Label emergency = new Label("Emergency:");
        emergency.setStyle(H2);
        CheckBox emergencyCheck = new CheckBox();
        emergencyCheck.setSelected(false);
        emergencyCheck.setStyle(Checkmark);
        grid.add(emergency, 0, 7);
        grid.add(emergencyCheck, 1, 7);
        Label roomLabel = new Label("Room:");
        roomLabel.setStyle(H2);
        TextField[] roomField = new TextField[1];
        roomField[0] = new TextField();
        roomField[0].setStyle(H3);
        roomField[0].setPromptText("Room Number");
        emergencyCheck.selectedProperty().addListener((_, _, newValue) -> {
            if (newValue) {
                grid.add(roomLabel, 0, 8);
                grid.add(roomField[0], 1, 8);
            } else {
                grid.getChildren().removeAll(roomLabel, roomField[0]);
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
        if (mode == 1) {
            if (currentPatient != null) {
                window.setTitle("Edit Patient");
                label.setText("Patient #" + currentPatient.getID());
                fields[0].setText(currentPatient.getName());
                fields[1].setText(currentPatient.getAddress());
                fields[2].setText(currentPatient.getPhoneNumber());
                fields[3].setText(currentPatient.getGender());
                fields[4].setText(currentPatient.getSymptoms());
                fields[5].setText(currentPatient.getPaymentMethod());
                for (Doctor d : getDoctors())
                    if (d.getID() == currentPatient.getDoctorInCharge())
                        Doctors.getSelectionModel().select(d.getName());
                if (currentPatient.isEmergency()) {
                    roomField[0].setText(currentPatient.getRoomNumber());
                    emergencyCheck.setSelected(true);
                }
            }
        }
        Button confirmButton = new Button("Confirm");
        confirmButton.setStyle(ButtonStyle);
        confirmButton.setOnAction(_ -> {
            if (checkEmptyForm(fields)) {
                if (Doctors.getValue().equals("Doctor..."))
                    AlertBox.alert("Warning", "Please add the missing Information", "Got it");
            } else {
                int dID = 0;
                for (Doctor d : getDoctors())
                    if (d.getName().equals(Doctors.getSelectionModel().getSelectedItem()))
                        dID = d.getID();
                if (mode == 0) {
                    if (emergencyCheck.isSelected()) {
                        if (checkEmptyForm(roomField)) {
                            AlertBox.alert("Warning", "Please add the missing Information", "Got it");
                        } else {
                            Patient newPatient = new EmergencyPatient(fields[0].getText(), fields[1].getText(), fields[2].getText(),
                                    fields[3].getText(), fields[4].getText(), fields[5].getText(), Integer.parseInt(roomField[0].getText()), emergencyCheck.isSelected(), dID);
                            patients.add(newPatient);
                            Patient.add(newPatient);
                        }
                    } else {
                        Patient newPatient = new NormalPatient(fields[0].getText(), fields[1].getText(), fields[2].getText(),
                                fields[3].getText(), fields[4].getText(), fields[5].getText(), emergencyCheck.isSelected(), dID);
                        patients.add(newPatient);
                        Patient.add(newPatient);
                    }
                } else {
                    if (currentPatient != null) {
                        currentPatient.EditPatient(fields[0].getText(), fields[1].getText(), fields[2].getText(), fields[3].getText(), fields[4].getText(), fields[5].getText(), roomField[0].getText(), emergencyCheck.isSelected(), dID);
                        Patient.save(currentPatient);
                    }
                }
                window.close();
            }
        });
        Button cancelButton = new Button("Cancel");
        cancelButton.setStyle(ButtonStyle);
        cancelButton.setOnAction(_ -> window.close());
        buttons.getChildren().addAll(confirmButton, cancelButton);
        window.setScene(new Scene(layout));
        window.show();
    }

    private void editDoctor(ObservableList<Doctor> doctors, int mode) {
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
        label.setPadding(new Insets(20, 20, 0, 20));

        HBox buttons = new HBox(30);
        GridPane grid = new GridPane();
        VBox layout = new VBox(5, label, grid, buttons);
        layout.setAlignment(Pos.CENTER);
        grid.setAlignment(Pos.CENTER);
        buttons.setAlignment(Pos.CENTER);
        grid.setHgap(20);
        grid.setVgap(5);
        grid.setPadding(new Insets(20, 20, 20, 20));
        String[] forms = new String[]{"Full Name", "Home Address", "Phone Number", "Department"};
        Label[] labels = new Label[forms.length];
        TextField[] fields = new TextField[forms.length];
        for (int i = 0; i < forms.length; i++) {
            labels[i] = new Label(forms[i] + ":");
            labels[i].setStyle(H2);
            fields[i] = new TextField();
            fields[i].setPromptText(forms[i]);
            fields[i].setStyle(H3);
            fields[i].setMaxSize(400, 200);
            grid.add(labels[i], 0, i);
            grid.add(fields[i], 1, i);
        }
        fields[2].textProperty().addListener((_, _, newValue) -> {
            if (!newValue.matches("\\d*"))
                fields[2].setText(newValue.replaceAll("\\D", ""));
        });
        if (mode == 1) {
            if (currentDoctor != null) {
                window.setTitle("Edit Doctor");
                label.setText("Doctor #" + currentDoctor.getID());
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
                AlertBox.alert("Warning", "Please add the missing Information", "Got it");
            } else {
                if (mode == 0) {
                    Doctor newDoctor = new Doctor(fields[0].getText(), fields[1].getText(), fields[2].getText(), fields[3].getText());
                    doctors.add(newDoctor);
                    Doctor.add(newDoctor);
                } else {
                    if (currentDoctor != null) {
                        currentDoctor.EditDoctor(fields[0].getText(), fields[1].getText(), fields[2].getText(), fields[3].getText());
                        Doctor.save(currentDoctor);
                    }
                }
                window.close();
            }
        });
        Button cancelButton = new Button("Cancel");
        cancelButton.setStyle(ButtonStyle);
        cancelButton.setOnAction(_ -> window.close());
        buttons.getChildren().addAll(confirmButton, cancelButton);
        window.setScene(new Scene(layout));
        window.show();
    }

    private void editAppointment(ObservableList<Appointment> appointments, int mode) {
        Stage window = new Stage();
        window.setTitle("Editing Appointment");
        window.setResizable(false);
        window.initModality(Modality.APPLICATION_MODAL);
        Appointment appointment;
        if (appointments.isEmpty())
            appointment = null;
        else
            appointment = appointments.getFirst();
        Label label = new Label();
        label.setStyle(H1);
        label.setAlignment(Pos.CENTER);
        label.setText("Make Appointment");
        label.setPadding(new Insets(20, 20, 0, 20));
        HBox buttons = new HBox(30);
        GridPane grid = new GridPane();
        VBox layout = new VBox(5, label, grid, buttons);
        layout.setAlignment(Pos.CENTER);
        grid.setAlignment(Pos.CENTER);
        buttons.setAlignment(Pos.CENTER);
        grid.setHgap(20);
        grid.setVgap(5);
        grid.setPadding(new Insets(20, 20, 20, 20));
        ChoiceBox<String> Patients = new ChoiceBox<>();
        Patients.setMaxSize(400, 200);
        for (Patient p : getPatients())
            Patients.getItems().add(p.getName());
        Patients.setStyle(H3);
        ChoiceBox<String> Doctors = new ChoiceBox<>();
        Doctors.setMaxSize(400, 200);
        for (Doctor d : getDoctors())
            Doctors.getItems().add(d.getName());
        Doctors.setStyle(H3);
        Doctors.getSelectionModel().selectedItemProperty().addListener((_, _, newValue) -> {
            Patients.getItems().clear();
            int SelectedId = 0;
            for (Doctor d : getDoctors())
                if (newValue.equals(d.getName()))
                    SelectedId = d.getID();
            for (Patient p : getPatients())
                if (p.getDoctorInCharge() == SelectedId)
                    Patients.getItems().add(p.getName());
        });
        DatePicker datePicker = new DatePicker();
        datePicker.setStyle(H3);
        datePicker.setMaxSize(400, 200);
        String[] forms = new String[]{"Doctor", "Patient", "Date"};
        Label[] labels = new Label[forms.length];
        for (int i = 0; i < forms.length; i++) {
            labels[i] = new Label(forms[i] + ":");
            labels[i].setStyle(H2);
            grid.add(labels[i], 0, i);
        }
        if (mode == 1) {
            if (appointment != null) {
                window.setTitle("Edit Appointment");
                label.setText("Appointment #" + appointment.getID());
                for (Doctor d : getDoctors())
                    if (d.getID() == appointment.getDoctorId())
                        Doctors.getSelectionModel().select(d.getName());
                for (Patient p : getPatients())
                    if (p.getID() == appointment.getPatientId())
                        Patients.getSelectionModel().select(p.getName());
                datePicker.setValue(appointment.getDate().getDate());
            }
        }
        if (mode == 2){
            if (appointment != null) {
                window.setTitle("View Appointment");
                label.setText("Appointment #" + appointment.getID());
                String dName = "", pName = "", aDate;
                for (Doctor d : getDoctors())
                    if (d.getID() == appointment.getDoctorId())
                        dName = d.getName();
                for (Patient p : getPatients())
                    if (p.getID() == appointment.getPatientId())
                        pName = p.getName();
                aDate = appointment.getDate().toString();
                String[] Data = new String[]{dName,pName,aDate};
                Label[] AptData = new Label[Data.length];
                AptData[0] = new Label("Dr. " + Data[0]);
                AptData[1] = new Label(Data[1]);
                AptData[2] = new Label(Data[2]);
                for (int i = 0; i < Data.length; i++) {
                    AptData[i].setStyle(H3);
                    grid.add(AptData[i], 1, i);
                }
            }
        }
        Button confirmButton = new Button("Confirm");
        confirmButton.setStyle(ButtonStyle);
        if (mode < 2) {
            grid.add(Doctors, 1, 0);
            grid.add(Patients, 1, 1);
            grid.add(datePicker, 1, 2);
            confirmButton.setOnAction(_ -> {
                if (Doctors.getValue() == null || Patients.getValue() == null || datePicker.getValue().equals(LocalDate.now()))
                    AlertBox.alert("Warning", "Please add the missing Information", "Got it");
                else {
                    int Did = 0;
                    int Pid = 0;
                    for (Doctor d : getDoctors())
                        if (Doctors.getValue().equals(d.getName()))
                            Did = d.getID();
                    for (Patient p : getPatients())
                        if (Patients.getValue().equals(p.getName()))
                            Pid = p.getID();
                    if (mode == 0) {
                        Appointment newAppointment = new Appointment(Did, Pid, datePicker.getValue());
                        appointments.add(newAppointment);
                        Appointment.add(newAppointment);
                    } else {
                        if (appointment != null)
                            appointment.EditAppointment(Pid, Did, datePicker.getValue());
                    }
                    window.close();

                }
            });
        } else {
            confirmButton.setOnAction(_ -> window.close());
        }
        layout.setPrefSize(600, 400);
        Button cancelButton = new Button("Cancel");
        cancelButton.setStyle(ButtonStyle);
        cancelButton.setOnAction(_ -> window.close());
        buttons.getChildren().addAll(confirmButton, cancelButton);
        window.setScene(new Scene(layout));
        window.show();
    }

    private Set<Doctor> getDoctors() {
        return Doctor.loadDoctors();
    }

    private Set<Patient> getPatients() {
        return Patient.loadPatients();
    }

    private Set<Appointment> getAppointments() {
        return Appointment.loadAppointments();
    }

    private Button BackButton() {
        Button backbutton = new Button("Back");
        backbutton.setMinSize(bwidth, blength);
        backbutton.setStyle(ButtonStyle);
        return backbutton;
    }

    private boolean checkEmptyForm(TextField[] fields) {
        for (TextField field : fields) {
            if (field.getText().isEmpty())
                return true;
        }
        return false;
    }

    private String getUserType() {
        return UserType;
    }

    public static void setUserType(String userType) {
        UserType = userType;
    }

    public static void setCurrentDoctor(Doctor currentDoctor) {
        SessionDoctor = currentDoctor;
    }
}
