package automaton641;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.LinkedList;

public class  Main extends Application
{
    public ListView<String> listView;
    public TextField textField;
    public MainProcess mainProcess;
    public void sendCommand() throws InterruptedException {
        String message = textField.getText();
        writeMessage("Command received: " + message);
        mainProcess.putMessage(message);
        textField.setText("");
    }
    public void writeMessage(String message) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                ObservableList<String> items= listView.getItems();
                items.add(message);
                listView.scrollTo(items.size());
            }
        });
    }
    public static void main(String[] args)
    {

        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception
    {
        stage.setTitle("TheGame");
        textField = new TextField();
        textField.setOnKeyPressed(this::handleKeyPressed);
        Button sendButton = new Button("Enviar");
        sendButton.setOnAction(this::handleButtonAction);
        HBox hBox = new HBox(textField, sendButton);
        HBox.setHgrow(textField, Priority.ALWAYS);
        HBox.setHgrow(sendButton, Priority.NEVER);
        listView = new ListView<>();
        ObservableList<String> items = FXCollections.observableArrayList (
                "Welcome to the game, choose between being server or client\nA) Server\nB) Client");
        listView.setItems(items);
        VBox vbox = new VBox(listView, hBox);
        VBox.setVgrow(listView, Priority.ALWAYS);
        VBox.setVgrow(hBox, Priority.NEVER);
        Scene scene = new Scene(vbox, 1024, 512);
        stage.setScene(scene);
        stage.show();
        mainProcess = new MainProcess();
        mainProcess.main = this;
        mainProcess.start();
    }
    private void handleButtonAction(ActionEvent event)
    {
        try {
            sendCommand();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    private void handleKeyPressed(KeyEvent event)
    {
        if (event.getCode().equals(KeyCode.ENTER))
        {
            try {
                sendCommand();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
