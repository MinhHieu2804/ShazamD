package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public abstract class Controller2 implements Initializable{
    @FXML
    Button backButton;

    @FXML
    WebView ttt;

    public void backScene1(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("scene1.fxml"));
        Scene s= new Scene(root,600,400);
        Stage window =(Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(s);
        window.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        WebEngine engine= ttt.getEngine();
        engine.load("https://translate.google.com");
    }
}
