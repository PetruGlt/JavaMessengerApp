package com.example;

import com.google.gson.Gson;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

    private Stage window;

    @Override
    public void start(Stage primaryStage) {
        window = primaryStage;
        // Show the login page first
        // showLoginPage();
        showLoginPage();
    }

    // Method to show the login page
    private void showLoginPage() {
        // Login UI components
        Label usernameLabel = new Label("Username:");
        TextField usernameField = new TextField();

        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();

        Button loginButton = new Button("Login");

        // Layout for login components
        VBox loginLayout = new VBox(10, usernameLabel, usernameField, passwordLabel, passwordField, loginButton);
        loginLayout.setPadding(new Insets(30));
        loginLayout.setStyle("-fx-background-color: gray; -fx-alignment: center;");

        // Login button action
        loginButton.setOnAction(e -> {
            String username = usernameField.getText().trim();
            String password = passwordField.getText().trim();

            if (validateLogin(username, password)) {
                /// TODO
                ///  here a clientID linked with the logged account is recieved from the server
                String clientID = "1";
                showMainMessengerPage(clientID);
            } else {
                showAlert("Login Failed", "Invalid username or password.");
            }
        });

        // Create the scene for the login page
        Scene loginScene = new Scene(loginLayout, 500, 400);
        window.setTitle("Login");
        window.setScene(loginScene);
        window.show();
    }

    /// TODO
    /// (server-side) Method to validate login (hardcoded)
    private boolean validateLogin(String username, String password) {
        return "user".equals(username) && "password123".equals(password); // Example credentials
    }

    /// TODO
    /// (server-side) Method to show an alert message
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Method to show the main messenger page
    private void showMainMessengerPage(String clientID) {
        // Create the ListView for recent conversations on the left
        ListView<String> conversationList = new ListView<>();
        conversationList.setPrefWidth(150);
        conversationList.getItems().addAll("John", "Alice", "Bob"); // Example conversations

        // Create a VBox for messages to allow different alignment
        VBox messageBox = new VBox();
        messageBox.setSpacing(10);

        // Wrap the messageBox inside a ScrollPane for scrolling functionality
        ScrollPane messageScrollPane = new ScrollPane();
        messageScrollPane.setContent(messageBox);
        messageScrollPane.setFitToWidth(true);
        messageScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        messageScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        VBox.setVgrow(messageScrollPane, Priority.ALWAYS);

        // Create the TextArea for input
        TextArea messageInput = new TextArea();
        messageInput.setPromptText("Type your message...");
        messageInput.setWrapText(true);
        messageInput.setMaxHeight(100);
        messageInput.setPrefRowCount(2);
        HBox.setHgrow(messageInput, Priority.ALWAYS);

        // Create the Send Button
        Button sendButton = new Button("Send");
        sendButton.setMinWidth(80);

        // Set up the Send button action
        sendButton.setOnAction(e -> {
            String message = messageInput.getText().trim();
            if (!message.isEmpty()) {
                /// TODO
                ///  List of group of recipients
                Message newMessage = new Message(clientID, message, new ArrayList<>(List.of(2,3)));

                System.out.println(newMessage);
                // Convert to json
                Gson gson = new Gson();
                String jsonMessage = gson.toJson(newMessage);

                /// TODO
                ///  sendMessageToServer(jsonMessage);

                addMessage(messageBox, message, "right");
                messageInput.clear();

                mockReply(messageBox);
            }

        });

        // Layout for message input area and button
        HBox inputLayout = new HBox(10, messageInput, sendButton);
        inputLayout.setPadding(new Insets(10));
        inputLayout.setAlignment(javafx.geometry.Pos.CENTER);

        // Layout for the main area
        HBox mainLayout = new HBox(10, conversationList, messageScrollPane);
        mainLayout.setPadding(new Insets(10));
        HBox.setHgrow(messageScrollPane, Priority.ALWAYS);

        // Main layout - BorderPane
        BorderPane root = new BorderPane();
        root.setCenter(mainLayout);
        root.setBottom(inputLayout);

        // Create Scene for the messenger page
        Scene messengerScene = new Scene(root);
        window.setMinWidth(600);
        window.setMinHeight(400);
        window.setTitle("Messenger App");
        window.setScene(messengerScene);
        window.show();
    }

    // Method to add a message to the VBox with either right or left alignment
    private void addMessage(VBox messageBox, String message, String alignment) {
        // Create a label for the message
        Label messageLabel = new Label(message);
        messageLabel.setWrapText(true);
        messageLabel.setMaxWidth(300);

        // Create timestamp
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        Label timeLabel = new Label(timestamp);
        timeLabel.setStyle("-fx-font-size: 10px; -fx-text-fill: #666666;");

        VBox messageWithTime = new VBox(5);
        messageWithTime.getChildren().addAll(messageLabel, timeLabel);

        // Create an HBox container for the message label
        HBox messageContainer = new HBox();
        messageContainer.setMaxWidth(Double.MAX_VALUE);

        // Add style based on message alignment
        if ("right".equals(alignment)) {
            // Align message to the right
            messageLabel.setStyle("-fx-background-color: lightblue; -fx-padding: 10; -fx-background-radius: 10;");
            messageContainer.setAlignment(javafx.geometry.Pos.CENTER_RIGHT);
            messageContainer.getChildren().add(messageWithTime);
        } else {
            // Align message to the left 
            messageLabel.setStyle("-fx-background-color: lightgray; -fx-padding: 10; -fx-background-radius: 10;");
            messageContainer.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
            messageContainer.getChildren().add(messageWithTime);
        }

        messageBox.getChildren().add(messageContainer);
    }

    // Method to simulate a mock reply after a message is sent
    private void mockReply(VBox messageBox) {
        // Adding a delay to simulate waiting for the mock reply
        new Thread(() -> {
            try {
                Thread.sleep(1500); // Simulate a delay before reply (1.5 seconds)
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Update UI thread after the delay
            javafx.application.Platform.runLater(() -> {
                addMessage(messageBox, "Friend: This is a mock reply to your message.", "left"); // Simulated reply on the left
            });
        }).start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}