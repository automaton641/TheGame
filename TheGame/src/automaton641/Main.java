package automaton641;

import javafx.application.Application;
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

public class  Main extends Application
{
public ListView<String> listView;
public TextField textField;
public void sendCommand()
{
    ObservableList<String> items= listView.getItems();
    items.add("command received: "+textField.getText());
    listView.scrollTo(items.size());
    textField.setText("");
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
    }
    private void handleButtonAction(ActionEvent event)
    {
            sendCommand();
    }
    private void handleKeyPressed(KeyEvent event)
    {
        if (event.getCode().equals(KeyCode.ENTER))
        {
            sendCommand();
        }
    }
}
